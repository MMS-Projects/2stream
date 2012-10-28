package net.mms_projects.tostream;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_NAME = "res.lang";

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(
			BUNDLE_NAME, new Locale("en"));

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static void setLocale(Locale locale) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	}

	private Messages() {
	}
}
