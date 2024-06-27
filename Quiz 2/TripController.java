import sun.java2d.pipe.SpanShapeRenderer;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class TripController implements DepartureController, ArrivalController{

    Comparator accordingToDeparture = new Comparator<Trip>(){
        @Override
        public int compare(Trip t1, Trip t2){
            return t1.getDepartureTime().compareTo(t2.getDepartureTime());
        }
    };

    Comparator accordingToArrival = new Comparator<Trip>(){
        @Override
        public int compare(Trip t1, Trip t2){
            return t1.getArrivalTime().compareTo(t2.getArrivalTime());
        }
    };

    protected TripSchedule tripSchedule;
    public Trip[] trips; // for later use in schedule methods.
    public TripController(String tripFile){

        this.tripSchedule = new TripSchedule();

        String[] tripEntries = FileIO.readFile(tripFile,true,true);
        String[] tempArr;
        String tripName ;
        int tripDuration;
        Date tripDepDate;

        for (String tripEntry : tripEntries){

            tempArr = tripEntry.split("\t");//temporary array first takes name \t deptime \t duration format.
            tripName = tempArr[0];
            tripDuration = Integer.parseInt(tempArr[2]);
            tempArr = tempArr[1].split(":"); //temporary array takes hour : minute format.
            tripDepDate = new Date(0,0,0,
                                   Integer.parseInt(tempArr[0]),
                                   Integer.parseInt(tempArr[1])); // trips departure date.

            this.tripSchedule.addTrip(new Trip(tripName, tripDepDate, tripDuration));
        }

        this.tripSchedule.calculateArrivalAll(); // calculates arrival time for all trips.
    }


    @Override
    public void DepartureSchedule(TripSchedule tripSchedule, String outputFile){

        int tripCount = this.tripSchedule.getTripCounter(); // how many trips there are in tripSchedule
        this.trips = this.tripSchedule.getTrips(); // copy of trip schedule
        String outputText = "Departure order:\n";
        SimpleDateFormat hourAndMinute = new SimpleDateFormat("HH:mm"); // date format for later.

        Arrays.sort(this.trips,
                    0,
                    tripCount,
                    accordingToDeparture); // sorts array

        for(int i = 0; i < tripCount - 1 ;i++ ){//checks delayed trips.
            if(this.trips[i].getDepartureTime().compareTo(this.trips[i+1].getDepartureTime()) == 0){
                this.trips[i].setState("Delayed");
                this.trips[i+1].setState("Delayed");
            }
        }

        for(int i = 0; i < tripCount; i++){//prints trips.
            outputText += trips[i].getTripName() + " depart at " + hourAndMinute.format(trips[i].getDepartureTime())
                        + "   Trip State:" + trips[i].getState() + "\n";
        }

        FileIO.writeToFile(outputFile, outputText, true,false);

    }

    @Override
    public void ArrivalSchedule(TripSchedule tripSchedule, String outputFile) {

        int tripCount = this.tripSchedule.getTripCounter(); // how many trips there are in tripSchedule
        this.trips = this.tripSchedule.getTrips();// copy of trip schedule
        String outputText = "Arrival order:\n";
        SimpleDateFormat hourAndMinute = new SimpleDateFormat("HH:mm");// date format for later.

        Arrays.sort(this.trips,
                0,
                tripCount,
                accordingToArrival); // sorts array

        for(int i = 0; i < tripCount - 1 ;i++ ){//checks delayed trips.
            if(this.trips[i].getArrivalTime().compareTo(this.trips[i+1].getArrivalTime()) == 0){
                this.trips[i].setState("Delayed");
                this.trips[i+1].setState("Delayed");
            }
        }

        for(int i = 0; i < tripCount; i++){//prints trips.
            outputText += trips[i].getTripName() + " arrive at " + hourAndMinute.format(trips[i].getArrivalTime())
                        + "   Trip State:" + trips[i].getState() + "\n";
        }

        FileIO.writeToFile(outputFile, outputText, true,false);
    }

    public TripSchedule getTripSchedule() {
        return tripSchedule;
    }
}
