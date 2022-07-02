#include "hook.h"

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

void Hook::InstallHook() {
    if (!(Hook::Instance().hook = SetWindowsHookEx(WH_MOUSE_LL, MouseCallback, NULL, 0))) {
        MessageBox(NULL, L"Could not Install HOOK.", L"SetWindowsHookEx", MB_OK | MB_ICONWARNING);
    }
}

void Hook::UninstallHook() {
    UnhookWindowsHookEx(Hook::Instance().hook);
}

LRESULT WINAPI MouseCallback(int nCode, WPARAM wParam, LPARAM lParam) {
    MSLLHOOKSTRUCT* pMouseStruct = (MSLLHOOKSTRUCT*)lParam;

    if (nCode >= 0) {
        if (pMouseStruct != NULL) {
            cout << "Mouse: x =  " << pMouseStruct->pt.x << "  y =  " << pMouseStruct->pt.y << endl;
        }

        switch (wParam) {
            case WM_LBUTTONUP:
                printf_s("LEFT CLICK UP\n");
                break;
            case WM_LBUTTONDOWN:
                printf_s("LEFT CLICK DOWN\n");
                 break;
        }
    }

    return CallNextHookEx(Hook::Instance().hook, nCode, wParam, lParam);
}
