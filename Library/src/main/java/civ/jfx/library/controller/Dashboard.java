package civ.jfx.library.controller;

import java.sql.SQLException;
import java.util.Objects;

import civ.jfx.library.database.BookDb;
import civ.jfx.library.model.Borrowings;
import civ.jfx.library.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Dashboard extends LogOut{
    public TextField booktTitle;
    public TextField bookEditor;
    public TextField bookAuthor;
    public TextField bookId;
    public Label resultArea;
    public CheckBox checkBoxAvailableBooks;
    public CheckBox checkBoxBorrowedBooks;

    public TableView<Item> booksTable;
    public TableColumn<Item, String> tableBookTitle;
    public TableColumn<Item, String> tableAuthor;
    public TableColumn<Item, String> tableEditor;
    public TableColumn<Item, String> tableBookDescription;

    public TableView<Borrowings> loansTable;
    public TableColumn<Borrowings, Integer> loanTableBookId;
    public TableColumn<Borrowings, String> loanTableBookTitle;
    public TableColumn<Borrowings, String> loanTableUserEmail;
    public TableColumn<Borrowings, String> loanTableFirstName;
    public TableColumn<Borrowings, String> loanTableLastName;
    public TableColumn<Borrowings, String> loanTableLimitDate;
    public TableColumn<Borrowings, String> loanTableReturnDate;
    public Label myName;
    public Label mMySurname;
    public Label myCategory;
    public Label login;



    public void searchBook(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!Objects.equals(this.booktTitle.getText(), "") || !Objects.equals(this.bookAuthor.getText(), "")
                || !Objects.equals(this.bookEditor.getText(), "")) {

            if (BookDb.checkBookExistence(this.booktTitle.getText(), this.bookAuthor.getText(),
                    this.bookEditor.getText())) {
                ObservableList<Item> bookItems = FXCollections.observableArrayList();
                bookItems.add(BookDb.searchBook1(this.booktTitle.getText(), this.bookAuthor.getText(),
                        this.bookEditor.getText()));
                booksTable.setItems(bookItems );
            } else {
                this.resultArea.setText("Book not found!");
            }
        } else {
            this.resultArea.setText("Fill the fields!");
        }
    }

    public void checkAvailableBooks(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (this.checkBoxAvailableBooks.isSelected()) {
            this.booksTable.setItems(null);
            ObservableList<Item> bookItems = BookDb.searchAvailableBooks2();
            booksTable.setItems(bookItems);
        } else {
            this.booksTable.setItems(null);
        }
    }

    public void checkBoxSeeMyBooks(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();
        if (this.checkBoxBorrowedBooks.isSelected()) {
            ObservableList<Borrowings> borrowings = BookDb.searchBorrowedBooksByMe(gateway.getUser().getLogin());
            loansTable.setItems(borrowings);
        } else {
            this.loansTable.setItems(null);
        }
    }

    public void showProfile(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Gateway gateway = (Gateway) stage.getUserData();

        login.setText(gateway.getUser().getLogin());
        myName.setText("Surname: " + gateway.getUser().getFirstName());
        mMySurname.setText("Name: " + gateway.getUser().getLastName());
        myCategory.setText("category: " + gateway.getUser().getCategory());
    }

    public void clearFields(ActionEvent actionEvent) {
        this.booktTitle.clear();
        this.bookEditor.clear();
        this.bookAuthor.clear();
        this.bookId.clear();
        this.resultArea.setText("");
    }
}
