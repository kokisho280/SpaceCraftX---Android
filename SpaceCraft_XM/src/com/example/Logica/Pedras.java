package com.example.Logica;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.framework.Image;
import com.example.framework.OrientationMode;
import com.example.spacecraft_x.R;

public class Pedras
{
    ArrayList <Pedra> pedras;
    int alturajanela,largurajanela;
    Image pedra_imagem;
    Context context;
    public Pedras(Context context)
    {
    	pedra_imagem = new Image(context, R.drawable.metereo,0,0,70,70);
        pedras = new ArrayList<Pedra>();
    }
    public void onScreenLoading(int w, int h)
	{
		alturajanela = h;
		largurajanela = w;
		for(int i = 0; i< pedras.size();i++)
        {
            pedras.get(i).onScreenLoading(w, h);
        }
	}
    public void update(int tempo_jogo)
    {
        int i = pedras.size();
        Random gerador = new Random();
        if(i == 0)
            pedras.add(new Pedra(gerador.nextInt(largurajanela-70),-10));
        else
        {
        		float x = (tempo_jogo/30)+1;//nivel de dificuldade
        		if(x >= 6)
        		{x=5;}
	            if(pedras.get(pedras.size()-1).getBgY() >= 200/x)// distancia que aparecem novos meteoritos
	            {
	            		pedras.add(new Pedra(gerador.nextInt(largurajanela),-10));
	            }
        }
        for(i = 0; i< pedras.size();i++)
        {
            if(pedras.get(i).getBgY() > alturajanela)
                pedras.remove(i);      
        }
        for(i = 0; i< pedras.size();i++)
        {
            pedras.get(i).update(tempo_jogo);
        }
    }
    public Pedra getPedra(int index)
    {
        return pedras.get(index);
    }
    public int getSize()
    {
        return pedras.size();
    }
    public void onDraw(Canvas canvas)
	{
    	for(int i = 0; i< pedras.size();i++)
        {
    		
    		pedra_imagem.SetX(pedras.get(i).getBgX());
    		pedra_imagem.SetY(pedras.get(i).getBgY());
    		pedra_imagem.Draw(canvas);    		
        }
	}
}
class Pedra {
    private int bgX, bgY, speedY,alturajanela,largurajanela;
    private boolean comparado;
    private RectF circulo;
    float halfW = 40/2;
    float halfH = 40/2;
    public Pedra (int x, int y)
    {
        bgX = x;
        bgY = y;
        circulo= new RectF(this.getBgX()-halfW+40, this.getBgY()-halfH+40, this.getBgX()+halfW+40, this.getBgY()+halfH+40); 
        speedY = 5;
        comparado = false;
    }
    public int getSpeedy()
    {
    	return speedY;
    }
    public void onScreenLoading(int w, int h)
	{
    	alturajanela = h;
		largurajanela = w;
	}
    public void update(int tempo_jogo)
    {
    	int velocidade = speedY+(tempo_jogo/30)+1;
        setBgY(getBgY() + velocidade);
        if(getBgY() > alturajanela)
        {
            try {
                this.finalize();
            } catch (Throwable ex) {
            }
        }
    }
    public int getBgX() {
        return bgX;
    }

    public void setBgX(int bgX) {
        this.bgX = bgX;
        circulo= new RectF(this.getBgX()-halfW+35, this.getBgY()-halfH+35, this.getBgX()+halfW+35, this.getBgY()+halfH+35); 
    }

    public int getBgY() {
        return bgY;
    }

    public void setBgY(int bgY) {
        this.bgY = bgY;
        circulo= new RectF(this.getBgX()-halfW+35, this.getBgY()-halfH+35, this.getBgX()+halfW+35, this.getBgY()+halfH+35); 
    }

    public RectF getCirculo() {
		return circulo;
	}
	public void setCirculo(RectF circulo) {
		this.circulo = circulo;
	}
	public boolean isComparado() {
        return comparado;
    }

    public void setComparado(boolean comparado) {
        this.comparado = comparado;
    }
}
