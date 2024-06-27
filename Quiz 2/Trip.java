import java.util.Date;

public class Trip {
    public String tripName ;
    public Date departureTime ;
    public Date arrivalTime;
    public int duration;
    public String state = "Idle";
    public Trip(String tripName, Date departureTime, int duration){
        this.tripName = tripName;
        this.departureTime = departureTime;
        this.duration = duration;
    }
    public void calculateArrival(){//calculates arrival time according to departure time and duration.
        this.arrivalTime = new Date(departureTime.getTime() + (duration * 60000L));
    }
    public Date getArrivalTime() {
        return arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTripName() {
        return tripName;
    }
}
