// Env.java
//package myfirst;
import javax.vecmath.Vector3d;
import simbad.sim.CherryAgent;
import simbad.sim.EnvironmentDescription;

import javax.vecmath.Point3d;
import simbad.sim.*;
import javax.vecmath.Vector3f;

/**
 *
 * @author Stylianos verros
 * @AEM 3272
 */

//Σε αυτήν την κλάση γίνεται η περιγραφή του περιβάλλοντος.
public class Env extends EnvironmentDescription {
    Env(){

        //Θέση Γραμμών.
        /*Line l0 = new Line(new Vector3d(-8, 0, 7), 1, this);
        l0.rotate90(1);
        add(l0);*/
        Line l1 = new Line(new Vector3d(-7, 0, -1), 8, this);
        l1.rotate90(2);
        add(l1);
        Line l2a = new Line(new Vector3d(-9, 0, -1), 4, this);
        l2a.rotate90(1);
        add(l2a);
        Line l2b = new Line(new Vector3d(-2, 0, -1), 10, this);
        l2b.rotate90(1);
        add(l2b);
        Line l3 = new Line(new Vector3d(7, 0, -1), 5, this);
        l3.rotate90(2);
        add(l3);
        /*Line l0 = new Line(new Vector3d(-8, 0, 7), 1, this);
        l0.rotate90(1);
        add(l0);
        Line l1 = new Line(new Vector3d(-7, 0, 6), 1, this);
        l1.rotate90(2);
        add(l1);
        Line l2 = new Line(new Vector3d(-7, 0, 4), 1, this);
        l2.rotate90(2);
        add(l2);
        Line l3 = new Line(new Vector3d(-7, 0, 2), 1, this);
        l3.rotate90(2);
        add(l3);
        Line l4 = new Line(new Vector3d(-7, 0, 0), 1, this);
        l4.rotate90(2);
        add(l4);
        Line l5 = new Line(new Vector3d(-8, 0, 0), 2, this);
        l5.rotate90(1);
        add(l5);
        Line l6 = new Line(new Vector3d(-5, 0, 0), 1, this);
        l6.rotate90(1);
        add(l6);
        Line l7 = new Line(new Vector3d(-3, 0, 0), 1, this);
        l7.rotate90(1);
        add(l7);
        Line l8 = new Line(new Vector3d(-1, 0, 0), 1, this);
        l8.rotate90(1);
        add(l8);
        Line l9 = new Line(new Vector3d(1, 0, 0), 1, this);
        l9.rotate90(1);
        add(l9);
        Line l10 = new Line(new Vector3d(3, 0, 0), 1, this);
        l10.rotate90(1);
        add(l10);
        Line l11 = new Line(new Vector3d(5, 0, 0), 2, this);
        l11.rotate90(1);
        add(l11);
        Line l12 = new Line(new Vector3d(7, 0, 0), 2, this);
        l12.rotate90(1);
        add(l12);
        Line l13 = new Line(new Vector3d(6, 0, 0), 1, this);
        l13.rotate90(2);
        add(l13);
        Line l14 = new Line(new Vector3d(6, 0, 2), 1, this);
        l14.rotate90(2);
        add(l14);
        Line l15 = new Line(new Vector3d(6, 0, 4), 1, this);
        l15.rotate90(2);
        add(l15);
        Line l16 = new Line(new Vector3d(6, 0, 6), 1, this);
        l16.rotate90(2);
        add(l16);*/


        //θέση Κάθετος τοίχος.
        this.wallColor = red;
        Wall w1 = new Wall(new Vector3d(-1, 0, 6), 8, 1, this);
        w1.rotate90(1);
        add(w1);

        //θέση Οριζόντιος τοίχος.
        Wall w2 = new Wall(new Vector3d(1.5, 0, 2), 5, 1, this);
        w2.rotate90(2);
        add(w2);

        //θέση εμποδίου BOX.
        this.boxColor = blue;
        add(new Box(new Vector3d(-3,0,-1), new Vector3f(2,2,2),this));

        //θέση ήλιου
        light1IsOn = true;
        light2IsOn = false;
        light1SetPosition(1, 2, 6);
        /*Έκανα έναν στόχο για να βρω την απόσταση που έχει το robot από τον στόχο και τι
         * ένταση φωτός έχουμε σε εκείνο το σημείο δλδ ο Ήλιος έχει την ίδια θέση με τον
         * στόχο(άπλα ο στόχος είναι στο έδαφος)
        Vector3d goal =   new Vector3d(6, 0, 5);
        add(new CherryAgent(goal,"goal",0.4f));*/

        //Θέση αψίδας στον κόσμο μας.
        //add(new CherryAgent(new Vector3d(1,0,2),"cherry",0.1f));;

        //Τήν θέση του robot "My robot" στο περιβάλλον.
        add(new MyRobot(new Vector3d(-8, 0, 7 ), "my robot"/*,goal*/));
    }
}
