import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashSet;
import java.util.Set;


/** 
 * The ChemicalGeneONLYAssociationsFromPharmGKB program retrieves the association
 * betwen chemicals and genes ONLY from the file, relationships.tsv downloaded
 * from PharmGKB. 
 * 
 * @author Kalpana Raja
 *
 */

public class ChemicalGeneONLYAssociationsFromPharmGKB {
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String line="";
		int count=0;

		String arg1 = args[0]; //input_file - chemical-entities other than gene
		String arg2 = args[1]; //input_file - chemical-gene
		String arg3 = args[2]; //output_file - chemical-geneONLY
		
		Set<String> drugs = new LinkedHashSet<String>();
		
		try {
			FileInputStream fis0 = new FileInputStream(arg1);
			InputStreamReader isr0 = new InputStreamReader(fis0,"UTF-8");
		    BufferedReader br0 = new BufferedReader(isr0);
		    while((line = br0.readLine()) != null) {
		    	String[] arrLine = line.split("\t");
		    	if(arrLine[2].equals("Chemical")) drugs.add(arrLine[0]);
		    }
		    
		    FileInputStream fis = new FileInputStream(arg2);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
		    BufferedReader br = new BufferedReader(isr);
		         
		    FileOutputStream fos = new FileOutputStream(arg3);
		    OutputStreamWriter osr = new OutputStreamWriter(fos, "UTF-8");
		    BufferedWriter bw = new BufferedWriter(osr);
		       
			while((line = br.readLine()) != null) {
				if(line.startsWith("Entity1_id")) {
					bw.append(line);
					bw.append("\n");
					continue;
				}
				
				String[] arrLine = line.split("\t");
				if(arrLine[2].equals("Chemical")) {
					if(drugs.contains(arrLine[0])) continue;
					
					bw.append(line);
					bw.append("\n");
				}

				count++;
				//if(count==10) break;
				//if(count%1000==0) System.out.println(count);
			}
			br0.close();
			br.close();
			bw.close();
		} catch(IOException e) {
			System.err.println(e);
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Execution time in milliseconds: " + elapsedTime);
	}

}
