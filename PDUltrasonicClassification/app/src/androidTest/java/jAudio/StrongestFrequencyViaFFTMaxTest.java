package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class StrongestFrequencyViaFFTMaxTest extends TestCase {

    double[] data;
    double sampleRate;
    StrongestFrequencyViaFFTMax strongestFrequencyViaFFTMax;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        sampleRate = 8000;
        strongestFrequencyViaFFTMax = new StrongestFrequencyViaFFTMax();
    }

    public void testExtractFeatureOverloaded() throws Exception {
        assertEquals("strongestFrequencyViaFFTMax extract feature return array length",1,strongestFrequencyViaFFTMax.extractFeature(data,sampleRate,data,data).length);
        assertEquals("strongestFrequencyViaFFTMax extract feature first value",1.0,strongestFrequencyViaFFTMax.extractFeature(data,sampleRate,data,data)[0]);
    }
}