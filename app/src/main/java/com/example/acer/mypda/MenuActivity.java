package com.example.acer.mypda;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public abstract class MenuActivity extends AppCompatActivity {

    View view;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.settings:

            Intent gotoSettings = new Intent(MenuActivity.this, SettingsActivity.class);
            startActivity(gotoSettings);
            return(true);

        case R.id.help:

            Intent gotoHelp = new Intent(MenuActivity.this, HelpActivity.class);
            String classString = getClass().toString();
            gotoHelp.putExtra("Help",classString);
            startActivity(gotoHelp);
            return(true);

        case R.id.exit:
            //new Exit().doExit(this);

            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);

            return true;
    }
        return(super.onOptionsItemSelected(item));
    }

}

