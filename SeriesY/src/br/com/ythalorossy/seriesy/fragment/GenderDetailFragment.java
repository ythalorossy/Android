package br.com.ythalorossy.seriesy.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.activity.GenderFormActivity;
import br.com.ythalorossy.seriesy.activity.GenderListActivity;
import br.com.ythalorossy.seriesy.activity.OnNavigate;
import br.com.ythalorossy.seriesy.model.Gender;
import br.com.ythalorossy.seriesy.model.repository.GenderRepository;
import br.com.ythalorossy.seriesy.utils.SeriesYUtil;

public class GenderDetailFragment extends Fragment implements OnClickListener {

	private OnNavigate onNavigate;
	
	private Gender gender;

	public GenderDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(Gender.GenderContract.COLUMN_ID)) {
			
			gender = GenderRepository.getInstance(getActivity()).findById(Long.valueOf(getArguments().getString(Gender.GenderContract.COLUMN_ID)));
		}
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		onNavigate = (OnNavigate) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_gender_detail, container, false);

		if (gender != null) {
			
			((Button) rootView.findViewById(R.id.btn_edit)).setOnClickListener(this);
			((Button) rootView.findViewById(R.id.btn_delete)).setOnClickListener(this);
			
			((TextView) rootView.findViewById(R.id.name)).setText(gender.getName());
		}

		return rootView;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_edit:	
				
				Bundle arguments = new Bundle();
				arguments.putString(Gender.GenderContract.COLUMN_ID, String.valueOf(gender.getId()));
				arguments.putBoolean(SeriesYUtil.IS_UPDATE, true);
				onNavigate.navigateTo(GenderFormActivity.class, arguments);
			break;
			case R.id.btn_delete:
					
				GenderRepository.getInstance(getActivity()).delete(gender);
				
				onNavigate.navigateTo(GenderListActivity.class, null);
					
			break;
		}
	}
}
