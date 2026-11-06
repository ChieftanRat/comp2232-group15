/**
 * Stores details of one repair job, created when a mechanic is assigned.
 * This record holds values rather than references to other objects.
 */
public class JobRecord
{
    /** Vehicle type, such as Car, Van or Truck. */
    private String vehicleType;
    /** Name of the mechanic assigned to the repair. */
    private String mechanicName;
    /** Full calculated charge for the repair. */
    private double totalCost;
    /** Total estimated time to complete the repair in days. */
    private int repairDays;

    /**
     * Creates a job record.
     * @param vehicleType type of vehicle being repaired
     * @param mechanicName name of the assigned mechanic
     * @param totalCost total price of the job
     * @param repairDays number of days needed for the job
     */
    public JobRecord(String vehicleType, String mechanicName, double totalCost, int repairDays)
    {
        this.vehicleType = vehicleType;
        this.mechanicName = mechanicName;
        this.totalCost = totalCost;
        this.repairDays = repairDays;
    }

    /** @return vehicle type */
    public String vehicleType() { return vehicleType; }

    /** @return assigned mechanic's name */
    public String mechanicName() { return mechanicName; }

    /** @return total job cost */
    public double totalCost() { return totalCost; }

    /** @return number of repair days */
    public int repairDays() { return repairDays; }
}
