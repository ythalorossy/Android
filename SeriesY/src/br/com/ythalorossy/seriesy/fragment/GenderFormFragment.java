package br.com.ythalorossy.seriesy.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import br.com.ythalorossy.seriesy.R;
import br.com.ythalorossy.seriesy.activity.GenderListActivity;
import br.com.ythalorossy.seriesy.activity.OnNavigate;
import br.com.ythalorossy.seriesy.model.Gender;
import br.com.ythalorossy.seriesy.model.repository.GenderRepository;
import br.com.ythalorossy.seriesy.model.repository.Repository;
import br.com.ythalorossy.seriesy.utils.SeriesYUtil;

public class GenderFormFragment extends Fragment {

	private OnNavigate onNavigate;

	private Gender gender;
	private boolean isUpdate = false;

	public GenderFormFragment() {
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		
		if (bundle != null && bundle.getBoolean(SeriesYUtil.IS_UPDATE, false)) {

			isUpdate = bundle.getBoolean(SeriesYUtil.IS_UPDATE);

			this.gender = GenderRepository.getInstance(getActivity()).findById(Long.valueOf(bundle.getString(Gender.GenderContract.COLUMN_ID)));

		} else {

			this.gender = new Gender();
		}
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		this.onNavigate = (OnNavigate) activity;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_gender_form, container, false);

		((Button) rootView.findViewById(R.id.btnSave))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						save(rootView);
					}
				});
		((Button) rootView.findViewById(R.id.btnCancel))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						onNavigate.navigateTo(GenderListActivity.class, null);
					}
				});

		if (isUpdate) {

			((EditText) rootView.findViewById(R.id.editTextName)).setText(this.gender.getName());
		}

		return rootView;
	}

	public void save(View view) {

		EditText name = (EditText) view.findViewById(R.id.editTextName);
		this.gender.setName(name.getText().toString());

		Repository<Gender> repository = GenderRepository.getInstance(getActivity());

		if (isUpdate) {

			this.gender = repository.update(this.gender);

		} else {

			this.gender = repository.save(this.gender);
		}

		this.onNavigate.navigateTo(GenderListActivity.class, null);
	}

}
