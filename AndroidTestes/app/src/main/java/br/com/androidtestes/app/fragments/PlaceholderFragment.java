package br.com.androidtestes.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.androidtestes.app.MainActivity;
import br.com.androidtestes.app.R;

public class PlaceholderFragment extends AbstractFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FragmentCallbacks) activity).onFragmentInteractive(FragmentCallbacks.CallbackType.ATTACHED);
    }
}
