package civ.jfx.library.controller;

import java.io.IOException;

import civ.jfx.library.Launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigate extends LogOut {

    public void goToAdminDashboard(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();
        stage.setUserData(gateway);

        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("/html/AdminDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("AdminPage");
        stage.setScene(scene);
        stage.show();

    }

    public void goToUsersPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("/html/ManageUsers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("UsersInformation");
        stage.setScene(scene);
        stage.show();

    }

    public void goToBooksPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("/html/ManageBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("BooksInformationPage");
        stage.setScene(scene);
        stage.show();

    }

}
