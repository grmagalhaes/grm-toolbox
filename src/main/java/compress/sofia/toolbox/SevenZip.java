package sofia.toolbox.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

/**
 * This class contains methods to manipulate 7ZIP files.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class SevenZip {

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
	 * Extracts the content of a 7ZIP file in a output folder (this function ignores the structure stored into the zip)
	 *
	 * <pre>
	 * For example: 
	 * 
	 *   SevenZip sevenZip = new SevenZip().extract("files.7z", "/temp");
	 * </pre>
	 * 
	 * @param     zipFileName  ZIP file name
	 * @param     outputFolder destination folder of the files into ZIP file
	 * @return    ZIP file extracted if <code>true</code> or error if <code>false</code>
	 */	

	public boolean extract(String sevenZipFileName, String outputFolder) {

		try {

			SevenZFile sevenZFile = new SevenZFile(new File(sevenZipFileName));

			SevenZArchiveEntry entry = sevenZFile.getNextEntry();			

			while(entry != null) {

				// Get the entry and its name

				String szEntryName;
				if (entry.getName().contains("/")) 
					szEntryName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1, entry.getName().length());
				else 
					if (entry.getName().contains("\\")) 
						szEntryName = entry.getName().substring(entry.getName().lastIndexOf("\\") + 1, entry.getName().length());	
					else
						szEntryName = entry.getName();

				FileOutputStream out = new FileOutputStream(outputFolder + File.separator + szEntryName);

				byte[] content = new byte[(int) entry.getSize()];

				sevenZFile.read(content, 0, content.length);
				out.write(content);
				out.close();
				entry = sevenZFile.getNextEntry();
			}

			// Close streams
			sevenZFile.close();


			return true;			

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}

	}		

	/**
	 * Create a 7ZIP file.
	 * 
	 * <pre>
	 * For example: 
	 * 
	 *   String[] files = {"file1.txt", "file2.txt", "file3.txt"};
	 *   SevenZip zip = new SevenZip().create("files.zip", files);
	 * </pre>
	 *
	 * @param     sevenZipFileName 7ZIP file name
	 * @param     files array containing the file names to be added into 7ZIP file
	 * @return    7ZIP file created if <code>true</code> or error if <code>false</code>
	 */

	public boolean create(String sevenZipFileName, String[] files) {

		byte[] buf = new byte[1024];

		try {

			SevenZOutputFile out = new SevenZOutputFile(new File(sevenZipFileName));

			// Compress the files
			for (int i=0; i<files.length; i++) {

				SevenZArchiveEntry entry = out.createArchiveEntry(new File(files[i]), files[i]);	
				out.putArchiveEntry(entry);

				FileInputStream in = new FileInputStream(files[i]);

				// Transfer bytes from the file to the 7Z file
				int len;
				while((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				// Complete the entry
				out.closeArchiveEntry();
				in.close();
			}

			// Complete the 7Z file
			out.close();

			return true;

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}
	}


}
