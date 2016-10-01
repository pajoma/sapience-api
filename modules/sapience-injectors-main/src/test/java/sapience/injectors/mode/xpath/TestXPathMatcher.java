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

/**
 * 
 */
package sapience.injectors.mode.xpath;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Stack;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;


import sapience.injectors.model.xpath.XPathElement;
import sapience.injectors.model.xpath.XPathGenerator;
import sapience.injectors.model.xpath.XPathMatcher;
import sapience.injectors.util.LocalNamespaceContext;

/**
 * @author pajoma
 *
 */
public class TestXPathMatcher {
	
	String xpath1 = "//aRoot/bElem/cElem[@cAttr='value']/dElem[@dAttr1='value' and @dAttr2='3']";
	String xpath2 = "//aRoot/bElem/cElem[@cAttr='value']/dElem[@dAttr2='3' and @dAttr1='value']";
	private Stack<XPathElement> stack;
	private Stack<String> stack2;
	private NamespaceContext context;
	private XPathMatcher matcher; 
	private XPathGenerator generator; 
	
//	@Before
	public void prepareStacks() {
		context = new LocalNamespaceContext();
		matcher = new XPathMatcher();
		generator = new XPathGenerator();
	}
	
	 @Test
	public void testSomething() {
		assertTrue(true);
	}
	
//	@Test
	public void testExactMatch() throws XPathExpressionException{
		String string1 = "elem[@attr='value']";
		String string2 = "elem[@attr='value']";
		
		assertTrue(matcher.matchingElement(string1, string2, context));
	}
	
//	@Test
	public void testBroadMatch() throws XPathExpressionException{
		String string1 = "elem[@attr='value']";
		String string2 = "elem[@attr]";
		
		assertTrue(matcher.matchingElement(string1, string2, context));
	}
	
//	@Test
	public void testNoMatch() throws XPathExpressionException{
		String string1 = "elem[@attr='value']";
		String string2 = "elem[@attr='nomatch']";
		
		assertFalse(matcher.matchingElement(string1, string2, context));
	}
	
//	@Test
	public void testNarrowMatch() throws XPathExpressionException{
		String string1 = "elem[@attr='value']";
		String string2 = "elem[@attr='value']";
		
		assertTrue(matcher.matchingElement(string1, string2, context));
	}
	
//	@Test
	public void testRealLifeMatch() throws XPathExpressionException{
		String string1 = "Metadata[@xlink:href='http://example.com/WebServices/40931e28a' and @xlink:arcrole='http://annot.com/WebService']";
		String string2 = "Metadata[@xlink:href and @xlink:arcrole]";
		
		assertTrue(matcher.matchingElement(string1, string2, context));
	}
	
//	@Test
	public void testAnotherRealLifeMatch() throws XPathExpressionException{
		String string1 = "Metadata[@xlink:title='BombThreatScenario']";
		String string2 = "Metadata[@xlink:href and @xlink:arcrole]";
		
		assertFalse(matcher.matchingElement(string1, string2, context));
	}
	
//	@Test
	public void testOhNoNamespaces() throws XPathExpressionException{
		String string1 = "Metadata[@ns2:href='value' and @ns2:arcrole='value']";
		String string2 = "Metadata[@xlink:href and @xlink:arcrole]";
		
		assertTrue(matcher.matchingElement(string1, string2, context));
	}
	
	
//	@Test
//	public void testPathWithText() throws XPathExpressionException, IOException{
//		String query = "//ProcessDescriptions/ProcessDescription[@processVersion='1' and @statusSupported='false' and @storeSupported='true']/DataInputs/Input/Identifier";
//		String path = "//ProcessDescriptions/ProcessDescription[@processVersion='1' and @statusSupported='false' and @storeSupported='true']/DataInputs/Input/Identifier/text()=-979906409";
//		
//		Stack<XPathElement> qStack = generator.asXPathElementStack(query, context);
//		Stack<XPathElement> pStack = generator.asXPathElementStack(path, context);
//		
//		assertTrue(matcher.matches(qStack, pStack));
//	}
	
//	@Test
//	public void testPathWithTextReverse() throws XPathExpressionException, IOException{
//		String query = "//ProcessDescriptions/ProcessDescription[@processVersion='1' and @statusSupported='false' and @storeSupported='true']/DataInputs/Input/Identifier/text()=-979906409";
//		String path = "//ProcessDescriptions/ProcessDescription[@processVersion='1' and @statusSupported='false' and @storeSupported='true']/DataInputs/Input/Identifier";
//		
//		Stack<XPathElement> qStack = generator.asXPathElementStack(query, context);
//		Stack<XPathElement> pStack = generator.asXPathElementStack(path, context);
//		
//		// if the query contains a text() - definition, the path has to have it as well
//		assertFalse(matcher.matches(qStack, pStack));
//	}
	
//	@Test
	public void testPathWithTextOnly() throws XPathExpressionException, IOException{
		String query = "//ProcessDescriptions/ProcessDescription[@processVersion='1' and @statusSupported='false' and @storeSupported='true']/DataInputs/Input/Identifier";
		String path = "//ProcessDescriptions/ProcessDescription[@processVersion='1' and @statusSupported='false' and @storeSupported='true']/DataInputs/Input/Identifier/text()=-979906409";
		
		Stack<String> q = generator.asXPathStringStack(query, context);
		Stack<String> p = generator.asXPathStringStack(path, context);
		
		assertTrue(matcher.matches(q, p, false, context));
		assertTrue(matcher.matches(q, p, true, context));
	}
	
	
	
//	@Test
	public void bothWithText() throws XPathExpressionException, IOException{
		String query = "//ProcessDescriptions/ProcessDescription[@processVersion='1' and @statusSupported='false' and @storeSupported='true']/DataInputs/Input/Identifier/text()=-979906409";
		String path = "//ProcessDescriptions/ProcessDescription[@processVersion='1' and @statusSupported='false' and @storeSupported='true']/DataInputs/Input/Identifier/text()=-979906409";
		
		Stack<String> q = generator.asXPathStringStack(query, context);
		Stack<String> p = generator.asXPathStringStack(path, context);
		
		assertTrue(matcher.matches(q, p, true, context));
		assertTrue(matcher.matches(q, p, true, context));
	}

}
