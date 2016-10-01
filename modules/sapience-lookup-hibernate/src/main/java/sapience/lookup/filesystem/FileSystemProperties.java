package sapience.lookup.filesystem;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class represents a Properties-Class to get access to the fs.properties,
 * where all possible properties of the filesystem are stored.
 * 
 * @author Henry Michels
 */
public class FileSystemProperties {
	private static final String BUNDLE_NAME = "sapience.annotations.lookup.filesystem.fs"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * empty Construcor
	 */
	private FileSystemProperties() {
		
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
