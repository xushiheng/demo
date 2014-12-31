package com.xsh.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button getJson;
	private Button showJson;
	private ListView jsonList;
	private String jsonData;
	private static JsonBean jsonBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		jsonList = (ListView) findViewById(R.id.listView1);
		getJson = (Button) findViewById(R.id.button1);
		getJson.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//网络请求要开子线程
				new Thread() {
					public void run() {
						try {
							URL url = new URL("http://byandroid.sinaapp.com/");
							HttpURLConnection http = (HttpURLConnection) url
									.openConnection();
							int nrc = http.getResponseCode();
							if (nrc == HttpURLConnection.HTTP_OK) {
								InputStream is = http.getInputStream();
								BufferedReader bf = new BufferedReader(
										new InputStreamReader(is));
								jsonData = bf.readLine();
								bf.close();
								is.close();
								//子线程中不可以直接使用Toast，除非使用Looper
								Looper.prepare();
								Toast.makeText(getApplicationContext(),
										"获取Json成功", Toast.LENGTH_SHORT).show();
								Looper.loop();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							Looper.prepare();
							Toast.makeText(getApplicationContext(),
									"获取Json失败，请检查网络连接", Toast.LENGTH_SHORT)
									.show();
							Looper.loop();
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
		showJson = (Button) findViewById(R.id.button2);
		showJson.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JsonUtils jsonUtils = new JsonUtils();
				jsonBean = jsonUtils.parseUserFromJson(jsonData);
				List<Map<String, String>> data = new ArrayList<Map<String, String>>();
				Map<String, String> item;
				Iterator<JsonBean.Data.Comment> iterator = jsonBean.data.comment
						.iterator();
				while (iterator.hasNext()) {
					JsonBean.Data.Comment comment = iterator.next();
					item = new HashMap<String, String>();
					item.put("user_logo", comment.user_logo);
					item.put("content", comment.content);
					item.put("user_name", comment.user_name);
					data.add(item);
				}
				SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,
						data, R.layout.listitem, new String[] { "content",
								"user_name", "user_logo" },
						new int[] { R.id.textView1, R.id.textView2,
								R.id.textView3 });
				jsonList.setAdapter(adapter);
			}
		});
		jsonList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putInt("position", position);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Detail.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	public static JsonBean getJsonBean(){
		return jsonBean;
	}
}
