package lostandfound.controller;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndexController {
	public static ModelAndView handleIndex( Request req, Response resp ) {
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put( "userFirstName", "Guest" );
		return new ModelAndView( templateVars, "indexServerSideEnabled" );
	}
}
