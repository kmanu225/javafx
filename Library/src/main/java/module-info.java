module civ {

    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    
    requires transitive java.sql;
    requires java.sql.rowset;

    opens civ.jfx.library to javafx.fxml;
    opens civ.jfx.library.controller to javafx.fxml;


    exports civ.jfx.library;
}
