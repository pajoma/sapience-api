package sapience.injectors.wps.identify;

import java.io.Serializable;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import sapience.injectors.Injector;
import sapience.injectors.Lookup;
import sapience.injectors.xml.LocalElementIdentifier;
import sapience.injectors.xml.Reference;



public class LookupMockup  {

	public List<Reference> check(Serializable docID) throws Exception {
		List<Reference> res = new ArrayList<Reference>();
		
		LocalElementIdentifier loc = new LocalElementIdentifier(docID, "//root/elems/elem[id=1]/");
		Serializable des = "annot=\"myontology.elem1\"";
		res.add(new Reference(loc, des));
		
		loc = new LocalElementIdentifier(docID, "//root/elems/elem[id=2]/elem21[attr=my_value4]/elem211/");;
		des = "xlink:href=\"myontology.elem211\" xlink:arcrole=\"http://modelreference\"";
		res.add(new Reference(loc, des));
		
		return res;
	}



	
} 
