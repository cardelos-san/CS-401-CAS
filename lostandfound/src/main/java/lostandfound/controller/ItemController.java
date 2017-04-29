package lostandfound.controller;

import lostandfound.model.*;
import lostandfound.util.*;

import java.io.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ItemController {
	
	final static Logger logger = LoggerFactory.getLogger( ItemController.class );
	
	
	public static List<Item> getAllItems( Request req, Response resp ) throws Exception {
		Configuration config = Configuration.getInstance();
		DBase db = new DBase( config.getProperty( "dbuser" ), 
				config.getProperty( "dbpasswd" ) );
		List<Item> items = db.getAllItems();
		
		return items;
	}
	
	public static ModelAndView addAnItem( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		String template = "addItemForm";
		int userID = 0;
		
		// Authorization handling
		Session session = new Session( req );
		try {
			userID = session.getUserID();
		} catch ( Exception e ) {
			logger.error( "Unauthorized access: " +  e.getMessage() );
		}
		
		// User is not logged-in as Administrator
		if (userID == 0){
			templateVars.put( "error", "Unauthorized access" );
			template = "error";
		}
		
		return new ModelAndView( templateVars, template );
	}
	
	
	public static ModelAndView addItemHandler( Request req, Response res ) throws Exception {
		Map<String, String> templateVars = new HashMap<String, String>();
		Configuration config = Configuration.getInstance();
		String template = "addItemForm";
		
		Session session = new Session( req );
		int userID = session.getUserID();
		
		// Image handling
		File itemImageDir = new File( config.getProperty( "itemimages", "public/images/items" ) );
		String imageName = ImageFile.copyImageUploadToFile( req, itemImageDir );
		
		// Grab other item details from POST data
		String publicDescription = req.queryParams("itemDescriptionPublic");
		String privateDescription = req.queryParams("itemDescriptionPrivate");
		String locationFound = req.queryParams("itemLocationFound");
		String category = req.queryParams("category");
		String dateFoundString = req.queryParams("itemDateFound");
		String status = req.queryParams("status");
		
		Date date = Date.valueOf(dateFoundString);
		
		Item newItem = new Item();
		newItem.addItem(publicDescription, privateDescription, locationFound,
						category, status, imageName, date, null, userID);
				
		// Redirect user to admin index
		res.redirect( "/" );
		
		return new ModelAndView( templateVars, template );
	}
	
	
	public static ModelAndView retrieveItem( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String template = "retrieveItemForm";
		Item item = null;
		int userID = 0;
		
		// Authorization handling
		Session session = new Session( req );		
		try {
			userID = session.getUserID();
		} catch ( Exception e ) {
			logger.error( "Unauthorized access: " +  e.getMessage() );
		}
		
		// User is not logged-in as Administrator
		if (userID == 0){
			templateVars.put( "error", "Unauthorized access" );
			template = "error";
		}
		
		// User is logged-in as Administrator
		else{
			Configuration config = Configuration.getInstance();
			String dbuser = config.getProperty( "dbuser" );
			String dbpasswd = config.getProperty( "dbpasswd" );
			DBase db = new DBase( dbuser, dbpasswd );
			
			try {
				item = db.getItem( itemID );
			} catch ( Exception e ) {
				logger.error( "Unable to fetch item with ID '" + itemID + "': " +  e.getMessage() );
				templateVars.put( "error", "No such item ID." );
				template = "error";
			}
			
			// Check if item has already been retrieved
			if (item != null) {
				if (item.getItemStatus().equals("retrieved")) {
					templateVars.put( "error", "Item has already been retrieved.");
					template = "error";
				} else {
					templateVars = item.toMap();
				}
			}
		}
		
		return new ModelAndView( templateVars, template );
	}
	
	
	public static ModelAndView retrieveItemHandler( Request req, Response res ) {
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
			logger.error( "Unable to process retrieval for item with ID '" + itemID + "': " +  e.getMessage() );
		}
		
		res.redirect( "/" );
		return new ModelAndView( templateVars, template );
	}
	

	public static ModelAndView deleteItem( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String template = "deleteItemForm";
		Item item = null;
		int userID = 0;
		
		// Authorization handling
		Session session = new Session( req );		
		try {
			userID = session.getUserID();
		} catch ( Exception e ) {
			logger.error( "Unauthorized access: " +  e.getMessage() );
		}
		
		// User is not logged-in as Administrator
		if (userID == 0){
			templateVars.put( "error", "Unauthorized access" );
			template = "error";
		}
		
		// User is logged-in as Administrator
		else{
			Configuration config = Configuration.getInstance();
			String dbuser = config.getProperty( "dbuser" );
			String dbpasswd = config.getProperty( "dbpasswd" );
			DBase db = new DBase( dbuser, dbpasswd );
			
			try {
				item = db.getItem( itemID );
			} catch ( Exception e ) {
				logger.error( "Unable to fetch item with ID '" + itemID + "': " +  e.getMessage() );
			}
			
			if ( item != null ) {
				templateVars = item.toMap();
			} else {
				templateVars.put( "error", "No such item ID." );
				template = "error";
			}
		}
		
		return new ModelAndView( templateVars, template );
	}
	
	
	public static ModelAndView deleteItemHandler( Request req, Response res ) throws Exception {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		Item item = null;
		String template = "deleteItemForm";
		
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty( "dbuser" );
		String dbpasswd = config.getProperty( "dbpasswd" );
		DBase db = new DBase( dbuser, dbpasswd );
		
		File itemImageDir = new File( config.getProperty( "itemimages", "public/images/items" ) );
		
		try {
			item = db.getItem( itemID );
			Item.deleteCategoryIdMap ( itemID );
			Item.deleteRetrievalIdMap (itemID);
			Item.deleteImageIdMap ( itemID);
			Item.deleteItem( itemID );
			ImageFile.deleteImageFile( item.image, itemImageDir );
		} catch ( Exception e ) {
			logger.error( "Unable to delete item with ID " + itemID, e );
			throw e;
		}
		
		// Redirect user to admin index
		res.redirect( "/" );
		
		return new ModelAndView( templateVars, template );
	}
	
	
	public static ModelAndView editItem( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String template = "editItemForm";
		Item item = null;
		int userID = 0;
		
		// Authorization handling
		Session session = new Session( req );		
		try {
			userID = session.getUserID();
		} catch ( Exception e ) {
			logger.error( "Unauthorized access: " +  e.getMessage() );
		}
		
		// User is not logged-in as Administrator
		if (userID == 0){
			templateVars.put( "error", "Unauthorized access" );
			template = "error";
		}
		
		// User is logged-in as Administrator
		else{
			Configuration config = Configuration.getInstance();
			String dbuser = config.getProperty( "dbuser" );
			String dbpasswd = config.getProperty( "dbpasswd" );
			DBase db = new DBase( dbuser, dbpasswd );
			
			try {
				item = db.getItem( itemID );
			} catch ( Exception e ) {
				logger.error( "Unable to fetch item with ID '" + itemID + "': " +  e.getMessage() );
			}
			
			if ( item != null ) {
				templateVars = item.toMap();
			} else {
				templateVars.put( "error", "No such item ID." );
				template = "error";
			}
		}
		
		return new ModelAndView( templateVars, template );
	}
	
	/*editItemHandler handles edit request on item
	 * @WARNING: Need to get adminID from user session. Currently using hard-coded adminID.
	 */
	
	public static ModelAndView editItemHandler(Request req, Response resp) throws Exception {
		Configuration config = Configuration.getInstance();
		Session session = new Session( req );
		int userID = session.getUserID();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		
		// Image handling
		File itemImageDir = new File( config.getProperty( "itemimages", "public/images/items" ) );
		String newImageName = ImageFile.copyImageUploadToFile( req, itemImageDir );
		
		// Get previous item data
		String dbuser = config.getProperty( "dbuser" );
		String dbpasswd = config.getProperty( "dbpasswd" );
		DBase db = new DBase( dbuser, dbpasswd );
		Item previousItem = db.getItem( itemID );
		
		// Determine if we have a new image or should keep the old one
		boolean newImageUploaded = ( newImageName != null );
		String image = ( newImageUploaded ) ? newImageName : previousItem.image;
		
		//itemPic
		String publicDescription = req.queryParams("itemDescriptionPublic");
		String privateDescription = req.queryParams("itemDescriptionPrivate");
		String locationFound = req.queryParams("itemLocationFound");
		String category = req.queryParams("category");
		String dateFoundString = req.queryParams("itemDateFound");
		String status = req.queryParams("status");
		Date date = Date.valueOf(dateFoundString);
		
		Item editedItem = new Item ();
		editedItem.editItem(publicDescription, privateDescription, locationFound,
		category, status, date, image, userID, itemID);
		
		if ( newImageUploaded ) ImageFile.deleteImageFile( previousItem.image, itemImageDir );
		
		resp.redirect("/");
		
		return null;
	}
	
	/*viewItem gets item data and retrieval data associated with item
	*/
	
	public static ModelAndView viewItem( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String template = "viewItemForm";
		Item item = null;
		int userID = 0;
		
		// Authorization handling
		Session session = new Session( req );		
		try {
			userID = session.getUserID();
		} catch ( Exception e ) {
			logger.error( "Unauthorized access", e );
		}
		
		// User is not logged-in as Administrator
		if (userID == 0){
			templateVars.put( "error", "Unauthorized access" );
			template = "error";
		}
		
		// User is logged-in as Administrator
		else{
			Configuration config = Configuration.getInstance();
			String dbuser = config.getProperty( "dbuser" );
			String dbpasswd = config.getProperty( "dbpasswd" );
			DBase db = new DBase( dbuser, dbpasswd );
			
			try {
				item = db.getItem( itemID );
			} catch ( Exception e ) {
				logger.error( "Unable to retrieve item with ID " + itemID, e );
			}
			
			if ( item != null ) {
				templateVars = item.toMap();
			} else {
				templateVars.put( "error", "No such item ID." );
				template = "error";
			}
		}
		
		return new ModelAndView( templateVars, template );
	}
	
}
