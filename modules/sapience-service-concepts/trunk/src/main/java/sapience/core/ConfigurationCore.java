package sapience.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import sapience.core.beans.RequestParameter;
import sapience.core.tasks.BuiltinRegistry;
import sapience.core.tasks.CoreTask;

/**
 * Configuration handler
 * @author allaves
 *
 */
public class ConfigurationCore {
	
	private final String CONF_FILE_PATH = System.getProperty("user.dir") + File.separator + "src" +
											File.separatorChar + "main" + File.separator + "webapp" + File.separator +
											"WEB-INF" + File.separator + "core.xml";

	/** Testing */						
	//private BuiltinRegistry registry;
	/** Testing */
	
	private URL baseurl;
	private CoreTask auxTask;
	private StringBuffer buffer;
	private Iterable<? extends CoreTask> tasks;
	private HashMap<String, ? extends CoreTask> tasksMap;
	
	public ConfigurationCore() {
		//this.registry = BuiltinRegistry.getInstance();
		this.buffer = new StringBuffer();
		this.tasks = new ArrayList();
		this.tasksMap = new LinkedHashMap<String, CoreTask>();
		loadConfiguration();
	}
	
	/**
	 * Parses core.xml and fills a list with the available tasks
	 */
	private void loadConfiguration() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(CONF_FILE_PATH);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.baseurl = new URL(doc.getElementsByTagName("base").item(0).getTextContent());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DOMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		NodeList tasks = doc.getElementsByTagName("task");
		Element elem;
		for (int i = 0; i < tasks.getLength(); i++) {
			elem = (Element) tasks.item(i);
			String taskName = elem.getElementsByTagName("name").item(0).getTextContent();
			String fqname = elem.getElementsByTagName("class").item(0).getTextContent();
			Class <? extends CoreTask> clazz;
			try {
				clazz = Class.forName(fqname).asSubclass(CoreTask.class);
				auxTask = clazz.newInstance();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			auxTask.setName(taskName);
			NodeList properties = elem.getElementsByTagName("property");
			Element prop;
			for (int j = 0; j < properties.getLength(); j++) {
				prop = (Element) properties.item(j);
				String key = prop.getTextContent();
				boolean mandatory = Boolean.valueOf(prop.getAttribute("mandatory"));
				//auxTask.setParameter(new RequestParameter(key, mandatory), null);
				auxTask.setParameter(key, null);
			}
			((ArrayList) this.tasks).add(auxTask);
		}
	}
	
	
//	/**
//	 * Load the available tasks in the registry
//	 */
//	private void buildTasks() {
//		// load tasks from CoreConfiguration
//		//Iterable<? extends CoreTask> taskList = getTasks();
//		// add them to registry
//		for (Iterator it = getTasks().iterator(); it.hasNext(); ) {
////			if(taskCls != null) {
//			try {
//				this.registry.addTask((Class<? extends CoreTask>) it.next().getClass());
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * Returns the available tasks at CORE
	 * @return iterable task list
	 */
	public Iterable<? extends CoreTask> getTasks() {
		return this.tasks;
	}
	

}
