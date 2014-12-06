package com.xsh.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xsh.provider.Words.Word;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "Contact.db";
	private static final String DB_TABLE1 = "contact";//联系人
	private static final String DB_TABLE2 = "contactgroup";//群组
	private static String DB_TABLE3;//群组成员建表
	private static final int DB_VERSION = 1;
	private static final String DB_CREATE1 = "create table " + DB_TABLE1 + "("
			+ Word.KEY_ID + " integer primary key autoincrement," + Word.KEY_NAME
			+ " text not null," + Word.KEY_MOBILE + " text not null," + Word.KEY_TELEPHONE
			+ " text null," + Word.KEY_EMAIL + " text null," + Word.KEY_REMARKS
			+ " text null,"+ Word.KEY_CHEAKED + " int not null default 0);";
	private static final String DB_CREATEGROUP = "create table " + DB_TABLE2 + "("
			+ Word.KEY_ID + " integer primary key autoincrement," + Word.KEY_NAME
			+ " text not null);";
	

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE1);
		db.execSQL(DB_CREATEGROUP);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exits notes");
		onCreate(db);
	}
	//群组成员建表方法
	public void createGroupMember(SQLiteDatabase db, String dbName){
		DB_TABLE3 = dbName;
		String DB_CREATEGROUPMEMBER = "create table " + DB_TABLE3 + "("
				+ Word.KEY_ID + " integer primary key autoincrement," + Word.KEY_NAME
				+ " text not null," + Word.KEY_MOBILE + " text not null," + Word.KEY_CHEAKED + " int not null default 0);";
		db.execSQL(DB_CREATEGROUPMEMBER);
	}
}