
public class Gleitpunktzahl {

	/********************/
	/* Membervariablen: */
	/********************/

	/* Vorzeichen, Mantisse und Exponent der Gleitpunktzahl */
	public boolean vorzeichen; /* true = "-1" */
	public int exponent;
	public int mantisse;

	/*
	 * Anzahl der Bits fuer die Mantisse: einmal gesetzt, soll sie nicht mehr
	 * veraendert werden koennen
	 */
	private static int sizeMantisse = 32;
	private static boolean sizeMantisseFixed = false;

	/*
	 * Anzahl der Bits fuer dem Exponent: einmal gesetzt, soll sie nicht mehr
	 * veraendert werden koennen. Maximale Groesse: 32
	 */
	private static int sizeExponent = 8;
	private static boolean sizeExponentFixed = false;

	/*
	 * Aus der Anzahl an Bits fuer den Exponenten laesst sich der maximale
	 * Exponent und der Offset berechnen
	 */
	private static int maxExponent = (int) Math.pow(2, sizeExponent) - 1;
	private static int expOffset = (int) Math.pow(2, sizeExponent - 1) - 1;

	/**
	 * Falls die Anzahl der Bits der Mantisse noch nicht gesperrt ist, so wird
	 * sie auf abm gesetzt und gesperrt
	 */
	public static void setSizeMantisse(int abm) {
		/*
		 * Falls sizeMantisse noch nicht gesetzt und abm > 0 dann setze auf
		 * abm und sperre den Zugriff
		 */
		if (!sizeMantisseFixed & (abm > 0)) {
			sizeMantisse = abm;
			sizeMantisseFixed = true;
		}
	}

	/**
	 * Falls die Anzahl der Bits des Exponenten noch nicht gesperrt ist, so wird
	 * sie auf abe gesetzt und gesperrt. maxExponent und expOffset werden
	 * festgelegt
	 */
	public static void setSizeExponent(int abe) {
		if (!sizeExponentFixed & (abe > 0)) {
			sizeExponent = abe;
			sizeExponentFixed = true;
			maxExponent = (int) Math.pow(2, abe) - 1;
			expOffset = (int) Math.pow(2, abe - 1) - 1;
		}
	}

	/** Liefert die Anzahl der Bits der Mantisse */
	public static int getSizeMantisse() {
		return sizeMantisse;
	}

	/** Liefert die Anzahl der Bits des Exponenten */
	public static int getSizeExponent() {
		return sizeExponent;
	}

	/**
	 * erzeugt eine Gleitpunktzahl ohne Anfangswert. Die Bitfelder fuer Mantisse
	 * und Exponent werden angelegt. Ist die Anzahl der Bits noch nicht gesetzt,
	 * wird der Standardwert gesperrt
	 */
	Gleitpunktzahl() {
		sizeMantisseFixed = true;
		sizeExponentFixed = true;
	}

	/** erzeugt eine Kopie der reellen Zahl r */
	Gleitpunktzahl(Gleitpunktzahl r) {

		/* Vorzeichen kopieren */
		this.vorzeichen = r.vorzeichen;
		/*
		 * Kopiert den Inhalt der jeweiligen Felder aus r
		 */
		this.exponent = r.exponent;
		this.mantisse = r.mantisse;
	}

	/**
	 * erzeugt eine reelle Zahl mit der Repraesentation des Double-Wertes d. Ist
	 * die Anzahl der Bits fuer Mantisse und Exponent noch nicht gesetzt, wird
	 * der Standardwert gesperrt
	 */
	Gleitpunktzahl(double d) {

		this();
		this.setDouble(d);
	}

	/**
	 * setzt dieses Objekt mit der Repraesentation des Double-Wertes d.
	 */
	public void setDouble(double d) {

		/* Abfangen der Sonderfaelle */
		if (d == 0) {
			this.setNull();
			return;
		}
		if (Double.isInfinite(d)) {
			this.setInfinite(d < 0);
			return;
		}
		if (Double.isNaN(d)) {
			this.setNaN();
			return;
		}

		/* Falls d<0 -> Vorzeichen setzten, Vorzeichen von d wechseln */
		if (d < 0) {
			this.vorzeichen = true;
			d = -d;
		} else
			this.vorzeichen = false;

		/*
		 * Exponent exp von d zur Basis 2 finden d ist danach im Intervall [1,2)
		 */
		int exp = 0;
		while (d >= 2) {
			d = d / 2;
			exp++;
		}
		while (d < 1) {
			d = 2 * d;
			exp--;
		} /* d in [1,2) */
		
		this.exponent = exp + expOffset;

		/*
		 * Mantisse finden; fuer Runden eine Stelle mehr als noetig berechnen
		 */
		double rest = d;
		this.mantisse = 0;
		for (int i = 0; i <= sizeMantisse; i++) {
			this.mantisse <<= 1;
			if (rest >= 1) {
				rest = rest - 1;
				this.mantisse |= 1;
			}
			rest = 2 * rest;
		}
		this.exponent -= 1; /* Mantisse ist um eine Stelle groesser! */

		/*
		 * normalisiere uebernimmt die Aufgaben des Rundens
		 */
		this.normalisiere();
	}

	/** liefert eine String-Repraesentation des Objekts */
	public String toString() {
		if (this.isNaN())
			return "NaN";
		if (this.isNull())
			return "0";
			
		StringBuffer s = new StringBuffer();
		if (this.vorzeichen)
			s.append('-');
		if (this.isInfinite())
			s.append("Inf");
		else {
			for (int i = 32 - Integer.numberOfLeadingZeros(this.mantisse) - 1;
					i >= 0; i--) {
				if (i == sizeMantisse - 2)
					s.append(',');
				if (((this.mantisse >> i) & 1) == 1)
					s.append('1');
				else
					s.append('0');
			}
			s.append(" * 2^(");
			s.append(this.exponent);
			s.append("-");
			s.append(expOffset);
			s.append(")");
		}
		return s.toString();
	}

	/** berechnet den Double-Wert des Objekts */
	public double toDouble() {
		/*
		 * Wenn der Exponent maximal ist, nimmt die Gleitpunktzahl einen der
		 * speziellen Werte an
		 */
		if (this.exponent == maxExponent) {
			/*
			 * Wenn die Mantisse Null ist, hat die Zahl den Wert Unendlich oder
			 * -Unendlich
			 */
			if (this.mantisse == 0) {
				if (this.vorzeichen)
					return -1.0 / 0.0;
				else
					return 1.0 / 0.0;
			}
			/* Ansonsten ist der Wert NaN */
			else
				return 0.0 / 0.0;
		}
		double m = this.mantisse;
		if (this.vorzeichen)
			m *= (-1);
		return m
			* Math.pow(2, (this.exponent - expOffset)
					- (sizeMantisse - 1));
	}

	public boolean isNull() {
		return (!this.vorzeichen && this.mantisse == 0 && this.exponent == 0);
	}

	public boolean isNaN() {
		return (this.mantisse != 0 && this.exponent == maxExponent);
	}
 
	public boolean isInfinite() {
		return (this.mantisse == 0 && this.exponent == maxExponent);
	}

	/**
	 * vergleicht betragsmaessig den Wert des aktuellen Objekts mit der reellen
	 * Zahl r
	 */
	public int compareAbsTo(Gleitpunktzahl r) {
		/*
		 * liefert groesser gleich 1, falls |this| > |r|
		 * 0, falls |this| = |r|
		 * kleiner gleich -1, falls |this| < |r|
		 */

		/* Exponenten vergleichen */
		int expVergleich = this.exponent - r.exponent;

		if (expVergleich != 0)
			return expVergleich;

		/* Bei gleichen Exponenten: Bitweisses Vergleichen der Mantissen */
		return this.mantisse - r.mantisse;
	}

	/**
	 * normalisiert und rundet das aktuelle Objekt auf die Darstellung r =
	 * (-1)^vorzeichen * 1,r_t-1 r_t-2 ... r_1 r_0 * 2^exponent. Die 0 wird zu
	 * (-1)^0 * 0,00...00 * 2^0 normalisiert WICHTIG: Es kann sein, dass die
	 * Anzahl der Bits nicht mit sizeMantisse uebereinstimmt. Das Ergebnis
	 * soll aber eine Mantisse mit sizeMantisse Bits haben. Deshalb muss
	 * evtl. mit Bits aufgefuellt oder Bits abgeschnitten werden. Dabei muss das
	 * Ergebnis nach Definition gerundet werden.
	 * 
	 * Beispiel: Bei 3 Mantissenbits wird die Zahl 10.11 * 2^-1 zu 1.10 * 2^0
	 */
	public void normalisiere() {
		/*
		 * TODO: hier ist die Operation normalisiere zu implementieren.
		 * Beachten Sie, dass die Groesse (Anzahl der Bits) des Exponenten
		 * und der Mantisse durch sizeExponent bzw. sizeMantisse festgelegt
		 * ist.
		 * Achten Sie auf Sonderfaelle!
		 */
                if(this.isInfinite() || this.isNaN() || this.isNull()){
                    return;
                }
                
                int maxMantisse = (int)Math.pow(2,sizeMantisse)-1;
                int minMantisse = (int)Math.pow(2, sizeMantisse-1);
                double m = maxMantisse;
                m = m/mantisse;
                m = Math.log(m) / Math.log(0.5);
                int x = (int)Math.ceil(m);
                
                //mantisse wird angepasst, sodass sie immer mit 1,... beginnt
                //dabei wird auch der Exponent dann angepasst
                double z = mantisse/(Math.pow(2,x));
                mantisse = (int)Math.round(z);
                exponent+=x;
                
                //wenn der exponent seinen Maximalwert überschreitet, kann auch mit der Manisse nichts
                //ausgeglichen werden
                if(maxExponent == exponent){
                    if(mantisse !=0){
                        
                        this.setInfinite(vorzeichen);
                    }
                    else{
                        //theoretisch noch Abfrage, ob pos/neg Vorzeichen
                        //würde aber beides in Null resultieren.
                        this.setNull();
                    }
                }
                if(maxExponent< exponent){
                    this.setInfinite(vorzeichen);
                }
                else if(maxExponent<Math.abs(exponent)){
                    this.setNull();
                }
                
	}

	/**
	 * denormalisiert die betragsmaessig groessere Zahl, so dass die Exponenten
	 * von a und b gleich sind. Die Mantissen beider Zahlen werden entsprechend
	 * erweitert. Denormalisieren wird fuer add und sub benoetigt.
	 */
	public static void denormalisiere(Gleitpunktzahl a, Gleitpunktzahl b) {
                if(a.isNaN() || b.isNaN() || a.isNull() || b.isNull() || a.isInfinite() || b.isInfinite()){
                    return;
                }
                double ad = a.toDouble();
                double bd = b.toDouble();
                if(ad<0){
                    ad = -ad;
                }
                if(bd<0){
                    bd = -bd;
                }
                if(ad>bd){
                    //denormalisiere a
                    int diff = a.exponent - b.exponent;
                    a.exponent = b.exponent;
                    a.mantisse=a.mantisse*(int) (Math.pow(2,diff));
                    
                }
                else if(bd>ad){
                    //denormalisiere b
                    //falls Exponenten gleich groß sind wird alles mal 2^0, also mal 1 gerechnet, also sollte nichts passieren
                    int diff = b.exponent - a.exponent;
                    b.exponent = a.exponent;
                    b.mantisse=b.mantisse*(int) (Math.pow(2,diff));
                }
                
	}

	/**
	 * addiert das aktuelle Objekt und die Gleitpunktzahl r. Dabei wird zuerst
	 * die betragsmaessig groessere Zahl denormalisiert und die Mantissen beider
	 * zahlen entsprechend vergroessert. Das Ergebnis wird in einem neuen Objekt
	 * gespeichert, normiert, und dieses wird zurueckgegeben.
	 */
	public Gleitpunktzahl add(Gleitpunktzahl r) {
		/*
		 * TODO: hier ist die Operation add zu implementieren. Verwenden Sie die
		 * Funktionen normalisiere und denormalisiere.
		 * Achten Sie auf Sonderfaelle!
		 */
                Gleitpunktzahl summe = new Gleitpunktzahl();
                if(r.isNull()){
                    summe = new Gleitpunktzahl(this);
                    return summe;
                }
                if(this.isNull()){
                    summe = new Gleitpunktzahl(r);
                    return summe;
                }
                if(this.isNaN() || r.isNaN()){
                    summe.setNaN();
                    return summe;
                }
                if(this.isInfinite() && r.isInfinite()) {
                    if(this.vorzeichen!=r.vorzeichen){
                        summe.setNaN();
                        return summe;
                    }
                    else {
                      summe.setInfinite(this.vorzeichen);
                      return summe;
                    }
                }
                if(this.isInfinite()){
                    summe.setInfinite(this.vorzeichen);
                    return summe;
                }
                if(r.isInfinite()) {
                    summe.setInfinite(r.vorzeichen);
                    return summe;
                }
                
                denormalisiere(this,r);
                summe.exponent = this.exponent;
//                if(this.exponent != r.exponent){
//                    System.out.println("Da ist was beim Normalisieren schief gelaufen!");
//                }
                
                if(this.toDouble()<0 && r.toDouble()<0){
                    //Zahlen addieren und negatives Vorzeichen
                    summe.vorzeichen=true;
                    summe.mantisse = this.mantisse + r.mantisse;
                }
                else if(this.toDouble()<0){
                    
                    if(this.compareAbsTo(r)>=1){
                        //wenn this vom betrag größer als r ist, dann
                        //this minus r und negatives vorzeichen
                        summe.vorzeichen=true;
                        summe.mantisse = this.mantisse - r.mantisse;
                    }
                    else if(this.compareAbsTo(r)<=-1){
                        //wenn this vom betrag her kleiner ist als r, dann
                        //r minus this und positives Vorzeichen
                        summe.vorzeichen = false;
                        summe.mantisse = r.mantisse-this.mantisse;
                    }
                    else{
                        //wenn die Zahlen vom Betrag her gleich groß sind: 0
                        summe.setNull();
                    }
                }
                else if(r.toDouble()<0){
                    if(this.compareAbsTo(r)<=-1){
                        //wenn r vom betrag größer als this ist, dann
                        //r minus this und negatives vorzeichen
                        summe.vorzeichen = true;
                        summe.mantisse = r.mantisse - this.mantisse;
                    }
                    else if(this.compareAbsTo(r)>=1){
                        //wenn r vom betrag her kleiner ist als this, dann
                        //this minus r und positives Vorzeichen
                        summe.mantisse = this.mantisse - r.mantisse;
                        summe.vorzeichen = false;
                    }
                    else{
                        //wenn die Zahlen vom Betrag her gleich groß sind: 0
                        summe.setNull();
                    }
                }
                else{
                    summe.mantisse = this.mantisse + r.mantisse;
                }
                
                summe.normalisiere();
                this.normalisiere();
                r.normalisiere();
                
		return summe;
	}

	/**
	 * subtrahiert vom aktuellen Objekt die Gleitpunktzahl r. Dabei wird zuerst
	 * die betragsmaessig groessere Zahl denormalisiert und die Mantissen beider
	 * zahlen entsprechend vergroessert. Das Ergebnis wird in einem neuen Objekt
	 * gespeichert, normiert, und dieses wird zurueckgegeben.
	 */
	public Gleitpunktzahl sub(Gleitpunktzahl r) {
		/*
		 * TODO: hier ist die Operation sub zu implementieren. Verwenden Sie die
		 * Funktionen normalisiere und denormalisiere.
		 * Achten Sie auf Sonderfaelle!
		 */
            r.vorzeichen = ! r.vorzeichen;
            Gleitpunktzahl res = this.add(r);
            r.vorzeichen = ! r.vorzeichen;
            return res;
	}
	
	/**
	 * Setzt die Zahl auf den Sonderfall 0
	 */
	public void setNull() {
		this.vorzeichen = false;
		this.exponent = 0;
		this.mantisse = 0;
	}
	
	/**
	 * Setzt die Zahl auf den Sonderfall +/- unendlich
	 */
	public void setInfinite(boolean vorzeichen) {
		this.vorzeichen = vorzeichen;
		this.exponent = maxExponent;
		this.mantisse = 0;
	}
	
	/**
	 * Setzt die Zahl auf den Sonderfall NaN
	 */
	public void setNaN() {
		this.vorzeichen = false;
		this.exponent = maxExponent;
		this.mantisse = 1;
	}
}
