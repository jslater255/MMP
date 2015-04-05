package jAudio;

import junit.framework.TestCase;

/**
 * Testing the values to be returned, calculated using the jAudio software.
 */
public class FFTTest extends TestCase {

    double[] data;
    FFT fft;

    public void setUp() throws Exception {
        super.setUp();
        data = new double[80000];
        for (int idx = 0; idx < data.length; idx++)
        {
            data[idx] = 1;
        }
        fft = new FFT(data, data, false, true);
    }

    public void testGetMagnitudeSpectrum() throws Exception {
        assertEquals("Testing Magnitude Spectrum", 0.7066031691479006, fft.getMagnitudeSpectrum()[0]);
        assertEquals("Testing correct length being returned", 65536, fft.getMagnitudeSpectrum().length);
    }

    public void testGetPowerSpectrum() throws Exception {
        assertEquals("Testing Power Spectrum", 65442.681801914005, fft.getPowerSpectrum()[0]);
        assertEquals("Testing correct length being returned", 65536, fft.getPowerSpectrum().length);
    }

    public void testGetPhaseAngles() throws Exception {
        assertEquals("Testing Phase Angles", 59.74398988706612, fft.getPhaseAngles()[0]);
        assertEquals("Testing correct length being returned", 65536, fft.getPhaseAngles().length);
    }

    public void testGetBinLabels() throws Exception {
        // 8000 is the default sample rate Bin labels needs
        assertEquals("Testing Bin Labels", 0.0, fft.getBinLabels(8000)[0]);
        assertEquals("Testing correct length being returned", 65536, fft.getBinLabels(8000).length);
    }

    public void testGetRealValues() throws Exception {
        assertEquals("Testing Real Values", 46665.867495852595, fft.getRealValues()[0]);
        assertEquals("Testing correct length being returned", 131072, fft.getRealValues().length);
    }
}