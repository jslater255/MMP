package com.cs394.jas38.pdultrasonicclassification;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class FileChooserTest extends ActivityInstrumentationTestCase2<FileChooser> {

    FileChooser mFileChooserActivity;
    ListView lv;
    ArrayAdapter<String> listAdapter ;
    ArrayList<String> fileList;

    public FileChooserTest(){
        super(FileChooser.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        mFileChooserActivity = getActivity();
        fileList= mFileChooserActivity.getListFiles(new File(mFileChooserActivity.getApplicationContext().getExternalFilesDir(null).getPath()));
        listAdapter = new ArrayAdapter<>(mFileChooserActivity.getApplicationContext(), R.layout.simplerow, fileList);
        lv = (ListView) mFileChooserActivity.findViewById(R.id.listView);
    }

    public void testClickMeButton_clickButtonAndExpectInfoText() {
        String expectedText = "ea.wav";
        assertNotNull("listView is null", lv);
        assertEquals(expectedText, lv.getAdapter().getItem(0));
    }

    public void tearDown() throws Exception {

    }
}