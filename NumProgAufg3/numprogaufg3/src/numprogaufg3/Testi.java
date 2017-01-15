

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
       
       
    }
}
