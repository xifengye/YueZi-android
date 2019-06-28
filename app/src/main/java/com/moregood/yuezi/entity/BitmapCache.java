package com.moregood.yuezi.entity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;

import com.moregood.yuezi.MyApplication;
import com.moregood.yuezi.utils.BitmapUtil;

public class BitmapCache {

	static private BitmapCache cache;

	private Hashtable<String, MySoftRef> hashRefs;

	private ReferenceQueue<Bitmap> q;

	private class MySoftRef extends SoftReference<Bitmap> {

		private String _key = "";

		public MySoftRef(Bitmap bmp, ReferenceQueue<Bitmap> q, String key) {

			super(bmp, q);

			_key = key;

		}

	}

	private BitmapCache() {

		hashRefs = new Hashtable<String, MySoftRef>();

		q = new ReferenceQueue<Bitmap>();

	}

	public static BitmapCache getInstance() {

		if (cache == null) {

			cache = new BitmapCache();

		}

		return cache;

	}

	private void addCacheBitmap(Bitmap bmp, String key) {

		cleanCache();

		MySoftRef ref = new MySoftRef(bmp, q, key);

		hashRefs.put(key, ref);

	}

	public Bitmap getBitmap(String id, Context context) {

		Bitmap bmp = null;
		Bitmap bitmap = null;

		if (hashRefs.containsKey(id)) {

			MySoftRef ref = (MySoftRef) hashRefs.get(id);
			if (ref == null)
				hashRefs.remove(id);
			else
				bmp = (Bitmap) ref.get();

		}

		if (bmp == null) {
			bmp = MyApplication.getInstance().createBitmapFromAssets(id);
			if(bmp!=null){
				int radius = (int) ((float) bmp.getWidth() * 0.125f);
				bitmap = BitmapUtil.getRoundedCornerBitmap(bmp, radius, radius, radius,
						radius);
				this.addCacheBitmap(bitmap, id);
			}
		}
		if(bitmap!=null){
			return bitmap;
		}else{
			return bmp;
		}

	}

	private void cleanCache() {

		MySoftRef ref = null;

		while ((ref = (MySoftRef) q.poll()) != null) {

			hashRefs.remove(ref._key);

		}

	}

	public void clearCache() {

		cleanCache();

		hashRefs.clear();

		System.gc();

		System.runFinalization();

	}

}
