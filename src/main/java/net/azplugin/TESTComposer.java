package net.azplugin;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;

import com.azexternal.Composer;
import com.azexternal.Composer.ComposerType;
import com.azexternal.DynamicSupplier;
import com.azexternal.PluginComponent;
import com.azexternal.ITaskExecutor;

import java.awt.Dimension;
import java.awt.Color;

/*
 * Welcome to AZComplex plugin maker!
 * 
 * Below you can see a basic realization of the `ComposerSubType` interface.
 */

@PluginComponent
public class TESTComposer implements ComposerType{ //Pay attention to the name format. The pattern is YOURNAMEComposer
	@Override public DynamicSupplier<String>getComposer(ITaskExecutor f,Composer c){
		return()->"Test string";//Returning a fixed string "Test string".
		//You can also place here a full anonimous class definition like this:
		/*
		 * 	return new DynamicSupplier<String>(){
		 * 		public String get(){return "Test string";}
		 * 		public void init(){...}
		 * 	}
		 */
		//This might be useful if you plan to initialize the supplier somehow.
	}
	@Override public ArrayList<Serializable>getDefaultProperties(String s){
		ArrayList<Serializable>list=new ArrayList<Serializable>();
		/*
		 * list.add(...) //<comment>
		 * list.add(...) //<comment>
		 * ...
		 */
		//Do not forget to add a comment with the name of a property added as well as a file in "coordinators" folder.
		return list;
	}
	@Override public void paint(Graphics2D g2,Composer c,int x,int y,Dimension size,Polygon p){
		//Please paint something smooth that represent the functionality well.
		g2.setColor(Color.BLACK);
		g2.fillOval(x+size.width/4,y+size.height/4,size.width/2,size.height/2);
	}
	@Override public boolean isFlatBased(){return false;} //"Flat-based" means "based on the string set by user (can be retrieved with properties.get(0))".
}
