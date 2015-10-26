package com.cola.autoresolution.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class AssetsUtil {
	private static final String CONFIG_FILE_NAME = "dimens.xml";

	public static void copyDimenXml(Context context) {
		try {
			InputStream myInput = context.getAssets().open(CONFIG_FILE_NAME);
			File file = new File(FileUtil.SRC_XML);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			OutputStream myOutput = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copyFile(String path, String fileName, InputStream input) {
		try {
			File file = new File(path + fileName);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			OutputStream myOutput = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = input.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			// Close the streams
			myOutput.flush();
			myOutput.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}