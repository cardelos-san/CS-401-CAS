package lostandfound.controller;

import java.util.HashMap;
import java.util.Map;
import lostandfound.model.Session;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndexController {
	
	public static ModelAndView getIndex( Request request, Response response ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		Session session = new Session( request );
		
		templateVars.put( "userFirstName", session.getUserFirstNameFromSession() );
		
		return new ModelAndView( templateVars, "indexServerSideEnabled" );
	}	
	
}
