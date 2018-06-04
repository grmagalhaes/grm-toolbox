package sofia.toolbox.util;

/**
 * This class contains methods to work with log files (using log4j).
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class Logger {

	// Logger constants
	public static final int OFF = 0;
	public static final int FATAL = 1;	
	public static final int ERROR = 2;
	public static final int WARN = 3;
	public static final int INFO = 4;	
	public static final int DEBUG = 5;
	public static final int ALL = 6;

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Logger.class);

	private static Logger instance = null;	

	private String message;
	private String messageCode;

	public static Logger getInstance()  {
		if (instance == null) 
			return new Logger();
		return instance;
	}	
	
	public void error(String message) {
		logger.error(message);
	}

	public void warn(String message) {
		logger.warn(message);
	}    

	public void fatal(String message) {
		logger.fatal(message);
	}    

	public void debug(String message) {
		logger.debug(message);
	}    

	public void info(String message) {
		logger.info(message);
	}   

	public String getMessageCode() {
		return this.messageCode;
	}
	
	public String getMessage() {
		return this.message;
	}

	public String getStackTrace(Exception e) {

		if (e == null) return null;

		StackTraceElement[] st = e.getStackTrace();

		StringBuffer result = new StringBuffer();

		int i= 0;

		for (StackTraceElement ste:st) {
			result.append(Text.repeat(" ", i++) + ste.getClassName() + "." + ste.getMethodName() + "() (" + 
					ste.getFileName() + ":" + ste.getLineNumber() + ")\n");
		}

		return result.toString();
	}

	public void setMessage(String method, String messageCode, String message, int logLevel) {
		
		if (method == null) method = "unknown";
		if (message == null) message = "null";

		this.messageCode = messageCode;		
		this.message = message.replaceAll("\\n", "\\\\n");


		switch (logLevel) {
		case DEBUG : logger.debug(method + ": " + this.getMessage()); break;
		case ERROR : logger.error(method + ": " + this.getMessage()); break;
		case FATAL : logger.fatal(method + ": " + this.getMessage()); break;
		case INFO  : logger.info(method  + ": " + this.getMessage()); break;
		case WARN  : logger.warn(method  + ": " + this.getMessage());
		}
	}
	
	public void setMessage(String method, Exception exception, int logLevel) {

		this.setMessage(method, null, exception.getLocalizedMessage(), logLevel);

		if (exception != null) {
			switch (logLevel) {
			case DEBUG : logger.debug(this.getStackTrace(exception)); break;
			case ERROR : logger.error(this.getStackTrace(exception)); break;
			case FATAL : logger.fatal(this.getStackTrace(exception)); break;
			case INFO  : logger.info(this.getStackTrace(exception));  break;
			case WARN  : logger.warn(this.getStackTrace(exception)); 
			}
		}
	}
}
