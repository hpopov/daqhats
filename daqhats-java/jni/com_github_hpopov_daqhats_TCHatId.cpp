#include "com_github_hpopov_daqhats_TCHatId.h"
#include "stdlib.h"

#include <stdint.h>
#include <daqhats/daqhats.h>

JNIEXPORT jintArray JNICALL Java_com_github_hpopov_daqhats_TCHatId_findConnectedMcc134HatsAddresses(JNIEnv *env, jclass TCHatIdClass)
{
    struct HatInfo *hats = NULL;
    int hat_count = 0;
    jintArray addresses;

    hat_count = hat_list(HAT_ID_MCC_134, NULL);
    addresses = env->NewIntArray(hat_count);

    // Verify there are HAT devices connected that are of the requested type.
    if (hat_count > 0)
    {
        // Allocate memory for the list of HAT devices.
        hats = new HatInfo[hat_count];

        // Get the list of HAT devices.
        hat_list(HAT_ID_MCC_134, hats);

        jint *arr = new jint[hat_count];
        for (int i = 0; i < hat_count; i++)
        {
            arr[i] = hats[i].address;
        }
        env->SetIntArrayRegion(addresses, 0, hat_count, arr);
        delete arr;
    }

    delete hats;
    return addresses;
}