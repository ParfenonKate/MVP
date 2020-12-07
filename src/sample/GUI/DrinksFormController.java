package sample.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.Models.*;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class DrinksFormController implements Initializable  {

    public ChoiceBox cmbDrinksType;
    public TextField txtDrinksVolume;


    public VBox JuicePane;
    public CheckBox chkHasPulp;
    public ChoiceBox cmbFruitType;

    public VBox SodaPane;
    public ChoiceBox cmbSodaType;
    public TextField txtBubbles;

    public VBox AlcoholPane;
    public ChoiceBox cmbAlcoholType;
    public TextField txtStrength;

    final String DRINKS_JUICE = "Сок";
    final String DRINKS_SODA = "Газировка";
    final String DRINKS_ALCOHOL = "Алкоголь";

    private Integer id = null;
    public DrinksModel drinksModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbDrinksType.setItems(FXCollections.observableArrayList(
                DRINKS_JUICE,
                DRINKS_SODA,
                DRINKS_ALCOHOL
        ));

        cmbDrinksType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updatePanes((String) newValue);
        });

        cmbFruitType.getItems().setAll(
                Juice.Type.Apple,
                Juice.Type.Orange,
                Juice.Type.Cherry
        );


        cmbFruitType.setConverter(new StringConverter<Juice.Type>() {
            @Override
            public String toString(Juice.Type object) {
                // просто указываем как рендерить
                switch (object) {
                    case Apple:
                        return "Яблочный";
                    case Orange:
                        return "Апельсиновый";
                    case Cherry:
                        return "Вишнёвый";
                }
                return null;
            }

            @Override
            public Juice.Type fromString(String string) {
                return null;
            }
        });
        cmbAlcoholType.getItems().setAll(
                Alcohol.Type.vodka,
                Alcohol.Type.wine,
                Alcohol.Type.bear
        );

        cmbAlcoholType.setConverter(new StringConverter<Alcohol.Type>() {
            @Override
            public String toString(Alcohol.Type object) {
                // просто указываем как рендерить
                switch (object) {
                    case vodka:
                        return "Водка";
                    case wine:
                        return "Вино";
                    case bear:
                        return "Пиво";
                }
                return null;
            }

            @Override
            public Alcohol.Type fromString(String string) {
                return null;
            }
        });

        cmbSodaType.getItems().setAll(
                Soda.Type.Cola,
                Soda.Type.Fanta,
                Soda.Type.Sprite
        );


        cmbSodaType.setConverter(new StringConverter<Soda.Type>() {
            @Override
            public String toString(Soda.Type object) {
                // просто указываем как рендерить
                switch (object) {
                    case Cola:
                        return "Кока-Кола";
                    case Fanta:
                        return "Фанта";
                    case Sprite:
                        return "Спрайт";
                }
                return null;
            }

            @Override
            public Soda.Type fromString(String string) {
                return null;
            }
        });

        updatePanes("");

    }
    
    public void updatePanes(String value) {
        this.JuicePane.setVisible(value.equals(DRINKS_JUICE));
        this.JuicePane.setManaged(value.equals(DRINKS_JUICE));
        this.SodaPane.setVisible(value.equals(DRINKS_SODA));
        this.SodaPane.setManaged(value.equals(DRINKS_SODA));
        this.AlcoholPane.setVisible(value.equals(DRINKS_ALCOHOL));
        this.AlcoholPane.setManaged(value.equals(DRINKS_ALCOHOL));
    }




    public void onSaveClick(javafx.event.ActionEvent actionEvent) {
        if (this.id != null) {
            Drinks drinks = getDrink();
            drinks.id = this.id;
            this.drinksModel.edit(drinks);
        } else {
            this.drinksModel.add(getDrink());
        }
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void onCancelClick(javafx.event.ActionEvent actionEvent) {

        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public Drinks getDrink() {

        Drinks result = null;
        int volume = Integer.parseInt(this.txtDrinksVolume.getText());


        switch ((String)this.cmbDrinksType.getValue()) {
            case DRINKS_ALCOHOL:
                result = new Alcohol(volume,Integer.parseInt(this.txtStrength.getText()) ,(Alcohol.Type)this.cmbAlcoholType.getValue());
                break;
            case DRINKS_JUICE:
                result = new Juice(volume, this.chkHasPulp.isSelected(), (Juice.Type)this.cmbFruitType.getValue());
                break;
            case DRINKS_SODA:
                result = new Soda(volume, Integer.parseInt(this.txtBubbles.getText()) , (Soda.Type)this.cmbSodaType.getValue());
                break;
        }
        return result;
    }

    public void setDrink(Drinks drink) {

        this.cmbDrinksType.setDisable(drink != null);
        this.id = drink != null ? drink.id : null;
        if (drink != null) {

            this.txtDrinksVolume.setText(String.valueOf(drink.getVolume()));

            if (drink instanceof Alcohol) {
                this.cmbDrinksType.setValue(DRINKS_ALCOHOL);
                this.cmbAlcoholType.setValue(((Alcohol) drink).getType());
                this.txtStrength.setText(String.valueOf(((Alcohol) drink).strength));
            } else if (drink instanceof Soda) {
                this.cmbDrinksType.setValue(DRINKS_SODA);
                this.cmbSodaType.setValue(((Soda) drink).getType());
                this.txtBubbles.setText(String.valueOf(((Soda) drink).bubbles));
            } else if (drink instanceof Juice) {
                this.cmbDrinksType.setValue(DRINKS_JUICE);
                this.cmbFruitType.setValue(((Juice) drink).getType());
                this.chkHasPulp.setSelected(((Juice) drink).hasPulp);
            }
        }
}
}
