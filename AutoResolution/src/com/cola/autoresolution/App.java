package com.cola.autoresolution;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;

import com.cola.autoresolution.util.FileUtil;
import com.cola.autoresolution.util.LogUtil;

public class App extends Application {
	private static final String TAG = "App";

	private static App instance;

	public int screenWidth;
	public int screenHeight;

	public String mVersion = "1.0.0";

	/**
	 * 是否退出程序
	 */
	private boolean mExitApp = false;

	public static App getInstance() {
		return instance;
	}

	/*
	 * 创建
	 */
	@SuppressWarnings("deprecation")
	public void onCreate() {
		LogUtil.w(TAG, "启动程序");
		instance = this;

		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		instance.screenWidth = display.getWidth();
		instance.screenHeight = display.getHeight();
		LogUtil.e(TAG, "手机分辨率为" + instance.screenWidth + ", "
				+ instance.screenHeight);

		// 使用手机IMEI
		// Client.imei = ((TelephonyManager)
		// getSystemService(TELEPHONY_SERVICE)).getDeviceId();

		getVersion();

		FileUtil.createDirectory(FileUtil.AUTORESOLUTION);

	};

	private void getVersion() {
		// 版本信息
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					getPackageName(), PackageManager.GET_CONFIGURATIONS);
			int lastIndex = packageInfo.versionName.lastIndexOf(".");
			if (lastIndex > 0) {
				mVersion = packageInfo.versionName.substring(0, lastIndex);
			}
		} catch (NameNotFoundException e) {
		}
	}

	/**
	 * 检查SD卡是否安装
	 * 
	 * @return
	 */
	public boolean checkSDcard() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	/**
	 * 设置是否退出了程序
	 * 
	 * @param exit
	 */
	public void setExitApp(boolean exit) {
		this.mExitApp = exit;
	}

	/**
	 * 是否已经退出程序
	 * 
	 * @return
	 */
	public boolean isExitApp() {
		return mExitApp;
	}
}
