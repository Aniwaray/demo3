package com.example.kr3demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("authorization.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 409, 459);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    //7efwqtub80i5@outlook.com 2L6KZG
    //v4mlcsi1bgnk@tutanota.com YOyhfR
    //p7rviz42j6bh@outlook.com  LdNyos
}