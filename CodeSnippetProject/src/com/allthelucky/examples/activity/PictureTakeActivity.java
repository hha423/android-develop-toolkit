package com.allthelucky.examples.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

/**
 * 拍照功能封装类， 当拍照并裁减成功时，将在Sd目录中生成zoom_result.jpg的图片，<br/>
 * 其路径为/hfk_temp/zoom_result.jpg，取得其文件路径可以进行进一步操作。
 * @author pxw
 */
public class PictureTakeActivity extends Activity {
	private static final int ACTION_SHOT = 0;
	private static final int ACTION_ZOOM = 1;

	private String fileShot = "";
	private String fileOutput = "";

	private int RESULT_YES = 0; // setResult返回码
	private int RESULT_ERROR = -1; // setResult返回码

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new View(this));

		Intent data = this.getIntent();
		this.fileOutput = data.getStringExtra("FILE_NAME");
		this.fileShot = Environment.getExternalStorageState().toString() + "/shot_source.jpg";

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, Configuration.ORIENTATION_LANDSCAPE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileShot)));
		startActivityForResult(intent, ACTION_SHOT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK && resultCode != 4) {
			setResult(-1, data);
			this.finish();
			return;
		}
		if (requestCode == ACTION_SHOT) { // 拍照处理
			startPhotoZoom(Uri.fromFile(new File(fileShot)));
		} else if (requestCode == ACTION_ZOOM) { // 缩放处理
			if (data == null) {
				setResult(RESULT_ERROR);
				this.finish();
			} else {
				processBitmap(data);
			}
		} else {
			setResult(RESULT_ERROR);
			this.finish();
		}
	}

	/**
	 * 启动缩放功能
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, Configuration.ORIENTATION_PORTRAIT);
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 宽高的比例
		intent.putExtra("aspectX", 172);
		intent.putExtra("aspectY", 110);
		// 裁剪图片输出宽高
		intent.putExtra("outputX", 260);
		intent.putExtra("outputY", 158);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		intent.putExtra("scale", "true");
		startActivityForResult(intent, ACTION_ZOOM);
	}

	/**
	 * 缩放结果处理
	 * 
	 * @param data
	 */
	private void processBitmap(Intent data) {
		final Bundle extras = data.getExtras();
		if (extras != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						File tmp = new File(fileOutput);
						if (tmp.exists()) {
							tmp.delete();
						}
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmp));
						Bitmap bitmap = (Bitmap) extras.getParcelable("data");
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
						bos.flush();
						bos.close();

						if (!bitmap.isRecycled()) { // recycle bitmap
							bitmap.recycle();
						}
						setResult(RESULT_YES);
						finish();
					} catch (IOException e) {
						e.printStackTrace();
						setResult(RESULT_ERROR);
						finish();
					}
				}
			}).start();
		} else {
			setResult(RESULT_ERROR);
			this.finish();
		}
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_ERROR);
		this.finish();
	}

}
