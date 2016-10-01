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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import sapience.injectors.model.LocalElement;
import sapience.injectors.model.Reference;
import sapience.injectors.util.LocalNamespaceContext;

import com.ctc.wstx.util.BaseNsContext;



/**
 * A couple of util methods helping to render in between the various representations of XPath 
 * @author 
 * 
 * TODO: add documentation
 *
 */
public class XPathGenerator {
	
	// singleton instance
	private static XPathGenerator instance;

	private static final Logger logger = Logger.getLogger(XPathGenerator.class.getName());
	
	
	// used to check if given string is a valid xpath
	private XPath compiler; 

	
	public XPathGenerator() {
//		context = ConfigurationFactory.get().getNamespaceContext();
		compiler = XPathFactory.newInstance().newXPath();
//		compiler.setNamespaceContext(context);
	}
		

	/**
	 * Serialization of Stack of XPathElements
	 * @param stack the stack
	 * @return the String 
	 * @throws IOException 
	 * @Deprecated use serialize in Xpathstack
	 */
	@Deprecated
	public String serializeXPathElementStack(XPathStack stack, NamespaceContext context) throws IOException {


		StringBuilder result = new StringBuilder("/");
		Iterator<XPathElement> each = stack.iterator();
		while (each.hasNext()) {
			String next = asXPathString(each.next(), context);
			if(next.charAt(0) != '/')  {
				result.append('/').append(next);
			}
		}
		

		return result.toString();

	}
	
	
	/** 
     * Helper to convert a path stack into a readable/parsable XPath string. 
     * @Deprecated use serialize in Xpathstack
     */ 
	@Deprecated
    public String serializeXPathStringStack(Stack<String> stack)  
     	{ 
 
		StringBuilder result = new StringBuilder("/");
		Iterator<String> each = stack.iterator();
		while (each.hasNext()) {
			String next = each.next();
			if(next != "//")  result.append('/').append(next);
		}
			
		return result.toString(); 
     }  
    
    /**
     * Converts a list of strings representing xpaths into their stack counterparts
     * @param refs a list of strings representing complete XPath
     * @return
     * @throws XPathExpressionException 
     * @throws IOException 
     */
    public List<XPathPattern> asXPathPatterns(List<String> xpath, NamespaceContext context) throws XPathExpressionException, IOException {
     	List<XPathPattern> res = new ArrayList<XPathPattern>();
     	for(String str : xpath) {
     		
     		
     		XPathPattern stack = asXPathPattern(str, context);
     		if(stack != null) res.add(stack);
     	}	
    	return res;
	}
	

    
    /**
     * @param stack
     * @return
     * @throws XPathExpressionException
     * @throws IOException 
     */
    public XPathStack asXPathElementStack(Stack<String> stack, NamespaceContext context)  throws XPathExpressionException, IOException {    	
    	if(stack == null) return null;
    	
    	XPathStack res = new XPathStack();
    	Iterator<String> it = stack.iterator();
    	while(it.hasNext()) {
    		res.add(asXPathElement(it.next(), context));
    	}
    	return res;
    }

    @Deprecated
    public Stack<String> asXPathStringStack(Serializable xpath, List<QName> namespaces, NamespaceContext pContext) throws IOException  
    {  
    	if(pContext == null) throw new IOException("Context can't be null");
    	
    	LocalNamespaceContext context = new LocalNamespaceContext((BaseNsContext) pContext); 
    	for(QName name : namespaces) {
    		context.put(name);
    	}
    	return asXPathStringStack(xpath, context);
    }
    
    
   
	private Pattern qnamePattern =  Pattern.compile("\\{http://(\\w|\\.|/|_)*\\}");

	private String defaultNamespace;
    /**
     * Helper to convert a XPath represented as String into a Stack of its individual elements (still represented as String)
     * @param xpath the serialized xpath
     * @return a stack with the invididual xpath elements as string
     * @throws IOException 
     *
     */
	@Deprecated
    public Stack<String> asXPathStringStack(Serializable xpath, NamespaceContext context) throws IOException  
     {  
    	if(context == null) throw new IOException("Context can't be null");
    	
    	String path = xpath.toString();
    	Matcher m = qnamePattern.matcher(path);
    	
    	// if we have URIs in {} defined, we have to replace them with their prefixes from the current namespace
    	while (m.find()) {
    		String uri = m.group();    		   		
    		String prefix = context.getPrefix(uri.substring(1, uri.length()-1));
    		if(prefix == null) {
    			// no prefix defined, this is not a valid pattern for this document
    			return null;
    		}
    		
    		path = path.replace(uri, prefix.concat(":"));
			
		}
    	
    	
    	// we compile it once to to check if this is a valid expression
    	try {
			compiler.compile(path);
		} catch (XPathExpressionException e) {
			throw new IllegalArgumentException("Not a valid XPath: "+xpath.toString(), e);
		}
    	
       	/* the string //a/b/c[..]/d is split up into 
    	 * //,a,b,c[..],d 
    	 * 
    	 */
		Stack<String> res = new Stack<String>();
		if((path.charAt(0) == '/') && path.charAt(1) == '/')  {
			res.add("//");
			path = path.substring(2);	// we remove the trailing abbreviation //
			}
		
		
		// we can not simply split the string using the "/", since we also have URLs in there (which would get split up as well)		
		StringBuilder sb = new StringBuilder();
		boolean literal = false;
		for(char c : path.toCharArray()) {
			
			switch (c) {
			case '/':
				if(!literal) {
					res.push(sb.toString());
					sb = new StringBuilder();
				} else {
					sb.append(c);
				}
				break;
			case '\'': // encloses literal
				literal = !literal;
				sb.append(c);
				break;
			case '\"': // encloses literal
				literal = !literal;	
				sb.append(c);
				break;
			default:
				sb.append(c);
				break;
			}
		}
		
		// the last bit has to be added as well (xpath expressions don't end with a slash, which is our our split token)
		res.add(sb.toString());
    	return res;
     }  

    /**
     * Transforms a serialized XPath into a stack of XPathElements 
     * @param xpath the serialized XPath
     * @return a Stack of XPathElement
     * @throws XPathExpressionException 
     * 		if any of the XPathElements contains invalid code
     * @throws IOException 
     * @see XPathElement
     */
//    public static Stack<XPathElement> asXPathElementStack(Serializable xpath, NamespaceContext context) throws XPathExpressionException, IOException {
//    	Stack<String> asString = asXPathStringStack(xpath, context);
//    	return asXPathElementStack(asString, context);
//    }
    
	
	 public XPathPattern asXPathPattern(Serializable xpath, NamespaceContext context) throws IOException   {

		 StringBuilder path = new StringBuilder(xpath.toString());
		 XPathPattern pattern = new XPathPattern();

		 // extract unique id from pattern (the pattern ends with @343214, where the number is the hashcode of the reference to inject)
		 int pos = path.lastIndexOf("~"); 
		 if(pos > 0) {
			 pattern.setUniqueID(path.substring(pos+1)); 
			 path = path.delete(pos, path.length());
		 }

		 /* the following extensions are supported (default is attribute extension)
		  *   //path1/path2/element[@attr]/child()  the matching element is injected as child of element //path1/path2
		  *   //path1/path2/element[@attr]/sibling()  the matching element is injected as sibling of element //path1/path2
		  *   //path1/path2/example[@attr]/attribute() the matching element is injected as attribute into the element //path1/path2/example
		  */
		 pattern.setType(path.substring(path.lastIndexOf("/")+1));
		 if(pattern.getType()>0) {
			 path = path.delete(path.lastIndexOf("/"), path.length());
		 }

		 XPathStack p = asXPathElementStack(path,context);
		 pattern.addAll(p);


		 return pattern;
	 }
	
	/**
     * Helper to convert a XPath represented as String into a Stack of its individual elements (still represented as String)
     * @param xpath the serialized xpath
     * @return a stack with the invididual xpath elements as string
     * @throws IOException 
     *
     */
    public XPathStack asXPathElementStack(Serializable xpath, NamespaceContext context) throws IOException  
     {  
    	try {
    		if(context == null) throw new IOException("Context can't be null");
        	
    		XPathStack res = new XPathStack();
        	XPathElement item;	
        	String path = xpath.toString();
        	Matcher m = qnamePattern.matcher(path);
        	
        	// if we have URIs in {} defined, we have to replace them with their prefixes from the current namespace
        	while (m.find()) {
        		String uri = m.group();    		   		
        		String prefix = context.getPrefix(uri.substring(1, uri.length()-1));
        		if((prefix == null)&&(uri != null)) {
        			// default namespace
        			path = path.replace(uri, "");
        		} else if(prefix == null) {
        			// no prefix defined, this is not a valid pattern for this document
        			return null;
        		} else {
        			path = path.replace(uri, prefix.concat(":"));
        		}
        		
        	
    			
    		}
        	
        	
        	// we compile it once to to check if this is a valid expression
        	try {
        		compiler.setNamespaceContext(context);
    			compiler.compile(path);
    		} catch (XPathExpressionException e) {
    			throw new IllegalArgumentException("Not a valid XPath: "+xpath.toString(), e);
    		}
        	
           	/* the string //a/b/c[..]/d is split up into 
        	 * //,a,b,c[..],d 
        	 * 
        	 */
    	
    		if((path.charAt(0) == '/') && path.charAt(1) == '/')  {
    			item = asXPathElement("//", context);
    			if(item != null) res.push(item);
    			path = path.substring(2);	// we remove the trailing abbreviation //
    			}
    		
    		
    		// we can not simply split the string using the "/", since we also have URLs in there (which would get split up as well)		
    		StringBuilder sb = new StringBuilder();
    		boolean literal = false;
    		for(char c : path.toCharArray()) {
    			
    			switch (c) {
    			case '/':
    				if(!literal) {
    					item = asXPathElement(sb.toString(), context);
    					if(item != null) res.push(item);
    					sb = new StringBuilder();
    				} else {
    					sb.append(c);
    				}
    				break;
    			case '\'': // encloses literal
    				literal = !literal;
    				sb.append(c);
    				break;
    			case '\"': // encloses literal
    				literal = !literal;	
    				sb.append(c);
    				break;
    			default:
    				sb.append(c);
    				break;
    			}
    		}
    		
    		// the last bit has to be added as well (xpath expressions don't end with a slash, which is our our split token)
    		item = asXPathElement(sb.toString(), context);
    		if(item != null) res.push(item);
        	return res;
		} catch (XPathExpressionException e) {
			throw new IOException(e);
		}
    	
    	
     }  
	
	/**
	 * Renders one XPath Element (without the trailing slash)
	 * @param elem the XPathelement to serialize
	 * @return the XPathElement as Stirng
	 * @throws IOException 
	 * @see XPathElement
	 * @Deprecated use serialize method in XPathElement
	 */
    @Deprecated
	public String asXPathString(XPathElement elem, NamespaceContext context) throws IOException {
		StringBuilder str = new StringBuilder();
		
		// beware of namespace prefix, read them from the namespace	prefix map if necessary
		QName name = elem.getElementName();
		String prefix = name.getPrefix();
		String namespaceURI = name.getNamespaceURI();
		
		
		if(prefix.length() == 0) {	// try to resolve from namespace context 
			if(namespaceURI.length() == 0) {
				// ok, not defined, we use the default namespace
				
				if (logger.isLoggable(Level.FINE)) {
					logger.log(Level.FINE, ("No valid namespace defined for: "+name));
				} 
				
				prefix = "";
			} else {
				prefix = context.getPrefix(name.getNamespaceURI());
			}
			
		}
		if((prefix != null)&&(prefix.length() > 0)) {	// 
			str.append(prefix).append(":");
		}
		str.append(name.getLocalPart());
		
		Collection<XPathAttribute> m = elem.getAttributes();
		int size = m.size();
		if(size > 0) {
			str.append('[');
			for(XPathAttribute e : m) {
				str.append(e.toString());
				if(--size > 0) str.append(" and "); 	
			}
			str.append(']');
		}


		return str.toString();
	}
	
	
	/**
	 * Takes a string, e.g. prefix:element[prefix:attribute='value'], and creates a XPathElement from it. 
	 * All prefixes have to be resolvable, otherwise we throw an XPathExpressionException
	 * 
	 * @param the string to be transformed 
	 * @return	the resulting XPathElement
	 * @throws XPathExpressionException 
	 * @see XPathElement
	 */
	public XPathElement asXPathElement(String a, NamespaceContext context) throws XPathExpressionException {
		XPathElement res = null;
		StringBuilder sb = new StringBuilder(a);
		
		
		

		/*
		 * identify if there's an attribute, and if any, remove it from the string parameter
		 */
		int pos = sb.indexOf("[");
		CharSequence attributesString = null;
		if(pos >= 0) {  // the string contains attributes
			attributesString = sb.subSequence(pos+1, sb.length()-1);
			sb.delete(pos, sb.length()); // delete attributes string
		}
	
		/*
		 * 
		 */
		
		/*
		 * handle qualified name of element (could be a URI, a prefix, or nothing at all)
		 */
		String[] array;
		
		
		
		// is text only (for example "text()='wfs:feature') the text can contain anything 
		if(sb.toString().startsWith("text()=")) {
			res = new XPathElement(new QName(sb.toString())); 
			
		
 		// is a URL with artefact
		} else if((array = sb.toString().split("#")).length == 2) {	
			res = new XPathElement(new QName(array[0], array[1]));
			
			// is with prefix, e.g. prefix:value, we have to resolve the prefix from the namespace context
		} else if((array = sb.toString().split(":")).length == 2) {
			String uri = context.getNamespaceURI(array[0]);
			res = new XPathElement(new QName(uri, array[1], array[0]));
		} else {
			res = new XPathElement(new QName(sb.toString()));
		}

		/*
		 * handle the attributes
		 */
		if((attributesString != null) && (attributesString.length() > 0)) {
			String[] attributes = attributesString.toString().split(" and ");
			String name = "";
			String value = "";
			for(String str : attributes) {
				if(str.contains("=")) {
					String[] attr = str.split("=");
					if(attr.length == 2) {
						name = attr[0];
						value =  attr[1];
					} else {
						throw new XPathExpressionException("Invalid attributes: "+attributesString);
					}
		
				} else {
						name = str;
				}
				
				if(! (name.startsWith("@"))) throw new XPathExpressionException("Invalid attributes: "+attributesString);
				res.addAttribute(name.substring(1, name.length()), value, context);
			}
		}
		
		
		return res;
	}

	
	/**
	 * Transform the XML Event Attribute into a String
	 * @param a the Attribute to transform
	 * @see Attribute
	 */
	public String asXPathString(Attribute a) {
		StringBuilder pb = new StringBuilder();
		QName q = a.getName();
		
		pb.append("@");
		if(q.getPrefix().length() > 0) {
			pb.append(q.getPrefix());
			pb.append(":");
		}
		
		pb.append(q.getLocalPart());
		pb.append("='");
		pb.append(a.getValue());
		pb.append("'");
		
		return pb.toString();
		
	}

	public String asXPathString(StartElement se) {
		StringBuilder pb = new StringBuilder();
		// element has attributes, we have to add them to the xpath for identification
		Iterator<?> attributes = se.getAttributes();
		pb.append(se.getName().getPrefix()).append(":");
		pb.append(se.getName().getLocalPart());
		
		if(attributes.hasNext()) {
			pb.append("[");
			while(attributes.hasNext()) {
				Attribute next = (Attribute) attributes.next();
				pb.append(asXPathString(next));
				if(attributes.hasNext()) pb.append(" and ");
			}
			pb.append("]");
		}
		return pb.toString();
	}

	public XPathElement asXPathElement(StartElement se) throws XPathExpressionException {
		XPathElement result = new XPathElement(se.getName());

		Iterator<?> it = se.getAttributes();
		while(it.hasNext()) {
			Attribute next = (Attribute) it.next();
			result.addAttribute(next.getName(), next.getValue());
		}
		return result;
	}
	
	
	/**
	 * Converts the XPath-Expressions in the list of references into XPath-Stacks
	 * @param refs
	 * @return
	 * @throws IOException 
	 */
	public List<Stack<String>> referencesAsList(List<Reference> refs, NamespaceContext context) throws IOException {
		List<Stack<String>> res = new ArrayList<Stack<String>>();
     	for(Reference reference : refs) {
     		Serializable elementID = ((LocalElement) reference.getSource()).getElementID();
     		res.add(asXPathStringStack(elementID, context));
     	}	
    	return res;
	}

	public XPathElement asXPathElement(Characters ch) {
		String string = asXPathString(ch);
		return string != null ? new XPathElement(new QName(string)) : null;  
	}

	public String asXPathString(Characters ch) {
		String str; 
		if((str = ch.getData().trim()).length() == 0) return null;
		//if(str.length() > 64) str = str.substring(0, 64);
		return new StringBuilder("text()=\'").append(str).append('\'').toString();
	}


	public void setDefaultNamespace(String defaultNamespace) {
		this.defaultNamespace = defaultNamespace;
	}
}
