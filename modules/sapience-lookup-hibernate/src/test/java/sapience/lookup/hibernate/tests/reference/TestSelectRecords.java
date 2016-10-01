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

package sapience.lookup.hibernate.tests.reference;

import java.net.URI;
import java.util.Hashtable;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import sapience.lookup.hibernate.controller.DbProperties;
import sapience.lookup.hibernate.controller.ReferenceController;
import sapience.lookup.hibernate.model.DatabaseEntity;
import sapience.lookup.hibernate.model.HibernateLocalElement;
import sapience.lookup.hibernate.model.HibernateModelFactory;
import sapience.lookup.hibernate.model.HibernateReference;



/**
 * This class tests the selection of more than one records with the same URI.
 * 
 * @author Henry Michels
 */
public class TestSelectRecords {
	
	ReferenceController refControl;
	HibernateModelFactory fac;
	String docID = "http://gdi-kalypso1.gridlab.uni-hannover.de:8080/ogc?service=WPS&request=DescribeProcess&version=0.4.0&identifier=Gaja3d_createTin";
	String elementID = "xPathExpression";
	String element = "http://services.ifgi.de/Gaja3DWorkflow#CreateTin";
	
	String elementID2 = "xPathExpression2";
	String element2 = "http://services.ifgi.de/Gaja3DWorkflow#CreateGrid";
    
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
		    refControl.addRecord(ref);
			HibernateLocalElement local2 = fac.createCompositeName(new URI(docID), elementID2);
		    HibernateReference ref2 = fac.createBinding(local2, element2);
		    refControl.addRecord(ref2);
		    
			Hashtable<String, String> parameters = new Hashtable<String, String>();
//			parameters.put(DbProperties.getString("Reference.Field.FastSearchDocID"), HibernateModelFactory.getChecksumForDocID(docID));
		    
		    List<DatabaseEntity> refList = refControl.selectRecords(parameters);
		    for (int i = 0; i < refList.size(); i++) {
				if (refList.get(i) instanceof HibernateReference) {
					HibernateReference ref3 = (HibernateReference) refList.get(i);
					if (i == 0) {
					    Assert.assertEquals(ref.getSource().getDocumentID(), ref3.getSource().getDocumentID());
					    Assert.assertEquals(ref.getSource().getElementID(), ref3.getSource().getElementID());
//					    Assert.assertEquals(ref.getFastSearchDocID(), ref3.getFastSearchDocID());
					    Assert.assertEquals(ref.getTarget(), ref3.getTarget());
					} else {
					    Assert.assertEquals(ref2.getSource().getDocumentID(), ref3.getSource().getDocumentID());
					    Assert.assertEquals(ref2.getSource().getElementID(), ref3.getSource().getElementID());
//					    Assert.assertEquals(ref2.getFastSearchDocID(), ref3.getFastSearchDocID());
					    Assert.assertEquals(ref2.getTarget(), ref3.getTarget());
					}
				} else {
					Assert.fail();
				}
			}
		}
		catch (Exception e) {
		     e.printStackTrace();
		     Assert.fail();
		}
	}
}
