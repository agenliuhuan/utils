package com.cola.autoresolution.util;

import java.io.UnsupportedEncodingException;

public class StringUtil {
	public static final String STORY_TITLE_LIMIT = "|";

	/**
	 * 分割字符串<br/>
	 * 分隔符已添加"\\"
	 * 
	 * @param src
	 * @param limit
	 *            分隔符
	 * @return
	 */
	public static String[] split(String src, String limit) {
		return src.split("\\" + limit);
	}

	/**
	 * 是否是空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str || str.trim().length() == 0)
			return true;
		return false;
	}

	/*
	 * 新字符串
	 */
	public static String newString(String strContent) {
		return new String((null == strContent) ? "" : strContent);
	}

	public static String getFontColor(String content, String color) {
		return "<font color=\"" + color + "\">" + content + "</font>";
	}

	/**
	 * 返回文本内容长度<br/>
	 * 一个中文为一个字，两个英文为一个字
	 * 
	 * @param content
	 * @return
	 */
	public static int getLengthByGBK(String content) {
		if (!StringUtil.isEmpty(content)) {
			try {
				return Math.round((float) content.getBytes("GBK").length / 2);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return 0;
	}

	/**
	 * 将所有的数字、字母及标点全部转为全角字符, 解决文本不对齐问题
	 * 
	 * @param input
	 * @return
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
}
