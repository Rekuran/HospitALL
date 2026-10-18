module hospitall.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires hospitall.shared;
    requires transitive javafx.graphics;

    opens com.hospitall.client to javafx.fxml;
    exports com.hospitall.client;
}
