module com.kursovaya {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.kursovaya to javafx.fxml;
    opens com.kursovaya.fxml_controllers to javafx.fxml;
    opens com.kursovaya.tables to javafx.base;
    exports com.kursovaya;
    exports com.kursovaya.tables;
}
