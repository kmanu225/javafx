package civ.jfx.library.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static civ.jfx.library.database.DbUtils.getConn;
import civ.jfx.library.model.Borrowings;
import civ.jfx.library.model.Item;
import civ.jfx.library.model.UserCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookDb {

    public static boolean checkAuthorExistence(String authorName, String AuthorBirthDate)
            throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(1) FROM Author WHERE authorName = ? AND BirthDate = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, authorName);
            stmt.setInt(2, Integer.parseInt(AuthorBirthDate)); // Assuming AuthorBirthDate is an integer, like a year

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        }
        return false;
    }

    public static void AddAuthor(String Name, String BirthYear) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Author (authorName, birthdate) VALUES (?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, Name);
            stmt.setInt(2, Integer.parseInt(BirthYear)); // Assuming BirthYear is an integer

            stmt.executeUpdate();
        }
    }

    public static int getAuthorId(String Name, String BirthYear) throws SQLException, ClassNotFoundException {
        String query = "SELECT Id FROM Author WHERE authorName = ? AND BirthDate = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, Name);
            stmt.setInt(2, Integer.parseInt(BirthYear)); // Assuming BirthYear is an integer

            ResultSet rs = stmt.executeQuery();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("Id");
            }
            return id;
        }
    }

    public static boolean checkBookExistence(String title, String firstEdition)
            throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(1) FROM Book WHERE Title = ? AND firstYearEdition = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, title);
            stmt.setString(2, firstEdition);

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && !title.isEmpty() && !firstEdition.isEmpty()) {
                return rs.getInt(1) == 1;
            }
        }
        return false;
    }

    public static void AddBook(String title, String firstEdition, String description)
            throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Book (Title, firstYearEdition, description) VALUES (?, ?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, title);
            stmt.setString(2, firstEdition);
            stmt.setString(3, description);

            stmt.executeUpdate();
        }
    }

    public static boolean checkEditorExistence(String editorISBN) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(1) FROM Editor WHERE ISBN = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, editorISBN);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        }
        return false;
    }

    public static void AddEditor(String editorName, String editorISBN) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Editor (editorName, ISBN) VALUES (?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, editorName);
            stmt.setString(2, editorISBN);

            stmt.executeUpdate();
        }
    }

    public static boolean checkHasWrittenExistence(String booktTitle, String bookFirstYearEdition, int AuthorId)
            throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(1) FROM HasWritten WHERE booktTitle = ? AND bookFirstYearEdition = ? AND AuthorId = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, booktTitle);
            stmt.setString(2, bookFirstYearEdition);
            stmt.setInt(3, AuthorId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        }
        return false;
    }

    public static void updateHasWritten(String booktTitle, String bookFirstYearEdition, int AuthorId)
            throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO HasWritten (booktTitle, bookFirstYearEdition, AuthorId) VALUES (?, ?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, booktTitle);
            stmt.setString(2, bookFirstYearEdition);
            stmt.setInt(3, AuthorId);

            stmt.executeUpdate();
        }
    }

    public static void AddItem(Integer itemId, String booktTitle, String bookFirstYearEdition, String editorISBN)
            throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Item (itemId, booktTitle, bookFirstYearEdition, editorISBN) VALUES (?, ?, ?, ?)";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            stmt.setString(2, booktTitle);
            stmt.setString(3, bookFirstYearEdition);
            stmt.setString(4, editorISBN);

            stmt.executeUpdate();
        }
    }

    public static Item searchBook(String bookTitle, String bookAuthor, String bookEditor)
            throws SQLException, ClassNotFoundException {

        String sql = """
                SELECT Item.booktTitle, authorName, editorName, description, itemId
                FROM Item
                JOIN Book ON Book.Title = Item.booktTitle AND Book.firstYearEdition = Item.bookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.booktTitle AND Book.firstYearEdition = HasWritten.bookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                JOIN Editor ON Editor.ISBN = Item.editorISBN
                WHERE 1=1
                """;

        // Dynamically add conditions based on provided parameters
        if (!Objects.equals(bookTitle, "")) {
            sql += " AND Item.booktTitle = ?";
        }
        if (!Objects.equals(bookAuthor, "")) {
            sql += " AND Author.authorName = ?";
        }
        if (!Objects.equals(bookEditor, "")) {
            sql += " AND Editor.editorName = ?";
        }
        sql += " ORDER BY booktTitle DESC";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            int parameterIndex = 1;

            if (!Objects.equals(bookTitle, "")) {
                stmt.setString(parameterIndex++, bookTitle);
            }
            if (!Objects.equals(bookAuthor, "")) {
                stmt.setString(parameterIndex++, bookAuthor);
            }
            if (!Objects.equals(bookEditor, "")) {
                stmt.setString(parameterIndex++, bookEditor);
            }

            ResultSet rs = stmt.executeQuery();
            return Observe0(rs);
        }
    }

    private static Item Observe0(ResultSet rs) throws SQLException {
        Item item = new Item();
        while (rs.next()) {
            item.setBookTitle(rs.getString("booktTitle"));
            item.setAuthorName(rs.getString("authorName"));
            item.setEditorName(rs.getString("editorName"));
            item.setDescription(rs.getString("description"));
            item.setItemId(rs.getInt("itemId"));
        }
        return item;
    }

    public static boolean checkBookExistence(String bookTitle, String bookAuthor, String bookEditor)
            throws SQLException, ClassNotFoundException {

        String query1 = "SELECT COUNT(1) FROM Item WHERE Item.booktTitle = ?";
        String query2 = """
                SELECT COUNT(1) FROM Item
                JOIN Book ON Book.Title = Item.booktTitle AND Book.firstYearEdition = Item.bookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.booktTitle AND Book.firstYearEdition = HasWritten.bookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                WHERE Author.authorName = ?""";
        String query3 = """
                SELECT COUNT(1) FROM Item
                JOIN Book ON Book.Title = Item.booktTitle AND Book.firstYearEdition = Item.bookFirstYearEdition
                JOIN Editor ON Editor.ISBN = Item.editorISBN
                WHERE Editor.editorName = ?""";

        try (Connection con = getConn()) {

            // Query 1: Check if bookTitle exists
            if (!Objects.equals(bookTitle, "")) {
                try (PreparedStatement stmt1 = con.prepareStatement(query1)) {
                    stmt1.setString(1, bookTitle);
                    ResultSet rs1 = stmt1.executeQuery();
                    if (rs1.next()) {
                        return rs1.getInt(1) == 1;
                    }
                }
            }

            // Query 2: Check if bookAuthor exists
            if (!Objects.equals(bookAuthor, "")) {
                try (PreparedStatement stmt2 = con.prepareStatement(query2)) {
                    stmt2.setString(1, bookAuthor);
                    ResultSet rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        return rs2.getInt(1) == 1;
                    }
                }
            }

            // Query 3: Check if bookEditor exists
            if (!Objects.equals(bookEditor, "")) {
                try (PreparedStatement stmt3 = con.prepareStatement(query3)) {
                    stmt3.setString(1, bookEditor);
                    ResultSet rs3 = stmt3.executeQuery();
                    if (rs3.next()) {
                        return rs3.getInt(1) == 1;
                    }
                }
            }

            return false;
        }
    }

    public static Item searchBook1(String bookTitle, String bookAuthor, String bookEditor)
            throws SQLException, ClassNotFoundException {

        String query = """
                SELECT Item.booktTitle, authorName, editorName, description
                FROM Item
                JOIN Book ON Book.Title = Item.booktTitle AND Book.firstYearEdition = Item.bookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.booktTitle AND Book.firstYearEdition = HasWritten.bookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                JOIN Editor ON Editor.ISBN = Item.editorISBN
                WHERE 1=1
                """;

        if (!Objects.equals(bookTitle, "")) {
            query += " AND Item.booktTitle = ?";
        }
        if (!Objects.equals(bookAuthor, "")) {
            query += " AND Author.authorName = ?";
        }
        if (!Objects.equals(bookEditor, "")) {
            query += " AND Editor.editorName = ?";
        }
        query += " ORDER BY booktTitle DESC";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            int parameterIndex = 1;

            if (!Objects.equals(bookTitle, "")) {
                stmt.setString(parameterIndex++, bookTitle);
            }
            if (!Objects.equals(bookAuthor, "")) {
                stmt.setString(parameterIndex++, bookAuthor);
            }
            if (!Objects.equals(bookEditor, "")) {
                stmt.setString(parameterIndex++, bookEditor);
            }

            ResultSet rs = stmt.executeQuery();
            return Observe1(rs);
        }
    }

    private static Item Observe1(ResultSet rs) throws SQLException {
        Item item = new Item();
        while (rs.next()) {
            item.setBookTitle(rs.getString("booktTitle"));
            item.setAuthorName(rs.getString("authorName"));
            item.setEditorName(rs.getString("editorName"));
            item.setDescription(rs.getString("description"));
        }
        return item;
    }

    public static ObservableList<Item> searchAvailableBooks() throws SQLException, ClassNotFoundException {
        String query = """
                SELECT Item.booktTitle, authorName, editorName, description, itemId
                FROM Item
                JOIN Book ON Book.Title = Item.booktTitle AND Book.firstYearEdition = Item.bookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.booktTitle AND Book.firstYearEdition = HasWritten.bookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                JOIN Editor ON Editor.ISBN = Item.editorISBN
                ORDER BY booktTitle DESC
                """;

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            return getAvailableBooks(rs);
        }
    }

    private static ObservableList<Item> getAvailableBooks(ResultSet rs) throws SQLException {
        ObservableList<Item> bookItems = FXCollections.observableArrayList();

        while (rs.next()) {
            Item item = new Item();
            item.setBookTitle(rs.getString("booktTitle"));
            item.setAuthorName(rs.getString("authorName"));
            item.setEditorName(rs.getString("editorName"));
            item.setDescription(rs.getString("description"));
            item.setItemId(rs.getInt("itemId"));
            bookItems.add(item);
        }
        return bookItems;
    }

    public static ObservableList<Item> searchAvailableBooks2() throws SQLException, ClassNotFoundException {
        String query = """
                SELECT DISTINCT Item.booktTitle, authorName, editorName, description
                FROM Item
                JOIN Book ON Book.Title = Item.booktTitle AND Book.firstYearEdition = Item.bookFirstYearEdition
                JOIN HasWritten ON Book.Title = HasWritten.booktTitle AND Book.firstYearEdition = HasWritten.bookFirstYearEdition
                JOIN Author ON Author.Id = HasWritten.AuthorId
                JOIN Editor ON Editor.ISBN = Item.editorISBN
                ORDER BY booktTitle DESC
                """;

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            System.out.println(resultSetToJson(rs));

            return getAvailableBooks2(rs);
        }
    }

    /// Convert ResultSet to JSON string
    private static String resultSetToJson(ResultSet rs) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        while (rs.next()) {
            ObjectNode bookNode = mapper.createObjectNode();
            bookNode.put("bookTitle", rs.getString("booktTitle"));
            bookNode.put("authorName", rs.getString("authorName"));
            bookNode.put("editorName", rs.getString("editorName"));
            bookNode.put("description", rs.getString("description"));

            arrayNode.add(bookNode);
        }

        return arrayNode.toString(); //
    }

    ///
    

    private static ObservableList<Item> getAvailableBooks2(ResultSet rs) throws SQLException {
        ObservableList<Item> bookItems = FXCollections.observableArrayList();

        while (rs.next()) {
            Item item = new Item();
            item.setBookTitle(rs.getString("booktTitle"));
            item.setAuthorName(rs.getString("authorName"));
            item.setEditorName(rs.getString("editorName"));
            item.setDescription(rs.getString("description"));
            bookItems.add(item);
        }
        return bookItems;
    }

    public static ObservableList<Borrowings> searchBorrowedBooks() throws SQLException, ClassNotFoundException {
        String query = """
                SELECT * FROM User
                JOIN Borrowings ON User.login = Borrowings.borrowerLogin
                JOIN Item ON Item.itemId = Borrowings.itemId
                WHERE Borrowings.giveBackDate IS NULL
                """;

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            return Observe2(rs);
        }
    }

    public static ObservableList<Borrowings> searchBorrowedBooksByMe(String myLogin)
            throws SQLException, ClassNotFoundException {
        String query = """
                SELECT * FROM User
                JOIN Borrowings ON User.login = Borrowings.borrowerLogin
                JOIN Item ON Item.itemId = Borrowings.itemId
                WHERE Borrowings.borrowerLogin = ?
                """;

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, myLogin);

            try (ResultSet rs = stmt.executeQuery()) {
                return Observe2(rs);
            }
        }
    }

    private static ObservableList<Borrowings> Observe2(ResultSet rs) throws SQLException {
        ObservableList<Borrowings> borrowingss = FXCollections.observableArrayList();

        while (rs.next()) {
            Borrowings borrowings = new Borrowings();
            borrowings.setItemId(rs.getInt("itemId"));
            borrowings.setBookTitle(rs.getString("booktTitle"));
            borrowings.setUserEmail(rs.getString("emailAddress"));
            borrowings.setLastName(rs.getString("lastName"));
            borrowings.setFirstName(rs.getString("firstName"));
            borrowings.setLimitDate(String.valueOf(rs.getDate("limitDate")));
            borrowings.setGiveBackDate(String.valueOf(rs.getDate("giveBackDate")));

            borrowingss.add(borrowings);
        }
        return borrowingss;
    }

    public static boolean checkItemExistence(Integer itemId) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(itemId) FROM Item WHERE itemId = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, itemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) != 0;
                }
            }
        }
        return false;
    }

    public static boolean checkForLend(Integer itemId) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(itemId) FROM Borrowings WHERE itemId = ? AND giveBackDate IS NULL";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, itemId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) == 0;
            }
        }
    }

    public static void updateBorrowedBooks(Integer itemId, String borrowerLogin)
            throws SQLException, ClassNotFoundException {
        String category = null;
        String queryCategory = "SELECT category FROM User WHERE login = ?";
        try (Connection con = getConn();
                PreparedStatement stmtCategory = con.prepareStatement(queryCategory)) {

            stmtCategory.setString(1, borrowerLogin);

            try (ResultSet rs2 = stmtCategory.executeQuery()) {
                if (rs2.next()) {
                    category = rs2.getString(1);
                }
            }
        }

        Integer maxBooks = new UserCategory().getMaxBooks(category);
        Integer maxDays = new UserCategory().getMaxDays(category);

        int everBorrowed = 0;
        String queryCountBorrowed = "SELECT COUNT(borrowerLogin) FROM Borrowings WHERE borrowerLogin = ?";
        try (Connection con = getConn();
                PreparedStatement stmtCount = con.prepareStatement(queryCountBorrowed)) {

            stmtCount.setString(1, borrowerLogin);

            try (ResultSet rs = stmtCount.executeQuery()) {
                if (rs.next()) {
                    everBorrowed = rs.getInt(1);
                }
            }
        }

        if (maxBooks > everBorrowed) {
            LocalDate borrowingDate = LocalDate.now();
            LocalDate limitDate = borrowingDate.plusDays(maxDays);

            String queryInsert = "INSERT INTO Borrowings (itemId, borrowerLogin, borrowingDate, limitDate) VALUES (?, ?, ?, ?)";
            try (Connection con = getConn();
                    PreparedStatement stmtInsert = con.prepareStatement(queryInsert)) {

                stmtInsert.setInt(1, itemId);
                stmtInsert.setString(2, borrowerLogin);
                stmtInsert.setDate(3, Date.valueOf(borrowingDate));
                stmtInsert.setDate(4, Date.valueOf(limitDate));

                stmtInsert.executeUpdate();
            }
        }
    }

    public static void updateReturnDate(Integer itemId, String borrowerLogin, LocalDate giveBackDate)
            throws SQLException, ClassNotFoundException {
        String query = "UPDATE Borrowings SET giveBackDate = ? WHERE itemId = ? AND borrowerLogin = ?";

        try (Connection con = getConn();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(giveBackDate));
            stmt.setInt(2, itemId);
            stmt.setString(3, borrowerLogin);

            stmt.executeUpdate();
        }
    }

}
