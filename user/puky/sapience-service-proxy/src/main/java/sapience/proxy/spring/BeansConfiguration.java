package sapience.proxy.spring;

import java.io.IOException;
import java.io.InputStream;

import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sapience.injectors.Lookup;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.factories.ReferenceFactory;
import sapience.proxy.persistence.JDOServiceRepository;
import sapience.proxy.persistence.LookupImpl;
import sapience.proxy.persistence.ServiceRepository;
import sapience.proxy.persistence.model.JDOModelFactory;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@Configuration
public class BeansConfiguration {
	private @Autowired PersistenceManagerFactory pmf;
	
	@Bean
	public ReferenceFactory createReferenceFactory() {
		return new JDOModelFactory();
	}
	
	@Bean
	public ServiceRepository createServiceRepository() {
		MemcacheService ms = createCache();
		if((pmf==null) || (ms==null)) {
			throw new RuntimeException("No PersistenceManagerFactory or Cache initiated");
			//System.exit(0);
		}
		
		return new JDOServiceRepository(pmf, ms);
	}

	@Bean
	public MemcacheService createCache() {

		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		return ms;
		
	}
	
	@Bean
	public ConfigurationFactory createConfigurationFactory() throws IOException {
		ConfigurationFactory fac = ConfigurationFactory.get();
		
		String location = "META-INF/InjectorConfig.xml";
		// we can override the location
		if(System.getProperty("injector.config") != null) {
			location = System.getProperty("injector.config");
		}
		
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);
		fac.loadConfiguration(resourceAsStream);
		fac.setLookup(createLookup());
		fac.setReferenceFactory(createReferenceFactory());

		return fac;
	}
		
	

	
	@Bean
	public Lookup createLookup() {
		return new LookupImpl();
	}
}
