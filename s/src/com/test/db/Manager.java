package com.test.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Manager {

	private final static String TAG = "Mansger";
	
	protected static String STU = "student";
	protected static String COS = "course";
	protected static String CMT = "comment";
	protected static String TCH = "teacher";
	
	
	protected DatabaseHelper dbHelper;
	protected SQLiteDatabase db;
	
	
	public Manager (Context context)
	{
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public void AddToStu(int stuId, String name, int cId, int grade)
	{
		ContentValues cv = new ContentValues();
		cv.put("sid", stuId);
		cv.put("name", name);
		cv.put("cid", cId);
		cv.put("grade", grade);
		db.insert(STU, null, cv);
	}
	
	public void AddToCos(String name, int tId, String time, String place, String intro)
	{
		ContentValues cv = new ContentValues();
		cv.put("name", name);
		cv.put("tid", tId);
		cv.put("time", time);
		cv.put("place", place);
		cv.put("intro", intro);
		db.insert(COS, null, cv);
	}
	
	public void AddToTch(int tchId, String name, int age, String contact)
	{
		ContentValues cv = new ContentValues();
		cv.put("tid", tchId);
		cv.put("name", name);
		cv.put("age", age);
		cv.put("contact", contact);
		db.insert(TCH, null, cv);
	}
	
	public void AddToCmt(int cId, int sId, String time, String content)
	{
		ContentValues cv = new ContentValues();
		cv.put("cid", cId);
		cv.put("sid", sId);
		cv.put("time", time);
		cv.put("content", content);
		db.insert(CMT, null, cv);
	}
	
	public String getCosName(int id)
	{
		Cursor cursor;
		cursor = db.query(COS, new String[]{"name"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
		cursor.moveToFirst();
		String name = cursor.getString(cursor.getColumnIndex("name"));
		return name;
	}
	
	public int getCosId(String name)
	{
		Cursor cursor;
		cursor = db.query(COS, new String[]{"id"}, "name=?", new String[]{name}, null, null, null);
		cursor.moveToFirst();
		int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
		return id;
	}
	
	public String getTchName(int id)
	{
		Cursor cursor;
		cursor = db.query(TCH, new String[]{"name"}, "tid=?", new String[]{String.valueOf(id)}, null, null, null);
		cursor.moveToFirst();
		String name = cursor.getString(cursor.getColumnIndex("name"));
		return name;
	}
	
	public String getStuName(int id)
	{
		Cursor cursor;
		cursor = db.query(STU, new String[]{"name"}, "sid=?", new String[]{String.valueOf(id)}, null, null, null);
		cursor.moveToFirst();
		String name = cursor.getString(cursor.getColumnIndex("name"));
		return name;
	}
}
