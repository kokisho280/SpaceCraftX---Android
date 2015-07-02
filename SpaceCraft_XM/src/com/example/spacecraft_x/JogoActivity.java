package com.example.spacecraft_x;


import java.util.List;

import com.example.Logica.Dados_jogo;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class JogoActivity  extends Activity{
	private PainelPrincipalJogo paineljogo;
	private static final String TAG = JogoActivity.class.getSimpleName();
    SensorManager sm;
	Sensor sensor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//retira o titulo
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//coloca janela fullscreen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		List<Sensor> lst = sm.getSensorList(Sensor.TYPE_ALL);
		if (lst == null || lst.size() == 0)
			Log.i("SensorCtrl", "Não tem sensores");
		else
			for (Sensor s : lst) {
				Log.i("SensorCtrl",
						"Sensor: " + s.getName() + ", 0-" + s.getMaximumRange()
								+ ", " + s.getPower() + "mA, "
								+ s.getResolution() + ", " + s.getType() + ", "
								+ s.getVendor() + ", " + s.getVersion());
				// sm.registerListener(this,s,SensorManager.SENSOR_DELAY_NORMAL);
			}
		sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (sensor == null) {
			Toast.makeText(this, "Nao existe um sensor adequado disponivel",
					Toast.LENGTH_LONG).show();
		}
		paineljogo = new PainelPrincipalJogo(this,sensor,sm,((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay());
		setContentView(paineljogo);
		Log.d(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	@Override
	protected void onPause() {		
	    super.onPause();
	    paineljogo.pauseGame();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		paineljogo.resumeGame();
	}
	
	@Override
	protected void onDestroy() {
	   super.onDestroy();
	   paineljogo.onExitGame();
	 
	}

}
