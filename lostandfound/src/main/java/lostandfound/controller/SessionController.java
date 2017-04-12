package lostandfound.controller;

import java.util.HashMap;
import java.util.Map;

import lostandfound.model.*;
import lostandfound.util.Configuration;
import lostandfound.util.DBase;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SessionController {

	public static ModelAndView displayLogin( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		return new ModelAndView( templateVars, "loginForm" );
	}
	
	public static ModelAndView handleLogin( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		String email = req.queryParams( "email" );
		String passwd = req.queryParams( "passwd" );
		String template = "loginForm";
		boolean authenticated = false;
		Session session = new Session( req );
		
		try {
			authenticated = session.authenticate( email, passwd );
		} catch ( Exception e ) {
			template = "loginForm";
		}
		
		if ( authenticated == true ) {
			Configuration config = Configuration.getInstance();
			String dbuser = config.getProperty( "dbuser" );
			String dbpasswd = config.getProperty( "dbpasswd" );
			DBase db = new DBase( dbuser, dbpasswd );
			User user;
			
			try {
				user = db.getUserFromEmail( email );
			} catch ( Exception e ) {
				// TODO: Log exception
				String errorMsg = "Error: Invalid email or password.";
				templateVars.put( "error" , errorMsg );
				return new ModelAndView( templateVars, "loginForm" );
			} finally {
				db.close();
			}
			
			templateVars.put( "userFirstName", user.getFirstName() );
			template = "indexServerSideEnabled";
			session.setUserID( user.getID() );
		} else {
			String errorMsg = "Error: Invalid email or password.";
			templateVars.put( "error" , errorMsg );
		}
		
		return new ModelAndView( templateVars, template );
	}
}
