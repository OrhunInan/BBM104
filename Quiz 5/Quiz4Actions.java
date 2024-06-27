public class Quiz4Actions {

    /**
     * Runs an array of commands and writes the results to a file.
     *
     * @param commandLines an array of commands to be executed
     * @param path the path of the file to write the results to
     */
    public static void runCommands(String[] commandLines, String path){

        String[] commandSplit;

        for (String command : commandLines){

            commandSplit = command.split("\t");
            runCommand(commandSplit, path);
        }
    }

    /**
     * Runs a single command and writes the result to a file.
     *
     * @param splitCommand an array of strings representing the command and its arguments
     * @param path the path of the file to write the result to
     */
    public static void runCommand(String[] splitCommand, String path){

        switch (splitCommand[0]){

            case "Convert from Base 10 to Base 2:":

                decimalToBinary(Integer.parseInt(splitCommand[1]), path);
                break;

            case "Count from 1 up to n in binary:":

                binaryCount(Integer.parseInt(splitCommand[1]), path);
                break;

            case "Check if following is palindrome or not:":

                boolean isPal;
                isPal = isPalindrome(splitCommand[1].replaceAll("[\\p{Punct}\\s]", "").toUpperCase());

                FileIO.writeToFile(path, String.format("\"%s\" is %sa palindrome.", splitCommand[1],
                                isPal ? "" : "not "), true, true);
                break;

            case "Check if following expression is valid or not:":

                boolean isVal = isPalindrome(splitCommand[1].replaceAll("[^()\\[\\]{}]", "")
                        .replaceAll("}", "{").replaceAll("\\)", "(")
                        .replaceAll("]","["));

                FileIO.writeToFile(path, String.format("\"%s\" is %sa valid expression.", splitCommand[1],
                        isVal ? "" : "not "), true, true);
                break;



        }
    }

    /**
     * Converts a decimal number to a binary number and writes it to a file.
     *
     * @param number the decimal number to be converted
     * @param path the path of the file to write the binary number to
     */
    public static void decimalToBinary(int number, String path){

        FileIO.writeToFile(path,String.format("Equivalent of %d (base 10) in base 2 is: ", number),
                true, false);

        Stack<Integer> stack = new Stack<>();

        while (number != 0){

            stack.add(number % 2);
            number /= 2;
        }

        while (stack.read() != null){

            FileIO.writeToFile(path, stack.pop().toString(), true, false);
        }

        FileIO.writeToFile(path,"", true, true);
    }

    /**
     * Counts from 1 up to n in binary and writes it to a file.
     *
     * @param n the upper limit of the counting
     * @param path the path of the file to write the binary numbers to
     */
    public static void binaryCount(int n, String path){

        FileIO.writeToFile(path,String.format("Counting from 1 up to %d in binary:", n),
                true, false);

        Stack<Integer> stack;
        int number;

        for (int i = 1; i <= n; i++){

            stack = new Stack<>();
            number = i;

            FileIO.writeToFile(path,"\t", true, false);



            while (number != 0){

                stack.add(number % 2);
                number /= 2;
            }

            while (stack.read() != null){

                FileIO.writeToFile(path, stack.pop().toString(), true, false);
            }
        }

        FileIO.writeToFile(path,"", true, true);
    }

    /**
     * Checks if a given text is a palindrome or not.
     * A palindrome is a word or phrase that is the same forward and backward,
     * ignoring punctuation and case.
     *
     * @param text the text to be checked
     * @return true if the text is a palindrome, false otherwise
     */
    public static boolean isPalindrome(String text){

        Stack<Character> stack = new Stack<>();

        if(text.length() % 2 == 1) {

            text = text.substring(0,text.length()/2) + text.substring(text.length()/2 +1, text.length());
        }

        for(char c : text.toCharArray()){

            stack.add(c);
        }

        return isPalindromeRecursion(stack, stack.length());
    }

    /**
     * A helper method that uses recursion to check if a stack of characters is a palindrome or not.
     *
     * @param stack the stack of characters to be checked
     * @param length the original length of the stack
     * @return true if the stack is a palindrome, false otherwise
     */
    private static boolean isPalindromeRecursion(Stack<Character> stack, int length){

        if(stack.length() == length/2 + 1){

            char a = stack.pop();
            char b = stack.pop();

            return a == b;
        }
        else{

            char upper = stack.pop();

            if(isPalindromeRecursion(stack,length)){


                return upper == stack.pop();
            }

            else return false;
        }
    }
}
