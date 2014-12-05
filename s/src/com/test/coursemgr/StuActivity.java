package com.test.coursemgr;

import java.util.ArrayList;
import java.util.List;

import com.test.db.StuManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class StuActivity extends Activity{

	private int stuId;
	private String stuName;
	
	private TextView name;
	private TextView id;
	private Button showAllCos;
	private ListView list;
	
	private StuManager mStuMgr;
	private Cursor cursor;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_activity);
		
		Intent intent = getIntent();
		stuName = intent.getStringExtra("name");
		stuId = intent.getIntExtra("id", 0);
		
		name = (TextView)findViewById(R.id.stu_name);
		name.setText(stuName);
		id = (TextView)findViewById(R.id.stu_id);
		id.setText(String.valueOf(stuId));

		showAllCos = (Button)findViewById(R.id.show_all_cos);
		showAllCos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(StuActivity.this,ShowAllCos.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id", stuId);
				
				intent.putExtras(bundle);
				StuActivity.this.startActivity(intent);
			}
		});

		list = (ListView)findViewById(R.id.stu_my_course);

		update();
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(StuActivity.this, CourseDetail.class);
				
				String name;
				if(cursor.getCount() == 0)
				{
					name = "default";
				}else
				{
					cursor.moveToPosition(position);
					name = cursor.getString(cursor.getColumnIndex("name"));
					
					Bundle bundle = new Bundle();
					bundle.putInt("sourceId", 1);
					bundle.putInt("id", stuId);
					bundle.putString("name", name);
					intent.putExtras(bundle);
					StuActivity.this.startActivity(intent);
				}
				
			}
		});
		
	}
	
	private void update()
	{
		mStuMgr = new StuManager(this, stuId);
		cursor = mStuMgr.showMyCourse();
		ArrayAdapter listAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,getData());
		list.setAdapter(listAdapter);
	}
	
	protected void onResume()
	{
		super.onResume();
		update();
	}

	private List<String> getData()
	{
        List<String> data = new ArrayList<String>();
        if(cursor.getCount() == 0)
        {
        	data.add("还没有选课，查看所有课程选课吧！");
            
        }else
        {
        	while(cursor.moveToNext())
            {
            	data.add(cursor.getString(cursor.getColumnIndex("name")));
            }
        }
        return data;
    }
}
