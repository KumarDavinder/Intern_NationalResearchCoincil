import java.util.*;

public class KmerFrequencies {
	
	private TreeMap<String,Integer> kmerfreq;
	private String seq;
	private int k;
	
	public KmerFrequencies() {
		this.kmerfreq=new TreeMap<String,Integer>();
		
	}
	
		
	public void init(String seq, int k) {
		char[] a = new char[4];
		this.seq=seq;
		this.k=k;
		String ap="";
		a[0]='A';
		a[1]='C';
		a[2]='G';
		a[3]='T';
		generate(ap,a,this.k);
	}
	
	public Object[] getKmerfreq() {
		return this.kmerfreq.values().toArray();
	}
	
	public void printkmer() {
		Set<String> str = this.kmerfreq.keySet();
		for (String ss : str) {
			System.out.println(ss+" "+this.kmerfreq.get(ss));
		}
	}
	
	private void generate(String s, char[] a, int kk) {
		if(s.length()==kk) {
			kmerfreq.put(s,new Integer(0));
			//System.out.println(s);
			return;
		}
		for(int i=0; i<a.length; i++)
			generate(s+a[i],a,kk);
		}
 
	public void calculate() {
		for (int i=0; i<this.seq.length()-k; i++) {
			String substring = this.seq.substring(i,i+k);
			//System.out.println(substring);
			Integer freq = this.kmerfreq.get(substring);
			if (freq!=null)
				this.kmerfreq.put(substring,new Integer(freq.intValue()+1));
		}
	}
}
