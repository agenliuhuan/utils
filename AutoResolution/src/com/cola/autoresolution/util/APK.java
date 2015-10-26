package com.cola.autoresolution.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/*
 * 安装包类
 */
public class APK {
	public final static String PACKAGE_ARCHIVE = "application/vnd.android.package-archive";

	/*
	 * 安装APK
	 */
	public static void installAPK(Context context, String strFile) {
		Intent intent = null;

		try {
			intent = new Intent(Intent.ACTION_VIEW);

			intent.setDataAndType(Uri.fromFile(new File(strFile)),
					PACKAGE_ARCHIVE);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			context.startActivity(intent);
		} catch (Exception e) {
		}
	}

	/*
	 * 安装APK
	 */
	public static void installAPK(Context context, Uri uri) {
		Intent intent = null;

		try {
			intent = new Intent(Intent.ACTION_VIEW);

			intent.setDataAndType(uri, PACKAGE_ARCHIVE);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			context.startActivity(intent);
		} catch (Exception e) {
		}
	}
}
