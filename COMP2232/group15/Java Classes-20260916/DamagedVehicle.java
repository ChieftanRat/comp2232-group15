public class DamagedVehicle extends Vehicle{
    private int damage;

    DamagedVehicle(String company, String model, String type, int damage) {
        // as the Vehicle class has private fields, any argument required must be passed along via super so the constructor continues to initialise its own fields
        super(company, model, type, damage);
        // damage is unusued in the Vehicle class being set to 0, so we ensure it is initialised with the parameter here
        this.damage = damage;
    }

    // The vechicle class already has this method but we are explicityly telling the code to return our this.damage = damage instead 
    @Override 
    // makes sure we return DamagedVehicle's value above instead of Vehicle's 0
    public int damage() {return damage;}

}
