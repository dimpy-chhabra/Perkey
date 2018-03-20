package com.example.dimpy.perkey_dos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    ActionBar actionBar;
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (getActiveFragment()) {
                        return true;
                    }
                    fragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                            .replace(R.id.content,
                                    new list_frag(),
                                    "list_frag").commit();
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    if (getActiveFragment()) {
                        return true;
                    }
                    SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
                    Boolean ani = pref.getBoolean("animate_bit", false);
                    if (ani == false) {
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.content,
                                        new scanner_frag(),
                                        "scanner_frag").commit();
                    } else {
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.content,
                                        new scanner_frag2(),
                                        "scanner_frag2").commit();
                    }


                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    if (getActiveFragment()) {
                        return true;
                    }
                    fragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                            .replace(R.id.content,
                                    new maps_frag(),
                                    "maps_frag").commit();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawable dr = getResources().getDrawable(R.drawable.icon_color);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 55, 55, true));
        actionBar.setIcon(d);
        actionBar.setDisplayUseLogoEnabled(Boolean.TRUE);
        //actionBar.hide();

        //TODO:Add a volley module to fetch the spry bit of the user's number from user's table, thusly replacing with scannerfrag2
        //TODO: Add a column in user's dets -> "spry bit" and a corresponding mall number!


        fragmentManager = getSupportFragmentManager();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("logged_in", true); // Storing boolean - true/false
        editor.putString("name", "Sarah Jones"); // Storing string
        editor.putString("phone", "9898938475"); // Storing string
        editor.putString("car_no", "MH BV12 9065"); // Storing string
        editor.putString("sex", "F"); // Storing string
        editor.putBoolean("animate_bit", false); //Storing Boolean
        editor.putInt("tans_id", -1); //Storing Integer
        editor.commit(); // commit changes

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.content,
                        new scanner_frag(),
                        "scanner_frag").commit();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(Boolean.TRUE);
    }

    public boolean getActiveFragment() {
        scanner_frag2 myFragment0 = (scanner_frag2) fragmentManager.findFragmentByTag("scanner_frag2");
        occu_check_frag myFragment1 = (occu_check_frag) fragmentManager.findFragmentByTag("occu_check_frag");
        if (myFragment1 != null && myFragment1.isVisible()) {
            navigation.setVisibility(View.GONE);
            return true;
        } else if (myFragment0 != null && myFragment0.isVisible()) {
            navigation.setVisibility(View.GONE);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_about_me:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content,
                                new about_me_frag(),
                                "about_me_frag").commit();
                return true;
            case R.id.action_help:
                new AlertDialog.Builder(this)
                        .setTitle("Stuck?")
                        .setMessage("Procedure :\nOnce you have parked your car" +
                                " go to the dashboard and hit the puch button\n" +
                                "Then view the fare and Accept the charges\n" +
                                "Congratulations, you're parked\n" +
                                "Now your timer kicks in. Keep a track of the time!\n" +
                                "Once you've backed your car, hit the Floating button\nGenerate" +
                                " the bill and EXIT!\n" +
                                "Contact Us :\n" +
                                "facebook as dimpychhabra20\n\n" +
                                "Still stuck? Reach me at dimpy.blithe@gmail.com")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getBaseContext(), ":)", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_help)
                        .show();

            /*case R.id.action_settings:
                Toast.makeText(this, "View Settings!", Toast.LENGTH_SHORT).show();
                return true;
            */
            case R.id.action_share:
                String shareBody = "Perkey, the smart parking App! Join us " +
                        "now! \n-DimpyChhabra\n( linkedin.com/in/dimpy-chhabra )";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Greeting from Perkey");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}