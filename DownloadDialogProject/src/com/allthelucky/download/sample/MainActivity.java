package com.allthelucky.download.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allthelucky.dialog.R;
import com.allthelucky.download.DownloadThread;
import com.allthelucky.download.DownloadThread.DownloadListener;

/**
 * 下载对话框示例
 * 
 * @author savant-pan
 * 
 */
public class MainActivity extends Activity {
	private static final String ARCHIVE_NAME = "application/vnd.android.package-archive";
	private static final String DOWNLOAD_URL = "http://www.allthelucky.com/d/wf.apk";
	private static final String FILE_NAME = "test.apk";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		showDialog(this, DOWNLOAD_URL, FILE_NAME);
	}

	public static void showDialog(final Context context, final String url, final String fileName) {
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		final View layout = LayoutInflater.from(context).inflate(R.layout.app_download_layout, null);
		final TextView msgText = (TextView) layout.findViewById(R.id.dialog_message);
		final TextView pctText = (TextView) layout.findViewById(R.id.pct_message);
		final ProgressBar progressBar = (ProgressBar) layout.findViewById(R.id.pct_progressbar);

		msgText.setText(R.string.downloading_text);
		progressBar.setMax(100);

		dialog.setView(layout);
		final DownloadThread downloadThread = new DownloadThread(context);
		downloadThread.setParams(url, fileName, new DownloadListener() {
			@Override
			public void onUpdate(int percent) {
				pctText.setText(percent + "%");
				progressBar.setProgress(percent);
			}

			@Override
			public void onCompleted(int code, String reason) {
				dialog.dismiss();
				if (DownloadListener.OK == code) {
					Intent intent = new Intent();
					intent.setAction(android.content.Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.parse("file://" + reason), ARCHIVE_NAME);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				} else {
					Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
				}
			}
		});
		dialog.setTitle(R.string.update_text);
		dialog.setButton(context.getText(R.string.cancel_text), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				downloadThread.cancel();
			}
		});
		dialog.setCancelable(false);
		downloadThread.start();
		dialog.show();
	}

}
