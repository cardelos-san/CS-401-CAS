package lostandfound.model;

import lostandfound.util.*;

public class User {

	public void addUser(String email, String passwd, String role,
			String firstName, String lastName) {
		Configuration config = Configuration.getInstance();
		String dbuser = config.getProperty("dbuser");
		String dbpasswd = config.getProperty("dbpasswd");
		
		DBase db = new DBase( dbuser, dbpasswd );
		db.addUser(email, passwd, role, firstName, lastName);
	}
	
}
