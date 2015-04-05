package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class FFTBinFrequenciesTest extends TestCase {

    double[] data;
    double sampleRate;
    FFTBinFrequencies fftBinFreq;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        sampleRate = 8000;
        fftBinFreq = new FFTBinFrequencies();
    }

    public void testExtractFeature() throws Exception {
        assertEquals("Testing FFTBinFrequencies extract feature double array length", 65536, fftBinFreq.extractFeature(data,sampleRate,null).length);
        assertEquals("Testing FFTBinFrequencies extract features: ", 0.030517578125, fftBinFreq.extractFeature(data,sampleRate,null)[0]);
    }
}