package com.example.kr3demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AccountManager extends ListCell<String>{

    @FXML
    private Button buttonExit;

    @FXML
    Label labelFio;

    @FXML
    private ListView<ProductData> listData;

    @FXML
    private ComboBox<String> sortPrice;

    @FXML
    private ComboBox<String> sortManufacture;

    ObservableList<ProductData> productDataList;

    @FXML
    public void close() {
        Stage stage = (Stage) buttonExit.getScene().getWindow();
        stage.close();
    }

    Database database = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        database = new Database();

        buttonExit.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            close();
        });
        loadInfo();
    }

    private void filterData() {

        String selectedManufacturer = sortManufacture.getValue();
        ObservableList<ProductData> filteredData = productDataList;
        if (!selectedManufacturer.equals("Все поставщики")) {
            filteredData = productDataList.stream()
                    .filter(productData  -> productData.getManufacturer().equals(selectedManufacturer))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        listData.setItems(filteredData);
    }
    void loadInfo() throws SQLException, ClassNotFoundException {
        try {

            sortPrice.getItems().addAll("По возрастанию", "По убыванию");

            sortPrice.setOnAction(event -> {
                if (sortPrice.getValue().equals("По возрастанию")) {
                    listData.getItems().clear();
                    try {
                        List<ProductData> l = database.getProductSortASC();
                        listData.getItems().addAll(l);
                        listData.setCellFactory(stringListView -> {
                            ListCell<ProductData> cell = new Data();
                            cell.setContextMenu(null);
                            return cell;
                        });
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (sortPrice.getValue().equals("По убыванию")) {
                    listData.getItems().clear();
                    try {
                        List<ProductData> l = database.getProductSortDES();
                        listData.getItems().addAll(l);
                        listData.setCellFactory(stringListView -> {
                            ListCell<ProductData> cell = new Data();
                            cell.setContextMenu(null);
                            return cell;
                        });
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });


            List<ProductData> ls = database.getProduct();
            productDataList = FXCollections.observableArrayList(ls);
            listData.getItems().addAll(ls);
            List<String> manufacturerList = productDataList.stream()
                    .map(ProductData::getManufacturer)
                    .distinct()
                    .collect(Collectors.toList());


            sortManufacture.getItems().add("Все поставщики");
            sortManufacture.getItems().addAll(manufacturerList);
            sortManufacture.setOnAction(event -> filterData());

            listData.setCellFactory(stringListView -> {
                ListCell<ProductData> cell = new Data();
                return cell;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}