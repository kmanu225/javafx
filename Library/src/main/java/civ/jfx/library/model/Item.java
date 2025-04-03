package civ.jfx.library.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item extends Book {

    private IntegerProperty itemId = new SimpleIntegerProperty();
    private StringProperty editorName = new SimpleStringProperty();

    private User borrower = new User();

    public int getItemId() {
        return itemId.get();
    }

    public IntegerProperty itemIdProperty() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId.set(itemId);
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public String getEditorName() {
        return editorName.get();
    }

    public StringProperty editorNameProperty() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName.set(editorName);
    }

}
