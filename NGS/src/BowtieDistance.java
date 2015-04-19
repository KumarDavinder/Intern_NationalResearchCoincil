
import java.util.*;
import java.io.*;
import java.lang.*;

public class BowtieDistance {
	private Read r1;
	private Read r2;
		
	public BowtieDistance(Read r1, Read r2) {
		this.r1=r1;
		this.r2=r2;
	}
	
	public double computeDistance() {
		int numPosOverlap=0;
		int numOverlapRegions=0;
		double distance=-1;
		TreeSet<Integer> positionsR1=null;
		TreeSet<Integer> positionsR2=null;
		positionsR1=r1.getAlignPos();
		positionsR2=r2.getAlignPos();
	
		if (this.r1.equals(this.r2))
			return 0.0;
		if (this.r1.getDirection().equals("+") && this.r2.getDirection().equals("-"))
			return 1.0;
		if (this.r1.getDirection().equals("-") && this.r2.getDirection().equals("+"))
			return 1.0;
		if (!this.r1.getChromosome().equals(this.r2.getChromosome())) 
			return 1.0;		
			
			
		for (Integer pos1 : positionsR1) {
			int head1 = pos1.intValue();
			int tail1 = head1+r1.getLength();
			for (Integer pos2 : positionsR2) {
				int head2 = pos2.intValue();
				int tail2 = head2+r2.getLength();
				int tmp = Math.min(tail1,tail2) - Math.max(head1,head2);
				//System.out.println("HEAD1 HEAD2 TAIL1 TAIL2 OVERLAP "+head1+" "+head2+" "+tail1+" "+tail2+" "+tmp);
				if (tmp<0) tmp=0;
				else if (tmp>0) numOverlapRegions++;
				numPosOverlap+=tmp;
			}
		}
		//1 - {2*(numero di posizioni sovrapposte tra read1 e read2) / (numero di volte in cui si allinea)*(lunghezza read1 + lunghezza read2)}
		double denominator=numOverlapRegions * (r1.getLength()+r2.getLength());
		if (denominator==0)
			//distance=-1;
			distance=1;
		else
			//distance = ((2.0*numPosOverlap)/denominator);
			distance = 1 - ((2.0*numPosOverlap)/denominator);
		return distance;
	}
}
