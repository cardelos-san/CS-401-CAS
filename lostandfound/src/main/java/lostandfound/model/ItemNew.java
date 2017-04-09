package lostandfound.model;

import lostandfound.util.*;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ItemNew {
	@JsonSerialize( using = IntToStringSerializer.class )
	public int itemID;
	public String image;
	public String description;
	public String category;
	public Date dateReceived;
	public String status;
	
	public ItemNew( int itemID, String image, String description, String category, Date dateReceived, String status ) {
		this.itemID = itemID;
		this.image = image;
		this.description = description;
		this.category = category;
		this.dateReceived = dateReceived;
		this.status = status;
	}
}
