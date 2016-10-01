package sapience.core.consistency;
import org.openrdf.model.Graph;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.*;

import com.google.inject.Inject;

import sapience.core.controllers.TaskController;
import sapience.core.controllers.sesame.SesameTaskController;
import sapience.core.exceptions.DatabaseException;

public class TemporalRepository {

	private Repository repo;
	@Inject
	private TaskController sesameConn;

	private String baseUri = "http://purl.org/net/concepts/";

	public TemporalRepository()
	{
		TemporalRepositoryBuilder builder = new TemporalRepositoryBuilder();
		repo = builder.getRepository();

	}

	public void ConstructQuery() {
		String rdf;
		String subject1 = "Transportation";
		String subject2 = "Hydrodynamics";
		// River hash
		String concept = "#_qwer";
		String uri = baseUri + concept;
		System.out.println(uri);

		try {
			rdf = sesameConn.getRDF(uri, "application/rdf+xml");
			System.out.println(rdf);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addContentbyGraph() throws RepositoryException
	{
		RepositoryConnection con = null;
		ValueFactory f = repo.getValueFactory();

		// create some resources and literals to make statements out of
		URI alice = f.createURI("http://example.org/people/alice");
		URI bob = f.createURI("http://example.org/people/bob");
		URI name = f.createURI("http://example.org/ontology/name");
		URI person = f.createURI("http://example.org/ontology/Person");
		Literal bobsName = f.createLiteral("Bob");
		Literal alicesName = f.createLiteral("Alice");

		try {
			con = repo.getConnection();

			try {
				// alice is a person
				con.add(alice, RDF.TYPE, person);
				// alice's name is "Alice"
				con.add(alice, name, alicesName);

				// bob is a person
				con.add(bob, RDF.TYPE, person);
				// bob's name is "Bob"
				con.add(bob, name, bobsName);
			}
			finally {
				
			}
		}
		catch (OpenRDFException e) {
			// handle exception
		}
		
		RepositoryResult<Statement> statements = con.getStatements(alice, null, null, true);
		
		try 
		{
			
			while (statements.hasNext()) 
			{
				Statement st = statements.next();
				System.out.println(st);
			}
		}
		finally {
			statements.close(); // make sure the result object is closed properly
			con.close();
		}


	}

	public void getAlice()
	{

	}


	public static void main (String[] args)
	{
		TemporalRepository test = new TemporalRepository();
		try {
			test.addContentbyGraph();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.ConstructQuery();
	}

}