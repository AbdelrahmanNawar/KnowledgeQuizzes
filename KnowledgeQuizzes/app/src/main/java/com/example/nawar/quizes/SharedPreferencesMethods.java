package com.example.nawar.quizes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;

class SharedPreferencesMethods {
	
	public static void savePreferences(Context context, String key, int value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	
	public static int loadSavedPreferences(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getInt(key, 0);
	}
	
	
	
	public static void savePreferencesString(Context context, String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	
	@Nullable
	public static String loadSavedPreferencesString(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		return sharedPreferences.getString(key, "");
	}


	
}
