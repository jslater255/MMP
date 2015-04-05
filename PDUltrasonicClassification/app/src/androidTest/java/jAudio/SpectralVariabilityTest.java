package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class SpectralVariabilityTest extends TestCase {

    double[] data;
    double sampleRate;
    SpectralVariability spectralVariability;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        sampleRate = 8000;
        spectralVariability = new SpectralVariability();
    }

    public void testExtractFeature() throws Exception {
            assertEquals("RMS extract feature return array length",1,spectralVariability.extractFeature(data,sampleRate,data).length);
            assertEquals("RMS extract feature first value",0.0,spectralVariability.extractFeature(data,sampleRate,data)[0]);
    }
}