package com.xsh.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.xsh.test.MyView;
import com.xsh.cache.*;

public class AsyncImageUtil {
	private ImageFileCache fileCache;
	private ImageMemoryCache memoryCache;
	public Context context;

	public AsyncImageUtil(Context context) {
		this.context = context;
		fileCache = new ImageFileCache();
		memoryCache = new ImageMemoryCache(context);
	}
	public AsyncImageUtil(){
		
	}
	public void getBitmap(String url, MyView myView) {
		// 先从内存缓存中找
		Bitmap bitmap = memoryCache.getBitmapFromCache(url);
		if (bitmap == null) {
			// 没有则从文件缓存中找
			bitmap = fileCache.getImage(url);
			if (bitmap == null) {
				// 最后从网络下载图片
				loadImage(url, myView);
			} else {
				memoryCache.addBitmapToCache(bitmap, url);
				myView.setBitmap(bitmap);
				myView.invalidate();
			}			
		}else{
			myView.setBitmap(bitmap);
			myView.invalidate();
		}

	}

	public void loadImage(String url, MyView myView) {
		BitmapDownloaderTask task = new BitmapDownloaderTask(myView);
		task.execute(url);
	}

	public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

		private MyView myView;
		public String url;

		public BitmapDownloaderTask(MyView myView) {
			this.myView = myView;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap mDownLoadBitmap = null;
			url = params[0];
			URL imageUrl = null;
			try {
				imageUrl = new URL(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				HttpURLConnection urlConn = (HttpURLConnection) imageUrl
						.openConnection();
				urlConn.setDoInput(true);
				urlConn.connect();
				InputStream is = urlConn.getInputStream();
				mDownLoadBitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mDownLoadBitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}
			myView.setBitmap(bitmap);
			myView.invalidate();
			fileCache.saveBitmap(bitmap, url);
			memoryCache.addBitmapToCache(bitmap, url);
		}
	}

}
