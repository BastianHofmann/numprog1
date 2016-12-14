
public class Testi {
    public static void main(String[] args){
        double C[][] = { {-0.1, 0.05, 0.333333},{0.05, -0.525, 0.333},{0.05, 0.475, -0.666666667} };
       // double b[] = { -6,-17,-23,2};
       //printvektor(b);
       //double x[] = Gauss.solve(C, b);
       double x[] = Gauss.solveSing(C);
       printvektor(x);
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
