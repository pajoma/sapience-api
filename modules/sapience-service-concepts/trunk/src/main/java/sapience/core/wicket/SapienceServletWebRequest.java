package sapience.core.wicket;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.protocol.http.servlet.ServletWebRequest;

public class SapienceServletWebRequest extends ServletWebRequest {

	public SapienceServletWebRequest(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);
		
		// read the Accept header and put all values in an array.
		getParameterMap().put("Accept", httpServletRequest.getHeader("Accept").split(","));
	}

}
