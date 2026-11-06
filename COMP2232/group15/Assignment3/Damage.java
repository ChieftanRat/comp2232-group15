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


	// Returns the extra repair days for this damage.
	public abstract int extraDays(int days);

}

