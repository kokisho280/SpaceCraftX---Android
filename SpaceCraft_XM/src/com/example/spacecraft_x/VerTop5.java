package com.example.spacecraft_x;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.jar.Attributes;

import com.example.framework.Image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class VerTop5 extends Activity
{
	static final String TAG_INTERNAL_STORAGE = "Top5";
	LinearLayout layoutprincipal;
	LinearLayout botoes,posicao;
	Top5 top;
	TextView titulo,inf;
	Bitmap imagem;
	Button Apagar, Voltar;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		imagem = BitmapFactory.decodeResource(getResources(),R.drawable.box);
		Attributes attrs = new Attributes();
		layoutprincipal = new LinearLayout(this);
		layoutprincipal.setOrientation(LinearLayout.VERTICAL);
	    LayoutParams LLParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
	    //layoutprincipal.setWeightSum(6f);
	    layoutprincipal.setLayoutParams(LLParams);
	    LLParams.height= LayoutParams.WRAP_CONTENT;
	    titulo= new TextView(this);
		titulo.setText("Top5");
		titulo.setLayoutParams(LLParams);
		titulo.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
		titulo.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
		layoutprincipal.addView(titulo);
	    File file = getBaseContext().getFileStreamPath(TAG_INTERNAL_STORAGE);
		if(file.exists())
		{
			top = this.carrega_top();
		}
		if(top == null || top.getSize() == 0)
		{ 
			inf = new TextView(this);
			inf.setText("Não existem classificações disponiveis");
			inf.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
			inf.setGravity(Gravity.CENTER);
			posicao = new LinearLayout(this);
			posicao.setOrientation(LinearLayout.HORIZONTAL);
			LayoutParams paramh = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1f);
			posicao.setLayoutParams(paramh);
			posicao.addView(inf);
			posicao.setGravity(Gravity.CENTER);
			layoutprincipal.addView(posicao);
		}
		else
		{
			for(int i = 0; i<top.getSize();i++)
			{
				posicao = new LinearLayout(this);
				posicao.setOrientation(LinearLayout.HORIZONTAL);
				LayoutParams paramh = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1f);
				posicao.setLayoutParams(paramh);
				LinearLayout inf_player = new LinearLayout(this);
				inf_player.setOrientation(LinearLayout.VERTICAL);
				inf_player.setLayoutParams(paramh);
				TextView nome = new TextView(this);
				nome.setText("Jogador :"+top.get_Classificacao(i).getNome());
				nome.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
				nome.setGravity(Gravity.CENTER_HORIZONTAL);
				TextView informacaoP = new TextView(this);
				informacaoP.setText(top.get_Classificacao(i).tempo+" segundos "+top.get_Classificacao(i).moedas+" moedas");
				informacaoP.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
				informacaoP.setGravity(Gravity.CENTER_HORIZONTAL);
				TextView pontos = new TextView(this);
				pontos.setText(top.get_Classificacao(i).pontuacao+" Pontos");
				pontos.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
				pontos.setGravity(Gravity.CENTER_HORIZONTAL);
				paramh = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,0.6f);
				ImageView imagem_user = new ImageView(this);
				if(top.get_Classificacao(i).getImagem()!= null)
				{imagem_user.setImageBitmap(top.get_Classificacao(i).getImagem());}
				else
				{
				imagem_user.setImageBitmap(imagem);
				}
				imagem_user.setLayoutParams(paramh);
				inf_player.addView(nome);
				inf_player.addView(informacaoP);
				inf_player.addView(pontos);
				inf_player.setGravity(Gravity.CENTER);
				posicao.addView(inf_player);
				posicao.addView(imagem_user);
				layoutprincipal.addView(posicao);
			}
		}
		Apagar = new Button(this);
		Apagar.setText("Apagar Top");
		Voltar = new Button(this);
		Voltar.setText("Voltar");
		Apagar.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
		Voltar.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
		botoes = new LinearLayout(this);
		botoes.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams paramh = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		botoes.setLayoutParams(paramh);
		paramh = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,0.5f);
		Voltar.setLayoutParams(paramh);
		Apagar.setLayoutParams(paramh);
		botoes.addView(Apagar);
		botoes.addView(Voltar);
		layoutprincipal.addView(botoes);
		this.setContentView(layoutprincipal);
		Voltar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(VerTop5.this,MainActivity.class);
				startActivity(intent);				
			}
		});
		Apagar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				File file = getBaseContext().getFileStreamPath(TAG_INTERNAL_STORAGE);
				if(file.exists())
				{
					file.delete();
					top.top.clear();
				}
				posicao.removeAllViews();
				setContentView(layoutprincipal);
			}
		});
	} 
	public Top5 carrega_top()
	{
		try
		{
		
				FileInputStream fis = openFileInput(TAG_INTERNAL_STORAGE);
				if(fis != null)
				{
					ObjectInputStream ins = new ObjectInputStream(fis);
					Top5 topx = (Top5)ins.readObject();
					ins.close();
					return topx;
				}
				return null;
		}
		catch(Exception e)
		{
			return null;
		}
	}

}
