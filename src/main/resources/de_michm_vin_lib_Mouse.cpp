#include "vinlib.h"

JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_move(JNIEnv *env, jobject jobj, jlong x, jlong y) {
    INPUT inputs[1] = {};

    ZeroMemory(inputs, sizeof(inputs));

    inputs[0].type = INPUT_MOUSE;
    inputs[0].mi.dwFlags = MOUSEEVENTF_MOVE;
    inputs[0].mi.dx = (long) x;
    inputs[0].mi.dy = (long) y;

    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}

JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_moveTo(JNIEnv *env, jobject jobj, jlong x, jlong y) {
    INPUT inputs[1] = {};

    ZeroMemory(inputs, sizeof(inputs));

    inputs[0].type = INPUT_MOUSE;
    inputs[0].mi.dwFlags = MOUSEEVENTF_MOVE | MOUSEEVENTF_ABSOLUTE;
    inputs[0].mi.dx = ((long) x) * 0.01;
    inputs[0].mi.dy = ((long) y) * 0.01;

    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}

JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_nativeClick(JNIEnv *env, jobject jobj, jlong button) {
    INPUT inputs[1] = {};

    ZeroMemory(inputs, sizeof(inputs));

    inputs[0].type = INPUT_MOUSE;
    inputs[0].mi.dwFlags = (long) button;

    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}