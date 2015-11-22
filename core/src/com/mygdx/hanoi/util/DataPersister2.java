package com.mygdx.hanoi.util;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/*
 * Class ini digunakan untuk menyimpan data ke dalam database (Persistance)
 */
public class DataPersister2 {
	
	/*public DataPersister(){
		init();
	}*/
	
	public Preferences getOrCreatePreferences(String prefName){
		return Gdx.app.getPreferences(prefName + ".prefs");
	}
	public Map getPreferencesData(Preferences prefName){
		return (Map) prefName.get(); //.get(key);
	}
	public void clearPreferences(Preferences prefName){
		prefName.clear();
	}
	public void insertPreferences(Preferences prefName, Map data){
		prefName.put(data);
		prefName.flush();
	}
	public void getAndSortPreferences(String prefName, String sortKey, String sort){
		final String ascending = "asc";
		final String descending = "desc";
		
		if(sort.equals(ascending)){
			
		}
		else{
			
		}
	}
}
