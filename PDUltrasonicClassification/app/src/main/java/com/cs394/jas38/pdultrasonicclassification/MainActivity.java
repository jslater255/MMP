package com.cs394.jas38.pdultrasonicclassification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CLASS NAME    : MainActivity
 * <p/>
 * FUNCTION      : This is the main screen of the App, uses the
 *                  activity_main.xml as the corresponding UI
 * <p/>
 * AMENDMENTS    :  Created by, James Slater
 * <p/>
 * --------------------------------------------------------------
 */
public class MainActivity extends ActionBarActivity {

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onCreate
     * <p/>
     * FUNCTION      : sets the view as the activity_main.xml
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Sets the title of the screen
        this.setTitle("Main Menu");
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onCreateOptionsMenu
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onOptionsItemSelected
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : loadFileChooserScreen
     * <p/>
     * FUNCTION      : To load the file chooser screen, will be
     *                  called when the clicks on the button
     * <p/>
     * INPUTS        : View the current view of the screen for the
     *                  new screen to start over
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public void loadFileChooserScreen(View view) {
        Intent intent = new Intent();
        intent.setClass(this, FileChooser.class);
        try{
            this.startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage() ,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : recordScreenBtn
     * <p/>
     * FUNCTION      : To load the record screen when click by
     *                  the button on screen
     * <p/>
     * INPUTS        : View the current view of the screen for the
     *                  new screen to start over
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public void recordScreenBtn(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Record.class);
        try{
            this.startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage() ,Toast.LENGTH_SHORT).show();
        }
    }
}
