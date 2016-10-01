package sapience.injectors.model.xpath;


public class XPathPattern extends XPathStack {
	private static final long serialVersionUID = 9002241376633521295L;


	public static final String ATTRIBUTE = "attribute()";
	public static final String CHILD_ELEMENT = "child()";
	public static final String SIBLING_ELEMENT = "sibling()";
	
	
	// 0 - not set
	// 1 - element
	// 2 - attribute
	
	private int flag = 0;


	private String id;
	
	public boolean isAttribute() {
		return (flag == 1);
	}
	
	public boolean isChildElement() {
		return (flag == 2);
	}
	
	public boolean isSiblingElement() {
		return (flag == 3);
	}

	public int getType() {
		return flag;
	}
	
	public void setType(String type) {
		if(type.equalsIgnoreCase(ATTRIBUTE)) flag = 1;
		else if(type.equalsIgnoreCase(CHILD_ELEMENT))  flag = 2;
		else if(type.equalsIgnoreCase(SIBLING_ELEMENT))  flag = 3;
		else {
			throw new RuntimeException("This is not a valid destination for an injection pattern: "+type);
		}
	}

	public void setUniqueID(String id) {
		this.id = id;
	}
	
	
	

}
