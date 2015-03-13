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
JNIEXPORT jdoubleArray JNICALL Java_com_cs394_jas38_pdultrasonicclassification_Broker_CallNativeOpenFile
  (JNIEnv *env, jobject obj, jstring filePath){

    const char *str = (*env)->GetStringUTFChars(env, filePath, 0);
    //"/storage/emulated/0/Android/data/com.cs394.jas38.pdultrasonicclassification/files/test.csv"
    SNDFILE *sf;
    SF_INFO info;

    /* Open the WAV file. */
    info.format = 0;
    sf = sf_open(str,SFM_READ,&info);

    char string[15];

    int num_items, i, j, c;
    num_items = info.channels * info.frames;
    c = info.channels;

    double *buf_double;
    buf_double = (double *) malloc(num_items*sizeof(double));
    int num = sf_read_double(sf,buf_double,num_items);
    sf_close(sf);

    sprintf(string, "%f", buf_double[0]);

    jdoubleArray result;
    result = (*env)->NewDoubleArray(env, num_items);

    (*env)->SetDoubleArrayRegion(env, result, 0, num_items, buf_double);

    return result;

  }
