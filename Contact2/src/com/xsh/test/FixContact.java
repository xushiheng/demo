package com.xsh.test;

import com.xsh.provider.Words.Word;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FixContact extends Activity {
	private Button save;
	private Button back;
	private EditText editName;
	private EditText editMobile;
	private EditText editTelephone;
	private EditText editEmail;
	private EditText editRemarks;
	private ContentResolver contentResolver;
	private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnew);
		contentResolver = getContentResolver();
		Intent intent1 = getIntent();
		name = intent1.getStringExtra("name");
		editName = (EditText) findViewById(R.id.editTextName);
        editMobile = (EditText) findViewById(R.id.editTextMobile);
        editTelephone = (EditText) findViewById(R.id.editTextTelephone);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editRemarks = (EditText) findViewById(R.id.editTextRemarks);
        save = (Button) findViewById(R.id.buttonSave);
        back = (Button) findViewById(R.id.buttonBack);
        
        loadEditText();//加载信息
        save.setOnClickListener(new OnClickListener() {
			//更新联系人信息
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = editName.getText().toString();
				String mobile = editMobile.getText().toString();
				String telephone = editTelephone.getText().toString();
				String email = editEmail.getText().toString();
				String remarks = editRemarks.getText().toString();
				ContentValues values = new ContentValues();
				values.put(Word.KEY_NAME, name);
				values.put(Word.KEY_MOBILE, mobile);
				values.put(Word.KEY_TELEPHONE, telephone);
				values.put(Word.KEY_EMAIL, email);
				values.put(Word.KEY_REMARKS, remarks);
				contentResolver.update(Word.CONTACTS_URI, values, Word.KEY_NAME + " = " + "'" + name + "'", null);
				Toast.makeText(FixContact.this, "修改联系人成功", Toast.LENGTH_SHORT).show();
			}
		});
        
        back.setOnClickListener(new OnClickListener() {
			//返回
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				FixContact.this.finish();
			}
		});
		
}
	private void loadEditText() {
		// TODO Auto-generated method stub
		Cursor cur = contentResolver.query(Word.CONTACTS_URI, new String[] {
				Word.KEY_ID, Word.KEY_NAME,Word.KEY_MOBILE, Word.KEY_TELEPHONE,
				Word.KEY_EMAIL, Word.KEY_REMARKS }, Word.KEY_NAME + " = " +"'"
				+ name + "'", null, null);
		cur.moveToFirst();
		editName.setText(cur.getString(cur.getColumnIndex(Word.KEY_NAME)));
		editMobile.setText(cur.getString(cur.getColumnIndex(Word.KEY_MOBILE)));
		editTelephone.setText(cur.getString(cur.getColumnIndex(Word.KEY_TELEPHONE)));
		editEmail.setText(cur.getString(cur.getColumnIndex(Word.KEY_EMAIL)));
		editRemarks.setText(cur.getString(cur.getColumnIndex(Word.KEY_REMARKS)));
	}
}

