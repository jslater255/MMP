package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class StrongestFrequencyViaZeroCrossingsTest extends TestCase {

    double[] data;
    double sampleRate;
    StrongestFrequencyViaZeroCrossings strongestFrequencyViaZeroCrossings;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        sampleRate = 8000;
        strongestFrequencyViaZeroCrossings = new StrongestFrequencyViaZeroCrossings();
    }

    public void testExtractFeature() throws Exception {
        assertEquals("strongestFrequencyViaZeroCrossings extract feature return array length",1,strongestFrequencyViaZeroCrossings.extractFeature(data,sampleRate,data).length);
        assertEquals("strongestFrequencyViaZeroCrossings extract feature first value",0.05,strongestFrequencyViaZeroCrossings.extractFeature(data,sampleRate,data)[0]);
    }
}