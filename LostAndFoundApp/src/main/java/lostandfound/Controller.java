package lostandfound;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;


/*Controller class contains all handler methods*/

public class Controller {
	public static ModelAndView handler(Request request, Response response) {
		/* Create a hash map of the "name" and "job" arguments from the URL.
		 * Calls to methods that would interact with the database would go here.
		 * The code below is a simple stand-in for more complex model code.
		 */
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("guest", request.params(":guest"));

		// Return an object with the completed model data and the name of the template to use. 
		return new ModelAndView(templateVars, "indexServerSideEnabled");
	}
	
	public static ModelAndView handler2(Request request, Response response) {
		/* Create a hash map of the "name" and "job" arguments from the URL.
		 * Calls to methods that would interact with the database would go here.
		 * The code below is a simple stand-in for more complex model code.
		 */
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("guest", request.params(":guest"));

		// Return an object with the completed model data and the name of the template to use. 
		return new ModelAndView(templateVars, "index");
	}
}