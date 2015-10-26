package com.cola.autoresolution.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.Inflater;

public class LrcUtil {
	private static final char[] NRCMAGIC = { 0xCE, 0x55, 0x6A, 0xB7, 0x6E, 0x8F, 0x66, 0xD3, 0x39, 0x63, 0x2D, 0x3F, 0x34, 0x40, 0x46, 0x5E };

	// 用于解密NRC数据

	/**
	 * 读取一个NRC文件并解码
	 * 
	 * @param filepath
	 *            nrc文件路径
	 * @return 简码后的字节数组
	 */
	public static byte[] DecodeNrc(String filepath) {
		byte key[];
		byte bts[];
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filepath));
			int len = bis.available();
			// 1.检测文件格式是否是krc
			if (len <= 4) {
				System.out.println("nrc文件错误!");
				return null;
			}
			key = new byte[4];
			bis.read(key);

			String strkey = new String(key, "utf-8");
			if (!strkey.equals("nrc1")) {
				System.out.println("不是nrc格式文件!");
				bis.close();
				return null;
			}

			bts = new byte[len - 4];
			bis.read(bts);
			bis.close();
		} catch (FileNotFoundException e) {
			System.out.println("找不到指定文件!");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("文件IO异常!");
			e.printStackTrace();
			return null;
		}

		// 2.解码
		int strlen = bts.length;
		for (int i = 0; i < strlen; i++) {
			int k = i % 16;
			bts[i] ^= NRCMAGIC[k];
		}

		// 3.解压
		try {
			Inflater decompresser = new Inflater();
			decompresser.setInput(bts, 0, bts.length);

			byte[] result = new byte[20480];
			int resultLength = decompresser.inflate(result);
			decompresser.end();

			byte[] decodebuf = new byte[resultLength];
			System.arraycopy(result, 0, decodebuf, 0, decodebuf.length);

			return decodebuf;

		} catch (java.util.zip.DataFormatException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
