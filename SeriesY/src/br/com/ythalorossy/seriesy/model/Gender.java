package br.com.ythalorossy.seriesy.model;

import android.provider.BaseColumns;

public class Gender {

	private long id;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static class GenderContract implements BaseColumns {
		
		public static final String PREFIX = "g";
		public static final String TABLE_NAME = "gender";
		public static final String COLUMN_ID = PREFIX +_ID;
		public static final String COLUMN_NAME = PREFIX +"_name";
		public static final String DEFAULT_ORDER = COLUMN_ID + " ASC";
	}
	
}
