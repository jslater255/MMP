package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.util.ArrayList;

import static android.view.View.INVISIBLE;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : LoadData
 * <p/>
 * FUNCTION      : The Load data screen
 * <p/>
 * INPUTS        :
 * <p/>
 * OUTPUTS       : inflates the LoadData.xml file
 * <p/>
 * AMENDMENTS    :  Created by, James Slater
 * <p/>
 * --------------------------------------------------------------
 */
public class LoadData extends ActionBarActivity
{
    /**
     * Need handler for callbacks to the UI thread
     */
    final Handler mHandler = new Handler();
    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : mUpdateResults
     * <p/>
     * FUNCTION      : Once the thread has finished it call the updateResultsInUi.
     *                  Runnable for posting back to the UI
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    final Runnable mUpdateResults = new Runnable()
    {
        public void run()
        {
            updateResultsInUi();
        }
    };
    /**
     * All the instances of used classes
     */
    ReadWrite rw;
    StatCalculator st;
    Broker broker;
    /**
     * This will hold the converted WAV file information
     */
    ArrayList<Double> wav = new ArrayList<>();
    /**
     * The local context to make it easier to pass to other classes.
     * I have done this as it reads a lot better to pass 'context' rather than this.
     */
    Context context;
    /**
     * From com.jjoe64.graphview.GraphView this is used for a pointer to the place
     * in the LoadData.xml file
     */
    GraphView graph;
    /**
     * A pointer to an android progress swirl
     */
    ProgressBar mProgress;
    /**
     * Pointers to the TextView sections in the xml file
     */
    TextView avg_out;
    TextView stan_dev;
    /**
     * Pointer to the Button in the xml file
     */
    Button playBtn;

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onCreate
     * <p/>
     * FUNCTION      : inflates the activity_load_data.xml UI file
     * <p/>
     * INPUTS        : Bundle savedInstanceState
     * <p/>
     * OUTPUTS       : Inflates the LoadData.xml file
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        /**
         * Sets the Title of the screen
         * Gets it from the R.string file
         */
        this.setTitle(getString(R.string.LOAD_DATA_TITLE));
        /**
         * Creates instance of the ReadWrite class to read the CSV file
         */
        rw = new ReadWrite();
        /**
         * Creates an instance of the StatCalculator class
         */
        st = new StatCalculator();
        /**
         * Create instance of the Broker class
         */
        broker = new Broker();
        /**
         * Sets the Context to this so it can be passed to other classes that
         * need to update or have access to the screen
         */
        context = this;
        /**
         * Gets the progress wheel in the xml file to be able to set
         * invisible once the graph is ready to load.
         */
        mProgress = (ProgressBar) findViewById(R.id.progBar);
        /**
         * This starts the thread to run the reading of the WAV file
         */
        startLoadingCSV();
        /**
         * Gets a pointer the the TextView from the XML
         */
        avg_out = (TextView) findViewById(R.id.avg_out);
        /**
         * Gets a pointer the the TextView from the XML
         */
        stan_dev = (TextView) findViewById(R.id.stan_dev_out);
        /**
         * Gets a pointer the the Button from the XML
         */
        playBtn = (Button) findViewById(R.id.playWAV);
        /**
         * Adds a listener to the play button to play back th wav file.
         */
        playBtn.setOnClickListener(new View.OnClickListener()
        {
            /**
             * ---------------------------------------------------------------
             * <p/>
             * CALL NAME     : onClick
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
            public void onClick(View v)
            {
                File file = new File(context.getExternalFilesDir(null), "/ea.wav");
                Context appContext = getApplicationContext();
                MediaPlayer mp = MediaPlayer.create(appContext, Uri.fromFile(file));
                mp.start();
            }
        });
        /**
         * Gets previous intent from MainActivity
         */
        Intent intent = getIntent();//
        /**
         * Gets a pointer the the Graph from the XML
         */
        graph = (GraphView) findViewById(R.id.graph);
        /**
         * Set manual X bounds
         */
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(200);
        /**
         * Set manual Y bounds
         */
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-1);
        graph.getViewport().setMaxY(1);
        /**
         * Allow the user to scroll through the grpah and be able to set the scale of the X axis
         */
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        /**
         * Turn the graph to not visible as it has no data.
         * It will be visible once the thread has loaded the CSV file
         */
        graph.setVisibility(INVISIBLE);
    }/* End of onCreate */

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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /**
         * Inflate the menu; this adds items to the action bar if it is present.
         */
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }/* End of onCreateOptionsMenu */

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
     * AMENDMENTS    : Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        /**
         * Handle action bar item clicks here. The action bar will automatically
         * handle clicks on the Home/Up button, so long as you specify a
         * parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();
        /**
         * noinspection SimplifiableIfStatement
         */
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }/* End of onOptionsItemSelected */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : startLoadingCSV
     * <p/>
     * FUNCTION      : Creates a thread to load the CSV file. This is needed as it is
     *                  a very big file and it allows the UI thread to stay responsive
     * <p/>
     * INPUTS        : Null
     * <p/>
     * OUTPUTS       : Void
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    protected void startLoadingCSV()
    {
        /**
         * Fire off a thread to do some work that we shouldn't do directly in the UI thread
         */
        Thread t = new Thread()
        {
            public void run()
            {
                //wav = rw.readCSV(context, "/ea.csv");
                /**
                 * Calls the running average method in the statCalculator and passes in:-
                 *  - The size of the FIFO list 50 is standard.
                 *  - A double array, by calling:-
                 *      - The Broker class to to get the Native C code to read the audio file.
                 *          This return the converted audio WAV file as an double array.
                 */
                wav = st.run_avg(50, broker.CallNativeOpenFile(context.getExternalFilesDir(null) + "/ea.wav"));
                /**
                 * We pass the wav ArrayList to countCrossZero to find all the spikes and their features.
                 */
                st.countCrossZero(wav);
                /**
                 * Once the above code has been completed we call mUpdateResults.
                 */
                mHandler.post(mUpdateResults);
            }
        };
        t.start();
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : updateResultsInUi
     * <p/>
     * FUNCTION      : This is called once the Thread had returned finishing its tasks.
     *                  Once the thread has returned we update the UI.
     * <p/>
     * INPUTS        : Null
     * <p/>
     * OUTPUTS       : void
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private void updateResultsInUi()
    {
        /**
         * Back in the UI thread -- update our UI elements based on the data in mResults.
         */
        DataPoint[] data = new DataPoint[wav.size() - 1];
        for (int idx = 0; idx < (wav.size() - 1); idx++)
        {
            /**
             * We put idx by 0.6 as that is how many milliseconds there are for each point.
             */
            //data[idx] = new DataPoint((0.6*idx), st.getSlope(wav.get(idx),wav.get(idx+1),idx));// To see the DY/DX plot
            data[idx] = new DataPoint(idx, wav.get(idx));
        }
        /**
         * Creates a series to add the to the graph.
         */
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        graph.addSeries(series);
        /**
         * Sets the Graph to visible and the progress wheel to invisible.
         */
        graph.setVisibility(View.VISIBLE);
        mProgress.setVisibility(INVISIBLE);
        /**
         * Gets some information from the array loaded.
         */
        Double avg = st.avg(wav);
        /**
         * Converts the doubles to 4 decimal places and String.
         */
        avg_out.setText(fourDecPla(avg));
        stan_dev.setText(fourDecPla(st.stand_dev(avg, wav)));
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : fourDecPla
     * <p/>
     * FUNCTION      : To take a Double round it to 4 decimal places and return it as a String.
     * <p/>
     * INPUTS        : Double val double to convert to 4 decimal places.
     * <p/>
     * OUTPUTS       : Returns the converted double as a string.
     * <p/>
     * AMENDMENTS    : Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public String fourDecPla(double val)
    {
        /**
         * Sets the val double to string using 10000 gives 4 decimal places.
         */
        return Double.toString(Math.round(val * 10000) / 10000.0d);
    }
}
