package civ.jfx.library.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

import civ.jfx.library.database.UserDb;
import civ.jfx.library.model.Password;
import civ.jfx.library.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ManageUsers extends Navigate {

    public TextField userCategoryLogin;
    public TextField newCategory;
    public Button searchUser;
    public Button updateUser;
    public Button deleteUser;
    public Button searchUsers;
    public TextField nameText;
    public TextField surnameText;
    public TextField emailText;
    public TextField loginText;
    public TableView<User> UserTable;
    public TableColumn<User, String> userLogin;
    public TableColumn<User, String> userFirstName;
    public TableColumn<User, String> userLastName;
    public TableColumn<User, String> userCategory;

    public TableColumn<User, String> userEmail;
    public TableColumn<User, Integer> maxBooks;
    public TableColumn<User, Integer> maxDays;
    public TextField newUserName;
    public TextField newUserSurname;
    public TextField newUserEmail;
    public TextField newUserLogin;
    public TextField newUserPassword;

    public TextArea resultArea;

    public TextField newUserCategory;

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        /*
         * The setCellValueFactory(...) that we set on the table columns are used to
         * determine
         * which field inside the Employee objects should be used for the particular
         * column.
         * The arrow -> indicates that we're using a Java 8 feature called Lambdas.
         * (Another option would be to use a PropertyValueFactory, but this is not
         * type-safe
         * We're only using StringProperty values for our table columns in this example.
         * When you want to use IntegerProperty or DoubleProperty, the
         * setCellValueFactory(...)
         * must have an additional asObject()):
         */
        userLogin.setCellValueFactory(cellData -> cellData.getValue().loginProperty());
        userFirstName.setCellValueFactory(cellData -> cellData.getValue().FirstNameProperty());
        userLastName.setCellValueFactory(cellData -> cellData.getValue().LastNameProperty());
        userEmail.setCellValueFactory(cellData -> cellData.getValue().emailAddressProperty());
        userCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        maxBooks.setCellValueFactory(cellData -> cellData.getValue().maxBooksProperty().asObject());
        maxDays.setCellValueFactory(cellData -> cellData.getValue().maxDaysProperty().asObject());

        updateView();
    }

    public void updateView() throws SQLException, ClassNotFoundException {
        ObservableList<User> users = UserDb.searchUsers();
        UserTable.setItems(users);
    }

    public void clearFields(ActionEvent actionEvent) {
        this.resultArea.clear();
        this.newUserSurname.clear();
        this.newUserName.clear();
        this.newUserEmail.clear();
        this.newUserLogin.clear();
        this.newUserCategory.clear();
        this.newUserPassword.clear();
    }

    public void searchUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resultArea.clear();
        if (!Objects.equals(this.loginText.getText(), "") || !Objects.equals(this.nameText.getText(), "")
                || !Objects.equals(this.surnameText.getText(), "") || !Objects.equals(this.emailText.getText(), "")) {

            if (UserDb.checkUserExistence(this.loginText.getText(), this.nameText.getText(), this.surnameText.getText(),
                    this.emailText.getText())) {
                ObservableList<User> users = FXCollections.observableArrayList();
                users.add(UserDb.searchUser(this.loginText.getText(), this.nameText.getText(),
                        this.surnameText.getText(), this.emailText.getText()));
                UserTable.setItems(users);
            } else {
                this.resultArea.setText("This user is not in our data base");
            }
        } else {
            this.resultArea.setText("Operation failed because the fields are empty.");
        }

    }

    public void searchUsers(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resultArea.clear();
        ObservableList<User> users = UserDb.searchUsers();
        UserTable.setItems(users);
    }

    public void getBlackList(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        this.resultArea.clear();
        ObservableList<User> users = UserDb.getBlackList();
        if (users.isEmpty()) {
            this.resultArea.setText("There is nobody on the black list.");
        }
        UserTable.setItems(users);
    }

    public void AddUser(ActionEvent actionEvent) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        if (!Objects.equals(this.newUserLogin.getText(), "") && !Objects.equals(this.newUserName.getText(), "")
                && !Objects.equals(this.newUserSurname.getText(), "")
                && !Objects.equals(this.newUserEmail.getText(), "")
                && !Objects.equals(this.newUserPassword.getText(), "")
                && !Objects.equals(this.newUserCategory.getText(), "")) {
            boolean presentLogin = UserDb.checkUserExistence(this.newUserLogin.getText());

            if (!presentLogin) {
                UserDb.AddUser(this.newUserLogin.getText(), this.newUserName.getText(), this.newUserSurname.getText(),
                        this.newUserEmail.getText(), this.newUserPassword.getText(), this.newUserCategory.getText());
                this.resultArea.setText("The new user has been added.");
            } else {
                resultArea.setText("This login is ever used! Choose an other one.");
            }

        } else {
            resultArea.setText("adding failed! Fill correctly all the fields before trying again.");
        }
    }

    public void deleteUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resultArea.clear();
        if (!Objects.equals(this.loginText.getText(), "")) {
            if (UserDb.checkUserExistence(this.loginText.getText())) {
                UserDb.deleteUser(this.loginText.getText());
            } else {
                this.resultArea.setText("The user you look for is not in our data base");
            }
        } else {
            this.resultArea.setText("Operation failed because the user login is empty.");
        }
    }

    public void updateUserCategory(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();
        String myLogin = gateway.getUser().getLogin();

        resultArea.clear();
        if (!Objects.equals(this.userCategoryLogin.getText(), "")
                && !Objects.equals(this.newCategory.getText(), "")) {
            UserDb.updateCategory(this.userCategoryLogin.getText(), myLogin, this.newCategory.getText());
            updateView();
            this.resultArea.setText("The user category has been updated.");
        } else {
            resultArea.setText("Update failed! Fill correctly all the fields before trying again.");
        }
    }

    public void generatePassword(ActionEvent actionEvent) {
        resultArea.clear();
        this.newUserPassword.setText(Password.generateRandomPassword(10));
    }

    public void genLogin(MouseEvent mouseEvent) {
        resultArea.clear();
        if (!Objects.equals(this.newUserName.getText(), "") && !Objects.equals(this.newUserSurname.getText(), "")) {
            String login = this.newUserName.getText().substring(0, 1) + this.newUserSurname.getText()
                    + new Random().nextInt(20);
            this.newUserLogin.setText(login);
        }
    }

}
