package cn.scau.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 常用时间工具类
 * @author Unicorn
 * 2017-10-03
 */
public final class CommonTimeUtil {

	//中国标准时间格式
	public static final String ENG_DATE_FORMAT = "yyyy年MM月dd日 EEE HH:mm:ss z";
	//常用时间格式
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY = "yyyy";
	public static final String MM = "MM";
	public static final String DD = "dd";
	
	private static SimpleDateFormat sdf = null;
	
	/**
	 * 日期的相互转换
	 * @param date
	 * @param formatReg 转换规则
	 * @return
	 */
	public static Date dateToDate(Date date, String formatReg) {
		sdf = new SimpleDateFormat(formatReg);
		String str = sdf.format(date);
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	/**
	 * 日期转为字符串
	 * @param date
	 * @param formatReg 转换格式
	 * @return
	 */
	public static String dateToString(Date date, String formatReg) {
		sdf = new SimpleDateFormat(formatReg);
		return sdf.format(date);
	}
	
	/**
	 * 时间戳转换为字符串
	 * @param stamp
	 * @param formReg 转换格式
	 * @return
	 */
	public static String timestampToString(Timestamp stamp, String formReg) {
		sdf = new SimpleDateFormat(formReg);
		return sdf.format(stamp);
	}
	
	/**
	 * 字符串转换为日期
	 * @param str
	 * @param formatReg 转换格式
	 * @return
	 */
	public static Date stringToDate(String str, String formatReg) {
		sdf = new SimpleDateFormat(formatReg);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 日期转换为时间戳
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		if(null == date) return null;
		return new Timestamp(date.getTime());
	}
	
	/**
	 * 从时间字符串中获取到年份
	 * @param str
	 * @return
	 */
	public static String getYearFromString(String str) {
		if(null == str || str.equals(""))
			return null;
		sdf = new SimpleDateFormat(YYYY);
		Date tmp = null;
		try {
			tmp = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf.format(tmp);
	}
	
	/**
	 * 从时间字符串中获取到月份
	 * @param str
	 * @return
	 */
	public static String getMonthFromString(String str) {
		if(null == str || str.equals(""))
			return null;
		sdf = new SimpleDateFormat(MM);
		Date tmp = null;
		try {
			tmp = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf.format(tmp);
	}
	
	/**
	 * 从时间字符串中获取到天数,时间差
	 * @param str
	 * @return
	 */
	public static String getDayFromString(String str) {
		if(null == str || str.equals(""))
			return null;
		sdf = new SimpleDateFormat(DD);
		Date tmp = null;
		try {
			tmp = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf.format(tmp);
	}
	
	/**
	 * 获取指定时间与当前时间差值的中文提醒
	 * @param date 指定的时间
	 * @return
	 */
	public static String getDifferenceNow(Date date) {
		long time = date.getTime();
		Calendar cal = Calendar.getInstance();
		long dif = cal.getTimeInMillis() - time;
		if(dif == -dif) {
			if(dif / 1000 < 60)
				return "前1分钟以内";
			else if(dif / 1000 / 60 < 60)
				return (dif / 1000 / 60) + "分钟前";
			else if(dif / 1000 / 60 / 60 < 24)
				return (dif / 1000 / 60 / 60) + "小时前";
			else 
				return (dif / 1000 / 60 / 60 / 24) + "天前";
		} else {
			dif = -dif;
			if(dif / 1000 < 60)
				return "1分钟以内";
			else if(dif / 1000 / 60 < 60)
				return (dif / 1000 / 60) + "分钟后";
			else if(dif / 1000 / 60 / 60 < 24)
				return (dif / 1000 / 60 / 60) + "小时后";
			else 
				return (dif / 1000 / 60 / 60 / 24) + "天后";
		}
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getCurrentTime() {
		return new Date();
	}
}
