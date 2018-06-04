package sofia.toolbox.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HTTP {
	
	private String proxyHost;
	private String proxyPort;
	private String proxyUser;
	private String proxyPassword;
	private URL url;	
	
	public HTTP(String proxyHost, String proxyPort, String proxyUser, String proxyPassword) {
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		this.proxyUser = proxyUser;
		this.proxyPassword = proxyPassword;
	}
	
	public HTTP(String proxyHost, String proxyPort) {
		this(proxyHost, proxyPort, null, null);
	}
	

	public HTTP() {
		this(null, null, null, null);
	}
	
	
	public String getUrl(String url) throws IOException {
		
		try {
			if (this.proxyHost != null) System.setProperty("http.proxyHost", proxyHost);
			if (this.proxyPort != null) System.setProperty("http.proxyPort", proxyPort);
			if (this.proxyUser != null) System.setProperty("http.proxyUser", proxyUser);
			if (this.proxyPassword != null) System.setProperty("http.proxyPassword", proxyPassword);
			
			this.url = new URL(url);
			URLConnection uc = this.url.openConnection();
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		    String encodedUserPwd =  encoder.encode((proxyUser + ":" + proxyPassword).getBytes());
		    uc.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
		    
	        BufferedReader rdr = new BufferedReader(new InputStreamReader(this.url.openStream(), "UTF-8"));
	        String buf = null;
	        StringBuffer output = new StringBuffer();
	        while ((buf = rdr.readLine()) != null) {
	            output.append(buf.replaceAll("&lt;p&gt;", ""));
	        }
	        
			return output.toString();

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void saveImage(String url, String filename) throws IOException {
		
		try {
			if (this.proxyHost != null) System.setProperty("http.proxyHost", proxyHost);
			if (this.proxyPort != null) System.setProperty("http.proxyPort", proxyPort);
			if (this.proxyUser != null) System.setProperty("http.proxyUser", proxyUser);
			if (this.proxyPassword != null) System.setProperty("http.proxyPassword", proxyPassword);
			
			this.url = new URL(url);
			URLConnection uc = this.url.openConnection();
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		    String encodedUserPwd =  encoder.encode((proxyUser + ":" + proxyPassword).getBytes());
		    uc.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
		    
		    InputStream in = new BufferedInputStream(this.url.openStream());
		    
		    File file = new File(filename);
	        FileOutputStream fos = new FileOutputStream(file);
	        
		    byte[] buf = new byte[1024];
		    int n = 0;
		    while (-1!=(n=in.read(buf)))
		    {
		       fos.write(buf, 0, n);
		    }
		    fos.close();
		    in.close();
		    
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String getImageBase64(String url) throws IOException {
		
		try {
			if (this.proxyHost != null) System.setProperty("http.proxyHost", proxyHost);
			if (this.proxyPort != null) System.setProperty("http.proxyPort", proxyPort);
			if (this.proxyUser != null) System.setProperty("http.proxyUser", proxyUser);
			if (this.proxyPassword != null) System.setProperty("http.proxyPassword", proxyPassword);
			
			this.url = new URL(url);
			URLConnection uc = this.url.openConnection();
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		    String encodedUserPwd =  encoder.encode((proxyUser + ":" + proxyPassword).getBytes());
		    uc.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
		    
		    InputStream in = new BufferedInputStream(this.url.openStream());
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    
	        byte[] buf = new byte[1024];
		    int n = 0;
		    while (-1!=(n=in.read(buf)))
		    {
		       baos.write(buf, 0, n);	 
		    }
		    in.close();
		    
		    String output = "data:" + uc.getContentType() + ";base64," + encoder.encode(baos.toByteArray());
		    baos.close();
		    
		    return (output.replaceAll("\n", "").replace("\r", ""));
		    
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
}		
