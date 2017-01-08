import java.util.Arrays;

/**
 * Die Klasse Newton-Polynom beschreibt die Newton-Interpolation. Die Klasse
 * bietet Methoden zur Erstellung und Auswertung eines Newton-Polynoms, welches
 * uebergebene Stuetzpunkte interpoliert.
 * 
 * @author braeckle
 * 
 */
public class NewtonPolynom implements InterpolationMethod {

	/** Stuetzstellen xi */
	double[] x;

	/**
	 * Koeffizienten/Gewichte des Newton Polynoms p(x) = a0 + a1*(x-x0) +
	 * a2*(x-x0)*(x-x1)+...
	 */
	double[] a;

	/**
	 * die Diagonalen des Dreiecksschemas. Diese dividierten Differenzen werden
	 * fuer die Erweiterung der Stuetzstellen benoetigt.
	 */
	double[] f;

	/**
	 * leerer Konstruktore
	 */
	public NewtonPolynom() {
	};

	/**
	 * Konstruktor
	 * 
	 * @param x
	 *            Stuetzstellen
	 * @param y
	 *            Stuetzwerte
	 */
	public NewtonPolynom(double[] x, double[] y) {
		this.init(x, y);
	}

	/**
	 * {@inheritDoc} Zusaetzlich werden die Koeffizienten fuer das
	 * Newton-Polynom berechnet.
	 */
	@Override
	public void init(double a, double b, int n, double[] y) {
		x = new double[n + 1];
		double h = (b - a) / n;

		for (int i = 0; i < n + 1; i++) {
			x[i] = a + i * h;
		}
		computeCoefficients(y);
	}

	/**
	 * Initialisierung der Newtoninterpolation mit beliebigen Stuetzstellen. Die
	 * Faelle "x und y sind unterschiedlich lang" oder "eines der beiden Arrays
	 * ist leer" werden nicht beachtet.
	 * 
	 * @param x
	 *            Stuetzstellen
	 * @param y
	 *            Stuetzwerte
	 */
	public void init(double[] x, double[] y) {
		this.x = Arrays.copyOf(x, x.length);
		computeCoefficients(y);
	}

	/**
	 * computeCoefficients belegt die Membervariablen a und f. Sie berechnet zu
	 * uebergebenen Stuetzwerten y, mit Hilfe des Dreiecksschemas der
	 * Newtoninterpolation, die Koeffizienten a_i des Newton-Polynoms. Die
	 * Berechnung des Dreiecksschemas soll dabei lokal in nur einem Array der
	 * Laenge n erfolgen (z.B. spaltenweise Berechnung). Am Ende steht die
	 * Diagonale des Dreiecksschemas in der Membervariable f, also f[0],f[1],
	 * ...,f[n] = [x0...x_n]f,[x1...x_n]f,...,[x_n]f. Diese koennen spaeter bei
	 * der Erweiterung der Stuetzstellen verwendet werden.
	 * 
	 * Es gilt immer: x und y sind gleich lang.
	 */
	private void computeCoefficients(double[] y) {
          /* TODO: diese Methode ist zu implementieren */
          this.f = new double[y.length];
          this.a = new double[y.length];
          for(int i = 0; i < y.length; i++) {
            this.f[i] = y[i];
          }
          this.a[0] = this.f[0];
          for(int k = 1; k < this.f.length; k++) {
            for(int i = 0; i < (this.f.length - k); i++) {
              this.f[i] = (this.f[i+1] - this.f[i]) / (this.x[i+k] - this.x[i]);
              if(i == 0) {
                this.a[k] = this.f[i];
              }
            }
          }
	}

	/**
	 * Gibt die Koeffizienten des Newton-Polynoms a zurueck
	 */
	public double[] getCoefficients() {
		return a;
	}

	/**
	 * Gibt die Dividierten Differenzen der Diagonalen des Dreiecksschemas f
	 * zurueck
	 */
	public double[] getDividedDifferences() {
		return f;
	}

	/**
	 * addSamplintPoint fuegt einen weiteren Stuetzpunkt (x_new, y_new) zu x
	 * hinzu. Daher werden die Membervariablen x, a und f vergoessert und
	 * aktualisiert . Das gesamte Dreiecksschema muss dazu nicht neu aufgebaut
	 * werden, da man den neuen Punkt unten anhaengen und das alte
	 * Dreiecksschema erweitern kann. Fuer diese Erweiterungen ist nur die
	 * Kenntnis der Stuetzstellen und der Diagonalen des Schemas, bzw. der
	 * Koeffizienten noetig. Ist x_new schon als Stuetzstelle vorhanden, werden
	 * die Stuetzstellen nicht erweitert.
	 * 
	 * @param x_new
	 *            neue Stuetzstelle
	 * @param y_new
	 *            neuer Stuetzwert
	 */
	public void addSamplingPoint(double x_new, double y_new) {
          /* TODO: diese Methode ist zu implementieren */
          // Pruefe ob der neue Stuetzpunkt schon existiert.
          for(int i = 0; i < this.x.length; i++) {
            if(this.x[i] == x_new) {
              return;
            }
          }
          double [] new_f = new double[this.f.length + 1];
          double [] new_a = new double[this.a.length + 1];
          double [] new_x = new double[this.x.length + 1];

          for(int i = 0; i < this.x.length; i++) {
            new_x[i] = this.x[i];
          }
          new_x[new_x.length-1] = x_new;

          new_f[new_f.length-1] = y_new;

          for(int i = (new_f.length-2); i >= 0; i--) {
            new_f[i] = (new_f[i+1] - this.f[i]) / (new_x[i] - new_x[(new_f.length - 1) - i]);
          }

          for(int i = 0; i < this.a.length; i++) {
            new_a[i] = this.a[i];
          }
          new_a[new_a.length-1] = new_f[new_f.length-1];

          this.f = new_f;
          this.a = new_a;
          this.x = new_x;
	}

	/**
	 * {@inheritDoc} Das Newton-Polynom soll effizient mit einer Vorgehensweise
	 * aehnlich dem Horner-Schema ausgewertet werden. Es wird davon ausgegangen,
	 * dass die Stuetzstellen nicht leer sind.
	 */
	@Override
	public double evaluate(double z) {
          /* TODO: diese Methode ist zu implementieren */
          double res = this.a[this.a.length - 1];
          
          for(int i = (this.x.length - 2); i >= 0; i--) {
            res = this.a[i] + ((z - this.x[i]) * res);
          }

          return res;
	}
}
