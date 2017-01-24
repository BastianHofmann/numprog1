package ode;

import java.util.Arrays;

/**
 * Der klassische Runge-Kutta der Ordnung 4
 * 
 * @author braeckle
 * 
 */
public class RungeKutta4 implements Einschrittverfahren {

	@Override
	/**
	 * {@inheritDoc}
	 * Bei der Umsetzung koennen die Methoden addVectors und multScalar benutzt werden.
	 */
	public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
                double[] y_k1;
		double[] k1;// = Arrays.copyOf(y_k, y_k.length);
                double[] k2;// = Arrays.copyOf(y_k, y_k.length);
                double[] k3; // = Arrays.copyOf(y_k, y_k.length);
                double[] k4; // = Arrays.copyOf(y_k, y_k.length);
                k1 = multScalar(ode.auswerten(t, y_k),delta_t);
                double help_t = t + 0.5 * delta_t;
                double[] help_y = addVectors(y_k, multScalar(k1,0.5));
                k2 = multScalar(ode.auswerten(help_t, help_y),delta_t);
                help_y = addVectors(y_k, multScalar(k2, 0.5));
                k3 = multScalar(ode.auswerten(help_t, help_y),delta_t);
                help_t = t + delta_t;
                help_y = addVectors(y_k, k3);
                k4 = multScalar(ode.auswerten(help_t, help_y),delta_t);
                y_k1 = addVectors(k1,(multScalar(k2,2)));
                y_k1 = addVectors(y_k1,(multScalar(k3,2)));
                y_k1 = addVectors(y_k1,k4);
                y_k1 = multScalar(y_k1,1.0/6.0);
                y_k1 = addVectors(y_k, y_k1);
		return y_k1;
	}

	/**
	 * addiert die zwei Vektoren a und b
	 */
	private double[] addVectors(double[] a, double[] b) {
		double[] erg = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			erg[i] = a[i] + b[i];
		}
		return erg;
	}

	/**
	 * multipliziert den Skalar scalar auf den Vektor a
	 */
	private double[] multScalar(double[] a, double scalar) {
		double[] erg = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			erg[i] = scalar * a[i];
		}
		return erg;
	}

}
