package com.example.Logica;

import android.content.Context;
import android.graphics.Canvas;

import com.example.framework.Image;
import com.example.framework.OrientationMode;
import com.example.spacecraft_x.R;

public class Direccoes
{
	private Image direita,esquerda,cima,baixo;
	private Context context;
	
	public Direccoes(Context context)
	{
		this.context = context;
	}

	public void onScreenLoading(int w, int h, OrientationMode orientation)
	{
		cima = new Image(context,R.drawable.seta_cima,(w/2),h-270,100,100);
		direita = new Image(context,R.drawable.seta_direita,(w/2)+60,h-200,100,100);
		esquerda = new Image(context,R.drawable.seta_esquerda,(w/2)-60,h-200,100,100);
		baixo = new Image(context,R.drawable.seta_baixo,(w/2),h-130,100,100);
	}
	public int verifica_direccao(float x, float y)
	{
		if(cima.IsTouch(x, y))
			return 1;
		if(baixo.IsTouch(x, y))
			return 2;
		if(direita.IsTouch(x, y))
			return 3;
		if(esquerda.IsTouch(x, y))
			return 4;
		return 0;
	}
	public void onDraw(Canvas canvas)
	{
		cima.Drawalpha(canvas, 70);
		direita.Drawalpha(canvas, 70);
		esquerda.Drawalpha(canvas, 70);
		baixo.Drawalpha(canvas, 70);
	}
}
