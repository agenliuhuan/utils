package com.cola.autoresolution.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	/**
	 * 时间转字符串
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String getDateString(long time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(time));
	}

	/**
	 * 获取当前时间的字符串
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrDateString(String pattern) {
		return getDateString(DateUtil.currentTimeMillis(), pattern);
	}

	private static long mFirstSysTimemillis = 0;
	private static long mFirstNtpTimemillis = 0;

	/**
	 * 获取当前时间<br/>
	 * 使用ntp作为标准时间
	 * 
	 * @return
	 */
	public static long currentTimeMillis() {
		long currentTimemillis = currentTimeMillisBySys();
		if (0 == mFirstNtpTimemillis) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					mFirstNtpTimemillis = currentTimeMillisByNtp();
					mFirstSysTimemillis = currentTimeMillisBySys();
				}
			}).start();
		} else {
			return mFirstNtpTimemillis + currentTimemillis - mFirstSysTimemillis;
		}
		return currentTimemillis;
	}

	/**
	 * 获取当前手机系统时间
	 * 
	 * @return
	 */
	private static long currentTimeMillisBySys() {
		return System.currentTimeMillis() - TimeZone.getDefault().getRawOffset() + TimeZone.getTimeZone("Asia/Shanghai").getRawOffset();
	}

	/**
	 * 获取NTP系统时间
	 * 
	 * @return
	 */
	private static long currentTimeMillisByNtp() {
		SntpClient client = new SntpClient();
		if (client.requestTime("ntp.fudan.edu.cn", 30000)) {
			return (client.getNtpTime() + System.nanoTime() / 1000 - client.getNtpTimeReference()) - TimeZone.getDefault().getRawOffset() + TimeZone.getTimeZone("Asia/Shanghai").getRawOffset();
		}
		return 0;
	}

	/**
	 * 字符串转日期
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static Date getDate(String time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
		}
		return null;
	}

	/*
	 * 获取时间标记字符串
	 */
	public static String getTimeMillisString(int nTimeMillis) {
		int nHours = 0, nMinutes = 0, nSeconds = 0;
		String strTime = "";

		try {
			nTimeMillis = nTimeMillis / 1000;

			nSeconds = nTimeMillis % 60;

			nTimeMillis -= nSeconds;

			if (nTimeMillis > 0) {
				nMinutes = nTimeMillis / 60;

				if (nMinutes > 0) {
					nHours = nMinutes / 60;
					nMinutes = nMinutes % 60;
				}
			}

			if (nHours > 0) {
				strTime = String.format("%02d:%02d:%02d", nHours, nMinutes, nSeconds);
			} else {
				strTime = String.format("%02d:%02d", nMinutes, nSeconds);
			}
		} catch (Exception e) {
		}

		return strTime;
	}

	public static String getTimeMillisStringHMS(int nTimeMillis) {
		int nHours = 0, nMinutes = 0, nSeconds = 0;
		String strTime = "";

		try {
			nTimeMillis = nTimeMillis / 1000;

			nSeconds = nTimeMillis % 60;

			nTimeMillis -= nSeconds;

			if (nTimeMillis > 0) {
				nMinutes = nTimeMillis / 60;

				if (nMinutes > 0) {
					nHours = nMinutes / 60;
					nMinutes = nMinutes % 60;
				}
			}

			strTime = String.format("%02d:%02d:%02d", nHours, nMinutes, nSeconds);
		} catch (Exception e) {
		}

		return strTime;
	}
}