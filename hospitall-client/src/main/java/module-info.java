module hospitall.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires hospitall.shared;
    requires transitive javafx.graphics;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires spring.beans;
    requires spring.context;
    requires spring.web;

    opens com.hospitall.client to javafx.fxml;
    opens com.hospitall.client.controller to javafx.fxml;
    opens com.hospitall.client.model to javafx.base;
    exports com.hospitall.client;
}
