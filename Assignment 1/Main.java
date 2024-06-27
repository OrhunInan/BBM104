import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.Files;
public class Main {
    public static String[] readFile(String path, boolean discardEmptyLines, boolean trim) {
        /**
         * Reads the file at the given path and returns contents of it in a string array.
         *
         * @param path              Path to the file that is going to be read.
         * @param discardEmptyLines If true, discards empty lines with respect to trim; else, it takes all the lines from the file.
         * @param trim              Trim status; if true, trims (strip in Python) each line; else, it leaves each line as-is.
         * @return Contents of the file as a string array, returns null if there is not such a file or this program does not have sufficient permissions to read that file.
         */

        try {
            List<String> lines = Files.readAllLines(Paths.get(path)); //Gets the content of file to the list.
            if (discardEmptyLines) { //Removes the lines that are empty with respect to trim.
                lines.removeIf(line -> line.trim().equals(""));
            }
            if (trim) { //Trims each line.
                lines.replaceAll(String::trim);
            }
            return lines.toArray(new String[0]);
        } catch (IOException e) { //Returns null if there is no such a file.
            e.printStackTrace();
            return null;
        }
    }
    public static void writeToFile(String path, String content, boolean append, boolean newLine) {
        /**
         * This function writes given content to file at given path.
         *
         * @param path    Path for the file content is going to be written.
         * @param content Content that is going to be written to file.
         * @param append  Append status, true if wanted to append to file if it exists, false if wanted to create file from zero.
         * @param newLine True if wanted to append a new line after content, false if vice versa.
         */

        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(path, append));
            ps.print(content + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) { //Flushes all the content and closes the stream if it has been successfully created.
                ps.flush();
                ps.close();
            }
        }
    }
    public static char[][] createBoard(String path) {
        /**
         * Reads the file at the given path and returns contents of the file as a string array without whitespace characters.
         *
         * @param path  Path to the file that contains the board.
         * @return game board as char matrix.
         */

        String[] stringBoard = readFile(path,true,true); //gets board as string array.
        char[][] charBoard = new char[stringBoard.length][];
        for (int i = 0; i < stringBoard.length; i++){ //removes whitespaces from string array and assigns it to char matrix.
            charBoard[i] = stringBoard[i].replaceAll("\\s", "").toCharArray();
        }
        return charBoard;


    }
    public static char[] getCommands(String path){
        /**
         * Reads the file at the given path and returns contents of the file as a string that doesn't contain whitespace characters.
         *
         * @param path  Path to the file that contains the string.
         * @return string from the path without the whitespaces.
         */

        String[] commands = readFile(path, true, true); //gets commands as a string.
        return commands[0].replaceAll("\\s", "").toCharArray(); //removes whitespaces and returns it as a char array.
    }
    public static int[] findPosition(char[][] board, char object){
        /**
         * finds the first indicies of an object in the board.
         *
         * @param board  matrix that will be searched for object.
         * @param object char function look for.
         * @return string from the path without the whitespaces.
         */

        int[] indicies = new int[2];
        for (int i = 0; i < board.length; i++){ // nested loop searches for object.
            for (int j = 0; j < board[0].length; j++){
                if (board[i][j] == object){
                    indicies[0] = i;
                    indicies[1] = j;
                    break;
                }
            }
        }
        return indicies;
    }
    public  static String matrixToString(char[][] matrix){
        /**
         * turns a char  matrix into a printable string.
         *
         * @param matrix  matrix that will be converted.
         * @return string that icludes matrix.
         */

        String returnText = "";
        for (char[] arr:matrix) {
            for (char element:arr){
                 returnText += element + " ";
            }
            returnText += "\n";
        }
        return returnText;
    }
    public static void main(String[] args) {
        //starting definitions.
        char[][] board = createBoard(args[0]);
        char[] commands = getCommands(args[1]);
        int boardX = board[0].length; //boards horizontal length
        int boardY = board.length; // boards vertical length
        int playerPoints = 0;
        int[] playerPosition = findPosition(board, '*'); // array contains player's position as x-y pair.
        int[] swapPosition = new int[2]; // this array will be used in order to find where player will be moved.
        int temp = 0; // temporary variable used for defining swap position.

        //start of game
        String outputText = "Game board:\n" + matrixToString(board) + "\nYour movement is:\n";//prints start board.
        for (int i = 0; i < commands.length; i++) { // game running through commands.

            outputText += commands[i] + " "; //prints moves.

            //finding swap position.
            if (commands[i] == 'U'){
                temp = playerPosition[0];
                swapPosition[1] = playerPosition[1];
                swapPosition[0] = Math.floorMod((temp - 1), boardY);
            }
            else if (commands[i] == 'D'){
                temp = playerPosition[0];
                swapPosition[1] = playerPosition[1];
                swapPosition[0] = Math.floorMod((temp + 1), boardY);
            }
            else if (commands[i] == 'L'){
                temp = playerPosition[1];
                swapPosition[0] = playerPosition[0];
                swapPosition[1] = Math.floorMod((temp - 1), boardX);
            }
            else if (commands[i] == 'R'){
                temp = playerPosition[1];
                swapPosition[0] = playerPosition[0];
                swapPosition[1] = Math.floorMod((temp + 1), boardX);
            }

            //special cases where swap position have important objects.
            if (board[swapPosition[0]][swapPosition[1]] == 'W'){// player bounces from the wall.

                //calculating where the player will land.
                if (commands[i] == 'U'){
                    temp = swapPosition[0];
                    swapPosition[0] = Math.floorMod((temp + 2), boardY);
                }
                else if (commands[i] == 'D'){
                    temp = swapPosition[0];
                    swapPosition[0] = Math.floorMod((temp - 2), boardY);
                }
                else if (commands[i] == 'L'){
                    temp = swapPosition[1];
                    swapPosition[1] = Math.floorMod((temp + 2), boardX);
                }
                else if (commands[i] == 'R'){
                    temp = swapPosition[1];
                    swapPosition[1] = Math.floorMod((temp - 2), boardX);
                }
            }
            if (board[swapPosition[0]][swapPosition[1]] == 'R'){// gives 10 points.
                playerPoints += 10;
                board[swapPosition[0]][swapPosition[1]] = 'X';
            }
            if (board[swapPosition[0]][swapPosition[1]] == 'Y'){// gives 5 points.
                playerPoints += 5;
                board[swapPosition[0]][swapPosition[1]] = 'X';
            }
            if (board[swapPosition[0]][swapPosition[1]] == 'B'){// takes 5 points.
                playerPoints -= 5;
                board[swapPosition[0]][swapPosition[1]] = 'X';
            }
            if (board[swapPosition[0]][swapPosition[1]] == 'H'){// finishes the game.
                board[playerPosition[0]][playerPosition[1]] = ' ';
                break;
            }


            //swaps position of player according to command.
            board[playerPosition[0]][playerPosition[1]] = board[swapPosition[0]][swapPosition[1]];
            board[swapPosition[0]][swapPosition[1]] = '*';
            playerPosition = swapPosition.clone();
        }
        outputText += "\n\nYour output is:\n" + matrixToString(board) + "\nScore: " + playerPoints;//end of game info.
        writeToFile("output.txt",outputText, false,false); // prints game results.
    }
}