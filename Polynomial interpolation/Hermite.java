/**
 * @(#)Hermite.java
 *
 *
 * @author Michal Nowakowski KrDzIS2013
 * @version 1.00 2018/1/29
 */

package interpolation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Hermite {
	
	private double[] xArr;
	private double[] yArr;
	private double[] derivatives;
	private boolean[] where; // array which holds in which node derivative exists
	
    public Hermite(double[] xArr, double[] yArr, double[] derivatives, boolean[] where ) {
    	this.xArr = xArr;
    	this.yArr = yArr;
    	this.where = where;
    	this.derivatives = derivatives;
    	
    	
    }
    //method which generetes nodes
    private double[] generateXarr(){
    	double[] arr = new double[xArr.length+derivatives.length];
		boolean[] tmp = new boolean[where.length];
		int count = 0;
		
		//counting how many derivatives exists
		for(int i=0;i<where.length;i++){
    		if(where[i] == true)
    			count++;
    	}
		
		//array that holds the same values as where array
		for(int i=0;i<tmp.length;i++){
			tmp[i] = where[i];
		}
		//to array arr ascribing all nodes
		for(int i=0;i<xArr.length;i++){
    			arr[i] = xArr[i];
    	}
    	
    	//to array arr adding such nodes in which derivative exists 
    	for(int i=xArr.length;i<arr.length+count;i++){
    		for(int j=0;j<where.length;j++){
    			if(tmp[j] == true){
    				arr[i] = xArr[j];
    				tmp[j] = false;
    				break;
    			}
    		}
    	}
		Arrays.sort(arr); //sorting array ascending 
		
    	return arr;
    }
    //method which ascribes to array values in set nodes
    private double[] generateYarr(){
    	double[] x = generateXarr();
    	double[] arr = new double[x.length];
		int count = 0;
		
		for(int i=0;i<where.length;i++){
    		if(where[i] == true)
    			count++;
    	}
    	//loops ascribing to node y or if derivative exists value will be asigned 2 times
		for(int i=0;i<x.length;i++){
   			for(int j=0;j<yArr.length;j++){
   				if(x[i] == xArr[j]){
   					arr[i] = yArr[j];

   				}
   			}
   		} 
	
		
    	return arr;
    }
        
    //method which for 2 first columns ascribing nodes and next to it values
    public double[][] generateArr(){
    	double[] x = generateXarr();
    	double[] y = generateYarr();
    	double[][] arr = new double[x.length][y.length+1];
    	for(int i=0;i<arr.length;i++){
    		for(int j=0;j<=0;j++){
	    				arr[i][j] = x[i];
    			
    		}
    	}
    	
    	for(int i=0;i<arr.length;i++){
    		for(int j=1;j<=1;j++){
    			arr[i][j] = y[i];
    		}
    	}
    
    	
    	
    	return arr;
    }
    //method which generates coefficients in 2d array for hermite interpolation 
    private double[][] generateCoefficients(){
    	double[][] arr = generateArr();
    	int jumpX = 1; // varialbe which task is to change step(which x from which x need to substract)
    	int jumpY = 1; 
    	int iTmp =0;
    	int jTmp = 2;
    	int count = 0;
  		for(int j=1;j<arr[iTmp].length-1;j++){
  			for(int i=0;i<arr.length-jumpX;i++){
  				if(arr[i+jumpX][0]-arr[i][0] == 0){
  					arr[i][j+1] = derivatives[count];
  					if(count < derivatives.length-1)
  						count++;	
  				}else
  					arr[i][j+1] = (arr[i+jumpY][j]-arr[i][j])/(arr[i+jumpX][0]-arr[i][0]); // obliczanie danego wspolczynnika w tablicy 2d
  			}
  			jumpX++;
  		}  
    	
 		return arr;
    }
    //method which generates polymional as a String
    public String generatePolymional(){
    	double[][] arr = generateCoefficients();
    	String[] tmpArr = new String[arr.length]; //array in which next intersections are held 
    	
    	String polymional = Double.toString(arr[0][1]) +""; //asigning to string free expression  
    	
    	for(int i=0;i<tmpArr.length;i++){
    		tmpArr[i] = "";
    	}
    	
    	int howMuch = 1;
    	
    	for(int i=0;i<tmpArr.length;i++){
    		for(int j=0;j<howMuch;j++){
	    		if(arr[j][0] <= 0)
	    			tmpArr[i] += "(x + "+Double.toString(Math.abs(arr[j][0]))+")";
	    		else 
	    			tmpArr[i] += "(x - "+Double.toString(Math.abs(arr[j][0]))+")";
    		}
    		howMuch++;
    	}
    	//loop genereting whole polymional 
    	for(int i=0;i<=arr.length-2;i++){
    	
    			if(arr[0][i+2] > 0)
    				polymional += "+"+Double.toString(arr[0][i+2])+"*"+tmpArr[i];
    			else
    				polymional += Double.toString(arr[0][i+2])+"*"+tmpArr[i];
    	}
    	
    	return polymional;
    }
    
    //method which computes value in point for selected node
     public double valueInPoint(double x){
    	
    	double[][] frac = generateCoefficients();
    	double result = frac[0][1];
    	
    	
    	int howMuch = 1;
    	double tmp;
    	for(int i=0;i<frac.length-1;i++){
    		tmp = 1.0;
    		for(int j=0;j<howMuch;j++){
	    			tmp *= (x-frac[j][0]);
    		}
    		howMuch++;
    		result +=frac[0][2+i]*tmp;
    	}	
    	
    	 BigDecimal bd = new BigDecimal(result);
   		 bd = bd.setScale(5, RoundingMode.HALF_UP);
    	
    	return bd.doubleValue();
    }
    //method which generetes nodes on set precision eg. precision=10 means that next nodes will be 1/10 bigger
    public static double[] generateX(int min,int max,int precision){
    	double[] arr = new double[(Math.abs(min)+max)*precision+1];
    	double diff = 0.0; 
    	int count = 0;
    	for(int i=0;i<arr.length;i++){
    		
    		diff = (double)(count*1.0/precision);
    		arr[i] = min+diff;
    		count++;
    	}
    	return arr;
    }
    //method which generetes value for selected nodes  
    public double[] generateValues(double[] x){
    	double[] y = new double[x.length];
    	for(int i=0;i<y.length;i++){
    		y[i] = valueInPoint(x[i]);
    	}   	
    	return y;
    }
    
    
    
    public static void main(String[] args){
    	double[] x = {-1,0,1};
    	double[] y = {-8,-2,-4};
    	double[] d = {1,2};
    	boolean[] where = {true,false,true};
    	Hermite h = new Hermite(x,y,d,where);
		System.out.println("W"+(x.length+d.length-1)+"(x) = "+h.generatePolymional());
    }
    
}