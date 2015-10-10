package com.aparoksha.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBmanager {

	private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + EVENT_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENT_NAME
					+ " TEXT NOT NULL, " + EVENT_TIME + " TEXT  NOT NULL, "
					+ EVENT_DATE + " TEXT NOT NULL, " + EVENT_VENUE
					+ " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
			onCreate(db);
		}

	}

	public static final String	EVENT_ID			= "id";
	public static final String	EVENT_NAME			= "event_name";
	public static final String	EVENT_TIME			= "event_time";
	public static final String	EVENT_DATE			= "event_date";
	public static final String	EVENT_VENUE			= "event_venue";

	private static final String	DATABASE_NAME		= "AparokshaDB";
	private static final String	TABLE_NAME			= "favourites";
	private static final int	DATABASE_VERSION	= 1;

	DBHelper					dbHelper;
	Context						context;
	SQLiteDatabase				sqLiteDatabase;

	public DBmanager(Context context) {
		this.context = context;
	}

	public DBmanager open() throws SQLException {
		dbHelper = new DBHelper(context);
		sqLiteDatabase = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() throws SQLException {
		dbHelper.close();
	}

	public long createEntry(String name, String time, String date, String venue)
			throws SQLException {
		// TODO Auto-generated method stub
		ContentValues contentValues;
		contentValues = new ContentValues();

		contentValues.put(EVENT_NAME, name);
		contentValues.put(EVENT_TIME, time);
		contentValues.put(EVENT_DATE, date);
		contentValues.put(EVENT_VENUE, venue);

		return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

	}

	public String getData() throws SQLException {
		// TODO Auto-generated method stub
		Cursor cursor;
		String result;
		String[] array;
		int iRow;
		int iName;
		int itime, idate, ivenue;

		array = new String[] { EVENT_ID, EVENT_NAME, EVENT_TIME, EVENT_DATE,
				EVENT_VENUE };
		result = "";
		cursor = sqLiteDatabase.query(TABLE_NAME, array, null, null, null,
				null, EVENT_ID + " ASC");

		iRow = cursor.getColumnIndex(EVENT_ID);
		iName = cursor.getColumnIndex(EVENT_NAME);
		itime = cursor.getColumnIndex(EVENT_TIME);
		idate = cursor.getColumnIndex(EVENT_DATE);
		ivenue = cursor.getColumnIndex(EVENT_VENUE);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			result += cursor.getString(iRow) + "\t" + cursor.getString(iName)
					+ "\t" + cursor.getString(itime) + "\t"
					+ cursor.getString(idate) + "\t" + cursor.getString(ivenue)
					+ "\n";
		}

		return result;
	}

	public String[] getEventNames() throws SQLException {
		String columnNames[];
		String favoriteEventNames[];
		int i;
		Cursor cursor;
		int iName;

		columnNames = new String[] { EVENT_ID, EVENT_NAME, EVENT_TIME,
				EVENT_DATE, EVENT_VENUE };
		cursor = sqLiteDatabase.query(TABLE_NAME, columnNames, null, null,
				null, null, EVENT_ID + " ASC");
		iName = cursor.getColumnIndex(EVENT_NAME);
		favoriteEventNames = new String[(int) DatabaseUtils.queryNumEntries(
				sqLiteDatabase, TABLE_NAME)];

		for (cursor.moveToFirst(), i = 0; !cursor.isAfterLast(); cursor
				.moveToNext(), i++) {
			favoriteEventNames[i] = cursor.getString(iName);
		}

		return favoriteEventNames;
	}

	public int getEventCount() throws SQLException {
		return ((int) DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_NAME));
	}

	public int checkentry(String event) throws SQLException {
		// TODO Auto-generated method stub
		Cursor cursor;
		String[] array;
		int iName = 0;

		cursor = null;
		array = new String[] { EVENT_ID, EVENT_NAME, EVENT_TIME, EVENT_DATE,
				EVENT_VENUE };
		try {
			cursor = sqLiteDatabase.query(TABLE_NAME, array, EVENT_NAME
					+ " = ?", new String[] { event }, null, null, null);

		} catch (Exception e) {
			Toast.makeText(context, "Error : " + e, Toast.LENGTH_SHORT).show();
		}
		if (cursor != null)
			iName = cursor.getColumnIndex(EVENT_DATE);

		if (cursor != null) {
			cursor.moveToFirst();
			Toast.makeText(context, cursor.getString(iName), Toast.LENGTH_SHORT)
					.show();
			return 1;
		}

		return 0;
	}

	public int checkentrynew(String event) throws SQLException {

		// Cursor cursor = getReadableDatabase().

		// rawQuery("select * from todo where _id = ?", new String[] { id });

		String count = "SELECT count(*) FROM favourites where  EVENT_NAME = ? "; // where
																					// EVENT_NAME
																					// =
																					// '"+
																					// event+
																					// " ' ";
		Cursor mcursor = sqLiteDatabase.rawQuery(count, new String[] { event });
		mcursor.moveToFirst();
		int icount = mcursor.getInt(0);
		if (icount > 0)
			return 1;
		else
			return 0;
	}

	public void deleteEntry(String event) throws SQLException {
		try {
			sqLiteDatabase.delete(TABLE_NAME, EVENT_NAME + " = ?",
					new String[] { event });
			// sqLiteDatabase.delete(TABLE_NAME,null, null);
			// sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME +
			// ";");
		} catch (Exception e) {
			Toast.makeText(context, "Error = " + e, Toast.LENGTH_SHORT).show();
		}
	}

}
