#include "com_github_hpopov_daqhats_test_JavaServer.h"
#include "string.h"
#include "stdlib.h"

JNIEXPORT jobject JNICALL JNICALL Java_com_github_hpopov_daqhats_test_JavaServer_calculatevolume_1in_1cplusplus(
    JNIEnv *env, jobject obj, jobject input)
{
    printf("C -> Calculate Volume called\n"); // Get the Input data
    jclass requestDataClass = env->GetObjectClass(input);
    jmethodID methodRequestDataGetRadius = env->GetMethodID(requestDataClass, "getRadius", "()I");
    if (methodRequestDataGetRadius == NULL)
    {
        printf("getRadius method does not exists, so returning \n");
        return NULL;
    }
    jmethodID methodRequestDataGetShape = env->GetMethodID(requestDataClass, "getShape", "()Ljava/lang/String;");
    if (methodRequestDataGetShape == NULL)
    {
        printf("getShape method does not exists, so returning \n");
        return NULL;
    }
    // jint radius = (jint)env->CallObjectMethod(input, methodRequestDataGetRadius);
    jint radius = env->CallIntMethod(input, methodRequestDataGetRadius);
    printf("Radius is %d\n", radius);
    jstring shape = (jstring)env->CallObjectMethod(input, methodRequestDataGetShape); // Convert jstring shape into C string
    jboolean iscopy;
    const char *cshape = env->GetStringUTFChars(shape, &iscopy);
    printf("Shape is %s\n", cshape); // Create return object
    jclass responseDataClass =
        env->FindClass("com/github/hpopov/daqhats/test/ResponseData");
    jobject responseData = env->AllocObject(responseDataClass); // Get the method Ids to be set
    jmethodID methodSetVolume = env->GetMethodID(responseDataClass, "setVolume", "(D)V");
    if (methodSetVolume == NULL)
    {
        printf("setVolume method does not exists, so returning \n");
        return NULL;
    }
    jmethodID methodSetStatus = env->GetMethodID(responseDataClass, "setStatus", "(Ljava/lang/String;)V");
    if (methodSetStatus == NULL)
    {
        printf("setStatus method does not exists, so returning \n");
        return NULL;
    } // Set the values in Return object
    double volume = (4 * 22 * radius * radius * radius) / (3 * 7);
    printf("Volume is %f\n", volume);
    env->CallObjectMethod(responseData, methodSetVolume, volume);
    const char *cstatus = "OK";
    env->CallObjectMethod(responseData, methodSetStatus, env->NewStringUTF(cstatus));
    return responseData;
}