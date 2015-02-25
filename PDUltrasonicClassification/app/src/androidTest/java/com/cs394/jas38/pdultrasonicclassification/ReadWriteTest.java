package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.AndroidTestCase;

import java.io.File;
import java.util.ArrayList;

public class ReadWriteTest extends AndroidTestCase {

    ArrayList<Double> wavTest;
    Context context;
    String fileName = "testing.csv";

    public void setUp() throws Exception {
        super.setUp();
        /*Intent intent = new Intent(getInstrumentation().getTargetContext(), LoadData.class);
        startActivity(intent,null,null);*/
        context = getContext();
        wavTest = new ArrayList<>();
        //context = this.getInstrumentation().getTargetContext().getApplicationContext();
        for (double idx = 0; idx < 10; idx++)
        {
            wavTest.add(idx);
        }

    }

    public void tearDown() throws Exception {

        File file = new File(context.getExternalFilesDir(null)+fileName);
        file.delete();
    }

    public void testWriteCSV() throws Exception {

        ReadWrite rw = new ReadWrite();

        rw.writeCSV(context, fileName, wavTest);

        File file = new File(fileName);

        assertTrue(file.exists());
    }

    public void testReadCSV() throws Exception {

        ReadWrite rw = new ReadWrite();

        ArrayList<Double> readWav = rw.readCSV(context, fileName);

        assertEquals(wavTest.get(0),readWav.get(0));
    }
}