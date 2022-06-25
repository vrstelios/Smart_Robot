// MyRobot.java
//package myfirst;

import simbad.sim.RangeSensorBelt;
import javax.vecmath.Vector3d;
import simbad.sim.*;

/**
 *
 * @author Stylianos verros
 * @AEM 3272
 */

//Σε αυτήν την κλάση γίνεται η περιγραφή του Ρομπότ.
public class MyRobot extends Agent {
    LightSensor left , right;
    RangeSensorBelt bumpers;
    RangeSensorBelt sonars;
    LineSensor line;
    //Vector3d goal;
    boolean StopRobot = false;
    boolean  CLOCKWISE;

    public MyRobot (Vector3d position, String name/*,Vector3d goal*/) {
        super(position,name);
       // this.goal = goal;
        //Δηλώνω sensor.
        line = RobotFactory.addLineSensor(this,12);//Πόσους sensor line έχει το robot μου.
        left= RobotFactory.addLightSensorLeft(this);//Έναν sensor light.
        right = RobotFactory.addLightSensorRight(this);//Έναν sensor light.
        bumpers=RobotFactory.addBumperBeltSensor(this, 8);//Πόσους sensor bumpers έχει το robot μου.
        sonars=RobotFactory.addSonarBeltSensor(this, 12);//Πόσους sensor sonar έχει το robot μου.
   }
    public void initBehavior() {
        //Για να κινητέ το robot.
        //setRotationalVelocity(1);//Καθορίζει τη ταχύτητα περιστροφής.
        //setTranslationalVelocity(-1);//Καθορίζει τη γραμμική ταχύτητα.
    }

    public void performBehavior() {
        /*μέτρησης για να βρω την έντασης του φωτός
        Vector3d lg = getLocalCoords(goal);//Στόχος σε Ρομποκ. Συντετ.
        double  dist = lg.length();// Η απόσταση του ρομπότ από το στόχο
        System.out.println("verros "+dist);
        System.out.println(left.getLux());
        System.out.println(right.getLux());
        if(dist<=0.6){
            System.out.println("verrosl "+left.getLux());
            System.out.println("verrosR "+right.getLux());
        }*/
        /**Line Sensor*/
        int leftL = 0, rightL = 0;
        float k = 0;
        for (int i = 0; i < line.getNumSensors() / 2; i++) {
            leftL += line.hasHit(i) ? 1 : 0;
            rightL += line.hasHit(line.getNumSensors() - i - 1) ? 1 : 0;
            k++;
        }
        /**
         * εχω παρα πολυ μικρη ταχυτητα στο μπορω πανω στην γραμμη για να εχω περισσοτερο χρονο αντιγραδης το ρομποτ
         * συνιστατε να ειναι *20 οταν πανω στην γραμμη.
         * */
        setTranslationalVelocity(0.2);//Καθορίζει την ταχύτητα κίνησης πάνω στην γραμμή.
        if (leftL == 0 && rightL == 0) {//ελέγχω αν υπάρχει γραμμή κάτω από το ρομπότ.
            /**Light Sensor*/
            double threshold = Math.abs(left.getLux() - right.getLux());//βρίσκω το κατώφλι της ευθείας με τον Ήλιο.
            double follow_sun = left.getLux() - right.getLux();//που κοιτάει το ρομπότ.
            if (threshold > follow_sun) {//το κάνω να κινητέ προς τον ήλιο(στόχο).
                setRotationalVelocity(-(right.getAverageLuminance() - left.getAverageLuminance()));
            } else {
                setRotationalVelocity((right.getAverageLuminance() - left.getAverageLuminance()));
            }
            obstacle_area();//καλώ την συνάρτηση για να ξεπεράσω εμπόδια.
            for (int i=1;i<sonars.getNumSensors();i+=3){
                if(sonars.hasHit(i) &&  rightL==1){//Όταν ξανά βρω την γραμμή ελέγχω το sonars 4 για το κάνω να ακολούθηση την γραμμή.
                    setTranslationalVelocity(0);
                    setRotationalVelocity(Math.PI/2);
                }
                if(sonars.hasHit(i) && leftL==1 ){
                    setTranslationalVelocity(0);
                    setRotationalVelocity(-Math.PI/2);
                }
            }
            if ((left.getLux() + right.getLux()) / 2 > 0.06){//για να σταματηση το ρομποτ
                StopRobot = true;
                setTranslationalVelocity(0);
            }else {
                setTranslationalVelocity(1);//Καθορίζει την ταχύτητα κίνησης έκτος της γραμμής.
            }
        } else if (leftL == 1 && rightL == 1) {//ελέγχω αν υπάρχει γραμμή κάτω από το ρομπότ(δεξιά ή αριστερά).
            /**Light Sensor*/
            //Το ρομπότ κινητέ εκεί που υπάρχει περισσότερο φως στους sonars.
            if (left.getAverageLuminance() > right.getAverageLuminance()) {
                CLOCKWISE=true;//Καθορίζει από ποια πλευρά θα ξεπεράσει το εμπόδιο το ρομπότ.
                setTranslationalVelocity(0.1);
                setRotationalVelocity(-Math.PI/2);
            } else {
                CLOCKWISE=false;
                setTranslationalVelocity(0.1);
                setRotationalVelocity(Math.PI/2);
            }
            if ((left.getLux() + right.getLux()) / 2 > 0.06){//για να σταματηση το ρομποτ
                StopRobot = true;
                setTranslationalVelocity(0);
            }
        } else {
            setTranslationalVelocity(0.2);
            setRotationalVelocity((leftL - rightL) / k * 5);//για να στρίψει το ρομπότ πάνω στην γραμμή.
        }
        /**Light Sensor*/
        if ((left.getLux() + right.getLux()) / 2 > 0.06) {//ελέγχω την ακτίνα του φωτός για να σταματήσει το ρομπότ.
            //Σταματάω το ρομπότ σε απόσταση 0,6 από τον ήλιο.
            StopRobot = true;
        }else if((left.getLux() + right.getLux()) / 2 <= 0.04){
            StopRobot =false;
        }
        if (StopRobot) {
            setRotationalVelocity(0);
            setTranslationalVelocity(0);
        }
        /**Bumper Sensor Belt*/
        for (int i=0;i<bumpers.getNumSensors();i++) {
            if (bumpers.hasHit(i)) {//ελέγχω αν χτυπάει κάπου το ρομπότ μου.
                setTranslationalVelocity(-1);//αμα χτυπήσει οπισθοχωρώ για λίγα μετρά.
                SimpleBehaviors.circumNavigate(this,bumpers,CLOCKWISE);//κάλο την συνάρτηση περιφερής.
            }
        }
    }
    //Αυτήν η συνάρτηση έγινε για να θρήσκο αυτά που χρειάζονται ώστε να καλέσω στην συνάρτηση περιφερής
    //του εμποδίου .Αρα έμμεσα από εδώ ξεκινάει η περιφορά.
    public void  obstacle_area(){
        int minD=0 ;
        for (int i=1;i<sonars.getNumSensors();i++){
            if (sonars.getMeasurement(i)<sonars.getMeasurement(minD))
                minD=i;
        }
        if(sonars.hasHit(minD)){
            SimpleBehaviors.circumNavigate(this,sonars,CLOCKWISE);
        }

    }

    /**Αυτές οι δυο συναρτήσεις getLocalCoords , getAngle() χρησιμοποιήθηκαν
     * μονό και μονό για να μετρήσω την ένταση του φωτός στα 0,6 μετρά.*/
    /*public Vector3d getLocalCoords(Vector3d p)
    {
        Vector3d a = new Vector3d();
        Point3d r = new Point3d();
        double th = getAngle();
        double x,y,z;
        getCoords(r);
        x=p.getX() - r.x;
        z=-p.getZ()+ r.z;
        a.setX(x*Math.cos(th) + z*Math.sin(th));
        a.setZ(z*Math.cos(th) - x*Math.sin(th));
        a.setY(p.y);
        return a;
    }
    public double getAngle()
    {
        double angle=0;
        double msin;
        double mcos;
        Transform3D m_Transform3D=new Transform3D();
        this.getRotationTransform(m_Transform3D);
        Matrix3d m1 = new Matrix3d();
        m_Transform3D.get( m1 );
        msin=m1.getElement( 2, 0 );
        mcos=m1.getElement( 0, 0 );
        if (msin<0)
        {
            angle = Math.acos(mcos);
        }
        else
        {
            if (mcos<0)
            {
                angle = 2*Math.PI-Math.acos(mcos);
            }
            else
            {
                angle = -Math.asin(msin);
            }
        }
        while (angle<0)
            angle+=Math.PI*2;
        return angle;
    }*/
}
