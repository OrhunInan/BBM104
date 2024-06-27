import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A class that represents a controller for the library system.
 * A controller has a book controller, a member controller, an output file name, a formatter for the date format, and a list of tickets for borrowing and reading books.
 * A controller can run multiple commands or a single command to perform various operations on the library system.
 */
public class LibraryController {

    // A controller for the books in the library
    private LibraryBookController bookController;
    // A controller for the members of the library
    private LibraryMemberController memberController;
    // The name of the output file
    private String outputFileName;
    // A formatter for the date format
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    // A list of tickets for borrowing and reading books
    protected List<BookTicket> ticketList;

    /**
     * Constructs a new controller with the given output file name
     * and initializes the book controller, member controller, and ticket list.
     * @param outputFileName a string that represents the name of the output file
     */
    public LibraryController(String outputFileName){

        this.bookController = new LibraryBookController();
        this.memberController = new LibraryMemberController();
        this.outputFileName = outputFileName;
        this.ticketList = new ArrayList<>();
    }

    /**
     * Prints the output to the output file.
     * @param output a string that represents the output to print
     */
    private void printOutput(String output){

        FileIO.writeToFile(this.outputFileName,output,true,true);
    }

    /**
     * Runs multiple commands on the library system, given an array of input strings.
     * @param input an array of strings that represent the commands to run
     */
    public void runMultipleCommands(String[] input){

        for(String line : input) runCommand(line);
    }

    /**
     * Runs a single command on the library system, given an input string.
     * The command can be one of the following: addBook, addMember, borrowBook, readInLibrary, returnBook, extendBook, getTheHistory.
     * The input string should have the correct format and parameters for each command.
     * @param input a string that represents the command to run
     */
    public void runCommand(String input){

        String[] inputFormatted = input.split("\t");

        switch (inputFormatted[0]){

            case "addBook":

                addBook(inputFormatted[1]);
                break;

            case "addMember":

                addMember(inputFormatted[1]);
                break;

            case "borrowBook":

                borrowBook(inputFormatted[1], inputFormatted[2], inputFormatted[3]);
                break;

            case "readInLibrary":

                readInLibrary(inputFormatted[1], inputFormatted[2], inputFormatted[3]);
                break;

            case "returnBook":

                returnBook(inputFormatted[1], inputFormatted[2], inputFormatted[3]);
                break;

            case "extendBook":

                extendBook(inputFormatted[1], inputFormatted[2], inputFormatted[3]);
                break;

            case "getTheHistory":

                getTheHistory();
                break;

            default:

                printOutput("Wrong command format!");
                break;
        }
    }

    /**
     * Adds a new book to the library system, either handwritten or printed, depending on the type parameter.
     * @param type a string that indicates the type of the book, "H" for handwritten, "P" for printed
     */
    public void addBook(String type){

        if( !(type.equals("H") || type.equals("P")) ) return;

        printOutput(bookController.addBook(type));
    }

    /**
     * Adds a new member to the library system, either academic or student, depending on the type parameter.
     * @param type a string that indicates the type of the member, "A" for academic, "S" for student
     */
    public void addMember(String type){

        if( !(type.equals("A") || type.equals("S")) ) return;

        printOutput(memberController.addMember(type));
    }

    /**
     * Borrows a book from the library for a member with a given book ID, member ID and borrow date.
     * Checks if the book and the member exist, if the book is available, if the member has exceeded the borrowing limit,
     * and updates the book and member status accordingly.
     * Creates a new book ticket and adds it to the ticket list.
     * @param bookID The ID of the book to be borrowed
     * @param memberID The ID of the member who borrows the book
     * @param borrowDate The date when the book is borrowed
     */
    public void borrowBook(String bookID, String memberID, String borrowDate){

        int bookIDFormatted;
        int memberIDFormatted;
        Date borrowDateFormatted;

        //exception handling.
        try {

            bookIDFormatted = Integer.parseInt(bookID);
            memberIDFormatted =  Integer.parseInt(memberID);
            borrowDateFormatted = formatter.parse(borrowDate);
        }
        catch (Exception e){return;}
        if (bookIDFormatted > bookController.getNumberOfBooks()) return;
        if (memberIDFormatted > memberController.getNumberOfMembers()) return;
        if( !bookController.getBook(bookIDFormatted).getAvailable() ){

            printOutput("You can not read this book!");
            return;
        }
        Member member = memberController.getMember(memberIDFormatted);
        if ((member instanceof AcademicMember && member.getNumberOfBooksTaken() >= 4) ||
                (member instanceof StudentMember && member.getNumberOfBooksTaken() >= 2)){

            printOutput("You have exceeded the borrowing limit!");
            return;
        }

        //execution of command.
        bookController.borrowBook(bookIDFormatted);
        printOutput(memberController.borrowBook(bookIDFormatted, memberIDFormatted,borrowDateFormatted));
        ticketList.add(new BookTicket(memberIDFormatted, bookIDFormatted, borrowDateFormatted, true));
    }

    /**
     * Reads a book in the library for a member with a given book ID, member ID and borrow date.
     * Checks if the book and the member exist, if the book is available, if the book is handwritten and the member is a student,
     * and updates the book and member status accordingly.
     * Creates a new book ticket and adds it to the ticket list.
     * @param bookID The ID of the book to be read
     * @param memberID The ID of the member who reads the book
     * @param borrowDate The date when the book is read
     */
    public void readInLibrary(String bookID, String memberID, String borrowDate){

        int bookIDFormatted;
        int memberIDFormatted;
        Date borrowDateFormatted;

        //exception handling.
        try{

            bookIDFormatted = Integer.parseInt(bookID);
            memberIDFormatted =  Integer.parseInt(memberID);
            borrowDateFormatted = formatter.parse(borrowDate);
        }catch (Exception e){return;}
        if (bookIDFormatted > bookController.getNumberOfBooks()) return;
        if (memberIDFormatted > memberController.getNumberOfMembers()) return;
        if (bookController.getBook(bookIDFormatted) instanceof HandwrittenBook &&
                memberController.getMember(memberIDFormatted) instanceof StudentMember){

            printOutput("Students can not read handwritten books!");
            return;
        }
        if( !bookController.getBook(bookIDFormatted).getAvailable() ){

            printOutput("You can not read this book!");
            return;
        }

        //command execution
        bookController.readInLibrary(bookIDFormatted);
        printOutput(memberController.readInLibrary(bookIDFormatted, memberIDFormatted, borrowDateFormatted));
        ticketList.add(new BookTicket(memberIDFormatted, bookIDFormatted, borrowDateFormatted, false));
    }

    /**
     * Returns a book to the library and prints the output.
     * @param bookID the ID of the book to be returned
     * @param memberID the ID of the member who returns the book
     * @param returnDate the date when the book is returned
     */
    public void returnBook(String bookID, String memberID, String returnDate){

        int bookIDFormatted;
        int memberIDFormatted;
        Date returnDateFormatted;

        //exception handling.
        try{

            bookIDFormatted = Integer.parseInt(bookID);
            memberIDFormatted =  Integer.parseInt(memberID);
            returnDateFormatted = formatter.parse(returnDate);
        }catch (Exception e){return;}
        if (bookIDFormatted > bookController.getNumberOfBooks()) return;
        if (memberIDFormatted > memberController.getNumberOfMembers()) return;
        if (bookController.getBook(bookIDFormatted).getAvailable())return;

        int fee = calculateFee(bookIDFormatted,memberIDFormatted, returnDateFormatted);

        for (BookTicket ticket : ticketList) if (ticket.bookId == bookIDFormatted){

            //returns book.
            if (ticket.didBorrow) printOutput(memberController.returnBorrowedBook(
                    bookIDFormatted, memberIDFormatted, returnDateFormatted, fee));
            else printOutput(memberController.returnBook(
                    bookIDFormatted, memberIDFormatted, returnDateFormatted, fee));
        }

        bookController.returnBook(bookIDFormatted);
        ticketList.removeIf(bookTicket ->
                bookTicket.bookId == bookIDFormatted && bookTicket.memberID == memberIDFormatted);
    }

    /**
     * Extends the deadline of a borrowed book and prints the output.
     * @param bookID the ID of the book to be extended
     * @param memberID the ID of the member who extends the book
     * @param extendDate the date when the book is extended
     */
    public void extendBook(String bookID, String memberID, String extendDate){

        int bookIDFormatted;
        int memberIDFormatted;
        Date extendDateFormatted;

        //exception handling.
        try{

            bookIDFormatted = Integer.parseInt(bookID);
            memberIDFormatted =  Integer.parseInt(memberID);
            extendDateFormatted = formatter.parse(extendDate);
        }catch (Exception e){return;}
        if (bookIDFormatted > bookController.getNumberOfBooks()) return;
        if (memberIDFormatted > memberController.getNumberOfMembers()) return;

        for (BookTicket ticket : ticketList){

            if (ticket.bookId == bookIDFormatted){

                long test =  ticket.borrowDate.getTime();
                test += (memberController.getMember(memberIDFormatted) instanceof AcademicMember) ?
                        1209600000 : 604800000;

                //checks if the member can extend their borrow time
                if (extendDateFormatted.after(new Date(test)) || ticket.didExtend){

                    printOutput("You cannot extend the deadline!");
                    return;
                }

                //executes command
                test += (memberController.getMember(memberIDFormatted) instanceof AcademicMember) ?
                        1209600000 : 604800000;
                ticket.didExtend = true;

                printOutput(String.format("The deadline of book [%d] was extended by member [%d] at %s",
                        bookIDFormatted, memberIDFormatted, formatter.format(extendDateFormatted)));
                printOutput(String.format("New deadline of book [%d] is %s",
                        bookIDFormatted, formatter.format(new Date(test))));
            }
        }
    }

    /**
     * Prints the history of the library, including members, books and tickets.
     */
    public void getTheHistory(){

        printOutput("History of library:");
        printOutput(memberController.getMemberList());
        printOutput(bookController.getBookshelf());
        printBooksInUse();


    }

    /**
     * Prints the books that are currently in use by members.
     */
    private void printBooksInUse(){

        String returnString = "";
        int numberOfType = 0;
        String list = "";

        //prints borrowed books.
        for (BookTicket ticket : ticketList){

            if (ticket.didBorrow){

                list += String.format("The book [%d] was borrowed by member [%d] at %s\n",
                        ticket.bookId, ticket.memberID, formatter.format(ticket.borrowDate));
                numberOfType++;
            }
        }
        returnString += String.format("Number of borrowed books: %d\n", numberOfType) + list + "\n";

        //resets values.
        numberOfType = 0;
        list = "";

        //prints books read in library.
        for (BookTicket ticket : ticketList){

            if (!ticket.didBorrow){

                list += String.format("The book [%d] was read in library by member [%d] at %s\n",
                        ticket.bookId, ticket.memberID, formatter.format(ticket.borrowDate));
                numberOfType++;
            }
        }
        returnString += String.format("Number of books read in library: %d\n", numberOfType) + list;

        printOutput(returnString.substring(0, returnString.length()-1));
    }

    /**
     * Calculates the fee for returning a book late.
     * @param bookID the ID of the book to be returned
     * @param memberID the ID of the member who returns the book
     * @param returnDate the date when the book is returned
     * @return the fee in dollars, or 0 if no fee is required
     */
    private int calculateFee(int bookID, int memberID, Date returnDate){

        long borrowDayLimit;

        borrowDayLimit = 604800000;
        borrowDayLimit *= (memberController.getMember(memberID) instanceof AcademicMember) ? 2 : 1;

        for (BookTicket ticket : ticketList){

            if (ticket.bookId == bookID) {

                if (ticket.didExtend) borrowDayLimit *= 2;
                borrowDayLimit += ticket.borrowDate.getTime();
            }
        }

        long timePast = (returnDate.getTime() - borrowDayLimit) / 86400000;
        if (timePast > 0) return (int)timePast;
        return 0;
    }
}