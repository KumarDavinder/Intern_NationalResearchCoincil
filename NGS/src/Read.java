//package neobio.alignment;

import java.io.*;
import java.util.*;

public class Read implements Comparable {
	private String name;
	private String sequence;
	private String direction;
	private TreeSet<Integer> alignPos;
	private String chromosome;
	private int length;
	private int pos;
	private KmerFrequencies kmerfreq;
	
	public Read(String name, String sequence, String direction, int pos, String chromosome) {
		this.name=name;
		this.sequence=sequence.toUpperCase();
		this.direction=direction;
		this.pos=pos;
		this.length=sequence.length();
		this.alignPos=new TreeSet<Integer>();
		this.chromosome=chromosome;
		this.kmerfreq=new KmerFrequencies();
	}
	
	public void calculateKmer(int k) {
		this.kmerfreq.init(this.sequence,k);
		this.kmerfreq.calculate();
		//this.kmerfreq.printkmer();
	}
	
	public void addAlignedPosition(int alignPos) {
		this.alignPos.add(new Integer(alignPos));
	}
	public KmerFrequencies getKmerfreq() {
		return this.kmerfreq;
	}
	
	public int getLength() {
		return this.length;
	}
	public String getDirection() {
		return this.direction;
	}
	public String getSequence() {
		return this.sequence;
	}
	
	public String getName() {
		return this.name;
	}

	public String getChromosome() {
		return this.chromosome;
	}

	public TreeSet<Integer> getAlignPos() {
		return this.alignPos;
	}
	
	
	public String calculateCompRevSeq() {
		String r = new StringBuffer(sequence).reverse().toString();
		char[] app = r.toCharArray();
		for (int i=0; i<app.length; i++) {
			if (app[i]=='A') app[i]='T';
			else if (app[i]=='T') app[i]='A';
			else if (app[i]=='C') app[i]='G';
			else if (app[i]=='G') app[i]='C';
		}
		r = new String(app);
		return r;
	}
	
	public int compareTo(Object o) {
		Read r = (Read) o;
		return this.name.compareTo(r.name);
	}
	
	public boolean equals(Read r) {
		return (this.name.equals(r.name));
	}
	public String toString() {
		return this.name+" "+this.pos;
	}
	public String getPositions() {
		String positions="";
		for (Integer i : alignPos)
			positions+=""+i+" ";
		return positions;
	}
	public String toFasta() {
		return ">"+this.name+"\n"+this.sequence;
		
	}
	
}


