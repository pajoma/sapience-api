package sapience.core.wsml;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.openrdf.OpenRDFException;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.n3.N3ParserFactory;

public class BuildingWSML {
	
	
	@Test
	public void testLoadingRDF() throws RDFParseException, OpenRDFException, IOException {
		InputStream is = BuildingWSML.class.getResourceAsStream("WSMLRdf.n3");
		
		RDFParser parser = new N3ParserFactory().getParser();
		parser.parse(is, "");
		parser.setRDFHandler(new RDFSHandler());
		
		// load source into temporary repository
//		Repository temp_repository = new SailRepository(new MemoryStore());
//		temp_repository.initialize();
//		temp_connection = temp_repository.getConnection();
//		temp_connection.add(source, ApplicationCore.getBaseURL(), RDFFormat.valueOf(task.getParameter(UpdateTask.PARAM_FORMAT, true)));
		
		
	}
}
