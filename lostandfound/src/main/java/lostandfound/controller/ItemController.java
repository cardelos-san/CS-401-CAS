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
		
		Session session = new Session( req );
		int userID = session.getUserID();
		
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
				newItem.addItem(publicDescription, privateDescription, locationFound,
						category, status, date, null, userID);
				
				//parse dateCreated, adminId = need to grab account
				//public void addItem(String description, String status,
				//java.sql.Date dateFound, java.sql.Date dateRetrieved, int adminId)
				//then Redirect user to index
					//res.redirect( "/--> adminView" );
			//else...
				//give error messages to user
		
		return new ModelAndView( templateVars, "addItemForm" );
	}
	
	
	public static ModelAndView retrieveItem( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String template = "retrieveItemForm";
		Item item = null;
		
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty( "dbuser" );
		String dbpasswd = config.getProperty( "dbpasswd" );
		DBase db = new DBase( dbuser, dbpasswd );
		
		try {
			item = db.getItem( itemID );
		} catch ( Exception e ) {
			//TODO Log exception
		}
		
		// TODO: Check if item has been retrieved already
		
		if ( item != null ) {
			templateVars = item.toMap();
		} else {
			templateVars.put( "error", "No such item ID." );
			template = "error";
		}
		
		return new ModelAndView( templateVars, template );
	}
	
	
	public static ModelAndView retrieveItemHandler( Request req, Response resp ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String template = "retrieveItemForm";
		
		String firstName = req.queryParams( "retrievalFirstName" );
		String lastName = req.queryParams( "retrievalLastName" );
		String email = req.queryParams( "retrievalEmail" );
		String phone = req.queryParams( "retrievalPhone" );
		String ident = req.queryParams( "retrievalIdent" );
		
		try {
			ItemRetrieval.addItemRetrieval( itemID, firstName, lastName, email, phone, ident );
			Item.setItemStatus( itemID, "retrieved" );
		} catch ( Exception e ) {
			// TODO: Log exception
		}
		
		return new ModelAndView( templateVars, template );
	}
	
	public static ModelAndView editItem( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String template = "editItemForm";
		Item item = null;
		
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty( "dbuser" );
		String dbpasswd = config.getProperty( "dbpasswd" );
		DBase db = new DBase( dbuser, dbpasswd );
		
		try {
			item = db.getItem( itemID );
		} catch ( Exception e ) {
			//TODO Log exception
		}
		
		// TODO: Check if item has been retrieved already
		
		if ( item != null ) {
			templateVars = item.toMap();
		} else {
			templateVars.put( "error", "No such item ID." );
			template = "error";
		}
		
		return new ModelAndView( templateVars, template );
	}
	
	/*editItemHandler handles edit request on item
	 * @WARNING: Need to get adminID from user session. Currently using hard-coded adminID.
	 */
	
	public static ModelAndView editItemHandler(Request req, Response resp){
		
		Session session = new Session( req );
		int userID = session.getUserID();
		
		//itemPic
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String publicDescription = req.queryParams("itemDescriptionPublic");
		String privateDescription = req.queryParams("itemDescriptionPrivate");
		String locationFound = req.queryParams("itemLocationFound");
		String category = req.queryParams("category");
		String dateFoundString = req.queryParams("itemDateFound");
		String status = req.queryParams("status");
		Date date = Date.valueOf(dateFoundString);
		
		
		Item editedItem = new Item ();
		editedItem.editItem(publicDescription, privateDescription, locationFound,
		category, status, date, userID, itemID);
		
		resp.redirect("/");
		
		return null;
		
	}
	
}
