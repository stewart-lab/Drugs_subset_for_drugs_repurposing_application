import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/** 
 * The ChemicalVariantORHaplotypeAssociationsFromPharmGKB program retrieves 
 * the association betwen chemicals and variants/haplotypes from the file, 
 * relationships.tsv downloaded from PharmGKB. 
 * 
 * @author Kalpana Raja
 *
 */

public class ChemicalVariantORHaplotypeAssociationsFromPharmGKB {
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String line="";
		int count=0;

		String arg1 = args[0]; //input_file
		String arg2 = args[1]; //output_file
		
		try {
			FileInputStream fis = new FileInputStream(arg1);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
		    BufferedReader br = new BufferedReader(isr);
		         
		    FileOutputStream fos = new FileOutputStream(arg2);
		    OutputStreamWriter osr = new OutputStreamWriter(fos, "UTF-8");
		    BufferedWriter bw = new BufferedWriter(osr);
		       
			while((line = br.readLine()) != null) {
				if(line.startsWith("Entity1_id")) {
					bw.append(line);
					bw.append("\n");
					continue;
				}
				
				String[] arrLine = line.split("\t");
				if(arrLine[2].equals("Chemical") && 
						(arrLine[5].equals("Variant") || arrLine[5].equals("Haplotype"))) {
					bw.append(line);
					bw.append("\n");
				}

				count++;
				//if(count==10) break;
				//if(count%1000==0) System.out.println(count);
			}
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
