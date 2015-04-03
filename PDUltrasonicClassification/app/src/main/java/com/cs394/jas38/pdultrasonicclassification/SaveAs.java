package com.cs394.jas38.pdultrasonicclassification;

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
 * Created by Slaters on 25/03/15.
 */
public class SaveAs extends ActionBarActivity
{

    Context context;
    EditText fileNameInput;
    TextView timeDate;
    Button saveBtn;
    String tmpFilePath, date, newFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_tmp_wav_file);
        context = this;

        fileNameInput = (EditText) findViewById(R.id.fileNameInput);
        timeDate = (TextView) findViewById(R.id.timeDate);
        saveBtn = (Button)  findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeName();
                Toast.makeText(context,"Changed name from " + tmpFilePath, Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Changed name to " + newFileName, Toast.LENGTH_SHORT).show();
            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        date = sdf.format(new Date());
        timeDate.setText(date);

        Intent intent = getIntent();
        tmpFilePath = intent.getStringExtra("audioFileName");
    }

    public void changeName(){
        newFileName = fileNameInput.getText().toString();
        //if(!newFileName.isEmpty()){
            File from      = new File(context.getExternalFilesDir(null).getPath(), "/tmp.wav");
            File to        = new File(context.getExternalFilesDir(null).getPath(),  "/" + newFileName + "_" + date + ".wav");
            if(from.renameTo(to)){
                Toast.makeText(context,"True", Toast.LENGTH_SHORT).show();
            }
        //}
    }
}
