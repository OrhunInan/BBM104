import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
public class Main {
    public static boolean primeTest(int n) { //Prime number tester.
        if (n < 2) return false;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
    public static int reverse(int number) {// Integer reverser.
        int reverse = 0;
        while (number != 0) {
            reverse *= 10; // is ignored first iteration
            reverse += number % 10;
            number /= 10;
        }
        return reverse;
    }
    public static String[] readFile(String path) { //file reading function taken from assignment1
        try {
            int i = 0;
            int length = Files.readAllLines(Paths.get(path)).size();
            String[] results = new String[length];
            for (String line : Files.readAllLines(Paths.get(path))) {
                results[i++] = line;
            }
            return results;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String armstrong(String number){//finds armstrong numbers.
        int numberInt = Integer.parseInt(number);
        String text = "Armstrong numbers up to " + number + ":\n"; // starting text

        //for loop that finds armstrong numbers.
        for (int counter=1; counter <= numberInt; counter++){
            int testNumber = counter;
            int result = 0;
            int power = (int)(Math.log10(testNumber) + 1);
            for (int j = 0; j < power; j++){
                result += Math.pow(testNumber % 10, power);
                testNumber /= 10;
            }
            if (result == counter){
                text += counter + " ";
            }
        }
        text += "\n\n";
        return text;
    }
    public static String emirp(String number){ // emirp numbers finder
        String text = "Emirp numbers up to " + number + ":\n"; //starting text
        int numberInt = Integer.parseInt(number);
        for (int counter = 13; counter <= numberInt; counter++){ // for loop that finds emirp numbers.
            if (primeTest(counter) && primeTest(reverse(counter)) && counter != reverse(counter)) {
                text += counter + " ";
            }
        }
        text += "\n\n";
        return text;
    }
    public static String abundant(String number){
        String text = "Abundant numbers up to " + number + ":\n";// starting text
        int numberInt = Integer.parseInt(number);
        int counter, result;
        for (counter = 1; counter <= numberInt; counter++){ //for loop for finding abundent numbers
            result = 0;
            for (int j = 1; j <= counter/2; j++ ){
                if (counter%j == 0){
                    result += j;
                }
            }
            if (counter < result){
                text +=  counter + " ";
            }
        }
        text += "\n\n";
        return text;
    }
    public static String ascendingSort(int[] numbers){//ascending number sorter.
        String text = "Ascending order sorting:\n";//starting string
        int [] tempNumbers;
        for (int i = 1; i <= numbers.length; i++ ){// this loop is for writing the numbers the way output files want it
            tempNumbers = new int[i];
            for (int j = 0; j < i; j++){
                int x = numbers[j];
                tempNumbers[j] = x;
            }
            Arrays.sort(tempNumbers);
            for (int j = 0; j < i; j++){
                text += tempNumbers[j] +" ";
            }
            text += "\n";
        }
        return text + "\n";
    }
    public static String descendingSort(int[] numbers){//ascending number sorter.
        String text = "Descending order sorting:\n";//starting string
        int [] tempNumbers;
        for (int i = 1; i <= numbers.length; i++ ){// this loop is for writing the numbers the way output files want it
            tempNumbers = new int[i];
            for (int j = 0; j < i; j++){
                int x = numbers[j];
                tempNumbers[j] = x;
            }
            Arrays.sort(tempNumbers);
            for (int j = 1; j < i+1; j++){
                text += tempNumbers[tempNumbers.length-j] +" ";
            }
            text += "\n";
        }
        return text + "\n";
    }
    public static void main(String[] args) {
        try {//creating a file
            File outputFile = new File("output.txt");
            outputFile.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String[] lines = readFile(args[0]);//reading commands
        int i = 0;
        int count, counter;
        int[] numbers;// int array for sorting function
        try {
            FileWriter outputWriter = new FileWriter("output.txt");
            while (i < lines.length) {//if else tree for deciding which function to use
                if (lines[i].compareTo("Armstrong numbers up to:") == 0) {
                    outputWriter.write(armstrong(lines[++i]));
                }
                else if (lines[i].compareTo("Emirp numbers up to:") == 0) {
                    outputWriter.write(emirp(lines[++i]));
                }
                else if (lines[i].compareTo("Abundant numbers up to:") == 0) {
                    outputWriter.write(abundant(lines[++i]));
                }
                else if (lines[i].compareTo("Ascending order sorting:") == 0) {
                    count = 0;
                    while (true) {// while loop finds which numbers will be taken to sorting
                        i++;
                        if (lines[i].compareTo("-1") == 0) {
                            break;
                        } else {
                            count++;
                        }
                    }
                    numbers = new int[count];
                    for (counter = 0; counter < count; counter++) {
                        numbers[counter] = Integer.parseInt(lines[i  - count + counter]);
                    }
                    outputWriter.write(ascendingSort(numbers));
                }
                else if (lines[i].compareTo("Descending order sorting:") == 0) {
                    count = 0;
                    while (true) {// while loop finds which numbers will be taken to sorting
                        i++;
                        if (lines[i].compareTo("-1") == 0) {
                            break;
                        } else {
                            count++;
                        }
                    }
                    numbers = new int[count];
                    for (counter = 0; counter < count; counter++) {
                        numbers[counter] = Integer.parseInt(lines[i  - count + counter]);
                    }
                    outputWriter.write(descendingSort(numbers));
                }
                else {
                    outputWriter.write("Finished...");
                    break;
                }
                i++;
            }
            outputWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
