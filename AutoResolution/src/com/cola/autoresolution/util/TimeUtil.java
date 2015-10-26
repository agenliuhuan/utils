package com.cola.autoresolution.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	/**
	 * 日期转字符串
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String getDateString(long time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(time));
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
	
	public static String getTimeStamp(){
		return String.valueOf(System.currentTimeMillis());
	}
}