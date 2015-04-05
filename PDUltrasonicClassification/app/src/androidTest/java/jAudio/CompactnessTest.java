package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class CompactnessTest extends TestCase {

    double[] data;
    Compactness compactnessTest;

    public void setUp() throws Exception {
        super.setUp();
        compactnessTest = new Compactness();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
    }

    public void testExtractFeature() throws Exception {
        double valTest = compactnessTest.extractFeature(data,8000,data)[0];
        // All the array is the same so compactness will be 0
        assertEquals("Testing Compactness: ",0.0,valTest);
    }
}