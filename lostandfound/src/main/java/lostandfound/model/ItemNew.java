package lostandfound.model;

import java.util.Date;

public class ItemNew {
	public String itemID;
	public String image;
	public String description;
	public String category;
	public String dateReceived;
	public String status;
	
	public ItemNew( String itemID, String image, String description, String category, String dateReceived, String status ) {
		this.itemID = itemID;
		this.image = image;
		this.description = description;
		this.category = category;
		this.dateReceived = dateReceived;
		this.status = status;
	}
}
