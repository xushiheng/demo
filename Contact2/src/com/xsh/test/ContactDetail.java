package com.xsh.test;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xsh.provider.Words.Word;

public class ContactDetail extends Activity {
	private String name;
	private ContentResolver contentResolver;
	private EditText editName;
	private EditText editMobile;
	private EditText editTelephone;
	private EditText editEmail;
	private EditText editRemarks;
	private Button fixBtn;
	private Button deleteBtn;
	private Button backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userdetail);
		contentResolver = getContentResolver();
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		editName = (EditText) findViewById(R.id.editTextUserDetail1);
		editMobile = (EditText) findViewById(R.id.editTextUserDetail2);
		editTelephone = (EditText) findViewById(R.id.editTextUserDetail3);
		editEmail = (EditText) findViewById(R.id.editTextUserDetail4);
		editRemarks = (EditText) findViewById(R.id.editTextUserDetail5);
		fixBtn = (Button) findViewById(R.id.buttonFix);
		deleteBtn = (Button) findViewById(R.id.buttonDelete);
		backBtn = (Button) findViewById(R.id.backButton);
		loadEditText();//加载详细的联系人信息

		fixBtn.setOnClickListener(new OnClickListener() {
			//修改联系人信息，跳转进入FixContact
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle data = new Bundle();
				data.putString("name", name);
				Intent intent1 = new Intent(ContactDetail.this,
						FixContact.class);
				intent1.putExtras(data);
				startActivity(intent1);
				ContactDetail.this.finish();
			}
		});
		deleteBtn.setOnClickListener(new OnClickListener() {
			//删除联系人信息
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				contentResolver.delete(Word.CONTACTS_URI, Word.KEY_NAME + " = "
						+ "'" + name + "'", null);
				ContactDetail.this.finish();
			}
		});
		backBtn.setOnClickListener(new OnClickListener() {
			//返回
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ContactDetail.this.finish();
			}
		});
		
	}

	private void loadEditText() {
		// TODO Auto-generated method stub
		Cursor cur = contentResolver.query(Word.CONTACTS_URI, new String[] {
				Word.KEY_ID, Word.KEY_NAME,Word.KEY_MOBILE, Word.KEY_TELEPHONE,
				Word.KEY_EMAIL, Word.KEY_REMARKS }, Word.KEY_NAME + " = " + "'"
				+ name + "'", null, null);
		cur.moveToFirst();
		editName.setText(cur.getString(cur.getColumnIndex(Word.KEY_NAME)));
		editMobile.setText(cur.getString(cur.getColumnIndex(Word.KEY_MOBILE)));
		editTelephone.setText(cur.getString(cur
				.getColumnIndex(Word.KEY_TELEPHONE)));
		editEmail.setText(cur.getString(cur.getColumnIndex(Word.KEY_EMAIL)));
		editRemarks
				.setText(cur.getString(cur.getColumnIndex(Word.KEY_REMARKS)));
         
	}
}
