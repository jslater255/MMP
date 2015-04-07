package com.cs394.jas38.pdultrasonicclassification;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : Broker
 * <p/>
 * FUNCTION      : This is the broker between the java side of the application
 *                  and the native C/C++ code.
 * <p/>
 * AMENDMENTS    :  Created by, James Slater
 * <p/>
 * --------------------------------------------------------------
 */
class Broker {

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : Broker
     * <p/>
     * FUNCTION      : Constructor
     * <p/>
     * INPUTS        : Null
     * <p/>
     * OUTPUTS       : The instance of this class
     * <p/>
     * AMENDMENTS    : Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public Broker(){
    }

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : CallNativeOpenFile
     * <p/>
     * FUNCTION      : It will load the audio file from the path that is passed in.
     *                  It converts the audio file to a double array.
     * <p/>
     * INPUTS        : String filePath, is the full path to find the audio file you want to be opened.
     *                  Example --
     *                  "/storage/emulated/0/Android/data/com.cs394.jas38.pdultrasonicclassification/files/test.wav"
     * <p/>
     * OUTPUTS       : Double array, is the audio file as numbers. It creates the array
     *                  from using the sndfile lib, all the files ate in the jni folder
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public native double[] CallNativeOpenFile(String filePath);

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : CallNativeSampleRate
     * <p/>
     * FUNCTION      : It will load the audio file from the path that is passed in.
     *                  It finds the sample rate of the audio file and return the number.
     * <p/>
     * INPUTS        : String filePath, is the full path to find the audio file you want to be opened.
     *                  Example --
     *                  "/storage/emulated/0/Android/data/com.cs394.jas38.pdultrasonicclassification/files/test.wav"
     * <p/>
     * OUTPUTS       : Integer of the sample rate of the audio file.
     * <p/>
     * AMENDMENTS    :  Created by, James Slater
     * <p/>
     * --------------------------------------------------------------
     */
    public native int CallNativeSampleRate(String filePath);

    /**
     * Loads the already made native library called 'Broker'.
     * More information on this within ../../jni/Android.mk
     */
    static
    {
        System.loadLibrary("Broker");
    }


}
