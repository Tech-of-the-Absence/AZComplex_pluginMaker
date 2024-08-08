#include <jni.h>
#include <string>
#include <vector>
#include <any>
#include <windows.h>

using namespace std;

#pragma pack(push,1)
struct RGBColor{
	BYTE r,g,b;
};
#pragma pack(pop)

struct Point{
	int x,y;
	Point(int x,int y);
	Point();
};

struct Rect{
	int x,y,w,h;
	Rect(int x,int y,int w,int h);
};

class Coordinator{
    public:
		virtual Point getPoint()=0;
		virtual void init(jobject command)=0;
		bool global;
};

class Composer{
	public:
		virtual const jchar*getString()=0;
		virtual void init(jobject command)=0;
		int length;
		bool global;
};

struct Image:public BITMAP{
	int*counter;
	Image(BITMAP&b);
	Image(const Image&image);
	~Image();
};

extern jobject(*getWatcher)(jobject action)noexcept;
extern int(*getTypeOrd)(jobject action)noexcept;
extern bool(*callWatcher)(std::vector<std::any>*,int wType);
extern void(*initWatcher)(vector<any>*params,jobject watcher,int wType);
extern void(*delay)(DWORD millis);
extern JNIEnv*env;
extern vector<Coordinator*>*coordinators;
extern vector<Composer*>*composers;
extern map<string,any*>*resources;

extern "C" __fastcall void initVars(JNIEnv*,jobject(*getWatcher)(jobject)noexcept,int(*getTypeOrd)(jobject)noexcept,bool(*callWatcher)(std::vector<std::any>*,int),void(*function_initWatcher)(vector<any>*,jobject,int),void(*delay)(DWORD),vector<Coordinator*>*,vector<Composer*>*,map<string,any*>*);
inline bool equals(jobject,jobject)noexcept;
inline int sqr(int)noexcept;
Image captureScreen(Rect r)noexcept;