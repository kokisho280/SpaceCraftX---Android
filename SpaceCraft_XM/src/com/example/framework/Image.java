package com.example.framework;

/*
 *  [Image.java]  - Classe destinada para exibição de imagens na tela do jogo
 * 
 *  Desenvolvida por : Luciano Alves da Silva
 *  e-mail : lucianopascal@yahoo.com.br
 *  site : http://www.gameutil2d.org
 *  
 */



import com.example.spacecraft_x.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Image extends GameElement {
	private Bitmap imagem,imagem_scaled;
	Context context;
	
	
	
	
	
    public Image(Context context, int id_image, int x, int y, int width, int height) {
		
    	this.context = context;
    	imagem = BitmapFactory.decodeResource(context.getResources(),id_image);
    	
     	Matrix mirrorMatrix = new Matrix();
     	
        mirrorMatrix.preScale(-1.0f, 1.0f);
        imagem = Bitmap.createScaledBitmap(imagem, width, height,false);
        imagem_scaled = Bitmap.createBitmap(imagem, 0, 0, imagem.getWidth(), imagem.getHeight(), mirrorMatrix, false);
        
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}    
   
    public void Draw(Canvas canvas) {
    	Drawable img = new BitmapDrawable(context.getResources(),imagem);
    	img.setBounds(this.x,this.y,this.width + this.x, this.height + this.y);
    	img.draw(canvas);
    }
    public void Drawalpha(Canvas canvas,int alpha) {
    	Drawable img = new BitmapDrawable(context.getResources(),imagem);
    	img.setBounds(this.x,this.y,this.width + this.x, this.height + this.y);
    	img.setAlpha(alpha);
    	img.draw(canvas);
    }
}
