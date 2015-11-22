package com.mygdx.hanoi.util;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/*
 * Class ini digunakan untuk menyimpan data ke dalam database (Persistance)
 */
public class DataPersister {
	
	/*public DataPersister(){
		init();
	}*/
	
	public Preferences getOrCreatePreferences(String prefName){
		return Gdx.app.getPreferences(prefName + ".prefs");
	}
	public Map getPreferencesData(String prefName){
		Preferences prefs = Gdx.app.getPreferences(prefName + ".prefs");
		return (Map) prefs.get(); //.get(key);
	}
	public void clearPreferences(String prefName){
		Preferences prefs = Gdx.app.getPreferences(prefName + ".prefs");
		prefs.clear();
	}
	public void insertPreferences(String prefName, Map data){
		Preferences prefs = Gdx.app.getPreferences(prefName + ".prefs");
		prefs.put(data);
		prefs.flush();
	}
	public void getAndSortPreferences(String prefName, String sortKey, String sort){
		String ascending = "asc";
		String descending = "desc";
		
		if(sort.equals(ascending)){
			
		}
		else{
			
		}
	}
}
