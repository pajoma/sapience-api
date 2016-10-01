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

package sapience.lookup.filesystem;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.CharBuffer;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sapience.lookup.LookupImpl;
import sapience.lookup.filesystem.FileSystemProperties;

/**
 * @author Henry Michels
 *
 */
@Deprecated
public class TestConsistencyChecker {
	
	TestExtractor testInjector;
	
//	Lookup lookup;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testRunAndUpdate(){
		try {
			testInjector = new TestExtractor();
//			lookup = LookupImpl.getInstance();
			Thread.sleep(1000);
//			lookup.check(new URI("http://test"), testInjector, CharBuffer.wrap(getContentClue(new File(FileSystemProperties.getString("FileSystem.Path")+"/WFS.wsdl"))));
			
			Assert.assertEquals(true, testInjector.getStore());
		} catch (Exception e) {
			Assert.fail("Exception was thrown!");
			e.printStackTrace();
		} 
	}
	
	private String getContentClue(File file) {
		String contentClue = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			int index = 0;
			while (line != null && index < 255) {
				if ((index + line.length()) > 255) {
					int difference = 255 - index;
					contentClue += line.substring(0, difference);
					index = 255;
				} else {
					contentClue += line;
					index += line.length();
				}
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentClue;
	}
	
	@After
	public void setDownAfterClass(){
//		lookup = null;
	}
	
}
