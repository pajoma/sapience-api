package sapience.proxy.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;

import sapience.injectors.model.Reference;
import sapience.proxy.exceptions.ServiceNotRegisteredException;
import sapience.proxy.persistence.model.JDOReference;
import sapience.proxy.persistence.model.JDOStoredServices;

import com.google.appengine.api.memcache.MemcacheService;

public class JDOServiceRepository implements ServiceRepository {

    private final MemcacheService cache;
    private final JdoTemplate jdoTemplate;

    @Autowired
    public JDOServiceRepository(PersistenceManagerFactory pmf,
	    MemcacheService memcacheService) {
	this.jdoTemplate = new JdoTemplate(pmf);
	this.cache = memcacheService;
    }

    public void delete(final String serviceID) throws IOException {
	try {
	    jdoTemplate.execute(new JdoCallback<JDOReference>() {
		public JDOReference doInJdo(PersistenceManager pm) throws JDOException {
		    /* delete all references */
		    cache.delete(serviceID);
		    JDOStoredServices result = loadFromDB(serviceID);
		    pm.deletePersistent(result);

		    return null;
		}
	    });

	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

    private JDOStoredServices loadFromDB(final String serviceID) throws JDOException {
	try {

	    return jdoTemplate.execute(new JdoCallback<JDOStoredServices>() {
		public JDOStoredServices doInJdo(PersistenceManager pm) throws JDOException {
		    Query q = pm.newQuery(JDOStoredServices.class, "sid =='" + serviceID + "'");
		    List<JDOStoredServices> result = (List<JDOStoredServices>) q.execute();
		    if(result.size() > 0) {
			cache.put(serviceID,result.get(0));
			return result.get(0);
		    }
		    return null;
		}
	    });

	} catch (Exception e) {
	    throw new JDOException(e.getLocalizedMessage(), e);
	}
    }

    
    @PostConstruct
    public void fillCache() throws IOException {
	try {
	    cache.clearAll();

	    jdoTemplate.executeFind(new JdoCallback<JDOReference>() {

		@SuppressWarnings("unchecked")
		public JDOReference doInJdo(PersistenceManager pm)
			throws JDOException {
		    // query: find all references in tthe d
		    Query query = pm.newQuery(JDOStoredServices.class);
		    Collection<JDOStoredServices> result = (Collection<JDOStoredServices>) query .execute();

		    // add to cache
		    for (JDOStoredServices u : result) {
			String sid = u.getSID().toString();
			cache.put(sid, u);
		    }
		    return null;
		}
	    });
	} catch (Exception e) {
	    throw new IOException(e);
	}

    }

    public List<Reference> load(String sid) throws IOException {
	try {
	    List<Reference> result = new ArrayList<Reference>();
	    JDOStoredServices jss = loadService(sid);
	    if(jss == null) return result;
	    for (JDOReference ref : jss.listStoredReferences()) {
		result.add(ref);
	    }
	    return result;
	} catch (Exception e) {
	    throw new IOException(e);
	}

    }

    public JDOStoredServices loadService(final String sid) throws IOException {
	try {
	    Object ob = cache.get(sid);
	    
	    if (ob == null) {
		// try to get it from the repository
		return loadFromDB(sid);
	    }

	    if (!(ob instanceof JDOStoredServices))
		throw new IOException("Inconsistent cache, should be JDOStoredServices: "+ ob.getClass().getName());
	    else {
		return (JDOStoredServices) ob;
	    }
	} catch (Exception e) {
	    throw new IOException(e);
	}

    }

    // return jdoTemplate.execute(new JdoCallback<JDOStoredServices>() {
    // public JDOStoredServices doInJdo(PersistenceManager pm) throws
    // JDOException {
    //			
    // Query q =
    // pm.newQuery("select from "+JDOStoredServices.class.getName()+" where sid =='"+serviceID+"'");
    // JDOStoredServices result = (JDOStoredServices) q.execute();
    //			
    // // return (Collection<String>) q.execute();
    // return result;
    // }
    // });

    public Collection<JDOStoredServices> loadServices() throws IOException {
	try {
	    
	    // we don't look in the cache, but query the database (which means
	    // this takes a while)
	    return jdoTemplate
		    .execute(new JdoCallback<Collection<JDOStoredServices>>() {
			public Collection<JDOStoredServices> doInJdo(PersistenceManager pm) throws JDOException {

			    Query q = pm.newQuery(JDOStoredServices.class);
			    Collection<JDOStoredServices> result = (Collection<JDOStoredServices>) q.execute();

			    // return (Collection<String>) q.execute();
			    return result;
			}
		    });
	} catch (Exception e) {
	    throw new IOException(e);
	}
	

    }

    /*
     * (non-Javadoc)
     * 
     * @see sapience.proxy.persistence.ServiceRepository#purge()
     */
    public void purge() throws IOException {
	try {
	    jdoTemplate.deletePersistentAll(jdoTemplate
		    .find(JDOStoredServices.class));
	    cache.clearAll();
	} catch (Exception e) {
	    throw new IOException(e);
	}

    }

    public void store(final JDOStoredServices s) throws IOException {
	try {
	    jdoTemplate.execute(new JdoCallback<JDOStoredServices>() {
		public JDOStoredServices doInJdo(PersistenceManager pm)
			throws JDOException {
		    pm.makePersistent(s);
		    cache.put(s.getSID(), s);
		    return null;
		}
	    });
	} catch (Exception e) {
	    throw new IOException(e);
	}

    }

    public void store(String sid, String request, List<Reference> references)
	    throws IOException {
	try {
	    JDOStoredServices jss = new JDOStoredServices(sid, request,
		    references.toArray(new JDOReference[references.size()]));
	    this.store(jss);
	} catch (Exception e) {
	    throw new IOException(e);
	}

    }

}