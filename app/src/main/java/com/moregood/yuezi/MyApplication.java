
package com.moregood.yuezi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyApplication extends Application {
	int windowWidth,windowHeight;
	
	boolean debuggable  = false;
	
	public boolean isDebuggable() {
		return debuggable;
	}
	public void setDebuggable(boolean debuggable) {
		this.debuggable = debuggable;
	}
	public void setWinSize(int width, int height) {
		windowWidth = width;
		windowHeight = height;
	}
	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	
	private static MyApplication instance = null;
	

	public static MyApplication getInstance() {
		if (instance == null) {
			instance = new MyApplication();
		}
		return instance;
	}
	

	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public Bitmap getBitmap(int resId) {
		return BitmapFactory.decodeResource(getResources(), resId);
	}
	
	public Bitmap createBitmapFromAssets(String assetBitamp) {
		try {
			InputStream stream = MyApplication.getInstance().getResources().getAssets().open(assetBitamp);
			return BitmapFactory.decodeStream(stream);
		} catch (OutOfMemoryError ooe) {
			ooe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
