public class WrongDeviceType extends Exception{
    private String type ;

    /**
     * Constructs a new WrongDeviceType exception with the specified device type message.
     *
     * @param type string that describes the right device type
     */
    public WrongDeviceType(String type){this.type = type;}

    /**
     * Returns the error message that describes the wrong device type.
     *
     * @return the error message that describes the wrong device type
     */
    @Override
    public String getMessage() {return String.format("ERROR: This device is not a %s!\n",type);}
}
