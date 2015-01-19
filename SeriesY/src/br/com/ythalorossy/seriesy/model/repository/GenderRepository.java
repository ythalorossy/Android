package br.com.ythalorossy.seriesy.model.repository;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.ythalorossy.seriesy.model.Gender;
import br.com.ythalorossy.seriesy.model.Gender.GenderContract;
import br.com.ythalorossy.seriesy.model.sqlite.helper.SeriesYSQLiteHelper;

public class GenderRepository extends Repository<Gender> {

	private Context context;

	private SeriesYSQLiteHelper helper;

	private SQLiteDatabase db;

	private static GenderRepository instance;

	public static GenderRepository getInstance(Context context) {
		
		if (instance == null) {
			instance = new GenderRepository(context);
		}
		
		return instance;
	}
	
	public void close(){
		if (instance.db.isOpen()) {
			instance.db.close();
		}
	}
	
	private GenderRepository(Context context) {
		
		this.context = context;
		
		helper = new SeriesYSQLiteHelper(context);
		
		db = helper.getWritableDatabase();
	}

	public Context getContext() {
		return context;
	}
	
	public Gender findById(Long id) {
		
		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		Gender gender = null;
		
		Cursor cursor = db.query(true, GenderContract.TABLE_NAME, null, GenderContract.COLUMN_ID + "=" +id, null, null, null, GenderContract.DEFAULT_ORDER, null);
		
		if (cursor.getCount() > 0) {
			
			cursor.moveToFirst();
			
			gender = cursorToGender(cursor);
		}
		
		instance.close();
		
		return gender;
	}

	public List<Gender> findAll() {
		
		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		List<Gender> genders = new ArrayList<Gender>();
		
		Cursor cursor = db.query(true, GenderContract.TABLE_NAME, null, null, null, null, null, GenderContract.DEFAULT_ORDER, null);
		
		if (cursor.moveToFirst()) {
			do {
				genders.add(cursorToGender(cursor));
				
			} while (cursor.moveToNext());
		}
		
		instance.close();
		
		return genders;
	}
	
	public Gender save(Gender gender) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(GenderContract.COLUMN_NAME, gender.getName());
		
		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		long id = db.insert(GenderContract.TABLE_NAME, null, contentValues);
		
		gender.setId(id);
		
		return gender;
	}
	
	private Gender cursorToGender(Cursor cursor) {
		Gender gender = new Gender();
		gender.setId(cursor.getLong(cursor.getColumnIndex(GenderContract.COLUMN_ID)));
		gender.setName(cursor.getString(cursor.getColumnIndex(GenderContract.COLUMN_NAME)));
		return gender;
	}

	public Gender update(Gender gender) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(GenderContract.COLUMN_NAME, gender.getName());
		
		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		db.update(GenderContract.TABLE_NAME, contentValues, GenderContract.COLUMN_ID + "=?", new String[]{String.valueOf(gender.getId())});
		
		instance.close();
		
		return gender;
	}

	public void delete(Gender gender) {

		if (!instance.db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		
		db.delete(GenderContract.TABLE_NAME, GenderContract.COLUMN_ID + "=?", new String[]{String.valueOf(gender.getId())});
		
		instance.close();
	}
}
