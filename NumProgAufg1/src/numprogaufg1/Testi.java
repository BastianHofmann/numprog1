
public class Testi {
    public static void main(String[] args){
        Gleitpunktzahl.setSizeExponent(4);
        Gleitpunktzahl.setSizeMantisse(2);
        
        Gleitpunktzahl a = new Gleitpunktzahl(-0.001);
        Gleitpunktzahl b = new Gleitpunktzahl(0.002);
        System.out.println("a: " + a.toString()+ " = " + a.toDouble());
        System.out.println("b: " + b.toString()+ " = " + b.toDouble());
        System.out.println(a.exponent);
        Gleitpunktzahl.denormalisiere(a, b);
        /*System.out.println("------\nDenormalisiert: ");
        
        System.out.println("a: " + a.toString()+ " = " + a.toDouble());
        System.out.println("b: " + b.toString()+ " = " + b.toDouble());
        System.out.println("-------");*/
        //System.out.println(a.mantisse + " " + b.mantisse);
        Gleitpunktzahl sum = a.add(b);
        System.out.println(("Summe von a  und b: " + sum.toString()+ " = " + sum.toDouble()));
        Gleitpunktzahl differenz = a.sub(b);
        System.out.println(("Differenz von a  und b: " + differenz.toString()+ " = " + differenz.toDouble()));
    }
}
