#include <map>
#include "lib/winazpluginbase.h"

#pragma region //Do not edit!
#define EXPORTABLE extern "C"
EXPORTABLE __fastcall void initVars(JNIEnv*,jobject(*)(jobject)noexcept,string(*)(jobject)noexcept,bool(*)(std::vector<std::any>*,string&),void(*)(vector<any>*,jobject,string),void(*)(DWORD),vector<Coordinator*>*,vector<Composer*>*,map<string,any*>*);
#pragma endregion

/*
 * Welcome to AZComplex plugin maker!
 * 
 * Below, there is some place to declare whatever you want to inject into the AZComplex.
*/

EXPORTABLE void init(); //Initialization function

EXPORTABLE Coordinator*create_TESTCOORDINATOR(jobject properties,jmethodID getElement);
EXPORTABLE Composer*create_TESTCOMPOSER(jobject properties,jmethodID getElement);
EXPORTABLE void TESTACTION(jobject action);
EXPORTABLE bool TESTWATCHER(vector<any>*params)noexcept;
EXPORTABLE void init_TESTWATCHER(vector<any>*params,jobject watcher);