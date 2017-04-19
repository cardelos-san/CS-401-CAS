package lostandfound.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lostandfound.util.Configuration;
import lostandfound.util.DBase;
import lostandfound.util.IntToStringSerializer;

public class ItemRetrieval {
	@JsonSerialize( using = IntToStringSerializer.class )
	public Integer retrievalID;
	public String firstName;
	public String lastName;
	public String email;
	public String phone;
	public String identification;
	
	/**
	 * Creates an ItemRetrieval object.
	 * @param retrievalID ID of the retrieval record.
	 * @param firstName First name of the person who retrieved the object.
	 * @param lastName Last name of the person who retrieved the object.
	 * @param email Email of the person who retrieved the object.
	 * @param phone Phone number of the person who retrieved the object.
	 * @param identification ID of the person who retrieved the object
	 */
	public ItemRetrieval( Integer retrievalID, String firstName, String lastName,
			String email, String phone, String identification ) {
		this.retrievalID = retrievalID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.identification = identification;
	}
	
	/**
	 * addItemRetrieval - Adds an item retrieval record
	 * @param itemID ID of item being retrieved
	 * @param firstName First name of person retrieving the item
	 * @param lastName Last name of person retrieving the item
	 * @param email Email address of person retrieving the item
	 * @param phone Phone number of person retrieving the item
	 * @param identification Identification of person retrieving the item
	 * @throws Exception if unable to add record to database
	 */
	public static void addItemRetrieval( Integer itemID, String firstName, String lastName, String email,
			String phone, String identification ) throws Exception {
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase(dbuser, dbpasswd);
		
		db.addItemRetrieval( itemID, firstName, lastName, email, phone, identification );
		db.close();
	}
}
