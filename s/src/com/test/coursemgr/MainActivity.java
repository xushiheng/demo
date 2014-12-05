package com.test.coursemgr;

import com.test.db.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button stuLogin;
	private Button tchLogin;
	private Button init;
	private EditText id;
	private EditText name;
	private TextView type;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	private boolean isStu;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new DatabaseHelper(this);
		db = dbHelper.getWritableDatabase();
		
		stuLogin = (Button)findViewById(R.id.stu_login);
		tchLogin = (Button)findViewById(R.id.tch_login);
		
		init = (Button)findViewById(R.id.init);
		
		init.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbHelper.onUpgrade(db, 1, 1);
			}
		});
		
//		id = (EditText)findViewById(R.id.login_id);
//		name = (EditText)findViewById(R.id.login_name);
		
		stuLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				name.setText("ewewe");
				isStu = true;
				Dialog();
			}
		});
		
		tchLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isStu = false;
				Dialog();
			}
		});
	}

	public void Dialog()
	{
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.login_dialog,(ViewGroup) findViewById(R.id.login_dialog));

		id = (EditText)layout.findViewById(R.id.login_id);
		name = (EditText)layout.findViewById(R.id.login_name);
		type = (TextView)layout.findViewById(R.id.login_type);
		if(isStu)
		{
			type.setText("学号");
		}else
		{
			type.setText("教师号");
		}
		
//		View layout = (ViewGroup)findViewById(R.id.login_dialog);
		AlertDialog.Builder builder = new Builder(this);
		builder.setView(layout);
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(isAllowed(isStu, id.getText().toString(), name.getText().toString()))
				{
					Intent intent;
					if(isStu)
					{
						intent = new Intent(MainActivity.this, StuActivity.class);
					}else
					{
						intent = new Intent(MainActivity.this, TchActivity.class);
					}
					
					Bundle bundle = new Bundle();
					bundle.putString("name", name.getText().toString());
					bundle.putInt("id", Integer.parseInt(id.getText().toString()));

					intent.putExtras(bundle);
					MainActivity.this.startActivity(intent);
				}else
				{
					Toast.makeText(MainActivity.this, type.getText().toString()+"或姓名错误！", 5000).show();
				}
				
			}
		});
		builder.show();
	}
	
	private boolean isAllowed(boolean isStu, String id, String name)
	{
		if(isStu)
		{
			if((name.equals("姜进")&&id.equals("123456"))||
					(name.equals("小明")&&id.equals("111111")))return true;
			else return false;
		}else
		{
			if((name.equals("刀哥")&&id.equals("1245"))||
					(name.equals("大明")&&id.equals("1234")))return true;
			else return false;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
