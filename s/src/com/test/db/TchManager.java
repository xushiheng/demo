package com.test.db;

import android.content.Context;
import android.database.Cursor;

public class TchManager extends Manager{

	private int tchId;
	private String tchName;
	private Cursor temp;
	
	public TchManager (Context context, int tchId)
	{
		super(context);
		
		this.tchId = tchId;
		temp = db.query(TCH, null, "tid=?", new String[]{String.valueOf(tchId)}, null, null, null);

		if(temp.getCount() == 0)
		{
			this.tchName = "default";
		}else
		{
			temp.moveToFirst();
			this.tchName = temp.getString(temp.getColumnIndex("name"));
		}
		
	}
	
	
	public Cursor showMyCourse()
	{
		String[] columns = {"name"};
		String selection = "tid=?";
		String[] selectionArgs = {String.valueOf(tchId)};
		
		return db.query(COS, columns, selection, selectionArgs, null, null, null);
	}
	
	public void createCos(String cosName, String time, String place, String intro)
	{
		super.AddToCos(cosName, tchId, time, place, intro);
	}
	
	public Cursor showStudent(int cId)
	{
		String[] columns = {"name","sid"};
		String selection = "cid=?";
		String[] selectionArgs = {String.valueOf(cId)};
		
		return db.query(STU, columns, selection, selectionArgs, null, null, null);
	}
	
	private void close()
	{
		db.close();
		dbHelper.close();
	}
}
