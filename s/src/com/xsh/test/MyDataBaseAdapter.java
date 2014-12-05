package com.xsh.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseAdapter {
	private static final String DB_NAME = "Dictionarytest.db";
	private static final String DB_TABLE1 = "wordlist1";
	private static final String DB_TABLE2 = "wordlist2";
	private static final String KEY_ID = "_id";
	static final String KEY_WORD = "word";
	static final String KEY_MEANING = "meaning";
	private Context mContext = null;
	private static final int DB_VERSION = 1;
	private static final String DB_CREATE1 = "CREATE TABLE " + DB_TABLE1 + "(" + KEY_ID
					+ " integer primary key autoincrement," + KEY_WORD
					+ " char(15) not null," + KEY_MEANING + " char(15) not null);";
	private static final String DB_CREATE2 = "CREATE TABLE " + DB_TABLE2 + "(" + KEY_ID
			        + " integer primary key autoincrement," + KEY_WORD
			        + " char(15) not null," + KEY_MEANING + " char(15) not null);";
	// 执行open()打开数据库是，保存返回的数据库对象
	private SQLiteDatabase mSQLiteDatabase = null;
	// 由SQLiteOpenHelper继承过来
	private DatabaseHelper mDatabaseHelper = null;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		// 构造函数，创建一个数据库
		DatabaseHelper(Context context) {
			// 当调用getWritableDatabase()或getReadableDatabase()方法时
			// 则创建一个数据库
			super(context, DB_NAME, null, DB_VERSION);

		}

		// 创建一个表
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE1);// 数据库没有表示创建一个
			db.execSQL(DB_CREATE2);
		}

		// 升级数据库
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO 自动生成的方法存根
			db.execSQL("drop table if exits notes");
			onCreate(db);		
		}
	}

	// 构造函数，取得Context
	public MyDataBaseAdapter(Context context) {
		mContext = context;
	}

	// 打开数据库，返回数据库对象

	public void open() throws SQLException {
		mDatabaseHelper = new DatabaseHelper(mContext);
		mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
	}

	// 关闭数据库

	public void close() {
		mDatabaseHelper.close();
	}

	// 插入数据
	public long insertData(String word, String meaning) {
		ContentValues initialValeus = new ContentValues();
		initialValeus.put(KEY_WORD, word);
		initialValeus.put(KEY_MEANING, meaning);
		return mSQLiteDatabase.insert(DB_TABLE1, KEY_ID, initialValeus);
	}
	
	public long insertData2(String word, String meaning) {
		ContentValues initialValeus = new ContentValues();
		initialValeus.put(KEY_WORD, word);
		initialValeus.put(KEY_MEANING, meaning);
		return mSQLiteDatabase.insert(DB_TABLE2, KEY_ID, initialValeus);
	}

	// 删除数据
	public long deleteData(String word) {
		return mSQLiteDatabase.delete(DB_TABLE2, KEY_WORD + " like ?", new String[]{word + "%"});
	}
	
    public Cursor fetchAllData(){
        	return mSQLiteDatabase.query(DB_TABLE2,new String[]{KEY_ID,KEY_WORD,KEY_MEANING},null,null,null,null,null);
               }
	
	// 查找指定数据
	public Cursor fetchData(String word) throws SQLException {
		Cursor mCursor = mSQLiteDatabase.query(true, DB_TABLE1, new String[] {KEY_ID ,KEY_WORD, KEY_MEANING }, KEY_WORD
						+ " like ? ", new String[] { word + "%" }, null, null, null, null);
		return mCursor;
	}
}
