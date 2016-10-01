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


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
import sapience.lookup.hibernate.utilities.HibernateUtil;

/**
 * @author Henry Michels
 *
 */
public class TestLookupPut {
	
	private static Lookup lookup;
	private static ReferenceController refControl;
	private static HibernateModelFactory fac;
	private static List<Reference> refs;
	
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
		refs = new ArrayList<Reference>();
		refControl.deleteAllRecords();
	}
	
	@Test
	public void testAddRecord(){
		try {
			HibernateLocalElement local = fac.createCompositeName(new URI(docID), elementID);
		    HibernateReference ref = fac.createBinding(local, element);
		    refs.add(ref);
			lookup.put(ref.getSource().getDocumentID().toString(), refs);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testUpdateRecord(){
		try {
			refs.clear();
			HibernateLocalElement local = fac.createCompositeName(new URI(docID), elementID);
		    HibernateReference ref = fac.createBinding(local, element+2);
		    refs.add(ref);
			lookup.put(ref.getSource().getDocumentID().toString(), refs);
			Assert.assertEquals(1, numberOfLines());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private int numberOfLines(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Criteria query =
	    	session.createCriteria(HibernateReference.class);
		List lines = query.list();
		transaction.commit();
		session.close();
		return lines.size();
	}
}
