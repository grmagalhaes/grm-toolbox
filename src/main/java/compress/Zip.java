package sofia.toolbox.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * This class contains methods to manipulate ZIP files.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class Zip {

	private String message;

	/**
	 * Returns the message of the last command.
	 *
	 * @return message of the last command
	 */

	public String getMessage() {
		return this.message;
	}

	/**
	 * Extracts the content of a ZIP file in a output folder (but use relative structure stored into the zip)
	 *
	 * <pre>
	 * For example: 
	 * 
	 *   Zip zip = new ZIP().extractUsingStructure("files.zip", "/temp");
	 * </pre>
	 * 
	 * @param     zipFileName  ZIP file name (using the structure stored into de zip)
	 * @param     outputFolder destination folder of the files into ZIP file
	 * @return    ZIP file extracted if <code>true</code> or error if <code>false</code>
	 */

	public boolean extractUsingStructure(String zipFileName, String outputFolder) {

		try {

			ZipFile zf = new ZipFile(zipFileName);

			// Enumerate each entry
			for (Enumeration<? extends ZipEntry> entries = zf.entries(); entries.hasMoreElements();) {

				// Get the entry and its name
				ZipEntry zipEntry = (ZipEntry)entries.nextElement();
				String zipEntryName = zipEntry.getName();

				OutputStream out = new FileOutputStream(outputFolder + File.separator + zipEntryName);
				InputStream in = zf.getInputStream(zipEntry);

				byte[] buf = new byte[1024];
				int len;
				while((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				// Close streams
				out.close();
				in.close();
			}
			
			zf.close();

			return true;			

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}

	}	
	
	/**
	 * Extracts the content of a ZIP file in a output folder (this function ignores the structure stored into the zip)
	 *
	 * <pre>
	 * For example: 
	 * 
	 *   Zip zip = new ZIP().extract("files.zip", "/temp");
	 * </pre>
	 * 
	 * @param     zipFileName  ZIP file name
	 * @param     outputFolder destination folder of the files into ZIP file
	 * @return    ZIP file extracted if <code>true</code> or error if <code>false</code>
	 */	
	
	public boolean extract(String zipFileName, String outputFolder) {

		try {

			ZipFile zf = new ZipFile(zipFileName);

			// Enumerate each entry
			for (Enumeration<? extends ZipEntry> entries = zf.entries(); entries.hasMoreElements();) {

				// Get the entry and its name
				ZipEntry zipEntry = (ZipEntry)entries.nextElement();

				String zipEntryName;
				if (zipEntry.getName().contains("/")) 
					zipEntryName = zipEntry.getName().substring(zipEntry.getName().lastIndexOf("/") + 1, zipEntry.getName().length());
				else 
					if (zipEntry.getName().contains("\\")) 
						zipEntryName = zipEntry.getName().substring(zipEntry.getName().lastIndexOf("\\") + 1, zipEntry.getName().length());	
					else
						zipEntryName = zipEntry.getName();


				OutputStream out = new FileOutputStream(outputFolder + File.separator + zipEntryName);
				InputStream in = zf.getInputStream(zipEntry);

				byte[] buf = new byte[1024];
				int len;
				while((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				// Close streams
				out.close();
				in.close();
			}
			
			zf.close();

			return true;			

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}

	}		

	/**
	 * Create a ZIP file.
	 * 
	 * <pre>
	 * For example: 
	 * 
	 *   String[] files = {"file1.txt", "file2.txt", "file3.txt"};
	 *   Zip zip = new ZIP().create("files.zip", files);
	 * </pre>
	 *
	 * @param     zipFileName ZIP file name
	 * @param     files array containing the file names to be added into ZIP file
	 * @return    ZIP file created if <code>true</code> or error if <code>false</code>
	 */

	public boolean create(String zipFileName, String[] files) {

		byte[] buf = new byte[1024];

		try {

			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));

			// Compress the files
			for (int i=0; i<files.length; i++) {

				FileInputStream in = new FileInputStream(files[i]);

				out.putNextEntry(new ZipEntry(files[i]));

				// Transfer bytes from the file to the ZIP file
				int len;
				while((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				// Complete the entry
				out.closeEntry();
				in.close();
			}

			// Complete the ZIP file
			out.close();

			return true;

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}
	}


}
