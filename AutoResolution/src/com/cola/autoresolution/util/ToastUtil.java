package com.cola.autoresolution.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {
	public static void showShort(final Context context, final String text) {
		try {
			Handler handler = new Handler(Looper.getMainLooper());
			handler.postAtFrontOfQueue(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
				}
			});
		} catch (Exception ex) {
			if (LogUtil.DEBUG)
				ex.printStackTrace();
		}
	}

	public static void showShort(final Context context, final int resId) {
		try {
			Handler handler = new Handler(Looper.getMainLooper());
			handler.postAtFrontOfQueue(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
				}
			});
		} catch (Exception ex) {
			if (LogUtil.DEBUG)
				ex.printStackTrace();
		}
	}

	public static void showShort(final Context context, final CharSequence text) {
		try {
			Handler handler = new Handler(Looper.getMainLooper());
			handler.postAtFrontOfQueue(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
				}
			});
		} catch (Exception ex) {
			if (LogUtil.DEBUG)
				ex.printStackTrace();
		}
	}

	public static void showLong(final Context context, final String text) {
		try {
			Handler handler = new Handler(Looper.getMainLooper());
			handler.postAtFrontOfQueue(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
				}
			});
		} catch (Exception ex) {
			if (LogUtil.DEBUG)
				ex.printStackTrace();
		}
	}

	public static void showLong(final Context context, final int resId) {
		try {
			Handler handler = new Handler(Looper.getMainLooper());
			handler.postAtFrontOfQueue(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
				}
			});
		} catch (Exception ex) {
			if (LogUtil.DEBUG)
				ex.printStackTrace();
		}
	}
}
