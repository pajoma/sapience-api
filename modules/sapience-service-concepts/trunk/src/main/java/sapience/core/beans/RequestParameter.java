package sapience.core.beans;

import java.beans.Beans;


public class RequestParameter extends Beans {
	
	private String name;
	private boolean mandatory;
	
	public RequestParameter(String name, boolean mandatory) {
		this.name = name;
		this.mandatory = mandatory;
	}
	
	public RequestParameter(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the mandatory
	 */
	public boolean isMandatory() {
		return mandatory;
	}
	
	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	

}
