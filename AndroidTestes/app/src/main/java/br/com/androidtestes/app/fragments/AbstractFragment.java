package br.com.androidtestes.app.fragments;


import android.app.Fragment;

public abstract class AbstractFragment extends Fragment {

    public interface FragmentCallbacks {

        public enum CallbackType { ATTACHED, DETTACHED}

        public void onFragmentInteractive(CallbackType callbackType);
    }

    protected static final String ARG_SECTION_NUMBER = "section_number";

}
