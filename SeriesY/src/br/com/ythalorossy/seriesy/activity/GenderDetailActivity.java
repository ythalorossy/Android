package br.com.ythalorossy.seriesy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.fragment.GenderDetailFragment;
import br.com.ythalorossy.seriesy.model.Serie;

public class GenderDetailActivity 
	extends FragmentActivity 
		implements OnNavigate {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_gender_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putString(Serie.SerieContract.COLUMN_ID, getIntent().getStringExtra(Serie.SerieContract.COLUMN_ID));
			
			GenderDetailFragment fragment = new GenderDetailFragment();
			fragment.setArguments(arguments);
			
			getSupportFragmentManager()
				.beginTransaction()
					.add(R.id.gender_detail_container, fragment)
				.commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, GenderListActivity.class));
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
