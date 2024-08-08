package net.azplugin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Predicate;

import com.azexternal.ITaskExecutor;
import com.azexternal.PluginManager.ExternalWatcherCommand;
import com.azexternal.PluginManager.ExternalWatcherCommand.WatcherSubType;

/*
 * Welcome to AZ-Plugin maker!
 * 
 * This enum file represents a bundle of custom watcher types.
 * Each enum constant is a single watcher type.
 */

public enum TestpluginWatcherSubType implements WatcherSubType{
    TESTWATCHER{
        public Predicate<ITaskExecutor>getWatcher(ExternalWatcherCommand c){
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
        public HashMap<String,Serializable>getDefaultProperties(ExternalWatcherCommand c){
            HashMap<String,Serializable>p=new HashMap<String,Serializable>();
            /*
             * p.put("property1",value1);
             * p.put("property2",value2);
             * p.put("property3",value3);
             * ...
             */
            return p;
        }
        public int getRequests(){return 1;} //2 - nothing additional required; 1 - coordinates required; 0 - area (`Rectangle`) required
        public void paint(int x,int y,int s/*radius*/,Graphics2D g2,int a/*opacity*/){
            g2.setColor(new Color(255,255,255,a));
            g2.fillRoundRect(x-s,y-s,s*2,s*2,s,s); //Please always paint round-rects (with arc size = s) for watchers.
            g2.setColor(new Color(255,255,100,a));
            g2.drawRoundRect(x-s,y-s,s*2,s*2,s,s);
        }
        public String getSourcePlugin(){return "Testplugin";} //Should always return the name of the export-folder.
    },
}
