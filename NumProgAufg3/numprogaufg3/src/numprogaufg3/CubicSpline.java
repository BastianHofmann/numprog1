import java.util.Arrays;

/**
 * Die Klasse CubicSpline bietet eine Implementierung der kubischen Splines. Sie
 * dient uns zur effizienten Interpolation von aequidistanten Stuetzpunkten.
 * 
 * @author braeckle
 * 
 */
public class CubicSpline implements InterpolationMethod {

	/** linke und rechte Intervallgrenze x[0] bzw. x[n] */
	double a, b;

	/** Anzahl an Intervallen */
	int n;

	/** Intervallbreite */
	double h;

	/** Stuetzwerte an den aequidistanten Stuetzstellen */
	double[] y;

	/** zu berechnende Ableitungen an den Stuetzstellen */
	double yprime[];

	/**
	 * {@inheritDoc} Zusaetzlich werden die Ableitungen der stueckweisen
	 * Polynome an den Stuetzstellen berechnet. Als Randbedingungen setzten wir
	 * die Ableitungen an den Stellen x[0] und x[n] = 0.
	 */
	@Override
	public void init(double a, double b, int n, double[] y) {
		this.a = a;
		this.b = b;
		this.n = n;
		h = ((double) b - a) / (n);

		this.y = Arrays.copyOf(y, n + 1);

		/* Randbedingungen setzten */
		yprime = new double[n + 1];
		yprime[0] = 0;
		yprime[n] = 0;

		/* Ableitungen berechnen. Nur noetig, wenn n > 1 */
		if (n > 1) {
			computeDerivatives();
		}
	}

	/**
	 * getDerivatives gibt die Ableitungen yprime zurueck
	 */
	public double[] getDerivatives() {
		return yprime;
	}

	/**
	 * Setzt die Ableitungen an den Raendern x[0] und x[n] neu auf yprime0 bzw.
	 * yprimen. Anschliessend werden alle Ableitungen aktualisiert.
	 */
	public void setBoundaryConditions(double yprime0, double yprimen) {
		yprime[0] = yprime0;
		yprime[n] = yprimen;
		if (n > 1) {
			computeDerivatives();
		}
	}

	/**
	 * Berechnet die Ableitungen der stueckweisen kubischen Polynome an den
	 * einzelnen Stuetzstellen. Dazu wird ein lineares System Ax=c mit einer
	 * Tridiagonalen Matrix A und der rechten Seite c aufgebaut und geloest.
	 * Anschliessend sind die berechneten Ableitungen y1' bis yn-1' in der
	 * Membervariable yprime gespeichert.
	 * 
	 * Zum Zeitpunkt des Aufrufs stehen die Randbedingungen in yprime[0] und yprime[n].
	 * Speziell bei den "kleinen" Faellen mit Intervallzahlen n = 2
	 * oder 3 muss auf die Struktur des Gleichungssystems geachtet werden. Der
	 * Fall n = 1 wird hier nicht beachtet, da dann keine weiteren Ableitungen
	 * berechnet werden muessen.
	 */
	public void computeDerivatives() {
          //y.length ist mindestens 3
          double[] d = new double[this.yprime.length-2]; //da 0 und n schon bestimmt
          //c beschreibt die obere 1er Reihe mit c[c.length-1] = 0, da das zu viel ist
          double[] c = new double[this.yprime.length-2];
          d[0] = 3/h * (y[2] - y[0] - h/3 * yprime[0]);
          d[d.length-1] =3/h * (y[y.length-1] - y[y.length-3] - h/3 * yprime[yprime.length-1]);
          
          for(int i = 1; i<y.length-3;i++){
              //y.length ist mindestens 4
              d[i] =3/h *  (y[i+2] - y[i]);
          }
          c[0] = 0.25;
          d[0] = d[0] / 4;
          for(int i = 1; i< d.length; i++){
              if(i < d.length-1){
              c[i] = 1/(4-c[i-1]);
              }
              d[i] = (d[i]-d[i-1]) / (4-c[i-1]);
          }
          yprime[yprime.length-2] = d[d.length-1];
          for(int i = yprime.length-3; i>0; i--){
              yprime[i] = d[i-1] - c[i-1] * yprime[i+1];
          }  
	}

	/**
	 * {@inheritDoc} Liegt z ausserhalb der Stuetzgrenzen, werden die
	 * aeussersten Werte y[0] bzw. y[n] zurueckgegeben. Liegt z zwischen den
	 * Stuetzstellen x_i und x_i+1, wird z in das Intervall [0,1] transformiert
	 * und das entsprechende kubische Hermite-Polynom ausgewertet.
	 */
	@Override
	public double evaluate(double z) {
          /* TODO: diese Methode ist zu implementieren */
          double[] x = new double[n+1];
          x[0] = a;
          x[n] = b;
          for(int i = 1; i<n ; i++){
              x[i] = x[i-1] + h;
          }
          int i = 0;
          if(x[i] >= z){
              return y[0];
          }
          if(x[n] <= z){
              return y[n];
          }
          while (x[i] < z){
              i++;
              if(x[i] == z){
                  return y[i];
              }
          }
          i--;
          double t = (z-x[i])/(x[i+1]-x[i]);
          double herm0 = 1 - Math.pow(t,2) + 2 * Math.pow(t,3);
          double herm1 = 3 * Math.pow(t,2) - 2 * Math.pow(t,3);
          double herm2 = t - 2 * Math.pow(t,2) + Math.pow(t,3);
          double herm3 = - Math.pow(t,2) + Math.pow(t,3);
          double q = y[i] * herm0 + y[i+1] * herm1 + h * yprime[i] * herm2 + h * yprime[i+1] * herm3;
          return q;
	}
}
