import Jama.Matrix;
import Jama.SingularValueDecomposition;


public class FindLeastSquareMin 
{
     public Matrix getLeastSquareMin(Matrix X,Matrix Y)
     {
    	 Matrix A = new Matrix(X.getRowDimension(),2,1);
    	 A.setMatrix(0,X.getRowDimension()-1,0,0,X.transpose());
    	 Matrix D=Y.transpose();
    	 FindSVD t=new FindSVD();
    	 SingularValueDecomposition SVD = t.getSVD(A); 
    	 Matrix Splus= (SVD.getS().transpose().times(SVD.getS())).times(SVD.getS().transpose());
     	 
         return ((SVD.getV().times(Splus)).times(SVD.getU().transpose())).times(D);     // returns a matrix of m --> slope of reg line
                                                                                        //      b --> intercept of reg line
     }
	
	 
	
	
}
