package sapience.injectors.stax.inject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

import sapience.injectors.Configuration;
import sapience.injectors.util.LocalNamespaceContext;

public class NamespaceHelper {
	private final Configuration config;
	
	public NamespaceHelper(Configuration config) {
		this.config = config;
		
	}
	private Pattern prefixPattern =  Pattern.compile("(\\s|<|/|@)\\w+:");
	private Pattern nsPattern =  Pattern.compile("xmlns:\\w+=\"(\\w|:|/|\\.)*\"");
	private Pattern nsDefaultPattern =  Pattern.compile("xmlns=\"(\\w|:|/|\\.)*\"");

	/**
	 * Helper method, taking a XML string like <ows:Metadata xmlns:ows=\"http://ogc.org/ows\" xmlns="http://example" 
	 * xmlns:xlink=\"http://wrc.org/xlink\" xlink:href=\"http://dude.com\"></ows:Metadata> from the reference 
	 * and checks if 
	 * a  the used prefixes match the globally used ones and
	 * b) any of the declared namespaces are redundant 
	 * 
	 * The same is true for the XPath definition
	 * 
	 * @param resultingXMLString
	 * @param context
	 */
	void processNamespace(StringBuilder sb, NamespaceContext global, LocalNamespaceContext local) {
	

		
		
		Matcher nsMatcher = nsPattern.matcher(sb);
		
		
		String prefix; 
		String uri;
		
		
		/* process the local namespaces: if the uri assigned to one of the prefixes used in the expression
		 * is also in our global context, we remove the uri
		 */
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
		
		/* process the default namespace (without a prefix). If there is a default namespace in the global context, 
		 * and it is the same as our local namespace, we remove it 
		 * 
		 */
		Matcher defaultMatcher = nsDefaultPattern.matcher(sb);
		if (defaultMatcher.find()) {
			int start = defaultMatcher.start();
			int end = defaultMatcher.end();		
			String d = sb.substring(start+7, end-1);
			String n = global.getNamespaceURI(XMLConstants.DEFAULT_NS_PREFIX);
			if((n != null) && (n.equalsIgnoreCase(d))) {
				sb.delete(start-1, end);
			}
			defaultMatcher.reset();
		}
		
		
		
		/* change the prefixes */
		Matcher prefixMatcher = prefixPattern.matcher(sb);
		try {
			while (prefixMatcher.find()) {
				int start = prefixMatcher.start();
				int end = prefixMatcher.end();
				
				String localprefix = sb.substring(start+1,end-1);
				if((global.getNamespaceURI(localprefix) == null) && (uri = local.getNamespaceURI(localprefix)) != null) {
					// get the other prefix
					prefix = global.getPrefix(uri);
						
					if((prefix != null)&&(! (localprefix.contentEquals(prefix)))) {
						sb.replace(start+1, end-1, prefix);
						prefixMatcher.reset();
					}
				}			
			}
		} catch (StringIndexOutOfBoundsException  e) {
			// we do nothing here
			e.printStackTrace();
		}

	}

	
	/**
	 * Helper method, takes an XML reference with local namespace definitions, and returns the namespaces as QNames
	 * @param xml
	 * @return
	 */
	List<QName> extractNamespaces(String xml) {
		List<QName> res = new ArrayList<QName>();
		
		Matcher nsMatcher = nsPattern.matcher(xml);
		while (nsMatcher.find()) {
			int start = nsMatcher.start();
			int end = nsMatcher.end();
			StringBuilder sbu = new StringBuilder(xml.substring(start,end));
			String prefix = sbu.substring(sbu.indexOf(":")+1, sbu.lastIndexOf("="));
			String uri = sbu.substring(sbu.indexOf("\"")+1, sbu.lastIndexOf("\""));
			res.add(new QName(uri,prefix));
		}
		return res;
	}

}
