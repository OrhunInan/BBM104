import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class SmartDevice implements Comparable<SmartDevice>{
    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    protected String name;
    protected boolean powerStatus;
    protected LocalDateTime lastCheckedTime;
    protected LocalDateTime switchTime;

    /**
     * Constructs a new SmartDevice object with the specified name and last checked time.
     *
     * @param name the name of the SmartDevice
     * @param lastCheckedTime the date and time when the SmartDevice was last checked
     */
    SmartDevice(String name, LocalDateTime lastCheckedTime){
        this.name = name;
        this.powerStatus = false;
        this.lastCheckedTime = lastCheckedTime;
    }

    /**
     * Constructs a new SmartDevice object with the specified name, power status, and last checked time.
     *
     * @param name the name of the SmartDevice
     * @param powerStatus the power status of the SmartDevice (true if on, false if off)
     * @param lastCheckedTime the date and time when the SmartDevice was last checked
     */
    SmartDevice(String name, boolean powerStatus, LocalDateTime lastCheckedTime){
        this.name = name;
        this.powerStatus = powerStatus;
        this.lastCheckedTime = lastCheckedTime;
    }

    /**
     * Sets the switch time of this object to the specified LocalDateTime.
     *
     * @param switchTime the new switch time of this object
     */
    public void setSwitchTime(LocalDateTime switchTime) {this.switchTime = switchTime;}

    /**
     * Returns the name of the object.
     *
     * @return the name of the object
     */
    public String getName() {return name;}

    /**
     * Generates a z-report for the SmartDevice.
     *
     * @return a string containing the z-report information
     */
    abstract String zReport();

    /**
     * Sets the power status of this object to the specified boolean value.
     * newTime object is used at subclasses of Smart Device.
     *
     * @param powerStatus the new power status of this object
     * @param newTime the new time associated with the updated power status
     */
    public void setPowerStatus(boolean powerStatus,LocalDateTime newTime) {this.powerStatus = powerStatus;}

    /**
     * Returns the date and time when this object was last checked or when this object was initialized.
     *
     * @return the date and time when this object was last checked or when this object was initialized
     */
    public LocalDateTime getLastCheckedTime() {return lastCheckedTime;}

    /**
     * Returns the switch time of the object.
     *
     * @return the switch of the object
     */
    public LocalDateTime getSwitchTime() {return switchTime;}

    /**
     * Sets the name of the SmartDevice device to the specified String.
     *
     * @param name the new name of the SmartDevice
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets the last checked time of this SmartDevice to the specified LocalDateTime.
     *
     * @param lastCheckedTime the new last checked time of this object
     */
    public void setLastCheckedTime(LocalDateTime lastCheckedTime) {this.lastCheckedTime = lastCheckedTime;}

    /**
     * Returns the current power status of the SmartDevice.
     *
     * @return the current power status of the SmartDevice as a boolean value (true for "on" and false for "off")
     */
    public boolean getPowerStatus(){return this.powerStatus;}

    /**
     * Compares this SmartDevice object with the specified SmartDevice object based on their switch times.
     *
     * @param device the SmartDevice object to compare with
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
     */
    public int compareTo(SmartDevice device){

        if(this.switchTime == null && device.getSwitchTime() == null){return 0;}
        if(this.switchTime == null){return 1;}
        if(device.getSwitchTime() == null){return -1;}
        if(this.switchTime.isAfter(device.getSwitchTime())){return 1;}
        if(this.switchTime.isBefore(device.getSwitchTime())){return -1;}
        if(this.switchTime.isEqual(device.getSwitchTime())){return 0;}

        return 0;
    }
}
