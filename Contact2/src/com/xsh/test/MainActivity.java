package com.xsh.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xsh.group.Group;
import com.xsh.provider.Words.Word;

public class MainActivity extends Activity {
	private ListView contactList;
	private ImageButton searchContact;
	private GridView bottomGrid;
	private AutoCompleteTextView autoText;
	private ContentResolver contentResolver;
	private SimpleAdapter adapter;
	private List<Map<String, String>> data;
	private final String[] bottomItemName = { "增加", "群组", "退出" };
	private final int[] bottomItemSource = { R.drawable.add, R.drawable.group,
			R.drawable.exit };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		contentResolver = getContentResolver();
		contactList = (ListView) findViewById(R.id.contactList);
		autoText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
		
		loadBottomGrid();//加载gridView
		loadContactList();//加载listView
		setAutotext();//加载autoCompleteTextView

		searchContact = (ImageButton) findViewById(R.id.imageButtonSearch);
		searchContact.setOnClickListener(new OnClickListener() {
			//查询联系人
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String x = autoText.getText().toString();
				data.clear();
				data.addAll(loadDataInSearch(x));
				adapter.notifyDataSetChanged();
			}
		});

		bottomGrid.setOnItemClickListener(new OnItemClickListener() {
			//gridView的点击事件，分别是增加联系人，群组和退出
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0: {
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, AddNew.class);
					startActivity(intent);
					break;
				}
				case 1: {
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, Group.class);
					startActivity(intent);
					break;
				}
				case 2: {
					MainActivity.this.finish();
					break;
				}
				}
			}
		});
		contactList.setOnItemClickListener(new OnItemClickListener() {
			//列表项的点击事件，进入联系人详细的信息
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String name = ((TextView) view.findViewById(android.R.id.text1))
						.getText().toString();
				Bundle bundle = new Bundle();
				bundle.putString("name", name);
				Intent intent = new Intent(MainActivity.this,
						ContactDetail.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void loadContactList() {
		// TODO Auto-generated method stub
		data = loadData();
		adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, new String[] {
						Word.KEY_NAME, Word.KEY_MOBILE }, new int[] {
						android.R.id.text1, android.R.id.text2 });// 绑定adapter

		contactList.setAdapter(adapter);
	}
	//保证返回MainActivity是列表和AutoText都是最新的
	@Override
	protected void onResume() {
		super.onResume();
		data.clear();
		data.addAll(loadData());
		adapter.notifyDataSetChanged();
		setAutotext();
	}
	//以下的都是一些功能实现代码
	private List<Map<String, String>> loadData() {
		// TODO Auto-generated method stub
		Cursor cur = contentResolver
				.query(Word.CONTACTS_URI, new String[] { Word.KEY_ID,
						Word.KEY_NAME, Word.KEY_MOBILE }, null, null, null);
		return getContact(cur);
	}

	private void setAutotext() {
		// TODO Auto-generated method stub
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, getAry());
		autoText.setAdapter(adapter);
	}

	private ArrayList<String> getAry() {
		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = null;
		cursor = contentResolver.query(Word.CONTACTS_URI, new String[] {
				Word.KEY_ID, Word.KEY_NAME }, null, null, null);
		if (cursor.getCount() == 0) {
			list.add("");
		} else {
			cursor.moveToFirst();

			do {
				list.add(cursor.getString(cursor.getColumnIndex(Word.KEY_NAME)));

			} while (cursor.moveToNext());
		}
		return list;
	}

	private List<Map<String, String>> loadDataInSearch(String x) {
		// TODO Auto-generated method stub
		Cursor cur = contentResolver.query(Word.CONTACTS_URI, new String[] {
				Word.KEY_ID, Word.KEY_NAME, Word.KEY_MOBILE }, Word.KEY_NAME
				+ " like ? ", new String[] { x + "%" }, null);
		return getContact(cur);
	}

	private List<Map<String, String>> getContact(Cursor cur) {
		// TODO Auto-generated method stub
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> item;
		if (cur.getCount() == 0) {
			item = new HashMap<String, String>();
			item.put(Word.KEY_NAME, "no people");
			item.put(Word.KEY_MOBILE, "no mobilephone number");
			data.add(item);
		} else {
			while (cur.moveToNext()) {
				item = new HashMap<String, String>();
				item.put(Word.KEY_NAME,
						cur.getString(cur.getColumnIndex(Word.KEY_NAME)));
				item.put(Word.KEY_MOBILE,
						cur.getString(cur.getColumnIndex(Word.KEY_MOBILE)));
				data.add(item);
			}
		}
		return data;
	}

	private void loadBottomGrid() {
		// TODO Auto-generated method stub
		if (bottomGrid == null) {
			bottomGrid = (GridView) findViewById(R.id.gridViewContact);
			bottomGrid.setGravity(Gravity.CENTER);
			bottomGrid.setAdapter(getMenuAdapter(bottomItemName,
					bottomItemSource));
		}
	}

	private SimpleAdapter getMenuAdapter(String[] menuNameArray,
			int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simpleAdapter;
	}
}
