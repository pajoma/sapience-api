package sapience.core.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.PageParameters;

import sapience.core.beans.RequestParameter;
import sapience.core.exceptions.ConfusingQueryParameterException;
import sapience.core.exceptions.MissingQueryParameterException;
import sapience.core.exceptions.UserInputException;

/**
 * The interface which has to be implemented for custom actions
 * 
 * @author pajoma
 *
 */
public abstract class CoreTask {
	
	private String name;
	private Map<String, String[]> parameterMap;
//	private PageParameters parameters;

	public CoreTask() {
		parameterMap = new LinkedHashMap<String, String[]>();
	}

	public String getParameter(String key, boolean required) throws UserInputException {
		List<String> vals = getParameters(key, required);
		if(vals.isEmpty()) {
			return ""; 
		} else if(vals.size() > 1) {
			throw new ConfusingQueryParameterException(key);
		} else {
			return vals.get(0);
		}
	}

	
	public List<String> getParameters(String key, boolean required) throws UserInputException {
		if((parameterMap == null) || (!parameterMap.containsKey(key)))
			if(required) 
				throw new MissingQueryParameterException(key);
			else 
				return new ArrayList<String>();
		
		// TODO check also PageParameters
		return Arrays.asList(parameterMap.get(key));
	}


	
	public void setParameter(String parameter, String value) {
		if(parameterMap != null) {
			parameterMap.put(parameter, new String[] {value});
		}
	}

	public void setParameters(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
		
	}
	


//	public void setParameters(PageParameters parameters) {
//		this.parameters = parameters;		
//	}
	
	
	
	/**
	 * Returns the keywords which triggers this action
	 * @return the name of the task
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	




	
}

