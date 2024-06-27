/**
 * A class that represents a controller for the books in a library.
 * A controller has an array of books, and a number of books in the array.
 * A controller can add, borrow, read in library, and return books.
 * A controller can also get the bookshelf status and a specific book by id.
 */
public class LibraryBookController {

    // An array of books in the library
    protected Book[] bookshelf;
    // The number of books in the bookshelf
    protected int numberOfBooks;

    /**
     * Constructs a new controller with an empty bookshelf of size 999998.
     */
    public LibraryBookController(){

        bookshelf = new Book[999998];
        numberOfBooks = 0;
    }

    /**
     * Adds a new book to the bookshelf, either handwritten or printed, depending on the type parameter.
     * @param type a string that indicates the type of the book, "H" for handwritten, "P" for printed
     * @return a string that shows the details of the created book
     */
    public String addBook(String type){

        this.bookshelf[this.numberOfBooks++] = (type.equals("H"))? new HandwrittenBook(): new PrintedBook();

        return String.format("Created new book: %s [id: %d]",
                (type.equals("H"))? "Handwritten" : "Printed",this.numberOfBooks);
    }

    /**
     * Borrows a book from the bookshelf by id, and sets its availability to false.
     * @param id an integer that represents the id of the book to borrow
     */
    public void borrowBook(int id){

        this.bookshelf[id-1].setAvailable(false);
    }

    /**
     * Reads a book in the library by id, and sets its availability and use state to false and true respectively.
     * @param id an integer that represents the id of the book to read
     */
    public void readInLibrary(int id){

        this.bookshelf[id-1].setAvailable(false);
        this.bookshelf[id-1].setInUseAtLibrary(true);
    }

    /**
     * Returns a book to the bookshelf by id, and sets its availability and use state to true and false respectively.
     * @param id an integer that represents the id of the book to return
     */
    public void returnBook(int id){

        this.bookshelf[id-1].setAvailable(true);
        this.bookshelf[id-1].setInUseAtLibrary(false);
    }

    /**
     * Returns a string that shows the status of the bookshelf, including the number and type of books.
     * @return a string that shows the status of the bookshelf
     */
    public String getBookshelf(){

        int numberOfType = 0;
        String list = "";
        String retunString;

        //prints printed books.
        for(int i = 0; i < this.bookshelf.length; i++){

            if(bookshelf[i] instanceof PrintedBook){
                list += String.format("\nPrinted [id: %d]",i+1);
                numberOfType++;
            }
        }
        retunString = String.format("Number of printed books: %d", numberOfType) + list + "\n";

        //resets variables.
        numberOfType = 0;
        list = "";

        //prints handwritten books.
        for(int i = 0; i < this.numberOfBooks; i++){

            if(bookshelf[i] instanceof HandwrittenBook){
                list += String.format("\nHandwritten [id: %d]",i+1);
                numberOfType++;
            }
        }
        retunString += String.format("\nNumber of handwritten books: %d", numberOfType) + list + "\n";

        return retunString;
    }

    /**
     * Returns a specific book from the bookshelf by id.
     * @param id an integer that represents the id of the book to get
     * @return a Book object that corresponds to the id
     */
    public Book getBook(int id){

        return bookshelf[id-1];
    }

    /**
     * Returns the number of books in the bookshelf.
     * @return an integer that represents the number of books in the bookshelf
     */
    public int getNumberOfBooks() {

        return numberOfBooks;
    }
}