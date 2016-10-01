package sapience.injectors;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.identify.TestingIdentification;
import org.junit.Test;

public class TestReadWSDL20References extends TestingIdentification {
	
	
	@Test
	public void setupp() throws IOException {
		InputStream resourceAsStream = TestInjectorFactory.class.getClassLoader().getResourceAsStream("InjectorConfig.xml");
		SelectorFactory.getInstance().loadConfig(resourceAsStream);
		
		Extractor ex = SelectorFactory.getInstance().getExtractor(getFileURL().openStream());
		ex.storeAnnotation("myid", getFileURL().openStream());
		
		

	}

	@Override
	public String getFileName() {
		return "TestWSDL_2_0(Refs).wsdl";
	}
	
	
}
