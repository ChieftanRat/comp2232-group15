/**
 Manages any damage to a vehicle. 
 * 
 */
abstract public class Damage
{

	/**
	 * Constructor.
	 *
	 */
	public Damage()
	{
	}

	/**
	 *  Returns the cost to repair the damage
	 *
	 * @param 
	 *
	 * @return the damage repair charge
	 */
	public abstract double cost();
	/**
	 *  Returns the extra time multiplier for weather damage
	 *
	 * @param 
	 *
	 * @return zero unless a subclass specifies additional time
	 */
	public double timeMultiplier()
	{
		return 0.0;
	}

}

