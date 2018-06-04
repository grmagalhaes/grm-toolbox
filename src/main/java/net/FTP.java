package sofia.toolbox.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;

import sofia.toolbox.io.FileSystem;

/**
 * This class contains methods to work with the ftp protocol.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class FTP {

	private FTPClient ftpClient = null;

	protected String server = null;
	protected int port;
	protected String user = null;	
	protected String password = null;

	private String localDir = null;
	private String remoteDir = null;

	ArrayList<String> fileList = new ArrayList<String>();
	ArrayList<String> errorFileList = new ArrayList<String>();	

	private String message;

	public FTP() {
		this.ftpClient = new FTPClient();
	}

	public FTP(String server) {
		this();
		this.server = server;
		this.port = 21;
	}

	public FTP(String server, int port) {
		this();
		this.server = server;
		this.port = port;
	}

	public String getMessage() {
		return this.message;
	}	
	
	public void setProxyServer(String server, int port) {
	    System.getProperties().put("socksProxyHost", server);		
		System.getProperties().put("socksProxyPort", Integer.toString(port));
	}
	
	public void clearProxyServer() {
	    System.getProperties().put("socksProxyHost", "");		
		System.getProperties().put("socksProxyPort", "");
	}	
	
	public boolean connect(String user, String password) {

		this.user = user;

		try {
			ftpClient.connect( this.server, this.port );

			if (FTPReply.isPositiveCompletion( ftpClient.getReplyCode())) {  
				if (!ftpClient.login(this.user, password)) { 
					this.message = ftpClient.getReplyString();
					return false;
				}
				else return true;
			}
			this.message = ftpClient.getReplyString();
			return false;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		}

	}

	public boolean disconnect()  {
		try {		
			if (this.ftpClient != null) {
				ftpClient.disconnect();
				ftpClient = null; 
			}				
			return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		}
	}

	public String getUser() {
		return user;
	}

	public String getLocalDir() {
		return this.localDir;
	}

	public void setLocalDir(String diretorioLocal) {
		this.localDir = diretorioLocal;
	}

	public String getRemoteDir() {
		return remoteDir;
	}

	public boolean setRemoteDir(String remoteDir) {
		try {
			this.remoteDir = remoteDir;			
			if (!this.ftpClient.changeWorkingDirectory(this.remoteDir))	return false; 
			else return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		} 
	}

	public void addFileList(String filename) {
		this.fileList.add(filename);
	}

	public void clearFileList() {
		this.fileList.clear();
	}

	public ArrayList<String> getErrorFileList() {
		return errorFileList;
	}	

	public boolean upload() {

		try {

			boolean error = false;

			errorFileList.clear();

			if ((this.getLocalDir() == null) || (this.getLocalDir().length() == 0)) {
				this.localDir = ".";
			}

			for (String pattern : this.fileList) {

				ArrayList<String> engine = new FileSystem().listDirectory(this.getLocalDir(), pattern);

				for (String filename : engine) {

					InputStream is;
					is = new FileInputStream(this.getLocalDir() + File.separator + filename);

					try {
						
						if( filename.endsWith(".txt") )
							this.ftpClient.setFileType( FTPClient.ASCII_FILE_TYPE );
						else
							this.ftpClient.setFileType( FTPClient.BINARY_FILE_TYPE );

						if (!this.ftpClient.storeFile(filename, is)) { 
							error = true;
							this.errorFileList.add(filename);
						}
					}
					finally {
						is.close();
					}
				}
			}

			return (!error);
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		}
	}

	public boolean download() {

		try {

			boolean error = false;		

			errorFileList.clear();			

			if ((this.getLocalDir() == null) || (this.getLocalDir().length() == 0)) {
				this.localDir = ".";
			}

			for (String pattern : this.fileList) {

				this.ftpClient.changeWorkingDirectory(this.getRemoteDir());
				
				FTPListParseEngine engine = ftpClient.initiateListParsing(pattern);				

				while (engine.hasNext()) {
					FTPFile[] remoteFiles = engine.getNext(25);  

					for (Object item : remoteFiles) {
						FTPFile itemDownload = (FTPFile) item;

						if (itemDownload.getType() == FTPFile.FILE_TYPE) {

							FileOutputStream fos = new FileOutputStream(this.getLocalDir() + File.separator + itemDownload.getName());

							if( itemDownload.getName().toLowerCase().endsWith(".txt") || itemDownload.getName().toLowerCase().endsWith(".xml")) 
								this.ftpClient.setFileType( FTPClient.ASCII_FILE_TYPE );
							else
								this.ftpClient.setFileType( FTPClient.BINARY_FILE_TYPE );

							if (! this.ftpClient.retrieveFile(itemDownload.getName(), fos)) {
								error = true;								
								errorFileList.add(itemDownload.getName());
							}
							fos.close();
						}

					}
				}
			}
			return (!error);

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		}
	}
	
	public ArrayList<String[]> list() {
		
		ArrayList<String[]> listItems = new ArrayList<String[]>();
		
		try {

			if ((this.getLocalDir() == null) || (this.getLocalDir().length() == 0)) {
				this.localDir = ".";
			}

			FTPListParseEngine engine = ftpClient.initiateListParsing(this.getRemoteDir());			

			while (engine.hasNext()) {
				FTPFile[] remoteFiles = engine.getNext(25);  
				for (Object item : remoteFiles) {
					FTPFile itemDownload = (FTPFile) item;
					String[] items = new String[5];
					items[0] = itemDownload.getName();
					items[1] = Long.toString(itemDownload.getSize());
					items[2] = itemDownload.getTimestamp().getTime().toString();
					items[3] = itemDownload.getTimestamp().getTime().toString();
					items[4] = Boolean.toString(itemDownload.getType() == FTPFile.DIRECTORY_TYPE);
					listItems.add(items);
				}
			}
			
			return listItems;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return listItems;
		}
	}		
	

    public boolean rename(String oldFilename, String newFilename) {

		try {
			if (!this.ftpClient.rename(oldFilename, newFilename)) return false;
			else return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		} 
	}
	
	public int acct(String account) {

		try {
			return (this.ftpClient.acct(account));
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
		return -1;
		}
	}
	
	public void setRemoteVerification(boolean enable) {
			this.ftpClient.setRemoteVerificationEnabled(enable);
	}	
	
	public int sendCommand(String command) {

		try {
			return (this.ftpClient.sendCommand(command));
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return -1;
		}
	}		
	
	public int sendCommand(String command, String args) {

		try {
			return (this.ftpClient.sendCommand(command, args));
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return -1;
		}
	}	

	public boolean noop() {

		try {
			return (this.ftpClient.sendNoOp());
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		}
	}		

	public boolean delete(String filename) {

		try {
			if (!this.ftpClient.deleteFile(filename)) return false;
			else return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		}
	}

	public boolean isConnected() {
		return this.ftpClient.isConnected();
	}
	
	public void setPassiveMode() {
			this.ftpClient.enterLocalPassiveMode();
	}	
	
	public void setActiveMode() {
		this.ftpClient.enterLocalActiveMode();
}	

	public FTPClient getFTPClient() {
		return ftpClient;
	}
	
	public int getReplyCode() {
		return this.ftpClient.getReplyCode();
	}
	
	public String getReplyString() {
		return this.ftpClient.getReplyString();
	}	
	
	public String[] getReplyStrings() {
		return this.ftpClient.getReplyStrings();
	}	


}
