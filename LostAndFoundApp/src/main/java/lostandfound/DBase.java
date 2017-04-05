package lostandfound;


import java.sql.SQLException;
import java.sql.*; //Allows you to use JDBC classes/interfaces
//import java.util.*; MAY NOT NEED THIS//
//import java.util.Date; MAY NOT NEED THIS//

public class DBase {

	private Connection conn;
    private boolean isopen;
    //Temporary username & password//
    private String userName;
    private String passWord;
    //private Scanner kbd = new Scanner(System.in); MAY NOT NEED THIS//
    
    //Attempt to connect to the JavaDB lost_and_found database//
    public DBase() {
    	
    	//This will be replaced by Configuration object//
    	userName = "root";
    	passWord = "soyyo1996";
    	
        try {
            //Connect to the database//
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/lost_and_found", userName, passWord);
        } catch (Exception e) {conn = null;}
        isopen = (conn != null);
    }
	
    //Test whether the database is open.//
    public boolean isOpen() {return isopen;}
	
    //Close the database connection//
    public void close() 
    {
        if (!isopen) return;
        try {conn.close();}
        catch (Exception e) {}
        isopen = false;
        conn = null; //Clean up before clearing//
    }
	
    /* This method will add an item to the database, given all an
     * item's attributes*/
    public void addItem(int id, String description, String status,
    		java.sql.Date dateFound, java.sql.Date dateRetrieved, int adminId)
    {
    	PreparedStatement stmt = null;
    	String sql;
    	
    	//Return if the database is closed//
    	if (!isopen) return;
    	
    	try{
    		//Create a statement for the update//
    		sql = "INSERT INTO inventory (item_id," + 
    				" description, status, date_found, date_retrieved," + 
    				" added_by_user) VALUES (?, ?, ?, ?, ?, ?)";
    		stmt = conn.prepareStatement(sql);
    	
    		//Set the parameters in the statement
    		stmt.setInt(1, id);
    		stmt.setString(2, description);
    		stmt.setString(3, status);
    		stmt.setDate(4, dateFound);
    		stmt.setDate(5, dateRetrieved);
    		stmt.setInt(6, adminId);
    	
    		//3. Execute SQL Update//
    		stmt.executeUpdate();
        
        	//4. Process the result set//
        	System.out.println("Completed Insert!");
    	} catch (Exception e) {}
    	
    	// Close the update statement and return//
    	try {stmt.close();}
    	catch (Exception e) {}
    }
    
    //Requesting iventory data//
   /* public int requestTable()
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
    		    
    		}*/

    
} //End of class
