 /**
 * @(#)Newton.java
 *
 *
 * @author Michal Nowakowski KrDzIS2013
 * @version 1.00 2018/1/29
 */

package interpolation;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Newton {
	
	private double[] xArr;
	private double[] yArr;
	
    public Newton(double[] xArr, double[] yArr) {
    	this.xArr = xArr;
    	this.yArr = yArr;
    }
    //method which genertes 2 first columns xi and next to it yi 
    private double[][] generateArr(double[] xArr,double[] yArr){
    	double[][] arr = new double[xArr.length][yArr.length+1];
    	for(int i=0;i<arr.length;i++){
    		for(int j=0;j<=0;j++){
    			arr[i][j] = xArr[i];
    		}
    	}
    	
    	for(int i=0;i<arr.length;i++){
    		for(int j=1;j<=1;j++){
    			arr[i][j] = yArr[i];
    		}
    	}
    	
    	return arr;
    }
    //method which genertes next coefficients in 2d array metoda
    private double[][] generateCoefficients(){
    	double[][] arr = generateArr(xArr,yArr);
    	int jumpX = 1;
    	int jumpY = 1;
    	int iTmp =0;
    	int jTmp = 2;
  		for(int j=1;j<arr[iTmp].length-1;j++){
  			for(int i=0;i<arr.length-jumpX;i++){
  				arr[i][j+1] = (arr[i+jumpY][j]-arr[i][j])/(arr[i+jumpX][0]-arr[i][0]);
  			}
  			jumpX++;
  		}  
    	
 		return arr;
    }
    //method which generetes polymional in String 
    public String generatePolymional(){
    	double[][] arr = generateCoefficients();
    	String[] tmpArr = new String[arr.length]; //help array holding next intersections 
    	
    	String polymional = Double.toString(arr[0][1]) +""; //adding free expression
    	
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
    
    //method which computes function value in node metoda 
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
    //method which generetes values for set nodes
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
    	Newton n = new Newton(x,y);
    	double[][] b = n.generateCoefficients();
		System.out.println("W"+(x.length-1)+"(x) = "+n.generatePolymional());
    }
    
}