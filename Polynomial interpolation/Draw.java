/**
 * @(#)Draw.java
 *
 *
 * @author Michal Nowakowski KrDzIS2013
 * @version 1.00 2018/2/6
 */


import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import interpolation.Lagrange;
import interpolation.Newton;
import interpolation.Hermite;
import interpolation.FunctionPlot;

public class Draw {

    public static void main(String[] args){
    	int what = 0;
    	double[] xArr1 = {-1,0,1,2};
    	double[] yArr1 = {-8,-2,-4,4}; 
    	double[] xArr = {1,2,3,4};
    	double[] yArr = {1,4,9,16}; 		
    	double[] dx = {12,1,-2}; 
    	boolean[] where = {true,true,true}; 
    	Hermite hermite = new Hermite(xArr1,yArr1,dx,where); 
    	Newton newton = new Newton(xArr1,yArr1);
    	Lagrange lagrange = new Lagrange(xArr,yArr);
    	boolean go = true;
    		
    	Dimension a = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)a.getWidth();
		int height = (int)a.getHeight();
		JFrame window = new JFrame("Function plot"); 
		window.setVisible(false);
		window.setSize(width,height); 
		window.setLocationRelativeTo(null); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
    		
    		
    	while(go){
	    	System.out.print("1 - Lagrange interpolation\n2 - Newton interpolation\n3 - Hermite interpolation\n4 - All interpolations\n");
	    	System.out.println("Your choice: ");
	    	Scanner keyboard = new Scanner(System.in);
	    	what = keyboard.nextInt();

	    	switch(what){
	    		case 1:{
			    	FunctionPlot container = new FunctionPlot(newton,hermite,lagrange,1);
			    	window.add(container); 
			    	window.setVisible(true); 
			    	go = false;
			    	break;
	    		}
	    		
	    		case 2:{
			    	window.setVisible(true);
			    	FunctionPlot container = new FunctionPlot(newton,hermite,lagrange,2); 
			    	window.add(container); 
			    	go = false;
			    	break;
	    		}
	    		
	    		case 3:{
			    	window.setVisible(true);
			    	FunctionPlot container = new FunctionPlot(newton,hermite,lagrange,3); 
			    	window.add(container);
			    	go = false;
			    	break;
	    		}
	    		
	    		case 4:{
			    	window.setVisible(true);
			    	FunctionPlot container = new FunctionPlot(newton,hermite,lagrange,4);  
			    	window.add(container); 
			    	break;
	    		}
	    		
	    		default:{
	    			System.out.println("You passed wrong character!");
	    		}
	    		
	    	}
    	
    	}
    	
    }
    
    
}