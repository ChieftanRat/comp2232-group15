public class Shop
{
	private Mechanic mechanics[];
	private Vehicle vehicles[];
	private Display output;
	private Menu menu;
	
	public Shop(String prompt)
	{
		mechanics = new Mechanic[DatabaseConstants.TOTAL_MECHANICS];
		for(int i = 0; i < DatabaseConstants.TOTAL_MECHANICS; i++)
			mechanics[i] = new Mechanic();

		vehicles = new Vehicle[DatabaseConstants.TOTAL_VEHICLES];
		for(int i = 0; i < DatabaseConstants.TOTAL_VEHICLES; i++)
			vehicles[i] = new Vehicle();

		output = new Display();
		menu = new Menu(prompt);
	}

	public void simulate() throws OutOfBoundIndexNumber
	{
		// Run the simulation.
		setup();
		run();
	}

	private void setup() throws OutOfBoundIndexNumber
	{
		DatabaseVM data = new DatabaseVM();

		MechanicData md;
		for(int i = 0; i < DatabaseConstants.TOTAL_MECHANICS; i++)
		{
			md = data.mechanic(i); 
			mechanics[i] = new Mechanic(md.name(), md.experience(), md.id());
		}

		VehicleData vd;
		for(int i = 0; i < DatabaseConstants.TOTAL_VEHICLES; i++)
		{
			vd = data.vehicle(i);
			vehicles[i] = new Vehicle(vd.company(), vd.model(), vd.type(), vd.damage());
		}
	}

	private void listAvailableMechanics(){
		//this is false since the checks haven't begun
		boolean isMechanicFound = false;
		//loops through the mechanics object
		for (int i=0; i < mechanics.length; i++) {
			String mechanicName = mechanics[i].name();
			//checks for elements in the mechanics array with non-empty name values
			if (!mechanicName.isEmpty()) {

				if (!isMechanicFound) {
					//purely for aesthetic purposes
					System.out.println();
					System.out.println("Available Mechanics: ");
				}
				//outputs a name when it matches that criteria
				System.out.println(mechanicName);
				//is true every time a mechanic is found
				isMechanicFound = true;
			}
		}
		//checks if the boolean hasn't been changed after the loop
		if (!isMechanicFound) {
			//informs the user if that is the case
			System.out.println("No mechanics are available");
		}
	}
	// start of the new method to list vehicles under repair 
	private void listVehiclesUnderRepair(){
		//this is false since the checks haven't begun
		boolean isVehicleFound = false;
		//loops through the vehicles object
		for (int i=0; i < vehicles.length; i++) {
			String vehicleModel = vehicles[i].company() + " " + vehicles[i].model() + " " + vehicles[i].type() + " " + vehicles[i].damage();
			//checks for elements in the vehicles array with non-empty model values
			// damage shows as 0 due to how the 
			if (!vehicleModel.isEmpty()) {

				if (!isVehicleFound) {
					//purely for aesthetic purposes
					System.out.println();
					System.out.println("Vehicles Under Repair: ");
				}
				//outputs a model when it matches that criteria
				System.out.println(vehicleModel);
				//is true every time a vehicle is found
				isVehicleFound = true;
			}
		}
		//checks if the boolean hasn't been changed after the loop
		if (!isVehicleFound) {
			//informs the user if that is the case
			System.out.println("No vehicles are under repair");
		}
	}
			
	private void run()
	{
		// This is the starting point of the simulation.

		output.addJob("V1", "m1", 1, 1.1);
		output.addJob("V2", "m2", 2, 2.2);

		menu.displayMenu();
		while(!performAction(menu.getMenuSelection())) 	
		{
			// Output the summary as explained in the assignment documentation.
			output.outputStatusTable();
			
			
			menu.displayMenu();
		}
		output.outputStatusTable();
		output.outputSummary(0, 0, 0);
	}
	
	private boolean performAction(int action)
	{
		boolean result = false;
		
		// Perform the actions of the simulation as outlined by the assignment documentation.
		
		switch(action)
		{
			case Menu.MENU_OPTION_AVAILABLE_MECHANICS:
				//shows a list of available mechanics once the user selects this option
				listAvailableMechanics();
				break;
				
			case Menu.MENU_OPTION_VEHICLES_UNDER_REPAIR:
				//shows a list of vehicles under repair once the user selects this option
				listVehiclesUnderRepair();
				break;
				
			case Menu.MENU_OPTION_TOTAL_VEHICLES_LEFT_TO_REPAIR: 
				break;
				
			case Menu.MENU_OPTION_TOTAL_REVENUE: 
				break;
				
			case Menu.MENU_OPTION_JOB_LIST:
				break;
				
			case Menu.MENU_OPTION_RUN_SIMULATION:
				break;
				
			case Menu.MENU_OPTION_FIXED_SIMULATION_RUN:
				break;
				
			case Menu.MENU_OPTION_EXIT:
				result = true;
				break;
				
			default:
				System.out.println("Invalid menu selection was made.\n");
		}
		
		return result;
	}
}