module com.gokugame.gokugame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens com.gokugame.gokugame to javafx.fxml;
    exports com.gokugame.gokugame;
}