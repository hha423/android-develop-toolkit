#include <stdio.h>
#include <stdlib.h>
#include "jni_helper.h"

static char *key="12514526234155261546254";

/*
 * Class:     com_example_android_jni_JNIHelper
 * Method:    helloWorld
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_android_jni_JNIHelper_helloWorld
  (JNIEnv *env, jclass clazz) {
  LOGV("Hello, jni helper");
 }

/*
 * Class:     com_example_android_jni_JNIHelper
 * Method:    printString
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_example_android_jni_JNIHelper_printString
  (JNIEnv *env, jclass clazz, jstring jmsg) {
  	const char *str=(*env)->GetStringUTFChars(env, jmsg, NULL);;
  	if(str==NULL) {
  		return;
  	}  
  	LOGV("params:%s",str);
  	(*env)->ReleaseStringUTFChars(env, jmsg, str);    
 }

 
/*
 * Class:     com_example_android_jni_JNIHelper
 * Method:    init
 * Signature: (Landroid/content/Context;)V
 */
JNIEXPORT void JNICALL Java_com_example_android_jni_JNIHelper_init
  (JNIEnv *env, jclass clazz, jobject jobj) {
	//jclass ctxclazz = (*env)->FindClass(env, "android/content/Context");
  	jmethodID mid=(*env)->GetMethodID(env, (*env)->GetObjectClass(env, jobj),"getApplicationInfo","()Landroid/content/pm/ApplicationInfo;");
	if (mid == NULL) {
		LOGV("get jmethodID error");
		return; 
	}
	jobject obj=(*env)->CallObjectMethod(env, jobj, mid);
	
	jfieldID fid=(*env)->GetFieldID(env, (*env)->GetObjectClass(env, obj), "packageName", "Ljava/lang/String;");
	if(fid == NULL) {
		LOGV("get jfeildID error");
		return; 
	}
	
	jstring jstr = (*env)->GetObjectField(env, obj, fid);
	const char *str = (*env)->GetStringUTFChars(env, jstr, NULL); 
	if (str == NULL) { 
		return;
	}
	
	LOGV("field:%s",str);
	(*env)->ReleaseStringUTFChars(env, jstr, str); 	
}

/*
 * Class:     com_example_android_jni_JNIHelper
 * Method:    encode
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_example_android_jni_JNIHelper_encode
  (JNIEnv *env, jclass clazz, jstring jstr){
	jmethodID mid=NULL;
	jstring outputjstr=NULL;
	jstring tempjstr=NULL;
	
	mid=(*env)->GetStaticMethodID(env, clazz, "encryptMD5","(Ljava/lang/String;)Ljava/lang/String;");
	tempjstr=jstr;
	int i=0;
	for(i=0;i<10;i++) {
		outputjstr=(*env)->CallStaticObjectMethod(env, clazz, mid, tempjstr);
		tempjstr=combine(env, outputjstr, key);
	}

	const char *outputstr= (*env)->GetStringUTFChars(env, outputjstr, NULL); 
	LOGV("output:%s",outputstr);
	(*env)->ReleaseStringUTFChars(env, outputjstr, outputstr); 
	
	return outputjstr;
 }
 
 jstring combine(const JNIEnv *env, const jstring jstr, const char *k) {
	const char *buffer=(char *)malloc(64);
	const char *str = (char *)(*env)->GetStringUTFChars(env, jstr, NULL); 
	
	strcpy(buffer, str);
    strcat(buffer, k); 
	(*env)->ReleaseStringUTFChars(env, jstr, str); 
	
	return (*env)->NewStringUTF(env, buffer);
 }


 