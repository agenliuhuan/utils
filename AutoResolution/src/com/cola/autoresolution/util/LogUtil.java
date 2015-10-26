package com.cola.autoresolution.util;

import java.util.Hashtable;
import java.util.Map;

import android.util.Log;

public class LogUtil {
	// 调试环境打印日志
	public final static String TAG = "com.cola.autoresolution";
	public static final boolean DEBUG = false;
	public static final boolean TEST = false;
	public static final boolean AD = false;
	public static final boolean AD_TENCENT_GDT = false;
	static {
//		UMengStatistUtil.setDebugMode(DEBUG);
	}
	public static final String LOG_FILE = FileUtil.AUTORESOLUTION + "log.txt";

	public static void e(String tag, Object msg) {
		if (DEBUG) {
			android.util.Log.e(tag, String.valueOf(msg));
			FileUtil.appendTextFile(
					LOG_FILE,
					DateUtil.getDateString(DateUtil.currentTimeMillis(),
							"yyyy-MM-dd HH:mm:ss.SSS")
							+ "\t E \t"
							+ tag
							+ "\t"
							+ String.valueOf(msg));
		}
	}

	public static void i(String tag, Object msg) {
		if (DEBUG) {
			android.util.Log.i(tag, String.valueOf(msg));
			FileUtil.appendTextFile(
					LOG_FILE,
					DateUtil.getDateString(DateUtil.currentTimeMillis(),
							"yyyy-MM-dd HH:mm:ss.SSS")
							+ "\t I \t"
							+ tag
							+ "\t"
							+ String.valueOf(msg));
		}
	}

	public static void d(String tag, Object msg) {
		if (DEBUG) {
			android.util.Log.d(tag, String.valueOf(msg));
			FileUtil.appendTextFile(
					LOG_FILE,
					DateUtil.getDateString(DateUtil.currentTimeMillis(),
							"yyyy-MM-dd HH:mm:ss.SSS")
							+ "\t D \t"
							+ tag
							+ "\t"
							+ String.valueOf(msg));
		}
	}

	public static void v(String tag, Object msg) {
		if (DEBUG) {
			android.util.Log.v(tag, String.valueOf(msg));
			FileUtil.appendTextFile(
					LOG_FILE,
					DateUtil.getDateString(DateUtil.currentTimeMillis(),
							"yyyy-MM-dd HH:mm:ss.SSS")
							+ "\t V \t"
							+ tag
							+ "\t"
							+ String.valueOf(msg));
		}
	}

	public static void w(String tag, Object msg) {
		if (DEBUG) {
			android.util.Log.w(tag, String.valueOf(msg));
			FileUtil.appendTextFile(
					LOG_FILE,
					DateUtil.getDateString(DateUtil.currentTimeMillis(),
							"yyyy-MM-dd HH:mm:ss.SSS")
							+ "\t W \t"
							+ tag
							+ "\t"
							+ String.valueOf(msg));
		}
	}

	private static Map<String, Long> startTimes = new Hashtable<String, Long>();

	public static void startTime(String label) {
		if (DEBUG) {
			startTimes.put(label, DateUtil.currentTimeMillis());
		}
	}

	public static void endTime(String tag, String label) {
		if (DEBUG) {
			long startTime = startTimes.get(label);
			long endTime = DateUtil.currentTimeMillis();
			Log.e(tag, label + "\t所花时间 " + (endTime - startTime) + "ms");
		}
	}
}
