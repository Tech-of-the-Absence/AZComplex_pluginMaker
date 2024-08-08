:: A script to make your main plugin file (core.jar).
:: Run it and

@echo off
if not exist "net/azplugin" mkdir "net/azplugin"
if not exist "net/out" mkdir "net/out"
cd net
javac -d out -sourcepath azplugin azplugin/*.java -cp ../lib/az_plugin_library.jar
if %ERRORLEVEL% neq 0 (
	color 04
	echo Error occured
	exit /b %ERRORLEVEL%
)
xcopy /s /q /Y ..\lib\azpluginlib_exported\* out\com\azexternal\
if %ERRORLEVEL% neq 0 (
	color 04
	echo Error occured
	exit /b %ERRORLEVEL%
)
cd out
jar cf ../../Testplugin/core.jar *
if %ERRORLEVEL% neq 0 (
	color 04
	echo Error occured
	exit /b %ERRORLEVEL%
)
color 02
cls
echo Compilation completed!