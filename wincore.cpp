#include <windows.h>
#include "wincore.h"

#pragma region //Do not edit!
#define EXPORTABLE extern "C"
EXPORTABLE __fastcall void initVars(JNIEnv*,jobject(*getWatcher)(jobject)noexcept,int(*getTypeOrd)(jobject)noexcept,bool(*callWatcher)(std::vector<std::any>*,int),void(*initWatcher)(vector<any>*,jobject,int),void(*delay)(DWORD),vector<Coordinator*>*,vector<Composer*>*,map<string,any*>*);
#pragma endregion



/*
 * Welcome to AZ-Plugin maker!
 * 
 * Below, you can see the realisations of some AZComplex stuff.
 * Follow these examples and make your own plugin dll for Windows!
 * Note, that each method here must be listed in the `methods.list` file.
 * Add `EXPORTABLE` before each method you intend to transmit to the AZComplex system.
 * 
 * `void init()` function is launched on plugin loading.
 * You can use it to initialize some necessary objects, create temp files etc...
 * 
 * Create your commands and providers right here.
 * I ask you to minimize the difference between effects of these methods and java-coded analogues.
*/

int realW,realH,screenW,screenH;
jfieldID field_coords,field_reversed;

EXPORTABLE void init(){
    //Objects from java will tell you that your screen's width is `screenW`.
    jobject screenSize=env->GetStaticObjectField(env->FindClass("com/azcomplex/Root"),env->GetStaticFieldID(env->FindClass("com/azcomplex/Root"),"SCREEN_SIZE","Ljava/awt/Dimension;"));
	screenW=env->GetIntField(screenSize,env->GetFieldID(env->GetObjectClass(screenSize),"width","I"));
	screenH=env->GetIntField(screenSize,env->GetFieldID(env->GetObjectClass(screenSize),"height","I"));
    //It can be not equal to `realW`, retrieved from the native code.
    realW=GetSystemMetrics(SM_CXSCREEN);
	realH=GetSystemMetrics(SM_CYSCREEN);
    field_coords=env->GetFieldID(env->FindClass("com/azexternal/ActionCommand"),"coords","I");
    field_reversed=env->GetFieldID(env->FindClass("com/azexternal/WatcherCommand"),"reversed","Z");
    // ...
}

//Coordinators and composers

EXPORTABLE Coordinator*create_TESTCOORDINATOR(jobject properties,jmethodID getElement){
    class V:public Coordinator{
        Point getPoint(){
            return Point(0,0);//Returning just a fixed point at (0,0)
        }
        void init(jobject command){
            //Nothing on initialization
        }
    };
    return new V();
}

EXPORTABLE Composer*create_TESTCOMPOSER(jobject properties,jmethodID getElement){
    class V:public Composer{
        const jchar*str=(const jchar*)L"Test string";
        const jchar*getString(){
            length=11; //Setting the length of the string that will be returned
            return str; //Returning a fixed string "Test string".
        }
        void init(jobject command){
            //Nothing on initialization
        }
    };
    return new V();
}

#pragma region //Some useful stuff
/**
 * @brief Provides watcher operating variables.
 * Creates parameter set for watcher - `vector<any>d;`. Transmit it to the callWatcher method to call the watcher.
 * Creates `reversed` variable representing the `reversed` flag of a watcher.
*/
#define deployWatcher() jobject watcher=getWatcher(action);\
	int wType=getTypeOrd(watcher);\
	vector<any>d;\
	initWatcher(&d,watcher,wType);\
	bool reversed=watcher==NULL?false:env->GetBooleanField(watcher,field_reversed);
/**
 * @brief Provides coordinates operating variables.
 * Creates `Coordinator* coords` variable - a pointer to the corresponding coordinator.
 * Creates `Point p` - a buffer to write the coordinates retrieved.
*/
#define extractCoords() Coordinator*coords=(*coordinators)[env->GetIntField(action,field_coords)];\
	bool dynamic=env->GetBooleanField(action,env->GetFieldID(env->GetObjectClass(action),"dynamicCoords","Z"));\
	coords->init(action);\
	Point p;
/**
 * @brief Updates coordinates in the `Point p`.
*/
#define updateCoords() p=coords->getPoint();
#pragma endregion

//Actions

EXPORTABLE void TESTACTION(jobject action){
    extractCoords();
	deployWatcher();
    INPUT input={0};
    input.type=INPUT_MOUSE;
    input.mi.dwFlags=MOUSEEVENTF_ABSOLUTE|MOUSEEVENTF_MOVE;
    input.mi.dx=p.x;
    input.mi.dx=p.x;
    bool flag=true;
    do{
        // <a place where nothing should be written in order to avoid problems>
		if(flag||dynamic){ //First coordinates update.
			updateCoords();
			if(p.x==-1)continue;
			input.mi.dx=p.x;
			input.mi.dy=p.y;
			flag=false;
		}
		SendInput(1,&input,sizeof(INPUT));
	}while(reversed^callWatcher(&d,wType));
}

#pragma region //Some useful stuff
struct CoordsKit{
	int x,y;
	bool dynamic;
	Coordinator*coords;
	CoordsKit(int x,int y,bool dynamic,Coordinator*coords){
		this->x=x;this->y=y;
		this->dynamic=dynamic;
		this->coords=coords;
	}
};
#undef updateCoords
#define updateCoords(){\
	Point p=cc.coords->getPoint();\
	cc.x=p.x;cc.y=p.y;\
}
#define updateCoordsInPixels(){\
	Point p=cc.coords->getPoint();\
	cc.x=p.x*realW/65535;cc.y=p.y*realH/65535;\
}
#pragma endregion

//Watchers

EXPORTABLE bool TESTWATCHER(vector<any>*params)noexcept{
	CoordsKit cc=any_cast<CoordsKit>(params->at(1));
	bool b=cc.x>cc.y;
	if(cc.dynamic)updateCoordsInPixels(); //Coords updating
	return b; //Note: parameters are indexed from 1. `params->at(0)` is watcher's delay.
}

#pragma region //Some useful stuff
#define addCoords() Coordinator*coords=(*coordinators)[env->GetIntField(watcher,field_coords)];\
	bool dynamic=env->GetBooleanField(watcher,env->GetFieldID(env->GetObjectClass(watcher),"dynamicCoords","Z"));\
	coords->init(watcher);\
	Point p=coords->getPoint();\
	CoordsKit cc(p.x,p.y,dynamic,coords);\
	params->push_back(cc);
#define addArea() jclass jrectClass=env->FindClass("java/awt/Rectangle");\
	jobject jrect=env->CallObjectMethod(properties,getProperty,env->NewStringUTF("area"));\
	Rect r(env->GetIntField(jrect,env->GetFieldID(jrectClass,"x","I"))*realW/screenW,env->GetIntField(jrect,env->GetFieldID(jrectClass,"y","I"))*realH/screenH,env->GetIntField(jrect,env->GetFieldID(jrectClass,"width","I"))*realW/screenW,env->GetIntField(jrect,env->GetFieldID(jrectClass,"height","I"))*realH/screenH);\
	params->push_back(r);
#pragma endregion

//Watchers initialization

EXPORTABLE void init_TESTWATCHER(vector<any>*params,jobject watcher){ //Optional method (necessary in case if coordinates are used)
    addCoords();
}