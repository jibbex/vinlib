#pragma once

using namespace std;

class Hook {
    public:
    static Hook& Instance() {
        static Hook hook;        
        return hook;
    }

    HHOOK hook;
    MSLLHOOKSTRUCT mouseStruct;
    void InstallHook(JNIEnv*, jobject, jmethodID);
    void UninstallHook();

    MSG msg;
    int Messsages();
    jmethodID callback;
    JNIEnv* env;
    jobject obj;
};


LRESULT WINAPI MouseCallback(int nCode, WPARAM wParam, LPARAM lParam);