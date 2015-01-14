package com.xsh.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class HttpHandler extends Handler {

	private Context context;
	private ProgressDialog progressDialog;

	public HttpHandler(Context context) {
		this.context = context;
	}
	public HttpHandler(){
		
	}
	protected void start() {
		progressDialog = ProgressDialog.show(context, "Please Wait...",
				"Processing...", true);
	}
	protected void succeed(String json){
		if(progressDialog!=null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
	protected void succeed(Bitmap bitmap){

	}
	public void handleMessage(Message message){
		switch (message.what) {
		case HttpUtil.DID_START:
			start();
			break;
		case HttpUtil.GET_SUCCEED:
			progressDialog.dismiss();
			String response = (String) message.obj;
			if(response != null){
				succeed(response);
			}else{
				Toast.makeText(context, "response is null", Toast.LENGTH_SHORT).show();
			}
			break;
		case HttpUtil.BITMAP_SUCCEED:
			Bitmap bm = (Bitmap) message.obj;
			if(bm != null){
				succeed(bm);
			}else{
				Toast.makeText(context, "bitmap is null", Toast.LENGTH_SHORT).show();
			}
			break;
		case HttpUtil.DID_ERROR:
			if(progressDialog!=null && progressDialog.isShowing()){
				progressDialog.dismiss();
			}
			Exception e = (Exception) message.obj;
			e.printStackTrace();
			Toast.makeText(context, "connection fail,please check connection!", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
