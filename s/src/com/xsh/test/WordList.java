package com.xsh.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class WordList extends Activity {
	MyDataBaseAdapter m_MyDataBaseAdapter;
	private ListView list2;
	private SimpleCursorAdapter adapter2;
	private String text2 = "是否删除单词？";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordlist);
		m_MyDataBaseAdapter = new MyDataBaseAdapter(this);
		m_MyDataBaseAdapter.open();
		list2 = (ListView) findViewById(R.id.list2);
		UpdataAdapter();

		list2.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				String word = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
				dialog(word);
			}
		});

	}

	private void UpdataAdapter() {

		Cursor cur = m_MyDataBaseAdapter.fetchAllData();
		adapter2 = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cur, new String[] {
						MyDataBaseAdapter.KEY_WORD,
						MyDataBaseAdapter.KEY_MEANING }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		list2.setAdapter(adapter2);
	}

	protected void dialog(final String word) {

		AlertDialog.Builder builder = new AlertDialog.Builder(WordList.this);
		builder.setTitle(text2);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				deleteWord(word);
			}

		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	protected void deleteWord(String word) {
		m_MyDataBaseAdapter.deleteData(word);
		UpdataAdapter();
	}
}