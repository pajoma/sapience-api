package sapience.service.concepts;

import junit.framework.TestCase;

import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.sail.memory.MemoryStore;

import sapience.core.controllers.sesame.SesameTaskController;
import sapience.core.exceptions.DatabaseException;

public class DatabaseTestCase extends TestCase {

	private final static String PREFIX = "http://example.com/foo/";
	private SesameTaskController db;

	public DatabaseTestCase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();

		SailRepository repository = new SailRepository(new MemoryStore());
		repository.initialize();
		SailRepositoryConnection conn = repository.getConnection();

		/*
		 * generate Statements that form a "linked list": PREFIX1 isConnectedTo
		 * PREFIX2. [...]
		 */
		for (int i = 1; i < 100; i++) {
			conn.add(new URIImpl(PREFIX + i), new URIImpl(PREFIX
					+ "isConnectedTo"), new URIImpl(PREFIX + (i + 1)));
		}

		db = new SesameTaskController(repository);
	}

	public final void testRDF() throws DatabaseException {
		String oneStatement = db.getRDF(PREFIX + "1",null);
		assertEquals(
				"<http://example.com/foo/1> <http://example.com/foo/isConnectedTo> <http://example.com/foo/2> .\n",
				oneStatement);
	}
	
	/**
	 * Tests if the full list of statements is returned.
	 * @throws DatabaseException
	 */
	public final void testFullRDF() throws DatabaseException {
		String allStatements = db.getRDF(PREFIX,null);
		
		// ok, this is not the perfect test ;-)
		assertTrue(allStatements.contains(PREFIX+"30"));
	}

}
