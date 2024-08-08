package net.azplugin;

import java.awt.Graphics2D;

import com.azexternal.DynamicSupplier;
import com.azexternal.PluginManager.ExternalComposer;
import com.azexternal.PluginManager.ExternalComposer.ComposerSubType;
import java.awt.Dimension;
import java.awt.Color;

/*
 * Welcome to AZ-Plugin maker!
 * 
 * Below you can see a basic realization of the `ComposerSubType` interface.
 * Note, that it must be implemented with enums only. Each enum constant is a single composer type.
 */

public enum TestpluginComposerSubType implements ComposerSubType{
    TESTCOMPOSER{
        public DynamicSupplier<String>getActiveComposer(ExternalComposer c){
			return()->"Test string";//Returning a fixed string "Test string".
			//You can also place here a full anonimous class definition like this:
			/*
			 * 	return new DynamicSupplier<String>(){
			 * 		public Point get(){return "Test string";}
			 * 		public void init(){...}
			 * 	}
			*/
			//This might be useful if you plan to initialize the supplier somehow.
		}
		public void initProperties(String s,ExternalComposer c){
			/*
			 * c.properties.add(...) //<comment>
			 * c.properties.add(...) //<comment>
			 * ...
			 */
			//Do not forget to add a comment with the name of a property added as well as a file in "coordinators" folder.
		}
		public void paint(Graphics2D g2,ExternalComposer c,int x,int y,Dimension size){
			//Please paint something smooth that represent the functionality well.
			g2.setColor(Color.BLACK);
			g2.fillOval(x+size.width/4,y+size.height/4,size.width/2,size.height/2);
		}
		public boolean isFlatBased(){return false;} //"Flat-based" means "based on the string set by user (can be retrieved with properties.get(0))".
		public String getSourcePlugin(){return "Testplugin";} //Should always return the name of the export-folder.
    },
}
