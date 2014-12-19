package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.example.animationdemo.R;

public class MainActivity extends Activity {
	Button rotateButton;
	Button scaleButton;
	Button alphaButton;
	Button translateButton;
	Button combineButton;
	MyView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		rotateButton = (Button) findViewById(R.id.rotateButton);
		scaleButton = (Button) findViewById(R.id.scaleButton);
		alphaButton = (Button) findViewById(R.id.alphaButton);
		translateButton = (Button) findViewById(R.id.translateButton);
		combineButton =(Button) findViewById(R.id.combineButton);
		image = (MyView) findViewById(R.id.myView);
		rotateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AnimationSet animationset = new AnimationSet(true);
				RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				rotateAnimation.setDuration(2000);
				
				animationset.addAnimation(rotateAnimation);
				animationset.setFillAfter(true);
				image.startAnimation(animationset);
			}
		});
		scaleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AnimationSet animationset = new AnimationSet(true);
				ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0, 1.0f,
						0, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				scaleAnimation.setDuration(2000);
				
				animationset.addAnimation(scaleAnimation);
				animationset.setFillAfter(true);
				image.startAnimation(animationset);

			}
		});
		alphaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AnimationSet animationset = new AnimationSet(true);
				AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
				alphaAnimation.setDuration(2000);
				
				animationset.addAnimation(alphaAnimation);
				animationset.setFillAfter(true);
				image.startAnimation(animationset);
			}
		});
		translateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AnimationSet animationset = new AnimationSet(true);
				TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
				translateAnimation.setDuration(2000);
				animationset.addAnimation(translateAnimation);
				animationset.setFillAfter(true);
				image.startAnimation(animationset);
				
			}
		});
		combineButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AnimationUtils();
				// TODO Auto-generated method stub
				Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.ani);
				animation.setFillAfter(true);
				image.startAnimation(animation);
			}
		});
	}
}
