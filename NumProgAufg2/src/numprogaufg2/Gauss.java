

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
                for(int i=b.length-1; i>=0;i--){ //for-Schleife um jede Zeile x_i absteigend zu bestimmen
                    if(R[i][i]==0){
                        System.out.println("ACHTUNG!");
                    }
                    double sum = 0;
                    for(int j=0; j<b.length; j++){
                        if(j!=i){
                            sum += R[i][j] * x[j];
                        }
                    }
                    if(b[i]-sum==0){
                        System.out.println("LGS nicht eindeutig lösbar!");
                    }
                    x[i] = (b[i] - sum)/R[i][i];
                    //n b(zw. i)-te Zeile, j-te Spalte:
                    //b_n - (alle j!=n : a_nj* x_j //also minus alle anderen Elemente in der Zeile mit ihrem jeweiligen x mal-genommen
                }
		return x;
	}

	/**
	 * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
	 * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden. 
	 * PARAMETER: A:
	 * Eine regulaere Matrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] solve(double[][] A, double[] b) {
                double[][] X = new double[b.length][b.length];
                double[] b_help = new double[b.length];
                for(int i=0; i<b.length; i++){
                    for(int j=0; j<b.length; j++){
                        X[i][j] = A[i][j];
                    }
                    b_help[i] = b[i];
                }
                for(int k=0; k<(b.length-1);k++){
                    //für jede Zeile (bis auf die letzte -> trivial:
                    //finde größte Zahl ab Zeile k in Spalte k.
                    int merker = k;
                    for(int j = k+1; j<b.length;j++){
                        if(Math.abs(X[j][k]) > Math.abs(X[merker][k])){
                            merker = j;
                        }
                    }
                    if(merker!=k){
                        //tausche k-te mit merker-Zeile
                        double h = 0;
                        h = b_help[k];
                        b_help[k] = b_help[merker];
                        b_help[merker] = h; 
                        for(int i=0; i<b.length;i++){
                            h = X[k][i];
                            X[k][i] = X[merker][i];
                            X[merker][i] = h;
                        }
                    }
                    //nun steht in der k-ten Spalte in der k-ten Zeile das kleinste Element dieser Spalte
                    if(X[k][k] == 0){
                        System.out.println("Das Element x_k,k = 0, also kann nicht weitergerechnet werden!");
                    }
                    else{
                    for(int i=k+1; i<b.length;i++){
                        //nun nullt man alle Zahlen in der k-ten Spalte unterhalb der k-ten Zeile
                        double vorfaktor = X[i][k] / X[k][k];
                        for(int j = k; j<b.length;j++){
                            //dafür muss man die ganze Zeile ab dem k-ten Element durchlaufen
                            X[i][j] = X[i][j] - vorfaktor * X[k][j];
                        }
                        b_help[i] =b_help[i]- vorfaktor*b_help[k];
                        
                    }
                    }
                }
                return backSubst(X,b_help);  
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
                double[][] X = new double[A.length][A.length];
                double[] b = new double[A.length];
                for(int i=0; i<b.length; i++){
                    for(int j=0; j<b.length; j++){
                        X[i][j] = A[i][j];
                    }
                    //b[i] = 0; wird schon per default mit Nullen gefüllt
                }
                int merker = 0;
                int k = 0;
                for(k=0; k<(b.length-1);k++){
                    //für jede Zeile (bis auf die letzte -> trivial:
                    //finde größte Zahl ab Zeile k in Spalte k.
                    merker = k;
                    for(int j = k+1; j<b.length;j++){
                        if(Math.abs(X[j][k]) > Math.abs(X[merker][k])){
                            merker = j;
                        }
                    }
                    if(Math.abs(X[merker][k]) < 1E-10){
                        break;
                    }
                    else if(merker!=k){
                        //tausche k-te mit merker-Zeile
                        double h = 0;
                        h = b[k];
                        b[k] = b[merker];
                        b[merker] = h; 
                        for(int i=0; i<b.length;i++){
                            h = X[k][i];
                            X[k][i] = X[merker][i];
                            X[merker][i] = h;
                        }
                    }
                    //nun steht in der k-ten Spalte in der k-ten Zeile das kleinste Element dieser Spalte
                    if(X[k][k] == 0){
                        //System.out.println("Das Element x_k,k = 0, also kann nicht weitergerechnet werden!");
                    }
                    else{
                    for(int i=k+1; i<b.length;i++){
                        //nun nullt man alle Zahlen in der k-ten Spalte unterhalb der k-ten Zeile
                        double vorfaktor = X[i][k] / X[k][k];
                        for(int j = k; j<b.length;j++){
                            //dafür muss man die ganze Zeile ab dem k-ten Element durchlaufen
                            X[i][j] = X[i][j] - vorfaktor * X[k][j];
                        }
                        b[i] =b[i]- vorfaktor*b[k];
                        
                    }
                    }
                }
                if(merker == (b.length-1) && X[merker][k] != 0 ) {
                    //der Nullvektor wird zurückgegeben, was aber wenn X[merker][k] == 0, dann
                    //handelt es sich um eine nicht eindeutig bestimmbare Matrix...
                    return b;
                }
                double[][] T = new double[k][k];
                double[] v = new double[k];
                for(int i=0; i<k; i++){
                    for(int j=0; j<k; j++){
                        T[i][j] = X[i][j];
                    }
                    v[i] =- X[i][k]; //i-te Zeile, k-te Spalte
                }
                double[] x = backSubst(T,v);
                double[] p = new double[X.length];
                for(int i=0; i<k; i++){
                    p[i] = x[i];
                }
                p[k] = 1;
                for(int i = k+1; i<p.length;i++){
                    p[i] = 0;
                }
                return p;
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
