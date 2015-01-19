package br.com.ythalorossy.seriesy.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class FileUtil {

	private static final String DOT = ".";
	
	public static final int 	IMG_RESULT_LOAD = 1;
	public static final String 	IMG_NAME_PREFIX = "SY";
	public static final String 	IMG_EXTENSION = "PNG";
	
	public static File getRootDirectory() {
	
		File root = new File(Environment.getExternalStorageDirectory() + File.separator + SeriesYUtil.APP_NAME + File.separator);
		
		return root;
	}
	
	public static File getFile(File root, String fileName, String extension) {
	
		File imageFile = new File(root, fileName + DOT + extension);
		
		return imageFile;
	}
	
	public static String getImagePath(Context context, Intent data) {
		
		Uri selectedImage = data.getData();

		String[] filePathCollumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = context.getContentResolver().query(selectedImage, filePathCollumn, null, null, null);

		cursor.moveToFirst();

		int ColumnIndex = cursor.getColumnIndex(filePathCollumn[0]);
		String picturePath = cursor.getString(ColumnIndex);
		cursor.close();
		
		return picturePath;
	}

}
