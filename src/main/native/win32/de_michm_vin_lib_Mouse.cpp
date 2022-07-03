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

JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_moveAbs(JNIEnv *env, jobject jobj, jlong x, jlong y) {
    INPUT inputs[1] = {};

    ZeroMemory(inputs, sizeof(inputs));

    inputs[0].type = INPUT_MOUSE;
    inputs[0].mi.dwFlags = MOUSEEVENTF_MOVE | MOUSEEVENTF_ABSOLUTE;
    inputs[0].mi.dx = ((long) x) * 0.01;
    inputs[0].mi.dy = ((long) y) * 0.01;

    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}

JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_moveTo(JNIEnv *env, jobject obj, jlong jx, jlong jy, jfloat speed) {
    const long MOVE_POINTS = 10 * ((float) speed);
    long x = (long) jx;
    long y = (long) jy;
    LPPOINT pt;

    if (GetCursorPos(pt)) {
        INPUT inputs[1] = {};

        ZeroMemory(inputs, sizeof(inputs));

        inputs[0].type = INPUT_MOUSE;
        inputs[0].mi.dwFlags = MOUSEEVENTF_MOVE;
        inputs[0].mi.dx = pt->x;
        inputs[0].mi.dy = pt->y;

        while(inputs[0].mi.dx != x && inputs[0].mi.dy != y) {
            long x_width = inputs[0].mi.dx - MOVE_POINTS;
            long y_width = inputs[0].mi.dy - MOVE_POINTS;

            if (x_width > MOVE_POINTS && inputs[0].mi.dx < x) {
                inputs[0].mi.dx += MOVE_POINTS;
            } else if (x_width > MOVE_POINTS && inputs[0].mi.dx > x) {
                inputs[0].mi.dx -= MOVE_POINTS;
            } else if (x_width < MOVE_POINTS && inputs[0].mi.dx < x) {
                inputs[0].mi.dx += x_width;
            } else if (x_width < MOVE_POINTS && inputs[0].mi.dx > x) {
                inputs[0].mi.dx -= x_width;
            }

            if (y_width > MOVE_POINTS && inputs[0].mi.dy < y) {
                inputs[0].mi.dy += MOVE_POINTS;
            } else if (y_width > MOVE_POINTS && inputs[0].mi.dy > y) {
                inputs[0].mi.dy -= MOVE_POINTS;
            } else if (y_width < MOVE_POINTS && inputs[0].mi.dy < y) {
                inputs[0].mi.dy += x_width;
            } else if (y_width < MOVE_POINTS && inputs[0].mi.dy > y) {
                inputs[0].mi.dy -= x_width;
            }

            SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
            Sleep(1);
        }
    }
}

JNIEXPORT void JNICALL Java_de_michm_vin_lib_Mouse_nativeClick(JNIEnv *env, jobject jobj, jlong button) {
    INPUT inputs[1] = {};

    ZeroMemory(inputs, sizeof(inputs));

    inputs[0].type = INPUT_MOUSE;
    inputs[0].mi.dwFlags = (long) button;

    SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
}