/**
 * @(#)Lagrange.java
 *
 *
 * @author Michal Nowakowski KrDzIS2013
 * @version 1.00 2018/1/25
 */
 
package interpolation;
import java.math.BigDecimal;
import java.math.RoundingMode;




public class Lagrange {
	
	private double[] xArr;
	private double[] yArr;

    public Lagrange(double[] xArr, double[] yArr){
    	this.xArr = xArr;
    	this.yArr = yArr;
    }
    
    //method computes denominator for next words lagrange polymional
    private double[] calcDenominator(){
    	double pivot;
    	double[] result = new double[xArr.length];
    	double r = 1;
    	
    	for(int i=0;i<result.length;i++){
    		result[i] = 1;
    	}
    	
    	for(int i=0;i<xArr.length;i++){
    		pivot = xArr[i];
    		for(int j=0;j<xArr.length;j++){
    			if(pivot - xArr[j] !=0){
    				result[i] *= (pivot-xArr[j]);		
    			}
    		}
    	}
    	
    	return result;
    }
    //method computes for which value next lagrange polymional word intersections will be multiplied 
    private double[] calcFraction(){
    	double[] denominators = calcDenominator();
    	double[] fractions = new double[denominators.length];
    	
    	for(int i=0;i<yArr.length;i++){
    		fractions[i] = yArr[i]/denominators[i];
    	}

    	return fractions;
    }
    //method which generate lagrange polymional as a String
    public String generatePolymional(){
    	String polymional = "";
    	double[] frac = calcFraction();
    	String[] arr = new String[xArr.length];
    	
    	for(int i=0;i<arr.length;i++){
    		arr[i] = "";
    	}
    	
    	for(int i=0;i<xArr.length;i++){
    		for(int j=0;j<xArr.length;j++){
    			if(xArr[j] != xArr[i]){
		    		if(xArr[j] >=0)
		    			arr[i] += "(x - "+Double.toString(Math.abs(xArr[j]))+")";
		    		else
		    			arr[i] += "(x + "+Double.toString(Math.abs(xArr[j]))+")";
    			}
    		}
    	}
    	
    	for(int i=0;i<arr.length;i++){
    		if(i==0){
    				polymional += Double.toString(frac[i])+"*"+arr[i];
    		}else
    			if(frac[i] > 0)
    				polymional += "+"+Double.toString(frac[i])+"*"+arr[i];
    			else
    				polymional += Double.toString(frac[i])+"*"+arr[i];
    	}
    	
    	return polymional;
    }
    
    //method which computes value in point
    public double valueInPoint(double x){
    	double[] frac = calcFraction();
    	double tmp;
    	double result = 0.0;
    	double pivot;
    	
    	for(int i=0;i<xArr.length;i++){
    		pivot = xArr[i];
    		tmp = 1.0;
    		for(int j=0;j<xArr.length;j++){
    			if(pivot - xArr[j] !=0){	
    				tmp *= (x-xArr[j]);		
    			}	
    		}
    		result +=frac[i]*tmp;
    	}
    	 BigDecimal bd = new BigDecimal(result);
   		 bd = bd.setScale(5, RoundingMode.HALF_UP); // wynik zaokraglam do 5 miejsc
    	
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
    //method which generete array of values for next nodes
    public double[] generateValues(double[] x){
    	double[] y = new double[x.length];
    	for(int i=0;i<y.length;i++){
    		y[i] = valueInPoint(x[i]);
    	}   	
    	return y;
    }
    //methods which computes maximum and minimum 
    public double findMax(int a, int b, double[] y){
    	double max = y[0];
    	
    	for(int i=0;i<y.length;i++){
    		if(max < y[i])
    			max = y[i];
    	}
    	return max;    	
    }
    
     public double findMin(int a, int b, double[] y){
    	double min = y[0];
    	
    	for(int i=0;i<y.length;i++){
    		if(min > y[i])
    			min = y[i];
    	}
    	return min;    	
    }
    
    
    
    
    public static void main(String[] args){
    	double[] xArr = {-1,0,1,2};
    	double[] yArr = {1,4,17,97};
    	
    	Lagrange l = new Lagrange(xArr,yArr);
    
    	
    	System.out.println("W"+(xArr.length-1)+"(x) = "+l.generatePolymional());
 
    	
    }
    
    
}