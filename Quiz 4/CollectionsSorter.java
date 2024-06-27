import java.util.*;

public class CollectionsSorter {
    public static void printList(String[] textWithID){

        FileIO.writeToFile("poemArrayList.txt","",false,false);
        List<String> textWithIDList = Arrays.asList(textWithID);

        for(String line : textWithIDList){
            FileIO.writeToFile("poemArrayList.txt",(line + "\n"),true,false);
        }
    }

    public static void printSortedList(String[] textWithID){

        FileIO.writeToFile("poemArrayListOrderByID.txt","",false,false);
        List<String> textWithIDList = Arrays.asList(textWithID);
        textWithIDList.sort(new IDComparator());

        for(String line : textWithID){
            FileIO.writeToFile("poemArrayListOrderByID.txt",(line + "\n"),true,false);
        }
    }

    public static void printHashMap(String[] textWithID){

        FileIO.writeToFile("poemHashMap.txt","",false,false);
        HashMap<Integer, String> textWithIDHashMap = new HashMap<>();
        int index;

        for(String line : textWithID){
            index = Integer.parseInt(line.split("\t")[0]);
            textWithIDHashMap.put(index, line);
        }
        for(int i = 1; i < textWithIDHashMap.size() + 1;i++){
            FileIO.writeToFile("poemHashMap.txt",(textWithIDHashMap.get(i) + "\n"),true,false);
        }
    }

    public static void printHashSet(String[] textWithID){

        FileIO.writeToFile("poemHashSet.txt","",false,false);
        HashSet<String> textWithIDHashSet = new HashSet<>(Arrays.asList(textWithID));

        for(String line : textWithIDHashSet){
            FileIO.writeToFile("poemHashSet.txt",(line + "\n"),true,false);
        }
    }
    
    public static void printTreeSet(String[] textWithID){

        FileIO.writeToFile("poemTreeSet.txt","",false,false);
        TreeSet<String > textWithIDTreeSet = new TreeSet<>(Arrays.asList(textWithID));

        for(String line : textWithIDTreeSet){
            FileIO.writeToFile("poemTreeSet.txt",(line + "\n"),true,false);
        }
    }

    public static void printSortedTreeSet(String[] textWithID){

        FileIO.writeToFile("poemTreeSetOrderByID.txt","",false,false);
        TreeSet<String > textWithIDSortedTreeSet = new TreeSet<>(new IDComparator());

        textWithIDSortedTreeSet.addAll(Arrays.asList(textWithID));
        for(String line : textWithIDSortedTreeSet){
            FileIO.writeToFile("poemTreeSetOrderByID.txt",(line + "\n"),true,false);
        }
    }
}
