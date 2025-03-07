module application.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires mysql.connector.j;
    requires java.sql;

    opens application.workshopjavafxjdbc to javafx.fxml;
    opens application.workshopjavafxjdbc.gui to javafx.fxml;
    opens application.workshopjavafxjdbc.model.entities to javafx.base;
    exports application.workshopjavafxjdbc;
}