
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SmartDeviceController {

    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    protected LocalDateTime time;
    protected List<SmartDevice> deviceHub;
    protected boolean didSetInitialTime;
    private boolean lastCommandIsZReport;

    /**
     * Constructs a new SmartDeviceController.
     */
    public SmartDeviceController(){
        this.deviceHub = new ArrayList<>();
    }

    /**
     * Executes a list of commands and returns the result as a string.
     *
     * @param commands an array of strings representing the commands to be executed
     * @return a string containing the results of executing the commands
     */
    public String runCommands(String[] commands){

        String returnString = "";

        for(int i = 0; i < commands.length; i++){

            lastCommandIsZReport = false;

            returnString += "COMMAND: " + commands[i] + "\n";//prints command.

            try {

                String[] command = commands[i].split("\t"); //Splits command into arguments.

                switch (command[0]){//Looks at the first argument in the command.

                    case "SetInitialTime":

                        returnString += setInitialTime(command);
                        break;

                    case "SetTime":

                        returnString += setTime(command);
                        break;

                    case "SkipMinutes":

                        returnString += skipMinutes(command);
                        break;

                    case "Nop":

                        returnString += nop(command);
                        break;

                    case "Add":

                        returnString += add(command);
                        break;

                    case "Remove":

                        returnString += remove(command);
                        break;

                    case "SetSwitchTime":

                        returnString += setSwitchTime(command);
                        break;

                    case "Switch":

                        returnString += switchPowerStatus(command);
                        break;

                    case "ChangeName":

                        returnString += changeName(command);
                        break;

                    case "PlugIn":

                        returnString += plugIn(command);
                        break;

                    case "PlugOut":

                        returnString += plugOut(command);
                        break;

                    case "SetKelvin":

                        returnString += setKelvin(command);
                        break;

                    case "SetBrightness":

                        returnString += setBrightness(command);
                        break;

                    case "SetColorCode":

                        returnString += setColorCode(command);
                        break;

                    case "SetWhite":

                        returnString += setWhite(command);
                        break;

                    case "SetColor":

                        returnString += setColor(command);
                        break;

                    case "ZReport":

                        returnString += zReport(command);
                        lastCommandIsZReport = true;
                        break;

                    default:

                        throw new ErroneousCommand();
                }

                if(returnString.contains("Program is going to terminate!")){break;}//self termination of the program
            }
            catch(Exception e){returnString += e.getMessage();}
        }

        if (!returnString.contains("Program is going to terminate!") && !lastCommandIsZReport) {//Prints last zReport.

            String[] endCommand = "ZReport".split("\t");
            returnString +=  "ZReport:\n" + zReport(endCommand);
        }

        return returnString;
    }

    /**
     *Sets the initial time of the program.
     *
     *@param command an array of Strings containing the command and its arguments
     *@return a String indicating if the operation was successful or if there was an error
     */
    public String setInitialTime(String[] command){

        try{

            //Error handling.
            checkLength(command.length,2);
            if(didSetInitialTime){throw new  ErroneousCommand();}

            try{this.time = LocalDateTime.parse(command[1],formatter);}
            catch(Exception e){return "ERROR: Format of the initial date is wrong! Program is going to terminate!\n";}
            didSetInitialTime = true;
        }
        catch(Exception e){
            if(!didSetInitialTime) {
                return "ERROR: First command must be set initial time! Program is going to terminate!\n";
            }
            return e.getMessage();
        }

        return "SUCCESS: Time has been set to " + time.format(formatter) + "!\n";
    }

    /**
     *Sets the time to a new value.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
    */
    public String setTime(String[] command){
        try {

            LocalDateTime newTime;

            //Error handling.
            checkLengthAndInitialTime(command.length,2);

            try {newTime = LocalDateTime.parse(command[1],formatter);}
            catch (Exception e){return "ERROR: Time format is not correct!\n";}
            if(newTime.isBefore(time)){throw new CanNotReverseTime();}
            if(newTime.isEqual(time)){return "ERROR: There is nothing to change!\n";}

            //moves time ahead until newTime is reached.
            while(deviceHub.get(0).getSwitchTime() != null && deviceHub.get(0).getSwitchTime().isBefore(newTime)){
                nop("Nop".split("\t"));
            }

            this.time = newTime;
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Moves time ahead based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String skipMinutes(String[] command){

        try {

            int minutes;

            //Error handling.
            checkLengthAndInitialTime(command.length, 2);

            try{minutes = Integer.parseInt(command[1]);}
            catch (Exception e){throw new ErroneousCommand();}
            if(minutes < 0){throw new CanNotReverseTime();}
            if(minutes == 0){return "ERROR: There is nothing to skip!\n";}

            LocalDateTime newTime = this.time.plusMinutes(minutes);

            //moves time ahead until newTime is reached.
            while(deviceHub.get(0).getSwitchTime() != null && deviceHub.get(0).getSwitchTime().isBefore(newTime)){
                nop("Nop".split("\t"));
            }

            this.time = newTime;
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Moves time ahead to the first SwitchTime in the deviceHub.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String nop(String[] command){

        try {

            //Error handling.
            checkLengthAndInitialTime(command.length, 1);

            if (deviceHub.size() == 0){return "ERROR: There is nothing to switch!\n";}
            LocalDateTime newTime =  deviceHub.get(0).switchTime;
            if (newTime == null){return  "NoPossibleSwitches\n";}

            //Executes Nop.
            this.time = newTime;
            checkSwitchTimes();
            sortDevices();
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Adds a new SmartDevice to deviceHub.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String add(String[] command){

        SmartDevice newDevice;

        try {

            if (!didSetInitialTime){throw new DidNotSetInitialTime();}

            newDevice = SmartDeviceAdder.addDevice(command, this.time);// tries to create the new device.

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(newDevice.getName())){return "ERROR: There is already a smart device with same name!\n";}
            }

            deviceHub.add(newDevice);
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Removes a SmartDevice from the deviceHub based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String remove(String[] command){

        String returnString = "";

        try {

            checkLengthAndInitialTime(command.length, 2);//Error handling.

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[1])){

                    //removes the device.
                    device.setPowerStatus(false,time);
                    returnString += "SUCCESS: Information about removed smart device is as follows:\n";
                    try{returnString += device.zReport() + "\n";}
                    catch(Exception u){returnString += u.getMessage();}
                    deviceHub.remove(device);
                    break;
                }

            }
            if (returnString.equals("")){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return returnString;
    }

    /**
     *Sets a new SwitchTime for a device based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String setSwitchTime(String[] command){

        try {

            LocalDateTime newTime;
            boolean didSwitch = false;

            //Error handling.
            checkLengthAndInitialTime(command.length, 3);

            try{ newTime = LocalDateTime.parse(command[2],formatter);}
            catch(Exception q){throw new ErroneousCommand();}
            if(newTime.isBefore(time)){return "ERROR: Switch time cannot be in the past!\n"; }

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                // Execution of command.
                if (device.getName().equals(command[1])){
                    device.setSwitchTime(newTime);
                    sortDevices();
                    checkSwitchTimes();
                    sortDevices();
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Switches power status of a SmartDevice based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String switchPowerStatus(String[] command){

        try {

            //Error handling.
            checkLengthAndInitialTime(command.length, 3);
            boolean newStatus = SmartDeviceAdder.getPowerStatusFromCommand(command[2]);
            boolean didSwitch = false;

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[1])){

                    if (newStatus == device.getPowerStatus()){
                        return "ERROR: This device is already switched " + ((newStatus) ? "on!\n":"off!\n");
                    }

                    // Execution of command.
                    device.setPowerStatus(newStatus,time);
                    device.setLastCheckedTime(time);
                    sortDevices();
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Changes the name of a SmartDevice based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String changeName(String[] command){

        try {

            //Error handling.
            checkLengthAndInitialTime(command.length, 3);
            if(command[1].equals(command[2])){return "ERROR: Both of the names are the same, nothing changed!\n";}

            boolean didSwitch = false;

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[2])){
                    return "ERROR: There is already a smart device with same name!\n";
                }
            }

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                // Execution of command.
                if (device.getName().equals(command[1])){

                    device.setName(command[2]);
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *plugs a new device to SmartPlug based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String plugIn(String[] command){

        try {

            int newAmpere;
            boolean didSwitch = false;

            //Error handling.
            checkLengthAndInitialTime(command.length, 3);
            try { newAmpere = Integer.parseInt(command[2]);}
            catch (Exception e){throw new ErroneousCommand();}

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[1])){

                    if (!(device instanceof SmartPlug)){throw new WrongDeviceType("smart plug");}
                    if (((SmartPlug) device).getAmpere() != 0){
                        return "ERROR: There is already an item plugged in to that plug!\n";
                    }
                    if (newAmpere <= 0 ){return "ERROR: Ampere value must be a positive number!\n";}

                    // Execution of command.
                    ((SmartPlug) device).setAmpere(newAmpere);
                    device.setLastCheckedTime(time);
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Removes the device from SmartPlug based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String plugOut(String[] command){

        try {
            
            boolean didSwitch = false;

            //Error handling.
            checkLengthAndInitialTime(command.length, 2);

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[1])){

                    if (!(device instanceof SmartPlug)){throw new WrongDeviceType("smart plug");}
                    if (((SmartPlug) device).getAmpere() == 0){
                        return "ERROR: This plug has no item to plug out from that plug!\n";
                    }

                    // Execution of command.((SmartPlug) device).plugOut(time);
                    device.setLastCheckedTime(time);
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Sets a new kelvin value for a SmartLamp based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String setKelvin(String[] command){

        try {

            boolean didSwitch = false;
            int kelvin;

            //Error handling.
            checkLengthAndInitialTime(command.length, 3);
            try{kelvin = Integer.parseInt(command[2]);}
            catch (Exception e){throw new ErroneousCommand();}

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[1])){

                    if (!(device instanceof SmartLamp)){throw new WrongDeviceType("smart lamp");}
                    SmartDeviceAdder.numberIsNotInRange(kelvin,"kelvin");

                    if (device instanceof SmartColorLamp){((SmartColorLamp) device).setHex(false);}

                    // Execution of command.
                    ((SmartLamp) device).setKelvin(kelvin);
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Sets a new brightness value for a SmartLamp based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String setBrightness(String[] command){

        try {

            boolean didSwitch = false;
            int brightness;

            //Error handling.
            checkLengthAndInitialTime(command.length, 3);
            try{brightness = Integer.parseInt(command[2]);}
            catch (Exception e){throw new ErroneousCommand();}


            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[1])){

                    if (!(device instanceof SmartLamp)){throw new WrongDeviceType("smart lamp");}
                    SmartDeviceAdder.numberIsNotInRange(brightness,"brightness");

                    // Execution of command.
                    ((SmartLamp) device).setBrightness(brightness);
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Sets a new hexadecimal color code for a SmartColorLamp based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String setColorCode(String[] command){

        try {

            boolean didSwitch = false;

            //Error handling.
            checkLengthAndInitialTime(command.length, 3);

            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[1])){

                    if (!(device instanceof SmartColorLamp)){throw new WrongDeviceType("smart color lamp");}
                    if(!SmartDeviceAdder.valueIsHex(command[2])){throw new ErroneousCommand();}

                    // Execution of command.
                    ((SmartColorLamp) device).setHexRGBVal(command[2]);
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Sets a new kelvin and brightness value for a SmartLamp based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String setWhite(String[] command){

        try {

            boolean didSwitch = false;
            int kelvin;
            int brightness;

            //Error handling.
            checkLengthAndInitialTime(command.length, 4);
            try{kelvin = Integer.parseInt(command[2]);}
            catch (Exception e){throw new ErroneousCommand();}
            try{brightness = Integer.parseInt(command[3]);}
            catch (Exception e){throw new ErroneousCommand();}


            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[1])){

                    if (!(device instanceof SmartLamp)){throw new WrongDeviceType("smart lamp");}
                    SmartDeviceAdder.numberIsNotInRange(kelvin,"kelvin");
                    SmartDeviceAdder.numberIsNotInRange(brightness,"brightness");

                    if (device instanceof SmartColorLamp){((SmartColorLamp) device).setHex(false);}

                    // Execution of command.
                    ((SmartLamp) device).setKelvin(kelvin);
                    ((SmartLamp) device).setBrightness(brightness);
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Sets a new hexadecimal color code  and brightness value for a SmartColorLamp based on command.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String indicating the success or failure of the operation
     */
    public String setColor(String[] command) {

        try {

            boolean didSwitch = false;
            int brightness;

            //Error handling.
            checkLengthAndInitialTime(command.length, 4);
            if(!SmartDeviceAdder.valueIsHex(command[2])){throw new ErroneousCommand();}
            try{brightness = Integer.parseInt(command[3]);}
            catch (Exception e){throw new ErroneousCommand();}


            for (SmartDevice device : deviceHub) {// checks if the device exists.

                if (device.getName().equals(command[1])){

                    if (!(device instanceof SmartColorLamp)){throw new WrongDeviceType("smart color lamp");}
                    SmartDeviceAdder.numberIsNotInRange(brightness,"brightness");

                    // Execution of command.
                    ((SmartColorLamp) device).setBrightness(brightness);
                    ((SmartColorLamp) device).setHexRGBVal(command[2]);
                    didSwitch = !didSwitch;
                    break;
                }
            }

            if (!didSwitch){throw new DeviceDoesNotExist();}
        }
        catch (Exception e){return e.getMessage();}

        return "";
    }

    /**
     *Returns the zReport of the deviceHub.
     *
     *@param command An array of Strings containing the command and its arguments
     *@return A String containing zReport
     */
    public String zReport(String[] command){

        //Error handling.
        try{checkLengthAndInitialTime(command.length,1);}
        catch(Exception e){return e.getMessage();}

        // Execution of command.
        String returnString = "Time is:\t" + time.format(formatter) + "\n";

        for(SmartDevice device: deviceHub){
            try{returnString += device.zReport() + "\n";}
            catch (Exception r){returnString += r.getMessage() + "\n";}
        }

        return returnString;
    }

    /**
     * Checks switch times of all the SmartDevices in deviceHub list.
     * If the switch time is reached or passed, changes the power status of the SmartDevice, Resets the switch time
     * of the SmartDevice to null. and updates the last checked time of the SmartDevice.
     */
    public void checkSwitchTimes(){


        for (SmartDevice device : deviceHub) {

            if (device.getSwitchTime() == null){continue;}

            if (time.isAfter(device.getSwitchTime()) || time.isEqual(device.getSwitchTime())) {

                device.setPowerStatus(!device.getPowerStatus(), time);
                device.setLastCheckedTime(time);
                device.setSwitchTime(null);
            }
        }
    }

    /**
     * Checks whether the length of a command matches a given length.
     *
     * @param commandLength the length of the command to be checked
     * @param length the expected length of the command
     * @throws ErroneousCommand if the length of the command does not match the expected length
     */
    public void checkLength(int commandLength, int length) throws ErroneousCommand{
        if (commandLength != length){throw new ErroneousCommand();}
    }

    /**
     * Checks whether the length of a command matches a given length and whether an initial time has been set.
     *
     * @param commandLength the length of the command to be checked
     * @param length the expected length of the command
     * @throws ErroneousCommand if the length of the command does not match the expected length
     * @throws DidNotSetInitialTime if an initial time has not been set
     */
    public void checkLengthAndInitialTime(int commandLength, int length)
            throws ErroneousCommand,DidNotSetInitialTime{

        checkLength(commandLength,length);
        if(!didSetInitialTime){throw new DidNotSetInitialTime();}
    }

    /**
     * Sorts the devices in the device hub using the natural ordering of Device's switch times.
     */
    public void sortDevices(){

        deviceHub.sort((device1, device2)
                -> device1.compareTo(device2));
    }
}