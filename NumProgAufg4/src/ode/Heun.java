package ode;

import java.util.Arrays;

/**
 * Das Einschrittverfahren von Heun
 * 
 * @author braeckle
 * 
 */
public class Heun implements Einschrittverfahren {

	@Override
	/**
	 * {@inheritDoc} 
	 * Nutzen Sie dabei geschickt den Expliziten Euler.
	 */
	public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
            double[] y_k1 = Arrays.copyOf(y_k, y_k.length);
            ExpliziterEuler euler = new ExpliziterEuler();
            double[] help_euler = euler.nextStep(y_k, t, delta_t, ode);
            double[] help_ode = ode.auswerten(t,y_k);
            double[] help_ode2 = ode.auswerten(t,help_euler);
            if(help_ode.length != help_ode2.length || help_ode.length != y_k1.length){
                System.out.println("Fehler! ODE und ODE2 müssen gleiche Länge haben!");
            }
            for(int i = 0; i< y_k1.length; i++){
                y_k1[i] = y_k[i] + delta_t/2 * (help_ode[i] + help_ode2[i]);
            }
            return y_k1;
            /*double[] y_k1 = Arrays.copyOf(y_k,y_k.length);
            ExpliziterEuler euler = new ExpliziterEuler();
            double[] help_euler = euler.nextStep(y_k, t, delta_t, ode);
            double[] help_ode = ode.auswerten(t, y_k);
            double[] help_ode2 = ode.auswerten(t, help_euler);
            double help = y_k[0] + delta_t / 2 * (help_ode[0]+help_ode2[0]);
            y_k1[0] = help;
            return y_k1;*/
	}

}
