package com.cs394.jas38.pdultrasonicclassification;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

public class LoadDataUITest extends ActivityUnitTestCase<LoadData> {

    LoadData ldActivity;

    ArrayList<Double> wav;
    GraphView graph;
    ProgressBar mProgress;
    Button playBtn;
    TextView smpl_rate, classificationOutLbl, certainltyOutLbl;

    String fileName = "tmp.wav";
    DataPoint[] data;

    public LoadDataUITest(){
        super(LoadData.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
        setActivityContext(context);

        Intent mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), LoadData.class);
        mLaunchIntent.putExtra("filename", fileName);
        startActivity(mLaunchIntent, null, null);
        ldActivity = getActivity();
        assertNotNull(ldActivity);

        graph = (GraphView) ldActivity.findViewById(R.id.graph);
        mProgress = (ProgressBar) ldActivity.findViewById(R.id.progBar);
        playBtn = (Button) ldActivity.findViewById(R.id.playWAV);

        smpl_rate = (TextView) ldActivity.findViewById(R.id.sample_rate_out);
        classificationOutLbl = (TextView) ldActivity.findViewById(R.id.Classification_out);
        certainltyOutLbl = (TextView) ldActivity.findViewById(R.id.certaintyOutLbl);

        wav = new ArrayList<>();

    }

    public void testUI(){
        assertNotNull(graph);
        assertNotNull(mProgress);
        assertNotNull(playBtn);
        assertNotNull(smpl_rate);
        assertNotNull(classificationOutLbl);
        assertNotNull(certainltyOutLbl);
    }
}