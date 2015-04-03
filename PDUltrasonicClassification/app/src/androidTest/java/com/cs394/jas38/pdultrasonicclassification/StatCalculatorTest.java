package com.cs394.jas38.pdultrasonicclassification;

import junit.framework.TestCase;

import java.util.ArrayList;

public class StatCalculatorTest extends TestCase {

    ArrayList<Double> wavTest = new ArrayList<>();
    StatCalculator st;

    public void setUp() throws Exception {
        super.setUp();

        st = new StatCalculator();

        for (double idx = 0; idx < 10; idx++)
        {
            wavTest.add(idx);
        }
    }

    public void tearDown() throws Exception {

    }

    public void testRun_avg() throws Exception {

    }

    public void testAvg() throws Exception {

        double avg = st.avg(wavTest);

        assertEquals("Check Avg Function", 4.5, avg);
    }

    public void testStand_dev() throws Exception {

        double stanDev = st.stand_dev(st.avg(wavTest), wavTest);

        assertEquals("Stand Dev ", 9.166666666666666, stanDev);

    }

    public void testPeaks() throws Exception {
        //TODO
    }

    public void testVariance() throws Exception {

        double var = st.variance(st.avg(wavTest), wavTest);

        assertEquals("Variance ", 3.0276503540974917, var);
    }

    public void testCountCrossZero() throws Exception {

        assertEquals("Cross Zero 0 ", 0, st.countCrossZero(wavTest));

        wavTest.clear();
        // Create an array that slopes below zero with enough points
        for (double idx = 100; idx >= -100; idx--)
        {
            wavTest.add(idx);
        }

        assertEquals("Cross zero 1 ", 1, st.countCrossZero(wavTest));
    }

    public void testGetSlope() throws Exception {

        assertEquals("Slope ", 1.6666666666666667, st.getSlope(0.0,1.0,0,1));
    }
}