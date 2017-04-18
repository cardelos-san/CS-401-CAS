package lostandfound.model;

import lostandfound.util.*;
import java.util.Date;
import javax.validation.constraints.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Item {
	private int itemID;
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
		this.category = category;
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
		this.category = category;
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
	 * Gets the item ID number
	 * @return Item ID
	 */
	@JsonSerialize( using = IntToStringSerializer.class )
	public int getItemID() {
		return itemID;
	}
	
	/**
	 * addItem - Adds an item to the database. Retrieves information regarding an item 
	 * and calls a method in the DBase class with the given information to complete
	 * the transaction
     * @param description a description of the item
     * @param status the status of the item ('lost' or 'found')
     * @param dateFound the date of which the item was found
     * @param dateRetrieved the date of which the item was retrieved (if item was found)
     * @param adminId the id of the administrator that is processing this transaction
	 */
	public void addItem(String description, String status,
			java.sql.Date dateFound, java.sql.Date dateRetrieved, int adminId)
	{
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.addItem(description, status, dateFound, dateRetrieved, adminId);
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
}
