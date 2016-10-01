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

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Stack;

import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;

import sapience.injectors.model.xpath.XPathElement;
import sapience.injectors.model.xpath.XPathStack;
import sapience.injectors.util.LocalNamespaceContext;

/**
 * @author pajoma
 *
 */
public class TestXPathGenerator {
	
	String xpath1 = "//aRoot/bElem/cElem[@cAttr='value']/dElem[@dAttr1='value' and @dAttr2='3']";
	String xpath2 = "//aRoot/bElem/cElem[@cAttr='value']/dElem[@dAttr2='3' and @dAttr1='value']";
	private XPathStack stack;
	private Stack<String> stack2;
	private LocalNamespaceContext context;
	
	@Before
	public void prepareStacks() {
		
		stack = new XPathStack();
		stack.add(new XPathElement(new QName("//")));
		stack.add(new XPathElement(new QName("aRoot")));
		stack.add(new XPathElement(new QName("bElem")));
		stack.add(new XPathElement(new QName("cElem")).addAttribute(new QName("cAttr"), "value"));
		stack.add(new XPathElement(new QName("dElem")).addAttribute(new QName("dAttr1"), "value").addAttribute(new QName("dAttr2"), "3"));
		
		context = new LocalNamespaceContext();
	}
	

	
	@Test
	public void testXPathElementStackToString() throws IOException{
		String rendered = stack.serialize(new StringBuilder(), context).toString();
		// the XPathElement doesn't preserve the order of the attributes
		System.out.println(rendered);
		boolean result = rendered.equals(xpath1) || rendered.equals(xpath2); 

		assertTrue(result);
	}
	


	                                                                                                                         	

	
	
	
}
