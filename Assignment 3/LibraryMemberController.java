import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that represents a controller for the members of a library.
 * A controller has an array of members, and a number of members in the array.
 * A controller can add, borrow, read in library, and return books for the members.
 * A controller can also get the member list and a specific member by id.
 */
public class LibraryMemberController {

    // An array of members in the library
    protected Member[] memberList;
    // The number of members in the member list
    protected int numberOfMembers;
    // A formatter for the date format
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Constructs a new controller with an empty member list of size 999998.
     */
    public LibraryMemberController() {

        memberList = new Member[999998];
        numberOfMembers = 0;
    }

    /**
     * Adds a new member to the member list, either academic or student, depending on the type parameter.
     * @param type a string that indicates the type of the member, "A" for academic, "S" for student
     * @return a string that shows the details of the created member
     */
    public String addMember(String type){

        this.memberList[this.numberOfMembers++] = (type.equals("A"))? new AcademicMember(): new StudentMember();

        return String.format("Created new member: %s [id: %d]",
                (type.equals("A"))? "Academic" : "Student",this.numberOfMembers);
    }

    /**
     * Borrows a book from the library for a member by book id and member id
     * and increments the number of books the member has borrowed by one.
     * @param bookID an integer that represents the id of the book to borrow
     * @param memberID an integer that represents the id of the member who borrows the book
     * @param borrowDate a Date object that represents the date of borrowing
     * @return a string that shows the details of the borrowing transaction
     */
    public String borrowBook(int bookID, int memberID, Date borrowDate){

        memberList[memberID-1].borrowBook();

        return String.format("The book [%d] was borrowed by member [%d] at %s",
                bookID, memberID,formatter.format(borrowDate));
    }

    /**
     * Reads a book in the library for a member by book id and member id.
     * @param bookID an integer that represents the id of the book to read
     * @param memberID an integer that represents the id of the member who reads the book
     * @param borrowDate a Date object that represents the date of reading
     * @return a string that shows the details of the reading transaction
     */
    public String readInLibrary(int bookID, int memberID, Date borrowDate){

        return String.format("The book [%d] was read in library by member [%d] at %s",
                bookID, memberID,formatter.format(borrowDate));

    }

    /**
     * Returns a book to the library for a member by book id and member id
     * @param bookID an integer that represents the id of the book to return
     * @param memberID an integer that represents the id of the member who returns the book
     * @param returnDate a Date object that represents the date of returning
     * @param fee an integer that represents the fee for returning late (if any)
     * @return a string that shows the details of the returning transaction
     */
    public String returnBook(int bookID, int memberID, Date returnDate, int fee){

        return String.format("The book [%d] was returned by member [%d] at %S Fee: %d",
                bookID, memberID, formatter.format(returnDate), fee);
    }

    /**
     * Returns a borrowed book to the library for a member by book id and member id
     * and decrements the number of books the member has borrowed by one.
     * @param bookID an integer that represents the id of the book to return
     * @param memberID an integer that represents the id of the member who returns the book
     * @param returnDate a Date object that represents the date of returning
     * @param fee an integer that represents the fee for returning late (if any)
     * @return a string that shows the details of the returning transaction
     */
    public String returnBorrowedBook(int bookID, int memberID, Date returnDate, int fee){

        memberList[memberID-1].returnBook();

        return String.format("The book [%d] was returned by member [%d] at %S Fee: %d",
                bookID, memberID, formatter.format(returnDate), fee);
    }

    /**
     * Returns a string that shows the list of members in the library, including their type and id.
     * @return a string that shows the list of members in the library
     */
    public String getMemberList(){

        int numberOfType = 0;
        String list = "";
        String retunString;

        //prints Student members.
        for(int i = 0; i < this.numberOfMembers; i++){

            if(memberList[i] instanceof StudentMember){
                list += String.format("\nStudent [id: %d]",i+1);
                numberOfType++;
            }
        }
        retunString = String.format("\nNumber of students: %d", numberOfType) + list + "\n";

        //resets variables.
        numberOfType = 0;
        list = "";

        //prints Academic members.
        for(int i = 0; i < this.memberList.length; i++){

            if(memberList[i] instanceof AcademicMember){
                list += String.format("\nAcademic [id: %d]",i+1);
                numberOfType++;
            }
        }
        retunString += String.format("\nNumber of academics: %d", numberOfType) + list + "\n";
        
        return retunString;
    }

    /**
     * Returns a specific member from the member list by id.
     * @param id an integer that represents the id of the member to get
     * @return a Member object that corresponds to the id
     */
    public Member getMember(int id){

        return this.memberList[id-1];
    }

    /**
     * Returns the number of members in the member list.
     * @return an integer that represents the number of members in the member list
     */
    public int getNumberOfMembers() {

        return numberOfMembers;
    }
}
