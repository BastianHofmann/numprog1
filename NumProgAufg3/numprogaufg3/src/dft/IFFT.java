package dft;

import java.util.Arrays;

/**
 * Schnelle inverse Fourier-Transformation
 *
 * @author Sebastian Rettenberger
 */
public class IFFT {
	/**
	 * Schnelle inverse Fourier-Transformation (IFFT).
	 *
	 * Die Funktion nimmt an, dass die Laenge des Arrays c immer eine
	 * Zweierpotenz ist. Es gilt also: c.length == 2^m fuer ein beliebiges m.
        * @param c
        * @return 
	 */
	public static Complex[] ifft(Complex[] c) {
                Complex[] v = new Complex[c.length];
                if(v.length == 1){
                    v[0] = c[0];
                }
                else{
                    int m = c.length/2;
                    Complex[] z1 = new Complex[m];
                    Complex[] z2 = new Complex[m];
                    for(int i=0; i<m; i++){
                        z1[i] = c[i*2];
                        z2[i] = c[i*2+1];
                    }
                    z1 = ifft(z1);
                    z2 = ifft(z2);
                    double real_omega  = Math.exp(2*Math.PI*(1/c.length));
                    Complex omega = new Complex(real_omega);
                    Complex help;
                    Complex compl_z1;
                    Complex compl_z2;
                    for(int j=0; j<m; j++){
                        help = omega.power(j);
                        compl_z1 = new Complex((z1[j]));
                        compl_z2 = new Complex((z2[j]));
                        help = help.mul(compl_z2);
                        v[j] = compl_z1.add(help);
                        v[m+j] = compl_z1.sub(help);
                    }
                }
		// TODO: diese Methode ist zu implementieren
		return v;
	}
}
