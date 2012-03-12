import java.io.Serializable;

public enum Planet implements Serializable {
	MERCURY (3.303e+23, 2.4397e6),
    VENUS   (4.869e+24, 6.0518e6),
    EARTH   (5.976e+24, 6.37814e6),
    MARS    (6.421e+23, 3.3972e6),
    JUPITER (1.9e+27,   7.1492e7),
    SATURN  (5.688e+26, 6.0268e7),
    URANUS  (8.686e+25, 2.5559e7),
    NEPTUNE (1.024e+26, 2.4746e7);

	private static int i=-1;
    private final double mass;   // in kilograms
    private final double radius; // in meters
    
    // universal gravitational constant  (m3 kg-1 s-2)
    public static final double G = 6.67300E-11;

    
    
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }
       
    public static Planet next() {
    	i++;
    	if (i<Planet.values().length) {
    		Planet p = Planet.values()[i];
     		return p;
    	}
    	else {
    		i=-1;
    		return null;
    	}
    }
    
    public double mass()   { return mass; }
    
    public double radius() { return radius; }

    
    public double surfaceGravity() {
        return G * mass / (radius * radius);
    }
    
    public double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }
    
    
    
    public static void main(String[] args) {
    	for (Planet p : Planet.values())
            System.out.printf("Planet %s : mass = %f     radius = %f%n",
                              p, p.mass(), p.radius());
    	
//        if (args.length != 1) {
//            System.err.println("Usage:  java Planet <earth_weight>");
//            System.exit(-1);
//        }
//        double earthWeight = Double.parseDouble(args[0]);
    	double earthWeight = Double.parseDouble("175");
        double mass = earthWeight/EARTH.surfaceGravity();
        for (Planet p : Planet.values())
           System.out.printf("Your weight on %s is %f%n",
                             p, p.surfaceWeight(mass));
    }

}
