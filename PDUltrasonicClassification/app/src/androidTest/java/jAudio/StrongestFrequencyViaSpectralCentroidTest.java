package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class StrongestFrequencyViaSpectralCentroidTest extends TestCase {

    double[] data;
    double sampleRate;
    StrongestFrequencyViaSpectralCentroid strongestFrequencyViaSpectralCentroid;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        sampleRate = 8000;
        strongestFrequencyViaSpectralCentroid = new StrongestFrequencyViaSpectralCentroid();
    }

    public void testExtractFeature() throws Exception {
        assertEquals("strongestFrequencyViaSpectralCentroid extract feature return array length",1,strongestFrequencyViaSpectralCentroid.extractFeature(data,sampleRate,data,data).length);
        assertEquals("strongestFrequencyViaSpectralCentroid extract feature first value",0.05,strongestFrequencyViaSpectralCentroid.extractFeature(data,sampleRate,data,data)[0]);
    }
}