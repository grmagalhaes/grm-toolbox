package sofia.toolbox.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

import sofia.toolbox.util.Text;

/**
 * This class contains methods to work with cryptography MD5.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public final class MD5 {

	private String message;
	private String value;

	public MD5() {

	}

	public MD5(final String value) {
		this.value = value;
	}
	
	public String getMessage() {
		return this.message;
	}	

	public String getHashCode(final String text) {
		int i;
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("MD5");

			byte[] digestBits = null;

			for (i = 0; i < text.length(); i++) {
				md.update((byte) text.charAt(i));
			}

			digestBits = md.digest();

			return Text.byteToHex(digestBits, 16).toUpperCase();
			
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return null;			
		}

	}

	public String getHashCode(final FileInputStream fis) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");

			byte[] digestBits = null;

			byte[] dataBytes = new byte[1024];
			int nread;
			nread = fis.read(dataBytes);

			while (nread > 0) {
				md.update(dataBytes, 0, nread);
				nread = fis.read(dataBytes);
			};		

			fis.close();

			digestBits = md.digest();

			return Text.byteToHex(digestBits, 16).toUpperCase();

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return null;
		}
	}
	
	public String getFileHashCode(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			return this.getHashCode(fis);
		}
		catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return null;
		}

	}

	public String getHashCode() {
		return getHashCode(this.value);
	}

	public String toString() {
		return getHashCode(this.value);
	}

}
