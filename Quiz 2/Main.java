public class Main {
    public static void main(String[] args) {
        FileIO.writeToFile(args[1],"",false,false); // creates a new file.
        TripController tc = new TripController(args[0]);
        tc.DepartureSchedule(tc.getTripSchedule(),args[1]);
        FileIO.writeToFile(args[1], "\n", true, false);// adds a new line.
        tc.ArrivalSchedule(tc.getTripSchedule(), args[1]);
    }
}