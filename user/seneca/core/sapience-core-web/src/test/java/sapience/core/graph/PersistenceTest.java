package sapience.core.graph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.n3.N3ParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import sapience.core.persistence.GraphRepository;
//import sapience.core.wsml.RDFSHandler;

import sapience.core.graph.util.GraphBuilder;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class PersistenceTest {

	private @Autowired GraphBuilder gb; 
	private @Autowired GraphRepository repo;
	
	@Test
	public void parseWSMl() throws RDFParseException, RDFHandlerException, IOException {
		InputStream is = null;
		is = new FileInputStream("src/test/resources/sapience/core/wsml/WSMLRdf.n3");
		
		RDFParser parser = new N3ParserFactory().getParser();
//		GraphEventHandler graph = new GraphEventHandler(gb);
//		RDFSHandler handler = new RDFSHandler(graph); 
//		parser.setRDFHandler(handler);
	
		parser.parse(is, "");
	
		// store
		Collection<CoreNode> list = gb.getGraph().listConceptNodes();
		repo.addNodes(new ArrayList<CoreNode>(list));
		
		// 
		repo.listNodes();
		
	}
}
