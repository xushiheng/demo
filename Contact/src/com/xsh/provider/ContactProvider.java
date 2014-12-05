package com.xsh.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ContactProvider extends ContentProvider{
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CONTACTS = 1;
    private static final int CONTACT = 2;
    private DatabaseHelper dbOpenHelper;
    static{
    	matcher.addURI(Words.AUTHORITY, "contacts", CONTACTS);
    	matcher.addURI(Words.AUTHORITY, "cotact/#", CONTACT);
    }
	
	@Override
	public int delete(Uri arg0, String where, String[] whereArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		int num = 0;
		switch (matcher.match(arg0)){
		case CONTACTS:
			num = db.delete("contact" , where , whereArgs);
			break;
		case CONTACT:
			long id = ContentUris.parseId(arg0);
			String whereClause = Words.Word.KEY_ID + "=" + id;
			if (where != null && !where.equals("")){
				whereClause = whereClause + " and " + where;
			}
			num = db.delete("contact" , whereClause , whereArgs);
			break;
		default:
			throw new IllegalArgumentException("未知URI:" + arg0);
		}
		getContext().getContentResolver().notifyChange(arg0, null);
		return num;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		switch(matcher.match(arg0)){
		case CONTACTS:
			return "vnd.android.cursor.dir/com.xsh.contact";
		case CONTACT:
			return "vnd.android.cursor.item/com.xsh.contact";
		default:
			throw new IllegalArgumentException("未知URI:" + arg0);
		}
	}

	@Override
	public Uri insert(Uri arg0, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		switch(matcher.match(arg0)){
		case CONTACTS:
			long rowId = db.insert("contact", Words.Word.KEY_ID, values);
			if(rowId > 0){
				Uri contactUri = ContentUris.withAppendedId(arg0, rowId);
				getContext().getContentResolver().notifyChange(contactUri, null);
				return contactUri;
			}
			break;
		default:
			throw new IllegalArgumentException("未知URI:" + arg0);
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbOpenHelper = new DatabaseHelper(this.getContext());
		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] projection, String where, String[] whereArgs,
			String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		switch(matcher.match(arg0)){
		case CONTACTS:
			return db.query("contact",projection,where,whereArgs,null,null,sortOrder);
		case CONTACT:
			long id = ContentUris.parseId(arg0);
			String whereClause = Words.Word.KEY_ID + "=" + id;
			if(where != null && !"".equals(where)){
				whereClause = whereClause + " and " + where;
			}
			return db.query("contact",projection,whereClause,whereArgs,null,null,sortOrder);
		default:
			throw new IllegalArgumentException("未知URI:" + arg0);
		}
	}

	@Override
	public int update(Uri arg0, ContentValues values, String where, String[] whereArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		int num = 0;
		switch(matcher.match(arg0)){
		case CONTACTS:
			num = db.update("contact", values, where, whereArgs);
			break;
		case CONTACT:
			long id = ContentUris.parseId(arg0);
			String whereClause = Words.Word.KEY_ID + id;
			if(where != null && !where.equals("")){
				whereClause = whereClause + " and " + where;
			}
			num = db.update("contact", values, whereClause, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("未知URI:" + arg0);
		}
		getContext().getContentResolver().notifyChange(arg0, null);
		return num;
	}

}
