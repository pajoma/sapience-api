package sapience.proxy.testing; 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

 
public class FileAnchor {

	
	public static InputStream getStream(String path) throws FileNotFoundException {
		InputStream is = FileAnchor.class.getClassLoader().getResourceAsStream(path);
		
		if(is == null) throw new FileNotFoundException(path); 
		
		return is; 
	}
	
	
	public static File getFile(String path) throws URISyntaxException, FileNotFoundException {
	
		return new File(getURL(path).toURI()); 
	}


	public static URL getURL(String path) throws FileNotFoundException {
		URL resource = FileAnchor.class.getClassLoader().getResource(path); 
		
		if(resource == null) throw new FileNotFoundException(path); 
		
		return resource; 
	}
}
