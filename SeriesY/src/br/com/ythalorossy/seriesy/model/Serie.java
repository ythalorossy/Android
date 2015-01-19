package br.com.ythalorossy.seriesy.model;

import android.provider.BaseColumns;

public class Serie {

	private long id;
	private String title;
	private Gender gender;
	private String cast;
	private String imageName;
	private String synopsis;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public long getId() {
		return id;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public static class SerieContract implements BaseColumns {

		public static final String PREFIX = "s";
		public static final String TABLE_NAME = "serie";
		public static final String COLUMN_ID =  PREFIX + _ID;
		public static final String COLUMN_TITLE = PREFIX + "_title";
		public static final String COLUMN_ID_GENDER = PREFIX + "_id_gender";
		public static final String COLUMN_IMAGE_NAME = PREFIX +"_image_name";
		public static final String COLUMN_SYNOPSIS = PREFIX +"_synopsis";
		public static final String DEFAULT_ORDER = COLUMN_ID + " ASC";
	}
}