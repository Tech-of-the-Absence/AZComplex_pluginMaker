package net.azplugin;

import com.azexternal.EditorContext;
import com.azexternal.ExternalContext;
import com.azexternal.ITaskExecutor;
import com.azexternal.ExternalContext.AZEvent;
import com.azexternal.ExternalContext.AZListener;

/*
 * Welcome to AZComplex plugin maker!
 *
 * This is the main class of your project.
 * Make sure that every subtype-class you made is loaded in the AZComplex System.
 */

public class Main{
	public static EditorContext context;
	public static ExternalContext external;
	public static void init(EditorContext context,String folder,ExternalContext external){
		Main.context=context;
		external.addAZListener(new AZListener(){
			public void scenarioEdited(AZEvent e){ //Fires after a change is commited.
				//...
			}
			public void scenarioSaving(AZEvent e){ //Fires before scenario is saved. You can affect or even cancel the saving process by calling `setDirectory()` / `cancel()`.
				//...
			}
			public void taskLaunching(AZEvent e){ //Fires before the task is launched. You can cancel it by calling `cancel()`.
				//...
			}
			public void screenSwitching(AZEvent e){ //Fires before the user changes current screen. You can prohibit this by calling `cancel()`.
				//...
			}
		});
		// ...
	}
	public static void delay(ITaskExecutor f,long millis){ //Please do not change or remove this method.
		long t=System.currentTimeMillis();
		while(System.currentTimeMillis()-t<=millis)switch(f.getRunningState()){
			case 0->{}
			case 1->{
				millis-=System.currentTimeMillis()-t;
				while(f.getRunningState()==1)f.getRobot().delay(0);
				t=System.currentTimeMillis();
			}
			case 2->throw new RuntimeException("Task cancelled");
		}
	}
}
