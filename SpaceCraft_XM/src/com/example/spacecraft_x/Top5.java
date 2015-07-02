package com.example.spacecraft_x;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class Top5  implements Serializable
{
	ArrayList<Classificacao> top;
	public Top5()
	{
		top = new ArrayList<Classificacao>();
	}
	public Classificacao get_Classificacao(int index)
	{
		return top.get(index);
	}
	public void LimpaClassificacao()
	{
		top.clear();
	}

	public int getSize()
	{
		return top.size();
	}
	public void Insere_Classificação(int _moedas,int _tempo,Bitmap _imagem,String _nome)
	{
		boolean inserido;
		Classificacao aux = new Classificacao(_moedas, _tempo, _imagem, _nome);
		ArrayList<Classificacao> temp = new ArrayList<Classificacao>();
		if(top.size() == 0)
		{
			top.add(aux);
			return;
		}
		else
		{
			for(int i = 0; i< top.size(); i++)
			{
				if(i == 4)
				{
					if(top.get(i).getPontuacao() < aux.getPontuacao())
					{
						top.remove(i);
						top.add(aux);
						return;
					}
					return;
				}
				else
				{
					if(top.get(i).getPontuacao() < aux.getPontuacao())
					{
						temp.add(aux);
						for(int x = i; x < top.size(); x++)
						{
							temp.add(top.get(x));
							if(temp.size() == 5)
							{
								top = temp;
								return;
							}
						}
						top = temp;
						return;	
					}
				}
				temp.add(top.get(i));
			}
			temp.add(aux);
			top = temp;
			return;
		}
	}
	
}
class Classificacao implements Serializable
{
	int tempo,moedas;
	int pontuacao;
	String nome;
	boolean temimagem;
	public boolean isTemimagem() {
		return temimagem;
	}
	public void setTemimagem(boolean temimagem) {
		this.temimagem = temimagem;
	}
	byte[] imagem;
	public Classificacao(int _moedas,int _tempo,Bitmap _imagem,String _nome)
	{
		this.setTempo(_tempo);
		this.setMoedas(_moedas);
		this.setImagem(_imagem);
		this.setNome(_nome);
		this.setPontuacao(_tempo*(_moedas+1));
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public int getMoedas() {
		return moedas;
	}
	public void setMoedas(int moedas) {
		this.moedas = moedas;
	}
	public int getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Bitmap getImagem() {
		if(isTemimagem())
		{
			Bitmap aux = BitmapFactory.decodeByteArray(imagem , 0, imagem.length);
			return aux;
		}
		else
		{
			return null;
		}
	}
	public void setImagem(Bitmap imagem) {
		if(imagem != null)
		{
			this.setTemimagem(true);
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 imagem.compress(CompressFormat.PNG, 0, bos);
		 this.imagem = bos.toByteArray();
		}
		else
		{
			this.setTemimagem(false);
		}
		
	}
}
