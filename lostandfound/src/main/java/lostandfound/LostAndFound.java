package lostandfound;

import lostandfound.controller.*;
import lostandfound.util.Configuration;
import lostandfound.util.JsonTransformer;

import static spark.Spark.*;

import java.io.*;

import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import org.mindrot.jbcrypt.*;



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
		
		// Create item images directory if it does not exist
		Configuration config = Configuration.getInstance();
		File itemsDir = new File( config.getProperty( "itemimages", "public/images/items" ) );
		itemsDir.mkdirs();
		
		/*Grants static access to public folder contents through browser
		e.g. type in http://localhost:4567/images/banner.png */
		staticFiles.externalLocation("public");
		
		/*Defining all routes*/
		get( "/", IndexController::getIndex, templateEngine);
		path( "/api", () -> {
			get( "/getAllItems", ItemController::getAllItems, new JsonTransformer());
		});
		/*
		path( "/api", () -> {
			get( "/getRetrievedItems", ItemController::getRetrievedItems, new JsonTransformer());
		});
		*/
		path( "/session", () -> {
			get( "/login", SessionController::displayLogin, templateEngine);
			post( "/login", SessionController::handleLogin, templateEngine);
			get( "/logout", SessionController::handleLogout);
		});
		

		path( "/item", () -> {
			get( "/addItem", ItemController::addAnItem, templateEngine);
			post( "/addItem",ItemController::addItemHandler, templateEngine);
			get( "/retrieveItem/:itemID", ItemController::retrieveItem, templateEngine);
			post( "/retrieveItem/:itemID", ItemController::retrieveItemHandler, templateEngine);
			get( "/deleteItem/:itemID", ItemController::deleteItem, templateEngine);
			post( "/deleteItem/:itemID", ItemController::deleteItemHandler, templateEngine);
			get( "/editItem/:itemID", ItemController::editItem, templateEngine);
			post( "/editItem/:itemID", ItemController::editItemHandler, templateEngine);
			get( "/viewItem/:itemID", ItemController::viewItem, templateEngine);
		});
		
		// Test bcrypt hash
		get( "/bcrypt/:pw", ( req, res ) -> BCrypt.hashpw( req.params( ":pw" ), BCrypt.gensalt() ) );
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
