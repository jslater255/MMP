package jAudio;

import com.cs394.jas38.pdultrasonicclassification.StatCalculator;

/**
 * A feature extractor that finds the strongest frequency component of a
 * signal, in Hz.
 *
 * <p>This is found by finding the highest bin in the power spectrum.
 *
 * <p>No extracted feature values are stored in objects of this class.
 *
 * @author Cory McKay
 */
public class StrongestFrequencyViaFFTMax
        extends Feature
{
	/* CONSTRUCTOR **************************************************************/


    /**
     * Basic constructor that sets the definition and dependencies (and their
     * offsets) of this feature.
     */
    public StrongestFrequencyViaFFTMax()
    {
        name = "Strongest Frequency Via FFT Maximum";
        definition = "The strongest frequency component of a signal, in Hz, " +
                "found via finding the FFT bin with the highest power.";

        dependencies = new String[2];
        dependencies[0] = "Power Spectrum";
        dependencies[1] = "FFT Bin Frequency Labels";

        offsets = new int[2];
        offsets[0] = 0;
        offsets[1] = 0;
    }


	/* PUBLIC METHODS **********************************************************/


    /**
     * Extracts this feature from the given samples at the given sampling
     * rate and given the other feature values.
     *
     * <p>In the case of this feature, the sampling_rate parameter is ignored.
     *
     * @param samples				The samples to extract the feature from.
     * @param sampling_rate			The sampling rate that the samples are
     *								encoded with.
     * @param other_feature_values	The values of other features that are
     *								needed to calculate this value. The
     *								order and offsets of these features
     *								must be the same as those returned by
     *								this class's getDependencies and
     *								getDependencyOffsets methods respectively.
     *								The first indice indicates the feature/window
     *								and the second indicates the value.
     * @return						The extracted feature value(s).
     * @throws Exception			Throws an informative exception if
     *								the feature cannot be calculated.
     */
    public double[] extractFeature( double[] samples,
                                    double sampling_rate,
                                    double[] other_feature_values )
            throws Exception
    {
//        double[] power_spectrum = other_feature_values[0];
//        double[] labels = other_feature_values[1];
//        int highest_bin = jAudioFeatureExtractor.GeneralTools.Statistics. getIndexOfLargest(power_spectrum);
        double[] result = new double[1];
//        result[0] = labels[highest_bin];
        return result;
    }

    /**
     * Edited original above to fit with data required
     */
    public double[] extractFeature( double[] samples,
                                    double sampling_rate,
                                    double[] other_feature_values,
                                    double[] other_feature_values_1)
            throws Exception
    {
        double[] power_spectrum = other_feature_values;
        double[] labels = other_feature_values_1;
        int highest_bin = new StatCalculator().getIndexOfLargest(power_spectrum);
        double[] result = new double[1];
        result[0] = labels[highest_bin];
        return result;
    }

}
