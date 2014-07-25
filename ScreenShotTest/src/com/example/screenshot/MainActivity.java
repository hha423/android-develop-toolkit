package com.example.screenshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.persistentservice.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

/**
 * 截屏测试应用
 * 
 * @author steven-pan
 * 
 */
public class MainActivity extends Activity {
	private Bitmap bm;
	private ImageView imgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.imgView = (ImageView) this.findViewById(R.id.pic);

		this.findViewById(R.id.take).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				bm = takeScreenShotb(MainActivity.this);
				imgView.setImageBitmap(bm);
			}
		});
	}

	// 获取Activity的截屏
	protected static Bitmap takeScreenShot(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b = view.getDrawingCache();
		Bitmap bb = Bitmap.createBitmap(b);
		view.destroyDrawingCache();
		return bb;
	}

	// 获取Activity的截屏,去掉TITLE
	protected static Bitmap takeScreenShotb(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		DisplayMetrics dmp = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dmp);

		// 获取屏幕长和高
		int width = dmp.widthPixels;
		int height = dmp.heightPixels;
		// 去掉标题栏
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();

		return b;
	}

	protected static void testDisplay(Activity activity) {
		// 通过WindowManager获取
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		System.out.println("heigth : " + dm.heightPixels);
		System.out.println("width : " + dm.widthPixels);

		// 通过Resources获取
		DisplayMetrics dm2 = activity.getResources().getDisplayMetrics();
		System.out.println("heigth2 : " + dm2.heightPixels);
		System.out.println("width2 : " + dm2.widthPixels);

		// 获取屏幕的默认分辨率
		Display display = activity.getWindowManager().getDefaultDisplay();
		System.out.println("width-display :" + display.getWidth());
		System.out.println("heigth-display :" + display.getHeight());
	}

	protected static void savePic(Bitmap b, File filePath) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	public void onBackPressed() {
		if (bm != null && !bm.isRecycled()) {
			bm.recycle();
		}
		super.onBackPressed();
	}

}
