package com.test.coursemgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.db.TchManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowStudent extends Activity
{
	private Cursor cursor;
	private int tId;
	private String cName;
	private int cId;
	private TchManager mTch;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		tId = intent.getIntExtra("tid", 0);
		cName = intent.getStringExtra("cname");
		mTch = new TchManager(this, tId);
		cId = mTch.getCosId(cName);
		cursor = mTch.showStudent(cId);
		
		ListView list = new ListView(this);
		SimpleAdapter adapter = new SimpleAdapter(this, getSimpleData(),
                android.R.layout.simple_list_item_2, new String[] { "sname",
                        "sid" }, new int[] { android.R.id.text1,
                        android.R.id.text2 });
		list.setAdapter(adapter);
		
		setContentView(list);
	}

	private List<Map<String, String>> getSimpleData(){
		
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> item;

        Log.e("ok", cursor.toString());
        if(cursor.getCount() == 0)
        {
        	item = new HashMap<String, String>();
            item.put("sname", "暂无学生选修");
//        	item.put("sid", "default");
            data.add(item);
        }else
        {
//        	int stuId;
        	while(cursor.moveToNext())
            {
        		item = new HashMap<String, String>();
//        		stuId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sid")));
        		Log.e("position", String.valueOf(cursor.getPosition()));
//        		Log.e("name", cursor.getString(cursor.getColumnIndex("name")));
            	item.put("sname", cursor.getString(cursor.getColumnIndex("name")));
            	item.put("sid", cursor.getString(cursor.getColumnIndex("sid")));
            	Log.e("item", item.toString());
            	data.add(item);
            }
        }
        return data;
    }
}
