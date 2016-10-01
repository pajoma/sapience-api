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
package sapience.lookup.hibernate.tests.finaltests;


import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import sapience.injectors.Lookup;
import sapience.injectors.model.Reference;
import sapience.lookup.LookupImpl;
import sapience.lookup.hibernate.controller.ReferenceController;
import sapience.lookup.hibernate.model.HibernateLocalElement;
import sapience.lookup.hibernate.model.HibernateModelFactory;
import sapience.lookup.hibernate.model.HibernateReference;

/**
 * @author Henry Michels
 *
 */
public class TestLookupCheck {
	
	private static Lookup lookup;
	private static ReferenceController refControl;
	private static HibernateModelFactory fac;
	
	private static String docID = "http://gdi-kalypso1.gridlab.uni-hannover.de:8080/ogc?service=WPS&request=DescribeProcess&version=0.4.0&identifier=Gaja3d_createTin";
	private static String elementID = "//ns3:Capabilities[@version='0.4.0']/ns3:ProcessOfferings/ns3:Process/ns1:Identifier[@codeSpace='']/text()='Gaja3d_createTin'/sibling()";
	private static String element = "http://services.ifgi.de/Gaja3DWorkflow#CreateTin";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		fac = new HibernateModelFactory();
		lookup = LookupImpl.getInstance();
		refControl = ((LookupImpl) lookup).getReferenceController();
		refControl.deleteAllRecords();
		addExampleRecords();
	}
	
	@Test
	public void noStoredConcepts(){
		List<Reference> refs = null;
		try{
			refs = lookup.check("test");
			if (refs != null) {
				Assert.assertTrue(refs.isEmpty());
			} else {
				Assert.fail();
			}
		
		} catch (IOException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void oneStoredConcept() throws Exception{
		List<Reference> refs = null;
		try{
			refs = lookup.check(docID+2);
			if (refs != null) {
				Assert.assertTrue(refs.size() == 1);
			} else {
				Assert.fail();
			}
		
		} catch (IOException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void manyStoredConcepts() throws Exception{
		List<Reference> refs = null;
		try{
			refs = lookup.check(docID);
			if (refs != null) {
				Assert.assertEquals(5, refs.size());
			} else {
				Assert.fail();
			}
		
		} catch (IOException e) {
			Assert.fail();
		}
	}
	
	private static void addExampleRecords(){
		for (int i = 0; i < 5; i++) {
			try {
				HibernateLocalElement local = fac.createCompositeName(new URI(docID), elementID+i);
			    HibernateReference ref = fac.createBinding(local, element+i);
			    HibernateModelFactory.apostrophesToQuotes(ref);
				refControl.addRecord(ref);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}
		}
		try {
			HibernateLocalElement local = fac.createCompositeName(new URI(docID+2), elementID);
		    HibernateReference ref = fac.createBinding(local, element);
		    HibernateModelFactory.apostrophesToQuotes(ref);
			refControl.addRecord(ref);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
