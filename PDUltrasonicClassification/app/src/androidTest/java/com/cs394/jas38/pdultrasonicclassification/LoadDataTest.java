package com.cs394.jas38.pdultrasonicclassification;

import junit.framework.TestCase;

public class LoadDataTest extends TestCase {

    LoadData ld;

    public void setUp() throws Exception {
        super.setUp();
        ld = new LoadData();
    }

    public void tearDown() throws Exception {

    }

    public void testStartLoadingCSV() throws Exception {


    }

    public void testStartLoadingWAV(){


    }

    public void testFourDecPla() throws Exception {

        double test = 3.333333;

        assertEquals("Test rounding to 4 decimal places", "3.3333", ld.fourDecPla(test));

    }
}