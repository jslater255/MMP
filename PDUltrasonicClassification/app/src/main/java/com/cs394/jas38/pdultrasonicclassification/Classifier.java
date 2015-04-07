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
    private final double NonPD_Spectral_Centroid_Overall_Standard_Deviation                         = 6.54E+00;
    private final double NonPD_Spectral_Rolloff_Point_Overall_Standard_Deviation                    = 4.92E-02;
    private final double NonPD_Compactness_Overall_Standard_Deviation                               = 2.06E+02;
    private final double NonPD_Spectral_Variability_Overall_Standard_Deviation                      = 5.14E-04;
    private final double NonPD_Root_Mean_Square_Overall_Standard_Deviation                          = 1.77E-02;
    private final double NonPD_Zero_Crossings_Overall_Standard_Deviation                            = 1.42E+01;
    private final double NonPD_Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation    = 1.11E+02;
    private final double NonPD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation = 1.02E+02;
    private final double NonPD_Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation       = 2.92E+02;
    /**
     * Average values
     */
    private final double NonPD_Spectral_Centroid_Overall_Average                                    = 7.73E+01;
    private final double NonPD_Spectral_Rolloff_Point_Overall_Average                               = 4.34E-01;
    private final double NonPD_Compactness_Overall_Average                                          = 1.76E+03;
    private final double NonPD_Spectral_Variability_Overall_Average                                 = 3.88E-03;
    private final double NonPD_Root_Mean_Square_Overall_Average                                     = 1.84E-01;
    private final double NonPD_Zero_Crossings_Overall_Average                                       = 1.61E+02;
    private final double NonPD_Strongest_Frequency_Via_Zero_Crossings_Overall_Average               = 1.26E+03;
    private final double NonPD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Average            = 1.21E+03;
    private final double NonPD_Strongest_Frequency_Via_FFT_Maximum_Overall_Average                  = 1.08E+03;
    /**
    * PD Values
    *
    * The Standard_Deviation values
    */
    private final double PD_Spectral_Centroid_Overall_Standard_Deviation                            = 1.05E+01;
    private final double PD_Spectral_Rolloff_Point_Overall_Standard_Deviation                       = 9.73E-02;
    private final double PD_Compactness_Overall_Standard_Deviation                                  = 1.92E+02;
    private final double PD_Spectral_Variability_Overall_Standard_Deviation                         = 7.63E-04;
    private final double PD_Root_Mean_Square_Overall_Standard_Deviation                             = 2.90E-02;
    private final double PD_Zero_Crossings_Overall_Standard_Deviation                               = 2.04E+01;
    private final double PD_Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation       = 1.59E+02;
    private final double PD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation    = 1.64E+02;
    private final double PD_Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation          = 1.48E+02;
    /**
     * Average values
     */
    private final double PD_Spectral_Centroid_Overall_Average                                       = 4.91E+01;
    private final double PD_Spectral_Rolloff_Point_Overall_Average                                  = 8.35E+00;
    private final double PD_Compactness_Overall_Average                                             = 1.25E+03;
    private final double PD_Spectral_Variability_Overall_Average                                    = 2.26E-03;
    private final double PD_Root_Mean_Square_Overall_Average                                        = 1.09E-01;
    private final double PD_Zero_Crossings_Overall_Average                                          = 1.03E+02;
    private final double PD_Strongest_Frequency_Via_Zero_Crossings_Overall_Average                  = 8.03E+02;
    private final double PD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Average               = 7.67E+02;
    private final double PD_Strongest_Frequency_Via_FFT_Maximum_Overall_Average                     = 6.05E+02;
    /**
     * The number of features
     */
    private final double number_of_features = 18;

    /**
     * percentPD & percentNonPD are two variables that are totalled,
     * depending on the new audio features values. The higher the value
     * means the feature point to it being PD or not.
     */
    private double percentPD;
    private double percentNonPD;
    /**
     * The certainty of the classification.
     */
    private double PDCertainty;
    private double NonPDCertainty;
    /**
     * The Audio structure of the wav file to be classified.
     */
    private final AudioStruct dataFile;

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
        PDCertainty = 0;
        NonPDCertainty = 0;
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
     * <p/>
     * INPUTS        : None
     * <p/>
     * OUTPUTS       : None
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public void classify(){
        /**
         * Standard Deviation values
         */
        /**
         * Checking SpectralCentroidStandDev
         */
        getSmallest(Math.abs((dataFile.getSpectralCentroidStandDev() - PD_Spectral_Centroid_Overall_Standard_Deviation)),
                Math.abs((dataFile.getSpectralCentroidStandDev() - NonPD_Spectral_Centroid_Overall_Standard_Deviation)),
                Math.abs((PD_Spectral_Centroid_Overall_Standard_Deviation - NonPD_Spectral_Centroid_Overall_Standard_Deviation)));

        /**
         * Checking SpectralRolloffPointStandDev
         */
        getSmallest(Math.abs((dataFile.getSpectralRolloffPointStandDev() - PD_Spectral_Rolloff_Point_Overall_Standard_Deviation)),
                Math.abs((dataFile.getSpectralRolloffPointStandDev() - NonPD_Spectral_Rolloff_Point_Overall_Standard_Deviation)),
                Math.abs((PD_Spectral_Rolloff_Point_Overall_Standard_Deviation - NonPD_Spectral_Rolloff_Point_Overall_Standard_Deviation)));
        /**
         * Checking CompactnessStandDev
         */
         getSmallest(Math.abs((dataFile.getCompactnessStandDev() - PD_Compactness_Overall_Standard_Deviation)),
                 Math.abs((dataFile.getCompactnessStandDev() - NonPD_Compactness_Overall_Standard_Deviation)),
                 Math.abs((PD_Compactness_Overall_Standard_Deviation - NonPD_Compactness_Overall_Standard_Deviation)));
        /**
         * Checking SpectralVariabilityStandDev
         */
        getSmallest(Math.abs((dataFile.getSpectralVariabilityStandDev() - PD_Spectral_Variability_Overall_Standard_Deviation)),
                Math.abs((dataFile.getSpectralVariabilityStandDev() - NonPD_Spectral_Variability_Overall_Standard_Deviation)),
                Math.abs((PD_Spectral_Variability_Overall_Standard_Deviation - NonPD_Spectral_Variability_Overall_Standard_Deviation)));
        /**
         * Checking RMSStandDev
         */
        getSmallest(Math.abs((dataFile.getRMSStandDev() - PD_Root_Mean_Square_Overall_Standard_Deviation)),
                Math.abs((dataFile.getRMSStandDev() - NonPD_Root_Mean_Square_Overall_Standard_Deviation)),
                Math.abs((PD_Root_Mean_Square_Overall_Standard_Deviation - NonPD_Root_Mean_Square_Overall_Standard_Deviation)));
        /**
         * Checking ZeroCrossingStandDev
         */
        getSmallest(Math.abs((dataFile.getZeroCrossingStandDev() - PD_Zero_Crossings_Overall_Standard_Deviation)),
                Math.abs((dataFile.getZeroCrossingStandDev() - NonPD_Zero_Crossings_Overall_Standard_Deviation)),
                Math.abs((PD_Zero_Crossings_Overall_Standard_Deviation - NonPD_Zero_Crossings_Overall_Standard_Deviation)));
        /**
         * Checking StrongestFrequencyViaZeroCrossingsStandDev
         */
        getSmallest(Math.abs((dataFile.getStrongestFrequencyViaZeroCrossingsStandDev() - PD_Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation)),
                Math.abs((dataFile.getStrongestFrequencyViaZeroCrossingsStandDev() - NonPD_Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation)),
                Math.abs((PD_Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation - NonPD_Strongest_Frequency_Via_Zero_Crossings_Overall_Standard_Deviation)));
        /**
         * Checking StrongestFrequencyViaSpectralCentroidStandDev
         */
        getSmallest(Math.abs((dataFile.getStrongestFrequencyViaSpectralCentroidStandDev() - PD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation)),
                Math.abs((dataFile.getStrongestFrequencyViaSpectralCentroidStandDev() - NonPD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation)),
                Math.abs((PD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation - NonPD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Standard_Deviation)));
        /**
         * Checking StrongestFrequencyViaFFTMaxStandDev
         */
        getSmallest(Math.abs((dataFile.getStrongestFrequencyViaFFTMaxStandDev() - PD_Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation)),
                Math.abs((dataFile.getStrongestFrequencyViaFFTMaxStandDev() - NonPD_Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation)),
                Math.abs((PD_Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation - NonPD_Strongest_Frequency_Via_FFT_Maximum_Overall_Standard_Deviation)));
        /**
         * Average values
         */
        /**
         * Checking SpectralCentroidAvg
         */
        getSmallest(Math.abs((dataFile.getSpectralCentroidAvg() - PD_Spectral_Centroid_Overall_Average)),
                Math.abs((dataFile.getSpectralCentroidAvg() - NonPD_Spectral_Centroid_Overall_Average)),
                Math.abs((PD_Spectral_Centroid_Overall_Average - NonPD_Spectral_Centroid_Overall_Average)));
        /**
         * Checking SpectralRolloffPointAvg
         */
        getSmallest(Math.abs((dataFile.getSpectralRolloffPointAvg() - PD_Spectral_Rolloff_Point_Overall_Average)),
                Math.abs((dataFile.getSpectralRolloffPointAvg() - NonPD_Spectral_Rolloff_Point_Overall_Average)),
                Math.abs((PD_Spectral_Rolloff_Point_Overall_Average - NonPD_Spectral_Rolloff_Point_Overall_Average)));
        /**
         * Checking CompactnessAvg
         */
        getSmallest(Math.abs((dataFile.getCompactnessAvg() - PD_Compactness_Overall_Average)),
                Math.abs((dataFile.getCompactnessAvg() - NonPD_Compactness_Overall_Average)),
                Math.abs((PD_Compactness_Overall_Average - NonPD_Compactness_Overall_Average)));
        /**
         * Checking SpectralVariabilitytAvg
         */
        getSmallest(Math.abs((dataFile.getSpectralVariabilitytAvg() - PD_Spectral_Variability_Overall_Average)),
                Math.abs((dataFile.getSpectralVariabilitytAvg() - NonPD_Spectral_Variability_Overall_Average)),
                Math.abs((PD_Spectral_Variability_Overall_Average - NonPD_Spectral_Variability_Overall_Average)));
        /**
         * Checking RMSAvg
         */
        getSmallest(Math.abs((dataFile.getRMSAvg() - PD_Root_Mean_Square_Overall_Average)),
                Math.abs((dataFile.getRMSAvg() - NonPD_Root_Mean_Square_Overall_Average)),
                Math.abs((PD_Root_Mean_Square_Overall_Average - NonPD_Root_Mean_Square_Overall_Average)));
        /**
         * Checking ZeroCrossingAvg
         */
        getSmallest(Math.abs((dataFile.getZeroCrossingAvg() - PD_Zero_Crossings_Overall_Average)),
                Math.abs((dataFile.getZeroCrossingAvg() - NonPD_Zero_Crossings_Overall_Average)),
                Math.abs((PD_Zero_Crossings_Overall_Average - NonPD_Zero_Crossings_Overall_Average)));
        /**
         * Checking StrongestFrequencyViaZeroCrossingsAvg
         */
        getSmallest(Math.abs((dataFile.getStrongestFrequencyViaZeroCrossingsAvg() - PD_Strongest_Frequency_Via_Zero_Crossings_Overall_Average)),
                Math.abs((dataFile.getStrongestFrequencyViaZeroCrossingsAvg() - NonPD_Strongest_Frequency_Via_Zero_Crossings_Overall_Average)),
                Math.abs((PD_Strongest_Frequency_Via_Zero_Crossings_Overall_Average - NonPD_Strongest_Frequency_Via_Zero_Crossings_Overall_Average)));
        /**
         * Checking StrongestFrequencyViaSpectralCentroidAvg
         */
        getSmallest(Math.abs((dataFile.getStrongestFrequencyViaSpectralCentroidAvg() - PD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Average)),
                Math.abs((dataFile.getStrongestFrequencyViaSpectralCentroidAvg() - NonPD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Average)),
                Math.abs((PD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Average - NonPD_Strongest_Frequency_Via_Spectral_Centroid_Overall_Average)));
        /**
         * Checking StrongestFrequencyViaFFTMaxAvg
         */
        getSmallest(Math.abs((dataFile.getStrongestFrequencyViaFFTMaxAvg() - PD_Strongest_Frequency_Via_FFT_Maximum_Overall_Average)),
                Math.abs((dataFile.getStrongestFrequencyViaFFTMaxAvg() - NonPD_Strongest_Frequency_Via_FFT_Maximum_Overall_Average)),
                Math.abs((PD_Strongest_Frequency_Via_FFT_Maximum_Overall_Average - NonPD_Strongest_Frequency_Via_FFT_Maximum_Overall_Average)));
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
        return (percentNonPD/number_of_features)*100;
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
        return (percentPD/number_of_features)*100;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getNonPDCertainty
     * <p/>
     * FUNCTION      : A getter for NonPDCertainty value
     * <p/>
     * INPUTS        : None
     * <p/>
     * OUTPUTS       : Double value
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getNonPDCertainty() {
        return (NonPDCertainty/percentNonPD)*100;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getPDCertainty
     * <p/>
     * FUNCTION      : A getter for PDCertainty value
     * <p/>
     * INPUTS        : None
     * <p/>
     * OUTPUTS       : Double value
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    public double getPDCertainty() {
        return (PDCertainty/percentPD)*100;
    }

    /**
     * Private Methods
     */
    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSmallest
     * <p/>
     * FUNCTION      : Finds the smallest difference from their
     *                  respective feature, the difference is passed
     *                  in and works out if it equal greater than or
     *                  less than and returns the appropriate value.
     *                  If they are even make it void and it does
     *                  nothing.
     *                  Also calls the method getCertainty and passed
     *                  the two differences to work out how certain it
     *                  is to the classification made.
     * <p/>
     * INPUTS        : double differencePD, double differenceNonPD,
     *                  double calFeatureDiff
     * <p/>
     * OUTPUTS       : Void
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    private void getSmallest(double differencePD, double differenceNonPD, double calFeatureDiff){
        if (differencePD < differenceNonPD)
        {
            percentPD++;
            PDCertainty += getCertainty(calFeatureDiff, Math.abs((differencePD - differenceNonPD)));
        }
        else
        {
            percentNonPD++;
            NonPDCertainty += getCertainty(calFeatureDiff, Math.abs((differencePD - differenceNonPD)));
        }
    }
    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getCertainty
     * <p/>
     * FUNCTION      : Finds the smallest difference from their
     *                  respective feature, the difference is passed
     *                  in and works out if it equal greater than or
     *                  less than and returns the appropriate value.
     * <p/>
     * INPUTS        : double calFeatureDiff, double newFeatureDiff
     * <p/>
     * OUTPUTS       : double between o - 1 this is the certainty
     *                  that the feature is PD or Non-PD
     * <p/>
     * AMENDMENTS    :  Created by, James Slater.
     * <p/>
     * --------------------------------------------------------------
     */
    private double getCertainty(double calFeatureDiff, double newFeatureDiff){
        return newFeatureDiff/calFeatureDiff;
    }
}
