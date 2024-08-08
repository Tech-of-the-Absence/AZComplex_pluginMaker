package net.azplugin;

import java.util.ArrayList;

import com.azexternal.DynamicSupplier;
import com.azexternal.EditorContext;
import com.azexternal.ITaskExecutor;

import java.awt.Point;

/*
 * Welcome to AZ-Plugin maker!
 * 
 * This is the main class of your project.
 * Make sure that every subtype-class you made is loaded in the AZComplex System.
 */

public class Main{
    public static EditorContext context;
    public static ArrayList<DynamicSupplier<Point>>activeCoordinators;
	public static ArrayList<DynamicSupplier<String>>activeComposers;
    public static void init(EditorContext context,ArrayList<DynamicSupplier<Point>>activeCoordinators,ArrayList<DynamicSupplier<String>>activeComposers){
        Main.context=context;
        Main.activeCoordinators=activeCoordinators;
        Main.activeComposers=activeComposers;
        // ...
    }
    public static void delay(ITaskExecutor f,long millis){ //Please do not change or remove this method.
		long t=System.currentTimeMillis();
		while(System.currentTimeMillis()-t<=millis)switch(f.getRunningState()){
			case 1->{
				millis-=System.currentTimeMillis()-t;
				while(f.getRunningState()==1)f.getRobot().delay(0);
				t=System.currentTimeMillis();
			}
			case 2->throw new RuntimeException("Task cancelled");
		}
	}
    public static String[]getActivationList1(){
        return new String[]{"TestpluginCoordinatorSubType"}; //Coordinator subtype-classes
    }
    public static String[]getActivationList2(){
        return new String[]{"TestpluginComposerSubType"}; //Composer subtype-classes
    }
    public static String[]getActivationList3(){
        return new String[]{"TestpluginActionSubType"}; //Action subtype-classes
    }
    public static String[]getActivationList4(){
        return new String[]{"TestpluginWatcherSubType"}; //Watcher subtype-classes
    }
}
