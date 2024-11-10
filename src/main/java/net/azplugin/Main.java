package net.azplugin;

import java.util.ArrayList;

import com.azexternal.DynamicSupplier;
import com.azexternal.EditorContext;
import com.azexternal.ExternalContext;
import com.azexternal.ExternalContext.AZEvent;
import com.azexternal.ExternalContext.AZListener;
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
	public static void init(EditorContext context,ArrayList<DynamicSupplier<Point>>activeCoordinators,ArrayList<DynamicSupplier<String>>activeComposers,String folder,ExternalContext external){
		Main.context=context;
		Main.activeCoordinators=activeCoordinators;
		Main.activeComposers=activeComposers;
		external.addAZListener(new AZListener(){
			public void presetEdited(AZEvent e){ //Fires after a change is commited.
				//...
			}
			public void presetSaving(AZEvent e){ //Fires before preset is saved. You can affect or even cancel the saving process by calling `setDirectory()` / `consume()`.
				//...
			}
			public void taskLaunching(AZEvent e){ //Fires before the task is launched. You can cancel it by calling `consume()`.
				//...
			}
			public void screenSwitching(AZEvent e){ //Fires before the user changes current screen. You can prohibit this by calling `consume()`.
				//...
			}
		});
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
}