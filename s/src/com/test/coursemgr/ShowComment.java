package com.test.coursemgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.db.CmtManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowComment extends Activity
{
	private String cosName;
	private int cosId;
	private int sourceId; 
	private CmtManager mComment;
	private int stuId;
	
	private ListView list;
	private Button addComment;
	private TextView course;
	private EditText comment;
	private LinearLayout linear;
	
	private Cursor cursor;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_comment);
		
		mComment = new CmtManager(this);
		Intent intent = getIntent();
		this.cosName = intent.getStringExtra("cosName");
		this.sourceId = intent.getIntExtra("sourceId", 0);
		this.stuId = intent.getIntExtra("id", 123456);
		cosId = mComment.getCosId(cosName);

		linear = (LinearLayout)findViewById(R.id.comment_linear);
		list = (ListView)findViewById(R.id.comment_list);
		addComment = (Button)findViewById(R.id.add_comment);
		course = (TextView)findViewById(R.id.comment_cos_name);
		course.setText(cosName);
		comment = (EditText)findViewById(R.id.write_comment);
		
		switch(sourceId)
		{
		case 1:
			//comment activity;
			break;
		case 2:
			//show comment list view
			comment.setVisibility(8);
			addComment.setText("我要选修");
			break;
		case 3:
			//show comment list view
			linear.setVisibility(8);
			break;
		default:
			break;
		}

		update();
		
		addComment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				switch(sourceId)
				{
				case 1:
					mComment.AddToCmt(cosId, stuId, "00-00-00", comment.getText().toString());
					comment.setText("");   //还是null?
//					comment.setFocusable(false);
					update();
					break;
				case 2:
					mComment.AddToStu(stuId, mComment.getStuName(stuId), cosId, 0);
					Toast.makeText(ShowComment.this, "选修成功", 5000).show();
					break;
				case 3:
					break;
				default:
					break;
				}
			}
		});
		
		
	}

	private void update()
	{

		cursor = mComment.showComment(cosId);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, getSimpleData(),
                android.R.layout.simple_list_item_2, new String[] { "content",
                        "sname" }, new int[] { android.R.id.text1,
                        android.R.id.text2 });
		list.setAdapter(simpleAdapter);
	}
	
	private List<Map<String, String>> getSimpleData(){
		
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> item;

        Log.e("ok", cursor.toString());
        if(cursor.getCount() == 0)
        {
        	item = new HashMap<String, String>();
        	item.put("content", "暂无评论");
//            item.put("sname", "default");
            data.add(item);
        }else
        {
        	int stuId;
        	while(cursor.moveToNext())
            {
        		item = new HashMap<String, String>();
        		stuId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sid")));
        		Log.e("position", String.valueOf(cursor.getPosition()));
//        		Log.e("name", cursor.getString(cursor.getColumnIndex("name")));
            	item.put("content", cursor.getString(cursor.getColumnIndex("content")));
            	item.put("sname", mComment.getStuName(stuId));
            	Log.e("item", item.toString());
            	data.add(item);
            }
        }
        return data;
    }
}
