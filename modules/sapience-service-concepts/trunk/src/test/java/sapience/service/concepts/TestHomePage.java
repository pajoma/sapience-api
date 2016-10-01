package sapience.service.concepts;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;

import sapience.core.pages.RootPage;
import sapience.core.wicket.ApplicationCore;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	@Override
	public void setUp()
	{
		tester = new WicketTester(new ApplicationCore());
		
		// add RDf to Expected parrepository 
	}

	public void testRenderMyPage()
	{
		//start and render the test page
		tester.startPage(RootPage.class);

		//assert rendered page class
		tester.assertRenderedPage(RootPage.class);

		//assert rendered label component
		tester.assertLabel("message", "If you see this message wicket is properly configured and running");
	}
}
