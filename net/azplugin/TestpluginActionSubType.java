package net.azplugin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.azexternal.ITaskExecutor;
import com.azexternal.PluginManager.ExternalActionCommand;
import com.azexternal.PluginManager.ExternalActionCommand.ActionSubType;

/*
 * Welcome to AZ-Plugin maker!
 * 
 * This enum file represents a bundle of custom action types.
 * Each enum constant is a single action type.
 */

public enum TestpluginActionSubType implements ActionSubType{
    TESTACTION{
        public Consumer<ITaskExecutor>getAction(ExternalActionCommand c){
            Predicate<ITaskExecutor>p=c.link==null?f->false:c.link.getWatcher();
            return f->{
                if(c.link!=null)c.link.init(f); //Watcher initialization
                if(c.delay!=0)Main.delay(f,c.delay); //Delay
                Main.activeCoordinators.get(c.coords).init(f); //Coordinator initialization (should be removed if action is not positional)
                Point coords=c.getPoint();
                int x=coords.x,y=coords.y;
                // <some other preparement instructions>
                do{
                    f.getRobot().mouseMove(x,y); //Some action
                    // ...
                    if(c.dynamicCoords){ //Coordinates update (should be removed if action is not positional)
                        coords=c.getPoint();
                        x=coords.x;y=coords.y;
                    }
                }while(p.test(f)); //Watcher control
            };
        }
        public HashMap<String,Serializable>getDefaultProperties(ExternalActionCommand c){
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
            g2.fillOval(x-s,y-s,s*2,s*2); //Please always paint circles for actions.
            g2.setColor(new Color(255,255,100,a));
            g2.drawOval(x-s,y-s,s*2,s*2);
        }
        public String getSourcePlugin(){return "Testplugin";} //Should always return the name of the export-folder.
    },
}