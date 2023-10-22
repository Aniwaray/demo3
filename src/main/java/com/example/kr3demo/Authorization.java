package com.example.kr3demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Authorization {

    @FXML
    private Button buttonEnter;

    @FXML
    private Button buttonGuest;

    @FXML
    private TextField textLogin;

    @FXML
    private TextField textPassword;

    @FXML
    private ImageView imageLogo;

    Database database = null;
    String surname, name, patronymic;

    @FXML
    void initialize() {

        database = new Database();

        Image image = new Image("C:/Users/Anna/IdeaProjects/kr3demo/logo.png");
        imageLogo.setImage(image);

        buttonEnter.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (!textLogin.getText().trim().equals("") && !textPassword.getText().trim().equals("")) {
                        int n;
                        n = database.getUsers(textLogin.getText(), textPassword.getText());
                        if (n != 0) {
                            System.out.println("Авторизация прошла успешно.");

                            if (database.getRole(textLogin.getText(), textPassword.getText()) == 1) {
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("accountAdmin.fxml"));
                                Scene scene = new Scene(fxmlLoader.load(), 771, 588);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                                AccountAdmin accountAdmin = fxmlLoader.getController();
                                name = database.getName(textLogin.getText());
                                surname = database.getSurname(textLogin.getText());
                                patronymic = database.getPatronymic(textLogin.getText());
                                accountAdmin.labelFio.setText("  " + surname + " " + name + " " + patronymic + "  ");

                            } else if (database.getRole(textLogin.getText(), textPassword.getText()) == 2) {
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("accountManager.fxml"));
                                Scene scene = new Scene(fxmlLoader.load(), 771, 588);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                                AccountManager accountManager = fxmlLoader.getController();
                                name = database.getName(textLogin.getText());
                                surname = database.getSurname(textLogin.getText());
                                patronymic = database.getPatronymic(textLogin.getText());
                                accountManager.labelFio.setText("  " + surname + " " + name + " " + patronymic + "  ");

                            } else if (database.getRole(textLogin.getText(), textPassword.getText()) == 3) {
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("accountClient.fxml"));
                                Scene scene = new Scene(fxmlLoader.load(), 771, 588);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                                AccountClient accountClient = fxmlLoader.getController();
                                name = database.getName(textLogin.getText());
                                surname = database.getSurname(textLogin.getText());
                                patronymic = database.getPatronymic(textLogin.getText());
                                accountClient.labelFio.setText("  " + surname + " " + name + " " + patronymic + "  ");

                            }
                        } else {
                            System.out.println("Произошла ошибка при входе в личный кабинет.");
                            //buttonEnter.setDisable(true);
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("captcha.fxml"));
                            Scene scene = new Scene(fxmlLoader.load(), 300, 200);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        buttonGuest.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("accountGuest.fxml"));
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
}