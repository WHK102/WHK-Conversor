package com.drawcoders.conversor;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Integer navigationDrawerItemSelected = 0;
    private Boolean show_convert_icon = false;
    private Fragment fragment_context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        this.navigationDrawerItemSelected = position + 1;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (this.navigationDrawerItemSelected) {
            case 1:
                fragment_context = new FragmentMain();
                mTitle = ""; // getString(R.string.title_menu_main);
                show_convert_icon = false;
                break;
            case 2:
                fragment_context = new FragmentFileToMD5Sum();
                mTitle = getString(R.string.title_menu_file_to_md5sum);
                show_convert_icon = true;
                break;
            case 3:
                fragment_context = new FragmentFileToSHA1Sum();
                mTitle = getString(R.string.title_menu_file_to_sha1);
                show_convert_icon = true;
                break;
            case 4:
                fragment_context = new FragmentAsciiToMD5();
                mTitle = getString(R.string.title_menu_ascii_to_md5);
                show_convert_icon = true;
                break;
            case 5:
                fragment_context = new FragmentAsciiToRc4();
                mTitle = getString(R.string.title_menu_ascii_to_rc4);
                show_convert_icon = true;
                break;
            case 6:
                fragment_context = new FragmentAsciiToSha1();
                mTitle = getString(R.string.title_menu_ascii_to_sha1);
                show_convert_icon = true;
                break;
            case 7:
                fragment_context = new FragmentAsciiToSha256();
                mTitle = getString(R.string.title_menu_ascii_to_sha256);
                show_convert_icon = true;
                break;
            case 8:
                fragment_context = new FragmentAsciiToSha512();
                mTitle = getString(R.string.title_menu_ascii_to_sha512);
                show_convert_icon = true;
                break;
            case 9:
                fragment_context = new FragmentAsciiToDecimal();
                mTitle = getString(R.string.title_menu_ascii_to_decimal);
                show_convert_icon = true;
                break;
            case 10:
                fragment_context = new FragmentDecimalToAscii();
                mTitle = getString(R.string.title_menu_decimal_to_ascii);
                show_convert_icon = true;
                break;
            case 11:
                fragment_context = new FragmentAsciiToOctal();
                mTitle = getString(R.string.title_menu_ascii_to_octal);
                show_convert_icon = true;
                break;
            case 12:
                fragment_context = new FragmentOctalToAscii();
                mTitle = getString(R.string.title_menu_octal_to_ascii);
                show_convert_icon = true;
                break;
            case 13:
                fragment_context = new FragmentAsciiToHexadecimal();
                mTitle = getString(R.string.title_menu_ascii_to_hexadecimal);
                show_convert_icon = true;
                break;
            case 14:
                fragment_context = new FragmentHexadecimalToAscii();
                mTitle = getString(R.string.title_menu_hexadecimal_to_ascii);
                show_convert_icon = true;
                break;
            case 15:
                fragment_context = new FragmentAsciiToBinary();
                mTitle = getString(R.string.title_menu_ascii_to_binary);
                show_convert_icon = true;
                break;
            case 16:
                fragment_context = new FragmentBinaryToAscii();
                mTitle = getString(R.string.title_menu_binary_to_ascii);
                show_convert_icon = true;
                break;
            case 17:
                fragment_context = new FragmentAsciiToBase64();
                mTitle = getString(R.string.title_menu_ascii_to_base64);
                show_convert_icon = true;
                break;
            case 18:
                fragment_context = new FragmentBase64ToAscii();
                mTitle = getString(R.string.title_menu_base64_to_ascii);
                show_convert_icon = true;
                break;
            case 19:
                fragment_context = new FragmentAsciiToHtmlentities();
                mTitle = getString(R.string.title_menu_ascii_to_htmlentities);
                show_convert_icon = true;
                break;
            case 20:
                fragment_context = new FragmentAsciiToUrlencode();
                mTitle = getString(R.string.title_menu_ascii_to_urlencode);
                show_convert_icon = true;
                break;
            case 21:
                fragment_context = new FragmentAsciiToSqliDWord();
                mTitle = getString(R.string.title_menu_ascii_to_sqlidw);
                show_convert_icon = true;
                break;
            case 22:
                fragment_context = new FragmentEnterTheMatrix();
                mTitle = getString(R.string.title_menu_ascii_to_enter_the_matrix);
                show_convert_icon = true;
                break;
        }

        //restoreActionBar();

        if(fragment_context != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment_context)
                    .commit();
        }

        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            if(this.show_convert_icon) {
                getMenuInflater().inflate(R.menu.main, menu);
                restoreActionBar();
                return true;

            }else{
                return super.onCreateOptionsMenu(menu);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_convert) {
            ((FragmentGenericConvert)fragment_context).convert(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
