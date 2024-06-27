import java.time.LocalDateTime;

public class SmartDeviceAdder {// a class for removing add functions from SmartDeviceController and improving readability

    /**
     * Adds a new SmartDevice object based on the given command and start time.
     * (Warning: this function or any other add function in SmartDeviceAdder doesn't check if the
     *  deviceID is already used.)
     *
     * @param command the command to execute, which contains the type of device to add
     * @param startTime the start time for the device to be added
     * @return a new SmartDevice object of the specified type
     * @throws ErroneousCommand if the command is not valid
     * @throws NumberOutOfRange if a number parameter in the command is out of range
     */
    public static SmartDevice addDevice(String[] command, LocalDateTime startTime)
            throws ErroneousCommand, NumberOutOfRange{

        switch(command[1]){//finds which device to add.

            case "SmartPlug":

                return addPlug(command, startTime);

            case "SmartCamera":

                return addCamera(command, startTime);

            case "SmartLamp":

                return addLamp(command, startTime);

            case "SmartColorLamp":

                return addColorLamp(command, startTime);

            default:

                throw new ErroneousCommand();
        }
    }

    /**
     * Creates a new SmartPlug object based on the given command and start time.
     *
     * @param command the command to execute, which contains the parameters for creating the plug
     * @param startTime the start time for the plug to be created
     * @return a new SmartPlug object with the specified parameters
     * @throws ErroneousCommand if the command is not valid
     * @throws NumberOutOfRange if a number parameter in the command is out of range
     */
    public static SmartPlug addPlug(String[] command,LocalDateTime startTime)
            throws ErroneousCommand, NumberOutOfRange{

        switch(command.length){//checks which version of the command will be used

            case 3:

                return new SmartPlug(command[2],startTime);

            case 4:

                return new SmartPlug(command[2],getPowerStatusFromCommand(command[3]),startTime);

            case 5:

                double ampere = getNumberDouble(command[4]);
                numberLessThanZero(ampere,"ampere");

                return new SmartPlug(command[2],getPowerStatusFromCommand(command[3]),ampere,startTime);

            default:

                throw new ErroneousCommand();
        }
    }

    /**
     * Creates a new SmartCamera object based on the given command and start time.
     *
     * @param command the command to execute, which contains the parameters for creating the camera
     * @param startTime the start time for the camera to be created
     * @return a new SmartCamera object with the specified parameters
     * @throws ErroneousCommand if the command is not valid
     * @throws NumberOutOfRange if a number parameter in the command is out of range
     */
    public static SmartCamera addCamera(String[] command, LocalDateTime startTime)
            throws ErroneousCommand, NumberOutOfRange{

        double storageUsageMB;

        switch(command.length){// finds which command to use.

            case 4:

                storageUsageMB = getNumberDouble(command[3]);
                numberLessThanZero(storageUsageMB,"storage");

                return new SmartCamera(command[2],storageUsageMB, startTime);

            case 5:

                storageUsageMB = getNumberDouble(command[3]);
                numberLessThanZero(storageUsageMB,"storage");

                return new SmartCamera(command[2],storageUsageMB,getPowerStatusFromCommand(command[4]), startTime);

            default:

                throw new ErroneousCommand();
        }
    }

    /**
     * Creates a new SmartLamp object based on the given command and start time.
     *
     * @param command the command to execute, which contains the parameters for creating the lamp
     * @param startTime the start time for the lamp to be created
     * @return a new SmartLamp object with the specified parameters
     * @throws ErroneousCommand if the command is not valid
     * @throws NumberOutOfRange if a number parameter in the command is out of range
     */
    public static SmartLamp addLamp(String[] command, LocalDateTime startTime)
            throws ErroneousCommand, NumberOutOfRange{

        switch(command.length){ // finds which command to use

            case 3:

                return new SmartLamp(command[2],startTime);

            case 4:

                return new SmartLamp(command[2],getPowerStatusFromCommand(command[3]),startTime);

            case 6:

                int kelvin = getNumberInt(command[4]);
                int brightness = getNumberInt(command[5]);
                numberIsNotInRange(kelvin,"kelvin");
                numberIsNotInRange(brightness,"brightness");

                return new SmartLamp(command[2], getPowerStatusFromCommand(command[3]),kelvin,brightness,startTime);

            default:

                throw new ErroneousCommand();
        }
    }

    /**
     * Creates a new SmartColorLamp object based on the given command and start time.
     *
     * @param command the command to execute, which contains the parameters for creating the lamp
     * @param startTime the start time for the lamp to be created
     * @return a new SmartColorLamp object with the specified parameters
     * @throws ErroneousCommand if the command is not valid
     * @throws NumberOutOfRange if a number parameter in the command is out of range
     */
    public static SmartColorLamp addColorLamp(String[] command, LocalDateTime startTime) throws  ErroneousCommand, NumberOutOfRange{

        switch(command.length){ // finds which command to use.

            case 3:

                return new SmartColorLamp(command[2],startTime);

            case 4:

                return new SmartColorLamp(command[2], getPowerStatusFromCommand(command[3]) ,startTime);

            case 6:

                if (valueIsHex(command[4])){// finds which mode the lamp is in

                    int brightness = getNumberInt(command[5]);
                    numberIsNotInRange(brightness,"brightness");

                    return new SmartColorLamp(command[2], getPowerStatusFromCommand(command[3]),
                                              command[4], brightness, startTime);
                }
                else{

                    int brightness = getNumberInt(command[5]);
                    int kelvin = getNumberInt(command[4]);
                    numberIsNotInRange(brightness,"brightness");
                    numberIsNotInRange(kelvin,"kelvin");

                    return new SmartColorLamp(command[2], getPowerStatusFromCommand(command[3]),
                                              kelvin, brightness, startTime);
                }

            default:

                throw new ErroneousCommand();
        }
    }

    /**
     * Determines the power status based on the given command.
     *
     * @param powerStatus the power status specified in the command
     * @return true if the power status is "On", false if the power status is "Off"
     * @throws ErroneousCommand if the power status is not "On" or "Off"
     */
    public static Boolean getPowerStatusFromCommand(String powerStatus) throws ErroneousCommand{

        switch (powerStatus){

            case "On":

                return true;

            case "Off":

                return false;

            default:

                throw new ErroneousCommand();
        }
    }

    /**
     * Checks if a number is less than or equal to zero.
     *
     * @param number the number to check
     * @param use a description of what the number is used for
     * @throws NumberOutOfRange if the number is less than or equal to zero
     */
    public static void numberLessThanZero(double number,String use) throws NumberOutOfRange{
        if(number <= 0){throw new NumberOutOfRange(use);}
    }

    /**
     * Checks if a number is within a valid range for a particular use.
     *
     * @param number the number to check
     * @param use a description of what the number is used for
     * @throws NumberOutOfRange if the number is outside the valid range for the given use
     */
    public static void numberIsNotInRange(int number ,String use) throws NumberOutOfRange{

        int lowerLimit;
        int upperLimit;

        if (use.equals("kelvin")){
            lowerLimit = 2000;
            upperLimit = 6500;
        }
        else{
            lowerLimit = 0;
            upperLimit = 100;
        }

        if (!(number >= lowerLimit && number <= upperLimit)){throw new NumberOutOfRange(use);}
    }

    /**
     * Converts a String representation of a number to an integer.
     *
     * @param number the String representation of the number to convert
     * @return the integer representation of the given number String
     * @throws ErroneousCommand if the given number String is not a valid integer
     */
    public static int getNumberInt(String number) throws ErroneousCommand{

        int numberInt;

        try { numberInt = Integer.parseInt(number); }
        catch (NumberFormatException e) { throw new ErroneousCommand(); } // changes the Exception class.

        return numberInt;
    }

    /**
     * Converts a String representation of a number to a double.
     *
     * @param number the String representation of the number to convert
     * @return the double representation of the given number String
     * @throws ErroneousCommand if the given number String is not a valid double
     */
    public static double getNumberDouble(String number) throws ErroneousCommand{

        double numberDouble;

        try { numberDouble = Double.parseDouble(number); }
        catch (NumberFormatException e) { throw new ErroneousCommand(); } // changes the Exception class.

        return numberDouble;
    }

    /**
     * Determines whether the given string value represents a hexadecimal number or an integer.
     *
     * @param value the string to be checked
     * @return true if the string represents a hexadecimal number, false if the string represents integer
     * @throws ErroneousCommand if the string is not a valid hexadecimal number and not an integer
     * @throws NumberOutOfRange if the hexadecimal code is outside the range of 0x0 to 0xFFFFFF
     */
    public static Boolean valueIsHex(String value) throws ErroneousCommand,NumberOutOfRange{

        try{Integer.parseInt(value);} // String contains int if successful
        catch (NumberFormatException e){

            if (value.toCharArray()[0] == '0' && value.toCharArray()[1] == 'x'){//tries to determine if the is value hex

                int testNumber;

                try{testNumber = Integer.parseInt(value.substring(2),16);}
                catch (NumberFormatException q){throw new ErroneousCommand();}

                if (testNumber < 0 || testNumber > 16777215){throw new NumberOutOfRange("hex");}

                return true;
            }

            throw new ErroneousCommand();
        }

        return false;
    }
}