import java.util.Date;

/**
 * This is a placeholder class for holding multiple values per object.
 * A ticket has the id of the member who borrows the book, the id of the book, the date of borrowing, and two flags that indicate if the member extended or borrowed the book.
 */
public class BookTicket {

    // The id of the member who borrows the book
    public int memberID;
    // The id of the book
    public int bookId;
    // The date of borrowing
    public Date borrowDate;
    //Indicates if the member extended the borrowing period
    public boolean didExtend;
    //Indicates if the member borrowed the book
    public boolean didBorrow;

    /**
     * Constructs a new ticket with the given parameters.
     * @param memberID an integer that represents the id of the member who borrows the book
     * @param BookId an integer that represents the id of the book
     * @param borrowDate a Date object that represents the date of borrowing
     * @param didBorrow a boolean that indicates if the member borrowed the book
     */
    public BookTicket(int memberID, int BookId, Date borrowDate, boolean didBorrow){

        this.memberID = memberID;
        this.bookId = BookId;
        this.borrowDate = borrowDate;
        this.didExtend = false;
        this.didBorrow = didBorrow;
    }
}
