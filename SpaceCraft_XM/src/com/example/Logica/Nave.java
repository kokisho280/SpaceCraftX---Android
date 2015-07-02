package com.example.Logica;

import com.example.framework.Image;
import com.example.framework.OrientationMode;
import com.example.spacecraft_x.R;
import com.example.spacecraft_x.R.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Display;

public class Nave 
{
	private int x,y;
	private int velocityX,velocityY;
	private Rect [] rect = new Rect[3];
	Image imagem_nave;
	int altura,largura;
	Context context;
	//150,150
	public Nave(int x , int y,Context _context,Display displayx)
	{
		largura=displayx.getWidth();
		altura = displayx.getHeight();
		velocityX=0;
		velocityY=0;
		this.context = _context;
		this.x = x;
		this.y=y;
		imagem_nave = new Image(context,R.drawable.nave2,this.getX(),this.getY(),300,300);
		rect[0] = new Rect(this.getX()+133,this.getY()+75,this.getX()+156,this.getY()+215);
        rect[1] = new Rect(this.getX()+118,this.getY()+145,this.getX()+173,this.getY()+202);
        rect[2]= new Rect(this.getX()+83,this.getY()+167,this.getX()+205,this.getY()+198);
	}

	public int getVelocityX() {
		return velocityX;
	}
	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}
	public int getVelocityY() {
		return velocityY;
	}
	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}
	public boolean ve_colisao(RectF x)
	{
		if(x.intersect(rect[0].left,rect[0].top, rect[0].right, rect[0].bottom))
		{
			return true;
		}
		if(x.intersect(rect[1].left,rect[1].top, rect[1].right, rect[1].bottom))
		{
			return true;
		}
		if(x.intersect(rect[2].left,rect[2].top, rect[2].right, rect[2].bottom))
		{
			return true;
		}
		
		return false;
	}
	public void  update()
	{
		if((this.x + velocityX) > -100 && (this.x + velocityX) < (largura-200))
			this.x += velocityX;
		if((this.y + velocityY) > -50 && (this.y + velocityY) < (altura-210))
			this.y += velocityY;
		imagem_nave.SetY(y);
		imagem_nave.SetX(x);
		rect[0] = new Rect(this.getX()+133,this.getY()+75,this.getX()+156,this.getY()+215);
        rect[1] = new Rect(this.getX()+118,this.getY()+145,this.getX()+173,this.getY()+202);
        rect[2]= new Rect(this.getX()+83,this.getY()+167,this.getX()+205,this.getY()+198);
	}
	public boolean ve_colisao(Rect x)
	{
		if(x.intersect(rect[0].left,rect[0].top, rect[0].right, rect[0].bottom))
		{
			return true;
		}
		if(x.intersect(rect[1].left,rect[1].top, rect[1].right, rect[1].bottom))
		{
			return true;
		}
		if(x.intersect(rect[2].left,rect[2].top, rect[2].right, rect[2].bottom))
		{
			return true;
		}
		
		return false;
	}
	public void onScreenLoading(int w, int h, OrientationMode orientation)
	{
		altura =h;
		largura=w;
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
	public void setY(int y) 
	{
		this.y = y;
		
	}
	public void onDraw(Canvas canvas)
	{
//		Paint paint = new Paint();
//		paint.setColor(Color.BLUE);
//		canvas.drawRect(rect[0], paint);
//		paint.setColor(Color.RED);
//		canvas.drawRect(rect[1], paint);
//		paint.setColor(Color.GREEN);
//		canvas.drawRect(rect[2], paint);
    	imagem_nave.Draw(canvas);
	}

}
