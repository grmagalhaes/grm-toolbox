package sofia.toolbox.net.testcase;

import java.io.File;

import sofia.toolbox.io.FileSystem;
import sofia.toolbox.net.FTPS;
import sofia.toolbox.net.Socket;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class FTPSTest extends TestCase {

	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;	

	String server = "ftp2.petrobras.com.br";
	String user = "teste";
	String password = "tEs!07te";
	String remoteDir = "/teste";

	FTPS ftps;

	public FTPSTest(String name) {
		super(name);
	}	

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}

	public void testFTPS() {

		String server = "test.rebex.net";
		String user = "demo";
		String password = "password";
		String remoteDir = "/";		

		if (Socket.getLocalHostName().toLowerCase().contains("mi")) {
			server = "ftp2.petrobras.com.br";
			user = "teste";
			password = "tEs!07te";
			remoteDir = "/teste";
		}

		ftps = new FTPS(server, 990);
		ftps.connect(user, password);

		ftps.setLocalDir(currentDir);
		ftps.setRemoteDir(remoteDir);

		ftps.clearFileList();
		ftps.setRemoteVerification(false);		

		if (Socket.getLocalHostName().toLowerCase().contains("mi")) {		
			ftps.addFileList("testftp.jpg");
			if (!ftps.upload()) fail ("Fail to upload the file\n" + ftps.getReplyString());
			if (!ftps.download()) fail("File not downloaded\n" + ftps.getMessage());	
			if (!ftps.delete("testftp.jpg")) fail ("Fail to delete the file\n"  + ftps.getReplyString());		
		}
		else {
			
			File file = new File (currentDir + "readme.txt");
			if (file.exists()) file.delete();

			ftps.addFileList("readme.txt");
			if (!ftps.download()) fail("File not downloaded\n" + ftps.getMessage());	
		}

		ftps.disconnect();

	}

}
