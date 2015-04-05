package com.cs394.jas38.pdultrasonicclassification;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : Classifier
 * <p/>
 * FUNCTION      : Classifies the AudioStruct being passed in.
 * <p/>
 * AMENDMENTS    :  Created by, James Slater on 02/04/15.
 * <p/>
 * --------------------------------------------------------------
 */
public class Classifier
{
    /**
     * All these values have been calculated externally and will
     * be the features I test new data against.
     *
     * All these values have been calculated using a 512 sized window.
     * Using jAudio 2.0 software
     *
     * Non PD Values
     *
     * The Standard_Deviation values
     */
    private final double Spectral_Centroid_Overall_Standard_Deviation                           = 6.54E+00;
    private final double Spectral_Rolloff_Point_Overall_Standard_Deviation                      = 4.92E-02;
    private final double Compactness_Overall_Standard_Deviation                                 = 2.06E+02;
    private final double Spectral_Variability_Overall_Standard_Deviation                        = 5.14E-04;
    private final double Root_Mean_Square_Overall_Standard_Deviation                            = 1.77E-02;
    private final double Zero_Crossings_Overall_Standard_Deviation                              = 1.42E+01;
    private final double Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation      = 1.11E+02;
    private final double Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation   = 1.02E+02;
    private final double Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation         = 2.92E+02;
    /**
     * Average values
     */
    private final double Spectral_Centroid_Overall_Average                                      = 7.73E+01;
    private final double Spectral_Rolloff_Point_Overall_Average                                 = 4.34E-01;
    private final double Compactness_Overall_Average                                            = 1.76E+03;
    private final double Spectral_Variability_Overall_Average                                   = 3.88E-03;
    private final double Root_Mean_Square_Overall_Average                                       = 1.84E-01;
    private final double Zero_Crossings_Overall_Average                                         = 1.61E+02;
    private final double Strongest_Frequency_Via_Zero_Crossings_Overall_Average                 = 1.26E+03;
    private final double Strongest_Frequency_Via_Spectral_Centroid_Overall_Average              = 1.21E+03;
    private final double Strongest_Frequency_Via_FFT_Maximum_Overall_Average                    = 1.08E+03;

    /**
     * percentPD & percentNonPD are two variables that are totalled,
     * depending on the new audio features values. The higher the value
     * means the feature point to it being PD or not.
     */
    double percentPD;
    double percentNonPD;

    /**
     * The Audio structure of the wav file to be classified.
     */
    AudioStruct dataFile;

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : Classifier
     * <p/>
     * FUNCTION      : Classifies the AudioStruct being passed in.
     * <p/>
     * INPUTS        : AudioStruct of the audio file that needs classifying.
     *                  Also initialises the two double values to 0,
     *                  percentPD and percentNonPD.
     * <p/>
     * OUTPUTS       : A classification
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 02/04/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public Classifier(AudioStruct dataFile){
        this.dataFile = dataFile;
        // set the Classifier to 0.0
        percentPD = 0;
        percentNonPD = 0;
        classify();
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : classify
     * <p/>
     * FUNCTION      : Checks all values depending on the variable it
     *                  maybe greater than or less than.
     *                  If the check is true it will add the weight to
     *                  percentPD if false adds the weight to percentNonPD.
     *
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public void classify(){

        /**
         * Standard Deviation values
         */
        if (dataFile.getSpectralCentroidStandDev()
                > Spectral_Centroid_Overall_Standard_Deviation){
            percentPD += 0.5;
        }else{
            percentNonPD += 0.5;
        }

        if (dataFile.getSpectralRolloffPointStandDev()
                > Spectral_Rolloff_Point_Overall_Standard_Deviation){
            percentPD += 0.25;
        }else{
            percentNonPD += 0.25;
        }

        if (dataFile.getCompactnessStandDev()
                < Compactness_Overall_Standard_Deviation){
            percentPD += 2;
        }else{
            percentNonPD +=2;
        }

        if (dataFile.getSpectralVariabilityStandDev()
                > Spectral_Variability_Overall_Standard_Deviation){
            percentPD += 0.25;
        }else{
            percentNonPD += 0.25;
        }

        if (dataFile.getRMSStandDev()
                > Root_Mean_Square_Overall_Standard_Deviation){
            percentPD += 0.5;
        }else{
            percentNonPD += 0.5;
        }

        if (dataFile.getZeroCrossingStandDev()
                > Zero_Crossings_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getStrongestFrequencyViaZeroCrossingsStandDev()
                > Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation){
            percentPD +=2;
        }else{
            percentNonPD +=2;
        }

        if (dataFile.getStrongestFrequencyViaSpectralCentroidStandDev()
                > Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation){
            percentPD +=2;
        }else{
            percentNonPD +=2;
        }

        if (dataFile.getStrongestFrequencyViaFFTMaxStandDev()
                < Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation){
            percentPD += 2;
        }else{
            percentNonPD+= 2;
        }

        /**
         * Average values
         */
        if (dataFile.getSpectralCentroidAvg()
                < Spectral_Centroid_Overall_Average){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getSpectralRolloffPointAvg()
                > Spectral_Rolloff_Point_Overall_Average){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getCompactnessAvg()
                < Compactness_Overall_Average){
            percentPD +=3;
        }else{
            percentNonPD +=3;
        }

        if (dataFile.getSpectralVariabilitytAvg()
                < Spectral_Variability_Overall_Average){
            percentPD += 0.25;
        }else{
            percentNonPD += 0.25;
        }

        if (dataFile.getRMSAvg()
                < Root_Mean_Square_Overall_Average){
            percentPD += 0.5;
        }else{
            percentNonPD += 0.5;
        }

        if (dataFile.getZeroCrossingAvg()
                < Zero_Crossings_Overall_Average){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getStrongestFrequencyViaZeroCrossingsAvg()
                < Strongest_Frequency_Via_Zero_Crossings_Overall_Average){
            percentPD +=2;
        }else{
            percentNonPD +=2;
        }

        if (dataFile.getStrongestFrequencyViaSpectralCentroidAvg()
                < Strongest_Frequency_Via_Spectral_Centroid_Overall_Average){
            percentPD +=2;
        }else{
            percentNonPD +=2;
        }

        if (dataFile.getStrongestFrequencyViaFFTMaxAvg()
                < Strongest_Frequency_Via_FFT_Maximum_Overall_Average){
            percentPD +=2;
        }else{
            percentNonPD +=2;
        }
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getPercentNonPD
     * <p/>
     * FUNCTION      : A getter for percentNonPD value
     * <p/>
     * INPUTS        : None
     * <p/>
     * OUTPUTS       : Double value
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getPercentNonPD() {
        return percentNonPD;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getPercentPD
     * <p/>
     * FUNCTION      : A getter for percentPD value
     * <p/>
     * INPUTS        : None
     * <p/>
     * OUTPUTS       : Double value
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getPercentPD() {
        return percentPD;
    }
}
