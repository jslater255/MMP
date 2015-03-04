package com.cs394.jas38.pdultrasonicclassification;


import java.util.ArrayList;

public class SpikeFeatureStruct {

    private int crossZeroPos;
    private int peakPos;
    private int peakStart;
    private int spikeLength;

    public SpikeFeatureStruct(int crossZeroPos, ArrayList<Double> wav){
        this.crossZeroPos = crossZeroPos;

        workoutPeakPos(wav);
        //workoutPeakStart(wav);
    }

    public int getCrossZeroPos() {
        return crossZeroPos;
    }

    public int getPeakPos() {
        return peakPos;
    }

    public int getPeakStart() {
        return peakStart;
    }

    public int getSpikeLength() {
        return spikeLength;
    }

    public void setSpikeLength(ArrayList<Double> wav) {
        //TODO
        int idx = crossZeroPos;
        while (wav.get(idx) < 0)
        {
            idx++;
        }
        System.out.println("Spike end: " + idx);
        spikeLength = idx - peakStart;
    }

    private void workoutPeakPos(ArrayList<Double> wav){

        // As we work backwards to find the peak from where it has crossed zero
        for(int i=crossZeroPos; i > 5; i--)
        {
            //if((wav.get(i-2) > wav.get(i-3))&&(wav.get(i-1) >= wav.get(i-2))&&(wav.get(i) >= wav.get(i-1)) && (wav.get(i) >= wav.get(i+1)) && (wav.get(i+1) >= wav.get(i+2)) && (wav.get(i+2) > wav.get(i+3)))
            if((wav.get(i-1) >= wav.get(i-2))&&(wav.get(i) >= wav.get(i-1)) && (wav.get(i) >= wav.get(i+1)) && (wav.get(i+1) >= wav.get(i+2)))
            {
                peakPos = i;
                //System.out.println("Peak at: " + peakPos + " and cross zero at: " + crossZeroPos);
                workoutPeakStart(wav);
                setSpikeLength(wav);
                break;
            }
        }
    }

    private void workoutPeakStart(ArrayList<Double> wav){

        int idx = peakPos;

        while ((wav.get(idx) > wav.get(idx-5)) && (idx > 0))
        {
            if (wav.get(idx) < 0)
            {
                break;
            }
            idx--;
        }
        peakStart = idx;
    }


}
