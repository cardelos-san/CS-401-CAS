package lostandfound.controller;

import lostandfound.model.*;
import lostandfound.util.*;

import java.io.*;
import java.nio.file.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ItemController {
	
	final static Logger logger = LoggerFactory.getLogger( ItemController.class );
	
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
	
	public static ModelAndView addItemHandler( Request req, Response res ) throws Exception {
		Map<String, String> templateVars = new HashMap<String, String>();
		Configuration config = Configuration.getInstance();
		String template = "addItemForm";
		//GET USER INFO: user.getID();
		//int userId;
		
		Session session = new Session( req );
		int userID = session.getUserID();
		
		// Image handling
		File itemImageDir = new File( config.getProperty( "itemimages", "public/images/items" ) );
		String imageName = handleImageUpload( req, itemImageDir );
		
		// Grab other item details from POST data
		String publicDescription = req.queryParams("itemDescriptionPublic");
		String privateDescription = req.queryParams("itemDescriptionPrivate");
		String locationFound = req.queryParams("itemLocationFound");
		String category = req.queryParams("category");
		String dateFoundString = req.queryParams("itemDateFound");
		String status = req.queryParams("status");
		
		Date date = Date.valueOf(dateFoundString);
		/*
		// DATE VALIDATION IS PROBABLY BETTER IN HTML FORM!
		// Get today's date
		LocalDate todaysDate = LocalDate.now();
		// Convert it to a String
		String todaysDateString = todaysDate.toString();
		// Convert it to Date object
		Date today = Date.valueOf(todaysDateString);
		
		// Compare dateFound to today's date: If date (found) is before today's Date
		if(date.compareTo(today) < 0)
		{ */
		

		
		//Are all these values valid??
		
			//if so... 
				Item newItem = new Item();
				newItem.addItem(publicDescription, privateDescription, locationFound,
						category, status, imageName, date, null, userID);
				
			// Redirect user to admin index
			res.redirect( "/" );
		/*}
		else
		{
			// Let user know dateFound is invalid
			templateVars.put( "error", "Invalid 'Date Found' entry." );
			template = "error";
		} */
		
		return new ModelAndView( templateVars, template );
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
			// TODO: Log exception
		}
		
		res.redirect( "/" );
		return new ModelAndView( templateVars, template );
	}
	

	public static ModelAndView deleteItem( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String template = "deleteItemForm";
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
	
	public static ModelAndView deleteItemHandler( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		int itemID = Integer.valueOf( req.params( ":itemID" ) );
		String template = "deleteItemForm";
		
		try {
			Item.deleteCategoryIdMap ( itemID );
			Item.deleteRetrievalIdMap (itemID);
			Item.deleteImageIdMap ( itemID);
			Item.deleteItem( itemID );
		} catch ( Exception e ) {
			// TODO: Log exception
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
	
	
	/**
	 * handleImageUpload - Handles copying an uploaded image to a file and returns
	 * the filename or null if nothing was uploaded.
	 * @param req Request object
	 * @param itemImageDir Directory in which to create image
	 * @return Name of the created file or null if no file was uploaded
	 * @throws ServletException if unable to get part from servlet
	 * @throws IOException if unable to create new file on filesystem
	 */
	private static String handleImageUpload( Request req, File itemImageDir ) throws IOException, ServletException {
		String imageName = null;
		req.attribute( "org.eclipse.jetty.multipartConfig", new MultipartConfigElement( "/temp" ) );
		Part itemPart = req.raw().getPart( "itemPic" );
		String uploadName = SparkUploadFilename.getFileName( itemPart );
		if ( uploadName != null && !uploadName.isEmpty() ) {
			String extension = uploadName.substring( uploadName.lastIndexOf( '.' ) );
			imageName = System.nanoTime() + extension;
			Path imageFile = Paths.get( itemImageDir.toString(), imageName );
			try ( InputStream input = itemPart.getInputStream() ) {
				Files.copy( input, imageFile );
			}
			logger.info( "Copied uploaded file: " + imageFile.toAbsolutePath() );
		}
		
		return imageName;
	}
	
}
