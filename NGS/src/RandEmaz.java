import java.lang.Math;

public class RandEmaz {
	
	public static void main(String[] args) {
		int taken=0;
		for (int i=0; i<100000; i++) {
			double x = Math.random();
			if (x<0.0007) taken++;
		}
		System.out.println(taken);
	}
}
