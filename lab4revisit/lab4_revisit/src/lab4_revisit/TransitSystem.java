package lab4_revisit;

import java.util.Scanner;
import java.sql.*;

public class TransitSystem {

	static void displayTable(Statement s, String table) throws SQLException
	{
		try
		{
		ResultSet myRs = s.executeQuery("Select* from " + table);
		ResultSetMetaData rsMeta = myRs.getMetaData();
		int colCount = rsMeta.getColumnCount();
		for(int i = 1; i <= colCount; i++){
			System.out.print(rsMeta.getColumnName(i) + "    ");
		}
		System.out.println();
		while(myRs.next())
		{
			for(int i = 1; i<= colCount; i++)
			System.out.print(myRs.getString(i) +  "\t\t");
			
			System.out.println();
		}
		myRs.close();
		}
		catch(SQLException e)
		{
			System.out.println("No such table found.");
		}
	}
	
		
	static void displaySchedule(Statement s, String x, String y, String z) throws SQLException
	{
		try
		{
		ResultSet myRs = s.executeQuery("Select O.tripnumber, O.ScheduledStartTime, "
				+ "O.scheduledARrivalTime, O.DriverName, O.BUSID "
				+ "From tripOffering O, Trip T "
				+ "Where O.TripNumber = T.Tripnumber "
				+ "AND StartLocationName = '" + x + "' AND DestinationName = '" + y + "' AND DATE = '" + z +"';");
		ResultSetMetaData rsMeta = myRs.getMetaData();
		int colCount = rsMeta.getColumnCount();
		for(int i = 1; i <= colCount; i++){
			System.out.print(rsMeta.getColumnName(i) + "   \t");
		}
		System.out.println();
		while(myRs.next())
		{
			for(int i = 1; i<= colCount; i++)
			System.out.print(myRs.getString(i) +  "\t\t\t");
			
			System.out.println();
		}
		myRs.close();
		}
		catch(SQLException e)
		{
			System.out.println("No such table found.");
		}
	}
	static void deleteTripOffering(Statement s) throws SQLException
	{
		Scanner in = new Scanner(System.in);
		String tripNum, Date, ScheduledStartTime;
	
		System.out.print("Enter Trip #: ");
		tripNum = in.nextLine();
		System.out.print("Enter Date: ");
		Date = in.nextLine();
		System.out.print("Enter ScheduledStartTime: ");
		ScheduledStartTime = in.nextLine();
		try
		{
			if(s.executeUpdate("DELETE From tripOffering " + 
					"WHERE TripNumber = '" + tripNum + "' AND " + 
					"Date = '" + Date + "' AND " +
					"ScheduledStartTime = '" + ScheduledStartTime + "'") == 0)
			{
					System.out.println("No Trip Offering found");
			}
			else
					System.out.println("Successfully deleted Trip Offering");
		}
		catch (SQLException e){

			System.out.println("No Trip Offering with Trip Number: " + tripNum + " on "+ Date + " starting at "+ ScheduledStartTime);
		}
		in.close();
	}
	static void addTripOffering(Statement s) throws SQLException
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Trip Number: ");
		String tripNo = sc.nextLine().trim();
		System.out.print("Date: ");
		String date = sc.nextLine().trim();
		System.out.print("Scheduled Start Time: ");
		String startTime = sc.nextLine().trim();
		System.out.print("Scheduled Arrival Time: ");
		String arrivalTime = sc.nextLine().trim();
		System.out.print("Driver Name: ");
		String driver = sc.nextLine().trim();
		System.out.print("Bus ID: ");
		String bus = sc.nextLine().trim();
		
		try{
			s.execute("INSERT INTO TripOffering VALUES ('" + tripNo + "', '" + date + "', '" + startTime + "', '" + arrivalTime + "', '" + driver + "', '" + bus + "')");
			System.out.print("Successfully added a new Trip Offering. ");
		}catch (SQLException e){
			System.out.println("Invalid input");
		}
		sc.close();
	}
	static void changeDriver(Statement s) throws SQLException
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("New Driver Name: ");
		String driver = sc.nextLine();
		System.out.print("Start Trip Number: ");
		String tripNo = sc.nextLine();
		System.out.print("Date: ");
		String date = sc.nextLine();
		System.out.print("Scheduled Start Time: ");
		String startTime = sc.nextLine();
		
		try{
			if(s.executeUpdate("UPDATE TripOffering " + 
								"SET DriverName = '" + driver + "' " +
								"WHERE TripNumber = '" + tripNo + "' AND " + 
								"Date = '" + date + "' AND " +
								"ScheduledStartTime = '" + startTime + "'") == 0){
				System.out.println("No Trip Offering with Trip Number: " + tripNo + " on "+ date + " starting at "+ startTime);
			}else
				System.out.println("Successfully updated Driver");
		}catch (SQLException e){
			//if some error occurs check input
			//e.printStackTrace();
			System.out.println("Unable to change driver");
		}
		
		
	}
	static void changeBus(Statement s) throws SQLException
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("New Bus Number: ");
		String bus = sc.nextLine();
		System.out.print("Start Trip Number: ");
		String tripNo = sc.nextLine();
		System.out.print("Date: ");
		String date = sc.nextLine();
		System.out.print("Scheduled Start Time: ");
		String startTime = sc.nextLine();
		
		try{
			if(s.executeUpdate("UPDATE TripOffering " + 
								"SET BusID = '" + bus + "' " +
								"WHERE TripNumber = '" + tripNo + "' AND " + 
								"Date = '" + date + "' AND " +
								"ScheduledStartTime = '" + startTime + "'") == 0){
				System.out.println("No Trip Offering with Trip Number: " + tripNo + " on "+ date + " starting at "+ startTime);
			}else
				System.out.println("Successfully updated Bus");
		}catch (SQLException e){
			e.printStackTrace();
			System.out.println("Invalid bus number");
		}
	}
	static void displayWeeklyShedule (Statement s) throws SQLException
	{
		Scanner in = new Scanner(System.in);
		String driver, date;
		System.out.print("Driver: ");
		driver = in.nextLine();
		System.out.print("date: ");
		date = in.nextLine();
		
		try
		{
			ResultSet rs = s.executeQuery("SELECT driverName, Date, ScheduledStartTime, ScheduledArrivalTime " +
					"FROM TripOffering " +
					"WHERE DriverName LIKE '" + driver + "' " +
					"AND Date = '" + date + "' ");
			ResultSetMetaData rsMeta = rs.getMetaData();
			int colCount = rsMeta.getColumnCount();
			for(int i = 1; i <= colCount; i++){
				System.out.print(rsMeta.getColumnName(i) + "   \t");
			}
			System.out.println();
			while(rs.next())
			{
				for(int i = 1; i<= colCount; i++)
				System.out.print(rs.getString(i) +  "\t\t");
				
				System.out.println();
			}
			rs.close();	
		}
		catch(SQLException e)
		{
			System.out.println("Invalid input");
		}
	}
	static void addDriver(Statement s) throws SQLException
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Driver name: ");
		String driver = sc.nextLine();
		System.out.print("Phone number: ");
		String phone = sc.nextLine();
		try
		{
			s.execute("INSERT INTO Driver VALUES ('" + driver + "', '" + phone + "')");
			System.out.println("Successfully added a new Driver");
		}
		catch (SQLException e)
		{
			System.out.println("Could not add driver.");
		}
		sc.close();
	}
	static void addBus(Statement s) throws SQLException
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Bus ID: ");
		String bus = sc.nextLine();
		System.out.print("Bus model: ");
		String model = sc.nextLine();
		System.out.print("Bus year: ");
		String year = sc.nextLine();
		
		
		try{
			s.execute("INSERT INTO Bus VALUES ('" + bus + "', '" + model + "', '" + year + "')");
			System.out.println("Successfully added a new Bus");
		}catch (SQLException e){
			System.out.println("Could not add a bus");
		}
	}
	public static void deleteBus(Statement stmt) throws SQLException{
		Scanner sc = new Scanner(System.in);
		System.out.print("Bus ID: ");
		String bus = sc.nextLine();
		
		try{
			
			if(stmt.executeUpdate("DELETE from Bus " + 
								"WHERE BusID = '" + bus + "'") == 0){
				System.out.println("No Bus ID = " + bus);
			}else{
				System.out.println("Successfully deleted");
			}
		}catch(SQLException e){
			System.out.println("No Bus ID  " + bus);
		}
	}
	static void recrodActualTrip(Statement s) throws SQLException
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Trip Number: ");
		String tripNum = sc.nextLine();
		System.out.print("Date: ");
		String date = sc.nextLine();
		System.out.print("Scheduled Start Time: ");
		String startTime = sc.nextLine();
		System.out.print("Stop Number: ");
		String stop = sc.nextLine();
		System.out.print("Scheduled Arrival Time: ");
		String arrivalTime = sc.nextLine();
		System.out.print("Actual Start Time: ");
		String actualStart = sc.nextLine();
		System.out.print("Actual Arrival Time: ");
		String actualArrival = sc.nextLine();
		System.out.print("Passengers in: ");
		String passIn = sc.nextLine();
		System.out.print("Passengers out: ");
		String passOut = sc.nextLine();
		
		try{
			
			s.execute("INSERT INTO ActualTripStopInfo VALUES ('" + tripNum + "', '" + date + "', '" + startTime + "', '" + stop + "', '" + arrivalTime
					 + "', '" + actualStart + "', '" + actualArrival + "', '" + passIn + "', '" + passOut + "')");
		}catch(SQLException e){
			System.out.println("Invalid input");
		}
		System.out.println("Successfully recorded data");
		sc.close();
	}
	public static void main(String[] args) {
		
		try{
			//Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab4", "root", "Tritoan123");
			
			Statement myStmt = myConn.createStatement();
			
			
			int input = 0;
			Scanner in = new Scanner(System.in);
			System.out.println("This is a Pomona Transit system program. Enter an option 0 -8:\n"
					+ "Option0: display table of your choice.\n"
					+ "Option1: display schedule of all trips given the start location, destination and date\n"
					+ "Option2: Edit the schedule by changing the TripOffering table\n"
					+ "Option3: Display the stops of a given trip\n"
					+ "Option4: Display weekly schedule of a given driver and date\n"
					+ "Ootion5: Add a driver\n"
					+ "Option6: add a bus\n"
					+ "Option7: Delete a bus\n"
					+ "Option8: Record actual trip information.\n"
					+ "Please enter an option: ");
		
			input = in.nextInt();
			
			while (input!= 0 && input != 1 && input != 2 && input!= 3 && input!= 4 && input !=5 && input !=6 && input!= 7 &&input!=8)
			{
				System.out.println("This is a Pomona Transit system program. Enter an option 0 -8:\n"
						+ "Option0: display table of your choice.\n"
						+ "Option1: display schedule of all trips given the start location, destination and date\n"
						+ "Option2: Edit the schedule by changing the TripOffering table\n"
						+ "Option3: Display the stops of a given trip\n"
						+ "Option4: Display weekly schedule of a given driver and date\n"
						+ "Ootion5: Add a driver\n"
						+ "Option6: add a bus\n"
						+ "Option7: Delete a bus\n"
						+ "Option8: Record actual trip information.\n"
						+ "Please enter an option: ");

				input = in.nextInt();
			}
			
			System.out.println("You choose option: " + input);
			
			switch(input)
			{
			case 0:
			{
				System.out.println("Which table you want to display?");
				String table;
				table = in.next();
				displayTable(myStmt, table);
				break;
			}
				
			case 1: 
			{
				
				String startLocation, destination, date;
				//get input from user
				System.out.print("StartLocation: ");
				startLocation = in.next();
				System.out.print("Destination: ");
				destination = in.next();
				System.out.print("date =");
				date = in.next();
				
				//call displayschedule
				displaySchedule(myStmt, startLocation, destination, date);
				break;
			}
			case 2:
				
					System.out.println("What would you like to do? ");
					System.out.println("1. Delete a trip offering. ");
					System.out.println("2. Add a set of trip offering. ");
					System.out.println("3. Change the driver for a given trip ");
					System.out.println("4. Change the bus for a given trip offering.");
					System.out.println("5. Cancel option ");
					
					int option;
					System.out.print("Please enter an option (1-5): ");
					option = in.nextInt();
					
					while (option != 1 && option != 2 && option != 3 && option != 4 && option !=5)
					{
						System.out.println("What would you like to do? ");
						System.out.println("1. Delete a trip offering. ");
						System.out.println("2. Add a set of trip offering. ");
						System.out.println("3. Change the driver for a given trip ");
						System.out.println("4. Change the bus for a given trip offering.");
						System.out.println("5. Cancel option ");
						System.out.print("Please enter an option (1-5): ");
						option = in.nextInt();
					}
					if (option == 1)
					{
						deleteTripOffering(myStmt);
					}
					else if (option ==2)
					{
						addTripOffering(myStmt);
						System.out.println("Do you have more trips to enter? Y/N");
						String answer = in.next();
						
						while (answer.charAt(0) == 'y')
						{
							addTripOffering(myStmt);
							System.out.println("Do you have more trips to enter? Y/N");
							answer = in.next();
						}
						
					}
					else if (option == 3)
					{
						changeDriver(myStmt);
					}
					else if(option == 4)
						changeBus(myStmt);
					else if (option == 5)
						System.out.println("See you next time");
					else
						System.out.println("Invalid option");
					
			
				break;
			case 3:
				System.out.println("Enter a trip number: ");
				String tripNum = in.next();
				ResultSet rs = myStmt.executeQuery("Select TripNumber, stopNumber, "
						+ "sequencenumber, drivingtime from tripstopinfo where "
						+ "tripnumber = " + tripNum);
				
				ResultSetMetaData rsMeta = rs.getMetaData();
				int colCount = rsMeta.getColumnCount();
				for(int i = 1; i <= colCount; i++){
					System.out.print(rsMeta.getColumnName(i) + "\t");
				}
				System.out.println();
				while(rs.next())
				{
					for(int i = 1; i<= colCount; i++)
					System.out.print(rs.getString(i) +  " \t\t");
					
					System.out.println();
				}
				break;
			case 4:
				displayWeeklyShedule(myStmt);
				break;
			case 5:
				addDriver(myStmt);
				break;
			case 6:
				addBus(myStmt);
				break;
			case 7:
				deleteBus(myStmt);
				break;
			case 8:
				recrodActualTrip(myStmt);
				break;
				
			}
			
			System.out.println("Thank you for checking out the Pomona transit system");
			
			in.close();
			
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		
	}

}

