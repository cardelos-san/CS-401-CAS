package lostandfound;
import lostandfound.controller.*;


import static spark.Spark.get;
import static spark.Spark.staticFileLocation;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import spark.template.thymeleaf.ThymeleafTemplateEngine;



/*LostAndFoundApp establishes web application routes and 
hands control to Controller class*/
public class LostAndFound {
	
	public static void main(String[] args) {
		
			//Initializing a template resolver for template settings//
			ITemplateResolver templateResolver = createTemplateResolver();
			
			//Creating empty template objects with template settings//
			ThymeleafTemplateEngine template = new ThymeleafTemplateEngine(templateResolver); 
			ThymeleafTemplateEngine template2 = new ThymeleafTemplateEngine(templateResolver);
			
			/*Grants static access to root folder contents through browser
			e.g. type in http://localhost:4567/banner.png */
			staticFileLocation("templates/");
			
			/*Defining all routes*/
			
			//Creating route http://localhost:4567/home/:guest//
			get("/home/:guest", Controller::handler, template);
			//Creating route http://localhost:4567/index/:guest//
			get("/index/:guest", Controller::handler2, template2);
			
			
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
		/*If .setCacheable(true), then set chache size
		templateResolver.setCacheTTLMs(360000L);*/
		
		return templateResolver;
	}
	

}
