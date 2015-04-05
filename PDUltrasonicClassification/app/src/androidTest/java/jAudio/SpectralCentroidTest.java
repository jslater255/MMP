package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class SpectralCentroidTest extends TestCase {

    double[] data;
    double sampleRate;
    SpectralCentroid spectralCentroid;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        sampleRate = 8000;
        spectralCentroid = new SpectralCentroid();
    }

    public void testExtractFeature() throws Exception {
        assertEquals("Spectral Centroid extract feature return array length",1,spectralCentroid.extractFeature(data,sampleRate,data).length);
        assertEquals("Spectral Centroid extract feature first value",39999.5,spectralCentroid.extractFeature(data,sampleRate,data)[0]);
    }
}