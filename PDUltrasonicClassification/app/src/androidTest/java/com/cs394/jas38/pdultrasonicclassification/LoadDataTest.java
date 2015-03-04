package com.cs394.jas38.pdultrasonicclassification;

import junit.framework.TestCase;

public class LoadDataTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testStartLoadingCSV() throws Exception {

    }

    public void testFourDecPla() throws Exception {

        double test = 3.333333;

        LoadData ld = new LoadData();

        assertEquals("Test rounding to 2 decimal places", 3.33, ld.fourDecPla(test));

    }
}