#include "de_michm_vin_lib_Mouse.h"
#include <Windows.h>
#include <iostream>

/**
 * Moves mouse cursor to the specified coordinates.
 *
 * @param long x-axis offset / position
 * @param long y-axis offset / position
 * @param long flags for DWORD Flags
 */
void mouseMove(long x, long y, long flags) {
    INPUT inputs[1] = {};

    ZeroMemory(inputs, sizeof(inputs));

    inputs[0].type = INPUT_MOUSE;
    inputs[0].mi.dx = x;
    inputs[0].mi.dy = y;
    inputs[0].mi.dwFlags = MOUSEEVENTF_MOVE | flags;

    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}

/**
 * Moves mouse cursor relative to it's current position.
 *
 * @param JNIEnv*
 * @param jobject
 * @param jlong x-axis offset
 * @param jlong y-axis offset
 */
JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_move(JNIEnv *env, jobject jobj, jlong x, jlong y) {
    mouseMove((long) x, (long) y, 0x0000);
}

/**
 * Moves mouse cursor absolute to the screen.
 *
 * @param JNIEnv*
 * @param jobject
 * @param jlong x-axis position
 * @param jlong y-axis position
 */
JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_nativeMoveAbs(JNIEnv *env, jobject jobj, jlong x, jlong y) {
    mouseMove((long) x, (long) y, MOUSEEVENTF_ABSOLUTE);
}

/**
 * Sets the mouse button state.
 *
 * @param JNIEnv*
 * @param jobject
 * @param jlong button
 */
JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_nativeClick(JNIEnv *env, jobject jobj, jlong button) {
    INPUT inputs[1] = {};

    ZeroMemory(inputs, sizeof(inputs));

    inputs[0].type = INPUT_MOUSE;
    inputs[0].mi.dwFlags = (long) button;

    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}

/**
 * Calls GetCursorPos and wraps it's result into a Point object and
 * returns it to the Java context.
 *
 * @param JNIEnv*
 * @param jobject
 * @return Point object
 */
JNIEXPORT jobject JNICALL Java_de_michm_vin_lib_Mouse_nativeGetCursorPos(JNIEnv *env, jobject obj) {
    LPPOINT pt;
    jlong x = -1;
    jlong y = -1;
    jclass pointClass = env->FindClass("de/michm/vin/lib/Point");
    jmethodID jconstructor = env->GetMethodID(pointClass, "<init>", "(JJ)V");

    if (GetCursorPos(pt)) {
        x = (jlong) pt->x;
        y = (jlong) pt->y;
    }

    return env->NewObject(pointClass, jconstructor, x, y);
}