package civ.jfx.library.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private StringProperty login = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty emailAddress = new SimpleStringProperty();
    private StringProperty hashedPassword = new SimpleStringProperty();
    private StringProperty category = new SimpleStringProperty();
    private IntegerProperty maxBooks = new SimpleIntegerProperty();
    private IntegerProperty maxDays = new SimpleIntegerProperty();

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty LastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty FirstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    public String getHashedPassword() {
        return hashedPassword.get();
    }

    public StringProperty hashedPasswordProperty() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword.set(hashedPassword);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public int getMaxBooks() {
        return maxBooks.get();
    }

    public IntegerProperty maxBooksProperty() {
        return maxBooks;
    }

    public void setMaxBooks(int maxBooks) {
        this.maxBooks.set(maxBooks);
    }

    public int getMaxDays() {
        return maxDays.get();
    }

    public IntegerProperty maxDaysProperty() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays.set(maxDays);
    }

}
