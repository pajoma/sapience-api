package sapience.injectors.stax;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.xpath.XPathExpressionException;

import sapience.injectors.Configuration;
import sapience.injectors.model.xpath.XPathElement;
import sapience.injectors.model.xpath.XPathGenerator;
import sapience.injectors.model.xpath.XPathMatcher;
import sapience.injectors.model.xpath.XPathStack;

import com.ctc.wstx.stax.WstxEventFactory;
import com.ctc.wstx.stax.WstxOutputFactory;

public abstract class StaxBase {
	protected final Configuration config;
	
	private XPathGenerator generator = null;
	private XPathMatcher matcher = null;
	
	private XMLInputFactory inFac;
	private XMLOutputFactory outFac;
	private XMLEventFactory eventFac;
	
	private XPathStack current_path;
	private XPathStack preceding_path;
	
	private NamespaceContext context;
	
	
	/**
	 * the current path, updated during parsing
	 * @return
	 * @throws XPathExpressionException 
	 */
	protected XPathStack getCurrentPath() throws XPathExpressionException {
		if (current_path == null) {
			current_path = new XPathStack();
			current_path.add(getGenerator().asXPathElement("//", context));
			
		}
		return current_path;
	}
	
	protected XPathStack getPrecedingPath() {
		if (preceding_path == null) {
			preceding_path = new XPathStack();
			
		}
		return preceding_path;
	}
	
	protected Configuration getConfiguration() {
		return config;
	}

	public XPathGenerator getGenerator() {
		if (generator == null) {
			generator = new XPathGenerator();
			generator.setDefaultNamespace(config.getDefaultNamespace());	
		}

		return generator;

	}

	public XPathMatcher getMatcher() {
		if (matcher == null) {
			matcher = new XPathMatcher();
			
		}

		return matcher;
	}

	public XMLInputFactory getXMLInputFactory() {
		if (inFac == null) {
			
			inFac = XMLInputFactory.newInstance();
	
			inFac.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
			inFac.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
			inFac.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
			inFac.setProperty(XMLInputFactory.IS_VALIDATING, Boolean.TRUE);
			
		}

		return inFac;
	}

	protected void updateCurrentPath(XPathElement elem) throws XPathExpressionException {
		if(elem != null) getCurrentPath().add(elem);	
	}
	
	public XMLOutputFactory getXMLOutputFactory() {
		if (outFac == null) {
			outFac = WstxOutputFactory.newInstance();
			
		}

		return outFac;
	}

	public XMLEventFactory getXMLEventFactory() {
		if(eventFac == null) {
			eventFac = WstxEventFactory.newInstance();
			
		}
		return eventFac;
	}

	
	public StaxBase( Configuration config) {
		this.config = config;
	}

	public NamespaceContext getNamespaceContext() {
		return context;
	}

	public void updateNamespaceContext(NamespaceContext context) {
		this.context = context;
		
	}
	
	
	protected void handleStartElement(StartElement se) {
		// do nothing
	}
	
	protected void handleEndElement(EndElement ee) {
		// do nothing
	}
	
	protected void handleCharacters(Characters ch) {
		// do nothing
	}
	
	protected void handleStartDocument() {
		// do nothing
	}
}

