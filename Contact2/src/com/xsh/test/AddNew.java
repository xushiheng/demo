package com.xsh.test;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xsh.provider.Words.Word;

public class AddNew extends Activity{
	private Button save;
	private Button back;
	private EditText editName;
	private EditText editMobile;
	private EditText editTelephone;
	private EditText editEmail;
	private EditText editRemarks;
	private ContentResolver contentResolver;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnew);
        contentResolver = getContentResolver();
        editName = (EditText) findViewById(R.id.editTextName);
        editMobile = (EditText) findViewById(R.id.editTextMobile);
        editTelephone = (EditText) findViewById(R.id.editTextTelephone);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editRemarks = (EditText) findViewById(R.id.editTextRemarks);
        save = (Button) findViewById(R.id.buttonSave);
        save.setOnClickListener(new OnClickListener() {
			//保存联系人信息
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
				contentResolver.insert(Word.CONTACTS_URI, values);
				Toast.makeText(AddNew.this, "添加联系人成功", Toast.LENGTH_SHORT).show();

			}
		});
        back = (Button) findViewById(R.id.buttonBack);
        back.setOnClickListener(new OnClickListener() {
			//返回到MainActivity
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AddNew.this.finish();
			}
		});
	}
}
