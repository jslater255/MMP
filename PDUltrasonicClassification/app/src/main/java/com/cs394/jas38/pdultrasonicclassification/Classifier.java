package com.cs394.jas38.pdultrasonicclassification;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : Classifier
 * <p/>
 * FUNCTION      : Classifies the AudioStruct being passed in.
 * <p/>
 * INPUTS        :
 * <p/>
 * OUTPUTS       : A classification
 * <p/>
 * AMENDMENTS    :  Created by, James Slater on 02/04/15.
 * <p/>
 * --------------------------------------------------------------
 */
public class Classifier {


    /**
     * Non PD Values
     */
    // The Standard_Deviation values
    private final double Spectral_Centroid_Overall_Standard_Deviation                           = 6.54E+00;
    private final double Spectral_Rolloff_Point_Overall_Standard_Deviation                      = 4.92E-02;
    //private final double Spectral_Flux_Overall_Standard_Deviation                               = 8.95E-04;
    private final double Compactness_Overall_Standard_Deviation                                 = 2.06E+02;
    private final double Spectral_Variability_Overall_Standard_Deviation                        = 5.14E-04;
    private final double Root_Mean_Square_Overall_Standard_Deviation                            = 1.77E-02;
    //private final double Fraction_Of_Low_Energy_Windows_Overall_Standard_Deviation              = 2.61E-02;
    private final double Zero_Crossings_Overall_Standard_Deviation                              = 1.42E+01;
    private final double Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation      = 1.11E+02;
    private final double Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation   = 1.02E+02;
    private final double Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation         = 2.92E+02;
    // The Average values
    private final double Spectral_Centroid_Overall_Average                                      = 7.73E+01;
    private final double Spectral_Rolloff_Point_Overall_Average                                 = 4.34E-01;
    //private final double Spectral_Flux_Overall_Average                                          = 1.61E-03;
    private final double Compactness_Overall_Average                                            = 1.76E+03;
    private final double Spectral_Variability_Overall_Average                                   = 3.88E-03;
    private final double Root_Mean_Square_Overall_Average                                       = 1.84E-01;
    //private final double Fraction_Of_Low_Energy_Windows_Overall_Average                         = 5.50E-01;
    private final double Zero_Crossings_Overall_Average                                         = 1.61E+02;
    private final double Strongest_Frequency_Via_Zero_Crossings_Overall_Average                 = 1.26E+03;
    private final double Strongest_Frequency_Via_Spectral_Centroid_Overall_Average              = 1.21E+03;
    private final double Strongest_Frequency_Via_FFT_Maximum_Overall_Average                    = 1.08E+03;

    double percentPD;
    double percentNonPD;

    AudioStruct dataFile;

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : Classifier
     * <p/>
     * FUNCTION      : Classifies the AudioStruct being passed in.
     * <p/>
     * INPUTS        : AudioStruct of the audio file that needs classifying.
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

    public void classify(){

        /**
         * Standard Deviation values
         */
        if (dataFile.getSpectralCentroidStandDev()
                > Spectral_Centroid_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getSpectralRolloffPointStandDev()
                > Spectral_Rolloff_Point_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getCompactnessStandDev()
                < Compactness_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getSpectralVariabilityStandDev()
                > Spectral_Variability_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getRMSStandDev()
                > Root_Mean_Square_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getZeroCrossingStandDev()
                > Zero_Crossings_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getStrongestFrequencyViaZeroCrossingsStandDev()
                > Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getStrongestFrequencyViaSpectralCentroidStandDev()
                > Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getStrongestFrequencyViaFFTMaxStandDev()
                < Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation){
            percentPD++;
        }else{
            percentNonPD++;
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
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getSpectralVariabilitytAvg()
                < Spectral_Variability_Overall_Average){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getRMSAvg()
                < Root_Mean_Square_Overall_Average){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getZeroCrossingAvg()
                < Zero_Crossings_Overall_Average){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getStrongestFrequencyViaZeroCrossingsAvg()
                < Strongest_Frequency_Via_Zero_Crossings_Overall_Average){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getStrongestFrequencyViaSpectralCentroidAvg()
                < Strongest_Frequency_Via_Spectral_Centroid_Overall_Average){
            percentPD++;
        }else{
            percentNonPD++;
        }

        if (dataFile.getStrongestFrequencyViaFFTMaxAvg()
                < Strongest_Frequency_Via_FFT_Maximum_Overall_Average){
            percentPD++;
        }else{
            percentNonPD++;
        }
    }

    public double getPercentNonPD() {
        return percentNonPD;
    }

    public double getPercentPD() {
        return percentPD;
    }
}
