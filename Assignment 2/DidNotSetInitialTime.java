public class DidNotSetInitialTime extends Exception{

    /**
     * Returns an error message indicating that the first command must be to set the initial time,
     * and that the program will terminate.
     *
     * @return an error message as a string
     */
    @Override
    public String getMessage() {

        return "ERROR: First command must be set initial time! Program is going to terminate!\n";
    }
}
