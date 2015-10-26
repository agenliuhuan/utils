package com.cola.autoresolution.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ActivityUtil {
	public static void startActivityUrlView(Activity activity, String url) {
		Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		activity.startActivity(it);
	}

	public static void startActivity(Activity activity, Class<?> cls) {
		ActivityUtil.startActivity(activity, cls, null);
	}

	public static void startActivity(Activity activity, Class<?> cls, int flags) {
		Intent intent = new Intent(activity, cls);
		intent.setFlags(flags);
		activity.startActivity(intent);
	}

	public static void startActivity(Activity activity, Class<?> cls, Bundle extras) {
		Intent intent = new Intent(activity, cls);
		if (null != extras && !extras.isEmpty()) {
			intent.putExtras(extras);
		}
		activity.startActivity(intent);
	}

	public static void startActivity(Activity activity, Class<?> cls, Bundle extras, int flags) {
		Intent intent = new Intent(activity, cls);
		intent.setFlags(flags);
		if (null != extras && !extras.isEmpty()) {
			intent.putExtras(extras);
		}
		activity.startActivity(intent);
	}

	public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode) {
		ActivityUtil.startActivityForResult(activity, cls, null, requestCode);
	}

	public static void startActivityForResult(Activity activity, Class<?> cls, Bundle extras, int requestCode) {
		Intent intent = new Intent(activity, cls);
		if (null != extras && !extras.isEmpty()) {
			intent.putExtras(extras);
		}
		activity.startActivityForResult(intent, requestCode);
	}
}
