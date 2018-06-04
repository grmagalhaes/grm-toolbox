package sofia.toolbox.rss;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class RSSParser {
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUBDATE = "pubDate";
	static final String GUID = "guid";
	static final String CATEGORY =  "category";
	static final String ENCLOSURE = "enclosure";

	final URL url;
	
	public RSSParser(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public RSSParser(String feedUrl, String proxyServer, String proxyPort, String user, String password) throws IOException {
		try {
			System.setProperty("http.proxyHost", proxyServer);
			System.setProperty("http.proxyPort", proxyPort);

			System.setProperty("http.proxyUser", user);
			System.setProperty("http.proxyPassword", password);
			
			this.url = new URL(feedUrl);
			URLConnection uc = this.url.openConnection();
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		    String encodedUserPwd =  encoder.encode((user + ":" + password).getBytes());
		    uc.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
			
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public RSS readFeed() {
		RSS feed = null;
		try {
			boolean isFeedHeader = true;
			// Set header values intial to the empty string
			String description = "";
			String title = "";
			String link = "";
			String language = "";
			String copyright = "";
			String author = "";
			String guid = "";
			String category = "";
			String enclosure = "";
			String pubDate = "";

			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = read();

			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName()
							.getLocalPart();
					switch (localPart) {
					case ITEM:
						if (isFeedHeader) {
							isFeedHeader = false;
							feed = new RSS(title, link, description, language,
									copyright, pubDate);
						}
						event = eventReader.nextEvent();
						break;
					case TITLE:
						title = getCharacterData(event, eventReader);
						break;
					case DESCRIPTION:
						description = getCharacterData(event, eventReader);
						break;
					case LINK:
						link = getCharacterData(event, eventReader);
						break;
					case GUID:
						guid = getCharacterData(event, eventReader);
						break;
					case LANGUAGE:
						language = getCharacterData(event, eventReader);
						break;
					case AUTHOR:
						author = getCharacterData(event, eventReader);
						break;
					case PUBDATE:
						pubDate = getCharacterData(event, eventReader);
						break;
					case COPYRIGHT:
						copyright = getCharacterData(event, eventReader);
						break;
					case CATEGORY:
						category = getCharacterData(event, eventReader);
						break;
					case ENCLOSURE:

					          StartElement element = (StartElement) event;

					          @SuppressWarnings("rawtypes")
							  Iterator iterator = element.getAttributes();
					          while (iterator.hasNext()) {
					            Attribute attribute = (Attribute) iterator.next();
					            QName name = attribute.getName();
					            String value = attribute.getValue();
					            if (name.getLocalPart().equals("url")) {
					            	enclosure = value;
					            	break;
					            }
					          }

						break;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						RSSMessage message = new RSSMessage();
						message.setAuthor(author);
						message.setDescription(description);
						message.setGuid(guid);
						message.setLink(link);
						message.setTitle(title);
						message.setCategory(category);
						message.setEnclosure(enclosure);
						message.setPubDate(pubDate);
						feed.getMessages().add(message);
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
		return feed;
	}

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
			throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}

	private InputStream read() {
		try {
	        BufferedReader rdr = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
	        String buf = null;
	        StringBuffer output = new StringBuffer();
	        while ((buf = rdr.readLine()) != null) {
//	        	System.out.println(buf);
	            output.append(buf.replaceAll("&lt;p&gt;", ""));
	        }
	        
            byte[] bytes = output.toString().getBytes("UTF-8");
            
            InputStream is = new ByteArrayInputStream(bytes);
			
			return is;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
} 

