package lostandfound.model;

import lostandfound.util.*;
import org.mindrot.jbcrypt.*;

public class User {
	private int id;
	private String email;
	private String firstName;
	private String lastName;
	
	public User( int id, String email, String firstName, String lastName ) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * Get's the user ID number
	 * @return ID number
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Gets the user's email address as a string
	 * @return Email address
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Gets the user's first name as a string
	 * @return First name
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Gets the user's last name as a string
	 * @return Last name
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * Adds a new user to the database
	 * @param email User's email
	 * @param passwd User's unhashed password
	 * @param role User's security role
	 * @param firstName User's first name
	 * @param lastName User's last name
	 */
	public static void addUser(String email, String passwd, String role,
			String firstName, String lastName) {
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		
		String hashedPasswd = BCrypt.hashpw( passwd, BCrypt.gensalt( 12 ) );
		
		DBase db = new DBase( dbuser, dbpasswd );
		db.addUser(email, hashedPasswd, role, firstName, lastName);
	}
	
}
