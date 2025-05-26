package bookshelf.model;

public enum BookStatus {
    TO_READ("to read"),
    IN_PROGRESS("in progress"),
    FINISHED("finished"),
    UNFINISHED("unfinished");


    private final String label;
    BookStatus(String label){

        this.label = label;
    }
    @Override
    public String toString() {
        return label;
    }
}
