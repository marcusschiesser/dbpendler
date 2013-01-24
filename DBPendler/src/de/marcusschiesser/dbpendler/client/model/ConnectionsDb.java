package de.marcusschiesser.dbpendler.client.model;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.marcusschiesser.dbpendler.common.vo.ConnectionVO;
import de.marcusschiesser.dbpendler.common.vo.StationVO;

public class ConnectionsDb extends SQLiteOpenHelper {

	public static final String KEY_ROWID = "_id";

	public static final String KEY_START = "start";
	public static final String KEY_DESTINATION = "destination";
	public static final String KEY_STARTTIME = "startTime";
	public static final String KEY_ENDTIME = "endTime";

	private static final String DATABASE_TABLE = "connections";
	private static final int DATABASE_VERSION = 3;
	
	private static final String DEFAULT_PROJECTION[] = { KEY_ROWID, KEY_START, KEY_DESTINATION, KEY_STARTTIME,
			KEY_ENDTIME };

	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE + " (_id integer primary key autoincrement, "
			+ "start text not null, destination text not null, startTime time not null, endTime time not null);";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ConnectionsDb.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}

	public ConnectionsDb(Context ctx) {
		super(ctx, "data", null, DATABASE_VERSION);
	}

	public long createConnection(ConnectionVO connection) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_START, connection.getStart().getValue());
		initialValues.put(KEY_DESTINATION, connection.getDestination().getValue());
		initialValues.put(KEY_STARTTIME, connection.getStartTime().getTime());
		initialValues.put(KEY_ENDTIME, connection.getDestinationTime().getTime());

		return getWritableDatabase().insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean deleteConnection(long rowId) {
		return getWritableDatabase().delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public Cursor fetchAllConnections() {
		return getWritableDatabase().query(DATABASE_TABLE, DEFAULT_PROJECTION , null, null, null, null, null);
	}

	public Cursor fetchConnection(long rowId) throws SQLException {
		Cursor cursor = getWritableDatabase().query(true, DATABASE_TABLE, DEFAULT_PROJECTION, KEY_ROWID + "=" + rowId,
				null, null, null, null, null);
		
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public boolean updateConnection(long rowId, ConnectionVO connection) {
		ContentValues args = new ContentValues();
		args.put(KEY_START, connection.getStart().getValue());
		args.put(KEY_DESTINATION, connection.getDestination().getValue());
		args.put(KEY_STARTTIME, connection.getStartTime().getTime());
		args.put(KEY_ENDTIME, connection.getDestinationTime().getTime());

		return getWritableDatabase().update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public static ConnectionVO createConnectionFromCursor(Cursor cursor) {
		ConnectionVO connectionVO = new ConnectionVO(new StationVO(cursor.getString(cursor.getColumnIndex(KEY_START))),
				new StationVO(cursor.getString(cursor.getColumnIndex(KEY_DESTINATION))),
				new Date(cursor.getLong(cursor.getColumnIndex(KEY_STARTTIME))),
				new Date(cursor.getLong(cursor.getColumnIndex(KEY_ENDTIME))) );
		return connectionVO;
	}
}
