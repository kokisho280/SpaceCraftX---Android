package com.example.spacecraft_x;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btNovoJogo,btTop5,btMultip,about_button,prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btNovoJogo = (Button)findViewById(R.id.btNovoJogo);
		btTop5 = (Button)findViewById(R.id.btVerTop5);
		btMultip = (Button)findViewById(R.id.btMulti);
		about_button = (Button)findViewById(R.id.about_button); 
		
		about_button.setOnClickListener(new View.OnClickListener(){
		public void onClick(View v) { 
			Intent i = new Intent(MainActivity.this, About.class); 
			startActivity(i); 
		}
		});

		btMultip.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MultiPlayerInicio.class);
				startActivity(intent);
			}
		});
		btNovoJogo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, JogoActivity.class);
				startActivity(intent);
				
			}
		});
		btTop5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,VerTop5.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
		 	MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.menu, menu);
	      return super.onCreateOptionsMenu(menu);
	      
	   }

	   @Override
	   public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.settings:
	         startActivity(new Intent(this, Prefs.class));
	         return true;
	      // More items go here (if any) ...
	      }
	      return false;
	   }
	


	
	protected void onResume() {
		super.onResume();
		Music.play(this, R.raw.main_music);
	}
	
	@Override 
	protected void onPause() {
		super.onPause(); 
		Music.stop(this); 
	}
	
}
