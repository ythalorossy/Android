package br.com.ythalorossy.seriesy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.fragment.SerieDetailFragment;
import br.com.ythalorossy.seriesy.model.Serie;

/**
 * An activity representing a single Serie detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link SerieListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link SerieDetailFragment}.
 */
public class SerieDetailActivity extends FragmentActivity implements OnNavigate {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_serie_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(Serie.SerieContract.COLUMN_ID, getIntent().getStringExtra(Serie.SerieContract.COLUMN_ID));
			
			SerieDetailFragment fragment = new SerieDetailFragment();
			fragment.setArguments(arguments);
			
			getSupportFragmentManager()
				.beginTransaction()
					.add(R.id.serie_detail_container, fragment)
				.commit();
		}
	}

	@Override
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
			NavUtils.navigateUpTo(this, new Intent(this, SerieListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void navigateTo(Class<?> clazz, Bundle bundle) {
		
		finish();
		
		Intent intent = new Intent(this, clazz);
		if (bundle != null) intent.putExtras(bundle);
		
		startActivity(intent);
		
	}
}
