#pragma once
#include <Windows.h>
#include <iostream>


using namespace std;

class Hook {
    public:
    static Hook& Instance() {
        static Hook myHook;
        return myHook;
    }

    HHOOK hook;
    MSLLHOOKSTRUCT mouseStruct;
    void InstallHook();
    void UninstallHook();

    MSG msg;
    int Messsages();
};


LRESULT WINAPI MouseCallback(int nCode, WPARAM wParam, LPARAM lParam);