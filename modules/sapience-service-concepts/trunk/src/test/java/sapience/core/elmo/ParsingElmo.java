package sapience.core.elmo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.junit.Test;
import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.sesame.SesameManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

public class ParsingElmo {

	@Test
	public void testParsingSailingBoat() throws RDFParseException, RepositoryException, IOException {
		InputStream s = ParsingElmo.class.getResourceAsStream("WSMLRdf.n3");
		
		ElmoModule module = new ElmoModule();
		SesameManagerFactory factory = new SesameManagerFactory(module); 
		SesameManager manager = factory.createElmoManager(); 

		manager.getConnection().add(s, "", RDFFormat.N3, null); 
		for (org.openrdf.concepts.rdfs.Class person : manager.findAll(org.openrdf.concepts.rdfs.Class.class)) { 
			System.out.print("Name: "); 
			System.out.println(person.getRdfsLabel());
			Set<Object> rdfValues = person.getRdfValues();
			
			System.out.println();
			} 
	}
}
