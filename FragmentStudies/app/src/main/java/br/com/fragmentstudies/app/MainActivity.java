package br.com.fragmentstudies.app;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance("Main Fragment"))
                    .commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void nextFragment(View v) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance("Second Fragment"))
                .commit();
    }

    public static class PlaceholderFragment extends Fragment {

        public String texto;

        public PlaceholderFragment() { }

        public static PlaceholderFragment newInstance(String text){

            PlaceholderFragment frag = new PlaceholderFragment();
            frag.texto = text;
            return frag;
        }

        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Toast.makeText(getActivity(), "Activity Created", Toast.LENGTH_SHORT).show();
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            if (savedInstanceState != null){
                ((TextView) rootView.findViewById(R.id.textView)).setText(texto);
            }

            return rootView;
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);


        }
    }
}
