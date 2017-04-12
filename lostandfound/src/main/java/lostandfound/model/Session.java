package lostandfound.model;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;

import lostandfound.util.Configuration;
import lostandfound.util.DBase;
import org.mindrot.jbcrypt.*;
import spark.Request;
import spark.Response;


public class Session {
	private Integer userID;
	private boolean isGuest;
	
	/**
	 * Creates a new Session object
	 * @param request Spark Request object
	 */
	public Session( Request request ) {
		// Get userID from session
		String userIDString = request.session().attribute( "userID" );
		if ( userIDString != null ) {
			userID = Integer.valueOf( userIDString );
		}
			
		if ( userID == null ) {
			// Get userID from hashed cookie
			String hash = request.cookie( "userHash" );
			Configuration config = Configuration.getInstance();
			String dbuser = config.getProperty("dbuser");
			String dbpasswd = config.getProperty("dbpasswd");
			DBase db = new DBase( dbuser, dbpasswd );
			
			try {
				userID = db.getUserIDFromCookieHash( hash );
			} catch ( SQLException e ) {
				// Log exception
			} finally {
				db.close();
			}
		}
		
		isGuest = ( userID == null );
	}
	
	/**
	 * Gets the user object related to the current session or null if no user is logged in
	 * @return User object
	 * @throws Exception If unable to create a user object from the current session
	 */
	public int getUserID() throws Exception {
		return userID;
	}
	
	/**
	 * Sets a userID into the current session.
	 * Only do this if the user has been successfully authenticated.
	 * @param userID
	 */
	public void setUserID( int userID ){
		this.userID = userID;
	}
	
	/**
	 * Determines if the current user is a guest
	 * @return True if user is guest, false if not
	 */
	public boolean isGuest() {
		return isGuest;
	}
	
	/**
	 * Sets a cookie in the response to store the user login
	 * @param response
	 * @throws NoSuchAlgorithmException, GeneralSecurityException
	 */
	public void setCookieFromSession( Response response )
			throws Exception, NoSuchAlgorithmException, GeneralSecurityException {
		// Days until cookie expires
		// TODO make this configurable
		int expirationDays = 30;
		// Create random hash
		MessageDigest sha256 = MessageDigest.getInstance( "SHA256" );
		SecureRandom random = new SecureRandom();
		String hash = sha256.digest( random.generateSeed( 130 ) ).toString();
		
		// Create a maximum age for the cookie
		LocalDate expiration = LocalDate.now().plusDays( 30 );
		int maxAge = 86400 * expirationDays;
		
		// Store random hash in the DB
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase( dbuser, dbpasswd );
		
		try {
			db.setCookieHashForUser( hash, userID, expiration );
			response.cookie( "userID", hash, maxAge, true );
		} catch ( Exception e ) {
			throw new Exception( "Unable to create new session cookie in database: " + e.getMessage() );
		} finally {
			db.close();
		}
	}
	
	/**
	 * Authenticates a user using their user/pass combo 
	 * @param email Email of user to log in
	 * @param passwd Plaintext password supplied by the user
	 * @return If authentication is successful or not
	 * @throws Exception 
	 */
	public boolean authenticate( String email, String passwd ) throws Exception {
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		DBase db = new DBase( dbuser, dbpasswd );
		String hash = "";
		
		try {
			hash = db.getAuthenticationHash( "email" );
		} catch ( SQLException e ) {
			throw new Exception ( "Unable to retrieve user authentication hash from database: " + e.getMessage() );
		} finally {
			db.close();
		}
		
		if ( hash.isEmpty() ) {
			return false;
		} else {
			return BCrypt.checkpw( passwd, hash );
		}
	}
}
