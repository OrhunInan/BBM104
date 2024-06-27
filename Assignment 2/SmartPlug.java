import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SmartPlug extends SmartDevice{

    protected final int voltage = 220;
    protected double ampere;
    protected double usedPower = 0;

    /**
     * Constructs a SmartPlug object with the specified name and last checked time.
     *
     * @param name the name of the SmartPlug
     * @param lastCheckedTime the time when the SmartPlug was last checked or initiation time of SmartPlug
     */
    public SmartPlug(String name, LocalDateTime lastCheckedTime){
        super(name,lastCheckedTime);
    }

    /**
     * Constructs a SmartPlug object with the specified name, power status and last checked time.
     *
     * @param name the name of the SmartPlug
     * @param powerStatus power state of the SmartPlug
     * @param lastCheckedTime the time when the SmartPlug was last checked or initiation time of SmartPlug
     */
    public SmartPlug(String name,boolean powerStatus, LocalDateTime lastCheckedTime){
        super(name,powerStatus,lastCheckedTime);
    }

    /**
     * Constructs a SmartPlug object with the specified name, power status and last checked time.
     *
     * @param name the name of the SmartPlug
     * @param powerStatus power state of the SmartPlug
     * @param ampere ampere usage of the device that is plugged in to the SmartPlug
     * @param lastCheckedTime the time when the SmartPlug was last checked or initiation time of SmartPlug
     */
    public SmartPlug(String name,boolean powerStatus,double ampere, LocalDateTime lastCheckedTime){
        super(name,powerStatus,lastCheckedTime);
        this.ampere = ampere;
    }

    /**
     * Generates a z-report for the SmartPlug.
     *
     * @return a string containing the z-report information
     */
    @Override
    public String zReport() {
        return String.format("Smart Plug %s is %s and consumed %.2fW so"
                        +" far (excluding current device), and its time to switch its status is %s.",
                this.name,(this.powerStatus) ? "on":"off", usedPower, (this.switchTime == null) ? "null":this.switchTime.format(formatter));
    }

    /**
     * Sets the power status of the SmartPlug and updates the last checked time.
     * If the power status is turned off, calculates the power usage using the  new time.
     *
     * @param powerStatus the new power status of the SmartPlug
     * @param newTime the new time when the power status was updated
     */
    @Override
    public void setPowerStatus(boolean powerStatus, LocalDateTime newTime) {
        super.setPowerStatus(powerStatus,newTime);
        if(!powerStatus) {calculatePowerUsage(newTime);}
    }

    /**
     * Sets the ampere value of the SmartPlug.
     *
     * @param ampere the ampere value to set
     */
    public void setAmpere(double ampere) {
        this.ampere = ampere;}

    /**
     * Unplugs the SmartPlug and calculates the power usage until the specified plug out time.
     *
     * @param plugOutTime the time when the SmartPlug was unplugged
     */
    public void plugOut(LocalDateTime plugOutTime) {

        if(powerStatus){calculatePowerUsage(plugOutTime);}
        this.ampere = 0.0;
    }

    /**
     * Returns the ampere value of the SmartPlug.
     *
     * @return the ampere value of the SmartPlug
     */
    public double getAmpere() {return ampere;}

    /**
     * Calculates the power usage of the SmartPlug since the last checked time until the specified finish time,
     * and updates the used power value.
     *
     * @param finishTime the time until which the power usage is calculated
     */
    public void calculatePowerUsage(LocalDateTime finishTime){

        long pastTime = this.lastCheckedTime.until(finishTime, ChronoUnit.MINUTES);

        usedPower += (pastTime*ampere*voltage)/60;
    }
}