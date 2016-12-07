
public class Testi {
    public static void main(String[] args){
        double C[][] = { {0,4,8,3},{0,2,2,4},{0,-3,-7,2},{0,1,5,2} };
        double b[] = { 7,0,1,2};
       printvektor(b);
       //double x[] = Gauss.solve(C, b);
       double x[] = Gauss.solveSing(C);
       printvektor(x);
       x = Gauss.solve(C, b);
       printvektor(x);
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
