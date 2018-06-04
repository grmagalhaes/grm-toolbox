package sofia.toolbox.util;

public class OS {

	public String getLanguage() {
		return  System.getProperty("user.language", null).toUpperCase();
	}

	public String getCountry() {
		return  System.getProperty("user.country", null).toUpperCase();
	}

	public String getUserName() {
		return  System.getProperty("user.name", null);
	}

	public String getUserDir() {
		return  System.getProperty("user.dir", null);
	}

	public String getUserHome() {
		return  System.getProperty("user.home", null);
	}

	public String getUserTimeZone() {
		return  System.getProperty("user.timezone", null);
	}

	public String getJavaVersion() {
		return  System.getProperty("java.version", null);
	}

	public String getJavaSpecificationVersion() {
		return  System.getProperty("java.specification.version", null);
	}

	public String getJavaVMVersion() {
		return  System.getProperty("java.vm.version", null);
	}

	public String getJavaIOTmpDir() {
		return  System.getProperty("java.io.tmpdir", null);
	}

	public String getOSName() {
		return  System.getProperty("os.name", null);
	}

	public String getOSVersion() {
		return  System.getProperty("os.version", null);
	}

	public String getOSArch() {
		return  System.getProperty("os.arch", null);
	}


}
