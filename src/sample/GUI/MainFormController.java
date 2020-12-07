package sample.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.util.StringConverter;
import sample.Models.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public TableView mainTable;
    public ComboBox cmbDrinkType;

    DrinksModel drinksModel = new DrinksModel();

    ObservableList<Class<? extends Drinks>> drinksTypes = FXCollections.observableArrayList(
            Drinks.class,
            Alcohol.class,
            Juice.class,
            Soda.class
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        drinksModel.addDataChangedListener(foods -> { mainTable.setItems(FXCollections.observableArrayList(foods)); });


        TableColumn<Drinks, String> volumeColumn = new TableColumn<>("Объем /мл.");

        volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));

        TableColumn<Drinks, String> TypeColumn = new TableColumn<>("Тип");
        TypeColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDrinkType());
        });


        TableColumn<Drinks, String> descriptionColumn = new TableColumn<>("Описание");

        descriptionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });


        mainTable.getColumns().addAll(volumeColumn, TypeColumn,descriptionColumn);


        cmbDrinkType.setItems(drinksTypes);

        cmbDrinkType.getSelectionModel().select(0);


        cmbDrinkType.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {

                if (Drinks.class.equals(object)) {
                    return "Все";
                } else if (Soda.class.equals(object)) {
                    return "Газировка";
                } else if (Alcohol.class.equals(object)) {
                    return "Алкоголь";
                } else if (Juice.class.equals(object)) {
                    return "Сок";
                }
                return null;
            }

            @Override
            public Class fromString(String string) {
                return null;
            }
        });

        cmbDrinkType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.drinksModel.setDrinksFilter((Class<? extends Drinks>) newValue);
        });

    }


    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DrinksForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(this.mainTable.getScene().getWindow());

        DrinksFormController controller = loader.getController();

        controller.drinksModel = drinksModel;

        stage.showAndWait();
    }

    public void onEditClick(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DrinksForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());

        DrinksFormController controller = loader.getController();
        controller.setDrink((Drinks) this.mainTable.getSelectionModel().getSelectedItem());
        controller.drinksModel = drinksModel;

        stage.showAndWait();

    }

    public void onDeleteClick(ActionEvent actionEvent) {
        Drinks d = (Drinks) this.mainTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(String.format("Вы точно хотите удалить эту строчку?"));

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            drinksModel.delete(d.id);
        }
    }

    public void onSaveToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные");
        fileChooser.setInitialDirectory(new File("."));


        File file = fileChooser.showSaveDialog(mainTable.getScene().getWindow());

        if (file != null) {
            drinksModel.saveToFile(file.getPath());
        }

    }

    public void onLoadFromFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить данные");
        fileChooser.setInitialDirectory(new File("."));


        File file = fileChooser.showOpenDialog(mainTable.getScene().getWindow());

        if (file != null) {
            drinksModel.loadFromFile(file.getPath());
        }
    }
}
