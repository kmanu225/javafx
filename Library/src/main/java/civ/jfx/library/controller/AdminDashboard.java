package civ.jfx.library.controller;

import java.io.IOException;
import java.sql.SQLException;

import civ.jfx.library.Launch;
import civ.jfx.library.database.BookDb;
import civ.jfx.library.model.Borrowings;
import civ.jfx.library.model.Item;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class AdminDashboard extends Dashboard {

    public CheckBox checkBoxLoans;
    public TableColumn<Item, Integer> tableBookIdAvailable;

    @FXML
    private void initialize() {
        tableBookTitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        tableAuthor.setCellValueFactory(cellData -> cellData.getValue().authorNameProperty());
        tableEditor.setCellValueFactory(cellData -> cellData.getValue().editorNameProperty());
        tableBookDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        tableBookIdAvailable.setCellValueFactory(cellData -> cellData.getValue().itemIdProperty().asObject());

        loanTableBookId.setCellValueFactory(cellData -> cellData.getValue().itemIdProperty().asObject());
        loanTableBookTitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        loanTableUserEmail.setCellValueFactory(cellData -> cellData.getValue().userEmailProperty());
        loanTableLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        loanTableFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        loanTableLimitDate.setCellValueFactory(cellData -> cellData.getValue().limitDateProperty());
        loanTableReturnDate.setCellValueFactory(cellData -> cellData.getValue().giveBackDateProperty());

    }

    public void goToAdminDashboard(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("/html/AdminDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("AdminPage");
        stage.setScene(scene);
        stage.show();

    }

    public void goToUsersPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("/html/ManageUsers.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    public void checkBoxAllLoansAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (this.checkBoxLoans.isSelected()) {
            checkBoxBorrowedBooks.setSelected(false);
            ObservableList<Borrowings> borrowings = BookDb.searchBorrowedBooks();
            loansTable.setItems(borrowings);
        } else {
            this.loansTable.setItems(null);
        }
    }

}
