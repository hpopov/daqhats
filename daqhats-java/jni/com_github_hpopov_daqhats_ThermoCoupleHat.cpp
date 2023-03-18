#include "com_github_hpopov_daqhats_ThermoCoupleHat.h"
#include "stdlib.h"

#include <stdint.h>
#include <daqhats/daqhats.h>

JNIEXPORT jint JNICALL Java_com_github_hpopov_daqhats_ThermoCoupleHat_openConnectionWithMcc134Hat(JNIEnv *env, jclass ThermoCoupleHatClass, jint address)
{
    int result = mcc134_open(address);
    if (result != RESULT_SUCCESS)
    {
        return -1;
    }
    return mcc134_info()->NUM_AI_CHANNELS;
}

JNIEXPORT jboolean JNICALL Java_com_github_hpopov_daqhats_ThermoCoupleHat_setMcc134ThermoCoupleType(JNIEnv *env, jclass ThermoCoupleHatClass, jint address, jint channel, jint tcTypeCode)
{
    int result = mcc134_tc_type_write(address, channel, tcTypeCode);
    if (result != RESULT_SUCCESS)
    {
        return false;
    }
    return true;
}

JNIEXPORT jobject JNICALL Java_com_github_hpopov_daqhats_ThermoCoupleHat_readMcc134ThermoCoupleValue(JNIEnv *env, jclass ThermoCoupleHatClass, jint address, jint channel)
{
    double temperature;
    jstring value = NULL;
    int result = mcc134_t_in_read(address, channel, &temperature);
    if (result != RESULT_SUCCESS)
    {
        return NULL;
    }

    if (temperature == OPEN_TC_VALUE)
    {
        value = env->NewStringUTF("Open");
    }
    else if (temperature == OVERRANGE_TC_VALUE)
    {
        value = env->NewStringUTF("OverRange");
    }
    else if (temperature == COMMON_MODE_TC_VALUE)
    {
        value = env->NewStringUTF("Common Mode");
    }
    else
    {
        value = NULL;
    }

    jclass ThermoCoupleValueClass = env->FindClass("com/github/hpopov/daqhats/ThermoCoupleValue");
    jmethodID thermoCoupleValueConstructor = env->GetMethodID(ThermoCoupleValueClass, "<init>", "(DLjava/lang/String;)V");
    jobject thermoCoupleValue = env->NewObject(ThermoCoupleValueClass, thermoCoupleValueConstructor, temperature, value);
    if (value != NULL)
    {
        env->DeleteLocalRef(value);
    }
    return thermoCoupleValue;
}

JNIEXPORT jboolean JNICALL Java_com_github_hpopov_daqhats_ThermoCoupleHat_closeConnectionWithMcc134Hat(JNIEnv *env, jclass ThermoCoupleHatClass, jint address)
{
    int result = mcc134_close(address);
    if (result != RESULT_SUCCESS)
    {
        return false;
    }
    return true;
}
