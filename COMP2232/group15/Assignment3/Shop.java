public class Shop
{
	private Mechanic mechanics[];
	private Vehicle vehicles[];
	private Display output;
	private Menu menu;
/** Vehicles waiting for a mechanic, in arrival order. */
	private Vehicle[] waitingVehicles;
	/** Number of vehicles currently in the waiting list.
	*/
	private int waitingCount;
	private JobRecord[] jobs;
	private int jobCount;
	private double totalRevenue;
	/** Vehicle currently assigned to each mechanic, or null if free. */
	private Vehicle[] activeVehicles;
	/** Number of days remaining for each mechanic's current repair. */
	private int[] remainingRepairDays;
	private double[] activeRepairCosts;
	private int repairedCount;
	private int totalDays;
	
	public Shop(String prompt)
	{
		mechanics = new Mechanic[DatabaseConstants.TOTAL_MECHANICS];
		for(int i = 0; i < DatabaseConstants.TOTAL_MECHANICS; i++)
			mechanics[i] = new Mechanic();

		vehicles = new Vehicle[DatabaseConstants.TOTAL_VEHICLES];
		for(int i = 0; i < DatabaseConstants.TOTAL_VEHICLES; i++)
			vehicles[i] = new Vehicle();

		waitingVehicles = new Vehicle[DatabaseConstants.TOTAL_VEHICLES];
		waitingCount = 0;

		jobs = new JobRecord[DatabaseConstants.TOTAL_VEHICLES];
		jobCount = 0;
		totalRevenue = 0.0;
		activeVehicles = new Vehicle[DatabaseConstants.TOTAL_MECHANICS];
		remainingRepairDays = new int[DatabaseConstants.TOTAL_MECHANICS];
		activeRepairCosts = new double[DatabaseConstants.TOTAL_MECHANICS];
        repairedCount = 0;
        totalDays = 0;
		
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
			mechanics[i] = new RepairMechanic(md.name(), md.experience(), md.id());
		}

		VehicleData vd;
		for(int i = 0; i < DatabaseConstants.TOTAL_VEHICLES; i++)
		{
			vd = data.vehicle(i);
			//the old Vechicle constructor never used the dmg value passed in here, as it would always be initialised to 0, 
			// so we used DamagedVehicle instead which fixes this
			vehicles[i] = new DamagedVehicle(vd.company(), vd.model(), vd.type(), vd.damage());
		}
	}
	/**
	 * Assigns a vehicle to the first available mechanic in database order.
	 * Creates its job record before repair work begins.
	 * A vehicle remains in the main list if no mechanic can accept it.
	 * @param vehicleIndex position of the vehicle in the main vehicle list
	 * @return true if assigned; false if no mechanic is available or index invalid
	 */
	private boolean assignVehicle(int vehicleIndex)
	{
		if (vehicleIndex < 0 || vehicleIndex >= vehicles.length ||
			vehicles[vehicleIndex] == null ||
			vehicles[vehicleIndex].model().isEmpty())
		{
			return false;
		}
		if (waitingCount > 0)
		{
			addToWaitingList(vehicleIndex);
			return false;
		}
		for (int i = 0; i < mechanics.length; i++)
		{
			RepairMechanic mechanic = (RepairMechanic) mechanics[i];
			if (mechanic.isAvailable())
			{
				DamagedVehicle vehicle = (DamagedVehicle) vehicles[vehicleIndex];
				int days = calculateRepairDays(vehicle, mechanic);
				JobRecord job = addJobRecord(vehicle, mechanic);
				if (job == null)
				{
					return false;
				}

				activeVehicles[i] = vehicle;
				remainingRepairDays[i] = days;
				activeRepairCosts[i] = job.totalCost();
				mechanic.setAvailable(false);
				vehicles[vehicleIndex] = null;
				output.outputAssignedToMechanic(mechanic.name(), vehicle.type(),
					vehicle.company(), vehicle.model());
				return true;
			}
		}
		// No mechanic is available, so the vehicle must wait.
		addToWaitingList(vehicleIndex);
		return false;
	}
	/**
	 * Assigns the first waiting vehicle to a mechanic who has become free.
	 * This will be called when a repair finishes in the daily simulation.
	 * @param mechanicIndex position of the newly available mechanic
	 * @return true if the waiting vehicle was assigned; otherwise false
	 */
	private boolean assignFirstWaitingVehicle(int mechanicIndex)
	{
		if (mechanicIndex < 0 || mechanicIndex >= mechanics.length ||
			waitingCount == 0 || jobCount >= jobs.length)
		{
			return false;
		}

		RepairMechanic mechanic = (RepairMechanic) mechanics[mechanicIndex];
		if (!mechanic.isAvailable() || activeVehicles[mechanicIndex] != null)
		{
			return false;
		}
		DamagedVehicle vehicle = (DamagedVehicle) waitingVehicles[0];
		int days = calculateRepairDays(vehicle, mechanic);
		JobRecord job = addJobRecord(vehicle, mechanic);
		if (job == null)
		{
			return false;
		}

		removeFromWaitingList();
		activeVehicles[mechanicIndex] = vehicle;
        remainingRepairDays[mechanicIndex] = days;
        activeRepairCosts[mechanicIndex] = job.totalCost();
		mechanic.setAvailable(false);
		output.outputAssignedToMechanic(mechanic.name(), vehicle.type(),
			vehicle.company(), vehicle.model());
		return true;
	}
		
	private void listAvailableMechanics(){
		//this is false since the checks haven't begun
		boolean isMechanicFound = false;
		//loops through the mechanics object
		for (int i=0; i < mechanics.length; i++) {
			String mechanicName = mechanics[i].name();
			//checks for elements in the mechanics array with non-empty name values
			if (!mechanicName.isEmpty() && ((RepairMechanic) mechanics[i]).isAvailable()) {

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
	private void listVehiclesUnderRepair(){
		//this is false since the checks haven't begun
		boolean isVehicleFound = false;
		//loops through the vehicles object
		for (int i=0; i < vehicles.length; i++) {
			// A vehicle moved to the waiting list is no longer in this array.
			if (vehicles[i] != null && !vehicles[i].model().isEmpty()) {
				String vehicleModel = vehicles[i].company() + " " + vehicles[i].model() + " " + vehicles[i].type() + " " + vehicles[i].damage();


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
			System.out.println("No vehicles are being repaired.");
		}
	}

	/**
	 * Moves a vehicle from the vehicle list to the end of the waiting list.
	 * Call this when no mechanic is available for the vehicle.
	 * @param index position of the vehicle in the main vehicle array
	 * @return true if a vehicle was moved; otherwise false
	 */
	private boolean addToWaitingList(int index)
	{
		if (index < 0 || index >= vehicles.length ||
		    vehicles[index] == null || vehicles[index].model().isEmpty() ||
		    waitingCount >= waitingVehicles.length)
		{
			return false;
		}

		Vehicle vehicle = vehicles[index];
		waitingVehicles[waitingCount] = vehicle;
		waitingCount++;
		vehicles[index] = null;
		output.outputAddedToWaitingList(vehicle.type(), vehicle.company(), vehicle.model());
		return true;
	}

	/**
	 * Removes and returns the first waiting vehicle, preserving arrival order.
	 * Call this when a mechanic is ready to accept a waiting vehicle.
	 * @return first waiting vehicle, or null if the list is empty
	 */
	private Vehicle removeFromWaitingList()
	{
		if (waitingCount == 0)
		{
			return null;
		}

		Vehicle nextVehicle = waitingVehicles[0];
		for (int i = 1; i < waitingCount; i++)
		{
			waitingVehicles[i - 1] = waitingVehicles[i];
		}
		waitingCount--;
		waitingVehicles[waitingCount] = null;
		return nextVehicle;
	}
	/**
	* Outputs the number of vehicles still in the main and waiting lists.
	*/
	private void totalVehiclesLeftToRepair()
    {
        System.out.println("There are " + countVehiclesLeft() + " number of vehicles left.");
    }
			
    /**
     * Performs one day's work: takes one new vehicle from the main list,
     * advances active repairs, and assigns waiting vehicles after completion.
     */
    private void processRepairDay()
    {
        totalDays++;
        output.outputIterationNumber(totalDays);

        // The assignment processes one new vehicle on each simulated day.
        for (int i = 0; i < vehicles.length; i++)
        {
            if (vehicles[i] != null && !vehicles[i].model().isEmpty())
            {
                assignVehicle(i);
                break;
            }
        }

        for (int i = 0; i < mechanics.length; i++)
        {
            if (activeVehicles[i] != null)
            {
                remainingRepairDays[i]--;
                if (remainingRepairDays[i] == 0)
                {
                    Vehicle completed = activeVehicles[i];
                    RepairMechanic mechanic = (RepairMechanic) mechanics[i];

                    output.outputRepairedByMechanic(mechanic.name(),
                        completed.type(), completed.company(), completed.model());
                    repairedCount++;
                    totalRevenue += activeRepairCosts[i];
                    activeVehicles[i] = null;
                    remainingRepairDays[i] = 0;
                    activeRepairCosts[i] = 0.0;
                    mechanic.setAvailable(true);

                    // A waiting job starts now, but its first repair day is tomorrow.
                    assignFirstWaitingVehicle(i);
                }
            }
        }
    }

	/**
     * Runs the simulation until all repairs finish or the day limit is reached.
     * @param maximumDays maximum number of days for this run
     */
    private void runSimulation(int maximumDays)
    {
        int daysThisRun = 0;
        while (daysThisRun < maximumDays && countVehiclesLeft() > 0)
        {
            processRepairDay();
            daysThisRun++;
        }
    }

    /** @return number of vehicles not yet fully repaired */
    private int countVehiclesLeft()
    {
        int total = waitingCount;
        for (int i = 0; i < activeVehicles.length; i++)
        {
            if (activeVehicles[i] != null)
                total++;
        }
        for (int i = 0; i < vehicles.length; i++)
        {
            if (vehicles[i] != null && !vehicles[i].model().isEmpty())
                total++;
        }
        return total;
    }

    /**
     * Reads the number of days for a fixed simulation from the keyboard.
     * @return positive day limit, or zero for an invalid input
     */
    private int readSimulationDays()
    {
        System.out.print("Enter the number of Days you want simulated: ");
        try
        {
            // No import required. The supplied Menu also reads one line at a time.
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(System.in));
            int days = Integer.parseInt(reader.readLine().trim());
            if (days > 0)
                return days;
        }
        catch (java.io.IOException | NumberFormatException e)
        {
            // An invalid value is reported below.
        }
        System.out.println("Please enter a positive whole number.");
        return 0;
    }
	
	private void outputRepairStatus()
	{
		try
		{
			output.outputStatusTable();
		}
		catch (java.util.NoSuchElementException e)
		{
			if (jobCount != 0)
				throw e;
		}
	}
	private void run()
	{
		// This is the starting point of the simulation.

		menu.displayMenu();
		while(!performAction(menu.getMenuSelection())) 	
		{
			// Output the summary
			outputRepairStatus();
			
			
			menu.displayMenu();
		}
		outputRepairStatus();
		output.outputSummary(repairedCount, totalDays, (int) Math.round(totalRevenue));
	}

	/**
	 * Creates the appropriate damage object for a database code.
	 *
	 * @param damageCode the damage code obtained from VehicleData
	 * @return the matching damage object
	 * @throws IllegalArgumentException if the code is unsupported
	 */
	private Damage createDamage(int damageCode)
	{
		switch (damageCode)
		{
			case DatabaseConstants.DAMAGE_POTHOLE_SMALL:
			case DatabaseConstants.DAMAGE_POTHOLE_MEDIUM:
			case DatabaseConstants.DAMAGE_POTHOLE_LARGE:
				return new Pothole(damageCode);

			case DatabaseConstants.DAMAGE_WIND:
			case DatabaseConstants.DAMAGE_FLOOD:
			case DatabaseConstants.DAMAGE_FIRE:
				return new WeatherDamage(damageCode);

			case DatabaseConstants.DAMAGE_BACK_BUMPER:
			case DatabaseConstants.DAMAGE_FRONT_BUMPER:
			case DatabaseConstants.DAMAGE_ROOF:
			case DatabaseConstants.DAMAGE_SIDE_DOOR:
			case DatabaseConstants.DAMAGE_WINDSHIELD:
			case DatabaseConstants.DAMAGE_SIDE_MIRROR:
				return new CollisionDamage(damageCode);

			default:
				throw new IllegalArgumentException(
					"Unsupported damage code: " + damageCode);
		}
	}

	/**
	 * Calculates the number of whole days needed for a vehicle repair.
	 * The longer minimum time of the vehicle and mechanic is used.
	 * Weather damage adds extra repair time.
	 * @param vehicle vehicle to repair
	 * @param mechanic mechanic assigned to the repair
	 * @return total repair time, rounded up to a whole day
	 */
	private int calculateRepairDays(DamagedVehicle vehicle, RepairMechanic mechanic)
	{
		int days = Math.max(vehicle.minimumRepairDays(), mechanic.minimumRepairDays());
		Damage damage = createDamage(vehicle.damage());

		if (damage instanceof WeatherDamage)
		{
			WeatherDamage weather = (WeatherDamage) damage;
			return (int) Math.ceil(days * (1 + weather.timeMultiplier()));
		}

		return days;
	}

	/**
	 * Calculates the total price of a repair using damage, labour,
	 * vehicle repair charge, and parts charge.
	 * @param vehicle vehicle to repair
	 * @param mechanic mechanic assigned to the repair
	 * @return total repair charge in dollars
	 */
	private double calculateRepairCost(DamagedVehicle vehicle, RepairMechanic mechanic)
	{
		int days = calculateRepairDays(vehicle, mechanic);
		Damage damage = createDamage(vehicle.damage());
		double labourCost = mechanic.hourlyRate() * 8 * days;

		return damage.cost() + labourCost + vehicle.repairCost() + vehicle.partsCost();
	}

	/**
	 * Creates a job record for an assigned repair and updates Display.
	 * Call this only after a mechanic has actually been assigned.
	 * @param vehicle vehicle being repaired
	 * @param mechanic mechanic assigned to the vehicle
	 * @return the new record, or null if the record list is full
	 */
	private JobRecord addJobRecord(DamagedVehicle vehicle, RepairMechanic mechanic)
	{
		if (jobCount >= jobs.length)
		{
			return null;
		}

		int days = calculateRepairDays(vehicle, mechanic);
		double cost = calculateRepairCost(vehicle, mechanic);
		JobRecord job = new JobRecord(vehicle.type(), mechanic.name(), cost, days);
		jobs[jobCount] = job;
		jobCount++;

		output.addJob(job.vehicleType(), job.mechanicName(), job.repairDays(), job.totalCost());
		return job;
	}

	/**
	 * Lists the job records in the order they were created.
	 * @return nothing
	 */
	private void listJobRecords()
	{
		if (jobCount == 0)
		{
			System.out.println("No job records available.");
			return;
		}

		for (int i = 0; i < jobCount; i++)
		{
			JobRecord job = jobs[i];
			System.out.println(job.vehicleType() + " " + job.mechanicName()
				+ " $" + job.totalCost() + " " + job.repairDays() + " days");
		}
	}
	/**
	 * Displays the revenue earned from completed repairs.
	 *Revenue increases only when a repair finishes.
	 */
	private void displayTotalRevenue()
	{
		System.out.println("Total revenue is $" + String.format("%.2f", totalRevenue));
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
				totalVehiclesLeftToRepair();
				break;
				
			case Menu.MENU_OPTION_TOTAL_REVENUE: 
				displayTotalRevenue();
				break;
				
			case Menu.MENU_OPTION_JOB_LIST:
				listJobRecords();
				break;
				
			case Menu.MENU_OPTION_RUN_SIMULATION:
				runSimulation(Integer.MAX_VALUE);
				break;
				
			case Menu.MENU_OPTION_FIXED_SIMULATION_RUN:
				int limit = readSimulationDays();
                if (limit > 0)
                    runSimulation(limit);
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
