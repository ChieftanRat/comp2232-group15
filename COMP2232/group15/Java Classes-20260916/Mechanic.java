public class Mechanic 
{
    private String name;
    private int id;
    private int experience;

    public Mechanic()
    {
        this.name = "";
        this.id = 0;
        this.experience = 0;
    }

    public Mechanic(String name, int experience, int id)
    {
        this.name = name;
        this.id = id;
        this.experience = experience;
    }

    public String name()
    {
        return name;
    }

    public int id()
    {
        return id;
    }

    public int experience()
    {
        return experience;
    }

}
