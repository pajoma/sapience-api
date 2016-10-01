package sapience.proxy.json;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import sapience.injectors.model.Reference;
import sapience.proxy.persistence.model.JDOLocalElement;
import sapience.proxy.persistence.model.JDOStoredServices;

public class JsonBuilder {
	private static String encode(Serializable str) {
	
		return str.toString().replaceAll("\"", "\\\\\"");
	}
	
	public static void writeListResponse(Writer w, String sid, List<Reference> list) throws IOException {
		w.append("{").append('\n');
		w.append("  \"sid\": \"").append(sid).append("\", ").append('\n');
		w.append("  \"list\": {").append('\n');
		
		Iterator<Reference> its = list.iterator();
		while(its.hasNext()) {
			Reference ref = its.next();
			JDOLocalElement loc = (JDOLocalElement) ref.getSource();
			
			w.append("    \"binding\": {").append('\n');
			w.append("      \"name\": \"").append(encode(loc.getElementID())).append("\", ").append('\n');
			w.append("      \"reference\": \"").append(encode(ref.getTarget())).append("\" ").append('\n');
			w.append("    }");
			if(its.hasNext()) w.append(",");
			w.append('\n');
		}
		w.append("  }").append('\n');
		w.append("}").append('\n');
		
	}

	public static void writeServicesResponse(Writer w, Collection<JDOStoredServices> list) throws IOException {
		w.append("{").append('\n');
		w.append("  \"list\": {").append('\n');
		
		Iterator<JDOStoredServices> its = list.iterator();
		while(its.hasNext()) {
			JDOStoredServices jss = its.next();
			w.append("    \"service\": {").append('\n');
			w.append("      \"id\": \"").append(encode(jss.getSID())).append("\", ").append('\n');
			w.append("      \"host\": \"").append(encode(jss.getServiceRequestHost())).append("\" ").append('\n');
			w.append("    }");
			if(its.hasNext()) w.append(",");
			w.append('\n');
		
		}
		w.append("  }").append('\n');
		w.append("}").append('\n');
		
	}
}