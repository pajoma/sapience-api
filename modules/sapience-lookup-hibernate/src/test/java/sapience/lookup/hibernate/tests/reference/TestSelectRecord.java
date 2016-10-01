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

/**
 * 
 */
package sapience.lookup.hibernate.tests.reference;

import java.net.URI;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import sapience.lookup.hibernate.controller.ReferenceController;
import sapience.lookup.hibernate.model.HibernateLocalElement;
import sapience.lookup.hibernate.model.HibernateModelFactory;
import sapience.lookup.hibernate.model.HibernateReference;



/**
 * @author Henry
 *
 */
public class TestSelectRecord {
	
	ReferenceController refControl;
	HibernateModelFactory fac;
	String docID = "http://gdi-kalypso1.gridlab.uni-hannover.de:8080/ogc?service=WPS&request=DescribeProcess&version=0.4.0&identifier=Gaja3d_createTin";
//	String elementID = "xPathExpression";
	String elementID = "//ns3:Capabilities[@version='0.4.0']/ns3:ProcessOfferings/ns3:Process/ns1:Identifier[@codeSpace='']/text()='Gaja3d_createTin'/sibling()";
	String element = "http://services.ifgi.de/Gaja3DWorkflow#CreateTin";
    
    @Before
	public void setUp() throws Exception {
		refControl = new ReferenceController();
		fac = new HibernateModelFactory();
	}
	
	@Test
	public void testSelectRecord(){
		try {
			refControl.deleteAllRecords();
			HibernateLocalElement local = fac.createCompositeName(new URI(docID), elementID);
		    HibernateReference ref = fac.createBinding(local, element);
		    HibernateModelFactory.apostrophesToQuotes(ref);
		    refControl.addRecord(ref);
		    HibernateReference ref2 = HibernateModelFactory.quotesToApostrophes((HibernateReference) refControl.selectRecord(ref));
		    HibernateModelFactory.quotesToApostrophes(ref);
		    Assert.assertEquals(ref.getSource().getDocumentID(), ref2.getSource().getDocumentID());
		    Assert.assertEquals(ref.getSource().getElementID(), ref2.getSource().getElementID());
		    // Assert.assertEquals(ref.getFastSearchDocID(), ref2.getFastSearchDocID());
		    Assert.assertEquals(ref.getTarget(), ref2.getTarget());
		}
		catch (Exception e) {
		     e.printStackTrace();
		     Assert.fail();
		}
	}
}
