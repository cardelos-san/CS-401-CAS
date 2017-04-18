package lostandfound.controller;

import lostandfound.model.*;
import lostandfound.util.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ItemController {
	
	public static List<Item> getAllItems( Request req, Response resp ) {
		Configuration config = Configuration.getInstance();
		DBase db = new DBase( config.getProperty( "dbuser" ), 
				config.getProperty( "dbpasswd" ) );
		List<Item> items = db.getAllItems();
		
		return items;
	}
	
	/*
	public static List<Item> getRetrievedItems( Request req, Response resp) {
		Configuration config = Configuration.getInstance();
		DBase db = new DBase( config.getProperty( "dbuser" ), 
				config.getProperty( "dbpasswd" ) );
		List<Item> retrievedItems = db.showRetrievedItems();
		
		return retrievedItems;
	}
	*/
	
	public static ModelAndView addAnItem( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		return new ModelAndView( templateVars, "addItemForm" );
	}
	
	public static ModelAndView addItemHandler( Request req, Response res ){
		Map<String, String> templateVars = new HashMap<String, String>();
		
		//itemPic
		String publicDescription = req.queryParams("itemDescriptionPublic");
		String privateDescription = req.queryParams("itemDescriptionPrivate");
		String locationFound = req.queryParams("itemLocationFound");
		String category = req.queryParams("category");
		String dateFoundString = req.queryParams("itemDateFound");
		String status = req.queryParams("status");
		//System.out.println("ID: " + itemId);
		//System.out.println("Status: " + status);
		//System.out.println("Description: " + description);
		//System.out.println("Date: " + dateCreatedString);
		//System.out.println("Category: " + category);
		Date date = Date.valueOf(dateFoundString);
		//System.out.print(date);
		
		
		//Are all these values valid??
		
			//if so... 
				Item newItem = new Item ();
				newItem.addItem(publicDescription, privateDescription, locationFound, status, date, null, 1);
				
				//parse dateCreated, adminId = need to grab account
				//public void addItem(String description, String status,
				//java.sql.Date dateFound, java.sql.Date dateRetrieved, int adminId)
				//then Redirect user to index
					//res.redirect( "/--> adminView" );
			//else...
				//give error messages to user
		
		return new ModelAndView( templateVars, "addItemForm" );
	}
	
}
