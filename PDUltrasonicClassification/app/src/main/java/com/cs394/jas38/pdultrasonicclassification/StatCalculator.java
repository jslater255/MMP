package com.cs394.jas38.pdultrasonicclassification;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CLASS NAME    : StatCalculator
 * <p/>
 * FUNCTION      : All that statistical evaluation of the data is done.
 * <p/>
 * AMENDMENTS    :  Created by, James Slater
 * <p/>
 * --------------------------------------------------------------
 */
public class StatCalculator
{
    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : run_avg
     * <p/>
     * FUNCTION      : A running average of the newly loaded file
     * <p/>
     * INPUTS        : Integer size, this is the number of doubles the FIFO list will hold.
     *                 Double array wav, is the information from the Audio file. it is called
     *                 wav because it is from the wav file
     * <p/>
     * OUTPUTS       : The new ArrayList of the now filtered audio data.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public ArrayList<Double> run_avg(int size, double[] wav)
    {
        /**
         * A FIFO (First In First Out) list to do the running avg of the input data.
         */
        LinkedList<Double> fifo = new LinkedList<>();
        /**
         * The new running data put into a ArrayList. This is done because it is easier
         * to use the data and more efficient.
         */
        ArrayList<Double> wav_run_avg = new ArrayList<>();

        for (double idx : wav) {
            /**
             * Add each double in the array to the FIFO list. Once it has reach the size
             * limit it will remove the first double in the list.
             */
            fifo.add(idx);
            if (fifo.size() > size) {
                fifo.removeFirst();
            }
            /**
             * Create a temp double to hold the avg of the FIFO list.
             */
            double temp = 0;
            for (double jdx : fifo) {
                temp += jdx;
            }
            /**
             * Adds the new averaged value to the new array
             */
            wav_run_avg.add((temp / size));
        }
        return wav_run_avg;
    }/* End of run_avg */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : avg
     * <p/>
     * FUNCTION      : Takes the average from the ArrayList given
     * <p/>
     * INPUTS        : ArrayList<Double> wav, is all the audio data
     * <p/>
     * OUTPUTS       : Double of the average of the ArrayList wav
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public double avg(ArrayList<Double> wav)
    {
        double avg_out = 0;
        for (double idx : wav)
        {
            avg_out += idx;
        }
        return (avg_out / wav.size());
    }/* End of avg */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : stand_dev
     * <p/>
     * FUNCTION      : Gets the Standard Deviation of the ArrayList given
     * <p/>
     * INPUTS        : Double avg, this will be worked out already.
     *                  ArrayList<Double> wav, the loaded file array
     * <p/>
     * OUTPUTS       : The standard deviation
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public double stand_dev(double avg_int, ArrayList<Double> wav)
    {
        double sum_x_mean = 0.0;
        for (double idx : wav)
        {
            sum_x_mean += Math.pow((idx - avg_int), 2);
        }
        return (sum_x_mean / (wav.size() - 1));
    }/* End of stand_dev */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : findSpikeFeatures
     * <p/>
     * FUNCTION      : Finds the peak location, spike start (x value) and the spike end.
     * <p/>
     * INPUTS        : Integer crossZeroPoint, this is when the Spike has crossed zero (the middle point).
     *                  Double ArrayList wav, is the audio data.
     * <p/>
     * OUTPUTS       : A SpikeFeatureStruct holding all the information for a spike.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public SpikeFeatureStruct findSpikeFeatures(int crossZeroPoint, ArrayList<Double> wav)
    {
        /**
         * Create a new Spike struct passing in the integer crossZeroPoint and the ArrayList wav.
         * Within the struct it will find all the features of a spike and return a complete struct.
         */
        return new SpikeFeatureStruct(crossZeroPoint, wav);
    }/* End of findSpikeFeatures */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : variance
     * <p/>
     * FUNCTION      : To calculate the variance of the ArrayList.
     * <p/>
     * INPUTS        : Double avg, is the average of the ArrayList.
     *                 Double ArrayList wav, the audio data.
     * <p/>
     * OUTPUTS       : The variance of the ArrayList wav
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public double variance(double avg, ArrayList<Double> wav)
    {
        double temp = 0;
        for (double idx : wav) {
            temp += Math.pow((idx - avg), 2);
        }
        temp = temp / (wav.size() - 1);
        return Math.sqrt(temp);
    }/* End of variance */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : countCrossZero
     * <p/>
     * FUNCTION      : Finds when the plot crosses zero, only +1 if the slope is steep enough before.
     * <p/>
     * INPUTS        : ArrayList<Double> wav, the loaded audio file array.
     * <p/>
     * OUTPUTS       : The number of times the audio data crosses data.
     * <p/>
     * AMENDMENTS    : Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public int countCrossZero(ArrayList<Double> wav)
    {
        /**
         * Creates a FIFO list, to check in case we have crosses zero within the list.
         * Creates an ArrayList of SpikeFeatureStruct
         * a double of the location of the last spike
         */
        LinkedList<Double> fifo = new LinkedList<>();
        LinkedList<SpikeFeatureStruct> spikeList = new LinkedList<>();
        double lastPos = 0;

        for (int idx = 0; idx < (wav.size() - 1); idx++)
        {
            fifo.add(wav.get(idx));
            if (fifo.size() > 10)
            {
                fifo.removeFirst();
                /**
                 * Once the FIFO list is full we can check :-
                 *      - If the values have crossed zero.
                 *      - If the slope leading up to the crossing of zero is steep enough.
                 */
                if (crossZero(fifo) && (getSlope(wav.get((idx - 5)), wav.get((idx - 15)), idx - 5, idx - 15) < -0.02))
                {
                    /**
                     *  Cast to int so it rounds.
                     *  Makes sure the we do not find the same spike twice.
                     */
                    if ((int) (lastPos+ 0.6) != (int) (0.6 * (idx - 5)))
                    {
                        lastPos = (0.6 * (idx - 5));
                        spikeList.add(findSpikeFeatures(idx, wav));
                    }
                }
            }
        }
        System.out.println("Cross Zero: " + spikeList.size());
        /* TODO change this to just return the full list. */
        return spikeList.size();
    }/* End of countCrossZero */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSlope
     * <p/>
     * FUNCTION      : Calculates the steepness of the two positions passed in.
     * <p/>
     * INPUTS        : Double y1 & y2, are the two y values to be tested,
     *                  integers x1 & x2 are the two X positions (Distance) between the y values
     * <p/>
     * OUTPUTS       : The steepness as a double.
     * <p/>
     * AMENDMENTS    : Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public Double getSlope(Double y1, Double y2, int x1, int x2)
    {
        /**
         * Slope = (y1 - y2) / (x1 - x2)
         * 0.6 as that is how many milliseconds for each point.
         */
        return (y1 - y2) / ((0.6 * x1) - (0.6 * x2));
    }/* End of getSlope */

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : crossZero
     * <p/>
     * FUNCTION      : Takes an array of 10 values from the y-axis, if the
     *                      first 5 (pos 0-4) are above zero and the latter 5 (5-9)
     *                      are below zero then we have crossed zero
     * <p/>
     * INPUTS        : The FIFO list from the method countCrossZero().
     *                  It hold a sample(of 10) of data from the audio file.
     * <p/>
     * OUTPUTS       : Boolean if it has crossed zero or not.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private boolean crossZero(LinkedList<Double> fifo)
    {
        boolean check = false;
        /**
         * 5 values above zero
         */
        if ((fifo.get(0) > 0) && (fifo.get(1) > 0) && (fifo.get(2) > 0) && (fifo.get(3) > 0) && (fifo.get(4) > 0))
        {   /**
             * 5 values below zero *
             */
            if ((fifo.get(6) < 0) && (fifo.get(7) < 0) && (fifo.get(8) < 0) && (fifo.get(9) < 0))
            {
                check = true;
            }
        }
        return check;
    }/* End of crossZero */

}/* End of the StatCalculator */
