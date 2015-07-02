package com.example.spacecraft_x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class FimJogo extends Activity{
	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_IMAGE_Gallery = 2;
	static final String TAG_INTERNAL_STORAGE = "Top5";
	int score;
	int tempo;
	ImageView imagem;
	Bitmap imagem_bit;
	TextView textscore,texttempo;
	Button cancelar,gravar,Tirarfoto,escolherGaleria;
	CheckBox escolher_imagem;
	EditText nome;
	private String selectedImagePath;
	
	boolean imgcheck=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.fimjogolayout);
		Intent intent = getIntent();
		Bundle params = intent.getExtras();
		score = params.getInt("Score");
		tempo = params.getInt("Tempo");
		nome = (EditText)findViewById(R.id.editTextNome);
		textscore = (TextView)findViewById(R.id.textViewScore);
		texttempo = (TextView)findViewById(R.id.TextViewTempo);
		gravar =  (Button)findViewById(R.id.buttonguardar);
		cancelar =  (Button)findViewById(R.id.buttonCancelar);
		escolher_imagem = (CheckBox)findViewById(R.id.checkBoxImagem);
		Tirarfoto = (Button)findViewById(R.id.buttonTirarFoto);
		escolherGaleria= (Button)findViewById(R.id.buttonEscolherGaleria);
		
		imagem = (ImageView)findViewById(R.id.imageView1);
		imagem_bit = (Bitmap) getLastNonConfigurationInstance();
		if(imagem_bit!=null){ imagem.setImageBitmap(imagem_bit);} 
		if(!imgcheck)
		{
			Tirarfoto.setVisibility(View.INVISIBLE);
			escolherGaleria.setVisibility(View.INVISIBLE);
		}
		textscore.setText(score+" Pontos");
		texttempo.setText(tempo+" Segundos");
		escolher_imagem.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
				{
					imgcheck=isChecked;
					Tirarfoto.setVisibility(View.VISIBLE);
					escolherGaleria.setVisibility(View.VISIBLE);
				}
				else
				{

					imgcheck=isChecked;
					Tirarfoto.setVisibility(View.INVISIBLE);
					escolherGaleria.setVisibility(View.INVISIBLE);
				}
			}
		});
		cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FimJogo.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		Tirarfoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			    }
			    finish();
			}
		});
		escolherGaleria.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_Gallery);
				finish();
			}
		});
		gravar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				try
				{
				File file = getBaseContext().getFileStreamPath(TAG_INTERNAL_STORAGE);
				if(file.exists())
				{
						FileInputStream fis = openFileInput(TAG_INTERNAL_STORAGE);
						if(fis != null)
						{
							ObjectInputStream ins = new ObjectInputStream(fis);
							Top5 top = (Top5)ins.readObject();
							ins.close();
							top.Insere_Classificação(score,tempo,imagem_bit,nome.getText().toString());
							FileOutputStream fos = openFileOutput(TAG_INTERNAL_STORAGE, Context.MODE_PRIVATE);
							ObjectOutputStream os = new ObjectOutputStream(fos);
							os.writeObject(top);
							os.close();
							Intent intent = new Intent(FimJogo.this,MainActivity.class);
							startActivity(intent);
						}
						
				}
				else
				{
					Top5 top = new Top5();
					top.Insere_Classificação(score,tempo,imagem_bit,nome.getText().toString());
					FileOutputStream fos = openFileOutput(TAG_INTERNAL_STORAGE, Context.MODE_PRIVATE);
					ObjectOutputStream os = new ObjectOutputStream(fos);
					os.writeObject(top);
					os.close();
					Intent intent = new Intent(FimJogo.this,MainActivity.class);
					startActivity(intent);
				}
			}
			catch( Exception ex)
			{}
				finish();
			}
		});
	}
	@Override
	public Object onRetainNonConfigurationInstance (){
	     return imagem_bit;
	}
	public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_Gallery && resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                imagem_bit = BitmapFactory.decodeFile(selectedImagePath);
                imagem.setImageBitmap(imagem_bit);
        }
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        imagem_bit = (Bitmap) extras.get("data");
	        imagem.setImageBitmap(imagem_bit);
	    }
	}
	
	protected void onResume() {
		super.onResume();
		Music.play(this, R.raw.main_music);
	}
	
	@Override 
	protected void onPause() {
		super.onPause(); 
		Music.stop(this); 
	}
	

}
