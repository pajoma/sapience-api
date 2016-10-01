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

import junit.framework.Assert;

import org.junit.Test;

import sapience.lookup.exceptions.FailedCreateTableException;
import sapience.lookup.hibernate.controller.ReferenceController;

/**
 * This class tests to create the table for the entity "Concept".
 * 
 * @author Henry
 */

public class TestCreateTable {
	
	@Test
	public void testCreateTable(){
		try {
			new ReferenceController();
		} catch (FailedCreateTableException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
}
