/*	Class Name: ControllerExample
 *********************************************************************
 *	This class is an example of a controller class. It will receive
 *	dummy data (that is implicitly entered in -- for now) and will 
 *	perform the necessary function with the given data by calling the 
 *	'Model' class. 
 */

package lostandfound.controller;

//import java.sql.SQLException;
//import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.text.DateFormat;
import java.util.*;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;


/*Controller class contains all handler methods*/

public class Controller {
	public static ModelAndView handler(Request request, Response response) {
		/* Create a hash map of the "name" and "job" arguments from the URL.
		 * Calls to methods that would interact with the database would go here.
		 * The code below is a simple stand-in for more complex model code.
		 */
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("guest", request.params(":guest"));

		// Return an object with the completed model data and the name of the template to use. 
		return new ModelAndView(templateVars, "indexServerSideEnabled");
	}
	
	public static ModelAndView handler2(Request request, Response response) {
		/* Create a hash map of the "name" and "job" arguments from the URL.
		 * Calls to methods that would interact with the database would go here.
		 * The code below is a simple stand-in for more complex model code.
		 */
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("guest", request.params(":guest"));

		// Return an object with the completed model data and the name of the template to use. 
		return new ModelAndView(templateVars, "index");
	}
}
/*public class ExampleController {

	public static void runExample(){
		Scanner kbd = new Scanner(System.in);
	    Item model = new Item();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		int functionNo = 1;

		do{
			functionNo = startMenu(kbd);
			if (functionNo == 1){
				int id, adminId;
				String description, status;
				java.sql.Date dateFound = null, dateRetrieved = null;
				java.util.Date dateFnd = null, dateRtvd = null;
				
				System.out.println("Enter an item id:");
				id = kbd.nextInt();
				kbd.nextLine(); // Eat up extra new line
				
				System.out.println("Enter a description:");
				description = kbd.nextLine();
				
				System.out.println("Enter item status");
				status = kbd.next();
				kbd.nextLine(); // Eat up extra new line
				
				System.out.println("Enter date found (YYYY-MM-DD):");
				String date_Found = kbd.nextLine();
				//Convert String Date to Date
				try {
					dateFnd = sdf.parse(date_Found);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Convert Date to SQL Date
				dateFound = new java.sql.Date(dateFnd.getTime());
				
				System.out.println("Enter date retrieved (YYYY-MM-DD):");
				String date_Retrieved = kbd.nextLine();
				//Convert String Date to Date
				try {
					dateRtvd = sdf.parse(date_Retrieved);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Convert Date to SQL Date
				dateRetrieved = new java.sql.Date(dateRtvd.getTime());
				
				System.out.println("Enter Admin ID:");
				adminId = kbd.nextInt();
				
				model.addItem(id, description, status, dateFound, 
						dateRetrieved, adminId);
			}
			
			else if (functionNo != 0){ 
				System.out.println("Sorry, your answer was invalid." +
					"\nPlease try again!");
			}
		}while (functionNo != 0);
		
		System.out.println("Thank you for using the 'Lost and Found'" +
		" Database!\nPlease come again!");

	}
	
	public static int startMenu(Scanner kbd)
	{
		System.out.println("What would you like to do?");
		System.out.println("1: Add an Item\n0: Quit");
		return kbd.nextInt();
	}

}// End of class*/
