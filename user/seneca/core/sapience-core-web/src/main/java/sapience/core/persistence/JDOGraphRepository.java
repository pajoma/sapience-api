package sapience.core.persistence;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoTemplate;

import sapience.core.graph.CoreNode;
import sapience.core.graph.CoreRelation;

public class JDOGraphRepository implements GraphRepository {

	private final JdoTemplate jdoTemplate;

	public JDOGraphRepository(PersistenceManagerFactory pmf) {
		this.jdoTemplate = new JdoTemplate(pmf);
	}

	
	
	@Override
	public List<CoreNode> listNodes() {
		try {

			return jdoTemplate.execute(new JdoCallback<List<CoreNode>>() {
				public List<CoreNode> doInJdo(PersistenceManager pm) throws JDOException {
					Query q = pm.newQuery(CoreNode.class);
					List<CoreNode> res = (List<CoreNode> ) q.execute();
					return res;
				}
			});

		} catch (Exception e) {
			throw new JDOException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<CoreRelation> listRelations() {
		try {

			return jdoTemplate.execute(new JdoCallback<List<CoreRelation>>() {
				public List<CoreRelation> doInJdo(PersistenceManager pm) throws JDOException {
					Query q = pm.newQuery(CoreRelation.class);
					List<CoreRelation> res = (List<CoreRelation> ) q.execute();
					return res;
				}
			});

		} catch (Exception e) {
			throw new JDOException(e.getLocalizedMessage(), e);
		}
	}



	@Override
	public void addNodes(final List<CoreNode> nodes) throws IOException {
		try {
			jdoTemplate.execute(new JdoCallback<List<CoreNode>>() {
				public List<CoreNode> doInJdo(PersistenceManager pm) throws JDOException {
					
					// TODO: update the graph of each of the nodes
					pm.makePersistentAll(nodes);
					return null;
				}
			});
		} catch (Exception e) {
			throw new IOException(e);
		}
		
	}

}
