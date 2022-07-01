# VinLib

Native Win API bindings for low level mouse and keyboard hooks.

## Run
```shell
java -D"java.library.path=.\target\classes" -classpath .\target\classes de.michm.vin.lib.Test

Enter coordinates to which the cursor should move. [x, y]
        e.g.: 120, 80

Enter click <left|right|middle> to click with the
specified mouse button.

Hit "q" to quit.

>
```

## Create CPP header definitions
```shell
javac -h [out_directory] <Mouse.java>
```

## Build from source
**To build the shared library on windows, MinGW is required.**

```shell
g++ -c -I%JAVA_HOME%\include -I%JAVA_HOME%\include\win32 de_michm_vin_lib_Mouse.cpp -o de_michm_vin_lib_Mouse.o
g++ -shared -o vinlib.dll de_michm_vin_lib_Mouse.o
```