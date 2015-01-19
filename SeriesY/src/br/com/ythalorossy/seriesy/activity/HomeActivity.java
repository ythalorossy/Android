package br.com.ythalorossy.seriesy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.utils.SeriesYUtil;

public class HomeActivity extends FragmentActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.home);
	}

	public void goForm(View view){
		
		Intent intent = new Intent(this, SerieFormActivity.class);
		
		Bundle bundle = new Bundle();
		bundle.putBoolean(SeriesYUtil.IS_UPDATE, false);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	public void goList(View view){
		
		Intent intent = new Intent(this, SerieListActivity.class);
		
		startActivity(intent);
	}

	public void goListGender(View view){
		
		Intent intent = new Intent(this, GenderListActivity.class);
		
		startActivity(intent);
	}	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK){
			
			new AlertDialog.Builder(this)
			.setMessage("Are you sure you want to exit?")
			.setCancelable(true)
			.setPositiveButton("Yes", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.setNegativeButton("No", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.create()
			.show();
			
		}
		return super.onKeyDown(keyCode, event);
	}

	
}
