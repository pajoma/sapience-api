package sapience.injectors.wps.identify;

import static junit.framework.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.mime.MimeType;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.ri.Stax2EventFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.xml.internal.stream.writers.XMLEventWriterImpl;

import sapience.injectors.ConfigurationFactory;
import sapience.injectors.TestWSDLInjector;
import sapience.injectors.xml.Reference;
import sapience.injectors.xml.XPathHelper;
import sapience.injectors.xml.XPathStack;

public class TestWoodstox {

	public Stack<String> path;
	public Stack<String> namespaces;
	
	private XMLInputFactory inFac;
	private XMLOutputFactory outFac;
	private XMLEventFactory eventFac;
	
	@Before 
	public void setupFactories() {
		inFac = XMLInputFactory2.newInstance();
		inFac.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
		inFac.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		inFac.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
		
		// output is appernot implemented in woodstox
		outFac = XMLOutputFactory2.newInstance();
		
		eventFac = Stax2EventFactoryImpl.newInstance();

	}

	@Test
	public void testLoad() throws Exception {
		InputStream resourceAsStream = TestWSDLInjector.class.getClassLoader()
				.getResourceAsStream("Test.xml");
		
		/*
		 * Algorithm for efficient injections (using woodstox) for each event if
		 * start element -> 1. add to stack 2. check if current stack path is
		 * part of any reference 2.a yes, but part only: mark all which have
		 * this stack path (which means next time we only match against marked
		 * items) 2.b yes, match: write annotation into outputstream 2.c no:
		 * continue if end element -> 1. pop stack
		 */


		
		
		// where to add annotations
		LookupMockup lo = new LookupMockup();
		this.inject(resourceAsStream, System.out, lo.check(null));


		

	}

	public void inject(InputStream in, OutputStream out, List<Reference> references) {
		StringBuilder pb;
		XPathHelper helper = new XPathHelper();
		helper.setReferences(references);
		
		
		
		path = new Stack<String>();
		
		try {
			XMLEventReader r = inFac.createXMLEventReader(in);
			XMLEventWriter w = outFac.createXMLEventWriter(out);
			

			while (r.hasNext()) {
				
				XMLEvent e = r.nextEvent();
				switch (e.getEventType()) {
				
				case XMLEvent.START_DOCUMENT: 
					w.add(e);
					break;
				case XMLEvent.START_ELEMENT:
					// add to current xpath
					StartElement se = (StartElement) e;
					this.addCurrentElementToPath(path, se);
					System.out.println(helper.toString(path));
					w.add(e);
		
					
					// check if the current xpath is in our list of references
					int marked = helper.checkIf(path);
					if(marked >= 0) {
						// yes, let's inject the reference
						this.inject(w, references.get(marked));
					} else {
						// simply write the element to the output
					}
					break;
				case XMLEvent.END_ELEMENT:
					this.path.pop();
					w.add(e);
					break;
				case XMLEvent.END_DOCUMENT:
					w.add(e);
					break;
				case XMLEvent.COMMENT:
					w.add(e);
//					r.get
//					w.writeComment(data)
					break;
				case XMLEvent.CHARACTERS:
					w.add(e);
				    break;
				case XMLEvent.CDATA:
					w.add(e);
				    break;		
				case XMLEvent.NAMESPACE:
					w.add(e);
				    break;					    
				default:
					break;
				}
			}
		} catch(XMLStreamException ex){
				System.out.println(
					ex.getMessage());
				if(ex.getNestedException()
					!= null) {
					ex.getNestedException().
						printStackTrace();
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
	}
	
	private void inject(XMLEventWriter w, Reference ref) throws IOException, XMLStreamException {
		String annot = ref.getTarget().toString();
		// identify event type of ref
		if(annot.getBytes()[0] == '<') {
			
		} else {
			handleAttribute(w,ref);
			
			
		}
	}
	
	private void handleAttribute(XMLEventWriter w, Reference ref) throws XMLStreamException {
		String target = ref.getTarget().toString();
		String[] attributes = target.split(" ");
		for(String attribute : attributes) {
			w.add(createAttribute(attribute));
		}
		
		
	}

	private Attribute createAttribute(String target) {
		String[] split = target.split("=");	// split between qname and value
		// TODO: this supports only the localname, any prefixes are or namespaces are omitted
		// Strategy: when storing annotations, we always store the full namespace, if we detect it here
		// we create a new qname with an arbitrary prefix (or having some predefined ones such as sawsdl: or xlink:
		int pos = split[0].indexOf(':');	 
		if(pos >= 0) {
			split[0] = split[0].substring(pos);
		}
		QName name = new QName(split[0]);
		return eventFac.createAttribute(new QName(split[0]), split[1]);
	}
	
	private void addCurrentElementToPath(Stack<String> stack, StartElement se) {
		
		StringBuilder pb = new StringBuilder();
		// element has attributes, we have to add them to the xpath for identification
		Iterator<Attribute> attributes = (Iterator<Attribute>) se.getAttributes();
		pb.append(se.getName().getLocalPart());
		
		if(attributes.hasNext()) {
			pb.append("[");
			while(attributes.hasNext()) {
				Attribute next = attributes.next();
				this.writeAttribute(pb, next);
				if(attributes.hasNext()) pb.append(" and ");
			}
			pb.append("]");
		}
		
		stack.add(pb.toString());
	}
	
	private void writeAttribute(StringBuilder pb, Attribute a) {
		// TODO: we have to consider the namespaces!! with this approach we simply assume that prefixes don't change
		QName q = a.getName();
		if(q.getPrefix().length() > 0) {
			pb.append(q.getPrefix());
			pb.append(":");
		}
		pb.append(q.getLocalPart());
		pb.append("=");
		pb.append(a.getValue());
		
	}

	public void testLoad2() throws ParserConfigurationException, SAXException,
			IOException {
		InputStream resourceAsStream = TestWSDLInjector.class.getClassLoader()
				.getResourceAsStream("InjectorConfig.xml");

		DocumentBuilderFactory parserFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder parser = parserFactory.newDocumentBuilder();
		Document doc = parser.parse(resourceAsStream);
		NodeList nodes = doc.getElementsByTagName("configuration");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element config = (Element) nodes.item(i);
			config.getChildNodes();
			// und so weiter

		}

	}
}
