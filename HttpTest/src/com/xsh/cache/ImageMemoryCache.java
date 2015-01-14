package com.xsh.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageMemoryCache {
	private static LruCache<String, Bitmap> mLruCache; // 硬引用缓存

	public ImageMemoryCache(Context context) {
		int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 4; // 硬引用缓存容量，为系统可用内存的1/4
		mLruCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				if (value != null)
					return value.getRowBytes() * value.getHeight();
				else
					return 0;
			}
		};		
	}

	// 从缓存中读取图片
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap = null;
		// 先从硬引用缓存中获取
		synchronized (mLruCache) {
			bitmap = mLruCache.get(url);
			if (bitmap != null) {
				// 如果找到的话，把元素移到最前面，从而保证在LRU算法中是最后被删除
				mLruCache.remove(url);
				mLruCache.put(url, bitmap);
				return bitmap;
			}
		}
		return bitmap;
	}

	// 添加图片到缓存
	public void addBitmapToCache( Bitmap bitmap,String url) {
		if (bitmap != null) {
			synchronized (mLruCache) {
				mLruCache.put(url, bitmap);
			}
		}
	}
}
