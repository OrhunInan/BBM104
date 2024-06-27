public class ErroneousCommand extends Exception{

    /**
     * Returns an error message indicating that the command given is erroneous.
     *
     * @return an error message as a string
     */
    @Override
    public String getMessage() {return "ERROR: Erroneous command!\n";}
}
