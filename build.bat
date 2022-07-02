javac -h .\src\main\native\win32\resources .\src\main\java\de\michm\vin\lib\Mouse.java
g++ -c -I%JAVA_HOME%\include -I%JAVA_HOME%\include\win32 .\src\main\native\win32\de_michm_vin_lib_Mouse.cpp -o .\src\main\resources\de_michm_vin_lib_Mouse.o
g++ -shared -o .\src\main\resources\vinlib.dll .\src\main\resources\de_michm_vin_lib_Mouse.o