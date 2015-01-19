package br.com.ythalorossy.seriesy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.fragment.GenderListFragment;
import br.com.ythalorossy.seriesy.fragment.SerieDetailFragment;
import br.com.ythalorossy.seriesy.fragment.SerieFormFragment;
import br.com.ythalorossy.seriesy.fragment.SerieListFragment;
import br.com.ythalorossy.seriesy.model.Gender;

public class GenderListActivity 
	extends FragmentActivity 
		implements GenderListFragment.Callbacks<Gender>{

	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_gender_list);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// TODO Large Layout
		if (findViewById(R.id.serie_detail_container) != null) {
			mTwoPane = true;
			((SerieListFragment) getSupportFragmentManager().findFragmentById(R.id.serie_list)).setActivateOnItemClick(true);
		}
	}

	@Override
	public void onItemSelected(Gender gender) {

		if (mTwoPane) {

			Bundle arguments = new Bundle();
			arguments.putString(Gender.GenderContract.COLUMN_ID, String.valueOf(gender.getId()));

			SerieDetailFragment fragment = new SerieDetailFragment();
			fragment.setArguments(arguments);

			getSupportFragmentManager()
				.beginTransaction()
					.replace(R.id.gender_detail_container, fragment)
				.commit();

		} else {
			Intent detailIntent = new Intent(this, GenderDetailActivity.class);
			detailIntent.putExtra(Gender.GenderContract.COLUMN_ID, String.valueOf(gender.getId()));
			startActivity(detailIntent);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_actions, menu);

		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
		return true;
		case R.id.action_add:

			if (mTwoPane) {
				
				SerieFormFragment formFragment = new SerieFormFragment();
				getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.serie_detail_container, formFragment)
					.commit();
			} else {
				
				Intent intent = new Intent(this, GenderFormActivity.class);
				startActivity(intent);
			}
			
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
//		if (keyCode == KeyEvent.KEYCODE_BACK){
//			
//			new AlertDialog.Builder(this)
//			.setMessage("Are you sure you want to exit?")
//			.setCancelable(true)
//			.setPositiveButton("Yes", new OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					SerieListActivity.this.finish();
//				}
//			})
//			.setNegativeButton("No", new OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.cancel();
//				}
//			})
//			.create()
//			.show();
//			
//		}
		return super.onKeyDown(keyCode, event);
	}
}
