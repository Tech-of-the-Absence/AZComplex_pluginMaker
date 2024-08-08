:: Here is a compilation script to build the wincore library for your plugin.
:: Just run and let it do everything for you.

@echo off
g++ -c -std=c++23 -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" wincore.cpp -o wincore.o
if %ERRORLEVEL% neq 0 (
	color 04
	echo Error occured
	exit /b %ERRORLEVEL%
)
g++ -shared -o .\Testplugin\wincore.dll .\wincore.o -Llib -l:winazpluginbase.a -lshcore -lGdi32
if %ERRORLEVEL% neq 0 (
	color 04
	echo Error occured
	exit /b %ERRORLEVEL%
)
del wincore.o
color 02
cls
echo Compilation completed!