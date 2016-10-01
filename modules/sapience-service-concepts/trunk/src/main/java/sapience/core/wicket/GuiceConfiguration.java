package sapience.core.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;

import sapience.core.controllers.RepositoryController;
import sapience.core.controllers.TaskController;
import sapience.core.controllers.sesame.SesameRepositoryController;
import sapience.core.controllers.sesame.SesameTaskController;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

public class GuiceConfiguration {
	
	Injector injector = null;
	private GuiceComponentInjector guiceComponentInjector;
	private final WebApplication app;
	
	public GuiceConfiguration(WebApplication app) {
		this.app = app;
	}
	
	public GuiceComponentInjector getComponentInstantiationListener() {
		if(guiceComponentInjector == null) {
			guiceComponentInjector = new GuiceComponentInjector(app, getInjector());
		}
		return guiceComponentInjector;
	}
	
	public Injector getInjector() {
		if (injector == null) {
			injector = Guice.createInjector(app.getConfigurationType().equals(
					Application.DEVELOPMENT) ? Stage.DEVELOPMENT
							: Stage.PRODUCTION, getModule());
			
		}
		return injector;
		
	}

	/**
	 * Binds the Guice modules
	 */
	private Module getModule() {
		return new Module() {
			public void configure(Binder binder) {
		
					try {
						
						RepositoryController<Repository> sesameRepositoryController = new SesameRepositoryController(app.getServletContext());
						binder.bind(RepositoryController.class).toInstance(sesameRepositoryController);
						
						binder.bind(TaskController.class).toInstance(new SesameTaskController(sesameRepositoryController));
						
					} catch (RepositoryException e) {
						throw new RuntimeException(e);
					}
		
			}
		};
	}
}
