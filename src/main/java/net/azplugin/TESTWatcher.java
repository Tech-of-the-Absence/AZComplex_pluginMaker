package net.azplugin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Predicate;

import com.azexternal.ITaskExecutor;
import com.azexternal.PluginComponent;
import com.azexternal.WatcherCommand;
import com.azexternal.WatcherCommand.WatcherType;

/*
 * Welcome to AZ-Plugin maker!
 * 
 * This class represents a custom watcher type.
 */

@PluginComponent
public class TESTWatcher implements WatcherType{ //Pay attention to the name style. The pattern is YOURNAMEWatcher
	@Override public Predicate<ITaskExecutor>getWatcher(WatcherCommand c){
		return f->{
			if(c.delay!=0)Main.delay(f,c.delay);//Delay
			int x=(int)c.dynamicProperties.get("x"),y=(int)c.dynamicProperties.get("y"); //Coordinates prepairing (should be removed if watcher is not positional)
			if(c.dynamicCoords){
				Point coords=c.getPoint();
				c.dynamicProperties.put("x",coords.x);
				c.dynamicProperties.put("y",coords.y);
			}
			// ...
			return x>y; //Deciding whether to continue
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
		g2.fillRoundRect(x-s,y-s,s*2,s*2,s,s); //Please always paint round-rects (with arc size = s) for watchers.
		g2.setColor(new Color(255,255,100,a));
		g2.drawRoundRect(x-s,y-s,s*2,s*2,s,s);
	}
}
