package com.cs394.jas38.pdultrasonicclassification;

import junit.framework.TestCase;

public class StatCalculatorTest extends TestCase {

    double[] wavTest = new double[10];
    StatCalculator st;

    public void setUp() throws Exception {
        super.setUp();

        st = new StatCalculator();

        for (double idx = 0; idx < 10; idx++)
        {
            wavTest[(int)idx] = idx;
        }
    }

    public void testAvg() throws Exception {

        double avg = st.avg(wavTest);

        assertEquals("Check Avg Function", 4.5, avg);
    }

    public void testStand_dev() throws Exception {

        double stanDev = st.stand_dev(wavTest);

        assertEquals("Stand Dev ", 3.0276503540974917, stanDev);

    }

    public void testVariance() throws Exception {

        double var = st.variance(st.avg(wavTest), wavTest);

        assertEquals("Variance ", 3.0276503540974917, var);
    }

    public void testCountCrossZero() throws Exception {

        assertEquals("Cross Zero 0 ", 0, st.countCrossZero(wavTest));
        wavTest = new double[200];
        // Create an array that slopes below zero with enough points
        double val = -100;
        for (double idx = 0; idx >= 200; idx++)
        {
            wavTest[(int)idx] = val;
            val++;
        }

        assertEquals("Cross zero 1 ", 0, st.countCrossZero(wavTest));
    }

    public void testGetSlope() throws Exception {

        assertEquals("Slope ", 1.6666666666666667, st.getSlope(0.0,1.0,0,1));
    }
}