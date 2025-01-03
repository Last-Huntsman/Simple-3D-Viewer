module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;
    requires org.testng;
    requires org.junit.jupiter.api;


    opens com.cgvsu to javafx.fxml;
    exports com.cgvsu;
    exports com.cgvsu.gui;
    opens com.cgvsu.gui to javafx.fxml;
}