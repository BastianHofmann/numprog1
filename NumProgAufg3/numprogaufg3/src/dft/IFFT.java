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
                    //m muss gerade sein, da c.length 2er-Potenz und somit gerade
                    int m = c.length/2;
                    Complex[] z1 = new Complex[m];
                    Complex[] z2 = new Complex[m];
                    for(int i=0; i<m; i++){
                        z1[i] = c[i*2];
                        z2[i] = c[i*2+1];
                    }
                    z1 = ifft(z1);
                    z2 = ifft(z2);
                    // cos 2*Math.Pi*1/c.length + i * sin(Math
                    Complex omega = new Complex(Math.cos(2*Math.PI*1/c.length),Math.sin(2*Math.PI*1/c.length));
                    //double real_omega  = Math.exp(2*Math.PI*(1/c.length));
                    //Complex omega = new Complex(real_omega);
                    for(int j=0; j<m; j++){
                        v[j] = z1[j].add((omega.power(j)).mul(z2[j]));
                        v[m+j] = z1[j].sub((omega.power(j)).mul(z2[j]));
                    }
                }
		return v;
	}
}
