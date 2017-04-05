/*	Class Name: DBase
 *********************************************************************
 *  This class uses JDBC to communicate with a JavaDB database of
 *  Lost and Found data. The driver needed to make the connection
 *  is already loaded from the 'lib' folder. The constructor connects 
 *  to the database, the
 *  ..... (FINISH AS GO ALONG WITH THE CLASS).
 */

package lostandfound.util;

import java.sql.*; //Allows you to use JDBC classes/interfaces
//import java.util.*; MAY NOT NEED THIS
//import java.util.Date; MAY NOT NEED THIS

public class DBase {

	private Connection conn;
    private boolean isopen;
    //private Scanner kbd = new Scanner(System.in); MAY NOT NEED THIS
    
    // Attempt to connect to the JavaDB lost_and_found database.
    public DBase(String uname, String pword) 
    {
        try {
            //Connect to the database
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/lost_and_found", uname, pword);
        } catch (Exception e) {conn = null;}
        isopen = (conn != null);
    }
	
    // Test whether the database is open.
    public boolean isOpen() {return isopen;}
	
    // Close the database connection.
    public void close() 
    {
        if (!isopen) return;
        try {conn.close();}
        catch (Exception e) {}
        isopen = false;
        conn = null; // Clean up before clearing
    }
	
    // This method will add an item to the database, given all an
    // item's attributes
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
    	
    		// 3. Execute SQL Update
    		stmt.executeUpdate();
        
        	// 4. Process the result set
        	System.out.println("Completed Insert!");
    	} catch (Exception e) {}
    	
    	// Close the update statement and return
    	try {stmt.close();}
    	catch (Exception e) {}
    }
    
    /**
     * addUser - Adds a user to the database
     * @param email user email
     * @param passwd hashed user password
     * @param role user role
     * @param firstName user first name
     * @param lastName user last name
     */
    public void addUser(String email, String passwd, String role,
    		String firstName, String lastName) {
    	
    	PreparedStatement stmt = null;
    	String sql;
    	
    	// Return if the database is closed.
    	if (!isopen) return;
    	
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
    	} catch (Exception e) {}
    	
    	// Close the update statement and return
    	try {
    		stmt.close();
    	} catch (Exception e) {}
    }
    
  //Requesting inventory data
    public void requestTable()
     	 throws SQLException {

     		    Statement stmt = null;
     		    String query =
     		        "select item_id, description, status " +
     		        "from " + "inventory";

     		    try {
     		        stmt = conn.createStatement();
     		        ResultSet rs = stmt.executeQuery(query);
     		        while (rs.next()) {
     		            int item_id = rs.getInt("item_id");
     		            String description = rs.getString("description");
     		            String status = rs.getString("status");
     		            
     		            System.out.println(item_id + "\t" + description +
     		                               "\t" + status);
     		        }
     		    } catch (SQLException e ) {} 
     		    finally {
     		        if (stmt != null) { stmt.close(); }
     		    }
     		    
     		}

     
 } //End of class

    

