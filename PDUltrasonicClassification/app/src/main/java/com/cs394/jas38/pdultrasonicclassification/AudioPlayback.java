package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

/**
 * ---------------------------------------------------------------
 * <p/>
 * CALL NAME     : AudioPlayback
 * <p/>
 * FUNCTION      : To play the audio file aloud.
 * <p/>
 * AMENDMENTS    : Created by, James Slater on 26/03/15.
 * <p/>
 * --------------------------------------------------------------
 */
class AudioPlayback {

    /**
     * ---------------------------------------------------------------
     * <p/>
     * CALL NAME     : AudioPlayback
     * <p/>
     * FUNCTION      : Passed in the current screen context and
     *                  the audio file.
     * <p/>
     * INPUTS        : Context context, File fullFilePath
     * <p/>
     * OUTPUTS       : Audio playing
     * <p/>
     * AMENDMENTS    :  Created by, James Slater on 26/03/15.
     * <p/>
     * --------------------------------------------------------------
     */
    public AudioPlayback(Context context, File fullFilePath){
        try{
            MediaPlayer mp = MediaPlayer.create(context, Uri.fromFile(fullFilePath));
            mp.start();
            Toast.makeText(context, "Playing Audio", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(context, "Error: No file found", Toast.LENGTH_SHORT).show();
        }
    }
}
