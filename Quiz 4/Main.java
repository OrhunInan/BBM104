

public class Main {

    public static void main(String[] args) {

        String[] poem = FileIO.readFile(args[0],true,false);

        CollectionsSorter.printList(poem);
        CollectionsSorter.printSortedList(poem);
        CollectionsSorter.printHashSet(poem);
        CollectionsSorter.printTreeSet(poem);
        CollectionsSorter.printSortedTreeSet(poem);
        CollectionsSorter.printHashMap(poem);
    }
}