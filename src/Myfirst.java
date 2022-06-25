// Myfirst.java
//package myfirst;
import simbad.gui.Simbad;
/**
 *
 * @author Stylianos verros
 * @AEM 3272
 */
public class Myfirst {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Δημιουργούμε ένα νέο αντικείμενο Simbad στο οποίο του περνάμε τι περιγραφή του κόσμου μας.
        Simbad frame = new Simbad(new Env() ,false);    }
}
