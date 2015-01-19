package br.com.ythalorossy.seriesy.model.sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.ythalorossy.seriesy.model.Gender.GenderContract;
import br.com.ythalorossy.seriesy.model.Serie.SerieContract;

public class SeriesYSQLiteHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "SeriesY.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String NOT_NULL = " NOT NULL";
	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_GENDERS = "CREATE TABLE "
			+ GenderContract.TABLE_NAME + " ( " + GenderContract.COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
			+ GenderContract.COLUMN_NAME + TEXT_TYPE +
			" );";
	
	private static final String SQL_CREATE_SERIES = "CREATE TABLE "
			+ SerieContract.TABLE_NAME + " (" + SerieContract.COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
			+ SerieContract.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP
			+ SerieContract.COLUMN_IMAGE_NAME + TEXT_TYPE + COMMA_SEP
			+ SerieContract.COLUMN_SYNOPSIS + TEXT_TYPE + COMMA_SEP 
			+ SerieContract.COLUMN_ID_GENDER + INTEGER_TYPE + NOT_NULL + " REFERENCES " + GenderContract.TABLE_NAME + "("+ GenderContract.COLUMN_ID +")"
			+ " );";

	private static final String SQL_DELETE_SERIES = "DROP TABLE IF EXISTS "
			+ SerieContract.TABLE_NAME;

	public SeriesYSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_GENDERS);
		db.execSQL(SQL_CREATE_SERIES);
		
		String genderSql = "insert into gender ("+GenderContract.COLUMN_NAME+") values ('teste')";
		db.execSQL(genderSql);
		
		for (int i = 0; i < 20; i++) {
			String SQL_INSERT_SERIES = 
					"INSERT INTO " + SerieContract.TABLE_NAME 
					+ "(" 
						+ SerieContract.COLUMN_TITLE + COMMA_SEP 
						+ SerieContract.COLUMN_ID_GENDER + COMMA_SEP
						+ SerieContract.COLUMN_SYNOPSIS + COMMA_SEP
						+ SerieContract.COLUMN_IMAGE_NAME
						+ ") " +
					"VALUES (" +
					"'The Killing', " +
					"'1', " +
					"'The second season of the AMC American crime drama television series The Killing premiered on April 1, 2012, concluded on June 17, 2012, and consisted of 13 episodes. The series was developed and produced by Veena Sud and based on the Danish series, Forbrydelsen (The Crime). Set in Seattle, Washington, this season follows the continued investigation into the murder of local teenager Rosie Larsen, with each episode covering approximately 24 hours. The season culminated in the closing of the Larsen murder, with the discovery of those involved with the murder.'," +
					"'the_killing.jpg')";
			
			db.execSQL(SQL_INSERT_SERIES);
		}
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.w(SeriesYSQLiteHelper.class.getName(),
				String.format(
						"Upgrading database from version %s to %s. All old data will be destroyed.",
						oldVersion, newVersion));

		db.execSQL(SQL_DELETE_SERIES);

		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		super.onDowngrade(db, oldVersion, newVersion);
	}
}
