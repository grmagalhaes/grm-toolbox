package sofia.toolbox.net;

import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 * This class contains methods to work with the soap (web services) protocol.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class RPC {

	private String endpoint;
	private String message;

	public RPC(String endpoint) {
		this.endpoint = endpoint;
	}

	public final String getEndpoint() {
		return this.endpoint;
	}		
	
	public final String getMessage() {
		return this.message;
	}
	

	public final Object call(String command, Object... params) {
		try {

			Service service = new Service();
			Call  call = (Call) service.createCall();

			call.setTargetEndpointAddress( new java.net.URL(this.endpoint) );
			call.setOperationName(new QName(this.endpoint, command));
			
			return (call.invoke (params));
			

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return null;
		}		

	}
	
}
