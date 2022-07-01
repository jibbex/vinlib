#include "de_michm_vin_lib_Mouse.h"
#include <Windows.h>

JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_move(JNIEnv *env, jobject jobj, jlong x, jlong y) {
    INPUT inputs[1] = {};

    ZeroMemory(inputs, sizeof(inputs));

    inputs[0].type = INPUT_MOUSE;
    inputs[0].mi.dwFlags = MOUSEEVENTF_MOVE;
    inputs[0].mi.dx = (long) x;
    inputs[0].mi.dy = (long) y;

    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}

JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_click(JNIEnv *env, jobject jobj, jint button) {
    INPUT inputs[4] = {};

        ZeroMemory(inputs, sizeof(inputs));

        inputs[2].type = INPUT_MOUSE;
        inputs[3].type = INPUT_MOUSE;

        switch ((int) button) {
            case de_michm_vin_lib_Mouse_LEFT_BUTTON:
                inputs[2].mi.dwFlags = MOUSEEVENTF_LEFTDOWN;
                inputs[3].mi.dwFlags = MOUSEEVENTF_LEFTUP;
                break;
            case de_michm_vin_lib_Mouse_RIGHT_BUTTON:
                inputs[2].mi.dwFlags = MOUSEEVENTF_RIGHTDOWN;
                inputs[3].mi.dwFlags = MOUSEEVENTF_RIGHTUP;
                break;
            case de_michm_vin_lib_Mouse_MIDDLE_BUTTON:
                inputs[2].mi.dwFlags = MOUSEEVENTF_MIDDLEDOWN;
                inputs[3].mi.dwFlags = MOUSEEVENTF_MIDDLEUP;
                break;
            default:
                return;
        }

        SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}