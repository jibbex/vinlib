#include "vinlib.h"
#include <jni.h>

using namespace std;

int Hook::Messsages() {
    while (Hook::Instance().msg.message != WM_QUIT) {
        if (PeekMessage(&Hook::Instance().msg, NULL, 0, 0, PM_REMOVE)) {
            TranslateMessage(&Hook::Instance().msg);
            DispatchMessage(&Hook::Instance().msg);
        }
        Sleep(1);
    }
    Hook::Instance().UninstallHook();
    return (int)Hook::Instance().msg.wParam;
}

void Hook::InstallHook(JNIEnv* env, jobject jobj, jmethodID callback) {
    if (!(Hook::Instance().hook = SetWindowsHookEx(WH_MOUSE_LL, MouseCallback, NULL, 0))) {
        MessageBox(NULL, LPCSTR("Could not Install HOOK."), LPCSTR("SetWindowsHookEx"), MB_OK | MB_ICONWARNING);
    } else {
        Hook::Instance().env = env;
        Hook::Instance().obj = jobj;
        Hook::Instance().callback = callback;
    }
}

void Hook::UninstallHook() {
    UnhookWindowsHookEx(Hook::Instance().hook);
}

LRESULT WINAPI MouseCallback(int nCode, WPARAM wParam, LPARAM lParam) {
    long button = -1;
    MSLLHOOKSTRUCT* pMouseStruct = (MSLLHOOKSTRUCT*)lParam;

    if (nCode >= 0) {
        if (pMouseStruct != NULL) {
            cout << "Mouse: x =  " << pMouseStruct->pt.x << "  y =  " << pMouseStruct->pt.y << endl;
        }

        switch (wParam) {
            case WM_LBUTTONUP:
                button = 0x0002;
                break;
            case WM_LBUTTONDOWN:
                button = 0x0008;
                break;
        }

        JNIEnv* env = Hook::Instance().env;

        (*env).CallVoidMethod(
            Hook::Instance().obj,
            Hook::Instance().callback,
            (jlong) pMouseStruct->pt.x,
            (jlong) pMouseStruct->pt.y,
            (jlong) button
        );
    }

    return CallNextHookEx(Hook::Instance().hook, nCode, wParam, lParam);
}
