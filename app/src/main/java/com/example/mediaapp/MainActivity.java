package com.example.mediaapp;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Video;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {
	MediaPlayer mediaPlayer,mediaPlayer2;
	Button url,sd,local,startvideo,stopvideo,volumeup,volumedown;
	VideoView videoview; 
	float volume=0.1f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);




		//Δήλωση αντικειμένων κουμπιών
		url=(Button) findViewById(R.id.url);
		sd=(Button) findViewById(R.id.sd);
		local=(Button) findViewById(R.id.local);
		volumedown = (Button) findViewById(R.id.volumedown);
		volumeup = (Button) findViewById(R.id.volumeup);


		//Δημιουργία αντικειμένου MediaPlayer, ορισμός ως μή επαναλαμβανόμενο,
		//ορισμός ελέγχου για την λήξη της αναπαραγωγής
		mediaPlayer = MediaPlayer.create(this, R.raw.song);
		mediaPlayer.setLooping(false);
		mediaPlayer.setVolume(volume, volume);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "The song is over", Toast.LENGTH_LONG).show();
			}
		});


		//Δήλωση ιδιοτήτων VideoView
		
		videoview = (VideoView) findViewById(R.id.videoView1);
		String path = "android.resource://" + getPackageName() + "/" + R.raw.nature;
		videoview .setVideoURI(Uri.parse(path));
		
		MediaController controller = new MediaController(this);
		controller.setAnchorView(videoview);
		controller.setMediaPlayer(videoview);
		videoview.setMediaController(controller);


		//Ορισμός onClickListeners για τα κουμπιά
		
		url.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mediaPlayer.stop();

				//Αναπαραγωγή ήχου από το διαδίκτυο
				
				String url = "http://freedownloads.last.fm/download/639019414/Andanum.mp3"; 
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				try {
					mediaPlayer.setDataSource(url);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					mediaPlayer.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // might take long! (for buffering, etc)
				
				mediaPlayer.setLooping(false);
				
				mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "The song is over", Toast.LENGTH_LONG).show();
					}
				});
				mediaPlayer.start();
			}
		});
		
		sd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mediaPlayer.stop();
				
				Uri myUri = Uri.parse(Environment.getExternalStorageDirectory() + "/" + "sdcardsong.mp3" );
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				try {
					mediaPlayer.setDataSource(MainActivity.this, myUri);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					mediaPlayer.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mediaPlayer.start();
				
			}
		});
		
		local.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mediaPlayer.stop();
				mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.song);
				mediaPlayer.setLooping(false);
				
				mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "The song is over", Toast.LENGTH_LONG).show();
					}
				});
				mediaPlayer.start();
			}
		});
		
		
volumeup.setOnClickListener(new OnClickListener() {
			
		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
				if(volume<1.0f)
				{
					volume+=0.1f;
					mediaPlayer.setVolume(volume, volume);
				}
				else{
					Toast.makeText(MainActivity.this, "Volume full!", Toast.LENGTH_LONG).show();
				}
		}
});
volumedown.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if(volume>0.0f)
		{
			volume-=0.1f;
			mediaPlayer.setVolume(volume, volume);
		}
		else{
			Toast.makeText(MainActivity.this, "Muted!", Toast.LENGTH_LONG).show();
		}
	}
});


		
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		mediaPlayer.pause();
	
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mediaPlayer.start();
		
	}
	

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mediaPlayer = MediaPlayer.create(this, R.raw.shutdown);
		mediaPlayer.setLooping(false);
		mediaPlayer.setVolume(0.5f, 0.5f);
		mediaPlayer.start();
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
