package sofia.toolbox.net.testcase;

import java.io.File;

import sofia.toolbox.io.FileSystem;
import sofia.toolbox.net.FTP;
import sofia.toolbox.net.Socket;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class FTPTest extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;	
	
	String server = "ftp.futebolnacional.com.br";
	String user = "futeboln";
	String password = "avT900900";
	
	String proxyServer = "inet-ftp.petrobras.com.br";		
	String proxyUser = "eey9";
	String proxyPassword = "grmgrM88";
	
	FTP ftp;	

	public FTPTest(String name) {
		super(name);
	}	

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}

	public void testFTP() {

		if (Socket.getLocalHostName().toLowerCase().contains("mi")) {
	      ftp = new FTP(proxyServer, 21);
			
		  ftp.connect(user + "@" + server + " " + proxyUser, password);
		  ftp.acct(proxyPassword);
		}
		else {
		  ftp = new FTP(server, 21);
		  ftp.connect(user, password);
		}
		
		ftp.setLocalDir(currentDir);
		ftp.setRemoteDir("/");
		
		ftp.clearFileList();
		ftp.addFileList("testftp.jpg");
	    ftp.setRemoteVerification(false);
		
		if (!ftp.upload()) fail ("Fail to upload the file\n" + ftp.getMessage());
		if (!ftp.download()) fail("File not downloaded\n" + ftp.getMessage());		
		if (!ftp.delete("testftp.jpg")) fail ("Fail to delete the file\n"  + ftp.getMessage());

		ftp.disconnect();
	}
	
}
