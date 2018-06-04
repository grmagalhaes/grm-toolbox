package sofia.toolbox.net;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * This class contains methods to work with the smtp protocol.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class SMTP {

	private Transport transport;
	private Session session = null;
	private String server, user, password;
	private int port;

	private String message;

	public SMTP(String server) {
		this.server = server;
		this.port = 25;
	}

	public SMTP(String server, int port) {
		this.server = server;
		this.port = port;
	}

	public String getMessage() {
		return this.message;
	}	

	public boolean isConnected() {
		if (this.user == null) return true;

		return this.transport.isConnected();
	}		


	public boolean connect()  {

		Properties props = new Properties();		

		props.put("mail.smtp.host", this.server);
		props.put("mail.smtp.port", this.port);		
		props.put("mail.smtp.auth", "false");

		this.session = Session.getInstance(props);

		try {
			this.transport = this.session.getTransport("smtp");
			return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		}
	}

	public boolean connect(String user, String password) {

		this.user = user;
		this.password = password;

		Properties props = new Properties();

		props.put("mail.smtp.host", this.server);
		props.put("mail.smtp.port", this.port);
		props.put("mail.smtp.auth", "true");		

		this.session = Session.getInstance(props, new SMTPAuthenticator(this.user, this.password));

		try {
			this.transport = session.getTransport("smtp");
			this.transport.connect(this.server, this.port, this.user, this.password);
			return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		} 
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {

		private String user;
		private String password;

		public SMTPAuthenticator (String user, String password) {
			this.user = user;
			this.password = password;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication (this.user, this.password);
		}	
	}

	public boolean disconnect() {
		try {

			if (this.transport.isConnected()) {
				this.transport.close();
			}
			return true;

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		}
	}

	public final boolean send(
			final String from,			
			final String[] tos,
			final String[] ccs,
			final String[] bccs,
			final String subject,
			final String body,
			final String[] filenames,
			final String[] images) {

		try {

			MimeMessage msg = new MimeMessage( session );

			InternetAddress addressFrom;
			addressFrom = new InternetAddress(from);

			msg.setFrom(addressFrom);

			if (tos != null) {
				if ((tos.length > 0) && (tos[0] != null)) { 
					InternetAddress[] addressTo = new InternetAddress[tos.length];
					for (int i = 0; i < tos.length; i++) {
						if (!tos[i].equals(""))
							addressTo[i] = new InternetAddress(tos[i]);
					}

					if (addressTo[0] != null)				
						msg.setRecipients(Message.RecipientType.TO, addressTo);
				}
			}

			if (ccs != null) {
				if ((ccs.length > 0) && (ccs[0] != null)) {
					InternetAddress[] addressCc = new InternetAddress[ccs.length];
					for (int i = 0; i < ccs.length; i++) {
						if (!ccs[i].equals(""))
							addressCc[i] = new InternetAddress(ccs[i]);
					}

					if (addressCc[0] != null)				
						msg.setRecipients(Message.RecipientType.CC, addressCc);
				}
			}

			if (bccs != null) {
				if ((bccs.length > 0) && (bccs[0] != null)) {
					InternetAddress[] addressBcc = new InternetAddress[bccs.length];
					for (int i = 0; i < bccs.length; i++) {
						if (!bccs[i].equals(""))
							addressBcc[i] = new InternetAddress(bccs[i]);
					}

					if (addressBcc[0] != null)
						msg.setRecipients(Message.RecipientType.BCC, addressBcc);
				}
			}

			msg.setSubject(subject);
			msg.setSentDate(new Date());

			Multipart multipart = new MimeMultipart();

			BodyPart mimeBodyPart = new MimeBodyPart();

			mimeBodyPart.setHeader("Content-Type", "text/html; charset=utf-8");
			mimeBodyPart.setContent(body, "text/html; charset=utf-8");

			multipart.addBodyPart(mimeBodyPart);

			if (filenames != null) {
				if (filenames.length > 0) {
					BodyPart mimeBodyPartAttachment;
					DataSource source;
					String attachmentName;

					for (String filename:filenames) {

						if ((filename != null) && (filename.length() > 0)) {

							mimeBodyPartAttachment = new MimeBodyPart();

							source = new FileDataSource(filename);
							mimeBodyPartAttachment.setDataHandler(new DataHandler(source));

							attachmentName = new File(filename).getName();
							mimeBodyPartAttachment.setFileName(attachmentName);

							multipart.addBodyPart(mimeBodyPartAttachment);
						}
					}

				}
			}

			if (images != null) {
				if (images.length > 0) {

					for (String image:images) {

						if ((image != null) && (image.length() > 0)) {

							File file = new File(image); 
							BufferedImage bi = ImageIO.read(file);  

							ByteArrayOutputStream bos = new ByteArrayOutputStream();  

							String imageType = "gif";
							if (image.toLowerCase().endsWith("gif")) imageType = "gif";
							else if (image.toLowerCase().endsWith("jpg")) imageType = "jpeg";
							else if (image.toLowerCase().endsWith("jpeg")) imageType = "jpeg";
							else if (image.toLowerCase().endsWith("png")) imageType = "png";

							ImageIO.write(bi, imageType , bos);  

							MimeBodyPart imagePart = new MimeBodyPart();     
							DataSource ds = new ByteArrayDataSource(bos.toByteArray(), "image/" + imageType);     
							imagePart.setDataHandler(new DataHandler(ds));     
							imagePart.setFileName(file.getName());   

							imagePart.setHeader("Content-ID", "<" + file.getName() + ">");     
							imagePart.setDisposition("inline");							

							multipart.addBodyPart(imagePart);
						}
					}

				}				

			}

			msg.setContent(multipart);
			msg.saveChanges();

			this.transport.sendMessage(msg, msg.getAllRecipients());

			return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	

			return false;
		} 			
	}

	public final boolean send(
			final String from,			
			final String to,
			final String cc,
			final String bcc,
			final String subject,
			final String body,
			final String filename,
			final String[] images) {

		String[] tos = {to};
		String[] ccs = {cc};
		String[] bccs = {bcc};
		String[] filenames = {filename};	

		return this.send(from, tos, ccs, bccs, subject, body, filenames, images);

	}	

	public final boolean send(
			final String from,			
			final String to,
			final String cc,
			final String bcc,
			final String subject,
			final String body,
			final String[] filenames) {

		String[] tos = {to};
		String[] ccs = {cc};
		String[] bccs = {bcc};

		return this.send(from, tos, ccs, bccs, subject, body, filenames, null);

	}

	public final boolean send(
			final String from,			
			final String to,
			final String cc,
			final String bcc,
			final String subject,
			final String body,
			final String filename) {

		String[] tos = {to};
		String[] ccs = {cc};
		String[] bccs = {bcc};
		String[] filenames = {filename};		

		return this.send(from, tos, ccs, bccs, subject, body, filenames, null);

	}


	public final boolean send(
			final String from,			
			final String to,
			final String subject,
			final String body,
			final String[] filenames) {

		String[] tos = {to};

		return this.send(from, tos, null, null, subject, body, filenames, null);

	}

	public final boolean send(
			final String from,			
			final String to,
			final String subject,
			final String body,
			final String filename) {

		String[] tos = {to};
		String[] filenames = {filename};

		return this.send(from, tos, null, null, subject, body, filenames, null);

	}

	public final boolean send(
			final String from,			
			final String to,
			final String subject,
			final String body) {

		String[] tos = {to};

		return this.send(from, tos, null, null, subject, body, null, null);

	}

}
