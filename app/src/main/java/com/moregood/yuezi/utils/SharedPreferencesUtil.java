package com.moregood.yuezi.utils;



import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.moregood.yuezi.MyApplication;


/**
 * @author yexifeng
 *
 */
public class SharedPreferencesUtil {

	private SharedPreferences sp;
	private Editor editor;
	private String name = "com.moregood.yuezi";


	private static SharedPreferencesUtil instance = null;

	public static SharedPreferencesUtil getInstance(){
		if(instance==null){
			instance  = new SharedPreferencesUtil(MyApplication.getInstance());
		}
		return instance;
	}

	public SharedPreferencesUtil(Context context) {
		this.sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		this.editor = sp.edit();
	}


	/**
	 * 添加信息到SharedPreferences
	 *
	 * @param map
	 * @throws Exception
	 */
	public SharedPreferencesUtil put(Map<String, String> map) {
		Set<String> set = map.keySet();
		for (String key : set) {
			editor.putString(key, map.get(key));
		}
		editor.commit();
		return this;
	}

	public SharedPreferencesUtil put(String key, String value) {
		editor.putString(key, value);
		editor.commit();
		return this;
	}
	public SharedPreferencesUtil put(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
		return this;
	}

	public SharedPreferencesUtil put(String key,long value){
		editor.putLong(key, value);
		editor.commit();
		return this;
	}
	public SharedPreferencesUtil put(String key,int value){
		editor.putInt(key, value);
		editor.commit();
		return this;
	}

	/**
	 * 删除信息
	 *
	 * @throws Exception
	 */
	public void removeAll() throws Exception {
		editor.clear();
		editor.commit();
	}

	/**
	 * 删除一条信息
	 */
	public void remove(String key){
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 获取信息
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getString(String key){
		if (sp != null) {
			return sp.getString(key, "");
		}
		return "";
	}
	public long getLong(String key){
		if (sp != null) {
			return sp.getLong(key,0l);
		}
		return 0l;
	}
	public int getInt(String key){
		if (sp != null) {
			return sp.getInt(key,0);
		}
		return 0;
	}
	public boolean getBoolean(String key){
		if (sp != null) {
			return sp.getBoolean(key,false);
		}
		return false;
	}



	/**
	 * 获取此SharedPreferences的Editor实例
	 * @return
	 */
	public Editor getEditor() {
		return editor;
	}

}

