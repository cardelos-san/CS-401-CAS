package lostandfound.controller;

import lostandfound.model.*;
import lostandfound.util.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import spark.Request;
import spark.Response;

public class ItemController {
	
	public static List<Item> getAllItems( Request req, Response resp ) {
		Configuration config = Configuration.getInstance();
		DBase db = new DBase( config.getProperty( "dbuser" ), 
				config.getProperty( "dbpasswd" ) );
		List<Item> items = db.getAllItems();
		
		return items;
	}
	
}
