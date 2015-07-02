package com.example.Logica;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.framework.Image;
import com.example.spacecraft_x.R;

public class Moeda {
	int x,y;
	int speedy;
	Image imagem;
	Context context;
	RectF caixa;
	int alturatela,larguratela;
	boolean gasto;
	public Moeda(Context _context)
	{
		gasto = false;
		imagem = new Image(_context,R.drawable.coin_01,50,-80,70,70);
		caixa = new RectF(50,-80, 120, -10);
		context = _context;
		speedy=5;
	}
	public void onScreenLoading(int w, int h)
	{
		alturatela = h;
		larguratela = w;
	}
	public void update(int tempo_jogo, Pedras meteoritos,Vida vida)
	{
		boolean intersecta = false;
		if(tempo_jogo >= 10)
		{
			int k = tempo_jogo%10;
			if(k == 0)
			{
				Random rand = new Random();
				do
				{
					intersecta = false;
					this.x = rand.nextInt(larguratela-70);
					this.y = -80;
					caixa = new RectF(this.x-5,this.y-5,this.x+75,this.y+75);
					for(int i = 0; i< meteoritos.getSize();i++)
					{
						if(meteoritos.getPedra(i).getCirculo().contains(caixa.left, caixa.top,caixa.right, caixa.bottom))
						{
							intersecta = true;
						}
					}
					if(this.caixa.contains(vida.caixa.left, vida.caixa.top, vida.caixa.right, vida.caixa.bottom))
					{
						intersecta = true;
					}
				}while(intersecta);
				imagem = new Image(context,R.drawable.coin_01,x,y,70,70);
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
						caixa = new RectF(this.x-5,this.y-5,this.x+75,this.y+75);
						imagem = new Image(context,R.drawable.coin_01,this.x,y,70,70);
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
			caixa = new RectF(this.x-5,this.y-5,this.x+75,this.y+75);
			imagem = new Image(context,R.drawable.coin_01,this.x,y,70,70);
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
