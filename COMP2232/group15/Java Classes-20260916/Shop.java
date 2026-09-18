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
				break;
				
			case Menu.MENU_OPTION_VEHICLES_UNDER_REPAIR:
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