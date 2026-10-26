/**
 Contains the details for each pothole. 
 * 
 */
public class Pothole extends Damage
{
	private int size;
	/**
	 * Constructor.
	 *
	 */
	public Pothole(int size)
	{
		switch (size) {
			case DatabaseConstants.DAMAGE_POTHOLE_SMALL,
			DatabaseConstants.DAMAGE_POTHOLE_MEDIUM,
			DatabaseConstants.DAMAGE_POTHOLE_LARGE -> this.size = size;

			default -> throw new IllegalArgumentException("Invalid pothole code: " + size);
				
		}
	}

	/**
	 *  Returns the size of the pothole
	 *
	 * @param 
	 *
	 * @return the pothole's damage code
	 */
	public int size()
	{
		return size;
	}

	@Override
	public double cost() 
	{
		return switch (size) {
			case DatabaseConstants.DAMAGE_POTHOLE_SMALL -> 100.0;
			case DatabaseConstants.DAMAGE_POTHOLE_MEDIUM -> 300.0;
			default -> 600.0;
		};
	}

}

