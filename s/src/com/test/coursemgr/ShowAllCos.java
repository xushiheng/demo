package com.test.coursemgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.db.StuManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowAllCos extends Activity{

	private int stuId;
	private StuManager mStu;
	private Cursor cursor;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		this.stuId = intent.getIntExtra("id", 0);
		mStu = new StuManager(this, stuId);
		
		cursor = mStu.showAllCourse();
		
		ListView listView;
		listView = new ListView(this);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, getSimpleData(),
                android.R.layout.simple_list_item_2, new String[] { "name",
                        "tname" }, new int[] { android.R.id.text1,
                        android.R.id.text2 });
		listView.setAdapter(simpleAdapter);
        setContentView(listView);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ShowAllCos.this, CourseDetail.class);
				String name;
				if(cursor.getCount() == 0)
				{
					name = "default";
				}else
				{
					cursor.moveToPosition(position);
					name = cursor.getString(cursor.getColumnIndex("name"));
				}
				Bundle bundle = new Bundle();
				
				if(isMyCos(name))
				{
					bundle.putInt("sourceId", 1);
				}else
				{
					bundle.putInt("sourceId", 2);
				}
				
				bundle.putInt("id", stuId);
				bundle.putString("name", name);
				intent.putExtras(bundle);
				ShowAllCos.this.startActivity(intent);
			}
		});
    }
    
	private List<Map<String, String>> getSimpleData(){
		
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> item;

        if(cursor.getCount() == 0)
        {
        	item = new HashMap<String, String>();
        	item.put("name", "default");
            item.put("tname", "default");
            data.add(item);
        }else
        {
        	int tchId;
        	while(cursor.moveToNext())
            {
        		item = new HashMap<String, String>();
        		tchId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("tid")));
        		Log.e("position", String.valueOf(cursor.getPosition()));
        		Log.e("name", cursor.getString(cursor.getColumnIndex("name")));
            	item.put("name", cursor.getString(cursor.getColumnIndex("name")));
            	item.put("tname", mStu.getTchName(tchId));
            	Log.e("item", item.toString());
            	data.add(item);
            }
        }
        return data;
    }
	
	private boolean isMyCos(String name)
	{
		StuManager stuM = new StuManager(this, stuId);
		Cursor cursor = stuM.showMyCourse();
		while(cursor.moveToNext())
		{
			if(name.equals(cursor.getString(cursor.getColumnIndex("name"))))return true;
		}
		return false;
	}
}
