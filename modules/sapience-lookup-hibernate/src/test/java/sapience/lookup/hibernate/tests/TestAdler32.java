/**
 * 
 */
package sapience.lookup.hibernate.tests;

import java.math.BigInteger;
import java.util.zip.Adler32;

import junit.framework.Assert;

import org.junit.Test;


/**
 * @author Henry
 *
 */
public class TestAdler32 {
	
	String testString = "http://gdi-kalypso1.gridlab.uni-hannover.de:8080/ogc?service=WPS&request=DescribeProcess&version=0.4.0&identifier=Gaja3d_createTin";
	
	@Test
	public void testStringToHexAndBack(){
		Adler32 adler32 = new Adler32();
		adler32.update(testString.getBytes());
		String testHex = Long.toHexString(adler32.getValue());
		System.out.println(testHex);
		byte[] bts = new BigInteger(testHex, 16).toByteArray(); 
		System.out.println(bts.length);
		Adler32 adler321 = new Adler32();
		adler321.update(testHex.getBytes());
		String result = Long.toString(adler321.getValue());
		
		System.out.println(result);
		Assert.assertEquals(testString, result);
	}
	

}
