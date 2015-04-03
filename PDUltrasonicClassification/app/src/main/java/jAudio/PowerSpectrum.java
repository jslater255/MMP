package jAudio;

/**
 * Created by Slaters on 01/04/15.
 */
public class PowerSpectrum extends Feature {

    /* CONSTRUCTOR **************************************************************/


    /**
     * Basic constructor that sets the definition and dependencies (and their
     * offsets) of this feature.
     */
    public PowerSpectrum()
    {
        name = "Power Spectrum";
        definition = "A measure of the power of different frequency components.";

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
        FFT fft = new FFT(samples, null, false, true);
        return fft.getPowerSpectrum();
    }

}
