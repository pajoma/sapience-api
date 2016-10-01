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
import java.util.List;
import java.util.Stack;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpressionException;


public class XPathMatcher  {

	private XPathGenerator generator = null;
	
	public XPathMatcher() {
		generator = new XPathGenerator();
	}

	
	/**
	 * Checks if any of the stacks in the array match the one in the first parameter
	 * @param pathStringStack
	 * 	an XPath stack
	 * @param queryStringStacks
	 * 	an array of XPath stacks
	 * @return
	 * @throws XPathExpressionException 
	 * @throws IOException 
	 */
	@Deprecated
	public int matches (Stack<String> pathStringStack, List<Stack<String>> queryStringStacks, boolean exactOnly, NamespaceContext context) throws XPathExpressionException, IOException {
		for(int i = 0; i < queryStringStacks.size(); i++) {
			if(matches(queryStringStacks.get(i), pathStringStack, exactOnly, context)) {
				return i;
			}
		}
		return -1;
	}
	
	public int matches (XPathStack pathStack, List<XPathPattern> queryStacks) throws IOException {
		for(int i = 0; i < queryStacks.size(); i++) {
			if(matches(queryStacks.get(i), pathStack)) {
				return i;
			}
		}
		return -1;
		
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @throws XPathExpressionException 
	 * @throws XPathExpressionException 
	 * @throws IOException 
	 * @Deprecated matching based on XPathElements is faster
	 */	
	@Deprecated 
	public boolean matches(Stack<String> queryStringStack, Stack<String> pathStringStack, boolean exactOnly, NamespaceContext context) throws XPathExpressionException, IOException  {
		throw new RuntimeException("String-based matching not any more supported");
//		if(exactOnly == true) {
//			return matches(queryStringStack, pathStringStack, queryStringStack.size()-1, pathStringStack.size()-1, exactOnly);
//		} else {
//			XPathStack queryStack = generator.asXPathElementStack(queryStringStack, context);
//			XPathStack pathStack = generator.asXPathElementStack(pathStringStack, context);
//			return matches(queryStack, pathStack, queryStringStack.size()-1, pathStringStack.size()-1);
//		}
		
	}
	
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @throws XPathExpressionException 
	 * @throws XPathExpressionException 
	 * @throws IOException 
	 */
	public boolean matches(XPathStack queryStack, XPathStack pathStack) throws  IOException  {
		if( (queryStack==null) || (pathStack == null) ) return false;
		return matches((XPathPattern) queryStack, pathStack, queryStack.size()-1, pathStack.size()-1);
	}

	/**
	 * 
	 * @param p1
	 * @param p2
	 * @throws XPathExpressionException 
	 * @throws XPathExpressionException 
	 * @throws IOException 
	 */
	public boolean matches(XPathPattern queryStack, XPathStack pathStack) throws  IOException  {
		// [//, ogcwfs:WFS_Capabilities[@xsi:schemaLocation='http://www.opengis.net/wfs http://webservices.ionicsoft.com/worldData/wfs/WORLD/REQUEST/get/DATA/LPR/wfs/1.0.0/WFS-capabilities.xsd' and @version='1.0.0'], ogcwfs:FeatureTypeList, ogcwfs:FeatureType, ogcwfs:Name[@attr='22'], text()='wfs:Airport']
		// [//, WFS_Capabilities[@xsi:schemaLocation='http://www.opengis.net/wfs http://webservices.ionicsoft.com/worldData/wfs/WORLD/REQUEST/get/DATA/LPR/wfs/1.0.0/WFS-capabilities.xsd' and @version='1.0.0'], FeatureTypeList, FeatureType, Name[@attr='22'], text()='wfs:Airport']
		
		if( (queryStack==null) || (pathStack == null) ) return false;
		return matches(queryStack, pathStack, queryStack.size()-1, pathStack.size()-1);
	}
	


	/**
	 * Evaluates if the two fields are equal. We don't support any of the functions
	 * The following fields are a match
	 *  (exact match)
	 *  a) elem[@attribute='value']
	 *  b) elem[@attribute='value']	
	 *  
	 *  (subsumption narrow)
	 *  a) elem[@attribute]
	 *  b) elem[@attribute='value']	
	 *  
	 *  (subsumption broad) 
	 *  a) elem
	 *  b) elem[@attribute='value']	
	 * @param a
	 * @param b
	 * @return
	 * @throws XPathExpressionException 
	 * @Deprecated matching based on XPathElements is faster
	 */	
	@Deprecated 
	public boolean matchingElement(String a, String b, NamespaceContext context) throws XPathExpressionException {	
		
		XPathElement xa = generator.asXPathElement(a, context); 
		XPathElement xb = generator.asXPathElement(b, context); 

		
		return xa.matchesQueryPattern(xb);
	}
	


	/**
	 * Returns true, if the two xpath expressions match (which means, they both refer to the same entity. 
	 * 
	 * @return -1 if not found, position in the list of references otherwise 
	 * 
	 * @param p1
	 * @param p2
	 * @throws XPathExpressionException 
	 * @throws IOException 
	 */
	public boolean matchingPath(String a, String b, boolean exactOnly, NamespaceContext c) throws XPathExpressionException, IOException {
		return matches(generator.asXPathStringStack(a, c), generator.asXPathStringStack(b, c), exactOnly, c);
	}

	


	/**
	 * Private recursive method, comparing two elements in the stacks at the two given positions. 
	 * 
	 * Example of two matching stacks (different length, but // is an abbreviation)
	 * Stack 1: //, root, elem, sub
	 * Stack 2: //, elem, sub
	 * 
	 * Example of two matching stacks if relaxed mode is on
	 * Stack 1: //, root, elem[@attr='value'], sub
	 * Stack 2: //, elem[@attr], sub
	 * 
	 * @param a
	 * 	the first stack
	 * @param b
	 * 	the second stack
	 * @param queryPos
	 * 	position in the first stack
	 * @param pathPos
	 * 	position in the second stack
	 * @return
	 * @throws XPathExpressionException 
	 */
	@Deprecated
	private boolean matches(Stack<String> queryStringStack, Stack<String> pathStringStack, int queryPos,  int pathPos,  boolean exact) throws XPathExpressionException {
		
		
		// break recursion
		if((queryPos == 0) || (pathPos == 0)) {	 
			
			// ok, until now everything was equal, which means we are either at the root for both, or one has a shortcut (//)
			return ( ((queryPos == 0)&& isAbbreviation(pathStringStack.get(queryPos))) ||
					 ((pathPos == 0)&& isAbbreviation(queryStringStack.get(pathPos))) ||
					 ((queryPos ==0) && (pathPos ==0)) );
		}		

		
		
		// if the current is NOT a text node in the query, but in the path, we skip it
		if( (! queryStringStack.get(queryPos).startsWith("text()")) && (pathStringStack.get(pathPos).startsWith("text()")))  {
			pathPos--;
		}
		
//		System.out.println(queryStringStack.get(queryPos));
//		System.out.println(pathStringStack.get(pathPos));
		
		// we ignore them for now
		// not relaxed, we do string comparison then
		if(matches(queryStringStack.get(queryPos), pathStringStack.get(pathPos))) {
				return matches(pathStringStack,queryStringStack, pathPos-1, queryPos-1, exact);
		} else 
				return false;
			
		
	
		
		
	}

	
	/**
	 * Basically the same as above, but directly with XPathElements (which means relaxed checking is automatically
	 * set to true)
	 */
	@SuppressWarnings("unused")
	private boolean matches(XPathPattern queryStack, XPathStack pathStack, int queryPos, int pathPos) throws IOException {
		try {
			
			
			
			XPathElement path = pathStack.get(pathPos);
			
			XPathElement query = queryStack.get(queryPos);
			
				
			// break recursion
			if((queryPos <= 0) || (pathPos  <= 0)) {	 
				// ok, until now everything was equal, which means we are either at the root for both, or one has a shortcut (//)
				return ( ((queryPos == 0)&& isAbbreviation(queryStack.get(queryPos).getElementName().getLocalPart())) ||
						 ((pathPos == 0)&& isAbbreviation(pathStack.get(pathPos).getElementName().getLocalPart())) ||
						 ((queryPos ==0) && (pathPos ==0)) );
			}	
			
			// we skip any text nodes (but only in the current path, and if the reference doesn't have a text node)
			if ( (path.toString().startsWith("text()")) &&
			   (!(query.toString().startsWith("text()")))) {
				pathPos--;
			}
				
			
			
			if(pathStack.get(pathPos).matchesQueryPattern(queryStack.get(queryPos))) {
					return matches(queryStack,pathStack,queryPos-1, pathPos-1);
			} else 
					return false;
	
		} catch (NullPointerException e) {
			throw new IOException("Invalid XPath definitions.");
		} catch (Exception e) {
			throw new IOException("Invalid XPath definitions.");
		}
		
	}

	
	private boolean matches(String query, String path) {
		/* TODO: does only match if there's a complete match, e.g. different attribute order would already destroy it #
		 * 
		 *  wps:Capabilities[@xml:lang='en-US' and @updateSequence='1' and @service='WPS' and @version='1.0.0']
		 *  wps:Capabilities[@service='WPS' and @version='1.0.0' and @xml:lang='en-US' and @updateSequence='1']
		 * 
		 * see ticket #48
		 */
		
		return(query.contentEquals(path));
		
	}
	
	private  boolean isAbbreviation(String val) {
		return "//".equals(val);
	}
	
}
