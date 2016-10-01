package sapience.injectors.xml;

import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.ri.Stax2EventWriterImpl;

public class ExtendedXMLEventWriter extends Stax2EventWriterImpl{

	public ExtendedXMLEventWriter(XMLStreamWriter2 sw) {
		super(sw);
	}

}
