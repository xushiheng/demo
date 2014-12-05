package com.test.coursemgr;

import com.test.db.CosManager;
import com.test.db.StuManager;
import com.test.db.TchManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CourseDetail extends Activity{

	private final static String TAG = "CoursDetail";
	private String cName;
	private int sourceId;
	private int mId;
	private Cursor cursor;
	private TextView cosName;
	private TextView tName;
	private TextView place;
	private TextView time;
	private TextView intro;
	private Button showComment;
	private Button select;
	private StuManager mStuMgr;
	private TchManager mTchMgr;
	
	private CosManager mCos;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.course_detail);
		
		Intent intent = getIntent();
		this.cName = intent.getStringExtra("name");
		this.sourceId = intent.getIntExtra("sourceId", 0);
		this.mId = intent.getIntExtra("id", 0);
		
		mStuMgr = new StuManager(this, mId);
		mTchMgr = new TchManager(this, mId);
		
		mCos = new CosManager(this);
		cursor = mCos.showDetails(cName);
		
		cosName = (TextView)findViewById(R.id.all_cos_name);
		tName = (TextView)findViewById(R.id.all_cos_tch_name);
		place = (TextView)findViewById(R.id.all_cos_place);
		time = (TextView)findViewById(R.id.all_cos_time);
		intro = (TextView)findViewById(R.id.all_cos_intro);
		
		showComment = (Button)findViewById(R.id.cos_show_comment);
		select = (Button)findViewById(R.id.cos_select);
		
		initWidget();
		
		showComment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CourseDetail.this,ShowComment.class);
				Bundle bundle = new Bundle();
				bundle.putInt("sourceId", sourceId);
				bundle.putString("cosName", cName);
				bundle.putInt("id", mId);
				intent.putExtras(bundle);
				CourseDetail.this.startActivity(intent);
			}
		});
		
		select.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(sourceId)
				{
				case 1:
					
					break;
				case 2:
					mStuMgr.AddToStu(mId, mStuMgr.getName(), mStuMgr.getCosId(cName), 0);
					Toast.makeText(CourseDetail.this, "选课成功", 5000).show();
					break;
				case 3:
					//show student 
					Intent intent = new Intent(CourseDetail.this, ShowStudent.class);
					Bundle bundle = new Bundle();
					bundle.putInt("tid", mId);
					bundle.putString("cname", cName);
					intent.putExtras(bundle);
					CourseDetail.this.startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
		
	}
	
	private void initWidget()
	{
		if(cursor.getCount() == 0 )
		{
			cosName.setText("default");
			tName.setText("default");
			place.setText("default");
			time.setText("default");
			intro.setText("default");
		}else
		{
			cursor.moveToFirst();       //不可少
			
			int tchId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("tid")));
			
			cosName.setText(cursor.getString(cursor.getColumnIndex("name")));
			tName.setText(mCos.getTchName(tchId));
			place.setText(cursor.getString(cursor.getColumnIndex("place")));
			time.setText(cursor.getString(cursor.getColumnIndex("time")));
			intro.setText(cursor.getString(cursor.getColumnIndex("intro")));
		}
		
		switch(sourceId)
		{
		case 1:
			showComment.setText("我要评论");
			select.setText("联系老师");
			break;
		case 2:
			showComment.setText("查看评论");
			select.setText("选修此课");
			break;
		case 3:
			showComment.setText("查看评论");
			select.setText("查看学生");
			break;
		default:
			showComment.setText("查看评论");
			select.setText("选修此课");
			break;
		}
	}
}
