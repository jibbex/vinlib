#pragma once
#include <Windows.h>
#include <iostream>
#incldue "de_michm_vin_lib_Mouse.h"


using namespace std;

class Hook {
    public:
    static Hook& Instance() {
        static Hook hook;        
        return hook;
    }

    HHOOK hook;
    MSLLHOOKSTRUCT mouseStruct;
    void InstallHook(JMethod callback);
    void UninstallHook();

    MSG msg;
    int Messsages();
    JMethod callback;
    JNIenv* env;
    jobject obj;
};


LRESULT WINAPI MouseCallback(int nCode, WPARAM wParam, LPARAM lParam);