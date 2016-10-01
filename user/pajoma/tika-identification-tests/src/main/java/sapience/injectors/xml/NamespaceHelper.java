package sapience.injectors.xml;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

/**
 * Contains helper methods to efficiently compare if one or our references is matching the  XPath of the current element
 * @author pajoma
 *
 */
public class NamespaceHelper {

	/* a list of all references which have to be added */
	private  Map<Integer, Stack> allReferences;
	
	
	public void setReferences(List<Reference> references) {
		allReferences = convertReferencesToXpathStacks(references);
	}
	
	/** 
    Helper to convert a path stack into a readable/parsable XPath string. 
    */ 
    public  String toString(Stack<String> stack)  
     { 
        StringBuilder result = new StringBuilder  ( "//" ) ; 
        Iterator<String> each = stack.iterator  (  ) ; 
        while  ( each.hasNext  (  )  )  
            result.append  (  ( String )  each.next  (  )  )  
                  .append  ( '/' ) ; 
        return result.toString  (  ) ; 
     }  
    
    
	/** 
    Helper to convert a String into a Stack
    */ 
    public  Stack<String> toStack(Serializable xpath)  
     {  
    	String[] tokens = xpath.toString().split("/");
    	Stack<String> res = new Stack<String>();
    	for(String str : tokens) {
    		if((str!=null)&&(str.length()>0)) {
    			res.add(str);
    		}
    	}
    	
    	return res;
     }  
    

    
    
	/**
	 * Returns true, if the current xpath matches one of the given references. Note that this method does also
	 * return false positives (e.g. //elem1/child will return true, even though the current path is //elem2/child)
	 * @return -1 if not found, position in the list of references otherwise 
	 * 
	 */
	public int checkIf(Stack<String> currentPath) {
		for(Entry<Integer, Stack> ref : allReferences.entrySet()) {
			// if stacks are not same length, they can't be a match
			if(currentPath.size()!=ref.getValue().size()) continue;
			
			// both have the same length, let's compared them elem by elem
			if (check(currentPath, ref.getValue(), currentPath.size()-1)) {
				return ref.getKey().intValue();
			}
			
			
		}
		return -1;
	}
	
	private boolean check(Stack<?> a, Stack<?> b, int pos) {
		if(pos == 0) {
			if(a.get(pos).equals(b.get(pos))) return true;
		}
		if(a.get(pos).equals(b.get(pos))) {
			return check(a,b,pos-1);
		}
		else return false;
	}
	
    
    private Map<Integer,Stack> convertReferencesToXpathStacks(List<Reference> list) {
    	Map<Integer,Stack> res = new HashMap<Integer,Stack>();
    	for(int i = 0; i < list.size(); i++) {
    		Stack<String> stack = toStack(((LocalElementIdentifier) list.get(i).getSource()).getElementID());
    		res.put(Integer.valueOf(i), stack);
    	}
    	
    	return res;
    }
}
