package com.xsh.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View{
	private float radius;
	private float borderwidth;
	private int bordercolor;
	private Paint paint;
	private Bitmap mSrc;

	public MyView(Context context , AttributeSet attrs){
		this(context , attrs , 0);
	}
	
	public MyView(Context context){
		this(context , null , 0);
	}

	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.MyView, defStyleAttr, 0);
		radius = a.getDimension(R.styleable.MyView_radius, 50);
		borderwidth = a.getDimension(R.styleable.MyView_borderwidth, 0);
		bordercolor = a.getColor(R.styleable.MyView_bordercolor, Color.RED);		
		a.recycle();
	}
	public void setBitmap(Bitmap bitmap){
		mSrc = bitmap;
	}

	public void onDraw(Canvas canvas) {
		if(mSrc == null){
			mSrc = BitmapFactory.decodeResource(getResources(), R.drawable.hz);
		}
		mSrc = Bitmap.createScaledBitmap(mSrc, (int) (2*radius), (int) (2*radius), false);
		canvas.drawBitmap(createCircleImage(mSrc), 0, 0, null);
		
	}

	private Bitmap createCircleImage(Bitmap mSrc2) {
		// TODO Auto-generated method stub
		paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap((int) (2*radius), (int) (2*radius), Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		canvas.drawCircle(radius, radius, radius, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(mSrc2, 0, 0, paint);
		paint.setColor(bordercolor);
		paint.setStrokeWidth(borderwidth);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(radius, radius, radius, paint);
		return target;
	}

}
