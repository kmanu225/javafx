package civ.jfx.library.controller;

import java.io.IOException;

import civ.jfx.library.Launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserDashboard extends Dashboard {

    @FXML
    private void initialize() {

        // books in the library
        tableBookTitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        tableAuthor.setCellValueFactory(cellData -> cellData.getValue().authorNameProperty());
        tableEditor.setCellValueFactory(cellData -> cellData.getValue().editorNameProperty());
        tableBookDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        // books borrowed
        loanTableBookId.setCellValueFactory(cellData -> cellData.getValue().itemIdProperty().asObject());
        loanTableBookTitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        loanTableUserEmail.setCellValueFactory(cellData -> cellData.getValue().userEmailProperty());
        loanTableLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        loanTableFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        loanTableLimitDate.setCellValueFactory(cellData -> cellData.getValue().limitDateProperty());
        loanTableReturnDate.setCellValueFactory(cellData -> cellData.getValue().giveBackDateProperty());

    }

    public void goToUserDashboard(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("/html/UserDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("UserPage");
        stage.setScene(scene);
        stage.show();

    }

}
