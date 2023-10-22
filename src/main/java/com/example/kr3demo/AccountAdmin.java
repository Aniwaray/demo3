package com.example.kr3demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class AccountAdmin extends ListCell<String> {


    @FXML
    private Button buttonExit, buttonAdd;

    @FXML
    Label labelFio;

    @FXML
    private ListView<ProductData> listData;

    @FXML
    private ComboBox<String> sortPrice, sortManufacture;

    ObservableList<ProductData> productDataList;

    @FXML
    private TextField textSearch;
    ObservableList<ProductData> dataList;

    @FXML
    public void close() {
        Stage stage = (Stage) buttonExit.getScene().getWindow();
        stage.close();
    }
    public void openContextMenu(){
            listData.setCellFactory(stringListView -> {
                ListCell<ProductData> cell = new Data();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem itemEdit = new MenuItem("Редактировать");
                MenuItem itemDelete = new MenuItem("Удалить");

                itemEdit.setOnAction(event -> {
                    ProductData item = cell.getItem();
                    //System.out.println(item.getName());
                });
                contextMenu.getItems().addAll(itemEdit);

                itemDelete.setOnAction(event -> {
                    ProductData item = cell.getItem();
                    //System.out.println(item.getName());
                });
                contextMenu.getItems().addAll(itemDelete);
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });
                return cell;
            });
    }

    Database database = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        database = new Database();

        buttonExit.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            close();
        });
        loadInfo();

        buttonAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("productAdd.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 771, 588);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
                    openContextMenu();
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
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    openContextMenu();
                }
            });

            List<ProductData> ls = database.getProduct();
            productDataList = FXCollections.observableArrayList(ls);
            listData.getItems().addAll(ls);
           openContextMenu();

            List<String> manufacturerList = productDataList.stream()
                    .map(ProductData::getManufacturer)
                    .distinct()
                    .collect(Collectors.toList());


            sortManufacture.getItems().add("Все поставщики");
            sortManufacture.getItems().addAll(manufacturerList);
            sortManufacture.setOnAction(event -> filterData());

            openContextMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}