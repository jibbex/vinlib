/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class de_michm_vin_lib_Mouse */

#ifndef _Included_de_michm_vin_lib_Mouse
#define _Included_de_michm_vin_lib_Mouse
#ifdef __cplusplus
extern "C" {
#endif
#undef de_michm_vin_lib_Mouse_LEFT_BUTTON
#define de_michm_vin_lib_Mouse_LEFT_BUTTON 0L
#undef de_michm_vin_lib_Mouse_RIGHT_BUTTON
#define de_michm_vin_lib_Mouse_RIGHT_BUTTON 1L
#undef de_michm_vin_lib_Mouse_MIDDLE_BUTTON
#define de_michm_vin_lib_Mouse_MIDDLE_BUTTON 2L
/*
 * Class:     de_michm_vin_lib_Mouse
 * Method:    move
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_move
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     de_michm_vin_lib_Mouse
 * Method:    click
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_click
  (JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
