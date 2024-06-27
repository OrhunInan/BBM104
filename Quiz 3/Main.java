import java.nio.file.NoSuchFileException;

public class Main {
    public static void main(String[] args) {

        String returnString ="";

        try {

            if (args.length != 1){throw new TooManyArguments();}

            String[] inputFile = FileIO.readFile(args[0], false, false);

            returnString = checkString(inputFile[0]);
        }
        catch (NoSuchFileException nsfe){ returnString = "There should be an input file in the specified path\n";}
        catch (TooManyArguments tma){ returnString = "There should be only 1 paremeter\n";}
        catch (ArrayIndexOutOfBoundsException aıoobe){returnString = "The input file should not be empty\n";}
        catch (Exception e){ System.out.println(e.toString());}
        finally { FileIO.writeToFile("../output.txt", returnString, true, false);}
    }

    /**
     *Checks if the given input string only contains letters (both lowercase and uppercase) and spaces.
     *
     *@param line the input string to be checked
     *@return a message indicating whether the program is running correctly or if the input contains unexpected characters
     */
    public static String checkString(String line){

        if (!line.matches("[ a-zA-Z]+")){return "The input file should not contains unexpected characters\n";}

        return "The program is running correctly\n";
    }
}