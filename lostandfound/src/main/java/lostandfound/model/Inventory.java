package lostandfound.model;

import lostandfound.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Inventory {
	
	/*Inventory class will model inventory object for the templating engine*/
	DBase database = new DBase("String username", "String password");
	
	
	/*Need to modify getInventory() method to return a ResultSet object
	 *that will be converted to a JSON object*/
	
	/*public void getInventory(){
		
		try{database.requestTable();}
		catch(Exception e){}
		
	}*/
}
