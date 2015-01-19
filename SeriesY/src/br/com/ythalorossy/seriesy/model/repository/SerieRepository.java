package br.com.ythalorossy.seriesy.model.repository;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import br.com.ythalorossy.seriesy.model.Gender;
import br.com.ythalorossy.seriesy.model.Gender.GenderContract;
import br.com.ythalorossy.seriesy.model.Serie;
import br.com.ythalorossy.seriesy.model.Serie.SerieContract;
import br.com.ythalorossy.seriesy.model.sqlite.helper.SeriesYSQLiteHelper;

public class SerieRepository extends Repository<Serie> {

	private Context context;

	private SeriesYSQLiteHelper helper;

	private SQLiteDatabase db;

	private static SerieRepository instance;

	public static SerieRepository getInstance(Context context) {
		
		if (instance == null) {
			instance = new SerieRepository(context);
		}
		
		return instance;
	}
	
	public void close(){
		if (instance.db.isOpen()) {
			instance.db.close();
		}
	}
	
	private SerieRepository(Context context) {
		
		this.context = context;
		
		helper = new SeriesYSQLiteHelper(context);
		
		db = helper.getWritableDatabase();
	}

	public Context getContext() {
		return context;
	}
	
	public Serie findById(Long id) {
		
		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		Serie serie = null;
		
//		Cursor cursor = db.query(true, SerieContract.TABLE_NAME, null, SerieContract.COLUMN_ID + "=" +id, null, null, null, SerieContract.DEFAULT_ORDER, null);
		
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(
				SerieContract.TABLE_NAME + 
				" LEFT OUTER JOIN " + GenderContract.TABLE_NAME + 
				" ON " + SerieContract.TABLE_NAME + "." + SerieContract.COLUMN_ID_GENDER + " = " + GenderContract.TABLE_NAME + "." + GenderContract.COLUMN_ID);		
		builder.appendWhere(SerieContract.COLUMN_ID + "=" +id);
		
		Cursor cursor = builder.query(db, null, null, null, null, null, SerieContract.COLUMN_TITLE + " ASC");
		
		if (cursor.getCount() > 0) {
			
			cursor.moveToFirst();
			
			serie = cursorToSerie(cursor);
		}
		
		instance.close();
		
		return serie;
	}

	public List<Serie> findAll() {
		
		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		List<Serie> series = new ArrayList<Serie>();
		
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(
				SerieContract.TABLE_NAME + 
				" LEFT OUTER JOIN " + GenderContract.TABLE_NAME + 
				" ON " + SerieContract.TABLE_NAME + "." + SerieContract.COLUMN_ID_GENDER + " = " + GenderContract.TABLE_NAME + "." + GenderContract.COLUMN_ID);
		
//		Cursor cursor = db.query(true, SerieContract.TABLE_NAME, null, null, null, null, null, SerieContract.DEFAULT_ORDER, null);
		
		Cursor cursor = builder.query(db, null, null, null, null, null, SerieContract.COLUMN_TITLE + " ASC");
		
		
		if (cursor.moveToFirst()) {
			do {
				series.add(cursorToSerie(cursor));
				
			} while (cursor.moveToNext());
		}
		
		instance.close();
		
		return series;
	}
	
	public Serie save(Serie serie) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(SerieContract.COLUMN_TITLE, serie.getTitle());
		contentValues.put(SerieContract.COLUMN_ID_GENDER, serie.getGender().getId());
		contentValues.put(SerieContract.COLUMN_SYNOPSIS, serie.getSynopsis());
		
		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		long id = db.insert(SerieContract.TABLE_NAME, null, contentValues);
		
		serie.setId(id);
		
		return serie;
	}
	
	private Serie cursorToSerie(Cursor cursor) {
		Serie serie = new Serie();
		serie.setId(cursor.getLong(cursor.getColumnIndex(SerieContract.COLUMN_ID)));
		serie.setTitle(cursor.getString(cursor.getColumnIndex(SerieContract.COLUMN_TITLE)));
		
		Long gender_id = cursor.getLong(cursor.getColumnIndex(GenderContract.TABLE_NAME+"."+GenderContract.COLUMN_ID));		
		String gender_name = cursor.getString(cursor.getColumnIndex(GenderContract.COLUMN_NAME));
		
		Gender gender = new Gender();
		gender.setId(gender_id);
		gender.setName(gender_name);
		serie.setGender(gender);
		serie.setImageName(cursor.getString(cursor.getColumnIndex(SerieContract.COLUMN_IMAGE_NAME)));
		serie.setSynopsis(cursor.getString(cursor.getColumnIndex(SerieContract.COLUMN_SYNOPSIS)));
		return serie;
	}

	public Serie update(Serie serie) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(SerieContract.COLUMN_TITLE, serie.getTitle());
		contentValues.put(SerieContract.COLUMN_ID_GENDER, serie.getGender().getId());
		contentValues.put(SerieContract.COLUMN_SYNOPSIS, serie.getSynopsis());
		contentValues.put(SerieContract.COLUMN_IMAGE_NAME, serie.getImageName());
		
		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		db.update(SerieContract.TABLE_NAME, contentValues, SerieContract.COLUMN_ID + "=?", new String[]{String.valueOf(serie.getId())});
		
		instance.close();
		
		return serie;
	}

	public void delete(Serie serie) {

		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		db.delete(SerieContract.TABLE_NAME, SerieContract.COLUMN_ID + "=?", new String[]{String.valueOf(serie.getId())});
		
		instance.close();
	}
}
