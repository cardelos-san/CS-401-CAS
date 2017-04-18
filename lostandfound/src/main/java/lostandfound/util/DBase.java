/*	Class Name: DBase
 *********************************************************************
 *  This class uses JDBC to communicate with a JavaDB database of
 *  Lost and Found data. The driver needed to make the connection
 *  is already loaded from the 'lib' folder. The constructor connects 
 *  to the database, the
 *  ..... (FINISH AS GO ALONG WITH THE CLASS).
 */

package lostandfound.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import lostandfound.model.*;

import java.sql.*; //Allows you to use JDBC classes/interfaces
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;
import org.json.JSONException;
public class DBase {

	private Connection conn;
    private boolean isopen;
    
    /**
     * DBase - Attempts to connect to the lost_and_found database
     * @param uname	the username the database is locked with
     * @param pword	the password the database is locked with
     */
    public DBase(String uname, String pword) 
    {
        try {
            //Connect to the database
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/lost_and_found", uname, pword);
        } catch (Exception e) {conn = null;}
        isopen = (conn != null);
    }
	
    /**
     * isOpen - Tests whether the database is open or closed
     * @return returns the boolean value to determine if the database
     * is open or closed
     */
    public boolean isOpen() {return isopen;}
	
    /**
     * close - Closes the database connection
     * @return returns to the calling method if the database is not open
     */
    public void close() 
    {
        if (!isopen) return;
        try {conn.close();}
        catch (Exception e) {}
        isopen = false;
        conn = null; // Clean up before clearing
    }
	
    /**
     * addItem - Adds an item to the database
     * @param description a description of the item
     * @param status the status of the item ('lost' or 'found')
     * @param dateFound the date of which the item was found
     * @param dateRetrieved the date of which the item was retrieved (if item was found)
     * @param adminId the id of the administrator that is processing this transaction
     */
    public void addItem(String publicDescription, String privateDescription, String locationFound, String status,
    		java.sql.Date dateFound, java.sql.Date dateRetrieved, int adminId)
    {
    	PreparedStatement stmt = null;
    	String sql;
    	
    	// Return if the database is closed.
    	if (!isopen) return;
    	
    	try{
    		// Create a statement for the update
    		sql = "INSERT INTO inventory (" + 
    				"description_public, description_private, location_found, status, " +
    				"date_found, date_retrieved, added_by_user) VALUES (?, ?, ?, ?, ?, ?, ?)";
    		stmt = conn.prepareStatement(sql);
    	
    		// Set the parameters in the statement
    		stmt.setString(1, publicDescription);
    		stmt.setString(2, privateDescription);
    		stmt.setString(3, locationFound);
    		stmt.setString(4, status);
    		stmt.setDate(5, dateFound);
    		stmt.setDate(6, dateRetrieved);
    		stmt.setInt(7, adminId);
    	
    		// Execute SQL Update
    		stmt.executeUpdate();
        
    	} catch (Exception e) {}
    	
    	// Close the update statement and return
    	try {stmt.close();}
    	catch (Exception e) {}
    }
    
    /**
     * deleteItem - Deletes an item in the database
     * @param id the item's id
     */
    public void deleteItem(int id)
    {
    	PreparedStatement stmt = null;
        String sql;
        
        // Return if the database is closed.
        if (!isopen) return;
        
        try {
            // Create a PreparedStatement for the update.
            sql = "DELETE FROM inventory WHERE item_id = ?";
            stmt = conn.prepareStatement(sql);

            // Set the parameters in the statement
            stmt.setInt(1, id);

            // Execute SQL Update
            stmt.executeUpdate();     
        } catch (Exception e) {}
        
        // Close the update statement and return.
        try {stmt.close();}
        catch (Exception e) {}
    }
    
    /**
     * editItem - Edits an item in the database
     */
    public void editItem()
    {
    	
    }
    
    /**
     * viewItem - View all information of an item in the database
     * @param id the item's id
     * @return returns ... (!!! EDIT)
     * WARNING: THIS INFO SHOULD BE RETURNED, SHOULD NOT PRINT IN DBASE
     * NEED TO RETURN MORE STUFF!!! -- Damin will view have this view
     * it should also return info about the retriever
     */
    public void viewItem(int id)
    {
    	PreparedStatement stmt = null;
    	ResultSet rset = null; // result - gets returned
        String sql, description, status;
        int itemId, adminId;
		java.sql.Date dateCreated, dateFound, dateRetrieved;
        
        // Return if the database is closed.
        if (!isopen) return;
        
        try {
            // Create a PreparedStatement for the update.
            sql = "SELECT * FROM inventory WHERE inventory.item_id = ?";
            stmt = conn.prepareStatement(sql);

            // Set the parameters in the statement
            stmt.setInt(1, id);

            // Execute SQL Update
            rset = stmt.executeQuery();
            
            // Process the result set
            // Print the tag names
            //System.out.println("");
            //System.out.println("ID\tDescription\tStatus\tDate Created\t" +
            		//"Date Found\ttDate Retrieved\tAdded By User\n");
            // Loop through the result and print
            while (rset.next()) {
                itemId = rset.getInt(1);
                description = rset.getString(2);
                status = rset.getString(3);
                dateCreated = rset.getDate(4);
                dateFound = rset.getDate(5);
                dateRetrieved = rset.getDate(6);
                adminId = rset.getInt(7);
                
                // WARNING: THIS INFO SHOULD BE RETURNED, SHOULD NOT PRINT HERE
               
            }

        } catch (Exception e) {}
        
        // Close the update statement and return.
        try {stmt.close();}
        catch (Exception e) {}
    }
    
    /**
     * viewAllItem - View all information of an item in the database
     * @return returns a list of all the items in the database
     */
    public List<Item> getAllItems()
    {
    	PreparedStatement stmt = null;
    	ResultSet rset = null;
        String sql, publicDescription, privateDescription, locationFound, status;
        int itemId, adminId;
		java.sql.Date dateCreated, dateFound, dateRetrieved;
		List<Item> items = new ArrayList<Item>();
        
        // Return if the database is closed.
        if (!isopen) return null;
        
        try {
            // Create a PreparedStatement for the update.
            sql = "SELECT * FROM inventory";
            stmt = conn.prepareStatement(sql);

            // Execute SQL Update
            rset = stmt.executeQuery();
            
            while (rset.next()) {
                itemId = rset.getInt( "item_id" );
                publicDescription = rset.getString( "description_public" );
                privateDescription = rset.getString( "description_private" );
                locationFound = rset.getString( "location_found" );
                status = rset.getString( "status" );
                dateCreated = rset.getDate( "date_created" );
                dateFound = rset.getDate( "date_found" );
                dateRetrieved = rset.getDate( "date_retrieved" );
                adminId = rset.getInt( "added_by_user" );
                items.add( new Item( itemId, "", publicDescription, privateDescription, locationFound,
                		"", dateCreated, dateFound, dateRetrieved, status ) );
            }

        } catch (Exception e) {}
        
        // Close the update statement and return.
        try {stmt.close();}
        catch (Exception e) {}
        
        return items;
    }
    
    /**
     * showRetrievedItem - Search and return all items marked as retrieved in the database
     * @return returns a list of all retrieved items in the database
     */
    /*
    public List<Item> showRetrievedItems()
    {
    	PreparedStatement stmt = null;
    	ResultSet rset = null; // result - gets returned
        String sql, description, status;
        int itemId, adminId;
		java.sql.Date dateCreated, dateFound, dateRetrieved;
		List<Item> retrievedItems = new ArrayList<Item>();
        
        // Return if the database is closed.
        if (!isopen) return null;
    	
    	try{	
        	// Create a PreparedStatement for the update.
        	sql = "SELECT * FROM inventory WHERE inventory.status = 'Retrieved'";
        	stmt = conn.prepareStatement(sql);
        
        	// Execute SQL Update
        	rset = stmt.executeQuery();
        	
        	// Process the result set
            // Loop through the result and print
            while (rset.next()) {
                itemId = rset.getInt(1);
                description = rset.getString(2);
                status = rset.getString(3);
                dateCreated = rset.getDate(4);
                dateFound = rset.getDate(5);
                dateRetrieved = rset.getDate(6);
                adminId = rset.getInt(7);
                
                retrievedItems.add(new Item (itemId, "", description, "", dateCreated, status));
            }
        	
    	} catch (Exception e) {}
    	
    	// Close the query statement and return.
        try {stmt.close();}
        catch (Exception e) {}
        
        return retrievedItems;
    }
    */
    
    
    /**
     * processRetrieval - Processes an item retrieval by getting, from the retriever, all personal
     * information and storing it in the 'retrieval records' table in the database
     * @param id the id of the item being retrieved
     * @param fname the retriever's first name
     * @param lname the retriever's last name
     * @param emailAddress the retriever's email address
     * @param phoneNum the retriever's phone number
     * @param retrieverId the retriever's personal identification
     */
    public void processRetrieval(int id, String fname, String lname, 
    		String emailAddress, String phoneNum, String retrieverId)
    {
    	PreparedStatement stmt = null;
    	String sql;
    	
    	// Return if the database is closed.
        if (!isopen) return;
    	
    	try{	
        	// Create a PreparedStatement for the update.
        	sql = "INSERT INTO retrieval_records (item_id, first_name, "
        			+ "last_name, email, phone, identification)"
        			+ "VALUES (?, ?, ?, ?, ?, ?)";
        	stmt = conn.prepareStatement(sql);
    		
    		// Set the parameters in the statement
    		stmt.setInt(1, id);
    		stmt.setString(2, fname);
    		stmt.setString(3, lname);
    		stmt.setString(4, emailAddress);
    		stmt.setString(5, phoneNum);
    		stmt.setString(6, retrieverId);
        	
        	// Execute SQL Update
        	stmt.executeUpdate();
        	
        	// Change the item STATUS FROM 'lost' to 'retrieved'
        	// Create a PreparedStatement for the update.
        	sql = "UPDATE inventory SET status = 'retrieved' WHERE item_id = ?";
        	stmt = conn.prepareStatement(sql);
        	
        	// Set the parameters in the statement
    		stmt.setInt(1, id);
        	
    		// Execute SQL Update
        	stmt.executeUpdate();
        	
    	} catch (Exception e) {}
    	
    	// Close the query statement and return.
        try {stmt.close();}
        catch (Exception e) {}
    }
    
    /**
     * getUserData - Returns a map of data for the supplied user ID
     * @param userID ID of the user to retrieve
     * @return Map of user data
     * @throws SQLException if a database error occurs
     * @throws Exception if the database connection is not open
     */
    public User getUserFromID( int userID ) throws Exception {
    	PreparedStatement stmt = null;
    	String sql;
    	User user = null;
    	
    	if ( !isopen ) {
    		throw new Exception( "Database connection is not open" );
    	}
    	
    	try {
    		sql = "SELECT email, first_name, last_name, role FROM users WHERE user_id = ? LIMIT 1";
    		stmt = conn.prepareStatement( sql );
    		
    		stmt.setInt( 1, userID );
    		ResultSet rset = stmt.executeQuery();
    		
    		if ( rset.next() ) {
    			user = new User( userID, rset.getString( "email" ),
    				rset.getString( "first_name" ),  rset.getString( "last_name" ) );
    		}
    	} finally {
    		stmt.close();
    	}
    	
    	return user;
    }
    
    /**
     * getUserData - Returns a map of data for the supplied user ID
     * @param userID ID of the user to retrieve
     * @return Map of user data
     * @throws SQLException if a database error occurs
     * @throws Exception if the database connection is not open
     */
    public User getUserFromEmail( String email ) throws Exception {
    	PreparedStatement stmt = null;
    	String sql;
    	User user = null;
    	
    	if ( !isopen ) {
    		throw new Exception( "Database connection is not open" );
    	}
    	
    	try {
    		sql = "SELECT user_id, first_name, last_name, role FROM users WHERE email = ? LIMIT 1";
    		stmt = conn.prepareStatement( sql );
    		
    		stmt.setString( 1, email );
    		ResultSet rset = stmt.executeQuery();
    		
    		if ( rset.next() ) {
    			user = new User( rset.getInt( "user_id" ), email,
    				rset.getString( "first_name" ),  rset.getString( "last_name" ) );
    		}
    	} finally {
    		stmt.close();
    	}
    	
    	return user;
    }
    
    /**
     * addUser - Adds a user to the database
     * @param email user email
     * @param passwd hashed user password
     * @param role user role
     * @param firstName user first name
     * @param lastName user last name
     * @throws Exception if the database connection is not open
     */
    public void addUser(String email, String passwd, String role,
    		String firstName, String lastName) throws Exception {
    	
    	PreparedStatement stmt = null;
    	String sql;
    	
    	if (!isopen) {
    		throw new Exception( "Database connection is not open" );
    	}
    	
    	try {
    		// Create a statement for the update
    		sql = "INSERT INTO users " +
    				"(email, passwd, role, first_name, last_name) " +
    				"VALUES (?, ?, ?, ?, ?, ?)";
    		stmt = conn.prepareStatement(sql);
    		
    		// Set the parameters in the statement
    		stmt.setString(1, email);
    		stmt.setString(2, passwd);
    		stmt.setString(4, role);
    		stmt.setString(5, firstName);
    		stmt.setString(6, lastName);
    	
    		// Execute statement
    		stmt.executeUpdate();
    	} finally {
    		stmt.close();
    	}
    }
    
    /**
     * Looks up a userID from a cookie hash. Returns userID or null if
     * hash either could not be resolved to an ID or the hash has expired
     * @param hash Hash in string form
     * @return User ID
     * @throws SQLException 
     */
    public Integer getUserIDFromCookieHash( String hash ) throws SQLException {
    	PreparedStatement stmt = null;
    	String sql;
    	Integer userID = null;
    	
    	sql = "SELECT * FROM user_hashes WHERE hash = ? LIMIT 1";
    	
    	try {
    		stmt = conn.prepareStatement( sql );
    		stmt.setString( 1, hash );
    		ResultSet rset = stmt.executeQuery();
    		
    		if ( rset.next() ) {
    			Date today = new Date();
    			Date expiration = rset.getDate( "expiration_date" );
    			if ( expiration.after( today ) ) {
    				userID = rset.getInt( "user_id" );
    			}
    		}
    	} finally {
    		stmt.close();
    	}
    	
    	return userID;
    }
    
    /**
     * Sets a user cookie hash in the database
     * @param hash Hash to store
     * @param userID ID of the user to store the hash for
     * @param expiration Date the hash should expire
     * @throws Exception If database addition fails
     */
    public void setCookieHashForUser( String hash, int userID, LocalDate expiration ) throws Exception {
    	PreparedStatement stmt = null;
    	String sql;
    	java.sql.Date sqlDate = java.sql.Date.valueOf( expiration );
    	
    	
    	if (!isopen) {
    		throw new Exception( "Database connection is not open" );
    	}
    	
    	try {
    		// Create a statement for the update
    		sql = "INSERT INTO user_hashes " +
    				"(hash, user_id, expiration_date) " +
    				"VALUES (?, ?, ?)";
    		stmt = conn.prepareStatement(sql);
    		
    		// Set the parameters in the statement
    		stmt.setString(1, hash);
    		stmt.setInt(2, userID);
    		stmt.setDate(3, sqlDate);
    	
    		// Execute statement
    		stmt.executeUpdate();
    	} finally {
    		stmt.close();
    	}
    }
    
    /**
     * Deletes a user cookie hash from the database
     * @param hash Hash to remove
     * @param userID ID of the user to remove the hash for
     * @throws Exception If database delete fails
     */
    public void deleteCookieHashForUser( String hash, int userID ) throws Exception {
    	PreparedStatement stmt = null;
    	String sql;
    	
    	if (!isopen) {
    		throw new Exception( "Database connection is not open" );
    	}
    	
    	try {
    		// Create a statement for the update
    		sql = "DELETE FROM user_hashes WHERE hash = ? AND user_id = ? LIMIT 1";
    		stmt = conn.prepareStatement(sql);
    		
    		// Set the parameters in the statement
    		stmt.setString(1, hash);
    		stmt.setInt(2, userID);
    	
    		// Execute statement
    		stmt.executeUpdate();
    	} finally {
    		stmt.close();
    	}
    }
    
    /**
     * Gets encrypted password hash for a user
     * @param email Email address of user to retrieve hash for
     * @return Authentication hash or an empty string if user not found
     * @throws SQLException 
     */
    public String getAuthenticationHash( String email ) throws SQLException {
    	PreparedStatement stmt = null;
    	String sql;
    	String hash = "";
    	
    	sql = "SELECT passwd FROM users WHERE email = ? LIMIT 1";
    	try {
    		stmt = conn.prepareStatement( sql );
    		stmt.setString( 1, email );
    		ResultSet rset = stmt.executeQuery();
    		
    		if ( rset.next() ) {
    			hash = rset.getString( "passwd" );
    		}
    	} finally {
    		stmt.close();
    	}
    	
    	return hash;
    }

     
 } // End of class