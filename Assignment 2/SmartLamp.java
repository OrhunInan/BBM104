import java.time.LocalDateTime;

public class SmartLamp extends SmartDevice{
    protected int brightness ;
    protected int kelvin ;

    /**
     * Constructs a SmartLamp object with the specified name and last checked time.
     *
     * @param name the name of the SmartLamp
     * @param lastCheckedTime the time when the SmartLamp was last checked or initiation time of SmartLamp
     */
    public SmartLamp(String name, LocalDateTime lastCheckedTime) {
        super(name, lastCheckedTime);
        this.kelvin = 4000;
        this.brightness = 100;
    }

    /**
     * Constructs a SmartLamp object with the specified name. power status and last checked time.
     *
     * @param name the name of the SmartLamp
     * @param powerStatus power state of the SmartLamp
     * @param lastCheckedTime the time when the SmartLamp was last checked or initiation time of SmartLamp
     */
    public SmartLamp(String name, boolean powerStatus, LocalDateTime lastCheckedTime) {
        super(name, powerStatus, lastCheckedTime);
        this.kelvin = 4000;
        this.brightness = 100;
    }

    /**
     * Constructs a SmartLamp object with the specified name. power status,kelvin value, brightness value
     * and last checked time.
     *
     * @param name the name of the SmartLamp
     * @param powerStatus power state of the SmartLamp
     * @param kelvin kelvin value for the SmartLamp
     * @param brightness brightness value for the SmartLamp
     * @param lastCheckedTime the time when the SmartLamp was last checked or initiation time of SmartLamp
     */
    public SmartLamp(String name, boolean powerStatus, int kelvin, int brightness, LocalDateTime lastCheckedTime) {
        super(name, powerStatus, lastCheckedTime);
        this.kelvin = kelvin;
        this.brightness = brightness;
    }

    /**
     * Constructs a SmartLamp object with the specified name. power status, brightness value and last checked time.
     * (Warning: This constructor is designed for constructing a SmartColorLamp if it's used in any other way
     * a new kelvin value must be set for utilizing most of the functionality of the SmartLamp.)
     *
     * @param name the name of the SmartLamp
     * @param powerStatus power state of the SmartLamp
     * @param brightness brightness value for the SmartLamp
     * @param lastCheckedTime the time when the SmartLamp was last checked or initiation time of SmartLamp
     */
    public SmartLamp(String name, boolean powerStatus, int brightness, LocalDateTime lastCheckedTime) {
        super(name, powerStatus, lastCheckedTime);
        this.brightness = brightness;
    }

    /**
     * Generates a z-report for the SmartLamp.
     *
     * @return a string containing the z-report information
     */
    @Override
    public String zReport() {
        return String.format("Smart Lamp %s is %s and its kelvin value is"
                        +" %dK with %d%c brightness, and its time to switch its status is %s.",
                this.name, (this.powerStatus) ? "on":"off",this.kelvin ,this.brightness, '%',
                (this.switchTime == null) ? "null":this.switchTime.format(formatter));
    }

    /**
     * Sets the brightness level for this object.
     *
     * @param brightness an integer representing the brightness level to set
     */
    public void setBrightness(int brightness) {this.brightness = brightness;}

    /**
     * Sets the Kelvin value of the SmartLamp.
     *
     * @param kelvin the Kelvin value to set
     */
    public void setKelvin(int kelvin) {this.kelvin = kelvin;}
}
