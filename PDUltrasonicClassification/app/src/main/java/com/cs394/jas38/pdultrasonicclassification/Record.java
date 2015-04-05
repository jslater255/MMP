package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : Record
 * <p/>
 * FUNCTION      : Extends ActionBarActivity. Record will take
 *                  audio from the mic or input from the headphone
 *                  jack and save it an an uncompressed WAV file.
 *                  Inflates the record_screen.xml file to screen
 *                  and saves a 10 sec audio wav file. It also
 *                  displays a plot of the incoming audio as feedback
 *                  for the recording.
 * <p/>
 * AMENDMENTS    :  Created by: James Slater
 * <p/>
 * --------------------------------------------------------------
 */
public class Record extends ActionBarActivity {
    /**
     * Buttons on the screen that will be linked to these variables.
     */
    private Button recordBtn;
    /**
     * String of the file name to save.
     */
    private String mFileName = null;
    /**
     * The screens context for the whole class to be able to use.
     * This is set as a context, rather than using 'this.' everywhere
     * makes it easier to read for the user.
     */
    private Context context;
    /**
     *  An instance of the ExtAudioRecorder class as a local variable.
     */
    private ExtAudioRecorder extAudioRecorder;
    /**
     * The countdown in seconds.
     */
    private int seconds;
    /**
     * A timer class to limit the recording to the times in seconds.
     */
    private Timer t;
    /**
     * A line series for the audio to be displayed as. This is filled
     * with the highest amplitude within a set time period
     */
    private LineGraphSeries<DataPoint> lineSeries;
    private double graph2LastXValue = 0;

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : onCreate
     * <p/>
     * FUNCTION      : Inflate the record_screen.xml file and set the
     *                  local variables to the relevant corresponding
     *                  features
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : A recording screen onto the device.
     * <p/>
     * AMENDMENTS    :  Created by: James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * sets the current view as the record_screen.xml
         */
        setContentView(R.layout.record_screen);
        context = this;
        /**
         * Sets the local variables to the buttons and text views
         * within the xml file.
         */
        recordBtn = (Button) findViewById(R.id.btn_record_start);
        /**
         * Adds the path to the start of the file name.
         */
        mFileName = this.getExternalFilesDir(null).getPath();
        /**
         * Called tmp so that if the user does not want to save it can be deleted.
         */
        mFileName += "/tmp.wav";
        /**
         * Gets the Graph view from the xml file. Sets up a new line graph series
         * and adds it to the graph view.
         * Then set the view of the graph from 0 to 100.
         * Set the Title as "Volume input".
         */
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        lineSeries = new LineGraphSeries<>();
        graphView.addSeries(lineSeries);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(100);
        graphView.setTitle("Volume input");

        /**
         * ---------------------------------------------------------------
         * <p/>
         * CALL NAME     : setOnClickListener
         * <p/>
         * FUNCTION      : Sets a listener to record button.
         * <p/>
         * INPUTS        :
         * <p/>
         * OUTPUTS       : When the button is clicked it starts the recording,
         *                  and calls the startRecording method
         * <p/>
         * AMENDMENTS    :  Created by: James Slater
         * <p/>
         * --------------------------------------------------------------
         */
        recordBtn.setOnClickListener(new View.OnClickListener() {
            boolean mStartRecording = true;

            @Override
            public void onClick(View v) {
                /**
                 * Calls the method startRecording()
                 * Sets the text within the button on screen to
                 * "Recording..."
                 * changes the bool value mStartRecording to
                 * the opposite.
                 */
                startRecording();
                recordBtn.setText("Recording...");
                mStartRecording = !mStartRecording;
            }
        });
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : startRecording
     * <p/>
     * FUNCTION      : To start the recording for the timers length
     *                  in seconds. While recording update the plot of the audio input.
     *                  Once the timer is up release the resources and reset the screen.
     * <p/>
     * INPUTS        : None
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by: James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private void startRecording() {
        /**
         * Get an instance of the extAudioRecorder.
         * Uncompressed recording (WAV).
         */
        extAudioRecorder = ExtAudioRecorder.getInstanse();
        /**
         * Set the output file as the with in the extAudioRecorder instance.
         */
        extAudioRecorder.setOutputFile(mFileName);
        /**
         * Prepare and start recording
         */
        extAudioRecorder.prepare();
        extAudioRecorder.start();

        //Declare the timer
        t = new Timer();
        seconds = 100;
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        /**
                         * Sets the text view within the thread so we can update it, on every count.
                         */
                        TextView cntDwn = (TextView) findViewById(R.id.countDwn);
                        cntDwn.setText(String.valueOf(seconds/10.0));
                        graph2LastXValue += 1;
                        lineSeries.appendData(new DataPoint(graph2LastXValue,
                                                getMaxAmplitube()),
                                                true, 100);
                        /**
                         * Take 1 of seconds each run through.
                         */
                        seconds -= 1;
                        /**
                         * Once the seconds hit 0 set the count back to 10.
                         * Call stop recording method.
                         * Cancel the timer thread.
                         */
                        if (seconds == 0) {
                            cntDwn.setText(String.valueOf(10));
                            stopRecording();
                            t.cancel();
                        }
                    }
                });
            }
        }, 0, 100);
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getMaxAmplitube
     * <p/>
     * FUNCTION      : Gets the biggest amplitude in the audio file
     *                  since the last call.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : double value of the amplitude.
     * <p/>
     * AMENDMENTS    :  Created by: James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private double getMaxAmplitube() {
        return extAudioRecorder.getMaxAmplitude();
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : stopRecording
     * <p/>
     * FUNCTION      : Stops the recording, and releases the used
     *                  resources.
     *                  Sets the test within the record button back to
     *                  "Record"
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by: James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private void stopRecording() {
        // Stop recording
        recordBtn.setText("Record");
        extAudioRecorder.stop();
        extAudioRecorder.release();
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : playBackRecording
     * <p/>
     * FUNCTION      : Opens the newly recorded file and passes it to
     *                  the AudioPlayback to play.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : The audio file will play aloud.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public void playBackRecording(View v){
        File audioFile = new File(mFileName);
        new AudioPlayback(context, audioFile);
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : saveAs
     * <p/>
     * FUNCTION      : Loads the SaveAs class. This will take the
     *                  newly recorded audio file and be renamed to
     *                  what ever the user inputs
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : Calls the SaveAs class.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public void saveAs(View v){
        try{
            Intent i = new Intent();
            i.setClass(context, SaveAs.class);
            i.putExtra("audioFileName", mFileName);
            startActivity(i);
        }catch (Exception e){
            Toast.makeText(context, e.getMessage() , Toast.LENGTH_SHORT).show();
        }
    }
}