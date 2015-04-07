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
 * FUNCTION      : A structure for the Audio file. The struct
 *                  holds all the information, from the file name
 *                  to all the feature values.
 * <p/>
 * INPUTS        : None
 * <p/>
 * OUTPUTS       :
 * <p/>
 * AMENDMENTS    :  Created by, James Slater on 01/04/15.
 * <p/>
 * --------------------------------------------------------------
 */
public class AudioStruct {
    /**
     * If you change the window size it will make the rule base fail.
     *
     * If changed please re work out the values for the rule base.
     */
    private final int window_size = 512;
    /**
     * The file name as the path is the same for all files.
     */
    private String fileName;
    /**
     * The sample rate of the audio file, for example:-
     * - 8000
     * - 11,025
     * - 16,000
     * - 22,050
     * - 44,100
     */
    private int sampleRate;
    /**
     * This is the wave from the audio file made up of doubles
     */
    private double[] wav;
    /**
     * Holds all the positions of the windows
     */
    private int[] window_start_indices;
    /**
     * Holds the sample set of values from wav array
     */
    private double[] wav_sample_window;
    /**
     * The Compactness variables.
     *
     * This is a good measure of how important a role regular beats
     * play in a piece of music. This is calculated by finding the
     * sum of all values in the beat histogram.
     *
     * An array of the values at each window.
     * Windows Average.
     * Windows standard deviation
     */
    private double[] compactnessWindowValues;
    private double compactnessAvg;
    private double compactnessStandDev;
    /**
     * The Spectral Centroid variables.
     *
     * The spectral centroid is a measure used in digital signal
     * processing to characterise a spectrum. It indicates where the
     * "center of mass" of the spectrum is.
     *
     * An array of the values at each window.
     * Windows Average.
     * Windows standard deviation
     */
    private double[] spectralCentroidWindowValues;
    private double spectralCentroidAvg;
    private double spectralCentroidStandDev;
    /**
     * The Spectral Rolloff Point variables.
     *
     * This is a measure measure of the amount of the right-skewedness
     * of the power spectrum. The spectral rolloff point is the
     * fraction of bins in the power spectrum at which 85% of the
     * power is at lower frequencies.
     *
     * An array of the values at each window.
     * Windows Average.
     * Windows standard deviation
     */
    private double[] spectralRolloffPointWindowValues;
    private double spectralRolloffPointAvg;
    private double spectralRolloffPointStandDev;
    /**
     * The Spectral Variability variables.
     * An array of the values at each window.
     * Windows Average.
     * Windows standard deviation
     */
    private double[] spectralVariabilityWindowValues;
    private double spectralVariabilitytAvg;
    private double spectralVariabilityStandDev;
    /**
     * The Root Means Squre (RMS) variables.
     *
     * This is a good measure of the power of a signal. RMS is
     * calculated by summing the squares of each sample, dividing
     * this by the number of samples in the window, and finding the
     * square root of the result.
     *
     * An array of the values at each window.
     * Windows Average.
     * Windows standard deviation
     */
    private double[] RMSWindowValues;
    private double RMSAvg;
    private double RMSStandDev;
    /**
     * The Zero Crossing variables.
     *
     * Zero crossings are calculated by finding the number
     * of times the signal changes sign from one sample to
     * another (or touches the zero axis).
     *
     * An array of the values at each window.
     * Windows Average.
     * Windows standard deviation
     */
    private double[] zeroCrossingWindowValues;
    private double zeroCrossingAvg;
    private double zeroCrossingStandDev;
    /**
     * The Strongest Frequency via Zero Crossing variables.
     *
     * This is found by mapping the fraction in the zero-crossings to a
     * frequency in Hz.
     *
     * An array of the values at each window.
     * Windows Average.
     * Windows standard deviation
     */
    private double[] strongestFrequencyViaZeroCrossingsWindowValues;
    private double strongestFrequencyViaZeroCrossingsAvg;
    private double strongestFrequencyViaZeroCrossingsStandDev;
    /**
     * The Strongest Frequency via Spectral Centroid variables.
     *
     * This is found by mapping the fraction in the spectral centroid
     * to a frequency in Hz.
     *
     * An array of the values at each window.
     * Windows Average.
     * Windows standard deviation
     */
    private double[] strongestFrequencyViaSpectralCentroidWindowValues;
    private double strongestFrequencyViaSpectralCentroidAvg;
    private double strongestFrequencyViaSpectralCentroidStandDev;
    /**
     * The Strongest Frequency via Fast Fourier Transform (FFT) Max variables.
     *
     * This is found by finding the highest bin in the power spectrum.
     *
     * An array of the values at each window.
     * Windows Average.
     * Windows standard deviation
     */
    private double[] strongestFrequencyViaFFTMaxWindowValues;
    private double strongestFrequencyViaFFTMaxAvg;
    private double strongestFrequencyViaFFTMaxStandDev;

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : AudioStruct Constructor
     * <p/>
     * FUNCTION      : Sets the file name, and sets all the Avg and
     *                  StandDev variables to -1. This is done to check
     *                  if they have already been calculated so we do
     *                  calculate it more than once.
     * <p/>
     * INPUTS        : String fileName
     * <p/>
     * OUTPUTS       : fileName has been initialised.
     *                  All average and standard deviation variables
     *                  and been set.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public AudioStruct(String fileName) {
        this.fileName = fileName;
        // set the sample window up to the window size
        wav_sample_window = new double[window_size];
        // set all values to -1
        // I do this because we can check if they have been calculated
        spectralRolloffPointAvg = spectralRolloffPointStandDev
                = strongestFrequencyViaFFTMaxAvg = strongestFrequencyViaFFTMaxStandDev
                = strongestFrequencyViaSpectralCentroidAvg = strongestFrequencyViaSpectralCentroidStandDev
                = strongestFrequencyViaZeroCrossingsAvg = strongestFrequencyViaZeroCrossingsStandDev
                = zeroCrossingAvg = zeroCrossingStandDev
                = RMSAvg = RMSStandDev
                = spectralVariabilitytAvg = spectralVariabilityStandDev
                = spectralCentroidAvg = spectralCentroidStandDev
                = compactnessAvg = compactnessStandDev = -1;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : setWavArray
     * <p/>
     * FUNCTION      : Sets the array wav. Once set we work out the
     *                  where the start of all the windows are and
     *                  fill the window_start_indices array with the
     *                  positions.
     * <p/>
     * INPUTS        : Array of double values called wav.
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public void setWavArray(double[] wav)
    {
        this.wav = wav;
        // Calculate the window start indices
        // Code from jAudio
        LinkedList<Integer> window_start_indices_list = new LinkedList<>();
        int this_start = 0;
        while (this_start < wav.length)
        {
            window_start_indices_list.add(this_start);
            this_start += window_size;
        }
        Integer[] window_start_indices_I = window_start_indices_list.toArray(new Integer[1]);
        window_start_indices = new int[window_start_indices_I.length];
        for (int i = 0; i < window_start_indices.length; i++)
        {
            window_start_indices[i] = window_start_indices_I[i];
        }// End of code from jAudio
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : calFeatures
     * <p/>
     * FUNCTION      : Calculate all the features from the audio data.
     * <p/>
     * INPUTS        : None
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public void calFeatures() {
        try {
            /**
             * As we have now know the how many windows are in the wav file.
             * We can set up the double arrays
             */
            spectralRolloffPointWindowValues = new double[window_start_indices.length];
            strongestFrequencyViaZeroCrossingsWindowValues = new double[window_start_indices.length];
            zeroCrossingWindowValues = new double[window_start_indices.length];
            RMSWindowValues = new double[window_start_indices.length];
            spectralVariabilityWindowValues = new double[window_start_indices.length];
            spectralCentroidWindowValues = new double[window_start_indices.length];
            compactnessWindowValues = new double[window_start_indices.length];
            strongestFrequencyViaSpectralCentroidWindowValues = new double[window_start_indices.length];
            strongestFrequencyViaFFTMaxWindowValues = new double[window_start_indices.length];
            /**
             * Extract features from each window one by one and add save the results.
             * The last window is zero-padded at the end if it falls off the edge of the provided samples
             * Find the samples in this window and zero-pad if necessary
             */
            for (int win = 0; win < window_start_indices.length; win++) {
                // code from jAudio
                int start_sample = window_start_indices[win];
                int end_sample = start_sample + window_size - 1;
                if (end_sample < wav.length)
                    System.arraycopy(wav,
                            start_sample, wav_sample_window,
                            start_sample - start_sample,
                            end_sample + 1 - start_sample);
                else
                    for (int samp = start_sample; samp <= end_sample; samp++) {
                        if (samp < wav.length)
                            wav_sample_window[samp - start_sample] = wav[samp];
                        else
                            wav_sample_window[samp - start_sample] = 0.0;
                    }// End of code from jAudio
                /**
                 * wav_sample_window has been filled with the value.
                 * Now to work out values from this window.
                 *
                 * magnitudeSpectrum is needed to work out the compactness & Spectral Variability.
                 * We use the magnitude spectrum is used for each window.
                 */
                /*
      Holds the magnitude and power spectrum of the current window
     */
                double[] magnitudeSpectrum = new MagnitudeSpectrum().extractFeature(this.wav_sample_window, 0, null);
                /**
                 *  The powerSpectrum is needed to work out the spectralCentroid,
                 *                                              Spectral Rolloff Point,
                 *                                              Strongest Frequency Via Spectral Centroid &
                 *                                              Strongest Frequency Via FFT Max.
                 *  We use the power spectrum is used for each window.
                 */
                double[] powerSpectrum = new PowerSpectrum().extractFeature(this.wav_sample_window, 0, null);
                /**
                 * As the extract feature returns an array we get the first value in the array, position 0
                 */
                compactnessWindowValues[win] = new Compactness().extractFeature(this.wav_sample_window, this.sampleRate, magnitudeSpectrum)[0];
                spectralVariabilityWindowValues[win] = new SpectralVariability().extractFeature(this.wav_sample_window, this.sampleRate, magnitudeSpectrum)[0];
                spectralCentroidWindowValues[win] = new SpectralCentroid().extractFeature(this.wav_sample_window, this.sampleRate, powerSpectrum)[0];
                spectralRolloffPointWindowValues[win] = new SpectralRolloffPoint().extractFeature(this.wav_sample_window, this.sampleRate, powerSpectrum)[0];
                /**
                 * We pass null at the end as they have no dependencies.
                 */
                RMSWindowValues[win] = new RMS().extractFeature(this.wav_sample_window, this.sampleRate, null)[0];
                zeroCrossingWindowValues[win] = new ZeroCrossings().extractFeature(this.wav_sample_window, this.sampleRate, null)[0];
                /**
                 * I make a new array for the single value of this window to be passed.
                 */
                double[] tmp = new double[1];
                tmp[0] = zeroCrossingWindowValues[win];
                /**
                 * Passing the temp array holding the single value, as the
                 * strongestFrequencyViaZeroCrossingsWindowValues has one dependency of the
                 * Zero Crossing of this window.
                 */
                strongestFrequencyViaZeroCrossingsWindowValues[win] = new StrongestFrequencyViaZeroCrossings().extractFeature(this.wav_sample_window, this.sampleRate, tmp)[0];
                /**
                 * strongestFrequencyViaSpectralCentroidWindowValues has a dependency of
                 * spectralCentroidWindowValues and the power spectrum of the current window.
                 *
                 * Within both StrongestFrequencyViaSpectralCentroid & StrongestFrequencyViaFFTMax
                 * classes, have overloaded the extractFeature method as they take more than one
                 * dependency.
                 */
                tmp[0] = spectralCentroidWindowValues[win];
                strongestFrequencyViaSpectralCentroidWindowValues[win] = new StrongestFrequencyViaSpectralCentroid().extractFeature(null, this.sampleRate, tmp, powerSpectrum)[0];
                /**
                 * StrongestFrequencyViaFFTMax has a dependency of FFTBinFrequencies so we
                 * pass in the information, FFTBinFrequencies has no dependencies.
                 */
                strongestFrequencyViaFFTMaxWindowValues[win] = new StrongestFrequencyViaFFTMax().extractFeature(this.wav_sample_window, this.sampleRate, powerSpectrum, new FFTBinFrequencies().extractFeature(this.wav_sample_window, this.sampleRate, null))[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getFileName
     * <p/>
     * FUNCTION      : A getter for fileName
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : String file name
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
     * CALL NAME     : getWav
     * <p/>
     * FUNCTION      : A getter for the wave array from the audio file
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : double array
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public double[] getWav() {
        return wav;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSampleRate
     * <p/>
     * FUNCTION      : A getter for the sample rate of the audio file
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : Integer local variable sampleRate.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * ---------------------------------------------------------------
     */
    public int getSampleRate() {
        return sampleRate;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : setSampleRate
     * <p/>
     * FUNCTION      : sets the sample rate
     * <p/>
     * INPUTS        : Integer sampleRate
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getCompactnessAvg
     * <p/>
     * FUNCTION      : Gets the local variable compactnessWindowValues
     *                  and works out the average of the array. It
     *                  also checks to see if the compactnessAvg is
     *                  negative 1, if so it means the value has not
     *                  been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : Double value of Compactness average
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 01/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getCompactnessAvg() {
        if (-1 == compactnessAvg) {
            compactnessAvg = new StatCalculator().avg(compactnessWindowValues);
        }
        return compactnessAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getCompactnessStandDev
     * <p/>
     * FUNCTION      : Gets the local variable compactnessWindowValues
     *                  and works out the standard deviation of the
     *                  array. It also checks to see if the
     *                  compactnessStandDev is negative 1, if so it means
     *                  the value has not been calculated yet.
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
        if (-1 == compactnessStandDev) {
            compactnessStandDev = new StatCalculator().stand_dev(compactnessWindowValues);
        }
        return compactnessStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSpectralCentroidAvg
     * <p/>
     * FUNCTION      : Gets the local variable spectralCentroidWindowValues
     *                  and works out the average of the array. It
     *                  also checks to see if the spectralCentroidAvg is
     *                  negative 1, if so it means the value has not
     *                  been calculated yet.
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
        if (-1 == spectralCentroidAvg) {
            spectralCentroidAvg = new StatCalculator().avg(spectralCentroidWindowValues);
        }
        return spectralCentroidAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSpectralCentroidStandDev
     * <p/>
     * FUNCTION      : Gets the local variable spectralCentroidWindowValues
     *                  and works out the standard deviation of the
     *                  array. It also checks to see if the
     *                  spectralCentroidStandDev is negative 1, if so it means
     *                  the value has not been calculated yet.
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
        if (-1 == spectralCentroidStandDev) {
            spectralCentroidStandDev = new StatCalculator().stand_dev(spectralCentroidWindowValues);
        }
        return spectralCentroidStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSpectralRolloffPointAvg
     * <p/>
     * FUNCTION      : Gets the local variable spectralRolloffPointWindowValues
     *                  and works out the average of the array. It
     *                  also checks to see if the spectralRolloffPointAvg is
     *                  negative 1, if so it means the value has not
     *                  been calculated yet.
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
        if (-1 == spectralRolloffPointAvg) {
            spectralRolloffPointAvg = new StatCalculator().avg(spectralRolloffPointWindowValues);
        }
        return spectralRolloffPointAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSpectralRolloffPointStandDev
     * <p/>
     * FUNCTION      : Gets the local variable spectralRolloffPointWindowValues
     *                  and works out the standard deviation of the
     *                  array. It also checks to see if the
     *                  spectralRolloffPointStandDev is negative 1, if so it means
     *                  the value has not been calculated yet.
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
        if (-1 == spectralRolloffPointStandDev) {
            spectralRolloffPointStandDev = new StatCalculator().stand_dev(spectralRolloffPointWindowValues);
        }
        return spectralRolloffPointStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSpectralVariabilitytAvg
     * <p/>
     * FUNCTION      : Gets the local variable spectralVariabilityWindowValues
     *                  and works out the average of the array. It
     *                  also checks to see if the spectralVariabilitytAvg is
     *                  negative 1, if so it means the value has not
     *                  been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getSpectralVariabilitytAvg() {
        if (-1 == spectralVariabilitytAvg) {
            spectralVariabilitytAvg = new StatCalculator().avg(spectralVariabilityWindowValues);
        }
        return spectralVariabilitytAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSpectralVariabilityStandDev
     * <p/>
     * FUNCTION      : Gets the local variable spectralVariabilityWindowValues
     *                  and works out the standard deviation of the
     *                  array. It also checks to see if the
     *                  spectralVariabilityStandDev is negative 1, if so it means
     *                  the value has not been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getSpectralVariabilityStandDev() {
        if (-1 == spectralVariabilityStandDev) {
            spectralVariabilityStandDev = new StatCalculator().stand_dev(spectralVariabilityWindowValues);
        }
        return spectralVariabilityStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getRMSAvg
     * <p/>
     * FUNCTION      : Gets the local variable RMSWindowValues
     *                  and works out the average of the array. It
     *                  also checks to see if the RMSAvg is
     *                  negative 1, if so it means the value has not
     *                  been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getRMSAvg() {
        if (-1 == RMSAvg) {
            RMSAvg = new StatCalculator().avg(RMSWindowValues);
        }
        return RMSAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getRMSStandDev
     * <p/>
     * FUNCTION      : Gets the local variable RMSWindowValues
     *                  and works out the standard deviation of the
     *                  array. It also checks to see if the
     *                  RMSStandDev is negative 1, if so it means
     *                  the value has not been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getRMSStandDev() {
        if (-1 == RMSStandDev) {
            RMSStandDev = new StatCalculator().stand_dev(RMSWindowValues);
        }
        return RMSStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getZeroCrossingAvg
     * <p/>
     * FUNCTION      : Gets the local variable zeroCrossingWindowValues
     *                  and works out the average of the array. It
     *                  also checks to see if the zeroCrossingAvg is
     *                  negative 1, if so it means the value has not
     *                  been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getZeroCrossingAvg() {
        if (-1 == zeroCrossingAvg) {
            zeroCrossingAvg = new StatCalculator().avg(zeroCrossingWindowValues);
        }
        return zeroCrossingAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getZeroCrossingStandDev
     * <p/>
     * FUNCTION      : Gets the local variable zeroCrossingWindowValues
     *                  and works out the standard deviation of the
     *                  array. It also checks to see if the
     *                  zeroCrossingStandDev is negative 1, if so it means
     *                  the value has not been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getZeroCrossingStandDev() {
        if (-1 == zeroCrossingStandDev) {
            zeroCrossingStandDev = new StatCalculator().stand_dev(zeroCrossingWindowValues);
        }
        return zeroCrossingStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getStrongestFrequencyViaZeroCrossingsAvg
     * <p/>
     * FUNCTION      : Gets the local variable
     *                  strongestFrequencyViaZeroCrossingsWindowValues
     *                  and works out the average of the array. It
     *                  also checks to see if the
     *                  strongestFrequencyViaZeroCrossingsAvg is
     *                  negative 1, if so it means the value has not
     *                  been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getStrongestFrequencyViaZeroCrossingsAvg() {
        if (-1 == strongestFrequencyViaZeroCrossingsAvg) {
            strongestFrequencyViaZeroCrossingsAvg = new StatCalculator().avg(strongestFrequencyViaZeroCrossingsWindowValues);
        }
        return strongestFrequencyViaZeroCrossingsAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getStrongestFrequencyViaZeroCrossingsStandDev
     * <p/>
     * FUNCTION      : Gets the local variable
     *                  strongestFrequencyViaZeroCrossingsWindowValues
     *                  and works out the standard deviation of the
     *                  array. It also checks to see if the
     *                  strongestFrequencyViaZeroCrossingsStandDev
     *                  is negative 1, if so it means
     *                  the value has not been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getStrongestFrequencyViaZeroCrossingsStandDev() {
        if (-1 == strongestFrequencyViaZeroCrossingsStandDev) {
            strongestFrequencyViaZeroCrossingsStandDev = new StatCalculator().stand_dev(strongestFrequencyViaZeroCrossingsWindowValues);
        }
        return strongestFrequencyViaZeroCrossingsStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getStrongestFrequencyViaSpectralCentroidAvg
     * <p/>
     * FUNCTION      : Gets the local variable
     *                  strongestFrequencyViaSpectralCentroidWindowValues
     *                  and works out the average of the array. It
     *                  also checks to see if the
     *                  strongestFrequencyViaSpectralCentroidAvg is
     *                  negative 1, if so it means the value has not
     *                  been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getStrongestFrequencyViaSpectralCentroidAvg() {
        if (-1 == strongestFrequencyViaSpectralCentroidAvg) {
            strongestFrequencyViaSpectralCentroidAvg = new StatCalculator().avg(strongestFrequencyViaSpectralCentroidWindowValues);
        }
        return strongestFrequencyViaSpectralCentroidAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getStrongestFrequencyViaSpectralCentroidStandDev
     * <p/>
     * FUNCTION      : Gets the local variable
     *                  strongestFrequencyViaSpectralCentroidWindowValues
     *                  and works out the standard deviation of the
     *                  array. It also checks to see if the
     *                  strongestFrequencyViaSpectralCentroidStandDev
     *                  is negative 1, if so it means
     *                  the value has not been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getStrongestFrequencyViaSpectralCentroidStandDev() {
        if (-1 == strongestFrequencyViaSpectralCentroidStandDev) {
            strongestFrequencyViaSpectralCentroidStandDev = new StatCalculator().stand_dev(strongestFrequencyViaSpectralCentroidWindowValues);
        }
        return strongestFrequencyViaSpectralCentroidStandDev;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getStrongestFrequencyViaFFTMaxAvg
     * <p/>
     * FUNCTION      : Gets the local variable
     *                  strongestFrequencyViaFFTMaxWindowValues
     *                  and works out the average of the array. It
     *                  also checks to see if the
     *                  strongestFrequencyViaFFTMaxAvg is
     *                  negative 1, if so it means the value has not
     *                  been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getStrongestFrequencyViaFFTMaxAvg() {
        if (-1 == strongestFrequencyViaFFTMaxAvg) {
            strongestFrequencyViaFFTMaxAvg = new StatCalculator().avg(strongestFrequencyViaFFTMaxWindowValues);
        }
        return strongestFrequencyViaFFTMaxAvg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getStrongestFrequencyViaFFTMaxStandDev
     * <p/>
     * FUNCTION      : Gets the local variable strongestFrequencyViaFFTMaxWindowValues
     *                  and works out the standard deviation of the
     *                  array. It also checks to see if the
     *                  strongestFrequencyViaFFTMaxStandDev is
     *                  negative 1, if so it means
     *                  the value has not been calculated yet.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getStrongestFrequencyViaFFTMaxStandDev() {
        if (-1 == strongestFrequencyViaFFTMaxStandDev) {
            strongestFrequencyViaFFTMaxStandDev = new StatCalculator().stand_dev(strongestFrequencyViaFFTMaxWindowValues);
        }
        return strongestFrequencyViaFFTMaxStandDev;
    }
}
