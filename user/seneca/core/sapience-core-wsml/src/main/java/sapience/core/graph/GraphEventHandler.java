package sapience.core.graph;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Properties;

import org.openrdf.model.Statement;



import sapience.core.graph.impl.CoreNodeImpl;
import sapience.core.graph.impl.JDOGraphBuilder;
import sapience.core.graph.util.GraphBuilder;
import sapience.core.rules.DescriptionSubjectRule;
import sapience.core.rules.DomainRelationRule;
import sapience.core.rules.LabelSubjektRule;
import sapience.core.rules.RangeRelationRule;
import sapience.core.rules.SubClassOfRule;
import sapience.core.rules.TypeClassRule;
import sapience.core.rules.TypePropertyPresentRule;
import sapience.core.rules.TypePropertyRule;

public class GraphEventHandler {
	
	TypeClassRule rule1;
	SubClassOfRule rule2;
	DescriptionSubjectRule rule3;
	LabelSubjektRule rule4;
	TypePropertyRule rule5;
	DomainRelationRule rule6;
	RangeRelationRule rule7;
	TypePropertyPresentRule rule8;
	private JDOGraphBuilder graphHandler;
	
	
	public GraphEventHandler(JDOGraphBuilder gb)
	{	
		setGraphHandler(gb);

		rule1 = new TypeClassRule();
		rule2 = new SubClassOfRule();
		
		rule3 = new DescriptionSubjectRule();
		
		rule4 = new LabelSubjektRule();
		
		rule5 = new TypePropertyRule();
		
		rule6 = new DomainRelationRule();
		
		rule7 = new RangeRelationRule();
		
		rule8 = new TypePropertyPresentRule();
		//ToDo: read rules out of property file
		//Class rule = Class.forName("SubClassOfRule");
		
		//readRules();
	}
	
	public boolean detectEvent(Statement sts) throws URISyntaxException
	{
		// TODO: if rule meets, break
		
		if	(rule1.meetsRule(sts, getGraphHandler())) return true;
		else if	 (rule2.meetsRule(sts, getGraphHandler())) return true;
		else if( rule3.meetsRule(sts, getGraphHandler())) return true;
		else if( rule4.meetsRule(sts, getGraphHandler())) return true;
		else if( rule5.meetsRule(sts, getGraphHandler())) return true;
		else if( rule6.meetsRule(sts, getGraphHandler())) return true;
		else if( rule7.meetsRule(sts, getGraphHandler())) return true;
		else if( rule8.meetsRule(sts, getGraphHandler())) return true;

		return false;
	}
	
	public void readRules() 
	{
		Properties prop = new Properties();
	    
	    try 
	    {
	    	FileInputStream fis = new FileInputStream("src/test/resources/sapience/core/wsml/RulesConfig.xml");
			prop.loadFromXML(fis);
		} catch (InvalidPropertiesFormatException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    prop.list(System.out);
	    System.out.println(prop.elements().nextElement().toString());
	    System.out.println("\nThe foo property: " +
	        prop.getProperty("fu"));
	  }

	public void setGraphHandler(JDOGraphBuilder graphHandler) {
		this.graphHandler = graphHandler;
	}

	public JDOGraphBuilder getGraphHandler() {
		return graphHandler;
	}

}

