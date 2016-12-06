

public class Gauss {

	/**
	 * Diese Methode soll die Loesung x des LGS R*x=b durch
	 * Rueckwaertssubstitution ermitteln.
	 * PARAMETER: 
	 * R: Eine obere Dreiecksmatrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] backSubst(double[][] R, double[] b) {
                if(b.length != R.length){
                    System.out.println("Die Matrix und der Vektor müssen gleich viele Zeilen haben!");
                }
                double[] x = new double[b.length];
                //zu Beginn sind alle Elemente durch defualt mit x = 0 belegt, oder extra machen?
                for(int i=b.length-1; i>0;i++){
                    //n b(zw. i)-te Zeile, j-te Spalte:
                    //b_n - (alle j!=n : a_nj* x_j //also minus alle anderen Elemente in der Zeile mit ihrem jeweiligen x mal-genommen
                }
		//TODO: Diese Methode ist zu implementieren
		return new double[2];
	}

	/**
	 * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
	 * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden. 
	 * PARAMETER: A:
	 * Eine regulaere Matrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] solve(double[][] A, double[] b) {
                //nachdem man per spalten-pivotsuche eine obere-Dreecksmatrix erhalten hat -> backSubst(A,b) aufrufen!
                //also: return backSubst(A,b);
		//TODO: Diese Methode ist zu implementieren
                //doppelte for-Schleife k und i, wobei die i's immer nur größer sein dürfen als k:
                for(int k=0; k<b.length; k++){
                    
                    for(int i=k+1;i<b.length;i++){ //i-te Zeile
                        //hilfs-Vektor h aufstellen: k-te Spalte mal  a_ik / a_kk
                        for(int j=0;j<b.length;j++){ //j-te Spalte
                            //A_ij - h_j
                        }
                    }
                    //evtl k-te Zeile normiere, also dass 1. nicht-null Eintrag =1 ?
                }
		return new double[2];
	}

	/**
	 * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
	 * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
	 * 
	 * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt): 
	 * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung 
	 *  solange durch, bis in einem Schritt alle moeglichen Pivotelemente
	 *  numerisch gleich 0 sind (d.h. <1E-10) 
	 * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
	 *  loesen Sie Tx = -v durch Rueckwaertssubstitution 
	 * -Geben Sie den Vektor (x,1,0,...,0) zurueck
	 * 
	 * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
	 * In diesem Fall soll der 0-Vektor zurueckgegeben werden. 
	 * PARAMETER: 
	 * A: Eine singulaere Matrix der Groesse n x n 
	 */
	public static double[] solveSing(double[][] A) {
                //man macht so lange das mit der Pivotisierung, bis man an einer
                //Spalte angekommen ist, bei der ab der k-ten Zeile jeder Eintrag = 0 ist
                //sobald das passiert ist, muss man Rückwärtssubstitution machen, aber nur noch mit einer
                //Teilmatrix:
                //die ersten k-1 Zeilen & Spalten und b = k-te Spalte mit k-1 Zeilen
                //der Ergebnisvektor x wird jetzt noch verlänger, sodass
                //er gleich lang ist wie A und wird mit einer 1 und dann nur noch Nullen aufgefüllt.
		//TODO: Diese Methode ist zu implementieren
		return new double[2];
	}

	/**
	 * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
	 * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
	 * Gauss-Loesung
	 */
	public static double[] matrixVectorMult(double[][] A, double[] x) {
		int n = A.length;
		int m = x.length;

		double[] y = new double[n];

		for (int i = 0; i < n; i++) {
			y[i] = 0;
			for (int j = 0; j < m; j++) {
				y[i] += A[i][j] * x[j];
			}
		}

		return y;
	}
}
