package net.azplugin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.azexternal.ActionCommand;
import com.azexternal.ActionCommand.ActionType;
import com.azexternal.ITaskExecutor;
import com.azexternal.PluginComponent;

/*
 * Welcome to AZ-Plugin maker!
 * 
 * This class represents a custom action type.
 */

@PluginComponent
public class TESTAction implements ActionType{ //Pay attention to the name style. The pattern is YOURNAMEAction
	@Override public Consumer<ITaskExecutor>getAction(ActionCommand c){
		Predicate<ITaskExecutor>p=c.link==null?f->false:c.link.getWatcher();
		return f->{
			if(c.link!=null)c.link.init(f); //Watcher initialization
			if(c.delay!=0)Main.delay(f,c.delay); //Delay
			Main.activeCoordinators.get(c.coords).init(f); //Coordinator initialization (should be removed if action is not positional)
			Point coords;
			int x=0,y=0;
			// <some other preparement instructions>
			boolean flag=true;
			do{
				// <a place where nothing should be written in order to avoid problems>
				if(flag||c.dynamicCoords){ //Coordinates update (should be removed if action is not positional)
					coords=c.getPoint();
					x=coords.x;
					if(x==-1)continue;
					y=coords.y;
					flag=false;
				}
				f.getRobot().mouseMove(x,y); //Some action
				// ...
			}while(p.test(f)); //Watcher control
		};
	}
	@Override public HashMap<String,Serializable>getDefaultProperties(){
		HashMap<String,Serializable>p=new HashMap<String,Serializable>();
		/*
			* p.put("property1",value1);
			* p.put("property2",value2);
			* p.put("property3",value3);
			* ...
			*/
		return p;
	}
	@Override public int getRequests(){return 1;} //2 - nothing additional required; 1 - coordinates required; 0 - area (`Rectangle`) required
	@Override public void paint(Graphics2D g2,int x,int y,int s/*radius*/,int a/*opacity*/){
		g2.setColor(new Color(255,255,255,a));
		g2.fillOval(x-s,y-s,s*2,s*2); //Please always paint circles for actions.
		g2.setColor(new Color(255,255,100,a));
		g2.drawOval(x-s,y-s,s*2,s*2);
	}
}
