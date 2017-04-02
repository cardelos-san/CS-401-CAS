package lostandfound.model;

//import java.sql.SQLException;
import lostandfound.util.*;
import java.util.*;

public class Item {

	Scanner kbd = new Scanner(System.in);
  DBase db = new DBase("root", "Kpopfeen96!!");
  String cmd;
	
	public String checkDBconnection(){
		//If not open -- not connected to database
		if (!db.isOpen()) {
          return "Could not connect to database.%n";
          //GET COMMENT FOR THIS EXIT
          //System.exit(1);
      }
		// Connected to database
		else return "";
	}
	
	public void addItem(int id, String description, String status,
			java.sql.Date dateFound, java.sql.Date dateRetrieved, int adminId)
	{
		db.addItem(id, description, status, dateFound, dateRetrieved, adminId);
	}
	
	public void getCurrentDate(){
		
	}
	
}