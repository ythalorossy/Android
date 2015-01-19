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
import br.com.ythalorossy.seriesy.fragment.SerieDetailFragment;
import br.com.ythalorossy.seriesy.fragment.SerieFormFragment;
import br.com.ythalorossy.seriesy.fragment.SerieListFragment;
import br.com.ythalorossy.seriesy.model.Serie;

public class SerieListActivity extends FragmentActivity implements
		SerieListFragment.Callbacks<Serie>{

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serie_list);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (findViewById(R.id.serie_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((SerieListFragment) getSupportFragmentManager().findFragmentById(R.id.serie_list)).setActivateOnItemClick(true);
		}
	}

	/**
	 * Callback method from {@link SerieListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Serie serie) {

		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(Serie.SerieContract.COLUMN_ID, String.valueOf(serie.getId()));

			SerieDetailFragment fragment = new SerieDetailFragment();
			fragment.setArguments(arguments);

			getSupportFragmentManager()
				.beginTransaction()
					.replace(R.id.serie_detail_container, fragment)
				.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, SerieDetailActivity.class);
			detailIntent.putExtra(Serie.SerieContract.COLUMN_ID, String.valueOf(serie.getId()));
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
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
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
				
				Intent intent = new Intent(this, SerieFormActivity.class);
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
