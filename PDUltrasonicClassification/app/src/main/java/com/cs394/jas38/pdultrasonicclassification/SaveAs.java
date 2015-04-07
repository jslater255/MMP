package com.cs394.jas38.pdultrasonicclassification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : SaveAs
 * <p/>
 * FUNCTION      : Extends ActionBarActivity, finds the tmp.wav
 *                  file and renames it as the users input.
 * <p/>
 * AMENDMENTS    :  Created by: James Slater
 * <p/>
 * --------------------------------------------------------------
 */
public class SaveAs extends ActionBarActivity
{
    /**
     * The screens context
     */
    private Context context;
    /**
     * The pointers to the features within the xml screen.
     */
    private EditText fileNameInput;
    private String date;

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onCreate
     * <p/>
     * FUNCTION      : Inflate the save_tmp_wav_file.xml file and set the
     *                  local variables to the relevant corresponding
     *                  features
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : A Save as screen onto the device.
     * <p/>
     * AMENDMENTS    :  Created by: James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Loads the save_tmp_wav_file.xml file.
         */
        setContentView(R.layout.save_tmp_wav_file);
        /**
         * Set the context.
         */
        context = this;
        /**
         * Sets the variables to the relevant parts of the screen.
         */
        fileNameInput = (EditText) findViewById(R.id.fileNameInput);
        TextView timeDate = (TextView) findViewById(R.id.timeDate);
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        /**
         * Sets a listener to the button SaveBtn.
         * It call a private method changeName()
         */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeName();
            }
        });
        /**
         * Gets today's date in the format 'YYYY_MM_DD'
         * Sets the text of timeDate to show on the screen
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        date = sdf.format(new Date());
        timeDate.setText(date);
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : changeName
     * <p/>
     * FUNCTION      : Gets the users input of the file name, within
     *                  the xml file it only allows certain digits and
     *                  a certain reference length.
     *                  If renameTo() returns true then it was
     *                  successful and informs the user by sending a
     *                  pop up message.
     * <p/>
     * INPUTS        : None
     * <p/>
     * OUTPUTS       : The tmp.wav file is renamed.
     * <p/>
     * AMENDMENTS    : Created by: James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private void changeName(){
        /**
         * Gets the input from the user.
         */
        String newFileName = fileNameInput.getText().toString().trim();
        File from      = new File(context.getExternalFilesDir(null).getPath(), "/tmp.wav");
        File to        = new File(context.getExternalFilesDir(null).getPath(),  "/" + newFileName + "_" + date + ".wav");
        /**
         * Returns true if the file is renamed successfully.
         */
        if(from.renameTo(to))
        {
            Toast.makeText(context,"Changed name to " + newFileName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(this, Record.class);
            try{
                this.startActivity(intent);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage() ,Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(context,"Failed to change name, try re-recording", Toast.LENGTH_SHORT).show();
        }
    }
}
