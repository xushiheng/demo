package com.test.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class CmtManager extends Manager{

	public CmtManager(Context context) {
		super(context);
	}

	public Cursor showComment(int cId)
	{
		Log.e("ok", "2");
		Cursor cursor;
		cursor = db.query(CMT, new String[]{"sid","content"}, "cid=?", new String[]{String.valueOf(cId)}, null, null, null);

		Log.e("ok", "4");
		return cursor;
	}
}
