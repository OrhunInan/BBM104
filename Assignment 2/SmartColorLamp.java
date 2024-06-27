import java.time.LocalDateTime;

public class SmartColorLamp extends SmartLamp {
    protected String hexRGBVal;
    protected boolean isHex;

    /**
     * Constructs a new SmartColorLamp which is in default white color mode.
     *
     * @param name the name of the SmartColorLamp
     * @param lastCheckedTime the last checked time of the SmartColorLamp
     */
    public SmartColorLamp(String name, LocalDateTime lastCheckedTime) {

        super(name, lastCheckedTime);
        isHex = false;
    }

    /**
     * Constructs a new SmartColorLamp which is in white color mode.
     *
     * @param name the name of the SmartColorLamp
     * @param powerStatus the power status of the SmartColorLamp
     * @param lastCheckedTime the last checked time of the SmartColorLamp
     */
    public SmartColorLamp(String name, boolean powerStatus, LocalDateTime lastCheckedTime) {

        super(name, powerStatus, lastCheckedTime);
        isHex = false;
    }

    /**
     * Constructs a new SmartColorLamp which is in white mode.
     *
     * @param name the name of the SmartColorLamp
     * @param powerStatus the power status of the SmartColorLamp
     * @param kelvin the kelvin value of the SmartColorLamp
     * @param brightness the brightness value of the SmartColorLamp
     * @param lastCheckedTime the last checked time of the SmartColorLamp
     */
    public SmartColorLamp(String name, boolean powerStatus, int kelvin, int brightness, LocalDateTime lastCheckedTime) {

        super(name, powerStatus, kelvin, brightness, lastCheckedTime);
        isHex = false;
    }

    /**
     * Constructs a new SmartColorLamp which is in color mode.
     *
     * @param name the name of the SmartColorLamp
     * @param powerStatus the power status of the SmartColorLamp
     * @param hexRGBVal the hexadecimal code of the SmartColorLamp
     * @param brightness the brightness value of the SmartColorLamp
     * @param lastCheckedTime the last checked time of the SmartColorLamp
     */
    public SmartColorLamp(String name, boolean powerStatus, String hexRGBVal,
                          int brightness, LocalDateTime lastCheckedTime) {

        super(name,powerStatus,brightness,lastCheckedTime);
        this.isHex = true;
        this.hexRGBVal = hexRGBVal;
    }

    /**
     * Sets the hexadecimal RGB value for this object.
     *
     * @param hexRGBVal a string representing the hexadecimal RGB value to set
     */
    public void setHexRGBVal(String hexRGBVal) {

        this.hexRGBVal = hexRGBVal;
        this.isHex = true;
    }

    /**
     * Sets whether this object's light is in color or white mode.
     *
     * @param hex a boolean value indicating whether the color mode is on or off
     */
    public void setHex(boolean hex) {isHex = hex;}

    /**
     * Sets the brightness level for this object.
     *
     * @param brightness an integer representing the brightness level to set
     */
    @Override
    public void setBrightness(int brightness) {
        super.setBrightness(brightness);
    }

    /**
     * Generates a z-report for the SmartColorLamp.
     *
     * @return a string containing the z-report information
     */
    @Override
    public String zReport() {

        return String.format("Smart Color Lamp %s is %s and its color value is"
                +" %s with %d%c brightness, and its time to switch its status is %s.",
                this.name,(this.powerStatus) ? "on":"off",(this.isHex) ? this.hexRGBVal:(this.kelvin +"K"),
                this.brightness, '%',(this.switchTime == null) ? "null":this.switchTime.format(formatter));
    }

}

