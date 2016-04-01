

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	  public static final String JAVA_DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
	  public static final String JAVA_DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	  public static final String JAVA_DATE_FORMAT_YMD = "yyyy-MM-dd";
	  public static final String JAVA_DATE_FORMAT_YM = "yyyy-MM";
	  public static final String JAVA_DATE_FORMAT_HM = "HH:mm";
	  public static final String JAVA_DATE_FORMAT_YMDW = "yyyy-MM-dd EEEE";
	  public static final String JAVA_DATE_FORMAT_W = "EEEE";
	  public static final String JAVA_DATE_FORMAT_CH_YMDHMS = "yyyy年MM月dd日 HH:mm:ss";
	  public static final String JAVA_DATE_FORMAT_CH_YMDHM = "yyyy年MM月dd日 HH:mm";
	  public static final String JAVA_DATE_FORMAT_CH_YMD = "yyyy年MM月dd日";
	  public static final String JAVA_DATE_FORMAT_CH_YM = "yyyy年MM月";
	  public static final String JAVA_DATE_FORMAT_CH_YMDW = "yyyy年MM月dd日EEEE";

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            Date对象
	 * @param mask
	 *            格式掩码
	 * @return 经过格式化的日期字符串
	 */
	public static String toString(Date date, String mask) {
		if (date == null) {
			return "";
		} else {
			DateFormat df = new SimpleDateFormat(mask);
			return df.format(date);
		}
	}

	/**
	 * 把日期字符串转换成Date
	 * 
	 * @param dateStr
	 *            (表示日期的字符串如2004-12-12、2004-12-12 22:02、2004/12/12)
	 * @return
	 */
	public static Date parseToDate(String dateStr) {
		DateFormat df = DateFormat.getDateTimeInstance();
		try {
			if (dateStr.indexOf(":") == -1) {
				dateStr = dateStr + " 00:00:00";
			} else if ((dateStr.indexOf(":") != -1 && dateStr.endsWith(":") == false)
					&& dateStr.substring(dateStr.indexOf(":") + 1).indexOf(":") == -1) {
				dateStr = dateStr + ":00";
			}

			return df.parse(dateStr);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 当前时间减去相应分钟后,得到的时间
	 * @param date 当前时间
	 * @param min 分钟
	 * @return
	 */
	public static String getTime( Date date,int min){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long d = calendar.getTimeInMillis();
		d = d-min*60*1000;
		calendar.setTimeInMillis(d);
		Date date1 = calendar.getTime();
		String o = DateUtils.toString(date1,"yyyyMMddHHmm");
		return o;
	}
	
	/**
	 * 得到相隔天数的日期
	 * @param date 当前日期
	 * @param day 天数
	 * @param mask 格式化掩码  yyyy-MM-dd
	 * @return
	 */
	public static String getDate(Date date,int day,String mask){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long d = calendar.getTimeInMillis();
		d = d-day*24*60*60*1000;
		calendar.setTimeInMillis(d);
		Date date1 = calendar.getTime();
		
		String o = "";
		if("yyyy-MM-dd".equals(mask))
			o = DateUtils.toString(date1,"yyyy-MM-dd");
		else
			o = DateUtils.toString(date1,"yyyyMMddHHmm");
		return o;
	}
	
	/**
	 * 得到与当前时间相差年数
	 * @param sj
	 * @return
	 */
	public static Float getYear(String sj) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		float day = 0f;
		try {
			java.util.Date mydate = myFormatter.parse(sj);
			day = (new Date().getTime() - mydate.getTime()) / (24*60*60*1000*365.0f);
		} catch (Exception e) {
			return 0f;
		}
		return day;
	}
	/**
	 * 得到与当前时间相差年数
	 * @param sj 时间
	 * @param dateType Y时间精确到年 M时间精确到月 d时间精确到 H时间精确到小时 m时间精确到分 s时间精确到秒
	 * @return
	 */
	public static Float getYear(Date sj,String dateType){
		float day = 0f;
		
		if(sj==null){
			day = 0f;
			return day;
		}
		try {
			Date date = new Date();
			Date d = null;
			Date s = null;
			if(dateType.equals("Y")){
				d = stringToDate(DateUtils.toString(date, "yyyy"),"yyyy");
				s = stringToDate(DateUtils.toString(sj, "yyyy"),"yyyy");
			}
			else if(dateType.equals("M")){
				//Date t= stringToDate("2012-09","yyyy-MM");
				d = stringToDate(DateUtils.toString(date, "yyyy-MM"),"yyyy-MM");
				//d = t;
				s = stringToDate(DateUtils.toString(sj, "yyyy-MM"),"yyyy-MM");
			}
			else if(dateType.equals("d")){
				d = stringToDate(DateUtils.toString(date, "yyyy-MM-dd"),"yyyy-MM-dd");
				s = stringToDate(DateUtils.toString(sj, "yyyy-MM-dd"),"yyyy-MM-dd");
			}
			else if(dateType.equals("H")){
				d = stringToDate(DateUtils.toString(date, "yyyy-MM-dd HH"),"yyyy-MM-dd HH");
				s = stringToDate(DateUtils.toString(sj, "yyyy-MM-dd HH"),"yyyy-MM-dd HH");
			}
			else if(dateType.equals("m")){
				d = stringToDate(DateUtils.toString(date, "yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");
				s = stringToDate(DateUtils.toString(sj, "yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");
			}
			else if(dateType.equals("s")){
				d = stringToDate(DateUtils.toString(date, "yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss");
				s = stringToDate(DateUtils.toString(sj, "yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss");
			}
			day = (d.getTime() - s.getTime()) / 31536000000f;//(24*60*60*1000*365.0f);
			//day=(int)(day*100)/100.0f;
			DecimalFormat df = new DecimalFormat("#.##");
			String tmpstr = df.format(day);
			day = Float.parseFloat(tmpstr);
		} catch (Exception e) {
			return 0f;
		}
		return day;
	}
	
	/**
	 * 获取与当前时间间隔月数
	 * @param sj 
	 * @return
	 */
	public static int getMonth(Date sj) {
		if(sj==null)
			return 0;
		
		Calendar cal = Calendar.getInstance(); 
		int curmonth = cal.get(Calendar.MONTH);
		int curyear = cal.get(Calendar.YEAR);
		
		cal.setTime(sj);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		
		int val = (curyear-year)*12+(curmonth-month);
		return val;
	}
	
	/**
	 * 获取与两个时间间隔月数
	 * @param maxsj
	 * @param minsj
	 * @return
	 */
	public static int getMonth(Date maxsj,Date minsj) {
		if(maxsj==null || minsj==null)
			return 0;
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(maxsj);
		int curmonth = cal.get(Calendar.MONTH);
		int curyear = cal.get(Calendar.YEAR);
		
		cal.setTime(minsj);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		
		int val = (curyear-year)*12+(curmonth-month);
		return val;
	}
	
	private static Date stringToDate(String sj,String dt) {
		SimpleDateFormat myFormatter = null;
		if(dt==null) 
			 myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		else 
			myFormatter = new SimpleDateFormat(dt);
		Date mydate = null;
		try {
			 mydate = myFormatter.parse(sj);
		} catch (Exception e) {
		}
		return mydate;
	}
	
	/**
	 * 获取当前日期的前、后多少天日期
	 * @param num 天数
	 * @return
	 */
	public static String getPreDate(int num){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, num);
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpledateformat.format(calendar.getTime());
		return strDate;
	}
	
	
	public static void main(String[] args){
		//System.out.println(DateUtils.toString(new Date(), "yyyyMMddHHmm"));
		//System.out.println(DateUtils.getDate(new Date(),2,"yyyy-MM-dd"));
		//System.out.println(DateUtils.getYear(parseToDate("2006-10-05"),"M"));
		System.out.println(DateUtils.toString(new Date(), "yyyy"));
		//System.out.println(getMonth(null,parseToDate("2012-02-05")));
	}
	
	
}