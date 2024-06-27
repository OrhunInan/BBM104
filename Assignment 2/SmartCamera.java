import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SmartCamera extends SmartDevice{
    private double storageUsageMB;
    private double usedStorage = 0;

    /**
     * Constructs a SmartCamera object with the given name, storage usage, and last checked time,
     * and sets its power status to off.
     *
     * @param name name/ID of the SmartCamera
     * @param storageUsageMB the amount of storage used by the SmartCamera, in megabytes
     * @param lastCheckedTime the last time the SmartCamera was checked or initiation time of SmartCamera
     */
    public SmartCamera(String name,double storageUsageMB, LocalDateTime lastCheckedTime){

        super(name,false, lastCheckedTime);
        this.storageUsageMB = storageUsageMB;
    }

    /**
     * Constructs a SmartCamera object with the given name, storage usage, power status, and last checked time,
     * and sets its power status to off.
     *
     * @param name Name/ID of the SmartCamera
     * @param storageUsageMB the amount of storage used by the SmartCamera, in megabytes
     * @param powerStatus power state of the SmartCamera
     * @param lastCheckedTime the last time the SmartCamera was checked or initiation time of SmartCamera
     */
    public SmartCamera(String name,double storageUsageMB,boolean powerStatus, LocalDateTime lastCheckedTime){

        super(name, powerStatus, lastCheckedTime);
        this.storageUsageMB = storageUsageMB;
    }

    /**
     * Generates a z-report for the SmartCamera.
     *
     * @return a string containing the z-report information
     */
    @Override
    public String zReport() {

        return String.format("Smart Camera %s is %s and used %.2f MB of storage so far "
                + "(excluding current status), and its time to switch its status is %s.",
                this.name,(this.powerStatus) ? "on":"off", usedStorage,
                (this.switchTime == null) ? "null":this.switchTime.format(formatter));
    }

    /**
     * Sets the power status of the SmartDevice and updates its last checked time.
     * If the new power status is off, the method calculates any usage since the last check and updates the
     * overall used storage property of the SmartCamera.
     *
     * @param powerStatus the new power status of the SmartDevice
     * @param newTime the new last checked time for the SmartDevice
     */
    @Override
    public void setPowerStatus(boolean powerStatus, LocalDateTime newTime) {

        super.setPowerStatus(powerStatus,newTime);
        if(!powerStatus){calculateMBUsage(newTime);}//calculates power usage if the devise is off.
    }

    /**
     * Calculates the storage usage in MB for the SmartCamera between the last checked time
     * and the given finish time, and updates the usedStorage property.
     *
     * @param finishTime the end time to calculate storage usage up until
     */
    public void calculateMBUsage(LocalDateTime finishTime){

        long pastTime = this.lastCheckedTime.until(finishTime, ChronoUnit.MINUTES);
        usedStorage += pastTime*this.storageUsageMB;
    }
}
