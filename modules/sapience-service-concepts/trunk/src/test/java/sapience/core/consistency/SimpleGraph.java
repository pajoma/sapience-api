package sapience.core.consistency;


import org.openrdf.query.*;
import org.openrdf.model.vocabulary.*;
import org.openrdf.repository.*;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;
import org.openrdf.rio.*;
import org.openrdf.model.*;

import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.io.*;

public class SimpleGraph {

	Repository therepository= null; 

	//useful -local- constants
	static RDFFormat NTRIPLES = RDFFormat.NTRIPLES;
	static RDFFormat N3 = RDFFormat.N3;
	static RDFFormat RDFXML = RDFFormat.RDFXML;
	static String RDFTYPE =  RDF.TYPE.toString();

	/**
	 *  In memory Sesame repository without type inferencing
	 */
	public SimpleGraph(){
		this(false);
	}

	/**
	 * In memory Sesame Repository with optional inferencing
	 * @param inferencing
	 */
	public SimpleGraph(boolean inferencing){
		try {
			if (inferencing){
				therepository = new  SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));

			}else{
				therepository = new SailRepository(new MemoryStore());
			}
			therepository.initialize();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Literal factory
	 * 
	 * @param s the literal value
	 * @param typeuri uri representing the type (generally xsd)
	 * @return
	 */
	public org.openrdf.model.Literal Literal(String s, URI typeuri){
		try {
			RepositoryConnection con = therepository.getConnection();
			try {
				ValueFactory vf = con.getValueFactory();
				if (typeuri == null) {
					return vf.createLiteral(s);
				} else {
					return vf.createLiteral(s, typeuri);
				}
			} finally {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Untyped Literal factory
	 * 
	 * @param s the literal
	 * @return
	 */
	public org.openrdf.model.Literal Literal(String s){
		return Literal(s, null);
	}

	/**
	 *  URIref factory
	 * 
	 * @param uri
	 * @return
	 */
	public URI URIref(String uri){
		try {
			RepositoryConnection con = therepository.getConnection();
			try {
				ValueFactory vf = con.getValueFactory();
				return vf.createURI(uri);
			} finally {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *  BNode factory
	 * 
	 * @return
	 */
	public BNode bnode(){
		try{
			RepositoryConnection con = therepository.getConnection();
			try {
				ValueFactory vf = con.getValueFactory();
				return vf.createBNode();
			} finally {
				con.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public void dumpNamespaces()
	{
		
		try 
		{
			RepositoryConnection con = therepository.getConnection();
			try
			{
				RepositoryResult<Namespace> testname = con.getNamespaces();
				
				while(testname.hasNext())
				{
					System.out.println(testname.next().toString());
				}
			}
			finally 
			{
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 *  dump RDF graph
	 * 
	 * @param out output stream for the serialization
	 * @param outform the RDF serialization format for the dump
	 * @return
	 */
	public void dumpRDF(OutputStream out, RDFFormat outform){
		try {
			RepositoryConnection con = therepository.getConnection();
			try {
				RDFWriter w = Rio.createWriter(outform, out);
				con.export(w, new Resource[0]);
			} finally {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Convenience URI import for RDF/XML sources
	 * 
	 * @param urlstring absolute URI of the data source
	 */
	public void addURI(String urlstring){
		addURI(urlstring, RDFFormat.RDFXML);
	}

	/**
	 *  Import data from URI source
	 *  Request is made with proper HTTP ACCEPT header
	 *  and will follow redirects for proper LOD source negotiation
	 * 
	 * @param urlstring absolute URI of the data source
	 * @param format RDF format to request/parse from data source
	 */
	public void addURI(String urlstring, RDFFormat format){
		try {
			RepositoryConnection con = therepository.getConnection();
			try {
				URL url = new URL(urlstring);
				URLConnection uricon = (URLConnection) url.openConnection();
				uricon.addRequestProperty("accept", format.getDefaultMIMEType());
				InputStream instream = uricon.getInputStream();
				con.add(instream, urlstring, format, new Resource[0]);
			} finally {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Import RDF data from a string
	 * 
	 * @param rdfstring string with RDF data
	 * @param format RDF format of the string (used to select parser)
	 */
	public void addString(String rdfstring,  RDFFormat format){
		try {
			RepositoryConnection con = therepository.getConnection();
			try {
				StringReader sr = new StringReader(rdfstring);
				con.add(sr, "", format, new Resource[0]);
			} finally {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Import RDF data from a file
	 * 
	 * @param location of file (/path/file) with RDF data
	 * @param format RDF format of the string (used to select parser)
	 */
	public void addFile(String filepath,  RDFFormat format){
		try {
			RepositoryConnection con = therepository.getConnection();
			try {
				con.add(new File(filepath), "", format, new Resource[0]);
			} finally {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Insert Triple/Statement into graph 
	 * 
	 * @param s subject uriref
	 * @param p predicate uriref
	 * @param o value object (URIref or Literal)
	 */
	public void add(URI s, URI p, Value o){
		try {
			RepositoryConnection con = therepository.getConnection();
			try {
				ValueFactory myFactory = con.getValueFactory();
				Statement st = myFactory.createStatement((Resource) s, p, (Value) o);
				con.add(st, new Resource[0]);
			}finally {
				con.close();
			}
		}
		catch (Exception e) {
			// handle exception
		}
	}

	/**
	 *  Tuple pattern query - find all statements with the pattern, where null is a wild card 
	 * 
	 * @param s subject (null for wildcard)
	 * @param p predicate (null for wildcard)
	 * 	@param o object (null for wildcard)
	 * @return serialized graph of results
	 */
	public List tuplePattern(URI s, URI p, Value o){
		try{
			RepositoryConnection con = therepository.getConnection();
			try {
				RepositoryResult repres = con.getStatements(s, p, o, true, new Resource[0]);
				ArrayList reslist = new ArrayList();
				while (repres.hasNext()) {
					reslist.add(repres.next());
				}
				return reslist;
			} finally {
				con.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}


