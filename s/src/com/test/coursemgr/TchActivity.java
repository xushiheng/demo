package com.test.coursemgr;

import java.util.ArrayList;
import java.util.List;

import com.test.db.StuManager;
import com.test.db.TchManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TchActivity extends Activity{

	private TchManager mTchMgr;
	private Cursor cursor;
	
	private int tchId;
	private String tchName;
	
	private TextView name;
	private TextView id;
	private Button createCos;
	private ListView list;
	
	private String newName;
	private String newTime;
	private String newPlace;
	private String newIntro;
	private EditText cosName;
	private EditText cosTime;
	private EditText cosPlace;
	private EditText cosIntro;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tch_activity);
		
		Intent intent = getIntent();
		tchName = intent.getStringExtra("name");
		tchId = intent.getIntExtra("id", 0);
		
		mTchMgr = new TchManager(this, tchId);
		
		
		name = (TextView)findViewById(R.id.tch_name);
		name.setText(tchName);
		id = (TextView)findViewById(R.id.tch_id);
		id.setText(String.valueOf(tchId));
		
		createCos = (Button)findViewById(R.id.create_cos);
		createCos.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Dialog();
			}
		});
		
		list = (ListView)findViewById(R.id.tch_my_course);
		
		listUpdate();
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(TchActivity.this, CourseDetail.class);
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
				bundle.putInt("sourceId", 3);
				bundle.putInt("id", tchId);
				bundle.putString("name", name);
				intent.putExtras(bundle);
				TchActivity.this.startActivity(intent);
			}
		});
	}
	
	private void listUpdate()
	{
		cursor = mTchMgr.showMyCourse();
		ArrayAdapter listAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,getData());
		list.setAdapter(listAdapter);
	}
	
	private List<String> getData()
	{
        List<String> data = new ArrayList<String>();
        if(cursor.getCount() == 0)
        {
        	data.add("default");
            
        }else
        {
        	while(cursor.moveToNext())
            {
            	data.add(cursor.getString(cursor.getColumnIndex("name")));
            }
        }
        return data;
    }
	
	protected void Dialog()
	{
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.new_cos,(ViewGroup) findViewById(R.id.new_cos));
		
		cosName = (EditText)layout.findViewById(R.id.new_cos_name);
		cosTime = (EditText)layout.findViewById(R.id.new_cos_time);
		cosPlace = (EditText)layout.findViewById(R.id.new_cos_place);
		cosIntro = (EditText)layout.findViewById(R.id.new_cos_intro);
		
		AlertDialog.Builder builder = new Builder(this);
		builder.setView(layout);
		builder.setPositiveButton("确认", new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				newName = cosName.getText().toString();
				newTime = cosTime.getText().toString();
				newPlace = cosPlace.getText().toString();
				newIntro = cosIntro.getText().toString();
				mTchMgr.createCos(newName, newTime, newPlace, newIntro);
				listUpdate();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.show();
	}
}
