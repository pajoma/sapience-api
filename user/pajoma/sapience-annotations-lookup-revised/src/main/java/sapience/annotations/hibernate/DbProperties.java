package sapience.annotations.hibernate;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DbProperties {
	private static final String BUNDLE_NAME = "sapience.annotations.hibernate.db"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private DbProperties() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
