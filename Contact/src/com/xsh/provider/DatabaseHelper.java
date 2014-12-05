package com.xsh.provider;

import com.xsh.provider.Words.Word;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "Contact.db";
	public static final String DB_TABLE1 = "contact";
	public static final String DB_TABLE2 = "group";
	public static final String DB_TABLE3 = "groupmember";
	private static final int DB_VERSION = 1;
	private static final String DB_CREATE1 = "create table " + DB_TABLE1 + "("
			+ Word.KEY_ID + " integer primary key autoincrement," + Word.KEY_NAME
			+ " text not null," + Word.KEY_MOBILE + " text not null," + Word.KEY_TELEPHONE
			+ " text null," + Word.KEY_EMAIL + " text null," + Word.KEY_REMARKS
			+ " text null);";


	DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("drop table if exits notes");
		onCreate(db);
	}
}