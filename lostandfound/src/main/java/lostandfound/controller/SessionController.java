package lostandfound.controller;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lostandfound.model.*;
import lostandfound.util.Configuration;
import lostandfound.util.DBase;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SessionController {
	
	final static Logger logger = LoggerFactory.getLogger( SessionController.class );

	public static ModelAndView displayLogin( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		return new ModelAndView( templateVars, "loginForm" );
	}
	
	public static ModelAndView handleLogin( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		String email = req.queryParams( "email" );
		String passwd = req.queryParams( "passwd" );
		String remember = req.queryParams( "remember" );
		String template = "loginForm";
		boolean authenticated = false;
		Session session = new Session( req );
		
		try {
			authenticated = session.authenticate( email, passwd );
		} catch ( Exception e ) {
			template = "loginForm";
		}
		
		if ( authenticated ) {
			Configuration config = Configuration.getInstance();
			String dbuser = config.getProperty( "dbuser" );
			String dbpasswd = config.getProperty( "dbpasswd" );
			DBase db = new DBase( dbuser, dbpasswd );
			User user;
			
			try {
				user = db.getUserFromEmail( email );
			} catch ( Exception e ) {
				// Unable to retrieve user ID from email
				String errorMsg = "Error: Invalid email or password.";
				templateVars.put( "error" , errorMsg );
				return new ModelAndView( templateVars, "loginForm" );
			} finally {
				db.close();
			}
			session.setUserID( user.getID() );
			
			// Set cookie if requested
			if ( remember != null ) {
				try {
					session.setCookieFromSession( res );
				} catch ( Exception e ) {
					logger.error( "Unable to set cookie", e );
				}
			}
			// Redirect user to index
			res.redirect( "/" );
		} else {
			String errorMsg = "Error: Invalid email or password.";
			templateVars.put( "error" , errorMsg );
		}
		
		return new ModelAndView( templateVars, template );
	}
	
	public static ModelAndView handleLogout( Request req, Response res ) {
		Session session = new Session( req );
		session.logout( res );
		res.redirect( "/" );
		return null;
	}
}
