package sapience.lookup.hibernate.controller;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class represents a Properties-Class to get access to the db.properties,
 * where all possible properties of the database are stored.
 * 
 * @author Henry Michels
 */
public class DbProperties {
	private static final String BUNDLE_NAME = "sapience.lookup.hibernate.controller.sqlite"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * empty Constructor
	 */
	private DbProperties() {
	}

	/**
	 * Method to get a String via a given key.
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
