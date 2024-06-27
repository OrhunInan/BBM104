public class DeviceDoesNotExist extends Exception{

    /**
     * Returns an error message indicating that a device could not be found.
     *
     * @return an error message as a string
     */
    @Override
    public String getMessage() {return "ERROR: There is not such a device!\n";}
}
