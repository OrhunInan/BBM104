/**
 * An abstract class that represents a member of a library.
 * A member can borrow and return books
 * A member has a limit on how many days they can keep a book and how many books they can borrow..
 */
abstract class Member {

    // The number of books the member has borrowed
    protected int numberOfBooksTaken;

    /**
     * Constructs a new member with no books borrowed.
     */
    public Member(){

        this.numberOfBooksTaken = 0;
    }

    /**
     * Increments the number of books the member has borrowed by one.
     */
    public void borrowBook(){

        this.numberOfBooksTaken++;
    }

    /**
     * Decrements the number of books the member has borrowed by one.
     */    public void returnBook(){

        this.numberOfBooksTaken--;
    }

    /**
     * Returns the number of books the member has borrowed.
     * @return the number of books the member has borrowed
     */
    public int getNumberOfBooksTaken(){

        return numberOfBooksTaken;
    }
}
