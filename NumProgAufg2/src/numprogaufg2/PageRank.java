import java.util.Arrays;
import java.util.Comparator;

public class PageRank {

  /**
   * Diese Methode erstellt die Matrix A~ fuer das PageRank-Verfahren
   * PARAMETER: 
   * L: die Linkmatrix (s. Aufgabenblatt) 
   * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
   *      zufaellig irgendeine Seite zu besuchen
   */
  public static double[][] buildProbabilityMatrix(int[][] L, double rho) {
    //a~_ij = (1-p) * a_ij + p/n --- wobei n = #Spalten bzw. Zeilen da A eine nxn Matrix
    //also eine doppelte for-Schleife
    //TODO: Diese Methode ist zu implementieren
    double[][] A = new double[L.length][L.length];
    double links = 0;
    // Loop over all possible transitions
    for(int i = 0; i < L.length; i++) {
      for(int j = 0; j < L.length; j++) {
        if(L[i][j] == 1) {
          // If transition exists
          // Count the outgoing links from j
          links = 0;
          for(int k = 0; k < L.length; k++) {
            links += L[k][j];
          }
          A[i][j] = (1 - rho) * (1 / links) + (rho / L.length);
        } else {
          // If transition does not exist
          A[i][j] = (rho / L.length);
        }
      }
    }
    return A;
  }

  /**
   * Diese Methode berechnet die PageRanks der einzelnen Seiten,
   * also das Gleichgewicht der Aufenthaltswahrscheinlichkeiten.
   * (Entspricht dem p-Strich aus der Angabe)
   * Die Ausgabe muss dazu noch normiert sein.
   * PARAMETER:
   * L: die Linkmatrix (s. Aufgabenblatt) 
   * rho: Wahrscheinlichkeit, zufaellig irgendeine Seite zu besuchen
   * ,anstatt einem Link zu folgen.
   *      
   */
  public static double[] rank(int[][] L, double rho) {
    //
    //TODO: Diese Methode ist zu implementieren
    double[][] A = PageRank.buildProbabilityMatrix(L, rho);
    // A - I
    for(int i = 0; i < A.length; i++) {
      A[i][i] = A[i][i] - 1;
    }
    double[] p = Gauss.solveSing(A);
    double lam = 0;
    // lam = 1 / sum lam
    for(int i = 0; i < p.length; i++) {
      lam += p[i];
    }
    lam = 1 / lam;
    for(int i = 0; i < p.length; i++) {
      p[i] *= lam;
    }
    return p;
  }

  /**
   * Diese Methode erstellt eine Rangliste der uebergebenen URLs nach
   * absteigendem PageRank. 
   * PARAMETER:
   * urls: Die URLs der betrachteten Seiten
   * L: die Linkmatrix (s. Aufgabenblatt) 
   * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
   *      zufaellig irgendeine Seite zu besuchen
   */ 
  public static String[] getSortedURLs(String[] urls, int[][] L, double rho) {
    int n = L.length;

    double[] p = rank(L, rho);

    RankPair[] sortedPairs = new RankPair[n];
    for (int i = 0; i < n; i++) {
      sortedPairs[i] = new RankPair(urls[i], p[i]);
    }

    Arrays.sort(sortedPairs, new Comparator<RankPair>() {

      @Override
      public int compare(RankPair o1, RankPair o2) {
        return -Double.compare(o1.pr, o2.pr);
      }
    });

    String[] sortedUrls = new String[n];
    for (int i = 0; i < n; i++) {
      sortedUrls[i] = sortedPairs[i].url;
    }

    return sortedUrls;
  }

  /**
   * Ein RankPair besteht aus einer URL und dem zugehoerigen Rang, und dient
   * als Hilfsklasse zum Sortieren der Urls
   */
  private static class RankPair {
    public String url;
    public double pr;

    public RankPair(String u, double p) {
      url = u;
      pr = p;
    }
  }
}
