/**
 * Copyright (C) 2010 Institute for Geoinformatics (ifgi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * ITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package sapience.injectors.model.xpath;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

public class XPathAttribute implements Serializable {

	private static final long serialVersionUID = 1042294553914646775L;
	
	// prefix:key=value or http://example.com#key=value 
	private final String value;
	private final QName name; 
	
	
	
	private static Pattern nsWithPrefix; 
	private static Pattern nsAsURL;
	private NamespaceContext context = null;
	
	static {
		nsWithPrefix = Pattern.compile(".+:.+");
		nsAsURL = Pattern.compile(".+://.+");
	}
	
	
	public XPathAttribute(QName key, String value) {
		this.value = value;
		this.name = key;
	}
	
	public XPathAttribute(String key, String value, NamespaceContext context) {
		this.value = preprocessValue(value);
		
		this.context = context;
		
		this.name = preprocessName(key);
		
		
	}




	private QName preprocessName(String key) {
		if(key.startsWith("@")) key = key.substring(1, key.length());

		// key is something like "prefix:value"
		if(nsWithPrefix.matcher(key).matches()) 
		{
			String[] s = key.split(":");
			String ns = context.getNamespaceURI(s[0]);
			if((ns == null) || (ns.length() == 0)) {
				
			}
			
			return new QName(context.getNamespaceURI(s[0]), s[1], s[0]);
			
		} 
		// key is "http://example.com#value"
		else if(nsAsURL.matcher(key).matches()) 
		{
			String[] s = key.split("#");
			return new QName(s[0], s[1], context.getPrefix(s[0]));
		} 
		// key is not qualified, we throw an exception
		else {
//			if (logger.isLoggable(Level.WARNING)) {
//				logger.log(Level.WARNING, "Unknown namespace for attribute: "+key);
//			}
			return new QName(key);
		}		
	}

	private String preprocessValue(String v) {
		if((v == null)||(v.length() == 0)) {
			return "";
		}
		
		if(v.charAt(0) == '\'') {
			return v.substring(1, v.length()-1);
		}
		
		return v;
	}

	public String getValue() {
		return value;
	}




	public QName getKey() {
			return name;
	}

	/**
	 * Two QNames match, if all the existing properties match. 
	 * 
	 * 	
	 * @param key
	 * @return
	 */
	public boolean matchingKey(QName key) {
		if(! (this.getKey().getLocalPart().contentEquals(key.getLocalPart()))){
			return false;
		}
		
		// same local names, let's check if namespaces are equal if they have any
		if( (key.getNamespaceURI() != null) &&
			(getKey().getNamespaceURI() != null) &&
			(!(key.getNamespaceURI().contentEquals(getKey().getNamespaceURI())))
			) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("@");
		
		String prefix = getKey().getPrefix();
		// try to resolve from namespace context
		if((prefix.length() == 0) && (getKey().getNamespaceURI().length() > 0) && (context != null) )
			prefix = context.getPrefix(getKey().getNamespaceURI());
		if((prefix != null) && (prefix.length() > 0)) 
			str.append(prefix).append(":");
		
		str.append(getKey().getLocalPart());
		
		if(getValue().length() > 0) {
			str.append("=");
			if(getValue().charAt(0) == '\'') 
				str.append(getValue()); 
			else 
				str.append("'").append(getValue()).append("'"); 
		} else {
			str.append("=\'\'");
		}
		
		

		
		return str.toString();
	}


	
}