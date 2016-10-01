package sapience.core.wsml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.openrdf.OpenRDFException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.n3.N3ParserFactory;

import sapience.core.wsml.RDFSHandler;
import sapience.core.graph.GraphEventHandler;
import sapience.core.graph.impl.JDOGraphBuilder;

public class BuildingWSML {
	
	
	@Test
	public void testLoadingRDF() throws RDFParseException, OpenRDFException, IOException {
		InputStream is = null;
		is = new FileInputStream("src/test/resources/sapience/core/wsml/WSMLRdf.n3");
		
		RDFParser parser = new N3ParserFactory().getParser();
		JDOGraphBuilder builder = new JDOGraphBuilder();
		GraphEventHandler graph = new GraphEventHandler(builder);
		RDFSHandler handler = new RDFSHandler(graph);
		parser.setRDFHandler(handler);
	
		parser.parse(is, "");
	
		
		
		// load source into temporary repository
//		Repository temp_repository = new SailRepository(new MemoryStore());
//		temp_repository.initialize();
//		temp_connection = temp_repository.getConnection();
//		temp_connection.add(source, ApplicationCore.getBaseURL(), RDFFormat.valueOf(task.getParameter(UpdateTask.PARAM_FORMAT, true)));
		
		
	}
}
