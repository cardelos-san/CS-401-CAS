package lostandfound.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lostandfound.controller.ItemController;
import spark.Request;

public class ImageFile {

	private static final Logger logger = LoggerFactory.getLogger( ItemController.class );
	
	/**
	 * handleImageUpload - Handles copying an uploaded image to a file and returns
	 * the filename or null if nothing was uploaded.
	 * @param req Request object
	 * @param itemImageDir Directory in which to create image
	 * @return Name of the created file or null if no file was uploaded
	 * @throws ServletException if unable to get part from servlet
	 * @throws IOException if unable to create new file on filesystem
	 */
	public static String copyImageUploadToFile( Request req, File itemImageDir ) throws IOException, ServletException {
		String imageName = null;
		req.attribute( "org.eclipse.jetty.multipartConfig", new MultipartConfigElement( "/temp" ) );
		Part itemPart = req.raw().getPart( "itemPic" );
		String uploadName = SparkUploadFilename.getFileName( itemPart );
		if ( uploadName != null && !uploadName.isEmpty() ) {
			String extension = uploadName.substring( uploadName.lastIndexOf( '.' ) );
			imageName = System.nanoTime() + extension;
			Path imageFile = Paths.get( itemImageDir.toString(), imageName );
			try ( InputStream input = itemPart.getInputStream() ) {
				Files.copy( input, imageFile );
			}
			logger.info( "Copied uploaded file: " + imageFile.toAbsolutePath() );
		}
		
		return imageName;
	}
	
	/**
	 * Deletes an image file from the filesystem
	 * @param imageName Name of the image file to delete
	 */
	public static void deleteImageFile( String imageName, File itemImageDir ) {
		if ( imageName == null || imageName.isEmpty() ) return;
		Path imageFile = Paths.get( itemImageDir.toString(), imageName );
		try {
			Files.deleteIfExists( imageFile ); // Windows won't delete the file if Spark has it open =(
		} catch ( Exception e ) {
			logger.error( "Failed to delete image file at " + imageFile.toAbsolutePath() + ": " + e.getMessage() );
		}
	}
	
}
