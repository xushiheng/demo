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
		surfaceHolder.addCallback(this);// �ص�
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		new Thread(this).start();//�����߳�
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isThreadRunning = false;//surfaceView����ʱֹͣ�߳�����
		try {//��ֹ�˳�ʱ�������߳���ͣ300ms������ִ��run����ʱ����false
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void drawView() {
		try {// isThreadRunning��Ϊfalse�󻹻���ִ��һ��drawView,Ҫ�����ж�
			if (surfaceHolder != null) {

				//��surface��������������
				canvas = surfaceHolder.lockCanvas();
				canvas.drawColor(Color.BLACK);//��ջ���
				if(i > 7){
					i = 0;
				}
				canvas.drawBitmap(bitmap[i], 0, 0,null);
				i ++;
				//��ͼ
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//surfaceView����ʱ��canvas��������
			if (canvas != null)
				//��������������ʾ����Ļ��
				surfaceHolder.unlockCanvasAndPost(canvas);
		}

	}

	public void run() {
		// ÿ��100msˢ��һ��
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