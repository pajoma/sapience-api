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

import java.util.Iterator;
import java.util.Stack;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

/**
 * Contains helper methods to efficiently compare if one or our references is matching the  XPath of the current element
 * @author pajoma
 *
 */
@Deprecated
public class XPathHelper {
	private static XPathGenerator gen = new XPathGenerator();
    
    /**
	 * Helper method, adding the current XML Element to the stack in the first parameter
	 * @param stack
	 * @param se
	 */
	public static void addCurrentElementToStack(Stack<String> stack, StartElement se) {
		
		StringBuilder pb = new StringBuilder();
		// element has attributes, we have to add them to the xpath for identification
		Iterator<?> attributes = se.getAttributes();
		pb.append(se.getName().getLocalPart());
		
		if(attributes.hasNext()) {
			pb.append("[");
			while(attributes.hasNext()) {
				Object next = attributes.next();
				if(next instanceof Attribute) {
					pb.append(gen.asXPathString((Attribute) next));
					if(attributes.hasNext()) pb.append(" and ");
				}

			}
			pb.append("]");
		}
		
		stack.add(pb.toString());
	}
	
	

	
	



	
}
