package lostandfound.model;

import lostandfound.util.*;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Item {
	private int itemID;
	public String image;
	public String description;
	public String category;
	public Date dateReceived;
	public Date dateFound;
	public Date dateRetrieved;
	public String status;
	public ItemRetrieval retrieval;
	
	/**
	 * Creates a new Item object
	 * @param itemID ID of the item
	 * @param image Path to the image of the item
	 * @param desription Description of the item
	 * @param category Category the item belongs to
	 * @param dateReceived Date the item was received
	 * @param dateFound Date the item was found
	 * @param dateRetrieved Date the item was picked up
	 * @param status Status of the item
	 */
	public Item( int itemID, String image, String description, String category,
			Date dateReceived, Date dateFound, Date dateRetrieved, String status ) {
		this.itemID = itemID;
		this.image = image;
		this.description = description;
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
	 * @param desription Description of the item
	 * @param category Category the item belongs to
	 * @param dateReceived Date the item was received
	 * @param dateFound Date the item was found
	 * @param dateRetrieved Date the item was picked up
	 * @param status Status of the item
	 * @param retrieval ItemRetrieval object detailing retrieval information
	 */
	public Item( int itemID, String image, String description, String category,
			Date dateReceived, Date dateFound, Date dateRetrieved, String status,
			ItemRetrieval retrieval ) {
		this.itemID = itemID;
		this.image = image;
		this.description = description;
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
}
