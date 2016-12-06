
public class Testi {
    public static void main(String[] args){
        Gleitpunktzahl.setSizeExponent(2);
        Gleitpunktzahl.setSizeMantisse(4);

        //unendlich + iwas = unendlich
        //unendlich - iwas = unendlich
        //unendlich + unednlich = unendlich
        //unendlich - unendlich = nan
        //nan +/- iwas
        //null +/- iwas
        Gleitpunktzahl a = new Gleitpunktzahl(0.5);
        //a.setNaN(true);
        Gleitpunktzahl b = new Gleitpunktzahl(3.75);
        b.setInfinite(true);
        System.out.println("a: " + a.toString()+ " = " + a.toDouble());
        System.out.println("b: " + b.toString()+ " = " + b.toDouble());
        System.out.println(a.exponent);
        // Gleitpunktzahl.denormalisiere(a, b);
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
