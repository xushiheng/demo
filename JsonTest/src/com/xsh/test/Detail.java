package com.xsh.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsh.cache.ImageFileCache;
import com.xsh.cache.ImageMemoryCache;

public class Detail extends Activity {
	private com.xsh.cache.ImageFileCache fileCache;
	private com.xsh.cache.ImageMemoryCache memoryCache;
	private ImageView user_logo;
	private Bitmap bitmap;
	private TextView user_name;
	private TextView content;
	private TextView cn_name;
	private TextView date;
	private TextView overall_rating;
	//不能在子线程中对UI进行操作，使用handler传递msg
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				user_logo.setImageBitmap(bitmap);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		fileCache = new ImageFileCache();
		memoryCache = new ImageMemoryCache(this);
		user_logo = (ImageView) findViewById(R.id.logo);
		user_name = (TextView) findViewById(R.id.user_name);
		content = (TextView) findViewById(R.id.content);
		cn_name = (TextView) findViewById(R.id.cn_name);
		date = (TextView) findViewById(R.id.date);
		overall_rating = (TextView) findViewById(R.id.overall_rating);
		JsonBean jsonBean = MainActivity.getJsonBean();
		Intent intent = getIntent();
		int position = intent.getIntExtra("position", 0);
		user_name.setText(jsonBean.data.comment.get(position).user_name);
		content.setText(jsonBean.data.comment.get(position).content);
		cn_name.setText(jsonBean.data.comment.get(position).cn_name);
		date.setText(jsonBean.data.comment.get(position).date);
		overall_rating
				.setText(jsonBean.data.comment.get(position).overall_rating);
		final String url = jsonBean.data.comment.get(position).user_logo;
		//开子线程加载图片
		new Thread() {
			public void run() {
				bitmap = getBitmap(url);
				handler.sendEmptyMessage(1);
			}
		}.start();
	}

	public Bitmap getBitmap(String url) {
		//先从内存缓存中找
		Bitmap bitmap = memoryCache.getBitmapFromCache(url);
		if (bitmap == null) {
			//没有则从文件缓存中找
			bitmap = fileCache.getImage(url);
			if (bitmap == null) {
				//最后从网络下载图片
				bitmap = com.xsh.cache.ImageGetFromHttp.downloadBitmap(url);
				if (bitmap != null) {
					//存入缓存
					fileCache.saveBitmap(bitmap, url);
					memoryCache.addBitmapToCache(bitmap, url);
				}
			} else {	
				memoryCache.addBitmapToCache(bitmap, url);
			}
		}
		return bitmap;
	}
}
