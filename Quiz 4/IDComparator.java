import java.util.Comparator;

public class IDComparator implements Comparator<String> {
    public int compare(String lineA,String lineB){

        int idA = Integer.parseInt(lineA.split("\t")[0]);
        int idB = Integer.parseInt(lineB.split("\t")[0]);

        return idA - idB;
    }
}
