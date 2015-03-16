package com.cs394.jas38.pdultrasonicclassification;

import java.util.ArrayList;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : SpikeFeatureStruct
 * <p/>
 * FUNCTION      : A struct for each spike found. It will hold the x pos of Start, Peak, Cross Zero
 *                  and overall length
 * <p/>
 * AMENDMENTS    :  Created by, James Slater
 * <p/>
 * --------------------------------------------------------------
 */
public class SpikeFeatureStruct {

    /**
     * The local variables for each spike each is the x pos
     */
    private int crossZeroPos;
    private int peakPos;
    private int peakStart;
    private int spikeLength;

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : SpikeFeatureStruct
     * <p/>
     * FUNCTION      : The Constructor, it calls workoutPeakPos to find the remaining features.
     * <p/>
     * INPUTS        : Integer crossZeroPos, this is the mid point of a spike
     *                      and everything is calculated from here.
     *                 Double ArrayList wav, data of the audio file.
     * <p/>
     * OUTPUTS       : An instance of this data structure having been filled.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public SpikeFeatureStruct(int crossZeroPos, ArrayList<Double> wav)
    {
        this.crossZeroPos = crossZeroPos;
        workoutPeakPos(wav);
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getCrossZeroPos
     * <p/>
     * FUNCTION      : Returns the variable crossZeroPos.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : Integer crossZeroPos.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public int getCrossZeroPos()
    {
        return crossZeroPos;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getPeakPos
     * <p/>
     * FUNCTION      : Returns the variable peakPos.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : Integer peakPos.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public int getPeakPos()
    {
        return peakPos;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getPeakStart
     * <p/>
     * FUNCTION      : Returns the variable peakStart.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : Integer peakStart.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public int getPeakStart()
    {
        return peakStart;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSpikeLength
     * <p/>
     * FUNCTION      : Returns the variable spikeLength.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       : Integer spikeLength.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public int getSpikeLength()
    {
        return spikeLength;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : setSpikeLength
     * <p/>
     * FUNCTION      : Sets the local variable spikeLength. We work this out by
     *                  finding where the spike returns to zero, and calculating
     *                  the distance from the start of the spike.
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public void setSpikeLength(ArrayList<Double> wav)
    {
        //TODO
        int idx = crossZeroPos;
        while (wav.get(idx) < 0)
        {
            idx++;
        }
        System.out.println("Spike end: " + idx);
        spikeLength = idx - peakStart;
    } /* End of setSpikeLength */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : workoutPeakPos
     * <p/>
     * FUNCTION      :
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private void workoutPeakPos(ArrayList<Double> wav)
    {

        // As we work backwards to find the peak from where it has crossed zero
        for (int i = crossZeroPos; i > 5; i--)
        {
            //if((wav.get(i-2) > wav.get(i-3))&&(wav.get(i-1) >= wav.get(i-2))&&(wav.get(i) >= wav.get(i-1)) && (wav.get(i) >= wav.get(i+1)) && (wav.get(i+1) >= wav.get(i+2)) && (wav.get(i+2) > wav.get(i+3)))
            if ((wav.get(i - 1) >= wav.get(i - 2)) && (wav.get(i) >= wav.get(i - 1)) &&
                    (wav.get(i) >= wav.get(i + 1)) && (wav.get(i + 1) >= wav.get(i + 2)))
            {
                peakPos = i;
                //System.out.println("Peak at: " + peakPos + " and cross zero at: " + crossZeroPos);
                workoutPeakStart(wav);
                setSpikeLength(wav);
                break;
            }
        }
    } /* End of workoutPeakPos */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : workoutPeakStart
     * <p/>
     * FUNCTION      : To find where the spike starts from the peak position.
     *                  We check to find it
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    : Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private void workoutPeakStart(ArrayList<Double> wav)
    {
        int idx = peakPos;
        while ((wav.get(idx) > wav.get(idx - 5)) && (idx > 0))
        {
            if (wav.get(idx) < 0)
            {
                break;
            }
            idx--;
        }
        peakStart = idx;
    }/* End of workoutPeakStart */
}/* End of SpikeFeatureStruct */