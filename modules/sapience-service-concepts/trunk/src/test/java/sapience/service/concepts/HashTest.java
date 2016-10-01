package sapience.service.concepts;

import org.junit.Test;


public class HashTest {
	
	@Test
	public void hashs() {
		String r = stringToHex("River");
		String b = stringToHex(new String[] {"River","Biology"});
		
//		this.findId(r, b);
		
		System.out.println(stringToHex("has-depth"));
		System.out.println(stringToHex("depth"));
		System.out.println(stringToHex(new String[] {"freezing-depth","Biology"}));
		System.out.println(stringToHex(new String[] {"has-depth","Biology"}));
		System.out.println(stringToHex(new String[] {"River","Biology"}));
		
	}
	
	
	// transform string to id
	public String stringToHex(String ... in) {
		if(in.length > 1) {
			String res = null; 
			int hashed = 0;
			for(String str : in) {
				hashed += str.hashCode();
			}
			return Integer.toHexString(hashed);
		} else {
			int hash = in.hashCode();
			return Integer.toHexString(hash);
		}
		
	}

	// find string in id
	public String findId(String in1, String in2) {
		Integer.parseInt(in1);
		Integer.parseInt(in2);
		
		return "";
		
	}
}
