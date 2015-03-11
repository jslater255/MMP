package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.widget.Toast;

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
public class Broker {

    public Broker(){

    }

    public void brokerCallNative(Context context){
        Toast.makeText(context, callNative(), Toast.LENGTH_SHORT).show();
    }

    public void brokerCallIntNative(Context context){
        Toast.makeText(context, "int " + callIntNative(), Toast.LENGTH_SHORT).show();
    }

    public void brokerCallNativeOpenFile(Context context,String filePath){
        Toast.makeText(context, "File? == " + CallNativeOpenFile(filePath), Toast.LENGTH_SHORT).show();
    }

    public native String callNative();
    public native int callIntNative();
    public native String CallNativeOpenFile(String filePath);
    static
    {
        System.loadLibrary("Broker");
    }


}
