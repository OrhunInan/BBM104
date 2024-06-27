/**
 * An abstract class that represents a book in a library.
 * A book has two boolean attributes: available and inUseAtLibrary.
 * A book can be borrowed and returned by members of the library.
 */
abstract class Book {

    // Indicates if the book is available for borrowing
    protected boolean available;
    // Indicates if the book is currently being used at the library
    protected boolean inUseAtLibrary;

    /**
     * Constructs a new book that is available and not in use at the library.
     */
    public Book(){

        this.available = true;
        this.inUseAtLibrary = false;
    }

    /**
     * Returns the availability of the book.
     * @return true if the book is available, false otherwise
     */
    public boolean getAvailable(){

        return available;
    }

    /**
     * Sets the availability of the book to a new value.
     * @param newAvailability the new availability value
     */
    public void setAvailable(boolean newAvailability){

        this.available = newAvailability;
    }

    /**
     * Sets the use state of the book at the library to a new value.
     * @param newUseState the new use state value
     */
    public void setInUseAtLibrary(boolean newUseState){

        this.inUseAtLibrary = newUseState;
    }
}