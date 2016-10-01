package sapience.core.tasks;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import sapience.core.ConfigurationCore;
import sapience.core.tasks.builtins.Describe;

import com.google.inject.Singleton;


public class BuiltinRegistry {

	private static Map<String, ? extends CoreTask> map = null;
	private static BuiltinRegistry instance = new BuiltinRegistry();
	
	private ConfigurationCore conf = null;
	
	
	private BuiltinRegistry() {
		this.conf = new ConfigurationCore();
		this.map = new LinkedHashMap();
		buildTasks();
	}
	
	public static BuiltinRegistry getInstance() {
		return instance;
	}
	
	
//	private static Map<String, CoreTask> getRegistry() {
//		if (map == null) {
//			map = new HashMap<String, CoreTask>();
//		}
//		return map;
//		
//	}
	
	public Map<String, ? extends CoreTask> getMap() {
		return this.map;
	}

	public CoreTask getTask(String name) {
		if(map.containsKey(name)) {
			return map.get(name);
		} else return null;
	}
	
	
	public void addTask(Class<? extends CoreTask> cls) throws InstantiationException, IllegalAccessException {
		//CoreTask inst = cls.newInstance();
		//Describe desc = cls.newInstance();
		//Class<? extends CoreTask> inst = cls.asSubclass(CoreTask.class);
		//map.put("", cls.newInstance());
		
	}
	
	/**
	 * Load the available tasks in the registry
	 */
	private void buildTasks() {
		// load tasks from CoreConfiguration
		//Iterable<? extends CoreTask> taskList = getTasks();
		// add them to registry
		//CoreTask auxTask = null;
		System.out.println("Tasks list from configurationCore: " + this.conf.getTasks().toString());
		
		for (Iterator it = this.conf.getTasks().iterator(); it.hasNext(); ) {
//			if(taskCls != null) {
			try {
				this.addTask((Class<? extends CoreTask>) it.next());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
