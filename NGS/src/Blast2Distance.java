import java.util.*;
import java.io.*;
import java.lang.*;

public class Blast2Distance {
	private Read r1;
	private Read r2;
		
	public Blast2Distance(Read r1, Read r2) {
		this.r1=r1;
		this.r2=r2;
	}
	
	public String computeDistance() throws Exception {
		
		writeFasta(r1, "r1.fas");
		writeFasta(r2, "r2.fas");
		
        
        Process proc=null;
        Runtime r=Runtime.getRuntime();
        proc=r.exec(new String[] {"blast2", "-p", "blastn", "-F", "F", "-w", "4", "-i", "r1.fas", "-j", "r2.fas", "-m", "8"});
        proc.waitFor();
		final BufferedReader stdOut = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		final BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		final BufferedWriter stdIn = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
		
		String res = stdOut.readLine();
		stdOut.close();
		stdErr.close();
		stdIn.close();
        r=Runtime.getRuntime();
        
        
        
        
        String score="0"; 
        String evalue="10";
        if (res==null || res.equals("")) {
			score="0";
			evalue="10";
		}
		else {
			String delimiter="\\s+";
			String[] fields=res.split(delimiter);
			score = fields[11];
			evalue = fields[10];
		}
        
        return score+","+evalue;
        
	}
	
	public void writeFasta(Read r, String name)  {
		try { 
			FileWriter fw = new FileWriter(name);
			BufferedWriter fbw = new BufferedWriter(fw);
			fbw.write(r.toFasta());
			fbw.close();
			fw.close();		
		}
		catch (Exception e) {
			e.printStackTrace();
        }
	}
}
