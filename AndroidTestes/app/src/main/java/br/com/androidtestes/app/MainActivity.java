package br.com.androidtestes.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.androidtestes.app.fragments.AbstractFragment;
import br.com.androidtestes.app.fragments.CorridasPendentesFragment;
import br.com.androidtestes.app.fragments.FragmentFactory;
import br.com.androidtestes.app.fragments.PlaceholderFragment;


public class MainActivity
        extends Activity
        implements
            NavigationDrawerFragment.NavigationDrawerCallbacks,
            AbstractFragment.FragmentCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        if (position != 3) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, FragmentFactory.get(PlaceholderFragment.class, position + 1))
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, FragmentFactory.get(CorridasPendentesFragment.class, position + 1))
                    .commit();
        }

        this.onSectionAttached(position + 1);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_corridas_pendentes);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return (id == R.id.action_settings) || super.onOptionsItemSelected(item);
    }

    public void goMeuTeste(View v){

        startActivity(new Intent(this, MeuTesteDrawerMenuActivity.class));
    }

    public void onFragmentInteractive(CallbackType callbackType) {

        switch (callbackType){
            case ATTACHED:
                break;
            case DETTACHED: break;
        }

    }
}
