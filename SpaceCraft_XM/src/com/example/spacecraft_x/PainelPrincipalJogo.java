package com.example.spacecraft_x;


import java.util.List;

import com.example.framework.OrientationMode;

import com.example.Logica.Dados_jogo;
import com.example.Logica.Direccoes;
import com.example.Logica.Nave;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Switch;
enum Movimento
{
	DIREITA,ESQUERDA,CIMA,BAIXO,PARADO;
}
public class PainelPrincipalJogo extends SurfaceView implements Runnable,SensorEventListener {;
	private Movimento movimento_nave;
	private Dados_jogo jogo;
	private Direccoes dir;
	Display mDisplay;
	long ultimamarca = -1;
	private int alturajanela,largurajanela;
	private boolean isWifiP2pEnabled = false;
	Thread thread = null;
	SurfaceHolder surfaceHolder;
    volatile boolean running = false;
    Context context;
    SensorManager sm;
	Sensor sensor;

	public PainelPrincipalJogo(Context context,Sensor sx, SensorManager smx,Display displayx) {
		super(context);
		mDisplay = displayx;
		movimento_nave=Movimento.PARADO;
		jogo = new Dados_jogo(context,mDisplay);
		dir = new Direccoes(context);
		this.context = context;
		this.sensor=sx;
		this.sm=smx;
		surfaceHolder = getHolder();
		setFocusable(true);
	}
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		alturajanela = h;
		largurajanela = w;
		super.onSizeChanged(w, h, oldw, oldh);
		OrientationMode om = (h > w) ? OrientationMode.PORTRAIT : OrientationMode.LANDSCAPE;
		dir.onScreenLoading(w,h, om);
		jogo.onScreenLoading(w, h, om);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Update();
		canvas.drawColor(Color.BLACK);
		jogo.Draw(canvas);
		dir.onDraw(canvas);
		
	}
	
	public void Update()
	{
		jogo.getCaixa_vida().update(jogo.getTempo_jogo(),jogo.getMeteoritos());
		jogo.getMoeda().update(jogo.getTempo_jogo(),jogo.getMeteoritos(),jogo.getCaixa_vida());
		jogo.verifica_coli();
		jogo.getMeteoritos().update(jogo.getTempo_jogo());
		jogo.getNave().update();
		if(jogo.getN_vidas() == 0)
		{
			this.onExitGame();
		}
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			switch(dir.verifica_direccao(event.getX(),event.getY()))
			{
				case 1://cima
					jogo.getNave().setVelocityY(-10);
					break;
				case 2://baixo
					jogo.getNave().setVelocityY(10);
					break;
				case 3://direita
					jogo.getNave().setVelocityX(+10);
					break;
				case 4://esquerda
					jogo.getNave().setVelocityX(-10);
					break;
			}
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			jogo.getNave().setVelocityX(0);
			jogo.getNave().setVelocityY(0);
		}
		
		return true;
	}

	@Override
	public void run() {
		
		while(running){
			
		    if(surfaceHolder.getSurface().isValid()){
		     Canvas canvas = surfaceHolder.lockCanvas();
		     this.onDraw(canvas);
		     surfaceHolder.unlockCanvasAndPost(canvas);
	    }
	  }
	}
	public void onExitGame()
	{
		running = false;
		if (sm != null && sensor != null)
			sm.unregisterListener(this);
		int x = 1;
		//thread.interrupt();
		Activity host = (Activity) this.getContext();
		Bundle bundle = new Bundle();
		bundle.putInt("Score",this.jogo.getMoedas());
		bundle.putInt("Tempo",this.jogo.getTempo_jogo());
		Intent intent = new Intent(context, FimJogo.class);
		host.startActivity(intent.putExtras(bundle));
		host.finish();
	}	
	public void resumeGame()
	{
		//gameState = GameState.PLAY_RESUME;
		sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void pauseGame()
	{
        //gameState = GameState.PAUSE;
		
		boolean retry = true;
	    running = false;
		while(retry){
		  try {
		   thread.join(); 
		    retry = false;
		  } catch (InterruptedException e) {
		 	     e.printStackTrace();
		    }
		}
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			if (ultimamarca > 0) {
				float sx = 0, sy = 0;
				switch (mDisplay.getRotation()) {
				case Surface.ROTATION_0:
					sx = -event.values[0];
					sy = event.values[1];
					break;
				case Surface.ROTATION_90:
					sx = event.values[1];
					sy = event.values[0];
					break;
				case Surface.ROTATION_180:
					sx = event.values[0];
					sy = -event.values[1];
					break;
				case Surface.ROTATION_270:
					sx = -event.values[1];
					sy = -event.values[0];
					break;
				}
				if(sx <0 && sx < -4f)
				{this.jogo.getNave().setVelocityX(-10);}
				if(sx >0 && sx > 4f)
				{
					{this.jogo.getNave().setVelocityX(+10);}
				}
				if(sx >-4 && sx < 4f)
				{this.jogo.getNave().setVelocityX(0);}
				if(sy <0 && sy < -1f)
				{this.jogo.getNave().setVelocityY(-10);}
				if(sy >0 && sy > 4f)
				{
					{this.jogo.getNave().setVelocityY(+10);}
				}
				if(sy >-1 && sy < 4f)
				{this.jogo.getNave().setVelocityY(0);}
//				this.jogo.getNave().setVelocityX((int)(this.jogo.getNave().getVelocityX()+(int)(deltatempo*sx)));
//				this.jogo.getNave().setVelocityY((int)(this.jogo.getNave().getVelocityY()+(int)(deltatempo*sy)));
			}
			ultimamarca = event.timestamp;
			invalidate();
		}
		
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
