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
 * ---------------------------------------------------------------
 * <p/>
 * CLASS NAME    : FileChooser
 * <p/>
 * FUNCTION      : Extends ActionBarActivity. Loads the
 *                  file_chooser.xml file to the screen
 * <p/>
 * AMENDMENTS    :  Created by, James Slater on 17/03/15.
 * <p/>
 * --------------------------------------------------------------
 */
public class FileChooser extends ActionBarActivity
{
    /**
     * List view variable of the container with in the xml file
     */
    private ListView listView;
    private ArrayList<String> fileList;
    /**
     * The context of the screen. This is used to get the file
     * paths and must not be null.
     */
    private Context context;
    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onCreate
     * <p/>
     * FUNCTION      : Get loaded on calling this class.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : Sets the current content to file_chooser.xml
     *                  also loads all the pointers to the various
     *                  aspects in the xml file by using findViewById.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 26/03/15.
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Inflates the xml file to the screen.
         */
        setContentView(R.layout.file_chooser);
        /**
         * Set context to this class
         */
        context = this;
        /**
         * Calls getListFiles with a folder all the files locations.
         * It returns an ArrayList of all the files that end on ".wav"
         * found in the folder location.
         */
        fileList= getListFiles(new File(context.getExternalFilesDir(null).getPath()));
        /**
         * Adds the array list of files names to the list adapter to add it to the screen.
         */
        /**
         * The array of the files in the folder. It will hold the
         * strings of the file names, provided by fileLists.
         */
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, fileList);
        /**
         * Sets a pointer to the list view container.
         */
        listView = (ListView) findViewById(R.id.listView);
        /**
         * Add the list adapter to the view to display the files to choose from.
         */
        listView.setAdapter(listAdapter);
        /**
         * Adds a listener to the list view to get the file the user has selected.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * ---------------------------------------------------------------
             * <p/>
             * CALL NAME     : onItemClick
             * <p/>
             * FUNCTION      : A listener on the list view. One of the variables
             *                  to be returned is the position of the item
             *                  selected in the list. Using this position we
             *                  get the file name from the list to pass to the
             *                  LoadData class via intent putExtra method.
             * <p/>
             * INPUTS        :
             * <p/>
             * OUTPUTS       : The LoadData screen is called.
             * <p/>
             * AMENDMENTS    :  Created by, James Slater on 26/03/15.
             * <p/>
             * --------------------------------------------------------------
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String fileName = parent.getItemAtPosition(position).toString();
                    Intent i = new Intent();
                    i.setClass(context, LoadData.class);
                    /**
                     * Passing the fileName string into the new intent with the link
                     * name of "filename".
                     */
                    i.putExtra("filename", fileName);
                    startActivity(i);
                } catch (Exception e) {
                    //TODO change the error message
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getListFiles
     * <p/>
     * FUNCTION      : Gets all the files in the folder inputted. Only
     *                  finds the files that end in ".wav" and adds
     *                  then to the list. If in its search it finds a
     *                  folder it adds the files within that folder to
     *                  the list to check.
     * <p/>
     * INPUTS        : File of the folder to search for the audio files
     * <p/>
     * OUTPUTS       : Array list of the file name found in the folder.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 26/03/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public ArrayList<String> getListFiles(File parentDir) {
        ArrayList<String> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files)
        {
            if (file.isDirectory())
            {
                inFiles.addAll(getListFiles(file));
            } else
            {
                if(file.getName().endsWith(".wav"))
                {
                    inFiles.add(file.getName());
                }
            }
        }
        return inFiles;
    }
}
