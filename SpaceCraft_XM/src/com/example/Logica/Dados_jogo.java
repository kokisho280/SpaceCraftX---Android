package com.example.Logica;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.example.framework.Image;
import com.example.framework.OrientationMode;
import com.example.spacecraft_x.PainelPrincipalJogo;
import com.example.spacecraft_x.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.view.Display;

public class Dados_jogo 
{
	Nave nave;
	Vida caixa_vida;
	Pedras meteoritos;
	Context context;
	int n_vidas=3;
	int moedas;
	Image vida;
	Moeda moeda;
	int tempo_jogo;
	Display display;
	public Dados_jogo(Context _context,Display displayx)
	{
		display=displayx;
		moeda =new Moeda(_context);
		moedas = 0;
		caixa_vida = new Vida(_context);
		vida = new Image(_context,R.drawable.onebit_43, 0,20,60,60);
		meteoritos= new Pedras(_context);
		this.context = _context;
		nave = new Nave(0,0, context,display);
	}
	public void onExitGame(Thread x)
	{
		x.interrupt();
	}
	public Vida getCaixa_vida() {
		return caixa_vida;
	}
	public void setCaixa_vida(Vida caixa_vida) {
		this.caixa_vida = caixa_vida;
	}
	long inicio_jogo = System.currentTimeMillis();
	public int getN_vidas() {
		return n_vidas;
	}
	public void setN_vidas(int n_vidas) {
		this.n_vidas = n_vidas;
	}
	public Pedras getMeteoritos() {
		return meteoritos;
	}
	public void onScreenLoading(int w, int h, OrientationMode orientation)
	{
		meteoritos.onScreenLoading(w, h);
		caixa_vida.onScreenLoading(w, h);
		moeda.onScreenLoading(w, h);
	}
	public Nave getNave() {
		return nave;
	}
	public void setNave(Nave nave) {
		this.nave = nave;
	}
	public void verifica_coli()
	{
		for(int i= 0; i< meteoritos.getSize();i++)
		{
			if(!meteoritos.getPedra(i).isComparado())
			{
				if(nave.ve_colisao(meteoritos.getPedra(i).getCirculo()))
				{
					n_vidas--;
					meteoritos.getPedra(i).setComparado(true);
				}
			}
		}
		if(nave.ve_colisao(caixa_vida.caixa))
		{
			n_vidas++;
			caixa_vida.setGasto(true);
		}
		if(nave.ve_colisao(moeda.caixa))
		{
			moedas++;
			moeda.setGasto(true);
		}
	}
	public int getTempo_jogo() {
		return tempo_jogo;
	}
	public void setTempo_jogo(int tempo_jogo) {
		this.tempo_jogo = tempo_jogo;
	}
	public void Draw(Canvas canvas)
	{
		moeda.draw(canvas);
		caixa_vida.draw(canvas);
		tempo_jogo = (int)(System.currentTimeMillis() - inicio_jogo)/1000 ;
		Typeface tf =Typeface.createFromAsset(context.getAssets(),"Pacifico.ttf");
		Paint paint = new Paint();
		paint.setTextSize(60);
		paint.setColor(Color.RED);
		paint.setTypeface(tf);
		meteoritos.onDraw(canvas);
		nave.onDraw(canvas);
		vida.Draw(canvas);
		String aux = "* "+n_vidas;
		canvas.drawText(aux,60,70,paint);
		aux = ""+(tempo_jogo)+" segundos";
		canvas.drawText(aux,60,200,paint);
		aux = "Moedas :"+moedas;
		canvas.drawText(aux,60,320,paint);
	}
	public int getMoedas() {
		return moedas;
	}
	public void setMoedas(int moedas) {
		this.moedas = moedas;
	}
	public Moeda getMoeda() {
		return moeda;
	}
	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}

}
