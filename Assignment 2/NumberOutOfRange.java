public class NumberOutOfRange extends Exception{

    private String reason;

    /**
     * Constructs a new NumberOutOfRange exception with the given reason.
     *
     * @param reason a string giving the problematic value which has thrown the exception
     */
    public NumberOutOfRange(String reason){

        this.reason = reason;
    }

    /**
     * Returns an error message based on the reason for the NumberOutOfRange exception.
     * The possible reasons are "ampere", "storage", "kelvin", "brightness" and "hex".
     * If the reason is unknown, returns "unknown".
     *
     * @return an error message as a string
     */
    @Override
    public String getMessage() {

        switch (this.reason){

            case "ampere":

                return "ERROR: Ampere value must be a positive number!\n";

            case "storage":

                return "ERROR: Megabyte value must be a positive number!\n";

            case "kelvin":

                return "ERROR: Kelvin value must be in range of 2000K-6500K!\n";

            case "brightness":

                return "ERROR: Brightness must be in range of 0%-100%!\n";

            case "hex":
                return "ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n";

            default:

                return "unknown\n";
        }
    }
}
