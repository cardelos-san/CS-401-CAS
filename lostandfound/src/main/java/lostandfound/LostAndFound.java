package lostandfound;
import lostandfound.controller.*;
import lostandfound.model.Item;
import lostandfound.util.Configuration;


import static spark.Spark.*;
import java.io.IOException;

import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import spark.template.thymeleaf.ThymeleafTemplateEngine;



/*LostAndFoundApp establishes web application routes and 
hands control to Controller class*/
public class LostAndFound {
	
	public static void main(String[] args) {
		//Initializing database configuration//
		Configuration.initialize();
		
		//Initializing a template resolver for template settings//
		ITemplateResolver templateResolver = createTemplateResolver();
		
		//Creating empty template objects with template settings//
		ThymeleafTemplateEngine templateEngine = new ThymeleafTemplateEngine(templateResolver);
		
		/*Grants static access to public folder contents through browser
		e.g. type in http://localhost:4567/images/banner.png */
		staticFiles.location("/public");
		
		/*Defining all routes*/
		get( "/", IndexController::handleIndex, templateEngine);
		path( "/ajax", () -> {
			get( "/getItems", Controller::handler, templateEngine);
			post( "/addItem", Controller::handler, templateEngine);
			post( "/editItem", Controller::handler, templateEngine);
		});
		//Creating route http://localhost:4567/home/:guest//
		//get("/home/:guest", Controller::handler, templateEngine);
		//Creating route http://localhost:4567/index/:guest//
		//get("/index/:guest", Controller::handler2, templateEngine);
		
		get("/test", ( request, response ) -> "Test string" );
	}
		

	/*Sets template settings*/
	public static ITemplateResolver createTemplateResolver(){
		
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		//Sets template mode//
		templateResolver.setTemplateMode(TemplateMode.HTML);
		//Sets resources/templates/ as root folder//
		templateResolver.setPrefix("templates/");
		//Looks for HTML file extension//
		templateResolver.setSuffix(".html");
		//Sets caching to false//
		templateResolver.setCacheable(false);
		/*If .setCacheable(true), then set cache size
		templateResolver.setCacheTTLMs(360000L);*/
		
		return templateResolver;
	}
	

}
