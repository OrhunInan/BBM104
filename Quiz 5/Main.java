
public class Main {
    public static void main(String[] args) {

        FileIO.writeToFile(args[0],"", false, false);

        String[] input = FileIO.readFile(args[1],true, true);

        Quiz4Actions.runCommands(input, args[0]);
    }
}