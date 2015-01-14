package com.xsh.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xsh.cache.ImageFileCache;

public class DetailActivity extends Activity{
	private MyView userLogo;
	private TextView userName;
	private TextView content;
	private TextView date;
	private TextView cnName;
	private RatingBar overallRating;
	private int position;
	private ImageFileCache fileCache;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        userLogo = (MyView) findViewById(R.id.myView2);
        userName = (TextView) findViewById(R.id.userName);
        content = (TextView) findViewById(R.id.content);
        date = (TextView) findViewById(R.id.date);
        cnName = (TextView) findViewById(R.id.cnName);
        overallRating = (RatingBar) findViewById(R.id.overallRating);
        fileCache = new ImageFileCache();
        loadLayout();
  }

	private void loadLayout() {
		// TODO Auto-generated method stub
		userName.setText(MainActivity.jsonBean.data.comment.get(position).userName);
		content.setText(MainActivity.jsonBean.data.comment.get(position).content);
		date.setText(MainActivity.jsonBean.data.comment.get(position).date);
		cnName.setText(MainActivity.jsonBean.data.comment.get(position).cnName);
		String a = MainActivity.jsonBean.data.comment.get(position).overallRating;
		float rating = Float.parseFloat(a);
		overallRating.setRating(rating);
		String url = MainActivity.jsonBean.data.comment.get(position).userLogo;
		userLogo.setBitmap(fileCache.getImage(url));
		userLogo.invalidate();
	}	
}
