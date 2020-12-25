//
// Created by Darell Hendry on 19/12/20.
//

#include <string.h>
#include <jni.h>
#include <ctype.h>

void uppercase(char* str) {
	size_t n = strlen(str);
	for (size_t i = 0; i < n; i++) {
		str[i] = toupper(str[i]);
	}
}

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   app/src/main/java/id/ac/ui/cs/mobileprogramming/razaqadhafin/tktplab/MainActivity.java
 */
JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_darellhendry_cashierpoint_ui_item_AddItemFragment_uppercaseStringFromJNI(
        JNIEnv* env,
        jobject thiz,
        jstring inp) {
    const char* input = (*env)->GetStringUTFChars(env, inp, 0);
    char output[256];

    strcpy(output, input);
    (*env)->ReleaseStringUTFChars(env, inp, input);

    uppercase(output);
    return (*env)->NewStringUTF(env, output);
}