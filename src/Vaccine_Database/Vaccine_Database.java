package Vaccine_Database;
import java.sql.*;
import java.util.Scanner;

public class Vaccine_Database {
	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/Vaccine_Database" 
						+ "?user=testuser" 
						+ "&password=password"
						+ "&allowMultiQueries=true"
						+ "&createDataBaseIfNotExist = true"
						+ "&useSSL = true");

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
						ResultSet.CONCUR_READ_ONLY);) {
			stmt.execute(
					"DROP SCHEMA IF EXISTS Vaccine_Database;"
					+ "CREATE SCHEMA Vaccine_Database;"
					+ "USE Vaccine_Database;");

			stmt.execute(
					"CREATE TABLE COMPANY (\r\n"
							+ "  Name VARCHAR(50) PRIMARY KEY,\r\n"
							+ "  Website VARCHAR(255) CHECK (Website LIKE \"https://%\")\r\n"
							+ ");\r\n"
							+ "\r\n"
							+ "CREATE TABLE DISEASE (\r\n"
							+ "  Name VARCHAR(50) PRIMARY KEY,\r\n"
							+ "  Communicable BOOL,\r\n"
							+ "  -- Whether the disease can be transmitted from a human to\r\n"
							+ "  --    another.\r\n"
							+ "  TYPE ENUM (\"infectious\", \"deficiency\", \"hereditary\")\r\n"
							+ ");\r\n"
							+ "\r\n"
							+ "CREATE TABLE VACCINE (\r\n"
							+ "  Name VARCHAR(50) PRIMARY KEY,\r\n"
							+ "  Manufacturer VARCHAR(50) NOT NULL,\r\n"
							+ "  FOREIGN KEY (Manufacturer) REFERENCES COMPANY (NAME) ON\r\n"
							+ "    UPDATE CASCADE\r\n"
							+ ");\r\n"
							+ "\r\n"
							+ "CREATE TABLE EFFICACY (\r\n"
							+ "  DiseaseName VARCHAR(50),\r\n"
							+ "  VaccineName VARCHAR(50),\r\n"
							+ "  Efficacy DECIMAl(5, 2),\r\n"
							+ "  PRIMARY KEY (DiseaseName, VaccineName),\r\n"
							+ "  FOREIGN KEY (DiseaseName) REFERENCES DISEASE (NAME),\r\n"
							+ "  FOREIGN KEY (VaccineName) REFERENCES VACCINE (NAME)\r\n"
							+ ");");
			stmt.execute(
					"INSERT INTO COMPANY\r\n"
							+ "VALUES (\r\n"
							+ "  \"Moderna\",\r\n"
							+ "  \"https://www.modernatx.com/\");\r\n"
							+ "\r\n"
							+ "INSERT INTO DISEASE\r\n"
							+ "VALUES (\r\n"
							+ "  \"Coronavirus disease 2019\",\r\n"
							+ "  TRUE,\r\n"
							+ "  \"infectious\");\r\n"
							+ "\r\n"
							+ "INSERT INTO VACCINE\r\n"
							+ "VALUES (\r\n"
							+ "  \"mRNA-1273\",\r\n"
							+ "  \"Moderna\");\r\n"
							+ "\r\n"
							+ "INSERT INTO EFFICACY\r\n"
							+ "VALUES (\r\n"
							+ "  \"Coronavirus disease 2019\",\r\n"
							+ "  \"mRNA-1273\",\r\n"
							+ "  94.1);\r\n"
					);
			
			
			/*
			 * Create the Vaccine_Database database and start it out with its tables.
			 * At the start of each run, each table starts out with its one default entry
			 * 
			 * 
			 * The program uses a switch with multiple cases, each allowing the user to select various options
			 * The program allows for the user to choose SELECT, INSERT, and UPDATE sql queries
			 * 
			 * 
			 * 
			 */
			
			
			String strSelect = "SHOW TABLES";
			System.out.println("Current SQL query is: " + strSelect + "\n");
			ResultSet rset = stmt.executeQuery(strSelect);

			System.out.println("The tables in Vaccine_Database are:");
			while (rset.next()) {
				System.out.println(rset.getString(1));
			}
			System.out.print("--------------------------------------------------------------------------");
			System.out.println("\n" + "Enter a letter to view a table's contents: " + "\n(c)ompany " + "\n(d)isease "
					+ "\n(e)fficacy " + "\n(v)accine ");
			System.out.println("Enter a number to insert a tuple into a table: " + "\n(1)company " + "\n(2)disease "
					+ "\n(3)efficacy " + "\n(4)vaccine ");
			System.out.println("Enter a table name to update or delete a tuple in that table: " + "\ncompany " + "\ndisease "
					+ "\nefficacy" + "\nvaccine ");
			System.out.println("\nTo view the companies and the vaccines they created type (find)");
			System.out.println("\nTo view this list again, type in the letter (m) and press enter");
			System.out.println("To quit type in the letter (q) and press enter");
			System.out.print("--------------------------------------------------------------------------\n");
			
			Scanner scanner = new Scanner(System.in);
			String selection = scanner.next();
			Boolean start = true;
			
			begin: while (start) {
				try {
					int rowCount = 0;
					switch (selection) {
					case "c":	// view the entries in the company table
						String selC = "SELECT * FROM company";
						System.out.println("Current SQL query is: " + selC + "\n");

						ResultSet rsetC = stmt.executeQuery(selC);
						while (rsetC.next()) {
							System.out.println("");
							System.out.print("Name: " + rsetC.getString("Name") + " | ");
							System.out.print("Website: " + rsetC.getString("Website") + " | ");
							System.out.println("");
							rowCount++;
						}
						System.out.println("\nTotal number of entries = " + rowCount);
						break;

					case "d":	// view the entries in the disease table
						String selD = "SELECT * FROM disease";
						System.out.println("Current SQL query is: " + selD + "\n");

						ResultSet rsetD = stmt.executeQuery(selD);
						while (rsetD.next()) {
							System.out.println("");
							System.out.print("Name: " + rsetD.getString("Name") + " | ");
							System.out.print("Communicable: " + rsetD.getString("Communicable") + " | ");
							System.out.print("TYPE: " + rsetD.getString("TYPE") + " | ");
							System.out.println("");
							rowCount++;
						}
						System.out.println("\nTotal number of entries = " + rowCount);
						break;

					case "e":	// view the entries in the efficacy table
						String selE = "SELECT * FROM efficacy";
						System.out.println("Current SQL query is: " + selE + "\n");

						ResultSet rsetE = stmt.executeQuery(selE);
						while (rsetE.next()) {
							System.out.println("");
							System.out.print("DiseaseName: " + rsetE.getString("DiseaseName") + " | ");
							System.out.print("VaccineName: " + rsetE.getString("VaccineName") + " | ");
							System.out.print("Efficacy: " + rsetE.getString("Efficacy") + " | ");
							System.out.println("");
							rowCount++;
						}
						System.out.println("\nTotal number of entries = " + rowCount);
						break;

					case "v":	// view the entries in the vaccine table
						String selV = "SELECT * FROM vaccine";
						System.out.println("Current SQL query is: " + selV + "\n");

						ResultSet rsetV = stmt.executeQuery(selV);
						while (rsetV.next()) {
							System.out.println("");
							System.out.print("Name: " + rsetV.getString("Name") + " | ");
							System.out.print("Manufacturer: " + rsetV.getString("Manufacturer") + " | ");
							System.out.println("");
							rowCount++;
						}
						System.out.println("\nTotal number of entries = " + rowCount);
						break;

					case "m":	// view the menu that shows available options for user to pick
						String tables = "SHOW TABLES";
						System.out.println("Current SQL query is: " + tables + "\n");
						ResultSet rsetT = stmt.executeQuery(tables);
						System.out.println("The tables in Vaccine_Database are:");
						
						while (rsetT.next()) {
							System.out.println(rsetT.getString(1));
						}

						System.out.print("--------------------------------------------------------------------------");
						System.out.println("\n" + "Enter a letter to view a table's contents: " + "\n(c)ompany "
								+ "\n(d)isease " + "\n(e)fficacy " + "\n(v)accine ");
						System.out.println("Enter a number to insert a tuple into a table: " + "\n(1)company "
								+ "\n(2)disease " + "\n(3)efficacy " + "\n(4)vaccine ");
						System.out.println("Enter a table name to update or delete a tuple in that table: " + "\ncompany "
								+ "\ndisease " + "\nefficacy" + "\nvaccine ");

						System.out.println("\nTo view this list again, type in the letter (m) and press enter");
						System.out.println("To quit type in the letter (q) and press enter");
						System.out.println("\nTo view the companies and the vaccines they created type (find)");
						System.out.print("--------------------------------------------------------------------------\n");
						break;

					case "q":	// end program
						System.out.println("You sure you wish to exit? (y)es | (n)");
						String ans = scanner.next();

						if (ans.equals("y")) {
							System.out.print("Good bye");
							start = false;
							System.exit(0);
						} else if (ans.equals("n")) {
							System.out.println("Enter in the letter (m) to see the menu");
							selection = scanner.next();
							continue begin;
						} else
							break;
					case "1":	// insert a tuple in the company table
						String sqlC = "INSERT INTO company (Name, Website) VALUES (?, ?)";
						System.out.println("Current SQL query is: " + sqlC + "\n");
						PreparedStatement preparedStatementC = conn.prepareStatement(sqlC);
						
						System.out.print("Enter company name: ");
						String Name = scanner.next();
						System.out.print("Enter company website link (https://example.com/): ");
						String Website = scanner.next();
						
						preparedStatementC.setString(1, Name);
						preparedStatementC.setString(2, Website);
						preparedStatementC.executeUpdate();
						System.out.println("New company has been added!");
						break;

					case "2":	// insert a tuple in the disease table
						String sqlD = "INSERT INTO disease (Name, Communicable, TYPE) VALUES (?, ?, ?)";
						System.out.println("Current SQL query is: " + sqlD + "\n");
						PreparedStatement preparedStatementD = conn.prepareStatement(sqlD);
						
						System.out.print("Enter disease name: ");
						String NameD = scanner.next();
						System.out.print("Communicable? (Enter 1 for yes or 0 for no): ");
						String Comm = scanner.next();
						System.out.println("Type of disease (infectious, deficiency, hereditary): ");
						String Type = scanner.next();
						
						preparedStatementD.setString(1, NameD);
						preparedStatementD.setString(2, Comm);
						preparedStatementD.setString(3, Type);
						preparedStatementD.executeUpdate();
						System.out.println("New disease has been added!");
						break;

					case "3":	// insert a tuple in the efficacy table
						String sqlE = "INSERT INTO efficacy (DiseaseName, VaccineName, Efficacy) VALUES (?, ?, ?)";
						System.out.println("Current SQL query is: " + sqlE + "\n");
						PreparedStatement preparedStatementE = conn.prepareStatement(sqlE);

						System.out.print("Enter disease name (from disease table): ");
						String DiseaseName = scanner.next();
						System.out.print("Enter vaccine name (from vaccine table): ");
						String VaccineName = scanner.next();
						System.out.print("Enter efficacy: ");
						String Efficacy = scanner.next();
						
						preparedStatementE.setString(1, DiseaseName);
						preparedStatementE.setString(2, VaccineName);
						preparedStatementE.setString(3, Efficacy);
						preparedStatementE.executeUpdate();
						System.out.println("New efficacy has been added!");
						break;

					case "4":	// insert a tuple in the vaccine table
						String sqlV = "INSERT INTO vaccine (Name, Manufacturer) VALUES (?, ?)";
						System.out.println("Current SQL query is: " + sqlV + "\n");
						PreparedStatement preparedStatementV = conn.prepareStatement(sqlV);
						
						System.out.print("Enter vaccine name: ");
						String NameV = scanner.next();
						System.out.print("Enter vaccine manufacturer name (Name of company): ");
						String Manufacturer = scanner.next();
						
						preparedStatementV.setString(1, NameV);
						preparedStatementV.setString(2, Manufacturer);
						preparedStatementV.executeUpdate();
						System.out.println("New vaccine has been added!");
						break;

					case "company":	// update a tuple in the company table
						System.out.println("Do you want to (u)PDATE or (d)ELETE a tuple from company table");
						String ansC = scanner.next();
						
						if(ansC.equals ("u")) {
						String sqlUPDATE1 = "UPDATE company " + "SET company.Name = ?, company.Website = ? "
								+ "WHERE company.Name = ? ";
						System.out.println("Current SQL query is: " + sqlUPDATE1 + "\n");
						PreparedStatement preparedStatementUPC = conn.prepareStatement(sqlUPDATE1);

						System.out.print("Enter the name of company you want to change: ");
						String NameUPC3 = scanner.next();
						System.out.print("Enter the new name for that company: ");
						String NameUPC1 = scanner.next();
						System.out.print("Enter the new website for that company (https://example.com/): ");
						String WebsiteUPC2 = scanner.next();
						
						preparedStatementUPC.setString(3, NameUPC3);
						preparedStatementUPC.setString(1, NameUPC1);
						preparedStatementUPC.setString(2, WebsiteUPC2);
						preparedStatementUPC.executeUpdate();
						System.out.println("Company has been updated!");
						break;
						}
						else if(ansC.equals("d")) { // delete a tuple in company table
							String sqlDELETE1 = "DELETE FROM company "
									+ "WHERE company.Name = ? ";
							System.out.println("Current SQL query is: " + sqlDELETE1 + "\n");
							PreparedStatement preparedStatementDELC = conn.prepareStatement(sqlDELETE1);
							
							System.out.println("Enter the name of company you want to delete: ");
							String NameDEL1 = scanner.next();
							
							preparedStatementDELC.setString(1, NameDEL1);
							preparedStatementDELC.executeUpdate();
							System.out.println("Company has been deleted!");
							break;
						}
						else
							break;

					case "disease":	// update a tuple in the disease table
						System.out.println("Do you want to (u)PDATE or (d)ELETE a tuple from disease table");
						String ansD = scanner.next();
						
						if(ansD.equals ("u")) {
						String sqlUPDATE2 = "UPDATE disease "
								+ "SET disease.Name = ?, disease.Communicable = ?, disease.TYPE = ? "
								+ "WHERE disease.Name = ? ";
						System.out.println("Current SQL query is: " + sqlUPDATE2 + "\n");
						PreparedStatement preparedStatementUPD = conn.prepareStatement(sqlUPDATE2);

						System.out.print("Enter the name of disease you want to change: ");
						String NameUPD4 = scanner.next();
						System.out.print("Enter the new name for that disease: ");
						String NameUPD1 = scanner.next();
						System.out.print("Is it Communicable? (1 for yes | 0 for no): ");
						String CommunicableUPD2 = scanner.next();
						System.out.print("What type of disease is it? (infectious, deficiency, hereditary): ");
						String TypeUPD3 = scanner.next();
						
						preparedStatementUPD.setString(4, NameUPD4);
						preparedStatementUPD.setString(1, NameUPD1);
						preparedStatementUPD.setString(2, CommunicableUPD2);
						preparedStatementUPD.setString(3, TypeUPD3);
						preparedStatementUPD.executeUpdate();
						System.out.println("Disease has been updated!");
						break;
						}
						else if(ansD.equals("d")) {  // delete a tuple in disease table
							String sqlDELETE2 = "DELETE FROM disease "
									+ "WHERE disease.Name = ? ";
							System.out.println("Current SQL query is: " + sqlDELETE2 + "\n");
							PreparedStatement preparedStatementDELD = conn.prepareStatement(sqlDELETE2);
							
							System.out.println("Enter the name of disease you want to delete: ");
							String NameDEL2 = scanner.next();
							
							preparedStatementDELD.setString(1, NameDEL2);
							preparedStatementDELD.executeUpdate();
							System.out.println("Disease has been deleted!");
							break;
						}
						else
							break;

					case "efficacy":	// update a tuple in the efficacy table
						System.out.print("Do you want to (u)PDATE or (d)ELETE a tuple from disease table");
						String ansE = scanner.next();
						
						if(ansE.equals ("u")) {
						String sqlUPDATE3 = "UPDATE efficacy " + "SET efficacy.Efficacy = ?, "
								+ "WHERE efficacy.VaccineName = ? ";
						System.out.println("Current SQL query is: " + sqlUPDATE3 + "\n");
						PreparedStatement preparedStatementUPE = conn.prepareStatement(sqlUPDATE3);
						
						System.out.print("Enter the name of the vaccine you want to change the efficacy for: ");
						String NameUPE2 = scanner.next();
						System.out.print("Enter the new efficacy for that vaccine: ");
						String NameUPE1 = scanner.next();
						
						preparedStatementUPE.setString(2, NameUPE2);
						preparedStatementUPE.setString(1, NameUPE1);
						preparedStatementUPE.executeUpdate();
						System.out.println("Efficacy has been updated!");
						break;
						}
						else if(ansE.equals("d")) { // delete a tuple in efficacy table
							String sqlDELETE3 = "DELETE FROM efficacy "
									+ "WHERE efficacy.DiseaseName = ? ";
							System.out.println("Current SQL query is: " + sqlDELETE3 + "\n");
							PreparedStatement preparedStatementDELE = conn.prepareStatement(sqlDELETE3);
							
							System.out.println("Enter the name of disease you want to delete the efficacy for: ");
							String DiseaseNameDEL3 = scanner.next();
							
							preparedStatementDELE.setString(1, DiseaseNameDEL3);
							preparedStatementDELE.executeUpdate();
							System.out.println("Efficacy has been deleted!");
							break;
						}
						else
							break;

					case "vaccine":	// update a tuple in the vaccine table
						System.out.println("Do you want to (u)PDATE or (d)ELETE a tuple from vaccine table");
						String ansV = scanner.next();
						
						if(ansV.equals ("u")) { // update a tuple in vaccine table
						String sqlUPDATE4 = "UPDATE vaccine "
								+ "SET vaccine.Name = ?, vaccine.Manufacturer = ? "
								+ "WHERE vaccine.Name = ? ";
						System.out.println("Current SQL query is: " + sqlUPDATE4 + "\n");
						PreparedStatement preparedStatementUPV = conn.prepareStatement(sqlUPDATE4);
						
						System.out.print("Enter the name of the vaccine you want to change: ");
						String NameUPV3 = scanner.next();
						System.out.print("Enter the new name for that vaccine: ");
						String NameUPV1 = scanner.next();
						System.out.print("Enter the name of the manufacturer (from company table): ");
						String NameUPV2 = scanner.next();
						
						preparedStatementUPV.setString(3, NameUPV3);
						preparedStatementUPV.setString(2, NameUPV2);
						preparedStatementUPV.setString(1, NameUPV1);
						preparedStatementUPV.executeUpdate();
						System.out.println("Vaccine has been updated!");
						break;
						}
						else if(ansV.equals("d")) { // delete a tuple in vaccine table
							String sqlDELETE4 = "DELETE FROM vaccine "
									+ "WHERE vaccine.Name = ? ";
							System.out.println("Current SQL query is: " + sqlDELETE4 + "\n");
							PreparedStatement preparedStatementDELE = conn.prepareStatement(sqlDELETE4);
							
							System.out.println("Enter the name of vaccine you want to delete: ");
							String Name4 = scanner.next();
							
							preparedStatementDELE.setString(1, Name4);
							preparedStatementDELE.executeUpdate();
							System.out.println("Vaccine has been deleted!");
							break;
						}
						else
							break;
						
					case "find": // find a specific vaccine and its manufacturer, select join query
						String findV = "SELECT vaccine.Name, vaccine.Manufacturer "
								+ "FROM vaccine "
								+ "INNER JOIN company "
								+ "ON vaccine.Manufacturer = company.Name "
								+ "GROUP BY Name;";
						System.out.println("Current SQL query is: " + findV + "\n");

						ResultSet rsetFV = stmt.executeQuery(findV);
						while (rsetFV.next()) {
							System.out.println("");
							System.out.print("Name: " + rsetFV.getString("vaccine.Name") + " | ");
							System.out.print("Manufacturer: " + rsetFV.getString("vaccine.Manufacturer") + " | ");
							System.out.println("");
							rowCount++;
						}
						System.out.println("\nTotal number of entries = " + rowCount);
						break;
						
					default:
						System.out.println("Not a valid selection, enter the letter (m) to see menu options");
					}
					selection = scanner.next();
				} catch (SQLException e) { // catch common sql errors such as foreign key constraints to allow user to make another selection
					System.out.println(e);
					System.out.println("\nEnter the letter (m) to view menu or enter another valid command to continue");
					selection = scanner.next();
				}
			}
			conn.close();
			scanner.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}