package com.example.kr3demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class ProductAdd {

    @FXML
    private Button buttonAdd;

    @FXML
    private ComboBox<String> comboCategory;

    @FXML
    private TextField textCost;

    @FXML
    private TextField textDescription;

    @FXML
    private TextField textManufacture;

    @FXML
    private TextField textName;

    @FXML
    private TextField textStock;

    Database database = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        // Инициируем объект
        database = new Database();
        database.maxProduct();
        List<String> tasks = database.getCategory(); // Получаем список пунктов из базы данных
        if (!tasks.isEmpty()) {
            ObservableList<String> items = FXCollections.observableArrayList(tasks);
            comboCategory.setItems(items);// Добавляем все пункты в выпадающий список
        }
        buttonAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (!textCost.getText().isEmpty() && !textDescription.getText().isEmpty() && !textManufacture.getText().isEmpty() && !textName.getText().isEmpty() && !textStock.getText().isEmpty() && comboCategory.getValue() != null) {
                        database.productAdd(Integer.parseInt(textCost.getText()), textDescription.getText(), textManufacture.getText(), textName.getText(), Integer.parseInt(textStock.getText()), comboCategory.getValue());
                        System.out.println("Данные добавлены.");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}