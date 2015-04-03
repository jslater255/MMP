package com.cs394.jas38.pdultrasonicclassification;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Slaters on 17/03/15.
 */
public class FileChooser extends ActionBarActivity
{

    ListView lv;
    private ArrayAdapter<String> listAdapter ;
    Context context;
    ArrayList<String> fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_chooser);

        context = this;

        fileList= getListFiles(new File(context.getExternalFilesDir(null).getPath()));

        listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, fileList);

        lv = (ListView) findViewById(R.id.listView);

        lv.setAdapter( listAdapter );

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    String fileName = parent.getItemAtPosition(position).toString();
                    Intent i = new Intent();
                    i.setClass(context, LoadData.class);
                    i.putExtra("filename", fileName);
                    startActivity(i);
                }catch (Exception e){
                    Toast.makeText(context, e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public ArrayList<String> getListFiles(File parentDir) {
        ArrayList<String> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".wav")){
                    inFiles.add(file.getName());
                }
            }
        }
        return inFiles;
    }
}
