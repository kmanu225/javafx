package civ.jfx.library.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Borrowings {
    private IntegerProperty itemId = new SimpleIntegerProperty();
    private StringProperty bookTitle = new SimpleStringProperty();
    private StringProperty userLogin = new SimpleStringProperty();
    private StringProperty userEmail = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty limitDate = new SimpleStringProperty();
    private StringProperty giveBackDate = new SimpleStringProperty();

    public String getBookTitle() {
        return bookTitle.get();
    }

    public StringProperty bookTitleProperty() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.set(bookTitle);
    }

    public String getUserLogin() {
        return userLogin.get();
    }

    public StringProperty userLoginProperty() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin.set(userLogin);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLimitDate() {
        return limitDate.get();
    }

    public StringProperty limitDateProperty() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate.set(limitDate);
    }

    public int getItemId() {
        return itemId.get();
    }

    public IntegerProperty itemIdProperty() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId.set(itemId);
    }

    public String getGiveBackDate() {
        return giveBackDate.get();
    }

    public StringProperty giveBackDateProperty() {
        return giveBackDate;
    }

    public void setGiveBackDate(String giveBackDate) {
        this.giveBackDate.set(giveBackDate);
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public StringProperty userEmailProperty() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail.set(userEmail);
    }
}
