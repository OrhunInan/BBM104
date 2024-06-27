public class Main {


    /**
     * The main method that reads in a file of commands, runs the commands, and outputs the result to a file.
     *
     * @param args an array of strings representing command-line arguments.
     *             args[0]: input file name, args[1] output file name
     */
    public static void main(String[] args) {

        String[] commands = FileIO.readFile(args[0] ,true,true);//getting commands.

        SmartDeviceController myController = new SmartDeviceController();//initializing myController

        String outputText = myController.runCommands(commands);//running all commands.

        FileIO.writeToFile(args[1], outputText,false,false);//outputting into the file.
    }
}