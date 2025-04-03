package civ.jfx.library.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

import civ.jfx.library.Launch;
import civ.jfx.library.database.DbUtils;
import civ.jfx.library.database.UserDb;
import civ.jfx.library.model.Password;
import civ.jfx.library.model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LogIn {
    @FXML
    public TextField login;

    public VBox bigVbox;

    @FXML
    public CheckBox checkManager;

    @FXML
    private Label alertUser;

    @FXML
    private PasswordField password;

    @FXML
    public boolean logIn() {
        try {
            if (DbUtils.getConn() == null) {
                this.alertUser.setText("The server is not available. Please try again later.");
                return false;
            }
            if (!Objects.equals(this.login.getText(), "") || !Objects.equals(this.password.getText(), "")) {

                String log = this.login.getText();
                String NewLog = log.replaceAll("'", "");
                User user = UserDb.searchUser(NewLog);

                if (!Objects.equals(user.getLogin(), "")) {

                    if (checkManager.isSelected() && Objects.equals(user.getCategory(), "M")) {
                        if (Objects.equals(Password.sha256(this.password.getText()), user.getHashedPassword())) {
                            this.alertUser.setText("Connected as a manager!");
                            return true;
                        } else {
                            this.alertUser.setText("User does not exists or password incorrect!");
                            return false;
                        }
                    } else if (Objects.equals(Password.sha256(this.password.getText()), user.getHashedPassword())) {
                        this.alertUser.setText("Connected as a user!");
                        return true;
                    } else {
                        this.alertUser.setText("User does not exists or password incorrect!");
                        return false;
                    }
                } else {
                    this.alertUser.setText("User does not exists or password incorrect!");
                    return false;
                }
            } else {
                this.alertUser.setText("Fill all the fields!");
                return false;
            }

        } catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
            Platform.exit();
        }
        return false;
    }

    @FXML
    public void goToUserPage(ActionEvent actionEvent) {
        try {
            if (logIn()) {
                Gateway gateway = new Gateway();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                gateway.setUser(UserDb.searchUser(login.getText()));

                if (!checkManager.isSelected()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("/html/UserDashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    stage.setUserData(gateway);
                    stage.setTitle("UserPage");
                    stage.setScene(scene);
                    stage.show();

                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(Launch.getResourceOrNull("/html/AdminDashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    stage.setUserData(gateway);
                    stage.setTitle("AdminPage");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            Platform.exit();
        }
    }
}
