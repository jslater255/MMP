package com.cs394.jas38.pdultrasonicclassification;

import java.util.LinkedList;

import jAudio.Compactness;
import jAudio.FFTBinFrequencies;
import jAudio.MagnitudeSpectrum;
import jAudio.PowerSpectrum;
import jAudio.RMS;
import jAudio.SpectralCentroid;
import jAudio.SpectralRolloffPoint;
import jAudio.SpectralVariability;
import jAudio.StrongestFrequencyViaFFTMax;
import jAudio.StrongestFrequencyViaSpectralCentroid;
import jAudio.StrongestFrequencyViaZeroCrossings;
import jAudio.ZeroCrossings;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : AudioStruct
 * <p/>
 * FUNCTION      :
 * <p/>
 * INPUTS        :
 * <p/>
 * OUTPUTS       :
 * <p/>
 * AMENDMENTS    :  Created by, James Slater on 01/04/15.
 * <p/>
 * --------------------------------------------------------------
 */
public class AudioStruct {

    /**
     * The file name as the path is the same for all files.
     */
    private String fileName;
    /**
     * The sample rate of the audio file, for example:-
     *      - 8000
     *      - 11,025
     *      - 16,000
     *      - 22,050
     *      - 44,100
     */
    private int sampleRate;
    /**
     * This is the wave from the audio file made up of doubles
     */
    private double[] wav;
    /**
     * If you change the window size it will make the rule base fail.
     *
     * If changed please re work out the values for the rule base.
     */
    private final int window_size = 512;

    private int[] window_start_indices;
    private double[] wav_sample_window;

    private double[] magnitudeSpectrum;
    private double[] powerSpectrum;

    private double[] compactnessWindowValues;
    private double compactnessAvg;
    private double compactnessStandDev;

    private double[] spectralCentroidWindowValues;
    private double spectralCentroidAvg;
    private double spectralCentroidStandDev;

    private double[] spectralRolloffPointWindowValues;
    private double spectralRolloffPointAvg;
    private double spectralRolloffPointStandDev;

    private double[] spectralVariabilityWindowValues;
    private double spectralVariabilitytAvg;
    private double spectralVariabilityStandDev;

    private double[] RMSWindowValues;
    private double RMSAvg;
    private double RMSStandDev;

    private double[] zeroCrossingWindowValues;
    private double zeroCrossingAvg;
    private double zeroCrossingStandDev;

    private double[] strongestFrequencyViaZeroCrossingsWindowValues;
    private double strongestFrequencyViaZeroCrossingsAvg;
    private double strongestFrequencyViaZeroCrossingsStandDev;

    private double[] strongestFrequencyViaSpectralCentroidWindowValues;
    private double strongestFrequencyViaSpectralCentroidAvg;
    private double strongestFrequencyViaSpectralCentroidStandDev;

    private double[] strongestFrequencyViaFFTMaxdWindowValues;
    private double strongestFrequencyViaFFTMaxAvg;
    private double strongestFrequencyViaFFTMaxStandDev;

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public AudioStruct(String fileName){
        this.fileName = fileName;
        // set the sample window up to the window size
        wav_sample_window = new double[window_size];
        // set all values to -1
        // I do this because we can check if they have been calculated
        spectralRolloffPointAvg                             = spectralRolloffPointStandDev
                = strongestFrequencyViaFFTMaxAvg            = strongestFrequencyViaFFTMaxStandDev
                = strongestFrequencyViaSpectralCentroidAvg  = strongestFrequencyViaSpectralCentroidStandDev
                = strongestFrequencyViaZeroCrossingsAvg     = strongestFrequencyViaZeroCrossingsStandDev
                = zeroCrossingAvg                           = zeroCrossingStandDev
                = RMSAvg                                    = RMSStandDev
                = spectralVariabilitytAvg                   = spectralVariabilityStandDev
                = spectralCentroidAvg                       = spectralCentroidStandDev
                = compactnessAvg                            = compactnessStandDev = -1;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public void setSampleRate(int sampleRate){
        this.sampleRate = sampleRate;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public void setWavArray(double[] wav){
            this.wav = wav;

        // Calculate the window start indices
        // Code from jAudio
        LinkedList<Integer> window_start_indices_list = new LinkedList<>();
        int this_start = 0;
        while (this_start < wav.length) {
            window_start_indices_list.add(new Integer(this_start));
            this_start += window_size;
        }
        Integer[] window_start_indices_I = window_start_indices_list.toArray(new Integer[1]);
        window_start_indices = new int[window_start_indices_I.length];
        for (int i = 0; i < window_start_indices.length; i++)
        {
            window_start_indices[i] = window_start_indices_I[i].intValue();
        }// End of code from jAudio
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public void calFeatures(){
        try {
            // As we have now know the how many windows are in the wav file
            // we can set up the double arrays
            spectralRolloffPointWindowValues = new double[window_start_indices.length];
            strongestFrequencyViaZeroCrossingsWindowValues = new double[window_start_indices.length];
            zeroCrossingWindowValues = new double[window_start_indices.length];
            RMSWindowValues = new double[window_start_indices.length];
            spectralVariabilityWindowValues = new double[window_start_indices.length];
            spectralCentroidWindowValues = new double[window_start_indices.length];
            compactnessWindowValues = new double[window_start_indices.length];
            strongestFrequencyViaSpectralCentroidWindowValues = new double[window_start_indices.length];
            strongestFrequencyViaFFTMaxdWindowValues = new double[window_start_indices.length];

            // Extract features from each window one by one and add save the
            // results.
            // The last window is zero-padded at the end if it falls off the edge of
            // the
            // provided samples.
            // Find the samples in this window and zero-pad if necessary
            for(int win = 0; win < window_start_indices.length; win++){
                // code from jAudio
                int start_sample = window_start_indices[win];
                int end_sample = start_sample + window_size - 1;
                if (end_sample < wav.length)
                    for (int samp = start_sample; samp <= end_sample; samp++)
                        wav_sample_window[samp - start_sample] = wav[samp];
                else
                    for (int samp = start_sample; samp <= end_sample; samp++) {
                        if (samp < wav.length)
                            wav_sample_window[samp - start_sample] = wav[samp];
                        else
                            wav_sample_window[samp - start_sample] = 0.0;
                    }// End of code from jAudio

                // wav_sample_window has been filled with the values
                // Now to work out values from this window
                // magnitudeSpectrum is needed to work out the compactness
                // we need the magnitudeSpectrum for each window
                magnitudeSpectrum = new MagnitudeSpectrum().extractFeature(this.wav_sample_window,0,null);
                // powerSpectrum is needed to work out the spectralCentroid
                // we need the powerSpectrum for each window
                powerSpectrum = new PowerSpectrum().extractFeature(this.wav_sample_window,0,null);
                // we get pos 0 as it only sends back one value
                compactnessWindowValues[win] = new Compactness().extractFeature(this.wav_sample_window,this.sampleRate, this.magnitudeSpectrum)[0];
                spectralVariabilityWindowValues[win] = new SpectralVariability().extractFeature(this.wav_sample_window,this.sampleRate, this.magnitudeSpectrum)[0];
                // Both Spectral values need the power spectrum as a dependency
                  spectralCentroidWindowValues[win] = new SpectralCentroid().extractFeature(this.wav_sample_window,this.sampleRate, this.powerSpectrum)[0];
                spectralRolloffPointWindowValues[win] = new SpectralRolloffPoint().extractFeature(this.wav_sample_window,this.sampleRate, this.powerSpectrum)[0];
                // Has no dependencies
                RMSWindowValues[win] = new RMS().extractFeature(this.wav_sample_window,this.sampleRate,null)[0];
                zeroCrossingWindowValues[win] = new ZeroCrossings().extractFeature(this.wav_sample_window,this.sampleRate,null)[0];
                // Only dependency is Zero Crossing value
                double[] tmp = new double[1];
                tmp[0] = zeroCrossingWindowValues[win];
                strongestFrequencyViaZeroCrossingsWindowValues[win] = new StrongestFrequencyViaZeroCrossings().extractFeature(this.wav_sample_window,this.sampleRate,tmp)[0];
                tmp[0] = spectralCentroidWindowValues[win];
                strongestFrequencyViaSpectralCentroidWindowValues[win] = new StrongestFrequencyViaSpectralCentroid().extractFeature(null,this.sampleRate,tmp,this.powerSpectrum)[0];
                strongestFrequencyViaFFTMaxdWindowValues[win] = new StrongestFrequencyViaFFTMax().extractFeature(this.wav_sample_window, this.sampleRate, this.powerSpectrum, new FFTBinFrequencies().extractFeature(this.wav_sample_window, this.sampleRate, null))[0];
            }
            System.out.println("strongestFrequencyViaFFTMaxdWindowValues avg = " + getStrongestFrequencyViaFFTMaxAvg() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public double[] getWav(){
        return wav;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public int getSampleRate() {
        return sampleRate;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getCompactnessAvg() {
        if (-1 == compactnessAvg){
            compactnessAvg = new StatCalculator().avg(compactnessWindowValues);
        }
        return compactnessAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getCompactnessStandDev() {
        if (-1 == compactnessStandDev){
            compactnessStandDev = new StatCalculator().stand_dev(compactnessWindowValues);
        }
        return compactnessStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getSpectralCentroidAvg() {
        if (-1 == spectralCentroidAvg){
            spectralCentroidAvg = new StatCalculator().avg(spectralCentroidWindowValues);
        }
        return spectralCentroidAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getSpectralCentroidStandDev() {
        if (-1 == spectralCentroidStandDev){
            spectralCentroidStandDev = new StatCalculator().stand_dev(spectralCentroidWindowValues);
        }
        return spectralCentroidStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getSpectralRolloffPointAvg() {
        if (-1 == spectralRolloffPointAvg){
            spectralRolloffPointAvg = new StatCalculator().avg(spectralRolloffPointWindowValues);
        }
        return spectralRolloffPointAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getSpectralRolloffPointStandDev() {
        if (-1 == spectralRolloffPointStandDev){
            spectralRolloffPointStandDev = new StatCalculator().stand_dev(spectralRolloffPointWindowValues);
        }
        return spectralRolloffPointStandDev;
    }

    public double getSpectralVariabilitytAvg() {
        if (-1 == spectralVariabilitytAvg){
            spectralVariabilitytAvg = new StatCalculator().avg(spectralVariabilityWindowValues);
        }
        return spectralVariabilitytAvg;
    }

    public double getSpectralVariabilityStandDev() {
        if (-1 == spectralVariabilityStandDev){
            spectralVariabilityStandDev = new StatCalculator().stand_dev(spectralVariabilityWindowValues);
        }
        return spectralVariabilityStandDev;
    }

    public double getRMSAvg() {
        if (-1 == RMSAvg){
            RMSAvg = new StatCalculator().avg(RMSWindowValues);
        }
        return RMSAvg;
    }

    public double getRMSStandDev() {
        if (-1 == RMSStandDev){
            RMSStandDev = new StatCalculator().stand_dev(RMSWindowValues);
        }
        return RMSStandDev;
    }

    public double getZeroCrossingAvg() {
        if (-1 == zeroCrossingAvg){
            zeroCrossingAvg = new StatCalculator().avg(zeroCrossingWindowValues);
        }
        return zeroCrossingAvg;
    }

    public double getZeroCrossingStandDev() {
        if (-1 == zeroCrossingStandDev){
            zeroCrossingStandDev = new StatCalculator().stand_dev(zeroCrossingWindowValues);
        }
        return zeroCrossingStandDev;
    }

    public double getStrongestFrequencyViaZeroCrossingsAvg() {
        if (-1 == strongestFrequencyViaZeroCrossingsAvg){
            strongestFrequencyViaZeroCrossingsAvg = new StatCalculator().avg(strongestFrequencyViaZeroCrossingsWindowValues);
        }
        return strongestFrequencyViaZeroCrossingsAvg;
    }

    public double getStrongestFrequencyViaZeroCrossingsStandDev() {
        if (-1 == strongestFrequencyViaZeroCrossingsStandDev){
            strongestFrequencyViaZeroCrossingsStandDev = new StatCalculator().stand_dev(strongestFrequencyViaZeroCrossingsWindowValues);
        }
        return strongestFrequencyViaZeroCrossingsStandDev;
    }

    public double getStrongestFrequencyViaSpectralCentroidAvg() {
        if (-1 == strongestFrequencyViaSpectralCentroidAvg){
            strongestFrequencyViaSpectralCentroidAvg = new StatCalculator().avg(strongestFrequencyViaSpectralCentroidWindowValues);
        }
        return strongestFrequencyViaSpectralCentroidAvg;
    }

    public double getStrongestFrequencyViaSpectralCentroidStandDev() {
        if (-1 == strongestFrequencyViaSpectralCentroidStandDev){
            strongestFrequencyViaSpectralCentroidStandDev = new StatCalculator().stand_dev(strongestFrequencyViaSpectralCentroidWindowValues);
        }
        return strongestFrequencyViaSpectralCentroidStandDev;
    }

    public double getStrongestFrequencyViaFFTMaxAvg() {
        if (-1 == strongestFrequencyViaFFTMaxAvg){
            strongestFrequencyViaFFTMaxAvg = new StatCalculator().avg(strongestFrequencyViaFFTMaxdWindowValues);
        }
        return strongestFrequencyViaFFTMaxAvg;
    }

    public double getStrongestFrequencyViaFFTMaxStandDev() {
        if (-1 == strongestFrequencyViaFFTMaxStandDev){
            strongestFrequencyViaFFTMaxStandDev = new StatCalculator().stand_dev(strongestFrequencyViaFFTMaxdWindowValues);
        }
        return strongestFrequencyViaFFTMaxStandDev;
    }
}
