package lostandfound.model;

import lostandfound.util.*;

public class User {

	public void addUser(String email, String passwd, String role,
			String firstName, String lastName) {
		
		DBase db = new DBase("root", "passwd");
		db.addUser(email, passwd, role, firstName, lastName);
	}
	
}
