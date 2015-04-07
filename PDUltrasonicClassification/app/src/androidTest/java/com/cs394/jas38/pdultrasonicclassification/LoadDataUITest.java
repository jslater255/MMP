package com.cs394.jas38.pdultrasonicclassification;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

public class LoadDataUITest extends ActivityInstrumentationTestCase2<LoadData> {

    LoadData ldActivity;

    ArrayList<Double> wav;
    GraphView graph;
    ProgressBar mProgress;
    Button playBtn;

    String fileName;
    DataPoint[] data;

    public LoadDataUITest(){
        super(LoadData.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        ldActivity = getActivity();

        graph = (GraphView) ldActivity.findViewById(R.id.graph);
        mProgress = (ProgressBar) ldActivity.findViewById(R.id.progBar);
        playBtn = (Button) ldActivity.findViewById(R.id.playBackBtn);

        wav = new ArrayList<>();

    }
}