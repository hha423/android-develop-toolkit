package com.allthelucky.examples.activity;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

/** 
 * @ClassName PlayMusicActivity 
 * @Description 播放声音
 * @author xuxiang
 * @date 2013-1-11
 */ 
public class PlayMusicActivity extends Activity {

	private MediaPlayer mMediaPlayer;
	private AssetFileDescriptor mAfd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		soundPrepare();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		soundStart();
	}

	private void soundPrepare() {
		try {
			mAfd = PlayMusicActivity.this.getAssets().openFd("shake_sound.mp3");
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		} catch (Exception e) {
		}
	}

	private void soundStart() {
		try {
			if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(mAfd.getFileDescriptor(), mAfd.getStartOffset(), mAfd.getLength());
			mMediaPlayer.prepare();
			mMediaPlayer.seekTo(0);
			mMediaPlayer.start();
		} catch (Exception e) {
		}
	}

}
