package com.example.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Callback,Runnable{
	Bitmap[] bitmap = {
			BitmapFactory.decodeResource(getResources(), R.drawable.z1),
			BitmapFactory.decodeResource(getResources(), R.drawable.z2),
			BitmapFactory.decodeResource(getResources(), R.drawable.z3),
			BitmapFactory.decodeResource(getResources(), R.drawable.z4),
			BitmapFactory.decodeResource(getResources(), R.drawable.z5),
			BitmapFactory.decodeResource(getResources(), R.drawable.z6),
			BitmapFactory.decodeResource(getResources(), R.drawable.z7),
			BitmapFactory.decodeResource(getResources(), R.drawable.z8)};
	SurfaceHolder surfaceHolder;
	private boolean isThreadRunning = true;
	Canvas canvas;
	int i = 0;

	public MySurfaceView(Context context) {
		super(context);
		surfaceHolder = this.getHolder();
		surfaceHolder.addCallback(this);// 回调
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		new Thread(this).start();//启动线程
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isThreadRunning = false;//surfaceView销毁时停止线程运行
		try {//防止退出时报错，让线程暂停300ms醒来后执行run方法时会变成false
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void drawView() {
		try {// isThreadRunning变为false后还会再执行一次drawView,要进行判断
			if (surfaceHolder != null) {

				//在surface创建后锁定画布
				canvas = surfaceHolder.lockCanvas();
				canvas.drawColor(Color.BLACK);//清空画布
				if(i > 7){
					i = 0;
				}
				canvas.drawBitmap(bitmap[i], 0, 0,null);
				i ++;
				//画图
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//surfaceView销毁时，canvas将不存在
			if (canvas != null)
				//将画布解锁并显示在屏幕上
				surfaceHolder.unlockCanvasAndPost(canvas);
		}

	}

	public void run() {
		// 每隔100ms刷新一次
		while (isThreadRunning) {
			drawView();
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}