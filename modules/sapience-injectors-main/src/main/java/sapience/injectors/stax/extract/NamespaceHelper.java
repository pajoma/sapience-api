package sapience.injectors.stax.extract;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;

import sapience.injectors.Configuration;

public class NamespaceHelper {
	private Pattern prefixPattern =  Pattern.compile("(\\s|<|/|@)\\w+:");
	private Pattern noPrefixPattern = Pattern.compile("(/\\w+\\[)");
	private final Configuration config;
	
	public NamespaceHelper(Configuration config) {
		this.config = config;
		
	}
	

	/**
	 * Helper method, taking a XML string like 
	 *<ows:Metadata xlink:href=".."></ows:Metadata>
	 * injects local namespace declarations, something like  
	 * <ows:Metadata xmlns:ows=\"http://ogc.org/ows\" xmlns:xlink=\"http://wrc.org/xlink\" xlink:href=\"http://dude.com\"></ows:Metadata>
	 * 
	 * @param resultingXMLString
	 * @param context
	 */
	StringBuilder injectNamespace(StringBuilder sb, String currentPath,  NamespaceContext context) {
		Matcher matcher = prefixPattern.matcher(sb);
		Matcher noPrefix = noPrefixPattern.matcher(sb);
		
		int injection_position = sb.indexOf(" ");

		boolean elementHasPrefix = false;
		Set<String>  prefixList = new HashSet<String>();

		// find prefixes in the reference XML
		while (matcher.find()) {
			String r = matcher.group();
			String subSequence = r.substring(1, r.length()-1);


			if(r.charAt(0) == '<') elementHasPrefix = true;
			prefixList.add(subSequence);
		}

		// find prefixes in the current xpath

		matcher = prefixPattern.matcher(currentPath);
		while (matcher.find()) {
			String r = matcher.group();
			prefixList.add(r.substring(1, r.length()-1));
		}





		// inject namespaces
		StringBuilder ns = new StringBuilder();
		if(! elementHasPrefix) {
			// do nothing, log a warning
		}
		else {
			for(String px : prefixList) {
				String nsURI = context.getNamespaceURI(px);
				ns.append("xmlns:").append(px).append("=").append("\"").append(nsURI).append("\" ");
			}

			// let's see if there are elements with out a prefix
			if(noPrefix.find()) {
				// add the default namespace as defined in the configuration if there has been one element without a qualified namespace
				String def = this.config.getDefaultNamespace();
				if((def!=null)&&(def.length()>0)) {
					ns.append("xmlns").append("=").append("\"").append(def).append("\" ");
				}

			}
			
			sb.insert(injection_position+1, ns);
		}

		return sb;
	}
}
