public class CanNotReverseTime extends Exception{

    /**
     * Returns an error message indicating that time cannot be reversed.
     *
     * @return an error message as a string
     */
    @Override
    public String getMessage() {return "ERROR: Time cannot be reversed!\n";}
}
