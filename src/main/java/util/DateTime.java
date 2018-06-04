package sofia.toolbox.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * This class contains utility methods to manipulate Dates, Times, Timestamps (some methods use locale).
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class DateTime {

	private Date date;
	private Calendar cal = new GregorianCalendar();
	private Locale locale;
	private String message;

	public DateTime() {
		this(new Locale("EN", "US"));
	}

	public DateTime(Locale locale) {
		this(locale,  new Date(System.currentTimeMillis()));
	}

	public DateTime(Locale locale, Date date) {
		this.locale = locale;
		this.date = date;
		cal.setTime(this.date);
	}

	public DateTime(String date, String pattern) {
		this.locale = new Locale("EN", "US");

		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern(pattern);
		try {
			this.date = df.parse(date);
			cal.setTime(this.date);
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
		}	
	}	

	public String getMessage() {
		return this.message;
	}

	public static Date getCurrentTime() {
		return new Date(System.currentTimeMillis());
	}	
	
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}	

	public void setDate(Date date) {
		this.date = date;
	}	

	public Date getDate() {
		return this.date;
	}

	public Date getTime() {
		return cal.getTime();
	}

	public long getTimeInMillis() {
		return cal.getTimeInMillis();
	}

	public long getTimeLong() {
		return cal.getTime().getTime();
	}

	public Time getSQLTime() {
		return new Time(this.date.getTime());
	}

	public Timestamp getSQLTimestamp() {
		return new Timestamp(this.date.getTime());
	}

	public java.sql.Date getSQLDate() {
		return new java.sql.Date(this.date.getTime());
	}

	public String getDateString() {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, this.locale);
		return df.format(this.date);
	}

	public String getYear() {
		SimpleDateFormat year = new SimpleDateFormat();
		year.applyPattern("yyyy");
		return year.format(this.date);
	}

	public String getMonth() {
		SimpleDateFormat month = new SimpleDateFormat();
		month.applyPattern("MM");
		return month.format(this.date);		

	}

	public String getDay() {
		SimpleDateFormat day = new SimpleDateFormat();
		day.applyPattern("dd");
		return day.format(this.date);		
	}

	public int getDayOfWeek() {
		return cal.get(Calendar.DAY_OF_WEEK); // 1=Sunday, 2=Monday, ...
	}

	public String getHour() {
		SimpleDateFormat hour = new SimpleDateFormat();
		hour.applyPattern("HH");
		return hour.format(this.date);	
	}

	public String getMinute() {
		SimpleDateFormat minute = new SimpleDateFormat();
		minute.applyPattern("mm");
		return minute.format(this.date);	
	}

	public String getSecond() {
		SimpleDateFormat second = new SimpleDateFormat();
		second.applyPattern("mm");
		return second.format(this.date);
	}

	public String getDate(String pattern) {
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern(pattern);
		return df.format(this.date);
	}

	public String toString() {
		return this.getShortDate();
	}

	public String getShortDate() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, this.locale);
		return df.format(this.date);
	}

	public String getLongDate() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, this.locale);
		return df.format(this.date);		
	}

	public void add(int daysToAdd) {
		cal.add(GregorianCalendar.DATE, daysToAdd);
	}

	public void dec(int daysToDecrement) {
		cal.add(GregorianCalendar.DATE, (daysToDecrement * -1));
	}

	public double Diff(DateTime d1) {
		return (Math
				.floor((this.getTime().getTime() - d1.getTime().getTime()) / 1000.0 / 86400.00) * -1) + 1;
	}

	public long elapsedTime(long d2) {
		return (d2 - getTimeLong());
	}

	public String[] getShortWeekdays() {

		DateFormatSymbols dateSymbols = new DateFormatSymbols(this.locale);
		String [] weekdays = dateSymbols.getShortWeekdays();

		return weekdays;		
	}	

	public String getShortWeekdayName(int day)  {

		if (day > 6) return null;

		return this.getShortWeekdays()[day];

	}

	public String[] getWeekdays() 
	{
		DateFormatSymbols dateSymbols = new DateFormatSymbols(this.locale);
		String [] weekdays = dateSymbols.getWeekdays();

		return weekdays;		
	}	

	public String getWeekdayName(int day) 
	{
		if (day > 6) return null;

		return this.getWeekdays()[day];		
	}

	public String[] getShortMonths() 
	{
		DateFormatSymbols dateSymbols = new DateFormatSymbols(this.locale);
		String[] months = dateSymbols.getShortMonths();

		return months;		
	}		

	public String getShortMonthName(int month) 
	{
		if (month > 11) return null;

		return this.getShortMonths()[month];
	}

	public String[] getMonths() 
	{
		DateFormatSymbols dateSymbols = new DateFormatSymbols(this.locale);
		String[] months = dateSymbols.getMonths();

		return months;		
	}		

	public String getMonthName(int month) 
	{
		if (month > 11) return null;

		return this.getMonths()[month];
	}	

	public static String getTimeZone() {

		Calendar now = Calendar.getInstance();
		TimeZone timeZone = now.getTimeZone();

		double totalHours = timeZone.getOffset(System.currentTimeMillis())/1000/60/60;
		int hours = (int)totalHours;
		int minutes = (int)((totalHours - hours)*60);

		return String.format("%03d", hours) + ":" + String.format("%02d", minutes);
	}

}
