/**
 * A mechanic with repair availability, hourly rate and minimum repair days.
 * The provided Mechanic class is extended rather than changed.
 */
public class RepairMechanic extends Mechanic
{
    /** Whether this mechanic is currently free to accept a repair. */
    private boolean available;

    /** Hourly labour charge for this mechanic. */
    private double hourlyRate;

    /** Minimum repair duration for this mechanic, in days. */
    private int minimumRepairDays;

    /**
     * Creates a mechanic using the assignment's experience-based rates.
     * @param name mechanic's name
     * @param experience one of the database's experience codes
     * @param id mechanic's employee number
     */
    public RepairMechanic(String name, int experience, int id)
    {
        super(name, experience, id);
        available = true;

        if (experience == DatabaseConstants.NOVICE)
        {
            hourlyRate = 20.0;
            minimumRepairDays = 20;
        }
        else if (experience == DatabaseConstants.INTERMEDIATE)
        {
            hourlyRate = 60.0;
            minimumRepairDays = 15;
        }
        else if (experience == DatabaseConstants.EXPERT)
        {
            hourlyRate = 100.0;
            minimumRepairDays = 10;
        }
        else
        {
            throw new IllegalArgumentException("Invalid mechanic experience: " + experience);
        }
    }

    /** @return true if the mechanic can be assigned a vehicle */
    public boolean isAvailable()
    {
        return available;
    }

    /**
     * Updates whether the mechanic is currently available.
     * @param available new availability status
     */
    public void setAvailable(boolean available)
    {
        this.available = available;
    }

    /** @return labour cost per hour */
    public double hourlyRate()
    {
        return hourlyRate;
    }

    /** @return minimum repair time in days */
    public int minimumRepairDays()
    {
        return minimumRepairDays;
    }
}
