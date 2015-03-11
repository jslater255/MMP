#include "sndfile.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <jni.h>
#include <sndfile.h>
#include <stdarg.h>
#include <errno.h>
#include "com_cs394_jas38_pdultrasonicclassification_Broker.h"



/*
 * Class:     com_cs394_jas38_pdultrasonicclassification_Broker
 * Method:    callNative
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_cs394_jas38_pdultrasonicclassification_Broker_callNative
  (JNIEnv *env, jobject obj)
  {
    (*env)->NewStringUTF(env,"New String");
  }

/*
 * Class:     com_cs394_jas38_pdultrasonicclassification_Broker
 * Method:    callIntNative
 * Signature: ()Ljava/lang/Integer;
 */
JNIEXPORT jint JNICALL Java_com_cs394_jas38_pdultrasonicclassification_Broker_callIntNative
  (JNIEnv *env, jobject obj)
  {
    int test = 8;
    return test;
  }

/*
 * Class:     com_cs394_jas38_pdultrasonicclassification_Broker
 * Method:    CallNativeOpenFile
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_cs394_jas38_pdultrasonicclassification_Broker_CallNativeOpenFile
  (JNIEnv *env, jobject obj, jstring filePath){

    //SNDFILE* 	sf_open		(const char *path, int mode, SF_INFO *sfinfo) ;
    char buff[100];
    memset(buff,0,sizeof(buff));
    char buf[128];
    const char *str = (*env)->GetStringUTFChars(env, filePath, 0);
    //"/storage/emulated/0/Android/data/com.cs394.jas38.pdultrasonicclassification/files/test.csv"
    //FILE* file = fopen(str,"r");

    SNDFILE *sf;
    SF_INFO info;

    /* Open the WAV file. */
    info.format = 0;
    sf = sf_open(str,SFM_READ,&info);
    if (sf == NULL)
    {
        (*env)->NewStringUTF(env, "Failed");
    }
    (*env)->NewStringUTF(env, "Not Failed");


  }
