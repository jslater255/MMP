package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
 * AMENDMENTS    :  Created by: James Slater
 * <p/>
 * --------------------------------------------------------------
 */
public class Record extends ActionBarActivity {
    Button record, playBackBtn;

    public static TextView vol;
    String mFileName = null;
    Context context;
    ExtAudioRecorder extAudioRecorder;
    int seconds;
    Timer t;
    LineGraphSeries<DataPoint> mSeries2;
    double graph2LastXValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_screen);
        context = this;
        /**
         *
         */
        record = (Button) findViewById(R.id.btn_record_start);
        playBackBtn = (Button) findViewById(R.id.playBackBtn);
        vol = (TextView) findViewById(R.id.countDwn);
        /**
         *
         */
        mFileName = this.getExternalFilesDir(null).getPath();
        /**
         * Called tmp so that if the user does not want to save it it can be deleted.
         */
        mFileName += "/tmp.wav";
        /**
         *
         */

        GraphView graph2 = (GraphView) findViewById(R.id.graph);
        mSeries2 = new LineGraphSeries<DataPoint>();
        graph2.addSeries(mSeries2);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(100);
        graph2.setTitle("Volume input");

        record.setOnClickListener(new View.OnClickListener() {
            boolean mStartRecording = true;
            @Override
            public void onClick(View v) {
                /**
                 *
                 */
                startRecording();
                record.setText("Recording...");
                mStartRecording = !mStartRecording;
            }
        });
    }
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
     * AMENDMENTS    :  Created by: James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private void startRecording() {
        // Start recording
        /**
         *
         */
        extAudioRecorder = ExtAudioRecorder.getInstanse(); // Uncompressed recording (WAV)
        /**
         *
         */
        extAudioRecorder.setOutputFile(mFileName);
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
                        TextView cntDwn = (TextView) findViewById(R.id.countDwn);

                        cntDwn.setText(String.valueOf(seconds/10.0));
                        //volIn.setText(String.valueOf(extAudioRecorder.getMaxAmplitude()));

                        graph2LastXValue += 1;
                        mSeries2.appendData(new DataPoint(graph2LastXValue, getRandom()), true,100);

                        seconds -= 1;

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

    private double getRandom() {
        double maxAmp = extAudioRecorder.getMaxAmplitude();
        return maxAmp;
    }

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
     * AMENDMENTS    :  Created by: James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private void stopRecording() {
        // Stop recording
        record.setText("Record");
        extAudioRecorder.stop();
        extAudioRecorder.release();
    }

    public void playBackRecording(View v){
        File audioFile = new File(mFileName);
        AudioPlayback audioPlayer = new AudioPlayback(context, audioFile);
    }

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