
import java.util.Arrays;

public class Testi {
    public static void main(String args[]){
        double y[] = {0,2,1};
        LinearInterpolation li = new LinearInterpolation();
        li.init(0,2,2,y);
        System.out.println("Für Evaluate(0) - erwartet: 0 - erhalten: "+li.evaluate(0));
        System.out.println("Für Evaluate(0.5) - erwartet: 1 - erhalten: "+li.evaluate(0.5));
        System.out.println("Für Evaluate(1) - erwartet: 2 - erhalten: "+li.evaluate(1));
        System.out.println("Für Evaluate(1.5) - erwartet: 1.5 - erhalten: "+li.evaluate(1.5));
        System.out.println("Für Evaluate(2) - erwartet: 1 - erhalten: "+li.evaluate(2));

        testNewton();
        testSplines();
       
       
    }

    public static void testNewton() {
      double[] x = { -1, 1, 3 };
      double[] y = { -3, 1, -3 };
      NewtonPolynom p = new NewtonPolynom(x, y);
      p.addSamplingPoint(0, 0);

      System.out.println(p.evaluate(0) + " sollte sein: 0.0");
      System.out.println("-------------------------------");
    }

	public static void testSplines() {
		CubicSpline spl = new CubicSpline();
		double[] y = { 2, 0, 2, 3 };
		spl.init(-1, 2, 3, y);
		spl.setBoundaryConditions(9, 0);
		System.out.println(Arrays.toString(spl.getDerivatives())
				+ " sollte sein: [9.0, -3.0, 3.0, 0.0].");
	}
}
