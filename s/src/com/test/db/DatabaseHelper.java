package com.test.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static String DB_NAME = "courseMgr";
	
	private static int DB_VERSION = 1;
	
	private static String CREATE_COURSE = "create table " +
											"course(" +
											"id integer primary key," +
											"name text," +
											"tid integer," +
											"time text," +
											"place text," +
											"intro text)";
	
	private static String CREATE_COMMENT = "create table " +
											"comment(" +
											"id integer primary key," +
											"cid integer," +
											"sid integer," +
											"time text," +
											"content text)";
	
	private static String CREATE_STUDENT = "create table " +
											"student(" +
											"id integer primary key," +
											"sid integer," +
											"name text," +
											"cid integer," +
											"grade integer default 0)";
	
	private static String CREATE_TEACHER = "create table " +
											"teacher(" +
											"id integer primary key," +
											"tid integer," +
											"name text," +
											"age integer," +
											"contact text)";
	public DatabaseHelper(Context context)
	{
		super(context,DB_NAME,null,DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_COURSE);
		db.execSQL(CREATE_COMMENT);
		db.execSQL(CREATE_STUDENT);
		db.execSQL(CREATE_TEACHER);
		
		insertInit(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists course");
		db.execSQL("drop table if exists comment");
		db.execSQL("drop table if exists student");
		db.execSQL("drop table if exists teacher");
		onCreate(db);
	}
	
	public void insertInit(SQLiteDatabase db)
	{		
		ContentValues cv = new ContentValues();
		
		cv.clear();
		cv.put("tid", 1245);
		cv.put("name", "刀哥");
		cv.put("age", 42);
		cv.put("contact", "18888888888");
		db.insert("teacher", null, cv);
		
		cv.clear();
		cv.put("tid", 1234);
		cv.put("name", "大明");
		cv.put("age", 35);
		cv.put("contact", "18866886688");
		db.insert("teacher", null, cv);
		
		cv.clear();
		cv.put("name", "数学");
		cv.put("tid", 1245);
		cv.put("time", "2014年");
		cv.put("place", "华科");
		cv.put("intro", "很难！！");
		db.insert("course", null, cv);
		
		cv.clear();
		cv.put("name", "体育");
		cv.put("tid", 1234);
		cv.put("time", "2013年");
		cv.put("place", "华科");
		cv.put("intro", "很棒！！");
		db.insert("course", null, cv);

		cv.clear();
		cv.put("sid", 123456);
		cv.put("name", "姜进");
		cv.put("cid", 1);
		cv.put("grade", 90);
		db.insert("student", null, cv);
		
		cv.clear();
		cv.put("sid", 111111);
		cv.put("name", "小明");
		cv.put("cid", 2);
		cv.put("grade", 94);
		db.insert("student", null, cv);
		
		cv.clear();
		cv.put("cid", 1);
		cv.put("sid", 123456);
		cv.put("time", "00-00-00");
		cv.put("content", "难");
		db.insert("comment", null, cv);
		
		cv.clear();
		cv.put("cid", 2);
		cv.put("sid", 111111);
		cv.put("time", "00-00-00");
		cv.put("content", "有意思");
		db.insert("comment", null, cv);
		
	}
}
