package com.xsh.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	MyDataBaseAdapter m_MyDataBaseAdapter;
	final String FILE_NAME = "/word.txt";
	private String text1 = "是否添加进单词本？";
	private ListView list1;
	private SimpleCursorAdapter adapter1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button read = (Button) findViewById(R.id.btn1);
		Button search = (Button) findViewById(R.id.btn2);
		Button vocabulary = (Button) findViewById(R.id.btn3);
		final EditText edit1 = (EditText) findViewById(R.id.edit1);
		list1 = (ListView) findViewById(R.id.list1);

		m_MyDataBaseAdapter = new MyDataBaseAdapter(this);
		m_MyDataBaseAdapter.open();

		list1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				String word = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
				String meaning = ((TextView)view.findViewById(android.R.id.text2)).getText().toString();
			    dialog(word,meaning);
			}
		});

		
		read.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				read();
			}
		});

		search.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String x = edit1.getText().toString();
				search(x);
			}
		});

		vocabulary.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, WordList.class);
				startActivity(intent);
			}
		});
	}

	protected void search(String x) {
		Cursor cur = m_MyDataBaseAdapter.fetchData(x);
		adapter1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cur, new String[] {
						MyDataBaseAdapter.KEY_WORD,
						MyDataBaseAdapter.KEY_MEANING }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		list1.setAdapter(adapter1);
	}

	protected void read() {
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				FileInputStream fis = new FileInputStream(
						sdCardDir.getCanonicalPath() + FILE_NAME);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fis));
				String word = null;
				String meaning = null;
				while ((word = br.readLine()) != null) {
					meaning = br.readLine();
					m_MyDataBaseAdapter.insertData(word, meaning);
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void dialog(final String word , final String meaning) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle(text1);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				addWord(word ,meaning);
			}

		});
		builder.setNegativeButton("取消", null);
		builder.create().show();

	}

	protected void addWord(String word , String meaning) {
		m_MyDataBaseAdapter.insertData2(word, meaning);
	}
}
