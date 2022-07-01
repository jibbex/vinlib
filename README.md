# VinLib

Native Win API bindings for low level mouse and keyboard hooks.

## Run 

## Build from source
**To build the shared library on windows, MinGW is required.**

```shell
g++ -c -I%JAVA_HOME%\include -I%JAVA_HOME%\include\win32 de_michm_vin_lib_Mouse.cpp -o de_michm_vin_lib_Mouse.o
g++ -shared -o vinlib.dll de_michm_vin_lib_Mouse.o
```