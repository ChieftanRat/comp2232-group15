public class Vehicle 
{
    private String model;
    private String type;
    private String company;
	private int damage;

    public Vehicle()
    {
        this.company = "";
        this.model = "";
        this.type = "";
		this.damage = 0;
    }

    public Vehicle(String company, String model, String type, int damage)
    {
        this.company = company;
        this.model = model;
        this.type = type;
		this.damage = 0;
    }

    public String company()
    {
        return company;
    }

    public String type()
    {
        return type;
    }

    String model()
    {
        return model;
    }
	
	public int damage()
	{
		return damage;
	}
}