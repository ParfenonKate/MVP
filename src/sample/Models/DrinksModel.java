package sample.Models;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DrinksModel {

    Class<? extends Drinks> drinksFilter = Drinks.class;

    ArrayList<Drinks> drinkList = new ArrayList<>();
    private int counter = 1;

    public interface DataChangedListener {
        void dataChanged(ArrayList<Drinks> drinks);
    }

    private ArrayList<DataChangedListener> dataChangedListeners = new ArrayList<>();
    public void addDataChangedListener(DataChangedListener listener) {

        this.dataChangedListeners.add(listener);
    }
    public void setDrinksFilter(Class<? extends Drinks> drinksFilter) {
        this.drinksFilter = drinksFilter;
        this.emitDataChanged();
    }


    public void add(Drinks drinks, boolean emit) {
        drinks.id = counter;
        counter += 1;

        this.drinkList.add(drinks);

        if (emit) {
            this.emitDataChanged();
        }
    }

    public void add(Drinks drinks) {
        add(drinks, true);
    }

    private void emitDataChanged() {
        for (DataChangedListener listener : dataChangedListeners) {
            ArrayList<Drinks> filteredList = new ArrayList<>(
                    drinkList.stream()
                            .filter(food -> drinksFilter.isInstance(food))
                            .collect(Collectors.toList())
            );
            listener.dataChanged(filteredList);
        }
    }

    public void edit(Drinks drinks) {

        for (int i = 0; i< this.drinkList.size(); ++i) {

            if (this.drinkList.get(i).id == drinks.id) {

                this.drinkList.set(i, drinks);
                break;
            }
        }
        this.emitDataChanged();
    }

    public void delete(int id)
    {
        for (int i = 0; i< this.drinkList.size(); ++i) {

            if (this.drinkList.get(i).id == id) {
                this.drinkList.remove(i);
                break;
            }
        }
        this.emitDataChanged();
    }

    public void saveToFile(String path) {

        try (Writer writer =  new FileWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<ArrayList<Drinks>>() { })
                    .withDefaultPrettyPrinter()
                    .writeValue(writer, drinkList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String path) {
        try (Reader reader =  new FileReader(path)) {
            ObjectMapper mapper = new ObjectMapper();
            drinkList = mapper.readerFor(new TypeReference<ArrayList<Drinks>>() { })
                    .readValue(reader);

            this.counter = drinkList.stream()
                    .map(drinks -> drinks.id)
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.emitDataChanged();
    }




}
