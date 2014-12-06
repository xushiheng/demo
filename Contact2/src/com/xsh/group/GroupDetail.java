package com.xsh.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xsh.provider.DatabaseHelper;
import com.xsh.provider.Words.Word;
import com.xsh.test.ContactDetail;
import com.xsh.test.R;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class GroupDetail extends Activity {
	private Button addContact;
	private Button back;
	private ListView groupMemberList;
	private TextView groupNameText;
	private String groupName;
	private SimpleAdapter adapter;
	private List<Map<String, String>> data;
	private DatabaseHelper dbOpenHelper;
	private Cursor cursor;
	private boolean[] select;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupdetail);
		Intent intent = getIntent();
		groupName = intent.getStringExtra("name");
		dbOpenHelper = new DatabaseHelper(this);
		groupNameText = (TextView) findViewById(R.id.textViewGroupName);
		groupMemberList = (ListView) findViewById(R.id.listViewGroupDetail);
		addContact = (Button) findViewById(R.id.buttonAddGroupUser);
		back = (Button) findViewById(R.id.buttonBackGroupDetail);
		groupNameText.setText(groupName);
		loadGroupMemberList();
		addContact.setOnClickListener(new OnClickListener() {
			//添加联系人，弹出多选列表对话框
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GroupDetail.this.finish();
			}
		});
		
		groupMemberList.setOnItemClickListener(new OnItemClickListener() {
			//点击进入联系人详细信息
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String name = ((TextView) view.findViewById(android.R.id.text1))
						.getText().toString();
				Bundle bundle = new Bundle();
				bundle.putString("name", name);
				Intent intent = new Intent(GroupDetail.this, ContactDetail.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		groupMemberList.setOnItemLongClickListener(new OnItemLongClickListener() {
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
		AlertDialog.Builder builder = new AlertDialog.Builder(GroupDetail.this);
		builder.setTitle("确定要删除该联系人吗？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
				db.delete(groupName,Word.KEY_NAME + " = "
						+ "'" + name + "'", null);
				data.clear();
				data.addAll(loadData());
				adapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	private  void dialog() {
		// TODO Auto-generated method stub
		final SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		cursor = db
				.query("contact", new String[] { Word.KEY_ID, Word.KEY_NAME,Word.KEY_MOBILE,
						Word.KEY_CHEAKED }, null, null, null, null, null);
		select = new boolean[cursor.getCount() + 1];
		AlertDialog.Builder builder = new AlertDialog.Builder(GroupDetail.this);
		builder.setTitle("添加联系人");
		builder.setMultiChoiceItems(cursor, Word.KEY_CHEAKED, Word.KEY_NAME,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1,
							boolean arg2) {
						// TODO Auto-generated method stub
						select[arg1] = arg2;
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
					for(int i = 0 ; select[i] && i < select.length - 1; i ++ ){
						cursor.moveToPosition(i);
						String contactname = cursor.getString(cursor.getColumnIndex(Word.KEY_NAME));
						String contactmobile = cursor.getString(cursor.getColumnIndex(Word.KEY_MOBILE));
						ContentValues values = new ContentValues();
						values.put(Word.KEY_NAME, contactname);
						values.put(Word.KEY_MOBILE, contactmobile);
						db.insert(groupName, Word.KEY_ID, values);
					}
					data.clear();
					data.addAll(loadData());
					adapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	private  void loadGroupMemberList() {
		// TODO Auto-generated method stub
		data = loadData();
		adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, new String[] {
						Word.KEY_NAME, Word.KEY_MOBILE }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		groupMemberList.setAdapter(adapter);
	}

	private List<Map<String, String>> loadData() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cur = db.query(groupName, new String[] { Word.KEY_ID, Word.KEY_NAME,
				Word.KEY_MOBILE }, null, null, null, null, null);
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
}

