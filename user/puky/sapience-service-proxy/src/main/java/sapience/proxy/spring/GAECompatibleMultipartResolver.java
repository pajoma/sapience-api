package sapience.proxy.spring;



import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;


/**
 * Inspired by: http://github.com/pjesi/springextras/tree/master/src/main/java/is/hax/spring/web/multipart/
 * and http://code.google.com/appengine/kb/java.html#fileforms 
 * @author pajoma
 *
 */
public class GAECompatibleMultipartResolver extends CommonsMultipartResolver {

	 public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
		 
	        // Create a new file upload handler
	        ServletFileUpload upload = new ServletFileUpload();
	        upload.setFileSizeMax(getFileUpload().getFileSizeMax());
	 
	        String encoding = determineEncoding(request);
	 
	        //Map<String, MultipartFile> multipartFiles = new HashMap<String, MultipartFile>();
	        Map<String, String[]> multipartParameters = new HashMap<String, String[]>();
	 
	 
	        MultiValueMap<String, MultipartFile> multipartFiles = new LinkedMultiValueMap<String, MultipartFile>();
	 
	 
	 
	        // Parse the request
	        try {
	            FileItemIterator iter = upload.getItemIterator(request);
	            while (iter.hasNext()) {
	                FileItemStream item = iter.next();
	 
	 
	                String name = item.getFieldName();
	                InputStream stream = item.openStream();
	                if (item.isFormField()) {
	 
	                    String value = Streams.asString(stream, encoding);
	 
	                    String[] curParam = (String[]) multipartParameters.get(name);
	                    if (curParam == null) {
	                        // simple form field
	                        multipartParameters.put(name, new String[]{value});
	                    } else {
	                        // array of simple form fields
	                        String[] newParam = StringUtils.addStringToArray(curParam, value);
	                        multipartParameters.put(name, newParam);
	                    }
	 
	 
	                } else { 
	 
	                    // Process the input stream
	                    MultipartFile file = new StreamingMultipartFile(item);
	                    multipartFiles.add(name, file);
	                }
	            }
	        } catch (IOException e) {
	            throw new MultipartException("something went wrong here",e);
	        } catch (FileUploadException e) {
	            throw new MultipartException("something went wrong here",e);
	        }
	 
	 
	        return new DefaultMultipartHttpServletRequest(request, multipartFiles, multipartParameters);
	    }
}
