module com.example.kr3demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kr3demo to javafx.fxml;
    exports com.example.kr3demo;
}