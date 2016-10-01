/**
 * Copyright 2010 Institute for Geoinformatics (ifgi)
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

package sapience.injectors.util;

/* *********************************************************************
 *                    *** DISCLAIMER ***
 * This code is covered by the Creative Commons Attribution 2.5 License 
 * (http://creativecommons.org/licenses/by/2.5/).
 * 
 * You may use this code in any way you see fit as long as you realize 
 * that the code is provided AS IS without any warrenties and confers 
 * to rights what so ever! The author cannot be held accountable for 
 * any loss, direct or indirect, afflicted by using the code. 
 * 
 * *********************************************************************
 */

import java.util.Stack;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Utility class for dealing with XML DOM elements.
 * 
 * 
 * @author Mikkel Heisterberg, lekkim@lsdoc.org
 * @see http://lekkimworld.com/2007/06/19/building_xpath_expression_from_xml_node.html
 */
public class ExtractXPath {
   
   /**
    * Constructs a XPath query to the supplied node.
    * 
    * @param n
    * @return
    */
   public static String fromNode(Node n) {
      // abort early
      if (null == n) return null;

      // declarations
      Node parent = null;
      Stack<Node> hierarchy = new Stack<Node>();
      StringBuffer buffer = new StringBuffer();

      // push element on stack
      hierarchy.push(n);

      parent = n.getParentNode();
      while (null != parent && parent.getNodeType() != Node.DOCUMENT_NODE) {
         // push on stack
         hierarchy.push(parent);

         // get parent of parent
         parent = parent.getParentNode();
      }

      // construct xpath
      Object obj = null;
      while (!hierarchy.isEmpty() && null != (obj = hierarchy.pop())) {
         Node node = (Node) obj;
         boolean handled = false;

         // only consider elements
         if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) node;

            // is this the root element?
            if (buffer.length() == 0) {
               // root element - simply append element name
               buffer.append(node.getLocalName());
            } else {
               // child element - append slash and element name
               buffer.append("/");
               buffer.append(node.getLocalName());
               
               if (node.hasAttributes()) {
                  // see if the element has a name or id attribute
                  if (e.hasAttribute("id")) {
                     // id attribute found - use that
                     buffer.append("[@id='" + e.getAttribute("id") + "']");
                     handled = true;
                  } else if (e.hasAttribute("name")) {
                     // name attribute found - use that
                     buffer.append("[@name='" + e.getAttribute("name") + "']");
                     handled = true;
                  }
               }

               if (!handled) {
                  // no known attribute we could use - get sibling index
                  int prev_siblings = 1;
                  Node prev_sibling = node.getPreviousSibling();
                  while (null != prev_sibling) {
                     if (prev_sibling.getNodeType() == node.getNodeType()) {
                        if (prev_sibling.getLocalName().equalsIgnoreCase(node.getLocalName())) {
                           prev_siblings++;
                        }
                     }
                     prev_sibling = prev_sibling.getPreviousSibling();
                  }
                  buffer.append("[" + prev_siblings + "]");
               }
            }
         }
      }

      // return buffer
      return buffer.toString();
   }
}

