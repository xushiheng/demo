package com.xsh.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class HttpUtil implements Runnable{
	public static class URLS{
		public static final String URL_TEST = "http://byandroid.sinaapp.com/";
	}
	public static final String TAG = HttpUtil.class.getSimpleName();
	
	//用于区分Handler里的类型
	public static final int DID_START = 0;
	public static final int DID_ERROR = 1;
	public static final int GET_SUCCEED = 2;
	public static final int BITMAP_SUCCEED = 3;
	//method类型
	private static final int GET = 0;
	private static final int BITMAP = 1;
/*
    private static final int POST = 2;
	private static final int PUT = 3;
	private static final int DELETE = 4;
*/
	private String url;
	private int method;
	private Handler handler;
	
	private HttpClient httpClient;
	
	public HttpUtil(){
		this(new Handler());
	}
	public HttpUtil(Handler handler){
		this.handler = handler;
	}
	
	public void create(int method,String url){
		this.method = method;
		this.url = url;
		ThreadPoolUtils.getInstance().execute(this);
	}
	
	public void getJson(String url){
		create(GET, url);
	}
	
	public void getBitmap(String url){
		create(BITMAP,url);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		handler.sendMessage(Message.obtain(handler, DID_START));
		httpClient = new DefaultHttpClient();
		//设置超时时间
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);
		try{
			HttpResponse response = null ;
			switch (method) {
			case GET:
				 response = httpClient.execute(new HttpGet(url));
				 processEntity(response.getEntity());
				break;
			
			case BITMAP:
				response = httpClient.execute(new HttpGet(url));
				processBitmapEntity(response.getEntity());
				break;
				
			default:
				break;
			}
		}catch(Exception e){
			handler.sendMessage(Message.obtain(handler,DID_ERROR,e));
		}
		
	}
	private void processEntity(HttpEntity entity) throws IOException {
		// TODO Auto-generated method stub
		byte[] data = EntityUtils.toByteArray(entity);
		String result = new String(data);
		handler.sendMessage(Message.obtain(handler,GET_SUCCEED,result));
	}
	private void processBitmapEntity(HttpEntity entity) throws IOException {
		// TODO Auto-generated method stub
		BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
		Bitmap bm = BitmapFactory.decodeStream(bufHttpEntity.getContent());
		handler.sendMessage(Message.obtain(handler, BITMAP_SUCCEED, bm));		
	}

}
