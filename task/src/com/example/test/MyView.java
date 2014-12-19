package com.example.test;

import com.example.animationdemo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
	float radius;// 圆的半径
	private float borderwidth;// 边界宽度
	private int bordercolor;// 边界颜色
	float polygondistance;// 多边形顶点到中心的距离
	private int polygonsides;// 多边形边数
	private int type;// 图形种类，0为圆，1为多边形
	private static final int TYPE_CIRCLE = 0;
	private static final int TYPE_POLYGON = 1;
	private int mWidth;
	private int mHeight;
	private Paint paint;
	private Bitmap mSrc;

	public MyView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyView(Context context) {
		this(context, null , 0);
	}

	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.MyView, defStyleAttr, 0);
		radius = a.getDimension(R.styleable.MyView_radius, 50);
		borderwidth = a.getDimension(R.styleable.MyView_borderwidth, 10);
		polygondistance = a
				.getDimension(R.styleable.MyView_polygondistance, 50);
		polygonsides = a.getInteger(R.styleable.MyView_polygonsides, 3);
		bordercolor = a.getColor(R.styleable.MyView_bordercolor, Color.RED);
		type = a.getInt(R.styleable.MyView_type, 0);// 默认是圆形
		a.recycle();
		mSrc = BitmapFactory.decodeResource(getResources(), R.drawable.hz);
	}

	@Override
	// 该方法还没有弄明白，参考了别人
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/**
		 * 设置宽度
		 */
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);

		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
		{
			mWidth = specSize;
		} else {
			// 由图片决定的宽
			int desireByImg = getPaddingLeft() + getPaddingRight()
					+ mSrc.getWidth();
			if (specMode == MeasureSpec.AT_MOST)// wrap_content
			{
				mWidth = Math.min(desireByImg, specSize);
			} else

				mWidth = desireByImg;
		}

		/***
		 * 设置高度
		 */

		specMode = MeasureSpec.getMode(heightMeasureSpec);
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
		{
			mHeight = specSize;
		} else {
			int desire = getPaddingTop() + getPaddingBottom()
					+ mSrc.getHeight();

			if (specMode == MeasureSpec.AT_MOST)// wrap_content
			{
				mHeight = Math.min(desire, specSize);
			} else
				mHeight = desire;
		}

		setMeasuredDimension(mWidth, mHeight);

	}

	public void onDraw(Canvas canvas) {
		int min = Math.min(mWidth, mHeight);
		mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
		switch (type) {
		case TYPE_CIRCLE:
			canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
			break;
		case TYPE_POLYGON:
			canvas.drawBitmap(createPolygonImage(mSrc ,min), 0, 0,null);
			break;
		}
	}

	 Bitmap createPolygonImage(Bitmap source,int min) {
		paint = new Paint();
		paint.setColor(bordercolor);
		paint.setStrokeWidth(borderwidth);
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		Path path = new Path();
		switch(polygonsides){
		case 3:
			path.moveTo(min/2, 0);
			path.lineTo(0, min);
			path.lineTo(min, min);
			path.close();
			break;
		case 4:
			path.moveTo(min/2, 0);
			path.lineTo(0, min/2);
			path.lineTo(min/2, min);
			path.lineTo(min,min/2);
			path.close();
			break;
		case 5:
			path.moveTo(min/2, 0);
			path.lineTo(0, min/2);
			path.lineTo(min/4, min);
			path.lineTo(3*min/4, min);
			path.lineTo(min, min/2);
			path.close();
			break;
		case 6:
			path.moveTo(min/2, 0);
			path.lineTo(0, min/3);
			path.lineTo(0, 2*min/3);
			path.lineTo(min/2, min);
			path.lineTo(min, 2*min/3);
			path.lineTo(min, min/3);
			path.close();
			break;
		}
		canvas.drawPath(path, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawPath(path, paint);
		return target;
	}

	private Bitmap createCircleImage(Bitmap source, int min) {
		// TODO Auto-generated method stub
		paint = new Paint();
		paint.setColor(bordercolor);
		paint.setStrokeWidth(borderwidth);
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		canvas.drawCircle(min / 2, min / 2, min / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(min / 2, min / 2, min / 2, paint);
		return target;
	}


}
