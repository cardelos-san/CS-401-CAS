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
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 

import java.sql.*; //Allows you to use JDBC classes/interfaces
import java.util.HashMap;
//import java.util.*; MAY NOT NEED THIS
//import java.util.Date; MAY NOT NEED THIS
import java.util.Map;
import org.json.JSONException;
public class DBase {

	private Connection conn;
    private boolean isopen;
    //private Scanner kbd = new Scanner(System.in); MAY NOT NEED THIS
    
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
     * @param id the item's id
     * @param description a description of the item
     * @param status the status of the item ('lost' or 'found')
     * @param dateFound the date of which the item was found
     * @param dateRetrieved the date of which the item was retrieved (if item was found)
     * @param adminId the id of the administrator that is processing this transaction
     */
    public void addItem(int id, String description, String status,
    		java.sql.Date dateFound, java.sql.Date dateRetrieved, int adminId)
    {
    	PreparedStatement stmt = null;
    	String sql;
    	
    	// Return if the database is closed.
    	if (!isopen) return;
    	
    	try{
    		// Create a statement for the update
    		sql = "INSERT INTO inventory (item_id," + 
    				" description, status, date_found, date_retrieved," + 
    				" added_by_user) VALUES (?, ?, ?, ?, ?, ?)";
    		stmt = conn.prepareStatement(sql);
    	
    		// Set the parameters in the statement
    		stmt.setInt(1, id);
    		stmt.setString(2, description);
    		stmt.setString(3, status);
    		stmt.setDate(4, dateFound);
    		stmt.setDate(5, dateRetrieved);
    		stmt.setInt(6, adminId);
    	
    		// Execute SQL Update
    		stmt.executeUpdate();
        
        	// Confirm the process
        	//System.out.println("Completed Insert!");
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
                //System.out.printf("%n Item No. %-8d %n Description: %-50s %n " +
                		//"Status: %-10s %n Date Created: %tD %n Date Found: %tD %n " +
                		//"Date Retrieved: %tD %n Admin Id: %-8d %n%n",
                   		//itemId, description, status, dateCreated, dateFound, dateRetrieved, adminId);
            }

        } catch (Exception e) {}
        
        // Close the update statement and return.
        try {stmt.close();}
        catch (Exception e) {}
    }
    
    /**
     * showRetrievedItem - Search and return all items marked as retrieved in the database
     * @return returns ... (!!! EDIT)
     * WARNING: THIS INFO SHOULD BE RETURNED, SHOULD NOT PRINT IN DBASE
     */
    public void showRetrievedItems()
    {
    	PreparedStatement stmt = null;
    	ResultSet rset = null; // result - gets returned
        String sql, description, status;
        int itemId, adminId;
		java.sql.Date dateCreated, dateFound, dateRetrieved;
        
        // Return if the database is closed.
        if (!isopen) return;
    	
    	try{	
        	// Create a PreparedStatement for the update.
        	sql = "SELECT * FROM inventory WHERE inventory.status = 'Retrieved'";
        	stmt = conn.prepareStatement(sql);
        
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
                //System.out.printf("%n Item No. %-8d %n Description: %-50s %n " +
                		//"Status: %-10s %n Date Created: %tD %n Date Found: %tD %n " +
                		//"Date Retrieved: %tD %n Admin Id: %-8d %n%n",
                   		//itemId, description, status, dateCreated, dateFound, dateRetrieved, adminId);
            }
        	
    	} catch (Exception e) {}
    	
    	// Close the query statement and return.
        try {stmt.close();}
        catch (Exception e) {}
    }
    
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
    public Map<String, String> getUserData( int userID ) throws Exception {
    	PreparedStatement stmt = null;
    	String sql;
    	
    	if ( !isopen ) {
    		throw new Exception( "Database connection is not open" );
    	}
    	
    	Map<String, String> userData = new HashMap<String, String>();
    	
    	try {
    		sql = "SELECT email, first_name, last_name, role FROM users WHERE user_id = ? LIMIT 1";
    		stmt = conn.prepareStatement( sql );
    		
    		stmt.setInt( 1, userID );
    		ResultSet rset = stmt.executeQuery();
    		
    		userData.put( "email", rset.getString( "email" ) );
    		userData.put( "firstName", rset.getString( "first_name" ) );
    		userData.put( "lastName", rset.getString( "last_name" ) );
    		userData.put( "role", rset.getString( "role" ) );
    	} finally {
    		stmt.close();
    	}
    	
    	return userData;
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
     * requestTable - Requesting inventory data
     */
    public void requestTable()
     	 throws SQLException, JSONException, IOException {

     		    Statement stmt = null;
     		    String query =
     		        "select item_id, description, status, date_retrieved " +
     		        "from " + "inventory";

     		    try {
     		        stmt = conn.createStatement();
     		        ResultSet rs = stmt.executeQuery(query);
     		        ResultSetToJSON.convert(rs);
     		        
     		    }catch (SQLException e ) {} 
     		    finally {
     		        if (stmt != null) { stmt.close(); }
     		    }
     		    
     }

     
 } // End of class

    

