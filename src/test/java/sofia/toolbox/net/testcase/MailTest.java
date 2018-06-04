package sofia.toolbox.net.testcase;

import java.io.File;

import sofia.toolbox.io.FileSystem;
import sofia.toolbox.net.SMTP;
import sofia.toolbox.net.Socket;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class MailTest extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;	

	public MailTest(String name) {
		super(name);
	}	

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}

	public void testSend() {

		System.out.println("method: " + this.getName());
		
		SMTP smtp = new SMTP("smtp.futebolnacional.com.br", 587);

		smtp.connect("contato@futebolnacional.com.br", "avT900900");
		
		String body = "<html><head><title>Sofia ECM 2.0</title></head>" + 
		              "<body><h1>Sofia ECM 2.0</h1>" + 
		              "<br>從系統斷開</body></html>";

        String from = "Sofia ECM 2.0 <contato@futebolnacional.com.br>";
        
        String to = "Gerson Rodrigues <gerson.rodrigues@gmail.com>";
        
        if (!Socket.getLocalHostName().toUpperCase().contains("MI")) {
          if (!smtp.send(from,  to, "Message test in chinese without image", body)) 
        	  fail("Fail to send the e-mail\n" + smtp.getMessage());
        }
        
	}
	
	public void testSend2() {

		System.out.println("method: " + this.getName());
		
		SMTP smtp = new SMTP("smtp.futebolnacional.com.br", 587);

		smtp.connect("contato@futebolnacional.com.br", "avT900900");
		
		String body = "<html><head><title>Sofia ECM 2.0</title></head>" + 
		              "<body><h1><img src=\"cid:mail_test_logo.png\">&nbsp;Sofia ECM 2.0</h1>" + 
		              "<br>Esta é uma mensagem de teste</body></html>";
		
		String[] images = {currentDir + "mail_test_logo.png"};

        String from = "Sofia ECM 2.0 <contato@futebolnacional.com.br>";
        
        String to = "Gerson Rodrigues <gerson.rodrigues@gmail.com>";
        
        if (!Socket.getLocalHostName().toUpperCase().contains("MI")) {
          if (!smtp.send(from, to, null, null, "Mensagem de teste com imagem", body, null, images)) 
        	  fail("Fail to send the e-mail\n" + smtp.getMessage());
        }
        
	}	


}
