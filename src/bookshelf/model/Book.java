package bookshelf.model;

public class Book {
    private final int BOOK_ID;
    private String ISBN;
    private String title;
    private String author;
    private String publishYear;
    private BookStatus status;



    public Book(final int BOOK_ID) {
        this.BOOK_ID = BOOK_ID;
    }

    public Book(final int BOOK_ID,String title, String author, String publishYear, BookStatus status, String ISBN) {
        this.BOOK_ID = BOOK_ID;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.status = status;// to read, reading now, finished, not finished
        this.ISBN= ISBN;

    }



    //getters and setters

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublishYear() {
        return publishYear;
    }
    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }
    public BookStatus getStatus() {
        return status;
    }
    public void setStatus(BookStatus status) {
        this.status=status;
    }
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getBOOK_ID() {
        return BOOK_ID;
    }

}