import java.util.*;
import java.io.*;
import java.lang.*;

public class KmerEuclDistance {
	private Read r1;
	private Read r2;
	private String direction;
	
	public KmerEuclDistance(Read r1, Read r2) {
		this.r1=r1;
		this.r2=r2;
	}
	
	public double computeDistance() {
		KmerFrequencies kmf1=null;
		KmerFrequencies kmf2=null;
		kmf1 = this.r1.getKmerfreq();
		kmf2 = this.r2.getKmerfreq();
		Object[] frequencies1 = kmf1.getKmerfreq();
		Object[] frequencies2 = kmf2.getKmerfreq();
		double distance=0;
		for (int i=0; i<frequencies1.length; i++) {
			double vr1 = ((Integer)frequencies1[i]).intValue(); // r1.getLength() ;
			double vr2 = ((Integer)frequencies2[i]).intValue(); // r2.getLength();
			//System.out.println(vr1+" "+vr2);
			distance+= Math.pow((vr1-vr2),2);
		}
		distance = Math.sqrt(distance);
		//System.out.println("Distance "+distance);
		return distance;
	}	
}
