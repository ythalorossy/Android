package br.com.ythalorossy.seriesy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.fragment.GenderFormFragment;
import br.com.ythalorossy.seriesy.utils.SeriesYUtil;

public class GenderFormActivity extends FragmentActivity implements OnNavigate {

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_gender_form);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {

			GenderFormFragment formFragment = new GenderFormFragment();

			if (getIntent().getBooleanExtra(SeriesYUtil.IS_UPDATE, false)) {

				formFragment.setArguments(getIntent().getExtras());
			}

			getSupportFragmentManager()
				.beginTransaction()
					.add(R.id.gender_form_container, formFragment)
						.commit();
		}

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
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
