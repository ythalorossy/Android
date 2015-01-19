package br.com.ythalorossy.seriesy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.fragment.SerieFormFragment;
import br.com.ythalorossy.seriesy.utils.SeriesYUtil;

public class SerieFormActivity extends FragmentActivity implements OnNavigate {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_serie_form);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {

			SerieFormFragment formFragment = new SerieFormFragment();

			if (getIntent().getBooleanExtra(SeriesYUtil.IS_UPDATE, false)) {

				formFragment.setArguments(getIntent().getExtras());
			}

			getSupportFragmentManager()
				.beginTransaction()
					.add(R.id.serie_form_container, formFragment)
						.commit();
		}

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
			NavUtils.navigateUpTo(this, new Intent(this, SerieListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void navigateTo(Class<?> clazz, Bundle bundle) {

		finish();
		
		NavUtils.navigateUpFromSameTask(this);
	}

}
