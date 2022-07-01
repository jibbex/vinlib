/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class de_michm_vin_lib_Mouse */

#ifndef _Included_de_michm_vin_lib_Mouse
#define _Included_de_michm_vin_lib_Mouse
#ifdef __cplusplus
extern "C" {
#endif
#undef de_michm_vin_lib_Mouse_CLICK_DURATION
#define de_michm_vin_lib_Mouse_CLICK_DURATION 300L
#undef de_michm_vin_lib_Mouse_LEFT_DOWN
#define de_michm_vin_lib_Mouse_LEFT_DOWN 2i64
#undef de_michm_vin_lib_Mouse_LEFT_UP
#define de_michm_vin_lib_Mouse_LEFT_UP 4i64
#undef de_michm_vin_lib_Mouse_RIGHT_DOWN
#define de_michm_vin_lib_Mouse_RIGHT_DOWN 8i64
#undef de_michm_vin_lib_Mouse_RIGHT_UP
#define de_michm_vin_lib_Mouse_RIGHT_UP 16i64
#undef de_michm_vin_lib_Mouse_MIDDLE_DOWN
#define de_michm_vin_lib_Mouse_MIDDLE_DOWN 32i64
#undef de_michm_vin_lib_Mouse_MIDDLE_UP
#define de_michm_vin_lib_Mouse_MIDDLE_UP 64i64
/*
 * Class:     de_michm_vin_lib_Mouse
 * Method:    move
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_move
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     de_michm_vin_lib_Mouse
 * Method:    moveTo
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_moveTo
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     de_michm_vin_lib_Mouse
 * Method:    nativeClick
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_nativeClick
  (JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif
