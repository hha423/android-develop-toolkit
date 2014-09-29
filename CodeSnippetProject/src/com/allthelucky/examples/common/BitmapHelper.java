package com.allthelucky.examples.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public final class BitmapHelper {

	/**
	 * make sure the color data size not more than 5M
	 * 
	 * @param rect
	 * @return
	 */
	public static boolean makesureSizeNotTooLarge(Rect rect) {
		final int FIVE_M = 5 * 1024 * 1024;
		if (rect.width() * rect.height() * 2 > FIVE_M) {
			// 不能超过5M
			return false;
		}
		return true;
	}

	public static int getSampleSizeOfNotTooLarge(Rect rect) {
		final int FIVE_M = 5 * 1024 * 1024;
		double ratio = ((double) rect.width()) * rect.height() * 2 / FIVE_M;
		return ratio >= 1 ? (int) ratio : 1;
	}

	/**
	 * 自适应屏幕大小 得到最大的smapleSize 同时达到此目标： 自动旋转 以适应view的宽高后, 不影响界面显示效果
	 * 
	 * @param vWidth
	 *            view width
	 * @param vHeight
	 *            view height
	 * @param bWidth
	 *            bitmap width
	 * @param bHeight
	 *            bitmap height
	 * @return
	 */
	public static int getSampleSizeAutoFitToScreen(int vWidth, int vHeight,
			int bWidth, int bHeight) {
		if (vHeight == 0 || vWidth == 0) {
			return 1;
		}

		int ratio = Math.max(bWidth / vWidth, bHeight / vHeight);
		int ratioAfterRotate = Math.max(bHeight / vWidth, bWidth / vHeight);

		return Math.min(ratio, ratioAfterRotate);
	}

	/**
	 * 检测是否可以解析成位图
	 * 
	 * @param datas
	 * @return
	 */
	public static boolean verifyBitmap(byte[] datas) {
		return verifyBitmap(new ByteArrayInputStream(datas));
	}

	/**
	 * 检测是否可以解析成位图
	 * 
	 * @param input
	 * @return
	 */
	public static boolean verifyBitmap(InputStream input) {
		if (input == null) {
			return false;
		}
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		input = input instanceof BufferedInputStream ? input
				: new BufferedInputStream(input);
		BitmapFactory.decodeStream(input, null, options);
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (options.outHeight > 0) && (options.outWidth > 0);
	}

	/**
	 * 检测是否可以解析成位图
	 * 
	 * @param path
	 * @return
	 */
	public static boolean verifyBitmap(String path) {
		try {
			return verifyBitmap(new FileInputStream(path));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	/***
	 *  save drawable to file
	 */
	public static void savePic(Context context, Drawable drawable,
			String filePath) {
		Bitmap b = drawableToBitamp(drawable);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(filePath));
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Bitmap → byte[]
	public static byte[] convertBitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	//Bitmap to Drawable
	public static Bitmap drawableToBitamp(Drawable drawable) {
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		System.out.println("DrawableתBitmap");
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		
//		BitmapDrawable bd = (BitmapDrawable) drawable;//another method
//		return bd.getBitmap();

		return bitmap;
	}

	public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
		return bd;
	}

	public static Bitmap getBitmapFromResources(Activity act, int resId) {
		Resources res = act.getResources();
		return BitmapFactory.decodeResource(res, resId);
	}

	// byte[] → Bitmap
	public static Bitmap convertBytes2Bimap(byte[] b) {
		if (b.length == 0) {
			return null;
		}
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	//Bitmap缩放
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

}
