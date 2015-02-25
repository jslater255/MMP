package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : ReadWrite
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
public class ReadWrite {


    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
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
    public ArrayList<Double> readCSV(Context context, String fileName)
    {
        ArrayList<Double> wav = new ArrayList<>();
        File file = new File(context.getExternalFilesDir(null), fileName);
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

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : writeCSV
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
    public void writeCSV(Context context, String fileName, ArrayList<Double> wav)
    {

        String out = "";
        for (double idx : wav)
        {
            out += Double.toString(idx) + ",";
        }

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);//context.getExternalFilesDir(null)+
            fos.write(out.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
