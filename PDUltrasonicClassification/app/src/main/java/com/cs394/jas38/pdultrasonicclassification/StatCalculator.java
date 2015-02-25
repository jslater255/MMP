package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CLASS NAME    : StatCalculator
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
public class StatCalculator {

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : run_avg
     * <p/>
     * FUNCTION      : to do a running average of the newly loaded CSV file
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public ArrayList<Double> run_avg(int size, ArrayList<Double> wav){

        LinkedList<Double> fifo = new LinkedList<Double>();
        ArrayList<Double> wav_run_avg = new ArrayList<>();

        for(double idx : wav){
            fifo.add(idx);
            if (fifo.size() > size)
            {
                fifo.removeFirst();
            }
            double temp = 0;
            for(double jdx : fifo)
            {
                temp += jdx;
            }
            wav_run_avg.add((temp/size));
        }
        System.out.println("");
        return wav_run_avg;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : avg
     * <p/>
     * FUNCTION      : Takes the average from the ArrayList given
     * <p/>
     * INPUTS        : ArrayList<Double> wav
     * <p/>
     * OUTPUTS       : Double of the average
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public double avg(ArrayList<Double> wav){

        double avg_out = 0;

        for(double idx : wav)
        {
            avg_out += idx;
        }

        avg_out = avg_out/wav.size();

        return avg_out;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : stand_dev
     * <p/>
     * FUNCTION      : Gets the Standard Deviation of the ArrayList given
     * <p/>
     * INPUTS        : Double avg, this will be worked out already.
     *                 ArrayList<Double> wav, the loaded CSV file array
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public double stand_dev(double avg_int, ArrayList<Double> wav){

        double stan_dev_int;
        double sum_x_mean = 0.0;

        for (double idx : wav){
            sum_x_mean += Math.pow((idx - avg_int),2);
        }

        stan_dev_int = sum_x_mean/ (wav.size() -1);

        return stan_dev_int;

    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     :
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
    public int peaks(ArrayList<Double> wav){
        int peaks = 0;

        //Starts at 1 to be able to check the first peak
        for(int i=1; i < wav.size(); i++)
        {
            //Make sure we have  filtered out the noise
            //Make sure we are not at the end of the array
            if((wav.get(i) > 0.5) && (i < wav.size()))
            {
                if(wav.get(i) > wav.get(i-1) && wav.get(i) > wav.get(i+1))
                {
                    peaks++;
                    /**System.out.println("");
                     System.out.println("pos= " + i);
                     System.out.println("b1= " + wav.get(i-1));
                     System.out.println("b2 (peak)= " + wav.get(i));
                     System.out.println("b3= " + wav.get(i+1));**/
                }
            }
        }
        return peaks;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : variance
     * <p/>
     * FUNCTION      : to calculate the variance of the ArrayList
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public double variance(double avg, ArrayList<Double> wav){
        double temp = 0;
        for(double idx : wav)
        {
            temp += Math.pow((idx - avg),2);
        }
        temp = temp / (wav.size()-1);
        return Math.sqrt(temp);
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : countCrossZero
     * <p/>
     * FUNCTION      : Finds when the plot crosses zero if the slope is steep enough
     * <p/>
     * INPUTS        : ArrayList<Double> wav, the loaded CSV file array.
     *                  Context context, the context of the UI
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public int countCrossZero(ArrayList<Double> wav, Context context){

        LinkedList<Double> fifo = new LinkedList<Double>();
        int crossZeroCount = 0;
        double lastPos = 0;

        for (int idx = 0;idx < (wav.size()-1); idx++)
        {
            //fifo.add(getSlope(wav.get(idx),wav.get(idx+1), idx));
            fifo.add(wav.get(idx));

            if (fifo.size() > 10){
                fifo.removeFirst();
                if (crossZero(fifo, context) && (getSlope(wav.get((idx-5)),wav.get((idx-15)),idx-5, idx-15) < -0.02))
                {
                    // cast to int so it rounds
                    if ((int)(lastPos+0.6) != (int)(0.6*(idx-5)))
                    {
                        crossZeroCount++;
                        lastPos = (0.6*(idx-5));
                    }
                }
            }
        }
        System.out.println("Cross Zero: " + crossZeroCount);
        return crossZeroCount;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : getSlope
     * <p/>
     * FUNCTION      : Calculates the steepness of the two positions passed in.
     * <p/>
     * INPUTS        : Double y1 & y2, are the two y values to be tested
     *                  integers x1 & x2 are the two X positions (Distance) between the y values
     * <p/>
     * OUTPUTS       : The steepness as a double.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public Double getSlope(Double y1, Double y2, int x1, int x2) {

        double m;

        //0.6 as that is how many milliseconds for each point
        m = (y1 -y2)/((0.6*x1) - (0.6*x2));

        return m;
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : crossZero
     * <p/>
     * FUNCTION      : Takes an array of 10 values from the y-axis, if the
     *                  first 5 (pos 0-4) are above zero and the latter 5 (5-9)
     *                  are below zero then we have crossed zero
     * <p/>
     * INPUTS        :
     * <p/>
     * OUTPUTS       :
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    private boolean crossZero(LinkedList<Double> fifo, Context context) {

        boolean check = false;

            // 5 values above zero
            if ((fifo.get(0)>0) && (fifo.get(1)>0) && (fifo.get(2)>0) && (fifo.get(3)>0) && (fifo.get(4)>0))
            {
                // 5 values below zero
                if((fifo.get(6)<0) && (fifo.get(7)<0) && (fifo.get(8)<0) && (fifo.get(9)<0))
                {
                    check = true;
                }
            }

        return check;
    }

}
