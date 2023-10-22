package com.example.kr3demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Data extends ListCell<ProductData> {

    @FXML
    ImageView imageView;

    @FXML
    static Label labelCount, labelDescription, labelManufacturer, labelName, labelPrice;

    @FXML
    AnchorPane anchorPane;

    @FXML
    HBox hBox;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(ProductData productData, boolean empty) {
        super.updateItem(productData, empty);

        if (empty || productData == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("data.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

             //Установка серого фона ячеек для товаров с отсутствующим статусом
//            String status = productData.getStatus();
//            if (status != null && status.equals("Отсутствуют")) {
//                anchorPane.setStyle("-fx-background-color: gray;");
//            } else {
//                anchorPane.setStyle(""); // Сброс стиля ячейки
//            }

            labelName.setText(productData.getName());
            File file = new File(productData.getPhoto());
            try {
                String urlImage = file.toURI().toURL().toString();
                Image image = new Image(urlImage);
                imageView.setImage(image);
            } catch (MalformedURLException ignored) {
            }
            setText(null);
            setGraphic(anchorPane);
            labelDescription.setText(productData.getDescription());
            labelManufacturer.setText(productData.getManufacturer());
            labelPrice.setText(String.valueOf(productData.getCost()));
            labelCount.setText(String.valueOf(productData.getStock()));
        }

    }
}