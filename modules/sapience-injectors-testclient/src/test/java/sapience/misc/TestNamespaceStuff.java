package sapience.misc;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import sapience.injectors.stax.extract.StaxBasedExtractor;
import sapience.injectors.util.NameSpaceContextImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-tests.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TestNamespaceStuff {

	
	
	@Test
	public void createURI() {
		String key1 = "localname";
		URI create1 = URI.create(key1);
		
		String key2 = "prefix:localname";
		URI create2 = URI.create(key2);
		
		String key3 = "http://www.example.com#localname";
		URI create3 = URI.create(key3);
		
		System.out.println();
	}
	
	
	/**
	 * Helper method, taking a XML string like <ows:Metadata xlink:href=".."></ows:Metadata> and
	 * injects local namespace declarations
	 * 
	 * @see StaxBasedExtractor#injectNamespaces
	 * @param resultingXMLString
	 * @param context
	 */
	
	@Test
	public void extractPrefix() {
		Pattern prefixPattern = Pattern.compile("(\\s|<)\\w+:");
		
		String xml =  "<ows:Metadata xlink:href=\"http://dude.com\"></ows:Metadata>";
		String expected = "<ows:Metadata xmlns:ows=\"http://ogc.org/ows\" xmlns:xlink=\"http://wrc.org/xlink\" xlink:href=\"http://dude.com\"></ows:Metadata>";
		NameSpaceContextImpl nc = new NameSpaceContextImpl();
		nc.put("ows", "http://ogc.org/ows");
		nc.put("xlink", "http://wrc.org/xlink");
		Matcher matcher = prefixPattern.matcher(xml);
		StringBuilder sb = new StringBuilder(xml);
		int injection_position = sb.indexOf(" ");

		boolean elementHasPrefix = false;
		List<String>  prefixList = new ArrayList<String>();
		
		while (matcher.find()) {
			String r = matcher.group();
			String subSequence = r.substring(1, r.length()-1);
			if(r.charAt(0) == '<') elementHasPrefix = true;
			prefixList.add(subSequence);
		}
		
		// inject namespaces
		StringBuilder ns = new StringBuilder();
		if(! elementHasPrefix) {
			// do nothing, log a warning
		}
		else {
			for(String px : prefixList) {
				String nsURI = nc.getNamespaceURI(px);
				ns.append("xmlns:").append(px).append("=").append("\"").append(nsURI).append("\" ");
			}
			sb.insert(injection_position+1, ns);
			
		}
		Assert.assertEquals(expected, sb.toString());
	}

	@Test
	public void throwsException() {
		StringBuilder sb = new StringBuilder("<ns1:Metadata ns2:href=\"http://core/WebServices/de73f64e\" ns2:arcrole=\"http://annot/modelReference\"></ns1:Metadata>");
		Pattern prefixPattern =  Pattern.compile("(\\s|<|/)\\w+:");
		Pattern nsPattern =  Pattern.compile("xmlns:\\w+=\"(\\w|:|/|\\.)*\"");
		
		NameSpaceContextImpl global = new NameSpaceContextImpl();
		global.put("ns1", "http://ogc.org/ows");
		global.put("ns2", "http://wrc.org/xlink");
		
		NameSpaceContextImpl local = new NameSpaceContextImpl();
		local.put("ns1", "http://ogc.org/ows");
		local.put("ns2", "http://wrc.org/xlink");
		String uri;
		String prefix; 
		
		Matcher prefixMatcher = prefixPattern.matcher(sb);
		Matcher nsMatcher = nsPattern.matcher(sb);
		
		/* process the local namespaces */
		while (nsMatcher.find()) {
			int start = nsMatcher.start();
			int end = nsMatcher.end();
			StringBuilder sbu = new StringBuilder(sb.substring(start,end));
			String thisPrefix = sbu.substring(sbu.indexOf(":")+1, sbu.lastIndexOf("="));
			String thisUri = sbu.substring(sbu.indexOf("\"")+1, sbu.lastIndexOf("\""));
			// add to local namespace context
			local.put(thisPrefix, thisUri);
			
			if((prefix = global.getPrefix(thisUri)) != null) {
				// namespace is registered, let's remove it
				sb.delete(start-1, end);
				
				// we have to reset, since we changed the state of the matched string with the deletion
				nsMatcher.reset(); 
			}
			
		}
		
		/* change the prefixes */
		try {
			while (prefixMatcher.find()) {
				int start = prefixMatcher.start();
				int end = prefixMatcher.end();
				
				String localprefix = sb.substring(start+1,end-1);
				if((global.getNamespaceURI(localprefix) == null) && (uri = local.getNamespaceURI(localprefix)) != null) {
					// get the other prefix
					prefix = global.getPrefix(uri);
					if(! (localprefix.contentEquals(prefix))) {
						sb.replace(start+1, end-1, prefix);
						prefixMatcher.reset();
					}
				}			
			}
		} catch (StringIndexOutOfBoundsException  e) {
			// we do nothing here
		}

		
	}
	
	@Test
	public  void processNamespace() {
		String xml =  "<ns1:Metadata xmlns:ns1=\"http://ogc.org/ows\" xmlns:ns2=\"http://wrc.org/xlink\" ns2:href=\"http://dude.com\"></ns1:Metadata>";
		String expected = "<ows:Metadata xlink:href=\"http://dude.com\"></ows:Metadata>";
		NameSpaceContextImpl nc = new NameSpaceContextImpl();
		nc.put("ows", "http://ogc.org/ows");
		nc.put("xlink", "http://wrc.org/xlink");
		
		NameSpaceContextImpl local = new NameSpaceContextImpl();
		
		
		Pattern prefixPattern =  Pattern.compile("(\\s|<|/)\\w+:");
		Pattern nsPattern =  Pattern.compile("xmlns:\\w+=\"(\\w|:|/|\\.)*\"");
		Pattern urlPattern = Pattern.compile("\".*\"");
		StringBuilder sb = new StringBuilder(xml);
		
		
		Matcher prefixMatcher = prefixPattern.matcher(sb);
		Matcher nsMatcher = nsPattern.matcher(sb);
		String prefix; 
		String uri;
		
		
		/* process the local namespaces */
		while (nsMatcher.find()) {
			int start = nsMatcher.start();
			int end = nsMatcher.end();
			StringBuilder sbu = new StringBuilder(sb.substring(start,end));
			String thisPrefix = sbu.substring(sbu.indexOf(":")+1, sbu.lastIndexOf("="));
			String thisUri = sbu.substring(sbu.indexOf("\"")+1, sbu.lastIndexOf("\""));
			// add to local namespace context
			local.put(thisPrefix, thisUri);
			
			if((prefix = nc.getPrefix(thisUri)) != null) {
				// namespace is registered, let's remove it
				sb.delete(start-1, end);
				
				// we have to reset, since we changed the state of the matched string with the deletion
				nsMatcher.reset(); 
			}
			
		}
		
		
		/* change the prefixes */
		while (prefixMatcher.find()) {
			int start = prefixMatcher.start();
			int end = prefixMatcher.end();
			
			prefix = sb.substring(start+1,end-1);
			if((uri = local.getNamespaceURI(prefix)) != null) {
				// get the other prefix
				prefix = nc.getPrefix(uri);
				sb.replace(start+1, end-1, prefix);
				prefixMatcher.reset();
			}			
		}
		

		
		
//		boolean elementHasPrefix = false;
//		Set<String>  prefixList = new HashSet<String>();
//		
//		while (prefixMatcher.find()) {
//			String r = matcher.group();
//			String subSequence = r.substring(1, r.length()-1);
//			if(r.charAt(0) == '<') elementHasPrefix = true;
//			prefixList.add(subSequence);
//		}
//		
//		// inject namespaces
//		StringBuilder ns = new StringBuilder();
//		if(! elementHasPrefix) {
//
//			// do nothing, log a warning
//		}
//		else {
//			for(String px : prefixList) {
//				String nsURI = context.getNamespaceURI(px);
//				ns.append("xmlns:").append(px).append("=").append("\"").append(nsURI).append("\" ");
//			}
//			sb.insert(injection_position+1, ns);
//		}
//		
//		return sb.toString();
	}

//	private void purgeNamespace(String )
	
}
