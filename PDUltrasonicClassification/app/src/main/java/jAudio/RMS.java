package jAudio;

/**
 * A feature extractor that extracts the Root Mean Square (RMS) from a set of
 * samples. This is a good measure of the power of a signal.
 *
 * <p>RMS is calculated by summing the squares of each sample, dividing this
 * by the number of samples in the window, and finding the square root of the
 * result.
 *
 * <p>No extracted feature values are stored in objects of this class.
 *
 * @author Cory McKay
 */
public class RMS
        extends Feature
{
	/* CONSTRUCTOR **************************************************************/


    /**
     * Basic constructor that sets the definition and dependencies (and their
     * offsets) of this feature.
     */
    public RMS()
    {
        name = "Root Mean Square";
        definition = "A measure of the power of a signal.";
        dependencies = null;
        offsets = null;
    }
	/* PUBLIC METHODS **********************************************************/
    /**
     * Extracts this feature from the given samples at the given sampling
     * rate and given the other feature values.
     *
     * <p>In the case of this feature, the sampling_rate and
     * other_feature_values parameters are ignored.
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
        double sum = 0.0;
        for (int samp = 0; samp < samples.length; samp++)
            sum += Math.pow(samples[samp], 2);
        double rms = Math.sqrt(sum / samples.length);
        double[] result = new double[1];
        result[0] = rms;
        return result;
    }
}
