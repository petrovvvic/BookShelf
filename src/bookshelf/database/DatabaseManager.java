package bookshelf.database;

import bookshelf.model.Book;
import bookshelf.model.BookStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    //databse url
    private static final String DB_URL ="jdbc:sqlite:database.sqlite";

    //method to init the databse at the start, so it creates the table if it's not exist
    public static void init(){
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            System.out.println("✅ Connected to SQLite!");
            String sql = """
                        create table if not exists book (
                            book_id integer primary key autoincrement,
                            isbn text unique ,
                            title text NOT NULL ,
                            author text,
                            publishYear text,
                            status text
                            )
""";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to " + DB_URL, e);
        }
    }


    // returns connected database, so the operations are possible
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }

    //adding new book
    private static void insertBook(String isbn, String title, String author, String publishYear, BookStatus status) throws SQLException{
        try (Connection conn = getConnection()) {
            String sql = """
                    insert into book (isbn,title,author,publishYear,status)
                    values(?,?,?,?,?)
                    """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, isbn);
            stmt.setString(2, title);
            stmt.setString(3, author);
            stmt.setString(4, publishYear);
            stmt.setString(5, status.toString());
            stmt.executeUpdate();
        }

       catch (SQLException e) {
           System.out.println("The Transaction could not be executed");
           System.out.println("SQLException: " + e.getMessage());

       }

    }
    //deleting a book by id
    private static void deleteBook(final int BOOK_ID) throws SQLException{
        String sql = """
                    DELETE FROM book WHERE book_id=?
                    """;

        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, BOOK_ID);
            stmt.executeUpdate();

        }

        catch (SQLException e) {
            System.out.println("The Transaction could not be executed");
            System.out.println("SQLException: " + e.getMessage());
        }

    }
    //returns all the books from the database
    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(rs.getInt("book_id"));
                book.setISBN(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublishYear(rs.getString("publishYear"));
                book.setStatus(BookStatus.valueOf(rs.getString("status").toUpperCase()));

                books.add(book);

            }
            return books;
        } catch (SQLException e) {
            System.out.println("The Transaction could not be executed");
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }

    }

    // return one book
    public static Book getOneBook(final int bookId) throws SQLException {
        String sql = """
        SELECT * FROM book
        WHERE book_id = ?
    """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book(rs.getInt("book_id"));
                    book.setISBN(rs.getString("isbn"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublishYear(rs.getString("publishYear"));
                    book.setStatus(BookStatus.valueOf(rs
                            .getString("status")
                            .toUpperCase()));
                    return book;
                } else {
                   return null; // no book found with that ID
                }
            }
        }catch (SQLException e) {
            System.out.println("The Transaction could not be executed");
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }


    //updates the book fields
    public static void updateBook(Book book) throws SQLException {
        String sql = """
        UPDATE book
        SET
            isbn        = ?,
            title       = ?,
            author      = ?,
            publishYear = ?,
            status      = ?
        WHERE book_id = ?
    """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind all fields—unchanged ones simply get rebound with their old values
            stmt.setString(1, book.getISBN());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getPublishYear());
            stmt.setString(5, book.getStatus().toString());
            stmt.setInt   (6, book.getBOOK_ID());

            int updated = stmt.executeUpdate();
            System.out.println("✅ Updated rows: " + updated);
        }
    }

    public static void main(String[] args) throws SQLException {
        init();
        Book book = getOneBook(6);
        System.out.println(book.getBOOK_ID());

    }

}
