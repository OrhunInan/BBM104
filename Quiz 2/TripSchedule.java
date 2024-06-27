public class TripSchedule{
    public Trip[] trips = new Trip[100];
    public int tripCounter = 0; //for the cases where there are less than 100 trips.
    public void addTrip(Trip trip){
        trips[tripCounter++] = trip;
    }
    public void calculateArrivalAll(){ // calculates arrival for all trips.
        for (int i = 0; i < tripCounter; i++){
            trips[i].calculateArrival();
        }
    }

    public int getTripCounter() {
        return tripCounter;
    }

    public Trip[] getTrips() {
        return trips;
    }
}
