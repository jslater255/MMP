package com.cs394.jas38.pdultrasonicclassification;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Slaters on 26/03/15.
 */
public class AudioPlayback {

    public AudioPlayback(Context context, File fullFilePath){
        try{
            MediaPlayer mp = MediaPlayer.create(context, Uri.fromFile(fullFilePath));
            mp.start();
        }catch(Exception e){
            Toast.makeText(context, "Error: No file found", Toast.LENGTH_SHORT).show();
        }
    }
}
