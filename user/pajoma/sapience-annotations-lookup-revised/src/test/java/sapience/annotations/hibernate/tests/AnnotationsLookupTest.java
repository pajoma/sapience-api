package sapience.annotations.hibernate.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import sapience.annotations.hibernate.entities.LocalElementID;
import sapience.annotations.hibernate.entities.Reference;

/**
 * Created by IntelliJ IDEA.
 * User: pajoma
 * Date: Oct 16, 2009
 * Time: 3:49:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnnotationsLookupTest {


    public List<Reference> references;

    

    @Before
    public void prepareTest() {
    	// prepare test data
    	references = new ArrayList<Reference>();
    	int max = 5; // how many references to create
    
    	for (int i = 0; i < max; i++) {
    		LocalElementID loc = new LocalElementID();
    		loc.setResourceID("http://www.example.com/myService/"+i);
    		
    		for (int j = 0; j < max; j++) {
	    		loc.setElementID("/root/node/"+j);
	    		
	    		Reference ref = new Reference();
	        	ref.setLocalElement(loc);
	        	ref.setReferencedEntity("http://www.thesaurus.com/domain/concept/"+j);
	        	ref.setReferenceType("http://purl.net/ifgi/annotations#modelReference");
    		}
		}
    }
    
    @Before 
    public void emptyDB() {
    	
    }

}
