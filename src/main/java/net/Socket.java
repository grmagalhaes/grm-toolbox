package sofia.toolbox.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class contains utiliy methods to work with socket.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class Socket {
	
	public static String getLocalHostAddress() {
		
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostAddress();

		} catch (Exception e) {
			return "127.0.0.1";			
		}
	}
	
	public static String getHostAddress(String hostname) {
		try 
		{ 
			InetAddress addr = InetAddress.getByName(hostname); 
			return addr.getHostAddress();
		} 
		catch (Exception e) {
			return null;
		}
	}

	public static String getLocalHostName() {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostName();

		} catch (Exception e) {
			return "127.0.0.1";			
		}

	}

	public static String getHostName(String hostAddress) {

		try 
		{ 
			InetAddress addr = InetAddress.getByName(hostAddress); 
			return addr.getHostName();
		} 
		catch (Exception e) 
		{
			return hostAddress;
		}

	}
	
	public static boolean ping(String hostname) {
		try {
			InetAddress address = InetAddress.getByName(hostname);
			return address.isReachable(1000);
		}
		catch (UnknownHostException e) {
			return false;
		}
		catch (IOException e) {
			return false;
		}
	}	

}
