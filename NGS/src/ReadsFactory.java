//package neobio.alignment;


import java.util.*;
import java.io.*;
import java.lang.Math;

public class ReadsFactory {

	public static void generateReads(String file) {
		try {
			FileReader fin=new FileReader(file);
			BufferedReader reader=new BufferedReader(fin);
			TreeMap<String,Read> mr = new TreeMap<String,Read>();
			String delimiter="\\s+";
			int seqCounter=0;
			String s="";
			while ((s=reader.readLine())!=null ) {
				String[] fields=s.split(delimiter);
				String name=fields[0]; //0
				String direction=fields[1]; //3
				String chromosome="1"; //fields[2]; //4

				//String alignPosition=fields[3];
				int alignPosition=Integer.parseInt(fields[3]); //5
				String sequence=fields[4]; //9//6

				if (direction.equals("0")) direction="+";
				else if (direction.equals("16")) direction="-";

				if (direction.equals("+")) {
					String uniqName=name+"_"+direction;
					Read r = null;
					r = mr.get(uniqName);
					if (r==null) {
						r = new Read(uniqName,sequence,direction,seqCounter,chromosome);
						mr.put(uniqName,r);
						seqCounter++;

					}
					r.addAlignedPosition(alignPosition);
					String direction2="-";
					Read r2 = null;
					String uniqName2=name+"_"+direction2;
					r2 = mr.get(uniqName2);
					if (r2==null) {
						//sequence=new StringBuffer(sequence).reverse().toString();
						sequence=r.calculateCompRevSeq();
						r2 = new Read(uniqName2,sequence,direction2,seqCounter,chromosome);
						mr.put(uniqName2,r2);
						seqCounter++;
					}
				}
				else if (direction.equals("-")) {
					String uniqName=name+"_"+direction;
					Read r = null;
					r = mr.get(uniqName);
					if (r==null) {
						//sequence=new StringBuffer(sequence).reverse().toString();
						r = new Read(uniqName,sequence,direction,seqCounter,chromosome);
						mr.put(uniqName,r);
						seqCounter++;

					}
					r.addAlignedPosition(alignPosition);
					String direction2="+";
					Read r2 = null;
					String uniqName2=name+"_"+direction2;
					r2 = mr.get(uniqName2);
					if (r2==null) {
						//sequence=new StringBuffer(sequence).reverse().toString();
						sequence=r.calculateCompRevSeq();
						r2 = new Read(uniqName2,sequence,direction2,seqCounter,chromosome);
						mr.put(uniqName2,r2);
						seqCounter++;
					}
				}
			}
			reader.close();
			fin.close();
			Collection<Read> rs = mr.values();
			//System.out.println(rs.size());
			for (Read rsi1 : rs) {
				rsi1.calculateKmer(4);
				//System.err.println("Kmer calculated");
				//System.out.println(rsi1.getName()+","+rsi1.getSequence()+","+rsi1.getPositions());
				//System.out.print(rsi1.getPositions());
				//System.out.println(rsi1.getPositions());
			}

			System.out.println("SEQ1,SEQ2,BOWTIEDISTANCE,NEEDLEMANWUNSCH,BLAST2SCORE,BLAST2EVALUE,ALIGNMENTFREE");
			for (Read rsi1 : rs) {
				for (Read rsi2 : rs) {
					//System.out.println(fsc);
					//fsc.writeFastaFile();
					//System.out.println(rsi);
					//System.out.println(rsi.getPositions("+"));

					if (rsi1.getName().equals(rsi2.getName())) continue;



					//Bowtie distance
					BowtieDistance bd = new BowtieDistance(rsi1,rsi2);
					double bowDist= bd.computeDistance();
					if (bowDist==-1) continue;

					/* campionamento random */

					// il random si imposta in percentuale se voglio il es 100 readsdiventano 200*200=40000, se voglio ca. 4000 reads (10%) imposto a >0.10
					//if (bowDist>0.9999 && Math.random()>=0.000007) continue; //0.000007 per 100k ; 0.000010 per 50k

					System.out.print(rsi1.getName()+","+ rsi2.getName()+",");

					System.out.print(bowDist+",");

					//Needleman Wunsch
					PairwiseAlignmentAlgorithm algorithm = new NeedlemanWunsch();
					ScoringScheme scoring = new BasicScoringScheme (1, -1, -1);
					algorithm.setScoringScheme(scoring);
					algorithm.loadSequences(rsi1.getSequence(), rsi2.getSequence());
					int score = algorithm.getScore();
					System.out.print(score+",");


					//BLAST2 distance
					//					Blast2Distance b2d=new Blast2Distance(rsi1,rsi2);
					//					System.out.print(b2d.computeDistance()+",");
					//					

					//Kmer Euclidean Distance
					KmerEuclDistance ked=new KmerEuclDistance(rsi1,rsi2);
					System.out.print(ked.computeDistance());
					System.out.println();
				}
			}

		}
		catch(Exception e) {
			System.err.println(file);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ReadsFactory.generateReads(args[0]);
	}
}
