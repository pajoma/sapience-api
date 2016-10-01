package sapience.core.consistency;

import java.util.List;

import org.openrdf.model.URI;
import org.openrdf.model.Value;

// TODO: refactor to junit testcase
// TODO: move into test package
public class SimpleTest {

	public static void main(String[] args) {
	
		//a test of graph operations
		
		SimpleGraph g = new SimpleGraph();
		//manually add a triple/statement with a URIref object
		
//		URI s1 = g.URIref("http://semprog.com/people/toby");
//		URI p1 = g.URIref(SimpleGraph.RDFTYPE);
//		URI o1 = g.URIref("http://xmlns.com/foaf/0.1/person");
//		g.add(s1, p1, o1);
//		
//		URI s2 = g.URIref("http://semprog.com/people/toby");
//		URI p2 = g.URIref("http://xmlns.com/foaf/0.1/nick");
//		Value o2 = g.Literal("kiwitobes");
//		g.add(s2, p2, o2);
		
		//Load Concept from an URI -  Persons OWL 
		g.addURI("http://daml.umbc.edu/ontologies/ittalks/person");
		
		//dump the graph in the specified format
		System.out.println("\n==Namespace Dump==\n");
		g.dumpNamespaces();
		
		System.out.println("\n==Graph Dump RDFXML==\n");
		//g.dumpRDF(System.out, SimpleGraph.RDFXML);
		
		System.out.println("\n==Graph Dump NTRIPLES==\n");
		g.dumpRDF(System.out, SimpleGraph.NTRIPLES);
		
		
}
	
}


/*
//manually add a triple/statement with a URIref object
URI s1 = g.URIref("http://semprog.com/people/toby");
URI p1 = g.URIref(SimpleGraph.RDFTYPE);
URI o1 = g.URIref("http://xmlns.com/foaf/0.1/person");
g.add(s1, p1, o1);

//manually add with an object literal
URI s2 = g.URIref("http://semprog.com/people/toby");
URI p2 = g.URIref("http://xmlns.com/foaf/0.1/nick");
Value o2 = g.Literal("kiwitobes");
g.add(s2, p2, o2);

//parse a string of RDF and add to the graph
String rdfstring = "<http://semprog.com/people/jamie> <http://xmlns.com/foaf/0.1/nick> \"jt\" .";
g.addString(rdfstring, SimpleGraph.NTRIPLES);

System.out.println("\n==TUPLE QUERY==\n");
List rlist = g.tuplePattern(null, g.URIref("http://xmlns.com/foaf/0.1/nick"), null);
System.out.print(rlist.toString());
*/