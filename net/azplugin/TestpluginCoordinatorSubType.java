package net.azplugin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import com.azexternal.DynamicSupplier;
import com.azexternal.PluginManager.ExternalCoordinator;
import com.azexternal.PluginManager.ExternalCoordinator.CoordinatorSubType;

/*
 * Welcome to AZ-Plugin maker!
 * 
 * Below you can see a basic realization of the `CoordinatorSubType` interface.
 * Note, that it must be implemented with enums only. Each enum constant is a single coordinator type.
 */

public enum TestpluginCoordinatorSubType implements CoordinatorSubType{
	TESTCOORDINATOR{
		public DynamicSupplier<Point>getActiveCoordinator(ExternalCoordinator c){
			return()->new Point(0,0);//Returning just a fixed point at (0,0)
			//You can also place here a full anonimous class definition like this:
			/*
			 * 	return new DynamicSupplier<Point>(){
			 * 		public Point get(){return new Point(0,0);}
			 * 		public void init(){...}
			 * 	}
			*/
			//This might be useful if you plan to initialize the supplier somehow.
		}
		public void initProperties(int x,int y,ExternalCoordinator c){
			/*
			 * c.properties.add(...) //<comment>
			 * c.properties.add(...) //<comment>
			 * ...
			 */
			//Do not forget to add a comment with the name of a property added as well as a file in "coordinators" folder.
		}
		public void paint(Graphics2D g2,ExternalCoordinator c,int x,int y,Dimension size){
			//Please paint something smooth that represent the functionality well.
			g2.setColor(Color.BLACK);
			g2.fillOval(x+size.width/4,y+size.height/4,size.width/2,size.height/2);
		}
		public void paintTrack(int x,int y,int s,Graphics2D g2,ExternalCoordinator c,int a){
			g2.setColor(new Color(0,0,0,a)); //Setting black color with the right opacity.
			g2.drawOval(x-2*s,y-2*s,4*s,4*s); //Drawing a circle of double radius.
		}
		public boolean isPositional(){return false;} //"Positional" means "based on x and y set by user and moveable".
		public String getSourcePlugin(){return "Testplugin";} //Should always return the name of the export-folder.
	},
}
