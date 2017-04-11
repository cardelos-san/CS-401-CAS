package lostandfound.controller;

import java.util.HashMap;
import java.util.Map;

import lostandfound.model.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SessionController {

	public static ModelAndView handleLogin( Request req, Response res ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		String email = req.queryParams( "uname" );
		String passwd = req.queryParams( "psw" );
		String template = "indexServerSideEnabled";
		boolean authenticated = false;
		
		templateVars.put( "userFirstName", email );
		return new ModelAndView( templateVars, template );
		/*
		Session session = new Session( req );
		try {
			authenticated = session.authenticate( email, passwd );
		} catch ( Exception e ) {
			// TODO: Should return error template
			template = "indexServerSideEnabled";
		}
		
		if ( authenticated == true ) {
			// TODO insert real user name
			templateVars.put( "userFirstName", "Admin" );
			template = "indexServerSideEnabled";
		} else {
			templateVars.put( "userFirstName", "Guest" );
		}
		*/
	}
}
