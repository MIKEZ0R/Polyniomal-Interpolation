/**
 * @(#)FunctionPlot.java
 *
 *
 * @author Michal Nowakowski KrDzIS2013
 * @version 1.00 2018/2/6
 */

package interpolation;
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
 

public class FunctionPlot extends JPanel{

  private Newton newton ;
	private Lagrange lagrange;
	private Hermite hermite;
	private int number;
	
	public FunctionPlot(Newton newton,Hermite hermite, Lagrange lagrange,int number){
		this.newton = newton;
		this.hermite = hermite;
		this.lagrange = lagrange;
		this.number = number;
	}
	
	//metoda sluzaca do skalowania pkt na osi ox
	 public void prepareX(double[] a,int odl){
	 	for(int i=0;i<a.length;i++){
	 		a[i] = a[i]*(double)odl ;
	 	}
	 }
	 
	 
	 //metoda sluzaca do skalowania pkt na osi oy
	 public void prepareY(double[] y,int odl){
	 	for(int i=0;i<y.length;i++){
	 		y[i] = (-1.0)*y[i]*(double)((double)odl/5) ;
	 	}
	 }
	
	public void paintComponent(Graphics g) {
		int rangeA = -20; //lewa strona przedzialu w ktorym rysowana bedzie funkcja
		int rangeB = 20; // prawa reszta jw.
		Graphics2D lag = (Graphics2D) g;
		Graphics2D newt = (Graphics2D) g;
		Graphics2D her = (Graphics2D) g;
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); // pobieram rozmiary ekranu
		int w = (int)d.getWidth()/2;
		int h = (int)d.getHeight()/2;
		int max = 400; // max na osi oy
		int odly = (h-100)/(max/5); //odlegosc jednego pkt na osi oy
		int odlx = (w-100)/20; //odlegosc jednego pkt na osi ox
        super.paintComponent(g);    
        g.translate(w,h);	//ustawiam srodek ukladu na w,h   
		g.drawLine(0,0,w-80,0); //prawa os ox
 		g.drawLine(0,0,-w+80,0); //lewa os ox
 		g.drawLine(0,0,0,h-100);// dolna czesci osi oy
 		g.drawLine(0,0,0,-h+100); //gorna czesc osi oy
 		g.drawLine(w-80,0,w-90,-10); 
 		g.drawLine(w-80,0,w-90,10);
 		g.drawString("x",w-80,20);
 		
 		g.drawLine(0,-h+100,-10,-h+110);
 		g.drawLine(0,-h+100,10,-h+110);
 		g.drawString("y",20,-h+100);
 		
 		//petla rysujaca pionowe kreski na osi ox
 		for(int i=1;i<=20;i++){
 		
 			g.drawLine(0+i*odlx,0,0+i*odlx,5);
 			g.drawLine(0+i*odlx,0,0+i*odlx,-5);
 			g.drawLine(0-i*odlx,0,0-i*odlx,5);
 			g.drawLine(0-i*odlx,0,0-i*odlx,-5);
 			if(i%5 == 0){
	 			g.drawString(Integer.toString(i),i*odlx,25);
	 			g.drawString(Integer.toString(-i),-i*odlx,25);
	 			
 			}
 		}
 		
 		
 		
 		//petla rysujaca poziome kreski na osi oy
 		for(int i=0;i<=max;i+=5){
 			g.drawLine(0,0-i/5*odly,5,0-i/5*odly);
 			g.drawLine(0,0-i/5*odly,-5,0-i/5*odly);
 			g.drawLine(0,0+i/5*odly,5,0+i/5*odly);
 			g.drawLine(0,0+i/5*odly,-5,0+i/5*odly);
 			if(i%25 == 0 && i !=0){
	 			g.drawString(Integer.toString(-i),15,i/5*odly);
		 		g.drawString(Integer.toString(i),-25,-i/5*odly);
 			}
 		}
 		
 		if(number == 1){
 			Graphics2D d2 = (Graphics2D) g;
	 		d2.setFont(new Font("TimesRoman", Font.BOLD, 11)); 
	 		d2.setPaint(Color.BLACK);	
	 		Rectangle2D.Double l = new Rectangle2D.Double(-w+160,-h+70, 50, 20);
	 		d2.drawString(" - Lagrange interpolation",-w+215,-h+85);
	 		d2.setPaint(Color.BLUE);
	 		d2.fill(l);
	 		d2.draw(l);
	 		
	 	
	 		
	 	
    	
	 		double[] x2 = Lagrange.generateX(rangeA,rangeB,30); // generuje wezly od rangeA do rangeB kazdy wezel oddalony od siebie o 1/30
	    	double[] y2 = lagrange.generateValues(x2); //generuje wartosci dla poszczegolnych wezlow
	    	prepareX(x2,odlx); // skaluje tablice x by dopasowac do ukladu
	    	prepareY(y2,odly); //skaluje tablice y by dopasowac do ukladu
	 		lag.setColor(Color.BLUE);
	 		
	 		//rysowane sa male prostokaty w zadanych pkt 
	 		for(int i=0;i<x2.length;i++){
		 		Rectangle2D.Double rect2 = new Rectangle2D.Double(x2[i] - 0.5, y2[i] - 0.5, 1, 1);
		 		lag.draw(rect2);
 	
 			}
 		}
 		
 		if(number == 2){
	 		Graphics2D d1 = (Graphics2D) g;
	 		d1.setFont(new Font("TimesRoman", Font.BOLD, 11)); 
	 		d1.setPaint(Color.BLACK);
	 		Rectangle2D.Double n = new Rectangle2D.Double(-w+160,-h+40, 50, 20);
	 		d1.drawString(" - Newton interpolation",-w+215,-h+55);
	 		d1.setPaint(Color.RED);
	 		d1.fill(n);
	 		d1.draw(n);
 			
 			
	    	double[] x = Newton.generateX(rangeA,rangeB,30); // generuje wezly od rangeA do rangeB kazdy wezel oddalony od siebie o 1/30
	    	double[] y = newton.generateValues(x);	//generuje wartosci dla poszczegolnych wezlow
	    	prepareX(x,odlx); // skaluje tablice x by dopasowac do ukladu
	    	prepareY(y,odly); //skaluje tablice y by dopasowac do ukladu
	    	newt.setColor(Color.RED);
	    		    	
	    		//rysowane sa male prostokaty w zadanych pkt 
	 		for(int i=0;i<x.length;i++){
	 			Rectangle2D.Double rect = new Rectangle2D.Double(x[i] - 0.5, y[i] - 0.5, 1, 1);
	 			newt.draw(rect);
	 	
	 		}
	    	
 		}
 		
 		if(number == 3){
 			Graphics2D d3 = (Graphics2D) g;
	 		d3.setPaint(Color.BLACK);
	 		d3.setFont(new Font("TimesRoman", Font.BOLD, 11)); 
	 		Rectangle2D.Double he = new Rectangle2D.Double(-w+160,-h+100, 50, 20);
	 		d3.drawString(" - Hermite interpolation",-w+215,-h+115);
	 		d3.fill(he);
	 		d3.draw(he);
	 		
	    	
	 		double[] x3 = Hermite.generateX(rangeA,rangeB,30); // generuje wezly od rangeA do rangeB kazdy wezel oddalony od siebie o 1/30
	    	double[] y3 = hermite.generateValues(x3); //generuje wartosci dla poszczegolnych wezlow
	    	prepareX(x3,odlx); // skaluje tablice x by dopasowac do ukladu
	    	prepareY(y3,odly); //skaluje tablice y by dopasowac do ukladu
	 		her.setColor(Color.BLACK);
	 		
	 		//rysowane sa male prostokaty w zadanych pkt 
	 		for(int i=0;i<x3.length;i++){
		 		Rectangle2D.Double rect22 = new Rectangle2D.Double(x3[i] - 0.5, y3[i] - 0.5, 1, 1);
		 		her.draw(rect22);
	 	
 			}
 		}
 		
 		if(number == 4){
 		
 		
	 		//elemety do legendy znajdujacej sie w lewym gornym rogu
	 		Graphics2D d1 = (Graphics2D) g;
	 		d1.setFont(new Font("TimesRoman", Font.BOLD, 11)); 
	 		d1.setPaint(Color.BLACK);
	 		Rectangle2D.Double n = new Rectangle2D.Double(-w+160,-h+40, 50, 20);
	 		d1.drawString(" - Newton interpolation",-w+215,-h+55);
	 		d1.setPaint(Color.RED);
	 		d1.fill(n);
	 		d1.draw(n);
	 		
	 		Graphics2D d2 = (Graphics2D) g;
	 		d2.setFont(new Font("TimesRoman", Font.BOLD, 11)); 
	 		d2.setPaint(Color.BLACK);	
	 		Rectangle2D.Double l = new Rectangle2D.Double(-w+160,-h+70, 50, 20);
	 		d2.drawString(" - Lagrange interpolation",-w+215,-h+85);
	 		d2.setPaint(Color.BLUE);
	 		d2.fill(l);
	 		d2.draw(l);
	 		
	 		Graphics2D d3 = (Graphics2D) g;
	 		d3.setPaint(Color.BLACK);
	 		d3.setFont(new Font("TimesRoman", Font.BOLD, 11)); 
	 		Rectangle2D.Double he = new Rectangle2D.Double(-w+160,-h+100, 50, 20);
	 		d3.drawString(" - Hermite interpolation",-w+215,-h+115);
	 		d3.fill(he);
	 		d3.draw(he);
	 		
	 		double[] x = Newton.generateX(rangeA,rangeB,30); // generuje wezly od rangeA do rangeB kazdy wezel oddalony od siebie o 1/30
	    	double[] y = newton.generateValues(x);	//generuje wartosci dla poszczegolnych wezlow
	    	prepareX(x,odlx); // skaluje tablice x by dopasowac do ukladu
	    	prepareY(y,odly); //skaluje tablice y by dopasowac do ukladu
	    	newt.setColor(Color.RED);
	    		    	
	    		//rysowane sa male prostokaty w zadanych pkt 
	 		for(int i=0;i<x.length;i++){
	 			Rectangle2D.Double rect = new Rectangle2D.Double(x[i] - 0.5, y[i] - 0.5, 1, 1);
	 			newt.draw(rect);
	 	
	 		}
	 		
	 		double[] x2 = Lagrange.generateX(rangeA,rangeB,30); // generuje wezly od rangeA do rangeB kazdy wezel oddalony od siebie o 1/30
	    	double[] y2 = lagrange.generateValues(x2); //generuje wartosci dla poszczegolnych wezlow
	    	prepareX(x2,odlx); // skaluje tablice x by dopasowac do ukladu
	    	prepareY(y2,odly); //skaluje tablice y by dopasowac do ukladu
	 		lag.setColor(Color.BLUE);
	 		
	 		//rysowane sa male prostokaty w zadanych pkt 
	 		for(int i=0;i<x2.length;i++){
		 		Rectangle2D.Double rect2 = new Rectangle2D.Double(x2[i] - 0.5, y2[i] - 0.5, 1, 1);
		 		lag.draw(rect2);
 	
 			}
 			
 			double[] x3 = Hermite.generateX(rangeA,rangeB,30); // generuje wezly od rangeA do rangeB kazdy wezel oddalony od siebie o 1/30
	    	double[] y3 = hermite.generateValues(x3); //generuje wartosci dla poszczegolnych wezlow
	    	prepareX(x3,odlx); // skaluje tablice x by dopasowac do ukladu
	    	prepareY(y3,odly); //skaluje tablice y by dopasowac do ukladu
	 		her.setColor(Color.BLACK);
	 		
	 		//rysowane sa male prostokaty w zadanych pkt 
	 		for(int i=0;i<x3.length;i++){
		 		Rectangle2D.Double rect22 = new Rectangle2D.Double(x3[i] - 0.5, y3[i] - 0.5, 1, 1);
		 		her.draw(rect22);
	 	
 			}
 		}
 		

    } 
    public static void main(String[] args){
    } 
}
    
    
