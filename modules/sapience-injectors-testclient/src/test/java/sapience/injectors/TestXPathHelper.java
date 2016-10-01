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

package sapience.injectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;


import sapience.injectors.model.xpath.XPathMatcher;
import sapience.injectors.util.LocalNamespaceContext;

/**
 * Testing the matching methods (which somewhat complex)
 * @author pajoma
 *
 */
public class TestXPathHelper {

	private XPathMatcher matcher;
	private NamespaceContext context;
	private String path, query;


	public void setup() {
		matcher = new XPathMatcher();
		context = new LocalNamespaceContext();
	}
	
	
	 @Test
	public void testSomething() {
		assertTrue(true);
	}
	 
	public void testA() throws XPathExpressionException, IOException  {
		path = "//root/sub[@attr=1]/subsub[@xlink:title=yay]";
		query = "//root/sub[@attr=1]/subsub[@xlink:title=yay]";
		assertTrue(matcher.matchingPath( query,path, true, context));
		assertTrue(matcher.matchingPath( query,path, false, context));
	}
	

	public void testB() throws XPathExpressionException, IOException {
		path = "//root/sub[@attr=1]/subsub[@xlink:title=yay]";
		query = "//root/sub[@attr=2]/subsub[@xlink:title=yay]";
		assertFalse(matcher.matchingPath( query,path, true, context));
		assertFalse(matcher.matchingPath( query,path, false, context));
	}
	

	public void testC() throws XPathExpressionException, IOException {
		path = "//sub[attr=1]/subsub";
		query = "not a valid expression";
		assertTrue(matcher.matchingPath( query,path, true, context));
		assertTrue(matcher.matchingPath( query,path, false, context));
	}
	

	public void testD() throws XPathExpressionException, IOException {
		path = "//root/sub[@attr=1]/subsub";
		query = "//sub[@attr=1]/subsub";
		assertTrue(matcher.matchingPath(query, path,  true, context));
		assertTrue(matcher.matchingPath(query, path,  false, context));
	}
	

	public void testE() throws XPathExpressionException, IOException {
		path = "//root/sub[@attr=1]/subsub/subsubsub[@attr=2]";
		query = "//ruth/sub[@attr=2]/subsub/subsubsub[@attr=2]";
		assertFalse(matcher.matchingPath( query,path, true, context));
		assertFalse(matcher.matchingPath( query,path, false, context));
	}
	
	public void testF() throws XPathExpressionException, IOException {
		path = "//ProcessDescriptions/ProcessDescription[@processVersion='1' and @statusSupported='false' and @storeSupported='true']/DataInputs/Input/Metadata[@xlink:href='http://example.com/WebServices/40931e28a' and @xlink:arcrole='http://example.com/WebService']";
		query = "//Metadata[@xlink:href and @xlink:arcrole]";
		assertFalse(matcher.matchingPath(query, path, true, context));
		assertTrue(matcher.matchingPath(query, path, false, context));
	}
	

	public void testG() throws XPathExpressionException, IOException {
		path = "//root/sub[@attr=1]/subsub/subsubsub[@attr=2]";
		query = "//root/sub/subsub/subsubsub[@attr]";
		assertFalse(matcher.matchingPath(query, path, true, context));
		assertTrue(matcher.matchingPath(query, path, false, context));
	}
	            
	
}
