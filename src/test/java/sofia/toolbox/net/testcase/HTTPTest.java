package sofia.toolbox.net.testcase;

import java.io.IOException;

import sofia.toolbox.net.HTTP;
import sofia.toolbox.net.Socket;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class HTTPTest extends TestCase {

	String url = "http://www.google.com";

	String proxyServer = "inet-corp.gnet.petrobras.com.br";		
	String proxyPort = "8080";
	String proxyUser = "petrobras\\eey9";
	String proxyPassword = "grmgrM87";

	HTTP http;	

	public HTTPTest(String name) {
		super(name);
	}	

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}

	public void testHTTP() throws IOException {
		String content; 

		if (Socket.getLocalHostName().toLowerCase().contains("mi")) {
			http = new HTTP(proxyServer, proxyPort, proxyUser, proxyPassword);

			content = http.getUrl(url);
			System.out.println(content);

			if (!content.contains("Google")) fail ("Fail to get tge page\n");
		}
		else {
			http = new HTTP();

			content = http.getUrl(url);
			System.out.println(content);

			if (!content.contains("Google")) fail ("Fail to get tge page\n");
		}
	}

}
