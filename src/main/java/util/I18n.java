package sofia.toolbox.util;

import java.util.Formatter;

import sofia.toolbox.io.Property;

/**
 * This class contains methods to work with internationalization (through
 * property files).
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class I18n {

	private Property i18n;

	public I18n(String i18nFile) {
		this.i18n = new Property(i18nFile);
	}

	public String getValue(String code) {
		return this.i18n.getProperty(code);
	}

	public String getValue(String code, Object... params) {

		if (params == null)
			return getValue(code);
		if (params.length == 0)
			return getValue(code);

		StringBuilder string = new StringBuilder();
		Formatter fm = new Formatter(string);
		
		String value = fm.format(this.i18n.getProperty(code), params).toString();
		fm.close();
		
		return value;

	}

	public static String getDefaultCountry(String language) {
		String country =  "";
		if (language.equalsIgnoreCase("pt"))
			country = "BR";
		else if (language.equalsIgnoreCase("pt_BR"))
			country = "BR";
		else if (language.equalsIgnoreCase("en"))
			country = "US";
		else if (language.equalsIgnoreCase("es"))
			country = "ES";
		else if (language.equalsIgnoreCase("zh"))
			country = "CN";
		else if (language.equalsIgnoreCase("zh_CN"))
			country = "CN";		
		else if (language.equalsIgnoreCase("he"))
			country = "IL";			
		return (country);
	}
}
