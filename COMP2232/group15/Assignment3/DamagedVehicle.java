/**
 * A vehicle with its actual damage code and assignment-defined repair data.
 * Extends Vehicle because the original Vehicle class must not be edited.
 */
public class DamagedVehicle extends Vehicle
{
    /** Code identifying the vehicle's damage. */
    private int damage;
    /** Basic vehicle repair charge, excluding damage and labour. */
    private double repairCost;
    /** Price of vehicle replacement parts. */
    private double partsCost;
    /** Minimum time required for this type and model, in days. */
    private int minimumRepairDays;

    /**
     * Creates a vehicle and identifies its repair costs and minimum days.
     * @param company vehicle manufacturer
     * @param model vehicle model
     * @param type vehicle type (Car, Van or Truck)
     * @param damage damage code from the database
     */
    public DamagedVehicle(String company, String model, String type, int damage)
    {
        super(company, model, type, damage);
        this.damage = damage;

        if (type.equalsIgnoreCase("Car"))
        {
            if (model.equalsIgnoreCase("Sedan"))
            {
                repairCost = 150; partsCost = 200; minimumRepairDays = 5;
            }
            else if (model.equalsIgnoreCase("Electric"))
            {
                repairCost = 300; partsCost = 400; minimumRepairDays = 10;
            }
            else if (model.equalsIgnoreCase("Hybrid"))
            {
                repairCost = 200; partsCost = 300; minimumRepairDays = 7;
            }
            else if (model.equalsIgnoreCase("Hatchback"))
            {
                repairCost = 100; partsCost = 100; minimumRepairDays = 5;
            }
            else
                throw new IllegalArgumentException("Unknown car model: " + model);
        }
        else if (type.equalsIgnoreCase("Van"))
        {
            if (model.equalsIgnoreCase("Diesel"))
            {
                repairCost = 500; partsCost = 550; minimumRepairDays = 15;
            }
            else if (model.equalsIgnoreCase("Electric"))
            {
                repairCost = 700; partsCost = 750; minimumRepairDays = 20;
            }
            else if (model.equalsIgnoreCase("Gas"))
            {
                repairCost = 600; partsCost = 600; minimumRepairDays = 10;
            }
            else
                throw new IllegalArgumentException("Unknown van model: " + model);
        }
        else if (type.equalsIgnoreCase("Truck"))
        {
            if (model.equalsIgnoreCase("Flatbed"))
            {
                repairCost = 1000; partsCost = 4000; minimumRepairDays = 25;
            }
            else if (model.equalsIgnoreCase("Dump"))
            {
                repairCost = 2000; partsCost = 6000; minimumRepairDays = 30;
            }
            else if (model.equalsIgnoreCase("Container"))
            {
                repairCost = 4000; partsCost = 9000; minimumRepairDays = 40;
            }
            else
                throw new IllegalArgumentException("Unknown truck model: " + model);
        }
        else
            throw new IllegalArgumentException("Unknown vehicle type: " + type);
    }

    /** @return original damage code */
    @Override
    public int damage()
    {
        return damage;
    }

    /** @return basic vehicle repair charge */
    public double repairCost()
    {
        return repairCost;
    }

    /** @return replacement parts cost */
    public double partsCost()
    {
        return partsCost;
    }

    /** @return vehicle's minimum repair days */
    public int minimumRepairDays()
    {
        return minimumRepairDays;
    }
}
