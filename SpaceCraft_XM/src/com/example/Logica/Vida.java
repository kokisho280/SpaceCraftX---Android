package com.example.Logica;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.framework.GameElement;
import com.example.framework.Image;
import com.example.spacecraft_x.R;

public class Vida{
	int x,y;
	int speedy;
	Image imagem;
	Context context;
	Rect caixa;
	int alturatela,larguratela;
	boolean gasto;
	public Vida(Context _context)
	{
		gasto = false;
		imagem = new Image(_context,R.drawable.box,50,-80,70,70);
		caixa = new Rect(55,-80, 115, -10);
		context = _context;
		speedy=5;
	}
	public void onScreenLoading(int w, int h)
	{
		alturatela = h;
		larguratela = w;
	}
	public void update(int tempo_jogo, Pedras meteoritos)
	{
		boolean intersecta = false;
		if(tempo_jogo >= 20)
		{
			int k = tempo_jogo%20;
			if(k == 0)
			{
				Random rand = new Random();
				do
				{
					intersecta = false;
					this.x = rand.nextInt(larguratela-70);
					this.y = -80;
					caixa = new Rect(this.x+5,this.y,this.x+60,this.y+70);
					for(int i = 0; i< meteoritos.getSize();i++)
					{
						if(meteoritos.getPedra(i).getCirculo().contains(caixa.left, caixa.top,caixa.right, caixa.bottom))
						{
							intersecta = true;
						}
					}
				}while(intersecta);
				imagem = new Image(context,R.drawable.box,x,y,70,70);
				gasto = false;
			}
			else
			{
				if(!gasto)
				{
					if(meteoritos.getSize() > 0)
					{
						speedy = 5+(tempo_jogo/30)+1;
					}
					if(this.getY() < alturatela)
					{
						this.setY(this.getY()+speedy);
						caixa = new Rect(this.x+5,y,this.x+60,y+70);
						imagem = new Image(context,R.drawable.box,this.x,y,70,70);
					}
				}
			}
		}
	}
	public boolean isGasto() {
		return gasto;
	}
	public void setGasto(boolean gasto) {
		this.gasto = gasto;
		if(this.gasto)
		{
			this.y = -80;
			caixa = new Rect(this.x+5,y,this.x+60,y+70);
			imagem = new Image(context,R.drawable.box,this.x,y,70,70);
		}
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpeedy() {
		return speedy;
	}
	public void setSpeedy(int speedy) {
		this.speedy = speedy;
	}
	public void draw (Canvas canvas)
	{
		imagem.Draw(canvas);
	}
	

}
