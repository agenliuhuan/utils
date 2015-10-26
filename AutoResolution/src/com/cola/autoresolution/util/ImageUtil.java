package com.cola.autoresolution.util;

import java.io.ByteArrayOutputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * 网络接口中图片格式大小
 * 
 * @author
 */
public class ImageUtil {

	/**
	 * 图片字节
	 * 
	 * @param bmp
	 * @param needRecycle
	 * @return
	 */
	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 图片是否为空
	 * 
	 * @param bitmap
	 * @return
	 */
	public static boolean isEmpty(Bitmap bitmap) {
		if (null == bitmap || bitmap.isRecycled()) {
			return true;
		}
		return false;
	}

	/**
	 * 图片旋转向左旋转90度后，按屏幕大小缩放
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap matrix(Bitmap bitmap, int width, int height) {
		int bWidth = bitmap.getWidth();
		int bHeight = bitmap.getHeight();

		try {
			// 旋转90度
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			Bitmap temp = Bitmap.createBitmap(bitmap, 0, 0, bWidth, bHeight,
					matrix, true);
			bitmap.recycle();

			// 缩放
			bitmap = Bitmap.createScaledBitmap(temp, height, width, true);
			if (null != bitmap && bitmap != temp) {
				temp.recycle();
			}
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 计算样本大小
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	/**
	 * 计算初始样本大小
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		// 手机密度
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static Bitmap getRoundBitmap(Bitmap bitmapSrc, int nWidth) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		try {
			Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmapSrc, nWidth,
					nWidth, true);

			Bitmap bitmapDes = Bitmap.createBitmap(nWidth, nWidth,
					Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmapDes);

			canvas.save();
			canvas.drawColor(Color.TRANSPARENT);

			// 绘图区域
			Path path = new Path();
			path.addCircle(nWidth / 2, nWidth / 2, nWidth / 2, Direction.CW);
			canvas.clipPath(path);

			canvas.drawBitmap(bitmapScaled, 0, 0, paint);

			// 白边
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(3.0f);
			paint.setColor(Color.WHITE);
			canvas.drawCircle(nWidth / 2, nWidth / 2,
					(float) (nWidth / 2 - 1.55), paint);
			canvas.restore();
			
			if (bitmapScaled != null && !bitmapScaled.isRecycled()) {
				bitmapScaled.recycle();
				bitmapScaled = null;
			}
			
			return bitmapDes;
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		}

		return null;
	}

	public static Bitmap getRoundBitmapEx(Bitmap bitmapSrc, int nWidth) {
		try {
			Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmapSrc, nWidth,
					nWidth, true);

			Bitmap bitmapDes = Bitmap.createBitmap(nWidth, nWidth,
					Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmapDes);

			canvas.save();
			canvas.drawColor(Color.TRANSPARENT);

			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmapDes.getWidth(),
					bitmapDes.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = bitmapDes.getWidth() / 2;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmapScaled, rect, rect, paint);

			// 白边
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(3.0f);
			paint.setColor(Color.WHITE);
			canvas.drawCircle(nWidth / 2, nWidth / 2,
					(float) (nWidth / 2 - 1.55), paint);
			
			if (bitmapScaled != null && !bitmapScaled.isRecycled()) {
				bitmapScaled.recycle();
				bitmapScaled = null;
			}

			return bitmapDes;
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		}

		return null;
	}
	
	public static Bitmap getRoundBitmapEx(Resources res, int resId, int nWidth) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		try {
			// 获取源图片的大小
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// opts.inPurgeable=true;
			// 当opts不为null时，但decodeFile返回空，不为图片分配内存，只获取图片的大小，并保存在opts的outWidth和outHeight
			BitmapFactory.decodeResource(res, resId, opts);
			int srcWidth = opts.outWidth, srcHeight = opts.outHeight;
			int destWidth = 0, destHeight = 0;
			// 缩放的比例
			double ratio = 0.0;
			double proportionate;
			destWidth = nWidth;
			ratio = (double) srcWidth / destWidth;
			proportionate = (double) (srcHeight / ratio);
			destHeight = (int) Math.rint(proportionate);
			// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			newOpts.inSampleSize = (int) ratio;// + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			newOpts.inPurgeable = true;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;

			Bitmap bitmapSrc = BitmapFactory
					.decodeResource(res, resId, newOpts);
			Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmapSrc, nWidth,
					nWidth, true);

			Bitmap bitmapDes = Bitmap.createBitmap(nWidth, nWidth,
					Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmapDes);

			canvas.save();
			canvas.drawColor(Color.TRANSPARENT);

			final int color = 0xff424242;
			final Rect rect = new Rect(0, 0, bitmapDes.getWidth(),
					bitmapDes.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = bitmapDes.getWidth() / 2;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmapScaled, rect, rect, paint);

			// 白边
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(3.0f);
			paint.setColor(Color.WHITE);
			canvas.drawCircle(nWidth / 2, nWidth / 2,
					(float) (nWidth / 2 - 1.55), paint);
			canvas.restore();

			if (bitmapSrc != null && !bitmapSrc.isRecycled()) {
				bitmapSrc.recycle();
				bitmapSrc = null;
			}
			
			if (bitmapScaled != null && !bitmapScaled.isRecycled()) {
				bitmapScaled.recycle();
				bitmapScaled = null;
			}
			
			return bitmapDes;
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		}
		return null;
	}


	public static Bitmap getRoundBitmap(Resources res, int resId, int nWidth) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		try {
			// 获取源图片的大小
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// opts.inPurgeable=true;
			// 当opts不为null时，但decodeFile返回空，不为图片分配内存，只获取图片的大小，并保存在opts的outWidth和outHeight
			BitmapFactory.decodeResource(res, resId, opts);
			int srcWidth = opts.outWidth, srcHeight = opts.outHeight;
			int destWidth = 0, destHeight = 0;
			// 缩放的比例
			double ratio = 0.0;
			double proportionate;
			destWidth = nWidth;
			ratio = (double) srcWidth / destWidth;
			proportionate = (double) (srcHeight / ratio);
			destHeight = (int) Math.rint(proportionate);
			// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			newOpts.inSampleSize = (int) ratio;// + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			newOpts.inPurgeable = true;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;

			Bitmap bitmapSrc = BitmapFactory
					.decodeResource(res, resId, newOpts);
			Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmapSrc, nWidth,
					nWidth, true);

			Bitmap bitmapDes = Bitmap.createBitmap(nWidth, nWidth,
					Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmapDes);

			canvas.save();
			canvas.drawColor(Color.TRANSPARENT);

			// 绘图区域
			Path path = new Path();
			path.addCircle(nWidth / 2, nWidth / 2, nWidth / 2, Direction.CW);
			canvas.clipPath(path);

			canvas.drawBitmap(bitmapScaled, 0, 0, paint);

			// 白边
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(3.0f);
			paint.setColor(Color.WHITE);
			canvas.drawCircle(nWidth / 2, nWidth / 2,
					(float) (nWidth / 2 - 1.55), paint);
			canvas.restore();

			if (bitmapSrc != null && !bitmapSrc.isRecycled()) {
				bitmapSrc.recycle();
				bitmapSrc = null;
			}
			
			if (bitmapScaled != null && !bitmapScaled.isRecycled()) {
				bitmapScaled.recycle();
				bitmapScaled = null;
			}
			
			return bitmapDes;
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		}
		return null;
	}

	/*
	 * 获取头像
	 */
	public static Bitmap getRoundBitmap(Bitmap bitmapDes, Bitmap bitmapSrc,
			int nWidth, int nHeight) {
		Bitmap bitmapDesNew = null, bitmapSrcNew = null;
		Bitmap bitmap = null;
		int nPixelsDes[] = null, nPixelsSrc[] = null, nA = 0, nR = 0, nG = 0, nB = 0;

		try {
			nPixelsDes = new int[nWidth * nHeight];
			nPixelsSrc = new int[nWidth * nHeight];

			bitmapDesNew = Bitmap.createScaledBitmap(bitmapDes, nWidth,
					nHeight, true);

			bitmapSrcNew = Bitmap.createScaledBitmap(bitmapSrc, nWidth,
					nHeight, true);

			bitmapDesNew
					.getPixels(nPixelsDes, 0, nWidth, 0, 0, nWidth, nHeight);

			bitmapSrcNew
					.getPixels(nPixelsSrc, 0, nWidth, 0, 0, nWidth, nHeight);

			if ((bitmapDesNew != bitmapDes) && (!bitmapDesNew.isRecycled())) {
				bitmapDesNew.recycle();
			}

			if ((bitmapSrcNew != bitmapSrc) && (!bitmapSrcNew.isRecycled())) {
				bitmapSrcNew.recycle();
			}

			if (!bitmapDes.isRecycled()) {
				bitmapDes.recycle();
			}

			if (!bitmapSrc.isRecycled()) {
				bitmapSrc.recycle();
			}

			for (int i = 0; i < nPixelsSrc.length; ++i) {
				nA = Color.alpha(nPixelsDes[i]);
				nR = Color.red(nPixelsDes[i]);
				nG = Color.green(nPixelsDes[i]);
				nB = Color.blue(nPixelsDes[i]);

				if ((nA == 255) && (nR == 255) && (nG < 200) && (nB < 200)) {
					nPixelsDes[i] = nPixelsSrc[i];
				}
			}

			bitmap = Bitmap.createBitmap(nPixelsDes, nWidth, nHeight,
					Config.ARGB_8888);
			
			if (bitmapDesNew != null && !bitmapDesNew.isRecycled()) {
				bitmapDesNew.recycle();
				bitmapDesNew = null;
			}
			if (bitmapSrcNew != null && !bitmapSrcNew.isRecycled()) {
				bitmapSrcNew.recycle();
				bitmapSrcNew = null;
			}
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
			bitmap = null;
		}

		return bitmap;
	}

	public static Bitmap getBitmapFromFile(String path, int width) {
		try {
			// 获取源图片的大小
			Bitmap bm;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// opts.inPurgeable=true;
			// 当opts不为null时，但decodeFile返回空，不为图片分配内存，只获取图片的大小，并保存在opts的outWidth和outHeight
			bm = BitmapFactory.decodeFile(path, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int destWidth = 0;
			int destHeight = 0;
			// 缩放的比例
			double ratio = 0.0;
			double proportionate;
			destWidth = width;
			ratio = (double) srcWidth / destWidth;
			proportionate = (double) (srcHeight / ratio);
			destHeight = (int) Math.rint(proportionate);
			// 高度小于宽度,采用等高缩放
			// if (destHeight < width) {
			// destHeight = width;
			// ratio = (double) srcHeight / destHeight;
			// proportionate = (double) (srcWidth / ratio);
			// destWidth = (int) Math.rint(proportionate);
			// }
			// if (width > srcWidth) {
			// destWidth = srcWidth;
			// destHeight = srcHeight;
			// }
			// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			newOpts.inSampleSize = (int) ratio;// + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			newOpts.inPurgeable = true;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			// 获取缩放后图片
			try {
				Bitmap srcBm = BitmapFactory.decodeFile(path, newOpts);
				Bitmap destBm = Bitmap.createScaledBitmap(srcBm, destWidth,
						destHeight, true);
				if (null != srcBm && destBm != srcBm) {
					srcBm.recycle();
				}
				return destBm;
			} catch (OutOfMemoryError e) {
				if (LogUtil.DEBUG)
					e.printStackTrace();
			} catch (Exception e) {
				if (LogUtil.DEBUG)
					e.printStackTrace();
			}
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getBitmapFromResources(Resources res, int id, int width) {
		if (null == res) {
			return null;
		}
		try {
			// 获取源图片的大小
			Bitmap bm;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// opts.inPurgeable=true;
			// 当opts不为null时，但decodeFile返回空，不为图片分配内存，只获取图片的大小，并保存在opts的outWidth和outHeight
			bm = BitmapFactory.decodeResource(res, id, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int destWidth = 0;
			int destHeight = 0;
			// 缩放的比例
			double ratio = 0.0;
			double proportionate;
			destWidth = width;
			ratio = (double) srcWidth / destWidth;
			proportionate = (double) (srcHeight / ratio);
			destHeight = (int) Math.rint(proportionate);
			// 高度小于宽度,采用等高缩放
			// if (destHeight < width) {
			// destHeight = width;
			// ratio = (double) srcHeight / destHeight;
			// proportionate = (double) (srcWidth / ratio);
			// destWidth = (int) Math.rint(proportionate);
			// }
			// if (width > srcWidth) {
			// destWidth = srcWidth;
			// destHeight = srcHeight;
			// }
			// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			newOpts.inSampleSize = (int) ratio;// + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			newOpts.inPurgeable = true;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			// 获取缩放后图片
			try {
				Bitmap srcBm = BitmapFactory.decodeResource(res, id, newOpts);
				Bitmap destBm = Bitmap.createScaledBitmap(srcBm, destWidth,
						destHeight, true);
				if (null != srcBm && destBm != srcBm) {
					srcBm.recycle();
				}
				return destBm;
			} catch (OutOfMemoryError e) {
				if (LogUtil.DEBUG)
					e.printStackTrace();
			} catch (Exception e) {
				if (LogUtil.DEBUG)
					e.printStackTrace();
			}
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getFullFromResources(Resources res, int id, int width,
			int height) {
		if (null == res) {
			return null;
		}
		try {
			// 获取源图片的大小
			Bitmap bm;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// opts.inPurgeable=true;
			// 当opts不为null时，但decodeFile返回空，不为图片分配内存，只获取图片的大小，并保存在opts的outWidth和outHeight
			bm = BitmapFactory.decodeResource(res, id, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			// 缩放的比例
			double ratio = 0.0;
			double ratio1 = (double) srcWidth / width;
			double ratio2 = (double) srcHeight / height;
			if (ratio1 > ratio2) {
				ratio = ratio1;
			} else {
				ratio = ratio2;
			}
			// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			newOpts.inSampleSize = (int) ratio;// + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			newOpts.inPurgeable = true;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = height;
			newOpts.outWidth = width;
			// 获取缩放后图片
			try {
				Bitmap srcBm = BitmapFactory.decodeResource(res, id, newOpts);
				Bitmap destBm = Bitmap.createScaledBitmap(srcBm, width, height,
						true);
				if (null != srcBm && destBm != srcBm) {
					srcBm.recycle();
				}
				return destBm;
			} catch (OutOfMemoryError e) {
				if (LogUtil.DEBUG)
					e.printStackTrace();
			} catch (Exception e) {
				if (LogUtil.DEBUG)
					e.printStackTrace();
			}
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getFullScreenBitmap(Resources res, int id, int width,
			int height) {
		if (null == res) {
			return null;
		}
		try {
			// 获取源图片的大小
			Bitmap bm;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// opts.inPurgeable=true;
			// 当opts不为null时，但decodeFile返回空，不为图片分配内存，只获取图片的大小，并保存在opts的outWidth和outHeight
			bm = BitmapFactory.decodeResource(res, id, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int destWidth = 0;
			int destHeight = 0;

			// 比较图片与屏幕的宽高比例
			double ratioWidth = (srcWidth * 1.0) / width;
			double ratioHeight = (srcHeight * 1.0) / height;
			double baseValue = 0.001;
			if (ratioWidth - ratioHeight > 0.001) {
				destWidth = (int) (srcWidth / ratioHeight);
				destHeight = height;
			} else if (ratioWidth - ratioHeight < baseValue
					&& ratioWidth - ratioHeight > -1 * baseValue) {
				destWidth = width;
				destHeight = height;
			} else {
				destWidth = width;
				destHeight = (int) (srcHeight / ratioWidth);
			}

			// 缩放的比例
			double ratio = 0.0;
			double proportionate;
			// destWidth = width;
			ratio = (double) srcWidth / destWidth;
			proportionate = (double) (srcHeight / ratio);
			// destHeight = (int) Math.rint(proportionate);
			// if (width > srcWidth) {
			// destWidth = srcWidth;
			// destHeight = srcHeight;
			// }
			// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			newOpts.inSampleSize = (int) ratio;// + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			newOpts.inPurgeable = true;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			// 获取缩放后图片
			try {
				Bitmap srcBm = BitmapFactory.decodeResource(res, id, newOpts);
				Bitmap destBm = Bitmap.createScaledBitmap(srcBm, destWidth,
						destHeight, true);
				if (null != srcBm && destBm != srcBm) {
					srcBm.recycle();
				}
				return destBm;
			} catch (OutOfMemoryError e) {
				if (LogUtil.DEBUG)
					e.printStackTrace();
			} catch (Exception e) {
				if (LogUtil.DEBUG)
					e.printStackTrace();
			}
		} catch (OutOfMemoryError e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		} catch (Exception e) {
			if (LogUtil.DEBUG)
				e.printStackTrace();
		}
		return null;
	}

	public static Bitmap roundCorners(final Bitmap source, final float radius) {
		int width = source.getWidth();
		int height = source.getHeight();

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		Bitmap clipped = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(clipped);
		canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		Bitmap rounded = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvas = new Canvas(rounded);
		canvas.drawBitmap(source, 0, 0, null);
		canvas.drawBitmap(clipped, 0, 0, paint);

		source.recycle();
		clipped.recycle();

		return rounded;
	}
}
