package com.xsh.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.xsh.provider.DatabaseHelper;
import com.xsh.provider.Words.Word;
import com.xsh.test.R;

public class Group extends Activity {
	private Button addGroup;
	private Button back;
	private EditText addGroupEdit;
	private ListView groupList;
	private SimpleAdapter adapter;
	private List<Map<String, String>> data;
	private DatabaseHelper dbOpenHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
		dbOpenHelper = new DatabaseHelper(this);
		groupList = (ListView) findViewById(R.id.listViewGroup);
		back = (Button) findViewById(R.id.buttonBackGroup);
		addGroup = (Button) findViewById(R.id.buttonAddGroup);
		loadGroupList();//加载列表
		addGroup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog();//弹出对话框，新增群组
			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Group.this.finish();
			}
		});
		groupList.setOnItemClickListener(new OnItemClickListener() {
			//跳转进入groupDetail
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String name = ((TextView) view.findViewById(android.R.id.text1))
						.getText().toString();
				Bundle bundle = new Bundle();
				bundle.putString("name", name);
				Intent intent = new Intent(Group.this, GroupDetail.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		groupList.setOnItemLongClickListener(new OnItemLongClickListener() {
			//长按删除
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String name = ((TextView) view.findViewById(android.R.id.text1))
						.getText().toString();
				dialogDelete(name);
				return false;
			}
			

		});
	}

	private void dialogDelete(final String name) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(Group.this);
		builder.setTitle("确定要删除该群组吗？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
				db.delete("contactgroup",Word.KEY_NAME + " = "
						+ "'" + name + "'", null);
				db.execSQL("drop table " + name + ";");
				data.clear();
				data.addAll(loadData());
				adapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	@Override
	protected void onStart(){
		super.onStart();
		data.clear();
		data.addAll(loadData());
		adapter.notifyDataSetChanged();
	}
	
	private void dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Group.this);
		TableLayout item = (TableLayout) getLayoutInflater().inflate(
				R.layout.groupdialog, (ViewGroup) findViewById(R.id.groupdialog));
		addGroupEdit = (EditText) item.findViewById(R.id.groupdialogedit);
		builder.setTitle("新增我的群组");
		builder.setView(item);
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
				String groupname = addGroupEdit.getText().toString();
				ContentValues values = new ContentValues();
				values.put(Word.KEY_NAME, groupname);
				db.insert("contactgroup", Word.KEY_ID, values);
				dbOpenHelper.createGroupMember(db, groupname);
				data.clear();
				data.addAll(loadData());
				adapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	
	}
	
	private  void loadGroupList() {
		// TODO Auto-generated method stub
		data = loadData();
		adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_1,
				new String[] { Word.KEY_NAME },
				new int[] { android.R.id.text1 });
		groupList.setAdapter(adapter);
	}

	private List<Map<String, String>> loadData() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cur = db.query("contactgroup", new String[] { Word.KEY_ID,
				Word.KEY_NAME }, null, null, null, null, null);
		return getGroup(cur);
	}

	private List<Map<String, String>> getGroup(Cursor cur) {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> item;
		if (cur.getCount() == 0) {
			item = new HashMap<String, String>();
			item.put(Word.KEY_NAME, "no group");
			data.add(item);
		} else {
			while (cur.moveToNext()) {
				item = new HashMap<String, String>();
				item.put(Word.KEY_NAME, cur.getString(cur.getColumnIndex(Word.KEY_NAME)));
				data.add(item);
			}
		}
		return data;
	}
}
