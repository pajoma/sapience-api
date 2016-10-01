package sapience.proxy.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.Adler32;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import sapience.injectors.Configuration;
import sapience.injectors.Injector;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.model.Reference;
import sapience.proxy.exceptions.ServiceNotRegisteredException;
import sapience.proxy.json.JsonBuilder;
import sapience.proxy.persistence.JDOServiceRepository;
import sapience.proxy.persistence.ServiceRepository;
import sapience.proxy.persistence.model.JDOStoredServices;

@Controller
public class ProxyController {
   
    @Autowired 
    private ConfigurationFactory cfac;

    private String key = "pefwoij";
    
    @Autowired
    private ServiceRepository repository;

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestParam String sid, HttpServletResponse response)
	    throws ServletException {
	try {
	    repository.delete(sid);
	} catch (Exception e) {
	    handleException(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void get(@RequestParam String sid, WebRequest request, HttpServletResponse response) {
	BufferedInputStream bis = null;

	    
	try {

	    Map<String, String[]> map = request.getParameterMap();
	    ServletOutputStream output = response.getOutputStream();
	    response.setContentType("application/xml");

	    if (!map.containsKey("sid")) 
		throw new MissingServletRequestParameterException("Service Identifier", "String");

	    // find the service id in the database
	    JDOStoredServices jss = repository.loadService(sid);

	    if (jss == null) {
		throw new ServiceNotRegisteredException(sid);
	    }

	    String serviceRequest = jss.getServiceRequest();
	    URL url = new URL(serviceRequest);

	    // if stored request is not exactly the same as the current request
	    // we simply forward
	    Map<String, String[]> cPs = request.getParameterMap();
	    Map<String, String> sPs = this.extractParameterMap(url.getQuery());
	    URI newURL = constructNewURL(url, cPs);

	    if (cPs.size() - 1 != sPs.size()) {
		response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		response.setHeader("Location", newURL.toString());
		return;
	    }

	    int c = 0;
	    for (Entry<String, String> e : sPs.entrySet()) {
		// check if the attribute is also in the request
		String p = request.getParameter(e.getKey());
		if ((p != null) && (p.equalsIgnoreCase(e.getValue())))
		    c++;

	    }
	    if (c != sPs.size()) {
		response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		response.setHeader("Location", newURL.toString());
		return;
	    }

	    /* the actual injection */
	    URLConnection connect = url.openConnection();
	   // connect.setConnectTimeout(400);

	    
	    bis = new BufferedInputStream(connect.getInputStream());
	    Configuration config = cfac.getConfiguration(bis);
	    Injector injector = config.getInjector();
	    injector.annotate(sid, bis, output);

	    response.setStatus(HttpServletResponse.SC_OK);


	} catch (MissingServletRequestParameterException e) {

	    // 400 - Bad Request: if required parameter is missing
	    handleException(e, response, HttpServletResponse.SC_BAD_REQUEST);
	    return;

	} catch (ServiceNotRegisteredException e) {

	    // 404 - Not Found: if given service id is not registered
	    handleException(e, response, HttpServletResponse.SC_NOT_FOUND);
	    return;

	} catch (SocketTimeoutException e) {

	    // 502 - Bad Gateway: service id registered, but failed to access
	    // upstream server
	    handleException(e, response,HttpServletResponse.SC_BAD_GATEWAY);
	    return;

	} catch (Exception e) {
	    handleException(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    return;
	    // TODO: log to file
	} finally {
	    IOUtils.closeQuietly(bis);
	}
    }

    @RequestMapping(value = "/list/services", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void list(HttpServletResponse response) throws ServletException {
	try {
	    response.setContentType("text/plain");
	    Collection<JDOStoredServices> list = repository.loadServices();
	    JsonBuilder.writeServicesResponse(response.getWriter(), list);
	} catch (Exception e) {
	    handleException(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
    }

    @RequestMapping(value = "/list/references", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void list(@RequestParam String sid, HttpServletResponse response)
	    throws ServletException {
	
	try {
	    response.setContentType("text/plain");
	    List<Reference> list = repository.load(sid);
	    JsonBuilder.writeListResponse(response.getWriter(), sid, list);
	} catch (Exception e) {
	    handleException(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

    }

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public ModelAndView post(MultipartHttpServletRequest req,
	    HttpServletResponse response) throws ServletException {
	try {
	    // set needed parameters
	    String serviceRequest = req.getParameter("service_request");
	    InputStream stream = req.getFile("uploaded_file").getInputStream();
	    String sid = generateServiceID(serviceRequest);

	    // start extracting the annotations
	    Configuration config = cfac.getConfiguration(stream);
	    List<Reference> resultList = config.getExtractor().extract(sid, stream);
	    cfac.getLookup().put(serviceRequest, resultList);
		
		
		
	    // prepare result
	    ModelAndView res = new ModelAndView();
	    res.setViewName("created");
	    res.addObject("request", serviceRequest);
	    res.addObject("sid", sid);
	    res.addObject("example", generateExampleRequest(serviceRequest, sid));
	    res.addObject("action", "created");
	    return res;

	    //		        
	    // }
	} catch (IOException ex) {
	    handleException(ex, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    return null;
	}

    }

    @RequestMapping(value = "/purge", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void purge(@RequestParam String key, HttpServletResponse response)
	    throws ServletException {
	try {
	    if (key.contentEquals(this.key)) {
		repository.purge();
	    }
	} catch (Exception e) {
	    handleException(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

    }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public ModelAndView put(MultipartHttpServletRequest req,
	    HttpServletResponse response) throws ServletException {
	return this.post(req, response);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void statistics(HttpServletResponse response)
	    throws ServletException {
	try {
	    Collection<JDOStoredServices> all = ((JDOServiceRepository) repository) .loadServices();
	    for (JDOStoredServices s : all) {
		for (Reference sr : s.listStoredReferences()) {
		    response.getWriter().append(s.getSID().toString()).append(": ").append(sr.toString()).append('\n');
		}
	    }
	} catch (Exception e) {
	    handleException(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
    }

    @RequestMapping(value = "/examples", method = RequestMethod.GET)
    public void test(HttpServletResponse response) throws ServletException {

	try {
	    Examples ex = new Examples(cfac);
	    ex.example_WSDL20Interface();
	    ex.example_WSDL11Operation();
	    ex.example_WSDL20XMLSchema();
	    ex.example_WPS10GetCapabilities();
	    ex.example_WPS10Process();
	} catch (MalformedURLException e) {
	    handleException(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	} catch (IOException e) {
	    handleException(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	// loads some test data into the database
	// http://swing.brgm.fr/cgi-bin/carrieres?service=wfs&version=1.0.0&request=GetCapabilities
	// http://gis-obama.uni-muenster.de:8180/intamap/WebProcessingService?Request=DescribeProcess&Service=WPS&identifier=org.intamap.wps.Interpolate
	// http://eea:eea@gis-obama.uni-muenster.de:8180/UBA_SOS/sos?request=getCapabilities&service=SOS

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String welcome() {
	return "redirect:../html/api.html";
    }

    private URI constructNewURL(URL url, Map<String, String[]> currentParameters)
	    throws URISyntaxException {
	List<StringBuilder> params = new ArrayList<StringBuilder>();
	StringBuilder res = new StringBuilder();
	for (Entry<String, String[]> e : currentParameters.entrySet()) {
	    if (e.getKey().contentEquals("sid"))
		continue;
	    for (String str : e.getValue()) {
		params.add(new StringBuilder(e.getKey()).append('=')
			.append(str));
	    }
	}
	if (params.size() > 0) {
	    Iterator<StringBuilder> it = params.iterator();
	    while (it.hasNext()) {
		res.append(it.next());
		if (it.hasNext())
		    res.append('&');
	    }
	}

	return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url
		.getPort(), url.getPath(), res.toString(), null);

    }

    // @RequestMapping(value = "/hello", method = RequestMethod.GET)
    // public ModelAndView hello() {
    // Map<String, ? super Object> model = new HashMap<String, Object>();
    // model.put("hello", "HELLO WORLD!");
    // ModelAndView modelAndView = new ModelAndView("hello", model);
    // return modelAndView;
    // }

    private Map<String, String> extractParameterMap(String query)
	    throws UnsupportedEncodingException {
	Map<String, String> pMap = new HashMap<String, String>();
	if(query == null) return pMap;
	
	String params[] = query.split("&");

	for (String param : params) {
	    String temp[] = param.split("=");
	    pMap.put(temp[0], java.net.URLDecoder.decode(temp[1], "UTF-8"));
	}
	return pMap;
    }

    private String generateExampleRequest(String serviceRequest, String sid) {
	String[] split = serviceRequest.split("\\?");
	
	StringBuilder res = new StringBuilder("http://semantic-proxy.appspot.com/api/get");
	
	res.append("?sid=").append(sid);
	if(split.length==2) res.append("&").append(split[1]);
	return res.toString();
	
	
    }

    private String generateServiceID(String serviceRequest) {
	Adler32 adler32 = new Adler32();
	adler32.update(serviceRequest.getBytes());
	return Long.toHexString(adler32.getValue());
    }

    private void handleException(Exception e, HttpServletResponse response, int status)  {
	try {
	    response.setContentType("text/plain");
	    
	    StringBuilder sb = new StringBuilder("ERROR: "); 
	    sb.append("An Exception has been thrown. Please check the message below, and either try it again or report a bug.").append("\n").append("\n");

	    OutputStream writer = response.getOutputStream();
	    writer.write(sb.toString().getBytes());
	    e.printStackTrace(new PrintStream(writer));
	    writer.flush();

	    response.setStatus(status);
	} catch (IOException e2) {
	    throw new RuntimeException(e2);
	}


	
    }
}
