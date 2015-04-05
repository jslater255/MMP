package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class SpectralRolloffPointTest extends TestCase {

    double[] data;
    double sampleRate;
    SpectralRolloffPoint spectralRolloffPoint;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        sampleRate = 8000;
        spectralRolloffPoint = new SpectralRolloffPoint();
    }

    public void testExtractFeature() throws Exception {
        assertEquals("spectralRolloffPoint extract feature return array length",1,spectralRolloffPoint.extractFeature(data,sampleRate,data).length);
        assertEquals("spectralRolloffPoint extract feature first value",0.8499875,spectralRolloffPoint.extractFeature(data,sampleRate,data)[0]);
    }
}