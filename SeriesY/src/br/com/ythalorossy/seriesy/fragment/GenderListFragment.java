package br.com.ythalorossy.seriesy.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import br.com.ythalorossy.seriesy.adapters.GenderListAdapter;
import br.com.ythalorossy.seriesy.model.Gender;
import br.com.ythalorossy.seriesy.model.repository.GenderRepository;


public class GenderListFragment extends ListFragment {

	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private Callbacks<Gender> mCallbacks = sDummyCallbacks;

	private int mActivatedPosition = ListView.INVALID_POSITION;

	public interface Callbacks<T> {
		public void onItemSelected(T t);
	}

	private static Callbacks<Gender> sDummyCallbacks = new Callbacks<Gender>() {
		public void onItemSelected(Gender t) {
		}
	};

	private GenderListAdapter genderListAdapter;

	public GenderListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		List<Gender> genders = GenderRepository.getInstance(getActivity()).findAll();
		
		genderListAdapter = new GenderListAdapter(getActivity(), genders);
		
		setListAdapter(genderListAdapter);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		super.onViewCreated(view, savedInstanceState);

		if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			
			setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@SuppressWarnings("unchecked")
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks<Gender>) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		mCallbacks.onItemSelected((Gender)genderListAdapter.getItem(position));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	public void setActivateOnItemClick(boolean activateOnItemClick) {
		getListView().setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
}
