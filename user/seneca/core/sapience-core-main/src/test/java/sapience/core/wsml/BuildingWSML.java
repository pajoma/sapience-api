package sapience.core.wsml;

import java.io.InputStream;

import org.junit.Test;

public class BuildingWSML {
	
	
	@Test
	public void testLoadingRDF() {
		InputStream is = BuildingWSML.class.getResourceAsStream("WSMLRdf.n3");
		
	}
}
