/**
 Contains info about different types of damage caused by the elements. 
 * 
 */
public class WeatherDamage extends Damage
{
	private int weatherType;

	/**
	 * Constructor.
	 *
	 */
	public WeatherDamage(int weatherType)
	{
		switch (weatherType) {
			case DatabaseConstants.DAMAGE_WIND,
				 DatabaseConstants.DAMAGE_FLOOD,
				 DatabaseConstants.DAMAGE_FIRE -> this.weatherType = weatherType;
			default -> throw new IllegalArgumentException("Invalid weather code: " + weatherType);
		}
	}

	/**
	 *  Returns the type of weather that cause the damage 
	 *
	 * @param 
	 *
	 * @return the weather's damage
	 */
	public int weatherType()
	{
		return weatherType;
	}

	@Override 
	public double cost() {
		return switch (weatherType) 
		{
			case DatabaseConstants.DAMAGE_WIND -> 400.0;
			case DatabaseConstants.DAMAGE_FLOOD -> 700.0;
			default -> 800.0;
		};
	}

	/**
	 *  Returns the extra repair time multiplier for this weather damage
	 *
	 * @return the extra repair time multiplier
	 */
	public double timeMultiplier() {
		return switch(weatherType) {
			case DatabaseConstants.DAMAGE_WIND -> 0.2;
			case DatabaseConstants.DAMAGE_FLOOD -> 0.4;
			default -> 0.5;
		};
	}

}

