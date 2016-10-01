package sapience.injectors.spring;


import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sapience.injectors.Lookup;
import sapience.injectors.factories.ConfigurationFactory;
import sapience.injectors.factories.ReferenceFactory;
import sapience.lookup.LookupImpl;
import sapience.lookup.hibernate.model.HibernateModelFactory;



@Configuration
public class BeansConfiguration {

	@Bean
	public ReferenceFactory createReferenceFactory() {
		return new HibernateModelFactory();
	}
	@Bean
	public Lookup createLookup() throws IOException {
		return LookupImpl.getInstance();
	}
	
	@Bean
	public ConfigurationFactory createConfigurationFactory() throws IOException {
		ConfigurationFactory fac = ConfigurationFactory.get();
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("InjectorConfig.xml");
		fac.loadConfiguration(resourceAsStream);
	
		
		fac.setLookup(createLookup());
		fac.setReferenceFactory(createReferenceFactory());
		
		;

		return fac;
		
	}
}
