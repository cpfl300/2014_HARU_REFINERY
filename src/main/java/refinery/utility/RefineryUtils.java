package refinery.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RefineryUtils {
	
	static final String FORMAT_IN_MYSQL = "yyyy/MM/dd HH:mm:ss";
	static final String FORMAT_IN_ARTICLE = "yyyy-MM-dd HH:mm:ss";
	static final int OFFSET_HOURS_IN_SERVICE = -12;
	static final String KOREA_ZONE_ID = "Asia/Seoul";
	
	private static final TimeZone KOREA_ZONE = TimeZone.getTimeZone(KOREA_ZONE_ID);
	private static SimpleDateFormat DATE_FORMAT_IN_ARTILCE = new SimpleDateFormat(FORMAT_IN_ARTICLE);
	

	public static String[] getServiceDatesByTime(int year, int month, int day, int hour) {
		String[] dates = new String[2];
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.set(year, month , day, hour, 0, 0);
		
		dates[1] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		calendar.add(Calendar.HOUR, OFFSET_HOURS_IN_SERVICE);
		dates[0] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		return dates;
	}


	public static String getDate(int year, int month, int day, int hour) {
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.set(year, month , day, hour, 0, 0);
		
		return DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
	}
	
	public static String getToday() {
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		Date today = new Date(calendar.getTimeInMillis());
		
		return DATE_FORMAT_IN_ARTILCE.format(today);
	}
	
}
