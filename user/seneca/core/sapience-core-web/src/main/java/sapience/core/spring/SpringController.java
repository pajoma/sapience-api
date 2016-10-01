package sapience.core.spring;

import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sapience.core.graph.impl.JDOGraphBuilder;
import sapience.core.graph.util.GraphBuilder;
import sapience.core.persistence.GraphRepository;
import sapience.core.persistence.JDOGraphRepository;

@Configuration
public class SpringController {
	
	private @Autowired PersistenceManagerFactory pmf;
	
	
	@Bean
	public GraphRepository createGraphRepository() {
		if(pmf==null) {
			throw new RuntimeException("No PersistenceManagerFactory or Cache initiated");
			//System.exit(0);
		}
		
		return new JDOGraphRepository(pmf);
	}
	
	
	@Bean
	public GraphBuilder createGraphBuilder() {
		return new JDOGraphBuilder();
	}
}
