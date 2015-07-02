package com.example.spacecraft_x;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Prefs extends PreferenceActivity { 
	
	// Option names and default values 
	private static final String OPT_MUSIC = "music" ; 
	private static final boolean OPT_MUSIC_DEF = true; 
	private static final String OPT_SENSOR = "sensor" ; 
	private static final boolean OPT_SENSOR_DEF = true;
	private static final String OPT_TECLAS = "teclas";
	private static final boolean OPT_TECLAS_DEF = true;
	private static final String OPT_NAVE = "Nave";
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		addPreferencesFromResource(R.xml.settings);

	}
/** Get the current value of the music option */ 
	public static boolean getMusic(Context context) 
	{
		return PreferenceManager.getDefaultSharedPreferences(context) 
				.getBoolean(OPT_MUSIC, OPT_MUSIC_DEF); 
	}
	public static boolean getSensor(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context) 
				.getBoolean(OPT_SENSOR,OPT_SENSOR_DEF); 
	}
	public static boolean getTeclas(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context) 
				.getBoolean(OPT_TECLAS,OPT_TECLAS_DEF); 
	}
	
}
