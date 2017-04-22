package lostandfound.model;

import lostandfound.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import javax.validation.constraints.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Item {
	@JsonSerialize( using = IntToStringSerializer.class )
	public int itemID;
	public String image;
	public String publicDescription;
	public String privateDescription;
	public String locationFound;
	public String category;
	public Date dateReceived;
	public Date dateFound;
	public Date dateRetrieved;
	public String status;
	public ItemRetrieval retrieval;
	
	/**
	 * Creates a new Item object
	 */
	public Item() {
		
	}
	
	/**
	 * Creates a new Item object
	 * @param itemID ID of the item
	 * @param image Path to the image of the item
	 * @param publicDesription Publicly viewable description of the item
	 * @param privateDesription Item information only visible by admin users
	 * @param locationFound Location the item was found at
	 * @param category Category the item belongs to
	 * @param dateReceived Date the item was received
	 * @param dateFound Date the item was found
	 * @param dateRetrieved Date the item was picked up
	 * @param status Status of the item
	 */
	public Item( int itemID, String image, String publicDescription, String privateDescription, String locationFound,
			String category, Date dateReceived, Date dateFound, Date dateRetrieved, String status ) {
		this.itemID = itemID;
		this.image = image;
		this.publicDescription = publicDescription;
		this.privateDescription = privateDescription;
		this.locationFound = locationFound;
		this.category = category.substring( 0, 1 ).toUpperCase() + category.substring(1); // Capitalize
		this.dateReceived = dateReceived;
		this.dateFound = dateFound;
		this.dateRetrieved = dateRetrieved;
		this.status = status;
		this.retrieval = null;
	}

	/**
	 * Creates a new Item object
	 * @param itemID ID of the item
	 * @param image Path to the image of the item
	 * @param publicDesription Publicly viewable description of the item
	 * @param privateDesription Item information only visible by admin users
	 * @param locationFound Location the item was found at
	 * @param category Category the item belongs to
	 * @param dateReceived Date the item was received
	 * @param dateFound Date the item was found
	 * @param dateRetrieved Date the item was picked up
	 * @param status Status of the item
	 * @param retrieval ItemRetrieval object detailing retrieval information
	 */
	public Item( int itemID, String image, String publicDescription, String privateDescription, String locationFound,  
			String category, Date dateReceived, Date dateFound, Date dateRetrieved, String status,
			ItemRetrieval retrieval ) {
		this.itemID = itemID;
		this.image = image;
		this.publicDescription = publicDescription;
		this.privateDescription = privateDescription;
		this.locationFound = locationFound;
		this.category = category.substring( 0, 1 ).toUpperCase() + category.substring(1); // Capitalize
		this.dateReceived = dateReceived;
		this.dateFound = dateFound;
		this.dateRetrieved = dateRetrieved;
		this.status = status;
		this.retrieval = retrieval;
	}
	
	/**
	 * Returns if the item has been retrieved or not
	 * @return If item is retrieved
	 */
	public boolean isRetrieved() {
		return ( retrieval != null );
	}
	
	/**
	 * Adds a retrieval object to the Item
	 * @param retrieval ItemRetrival record object to set 
	 */
	public void setRetrieval( ItemRetrieval retrieval ) {
		this.retrieval = retrieval;
	}
	
	/**
	 * addItem - Adds an item to the database. Retrieves information regarding an item 
	 * and calls a method in the DBase class with the given information to complete
	 * the transaction
     * @param publicDescription publicly viewable description of the item
     * @param privateDescription item information to hide from the public
     * @param locationFound location the item was found
     * @param category category to assign the item to
     * @param status the status of the item ('lost' or 'found')
     * @param dateFound the date of which the item was found
     * @param dateRetrieved the date of which the item was retrieved (if item was found)
     * @param adminId the id of the administrator that is processing this transaction
	 */
	public void addItem(String publicDescription, String privateDescription, String locationFound, String category,
			String status, String image, java.sql.Date dateFound, java.sql.Date dateRetrieved, int adminId)
	{
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.addItem(publicDescription, privateDescription, locationFound, category, status, image, dateFound, dateRetrieved, adminId);
	}
	
	/**
	 * deleteItem - Deletes and item in the database. Retrieves the item ID
	 * @param itemId the ID of the item the user wishes to delete
	 */
	public static void deleteItem(int itemId)
	{
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.deleteItem(itemId);
	}
	
	/**
	 * deleteCategoryIdMap - Deletes the primary key mapping the inventory and inventory_category_map tables
	 * @param itemId the ID of the item to delete
	 */
	public static  void deleteCategoryIdMap(int itemId)
	{
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.deleteCategoryIdMap(itemId);
	}
	
	public static void deleteRetrievalIdMap(int itemId)
	{
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.deleteRetrievalIdMap(itemId);
	}
	
	public static void deleteImageIdMap(int itemId)
	{
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.deleteImageIdMap(itemId);
	}
	
	/**
	 * editItem - Edits an item from the database. Retrieves information from editItemHandler
	 * in ItemController and calls a method in the DBase class with the given information to modify
	 * existing item in database.
	 */
	
	public void editItem(String publicDescription, String privateDescription, String locationFound, String category,
			String status, java.sql.Date dateFound, String image, int adminId, int itemID)
	{
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.editItem(publicDescription, privateDescription, locationFound, category, status, dateFound, image, adminId,itemID);
	}
	
	/**
	 * setItemStatus - Updates the status of an item in the database
	 * @param itemID ID of the item to update
	 * @param status New status to set
	 * @throws Exception if item status cannot be set
	 */
	public static void setItemStatus( Integer itemID, String status ) throws Exception {
		if ( status != "lost" && status != "retrieved" ) throw new Exception( "Invalid item status" );
		
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.setItemStatus( itemID, status );
		db.setItemRetrievalDateToNow( itemID );
		db.close();
	}
	
	/**
     * showRetrievedItem - Search and return all items marked as retrieved in the database
     * @return returns ... (!!! EDIT)
     * WARNING: THIS INFO SHOULD BE RETURNED, SHOULD NOT PRINT IN DBASE
     */
	/*
	public void showRetrievedItems()
	{
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.showRetrievedItems();
	}
	*/
	
	/**
	 * Returns a map of the instance values of the Item and associated ItemRetrieval
	 * @return
	 */
	public Map<String,String> toMap() {
		Map<String,String> map = new HashMap<String,String>();
		
		map.put( "itemID", String.valueOf( itemID ) );
		map.put( "image",  image );
		map.put( "publicDescription", publicDescription );
		map.put( "privateDescription", privateDescription );
		map.put( "locationFound", locationFound );
		map.put( "category", category );
		map.put( "status", status );
		map.put( "dateReceived" , dateReceived.toString() );
		map.put( "dateFound" , dateFound.toString() );
		map.put( "isRetrieved", ( isRetrieved() ) ? "True" : "False" );
		
		if ( isRetrieved() )  {
			map.put( "dateRetrieved" , dateRetrieved.toString() );
			map.putAll( retrieval.toMap() );
		}
		
		return map;
	}
}
