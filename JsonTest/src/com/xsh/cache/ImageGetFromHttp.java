package com.xsh.cache;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageGetFromHttp {
	
	public static Bitmap downloadBitmap(String url) {
		URL mUrl;
		Bitmap bitmap = null;
		try {
			mUrl = new URL(url);
			//取得连接
			HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
			conn.setConnectTimeout(6000);//设置超时
			conn.setDoInput(true);
			conn.setUseCaches(true);//使用缓存
			InputStream is = conn.getInputStream();//获得图片数据流
			bitmap = BitmapFactory.decodeStream(is);//转化为bitmap
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;		
	}	
}
