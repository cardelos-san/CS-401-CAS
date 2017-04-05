package lostandfound.model;

//import java.sql.SQLException;
import lostandfound.util.*;
import java.util.*;

public class Item {

	Scanner kbd = new Scanner(System.in);
	DBase db = new DBase("root", "Kpopfeen96!!");
	String cmd;
	
	/**
	 * chackDBconnection - Checks whether there is a connection to the database
	 * @return returns a string indicating the status of the connection to the database
	 */
	public String checkDBconnection(){
		// If not open -- not connected to database
		if (!db.isOpen()) {
          return "Could not connect to database.%n";
          // GET COMMENT FOR THIS EXIT
          //System.exit(1); --> Will never get to this line!
      }
		else return ""; // Connected to database
	}
	
	/**
	 * addItem - Adds an item to the database. Retrieves information regarding an item 
	 * and calls a method in the DBase class with the given information to complete
	 * the transaction
	 * @param id the item's id
     * @param description a description of the item
     * @param status the status of the item ('lost' or 'found')
     * @param dateFound the date of which the item was found
     * @param dateRetrieved the date of which the item was retrieved (if item was found)
     * @param adminId the id of the administrator that is processing this transaction
	 */
	public void addItem(int id, String description, String status,
			java.sql.Date dateFound, java.sql.Date dateRetrieved, int adminId)
	{
		db.addItem(id, description, status, dateFound, dateRetrieved, adminId);
	}
	
	/**
	 * deleteItem - Deletes an item from the database. Retrieves the item's id and calls
	 * a method in the DBase class with the given information to complete the transaction
	 * @param id the item's id
	 */
	public void deleteItem(int id)
	{
		db.deleteItem(id);
	}
		
	/**
	 * editItem - Edits an item in the database
	 */
	public void editItem()
	{
		
	}
		
	/**
     * viewItem - View all information of an item in the database. Retrieves the item's id
     * and calls a method in the DBase class with the given information to complete the
     * transaction
     * @param id the item's id
     * @return returns ... (!!! EDIT)
     * WARNING: THIS INFO SHOULD BE RETURNED, SHOULD NOT PRINT IN DBASE
     */
	public void viewItem(int id)
	{
		db.viewItem(id);
	}
	
	// This method will prompt the database to return all items marked 'retrieved'.
	// WARNING: THIS INFO SHOULD BE RETURNED, SHOULD NOT PRINT IN DBASE
	/**
     * showRetrievedItem - Search and return all items marked as retrieved in the database
     * @return returns ... (!!! EDIT)
     * WARNING: THIS INFO SHOULD BE RETURNED, SHOULD NOT PRINT IN DBASE
     */
	public void showRetrievedItems()
	{
		db.showRetrievedItems();
	}
	
} // End of Class