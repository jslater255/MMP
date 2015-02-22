package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Slater on 20/02/15.
 */
public class ReadWrite {


    public ArrayList<Double> readCSV(Context context)
    {
        ArrayList<Double> wav = new ArrayList<>();
        File file = new File(context.getExternalFilesDir(null), "/ea.csv");
        Object out;
        System.out.println(context.getExternalFilesDir(null));
        try {

            InputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);

            int data = isr.read();
            String str = "";
            while(data != -1){

                if ((char)data == ',')
                {
                    wav.add(Double.parseDouble(str));
                    str = "";
                }
                else
                {
                    str = str + (char)data;
                }

                //System.out.println(theChar);
                data = isr.read();
            }
            is.close();
            isr.close();

        } catch (IOException e) {
            // Unable to create file, likely because external storage is
            // not currently mounted.
            Log.w("ExternalStorage", "Error writing " + file, e);
        }

        return wav;
    }

    public void writeCSV(Context context, String filePath)
    {

        try {

            String FILENAME = "hello_file";
            String string = "hello world!";

            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(string.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
