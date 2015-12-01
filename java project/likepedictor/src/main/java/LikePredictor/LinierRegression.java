package LikePredictor;

import java.util.*;
import java.util.stream.Stream;

import Jama.Matrix;
import Jama.QRDecomposition;


// http://introcs.cs.princeton.edu/java/97data/MultipleLinearRegression.java.html

public class LinierRegression {
	
	 ArrayList<ArrayList<Double>> xVals;
	 ArrayList<Double> yVals ; 
	 Matrix regressionCoffiecents;  
	
	static ArrayList<ArrayList<Double>> array2list2d(  Double[][] matriz  )
	{
//		= {{1,5,2,8,4,70,50,80},{3,7,4,2,6,60,30,70},{8,1,6,4,2,10,40,60}};
		 List<Double[]> list = Arrays.asList(matriz);
		 ArrayList<ArrayList<Double>>  result = new ArrayList<ArrayList<Double>> ();
		    for(Double[] array : list){
		        result.add( new ArrayList<Double>( Arrays.asList(array) ));
		    }
		 return result;
	}
	
	static double[][]  arraylist2list2d(ArrayList<ArrayList<Double>>  arrayList)
	{
		double[][] array = new double[arrayList.size()][];
		for (int i = 0; i < arrayList.size(); i++) {
		    ArrayList<Double> row = arrayList.get(i);
		    array[i] = Stream.of(row.toArray(new Double[row.size()])).mapToDouble(Double::doubleValue).toArray();
		}
		
		return array;
	}
	
	void add(ArrayList<Double> xVector , Double y)
	{
		xVals.add(xVector);
		yVals.add(y);
	}
	
	LinierRegression( )
	{
		xVals = new ArrayList<ArrayList<Double>>();
		yVals =  new ArrayList<Double>();
	}
	
	LinierRegression(  ArrayList<ArrayList<Double>> xVals ,  ArrayList<Double> yVals  )
	{
		this.xVals = xVals;
		this.yVals = yVals;
	}
	
	void solve()
	{
		double[][] x = arraylist2list2d(xVals);
		Double[] yD = (Double[] )yVals.toArray( new Double[yVals.size()] );
		double[] y =  Stream.of(yD).mapToDouble(Double::doubleValue).toArray(); // http://stackoverflow.com/questions/1109988/how-do-i-convert-double-to-double
		Matrix X = new Matrix(x);
		Matrix Y = new Matrix(y, yVals.size());
		
		QRDecomposition qr = new QRDecomposition(X);
		regressionCoffiecents = qr.solve(Y);
	}
	
	Double predict( ArrayList<Double> xVector  )
	{
		Double sum = 0.0;
		for(int i=0; i < xVector.size(); i++)
			sum += xVector.get(i) * regressionCoffiecents.get(i, 0 );
		
		return sum;
	}
	
	
	 public static void main(String[] args) {
		 
		 
		 Double[][] matriz  = { {  1.0,  10.0,  20.0 },
                 {  1.0,  20.0,  40.0 },
                 {  1.0,  40.0,  15.0 },
                 {  1.0,  80.0, 100.0 },
                 {  1.0, 160.0,  23.0 },
                 {  1.0, 200.0,  18.0 } };
		 ArrayList<ArrayList<Double>> xVals = array2list2d( matriz );
		 
		 Double[] y = { 243.0, 483.0, 508.0, 1503.0, 1764.0, 2129.0 };
		 
		 ArrayList<Double> yVals =  new ArrayList<Double>(Arrays.asList(y));
		 
		 
		 LinierRegression l = new LinierRegression(xVals , yVals);
		 
		 l.solve();
		 
		 Double[] s ={  2.0, 160.0,  23.0  } ;
		 Double ans = l.predict(new  ArrayList<Double>( new ArrayList<Double> (Arrays.asList(s)   )));
		 
		 System.out.println(ans);
		 
		 
		 
		 
	 }

}
