package jAudio;

/**
 * A feature extractor that extracts the Spectral Cecntroid. This is a measure
 * of the "centre of mass" of the power spectrum.
 *
 * <p>This is calculated by calculating the mean bin of the power spectrum. The
 * result returned is a number from 0 to 1 that represents at what fraction of
 * the total number of bins this central frequency is.
 *
 * <p>No extracted feature values are stored in objects of this class.
 *
 * @author Cory McKay
 */
public class SpectralCentroid extends Feature
{
	/* CONSTRUCTOR **************************************************************/
    /**
     * Basic constructor that sets the definition and dependencies (and their
     * offsets) of this feature.
     *
     * <p>Daniel McEnnis 05-07-05	altered offsets to match dependancies
     */
    public SpectralCentroid()
    {
        name = "Spectral Centroid";
        definition = "The centre of mass of the power spectrum.";

        dependencies = new String[1];
        dependencies[0] = "Power Spectrum";

        offsets = new int[1];
        offsets[0] = 0;
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
        double[] pow_spectrum = other_feature_values;

        double total = 0.0;
        double weighted_total = 0.0;
        for (int bin = 0; bin < pow_spectrum.length; bin++)
        {
            weighted_total += bin * pow_spectrum[bin];
            total += pow_spectrum[bin];
        }

        double[] result = new double[1];
        if(total != 0.0){
            result[0] = weighted_total / total;
        }else{
            result[0] = 0.0;
        }
        return result;
    }

}
