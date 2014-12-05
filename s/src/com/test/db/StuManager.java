package com.test.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StuManager extends Manager{
	
	private Cursor temp;
	
	public StuManager (Context context, int stuId)
	{
		super(context);
		
		temp = db.query(STU, null, "sid=?", new String[]{String.valueOf(stuId)}, null, null, null);
		
	}
	
//	public void AddData(int stuId, String name, int cId, int grade)
//	{
//		ContentValues cv = new ContentValues();
//		cv.put("sid", stuId);
//		cv.put("name", name);
//		cv.put("cid", cId);
//		cv.put("grade", grade);
//		db.insert(STU, null, cv);
//	}
	
	public Cursor showMyCourse()
	{
		String[] columns = {"name"};
		String selection = "id in (";
		String[] selectionArgs = new String[temp.getCount()];
		int column = temp.getColumnIndex("cid");
		int i=0;

		temp.moveToFirst();
		selection += "?";
		selectionArgs[i++] = temp.getString(column);
		
		while(temp.moveToNext())
		{

			selectionArgs[i++] = temp.getString(column);
			selection += ",?";
		}
		selection += ")";
		
		Cursor cursor;

		cursor = db.query(COS, columns, selection, selectionArgs, null, null, null);
		return cursor;
	}
	
	public Cursor showAllCourse()
	{
		String[] columns = {"name","tid"};
		return db.query(COS, columns, null, null, null, null, null);
	}
	
	public String getName()
	{
		temp.moveToFirst();
		return temp.getString(temp.getColumnIndex("name"));
	}
	
//	public String getCosName(int id)
//	{
//		Cursor cursor;
//		cursor = db.query(COS, new String[]{"name"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
//		cursor.moveToFirst();
//		String name = cursor.getString(cursor.getColumnIndex("name"));
//		return name;
//	}
	
//	public int getCosId(String name)
//	{
//		Cursor cursor;
//		cursor = db.query(COS, new String[]{"id"}, "name=?", new String[]{name}, null, null, null);
//		cursor.moveToFirst();
//		int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
//		return id;
//	}
	
//	public String getTchName(int id)
//	{
//		Cursor cursor;
//		cursor = db.query(TCH, new String[]{"name"}, "tid=?", new String[]{String.valueOf(id)}, null, null, null);
//		cursor.moveToFirst();
//		String name = cursor.getString(cursor.getColumnIndex("name"));
//		return name;
//	}
	
	private void close()
	{
		db.close();
		dbHelper.close();
	}
}
