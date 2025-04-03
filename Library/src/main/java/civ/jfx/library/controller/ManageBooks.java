package civ.jfx.library.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import civ.jfx.library.database.BookDb;
import civ.jfx.library.database.UserDb;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ManageBooks extends Navigate {

    public TextField bookToAddTitle;
    public TextField bookToAddFirstEdition;
    public TextField bookToAddDescription;
    public TextField bookToAddId;
    public TextField bookToAddAuthor;
    public TextField bookToAddBirthYear;
    public TextField bookToAddEditor;
    public TextField bookToAddEditionYear;
    public TextField bookToLendId;
    public TextField bookToLendBorrowerLogin;
    public TextField bookToAddEditorISBN;
    public Label resultArea;
    public Label resultArea2;
    public Label resultArea1;
    public TextField bookToReturnId;
    public TextField bookToReturnBorrowerLogin;
    public TextField giveBackDate;

    public void lend(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        this.resultArea.setText("");
        if (!Objects.equals(bookToLendId.getText(), "") && !Objects.equals(bookToLendBorrowerLogin.getText(), "")) {
            if (BookDb.checkItemExistence(Integer.valueOf(bookToLendId.getText()))) {
                if (BookDb.checkForLend(Integer.valueOf(bookToLendId.getText()))) {
                    if (UserDb.checkUserExistence(bookToLendBorrowerLogin.getText())) {
                        BookDb.updateBorrowedBooks(Integer.valueOf(bookToLendId.getText()),
                                bookToLendBorrowerLogin.getText());
                        this.resultArea1.setText("Operation succeeded!");
                    } else {
                        this.resultArea1.setText("The user does not exists in our database.");
                    }
                } else {
                    this.resultArea1.setText("This book has not yet been returned in the library.");
                }
            } else {
                this.resultArea1.setText("This book doesn't exits in our database.");
            }
        } else {
            this.resultArea1.setText("Fill all the fields please.");
        }
    }

    public void add(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (!Objects.equals(this.bookToAddTitle.getText(), "")
                    && !Objects.equals(this.bookToAddFirstEdition.getText(), "")) {
                if (!BookDb.checkBookExistence(this.bookToAddTitle.getText(), this.bookToAddFirstEdition.getText())) {
                    BookDb.AddBook(this.bookToAddTitle.getText(), this.bookToAddFirstEdition.getText(),
                            this.bookToAddDescription.getText());
                    this.resultArea.setText("The book has been successfully added to the database!");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            this.resultArea.setText("Edition year should be an integer!");
        }

        try {
            if (!Objects.equals(this.bookToAddAuthor.getText(), "")
                    && !Objects.equals(this.bookToAddBirthYear.getText(), "")) {
                if (!BookDb.checkAuthorExistence(this.bookToAddAuthor.getText(), this.bookToAddBirthYear.getText())) {
                    BookDb.AddAuthor(this.bookToAddAuthor.getText(), this.bookToAddBirthYear.getText());
                    this.resultArea.setText("The author have been successfully added in the database!");
                }
            } else {
                boolean a = Objects.equals(this.bookToAddAuthor.getText(), "");
                boolean b = Objects.equals(this.bookToAddBirthYear.getText(), "");
                if (a && !b || !a && b) {
                    this.resultArea.setText("Fill author information!");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            Platform.exit();
        }

        try {
            if (!Objects.equals(this.bookToAddAuthor.getText(), "")
                    && !Objects.equals(this.bookToAddBirthYear.getText(), "")
                    && !Objects.equals(this.bookToAddTitle.getText(), "")
                    && !Objects.equals(this.bookToAddFirstEdition.getText(), "")) {
                int id = BookDb.getAuthorId(this.bookToAddAuthor.getText(), this.bookToAddBirthYear.getText());

                if (!BookDb.checkHasWrittenExistence(this.bookToAddTitle.getText(),
                        this.bookToAddFirstEdition.getText(), id)
                        && BookDb.checkAuthorExistence(this.bookToAddAuthor.getText(),
                                this.bookToAddBirthYear.getText())
                        && BookDb.checkBookExistence(this.bookToAddTitle.getText(),
                                this.bookToAddFirstEdition.getText())) {
                    BookDb.updateHasWritten(this.bookToAddTitle.getText(), this.bookToAddFirstEdition.getText(), id);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            Platform.exit();
        }

        if (!Objects.equals(this.bookToAddEditorISBN.getText(), "")) {
            if (!BookDb.checkEditorExistence(this.bookToAddEditorISBN.getText())
                    && !Objects.equals(this.bookToAddEditorISBN.getText(), "")
                    && !Objects.equals(this.bookToAddEditionYear.getText(), "")
                    && !Objects.equals(this.bookToAddEditor.getText(), "")) {
                BookDb.AddEditor(this.bookToAddEditor.getText(), this.bookToAddEditorISBN.getText());
                this.resultArea.setText("The editor have been successfully added!");
            }

        }

        if (!Objects.equals(this.bookToAddEditorISBN.getText(), "")
                && !Objects.equals(this.bookToAddEditionYear.getText(), "")
                && !Objects.equals(this.bookToAddEditor.getText(), "")
                && !Objects.equals(this.bookToAddTitle.getText(), "")
                && !Objects.equals(this.bookToAddFirstEdition.getText(), "")) {
            if (BookDb.checkEditorExistence(this.bookToAddEditorISBN.getText())
                    && BookDb.checkBookExistence(this.bookToAddTitle.getText(), this.bookToAddFirstEdition.getText())) {
                BookDb.AddItem(Integer.valueOf(this.bookToAddId.getText()), this.bookToAddTitle.getText(),
                        this.bookToAddFirstEdition.getText(), this.bookToAddEditorISBN.getText());
            }
        }

        this.resultArea.setText("Operation succeeded!");

        boolean a = Objects.equals(this.bookToAddEditorISBN.getText(), "");
        boolean b = Objects.equals(this.bookToAddEditionYear.getText(), "");
        boolean c = Objects.equals(this.bookToAddEditor.getText(), "");

        boolean d = Objects.equals(this.bookToAddTitle.getText(), "");
        boolean e = Objects.equals(this.bookToAddFirstEdition.getText(), "");
        boolean f = Objects.equals(this.bookToAddDescription.getText(), "");

        if (a && b && !c || a && !b && c || !a && b && c) {
            this.resultArea.setText("Fill editor information!");
        }

        if ((d || e) && !f || !e && f || !d && e) {
            this.resultArea.setText("Fill book information!");
        }

        if (a && b && c && d && e && f) {
            this.resultArea.setText("Fill the fields!");
        }

    }

    public void validateReturn(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        this.resultArea.setText("");
        if (!Objects.equals(bookToReturnId.getText(), "") && !Objects.equals(bookToReturnBorrowerLogin.getText(), "")
                && !Objects.equals(giveBackDate.getText(), "")) {
            if (BookDb.checkItemExistence(Integer.valueOf(bookToReturnId.getText()))) {
                if (UserDb.checkUserExistence(bookToReturnBorrowerLogin.getText())) {
                    BookDb.updateReturnDate(Integer.valueOf(bookToReturnId.getText()),
                            bookToReturnBorrowerLogin.getText(), LocalDate.parse(giveBackDate.getText()));
                    this.resultArea2.setText("Operation succeeded!");
                } else {
                    this.resultArea2.setText("The user does not exists in the database.");
                }
            } else {
                this.resultArea2.setText("This book doesn't exits in the database.");
            }
        } else {
            this.resultArea2.setText("Fill all the fields please.");
        }
    }
}
