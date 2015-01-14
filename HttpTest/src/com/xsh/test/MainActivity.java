package com.xsh.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.xsh.util.HttpHandler;
import com.xsh.util.HttpUtil;
import com.xsh.util.JsonBean;
import com.xsh.util.JsonUtil;
import com.xsh.util.MyAdapter;

public class MainActivity extends Activity {
	public static JsonBean jsonBean;
	private ListView listView;
	private MyAdapter myAdapter;
	private List<Map<String, Object>> listItems;
	private JsonUtil jsonUtil = new JsonUtil();
	private HttpUtil httpUtil;
	private HttpHandler httpHandler = new HttpHandler(this){
		@Override
		protected void succeed(String json){
			super.succeed(json);
			jsonBean = jsonUtil.parseUserFromJson(json);
			listItems = getListItems();
			setListView(listItems);
		}
	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);
        httpUtil = new HttpUtil(httpHandler);
        httpUtil.getJson(HttpUtil.URLS.URL_TEST);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putInt("position", position);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
    }
    
	private void setListView(List<Map<String, Object>> listItems2) {
		// TODO Auto-generated method stub
		myAdapter = new MyAdapter(this, listItems2);
		listView.setAdapter(myAdapter);
	}

	private List<Map<String, Object>> getListItems() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listItems = new ArrayList<Map<String,Object>>();
		Map<String, Object> item;
		Iterator<JsonBean.Data.Comment> iterator = jsonBean.data.comment
				.iterator();
		while(iterator.hasNext()){
			JsonBean.Data.Comment comment = iterator.next();
			item = new HashMap<String, Object>();
			item.put("logo", comment.userLogo);
			item.put("name", comment.userName);
			item.put("comment", comment.content);
			listItems.add(item);
		}
		return listItems;
	}
}
