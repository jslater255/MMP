package com.cs394.jas38.pdultrasonicclassification;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Slater on 21/02/15.
 */
public class StatCalculator {

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
        /**for(double kdx : wav_run_avg)
         {
         System.out.print(kdx + ",");
         }**/
    }


    public double avg(ArrayList<Double> wav){
        double avg_out = 0;

        for(double idx : wav){
            avg_out += idx;
        }
        avg_out = avg_out/wav.size();
        System.out.println("WAV Size " + wav.size());
        System.out.println("AVG= " + avg_out);
        return avg_out;
    }

    public double stand_dev(double avg_int, ArrayList<Double> wav){

        double stan_dev_int = 0.0;
        double sum_x_mean = 0.0;

        for (double idx : wav){
            sum_x_mean += Math.pow((idx - avg_int),2);
        }

        stan_dev_int = sum_x_mean/ (wav.size() -1);

        return stan_dev_int;

    }

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

    public void varience(double avg, ArrayList<Double> wav){
        double temp = 0;
        for(double idx : wav){
            temp += avg - idx;
            temp = Math.pow(temp,2);
        }
        System.out.println("Variance: " + temp);
    }

}
