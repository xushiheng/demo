package com.test.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CosManager extends Manager{

	public CosManager (Context context)
	{
		super(context);
	}
	
//	public void AddData(String name, int tId, String time, String place, String intro)
//	{
//		ContentValues cv = new ContentValues();
//		cv.put("name", name);
//		cv.put("tid", tId);
//		cv.put("time", time);
//		cv.put("place", place);
//		cv.put("intro", intro);
//		db.insert(COS, null, cv);
//	}
	
	public Cursor showDetails(String cName)
	{
		String[] columns = {"name","tid","time","place","intro"};
		String selection = "name=?";
		String[] selectionArgs = {String.valueOf(cName)};
		return db.query(COS, columns, selection, selectionArgs, null, null, null);
	}
	
//	public String getCosName(int id)
//	{
//		Cursor cursor;
//		cursor = db.query(COS, new String[]{"name"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
//		cursor.moveToFirst();
//		String name = cursor.getString(cursor.getColumnIndex("name"));
//		return name;
//	}
//	
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
