/**
 Contains info about damage caused by an impact to one or more parts.  
 * 
 */
public class CollisionDamage extends Damage
{
	private int parts;

	/**
	 * Constructor.
	 *
	 */
	public CollisionDamage(int parts)
	{
		switch (parts) {
			case DatabaseConstants.DAMAGE_BACK_BUMPER,
				 DatabaseConstants.DAMAGE_FRONT_BUMPER,
				 DatabaseConstants.DAMAGE_ROOF,
				 DatabaseConstants.DAMAGE_SIDE_DOOR,
				 DatabaseConstants.DAMAGE_WINDSHIELD,
				 DatabaseConstants.DAMAGE_SIDE_MIRROR -> this.parts = parts;

				 default -> throw new IllegalArgumentException("Invalid collision code: " + parts);
		}
	}

	/**
	 *  Returns the damaged parts. 
	 *
	 * @param 
	 *
	 * @return 
	 */
	public int parts()
	{
		return parts;
	}

	@Override 
	public double cost()
	{
		return switch (parts) {
			case DatabaseConstants.DAMAGE_BACK_BUMPER -> 150.0;
			case DatabaseConstants.DAMAGE_FRONT_BUMPER -> 100.0;
			case DatabaseConstants.DAMAGE_ROOF -> 500.0;
			case DatabaseConstants.DAMAGE_SIDE_DOOR -> 300.0;
			case DatabaseConstants.DAMAGE_WINDSHIELD -> 400.0;
			default -> 50.0;
		};
	}

}

