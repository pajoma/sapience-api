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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

/**
 * Represents one particular XPath element in the path, e.g.
 * /element[attribute='value']
 * 
 * 
 * 
 * @author puky, pajoma
 * 
 */
public class XPathElement implements Serializable {
	private static final long serialVersionUID = 5436071145095659534L;
	private Map<QName, XPathAttribute> attributes = null;

	private final QName localName;

	public XPathElement(String namespaceURI, String localName) {
		this(new QName(namespaceURI, localName));
	}

	/**
	 * Shortcut, creating an Element with exactly one attribute
	 * 
	 * @param uri
	 * @param qName
	 * @param aName
	 * @param value
	 */
	public XPathElement(String uri, String local, String attribute_key,
			String value) {
		this(uri, local);
		addAttribute(attribute_key, value, null);
	}

	public XPathElement(QName name) {
		this.localName = name;
	}

	public Collection<XPathAttribute> getAttributes() {
		return getAttributeMap().values();
	}

	/**
	 * @return a map with all attributes of the element
	 */
	public Map<QName, XPathAttribute> getAttributeMap() {
		if (attributes == null) {
			attributes = new HashMap<QName, XPathAttribute>();
		}

		return attributes;
	}

	public XPathElement addAttribute(String key, String value,
			NamespaceContext context) {
		XPathAttribute attr = new XPathAttribute(key, value, context);
		getAttributeMap().put(attr.getKey(), attr);
		return this;
	}

	public XPathElement addAttribute(QName name, String value) {
		XPathAttribute attr = new XPathAttribute(name, value);
		getAttributeMap().put(attr.getKey(), attr);
		return this;

	}

	public String getAttributeValue(QName key) {
		for (XPathAttribute attr : this.getAttributes()) {
			if (attr.matchingKey(key)) {
				return attr.getValue();
			}
		}
		return null;
	}

	public QName getElementName() {
		return localName;
	}

	public boolean hasAttributes() {
		return (getAttributeMap().size() > 0);
	}

	/**
	 * 
	 * Evaluates if this XPathElement matches another. We don't support any of
	 * the functions
	 * 
	 * The following fields are a match 
	 * 
	 * (exact match = equals) 
	 * a) elem[@attribute='value'] b) elem[@attribute='value']
	 * 
	 * (subsumption match) 
	 * a) elem[@attribute] (pattern) b) elem[@attribute='value']
	 * 
	 * (subsumption match) 
	 * a) elem (pattern) b) elem[@attribute='value']
	 * 
	 * (no match) 
	 * a) elem[@myAttribute] b) elem[@yourAttribute='value']
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	public boolean matchesQueryPattern(XPathElement pattern) {
		boolean result = true;
		int foundAttributes = 0;
		

		

		// same name (means, same namespace AND same localname
		result &= this.getElementName().equals(pattern.getElementName());
		// System.out.println(this.getElementName().getLocalPart());//TODO:
		// sysout
		// System.out.println(this.getElementName().getNamespaceURI());
		// System.out.println(pattern.getElementName().getLocalPart());
		// System.out.println(pattern.getElementName().getNamespaceURI());

		// only if both have attributes specified we consider them
		if (result && (this.getAttributeMap().size() > 0)
				&& (pattern.getAttributeMap().size() > 0)) {
			// for every attribute in first lists we check in the other
			for (Entry<QName, XPathAttribute> patternEntry : pattern
					.getAttributeMap().entrySet()) {
				// go through our local attributes
				boolean matching_attributes = false;
				for (XPathAttribute thisAttr : this.getAttributes()) {
					// if one of our local attributes matches the key
					if (thisAttr.matchingKey(patternEntry.getKey())) {
						// if there's a value in the query, let's see if we the
						// same
						if (patternEntry.getValue().getValue().length() > 0) {
							String value = patternEntry.getValue().getValue();
							String value2 = thisAttr.getValue();
							if (patternEntry.getValue().getValue()
									.equalsIgnoreCase(thisAttr.getValue())) {
								matching_attributes = true;
							}
						} else {
							matching_attributes = true; // matching key, and no
														// value defined, yay
						}
						break;
					}
				}
				// that's a bit weird, but have to ensure that this is reset to
				// false even if the pattern attribute is not contained in the
				// current one
				if (matching_attributes) {
					foundAttributes++;
					matching_attributes = false;
				}

			}
		}

		result &= (foundAttributes == pattern.getAttributes().size());

		return result;
	}

	@Override
	public String toString() {
		return this.serialize(new StringBuilder(), null).toString();
	}

	/**
	 * Checks if the current element is a character node
	 * 
	 * @return
	 */
	public boolean isCharacters() {
		return getElementName().getLocalPart().startsWith("text()");
	}

	public StringBuilder serialize(StringBuilder str, NamespaceContext context) {

		// beware of namespace prefix, read them from the namespace prefix map
		// if necessary
		QName name = this.getElementName();
		String prefix = name.getPrefix();
		String namespaceURI = name.getNamespaceURI();

		if (prefix.length() == 0) { // try to resolve from namespace context
			if (namespaceURI.length() == 0) {
				prefix = "";
			} else {
				if(context != null) {
					prefix = context.getPrefix(name.getNamespaceURI());
					
				}
				
			}

		}
		if ((prefix != null) && (prefix.length() > 0)) { // 
			str.append(prefix).append(":");
		} else if( (prefix.length() == 0) && (namespaceURI.length() > 0)) { 
			// no prefix, but namespace defined (e.g.: content of xmlns="" -> keep url in {}
			str.append("{").append(namespaceURI).append("}"); 
		}
		
		str.append(name.getLocalPart());

		Collection<XPathAttribute> m = this.getAttributes();
		int size = m.size();
		if (size > 0) {
			str.append('[');
			for (XPathAttribute e : m) {
				str.append(e.toString());
				if (--size > 0)
					str.append(" and ");
			}
			str.append(']');
		}

		return str;

	}

}
