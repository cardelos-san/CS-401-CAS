package lostandfound.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
}
