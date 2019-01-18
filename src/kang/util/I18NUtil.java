package kang.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18NUtil {
	// Locale.US Locale.Chinese
	private ResourceBundle bundle;
	private String resourceFile;

	private I18NUtil(String resourceFile, Locale locale) {
		this.resourceFile = resourceFile;
		this.bundle = ResourceBundle.getBundle("i18n/" + resourceFile , locale);
	}
	
	public static I18NUtil getInstance(String resourceFile) {
		// TODO 读取配置文件，以确定locale
		
		
		return new I18NUtil(resourceFile, Locale.getDefault());
	}
	
	public static I18NUtil getInstance(String resourceFile, Locale locale) {
		return new I18NUtil(resourceFile, locale);
	}
	
	public String getText(String key) {
		return bundle.getString(key);
	}
	public String getText(String key, Locale locale) {
		return ResourceBundle.getBundle("i18n/" + resourceFile , locale).getString(key);
	}
	
}
