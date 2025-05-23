package net.azplugin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import com.azexternal.Coordinator;
import com.azexternal.Coordinator.CoordinatorType;
import com.azexternal.DynamicSupplier;
import com.azexternal.PluginComponent;
import com.azexternal.ITaskExecutor;

/*
 * Welcome to AZComplex plugin maker!
 * 
 * Below you can see a basic realization of the `CoordinatorSubType` interface.
 */

@PluginComponent
public class TESTCoordinator implements CoordinatorType{ //Pay attention to the name format. The pattern is YOURNAMECoordinator
	@Override public DynamicSupplier<Point>getCoordinator(ITaskExecutor f,Coordinator c){
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
	@Override public ArrayList<Serializable>getDefaultProperties(int x,int y){
			ArrayList<Serializable>list=new ArrayList<Serializable>();
			/*
			 * list.add(...) //<comment>
			 * list.add(...) //<comment>
			 * ...
			 */
			//Do not forget to add a comment with the name of a property added as well as a file in "coordinators" folder.
			return list;
		}
	@Override public void paint(Graphics2D g2,Coordinator c,int x,int y,Dimension size){
		//Please paint something smooth that represent the functionality well.
		g2.setColor(Color.BLACK);
		g2.fillOval(x+size.width/4,y+size.height/4,size.width/2,size.height/2);
	}
	@Override public void paintTrack(Graphics2D g2,int x,int y,int s,Coordinator c){
		g2.setColor(new Color(0,0,0)); //Setting black color with the right opacity.
		g2.drawOval(x-2*s,y-2*s,4*s,4*s); //Drawing a circle of double radius.
	}
	@Override public boolean isPositional(){return false;} //"Positional" means "based on x and y set by user and moveable".
}
