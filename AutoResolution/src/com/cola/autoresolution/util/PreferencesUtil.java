package com.cola.autoresolution.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cola.autoresolution.App;

/**
 * 用户偏好设置工具
 * 
 * @author
 */
public class PreferencesUtil {
	/**
	 * 设置应用第一次运行标志
	 * 
	 * @param first
	 */
	public static void setAppFirstRun(boolean first) {
		SharedPreferences preferences = App.getInstance().getSharedPreferences(FileUtil.APP, Context.MODE_PRIVATE);
		preferences.edit().putBoolean("AppFirstRun"/* + App.getInstance().mVersion*/, first).commit();
	}

	/**
	 * 查询应用是否第一次运行
	 * 
	 * @return
	 */
	public static boolean isAppFirstRun() {
		SharedPreferences preferences = App.getInstance().getSharedPreferences(FileUtil.APP, Context.MODE_PRIVATE);
		return preferences.getBoolean("AppFirstRun"/* + App.getInstance().mVersion*/, true);
	}

	/** 记录是否更改过 */
	public static void setScoreChange(boolean change) {
		SharedPreferences preferences = App.getInstance().getSharedPreferences(FileUtil.APP, Context.MODE_PRIVATE);
		preferences.edit().putBoolean("ScoreChange" + App.getInstance().mVersion, change).commit();
	}
	
	public static boolean isScoreChange() {
		SharedPreferences preferences = App.getInstance().getSharedPreferences(FileUtil.APP, Context.MODE_PRIVATE);
		return preferences.getBoolean("ScoreChange" + App.getInstance().mVersion, false);
	}
	
	//
	public static void setShareToWeixinFriendGroup(boolean weixinFriend) {
		SharedPreferences preferences = App.getInstance().getSharedPreferences(FileUtil.APP, Context.MODE_PRIVATE);
		preferences.edit().putBoolean("ShareToWeixinFriendGroup", weixinFriend).commit();
	}
	
	//
	public static boolean isShareToWeixinFriendGroup() {
		SharedPreferences preferences = App.getInstance().getSharedPreferences(FileUtil.APP, Context.MODE_PRIVATE);
		return preferences.getBoolean("ShareToWeixinFriendGroup", false);
	}
}