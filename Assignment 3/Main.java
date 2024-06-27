

public class Main {
    public static void main(String[] args) {

        String[] input = FileIO.readFile(args[0], true, true);//reads input
        LibraryController controller = new LibraryController(args[1]);//constructs library controller

        FileIO.writeToFile(args[1],"",false,false);//clears output txt file
        controller.runMultipleCommands(input);//runs commands
    }
}