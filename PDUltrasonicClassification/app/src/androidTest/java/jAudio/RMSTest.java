package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class RMSTest extends TestCase {

    double[] data;
    double sampleRate;
    RMS rms;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        sampleRate = 8000;
        rms = new RMS();
    }

    public void testExtractFeature() throws Exception {
        assertEquals("RMS extract feature return array length",1,rms.extractFeature(data,sampleRate,null).length);
        assertEquals("RMS extract feature first value",1.0,rms.extractFeature(data,sampleRate,null)[0]);
    }
}