package com.cola.autoresolution.util;

import java.text.DecimalFormat;

public class NumberUtil {
	/**
	 * 是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (null != str) {
			for (int i = 0; i < str.length(); i++) {
				if (!Character.isDigit(str.charAt(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 补零
	 * 
	 * @param number
	 *            数字
	 * @param bits
	 *            几位数
	 * @return
	 */
	public static String fill0(int number, int bits) {
		String temp = String.valueOf(number);
		int len = temp.length();
		if (len < bits) {
			len = bits - len;
			for (int i = 0; i < len; i++) {
				temp = "0" + temp;
			}
		}
		return temp;
	}

	/**
	 * 文件大小字符串表示
	 * 
	 * @param size
	 * @return
	 */
	public static String SizeToString(long size) {
		if (size < 1000)
			return String.valueOf(size);
		double value = (double) size / 1024;
		if (value < 10)
			return new DecimalFormat("0.00").format(value) + "KB";
		if (value < 100)
			return new DecimalFormat("0.0").format(value) + "KB";
		if (value < 1000)
			return new DecimalFormat("0").format(value) + "KB";
		value = value / 1024;
		if (value < 10)
			return new DecimalFormat("0.00").format(value) + "MB";
		if (value < 100)
			return new DecimalFormat("0.0").format(value) + "MB";
		if (value < 1000)
			return new DecimalFormat("0").format(value) + "MB";
		return null;
	}

	/**
	 * 时间字符串表示
	 * 
	 * @param size
	 * @return
	 */
	public static String DurationToString(int duration) {
		StringBuilder sb = new StringBuilder();
		// int milliseconds = duration % 1000;
		duration = duration / 1000;
		if (duration >= 24 * 60 * 60) {
			duration = duration % (24 * 60 * 60);
		}
		if (duration >= 60 * 60) {
			int hours = duration / (60 * 60);
			if (hours < 10)
				sb.append(0);
			sb.append(hours);
			sb.append(":");
			duration = duration % (60 * 60);
		}
		int minutes = duration / 60;
		if (minutes < 10)
			sb.append(0);
		sb.append(minutes);
		sb.append(":");
		int seconds = duration % 60;
		if (seconds < 10)
			sb.append(0);
		sb.append(seconds);
		// sb.append(".");
		// sb.append(milliseconds);
		return sb.toString();
	}

	/**
	 * 字符串时间转整形时间<br/>
	 * 02:00.123-->120123
	 * 
	 * @param str
	 * @return
	 */
	public static int StringToDuration(String str) {
		if (!str.matches("\\d{1,2}:\\d{1,2}\\.\\d{1,3}"))
			return -1;
		int duration = 0;
		String minutes = str.substring(0, str.indexOf(":"));
		String seconds = str.substring(str.indexOf(":") + 1, str.indexOf("."));
		String millisecond = str.substring(str.indexOf(".") + 1);

		duration += Integer.parseInt(millisecond);
		duration += Integer.parseInt(seconds) * 1000;
		duration += Integer.parseInt(minutes) * 60 * 1000;
		return duration;
	}
}
