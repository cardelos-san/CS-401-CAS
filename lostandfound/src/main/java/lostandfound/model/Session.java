package lostandfound.model;

import java.util.Map;

import lostandfound.util.Configuration;
import lostandfound.util.DBase;
import spark.Request;
import spark.Response;


public class Session {
	private User user;
	private Integer userID;
	private boolean isGuest;
	
	public Session( Request request, Response response ) {
		userID = Integer.valueOf( request.session().attribute( "userID" ) );
		
		// TODO attempt to load session from cookie
	}
	
	/**
	 * Gets the user object related to the current session
	 * @return
	 * @throws Exception If unable to create a user object from the current session
	 */
	public User getUserFromSession() throws Exception {
		if ( userID == null ) {
			// No user ID set, return guest user
			// TODO: Figure out how to handle guests. Special guest class extending User?
			return new User( 0, "", "Guest", "Guest");
		} else if ( user == null ) {
			// User ID found but no user object yet created. Create object and return it.
			// Set up DBase object
			Configuration config = Configuration.getInstance();
			String dbuser = config.getProperty("dbuser");
			String dbpasswd = config.getProperty("dbpasswd");
			DBase db = new DBase(dbuser, dbpasswd);
			
			Map<String, String> userData = db.getUserData(userID);
			user =  new User( userID, userData.get( "email" ), userData.get( "firstName" ), userData.get( "lastName" ) );
			return user;
		} else {
			// Return already created user object
			return user;
		}
	}
}
