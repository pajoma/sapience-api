package sapience.proxy.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.context.request.WebRequest;

public class OGCRequestValidator  {


	
	public void validate(WebRequest request) throws IOException {		
		Map<String, String[]> map = request.getParameterMap();
		
		
		
	}

}
