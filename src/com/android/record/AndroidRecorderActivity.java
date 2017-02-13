package com.android.record;

import java.io.IOException;


import android.app.Activity;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AndroidRecorderActivity extends Activity {
	private Dialog voiceRecordDialog;
	private Dialog callRecordDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final AudioRecorder recorder = new AudioRecorder("/video/");
		Button recordBtn = (Button) this.findViewById(R.id.recordVoiceBtn);
		Button playBtn = (Button) this.findViewById(R.id.playAudioBtn);
		Button recordCallBtn = (Button) this.findViewById(R.id.recordCallBtn);
		
		playBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MediaPlayer mp = new MediaPlayer();
				try {
					mp.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+"/video/audio.3gp");
					mp.prepare();
	                mp.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
                mp.setOnCompletionListener(new OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                    	mp.stop();
                        mp.release();
                    }
                });
			}
		});
		
		recordBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					voiceRecordDialog = new Dialog(AndroidRecorderActivity.this);
					voiceRecordDialog.setContentView(R.layout.custom_dialog);
					voiceRecordDialog.setTitle("Voice recording in progress...");
					Button stopBtn = (Button) voiceRecordDialog.findViewById(R.id.button2);
					
					recorder.start();
					stopBtn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							try {
								recorder.stop();
								voiceRecordDialog.dismiss();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					voiceRecordDialog.show();
				} catch (IOException e) {
					try {
						recorder.stop();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		});
		
		recordCallBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					callRecordDialog = new Dialog(AndroidRecorderActivity.this);
					callRecordDialog.setContentView(R.layout.custom_dialog);
					callRecordDialog.setTitle("Call recording in progress...");
					Button stopBtn = (Button) callRecordDialog.findViewById(R.id.button2);
					
					recorder.start();
					stopBtn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							try {
								recorder.stop();
								callRecordDialog.dismiss();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					callRecordDialog.show();
				} catch (IOException e) {
					try {
						recorder.stop();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		});
		
	}
}