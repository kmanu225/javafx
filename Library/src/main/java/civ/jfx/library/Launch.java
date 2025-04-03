package civ.jfx.library;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launch extends Application {

    static final String BASE_PATH = "/civ/jfx/library/view";

    public static URL getResourceOrNull(String name) {
        return Launch.class.getResource(BASE_PATH + name);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("/html/LogInPage.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        Image background = new Image(
                Objects.requireNonNull(Launch.class.getResourceAsStream(BASE_PATH + "/images/logo-b.png")));
        stage.getIcons().add(background);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
