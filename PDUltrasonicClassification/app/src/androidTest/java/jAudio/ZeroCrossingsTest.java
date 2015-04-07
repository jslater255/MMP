package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class ZeroCrossingsTest extends TestCase {

    double[] data;
    double sampleRate;
    ZeroCrossings zeroCrossings;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        sampleRate = 8000;
        zeroCrossings = new ZeroCrossings();
    }

    public void testExtractFeature() throws Exception {
        assertEquals("zeroCrossings extract feature return array length",1,zeroCrossings.extractFeature(data,sampleRate,null).length);
        assertEquals("zeroCrossings extract feature first value",0.0,zeroCrossings.extractFeature(data,sampleRate,null)[0]);
    }
}