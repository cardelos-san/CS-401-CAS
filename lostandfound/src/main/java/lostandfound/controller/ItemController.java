package lostandfound.controller;

import lostandfound.model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import spark.Request;
import spark.Response;

public class ItemController {
	
	public static List<ItemNew> getAllItems( Request req, Response resp ) {
		List<ItemNew> items = new ArrayList<ItemNew>();
		ItemNew item = new ItemNew( 1, "None", "Brown leather wallet", "Wallet", Date.valueOf( "2017-04-07" ), "Lost" );
		
		items.add( item );
		
		return items;
	}
	
}
