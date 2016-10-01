package sapience.proxy.persistence.model;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
 
/**
 * A container for all persistent services
 * 
 * @author pajoma
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class JDOStoredServices implements Serializable {


	private static final long serialVersionUID = -7951344657398713006L;
	
	@PrimaryKey 
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)  	
	private Key key;
	
	@Persistent
	private String sid;
	
	@Persistent 
	private String service_request;
	
	@Persistent
	private List<JDOReference> references;
	
	
	public JDOStoredServices(String sid, String request, List<JDOReference> ref) {
		// check if we have a valid service id, in this case it has to be a hex string
		
		if(! (sid.matches("\\p{XDigit}+"))) throw new IllegalArgumentException("This is not a valid service ID: "+sid);
		
		this.sid = sid;
		this.service_request = request;
		this.references = ref;
	}
	
	public JDOStoredServices(String sid, String request,  JDOReference ... references) {
		this(sid, request, Arrays.asList(references));
		
	}


	public Serializable getSID() {
		return sid;
	}
	
	public String getServiceRequest() {
		return service_request;
	}
	
	public List<JDOReference> listStoredReferences() {
		if (references == null) {
			this.references = new ArrayList<JDOReference>();
			
		}
		return references;
	}
	

	@Override
	public String toString() {
		return sid.toString();
	}

	public String getServiceRequestHost() {
		 StringBuilder sb = new StringBuilder(getServiceRequest());
		 int index; 
		if((index = sb.lastIndexOf("?")) > 0) {
		    return sb.substring(0, index);
		} else return sb.toString();
		
		 
		
	}


}
