package sofia.toolbox.rss;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class RSS {

	final String title;
	final String link;
	final String description;
	final String language;
	final String copyright;
	final String pubDate;

	final List<RSSMessage> entries = new ArrayList<RSSMessage>();

	public RSS(String title, String link, String description, String language,
			String copyright, String pubDate) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
		this.pubDate = pubDate;
	}

	public List<RSSMessage> getMessages() {
		return entries;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public String getLanguage() {
		return language;
	}

	public String getCopyright() {
		return copyright;
	}

	public String getPubDate() {
		return pubDate;
	}
	
	public String convertPubDate(String pubDate) throws ParseException {
		 
		DateFormat df6 = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.US);

		Date d6 = df6.parse(pubDate);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d6);
        return (String.format("%02d/%02d/%04d %02d:%02d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
	}		
	
	

	@Override
	public String toString() {
		return "Feed [copyright=" + copyright + ", description=" + description
				+ ", language=" + language + ", link=" + link + ", pubDate="
				+ pubDate + ", title=" + title + "]";
	}

} 