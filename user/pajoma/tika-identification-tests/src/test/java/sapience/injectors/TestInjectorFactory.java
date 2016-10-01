package sapience.injectors;

import static junit.framework.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class TestInjectorFactory {

	
	@Test
	public void setup() throws IOException {
		InputStream resourceAsStream = TestInjectorFactory.class.getClassLoader().getResourceAsStream("InjectorConfig.xml");
		assertNotNull(resourceAsStream);
		
		SelectorFactory.getInstance().loadConfig(resourceAsStream);
		Collection<Selector> list = SelectorFactory.getInstance().listSelectors();
		
		assertEquals(3, list.size());
		
		
		
	}
	
	
}
