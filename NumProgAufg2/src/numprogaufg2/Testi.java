
public class Testi {
    public static void main(String[] args){
        double C[][] = { {2,10,7,3},{4,-5,-2,-8},{8,-5,0,-4},{4,-10,3,-16} };
        double b[] = { -6,-17,-23,2};
       //double x[] = Gauss.solve(C, b);
       double x[] = Gauss.solveSing(C);
       Util.printVector(x);
       //x = Gauss.solve(C, b);
       //printvektor(x);
    }
    
    private static void printvektor(double b[]){
        System.out.print("{ ");
        for(int i=0; i<b.length;i++){
            System.out.print(b[i]);
            if(i!=b.length-1){
                System.out.print(", ");
            }
        }
        System.out.println(" }");
    }
}
