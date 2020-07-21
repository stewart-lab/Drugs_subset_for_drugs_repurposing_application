import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


/** 
 * The DrugIDAssigner program gets drug ID for a list of drugs from our drugs
 * lexicon. The main resources of our drugs lexicon are UMLS Metathesaurus, 
 * DrugBank and PharmGKB.
 * 
 * @author Kalpana Raja
 *
 */
public class DrugIDAssigner {
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String line="";
		int count=0;

		String arg1 = args[0]; //input_file -- drug/biologics lexicon
		String arg2 = args[1]; //input_file
		String arg3 = args[2]; //output_file
		
		HashMap<String, String> drugAndID = new LinkedHashMap<String, String>();
		ArrayList<String> output = new ArrayList<String>();
		
		try {
			//drug/biologics lexicon
			FileInputStream fis0 = new FileInputStream(arg1);
			InputStreamReader isr0 = new InputStreamReader(fis0,"UTF-8");
		    BufferedReader br0 = new BufferedReader(isr0);
		    while((line = br0.readLine()) != null) {
		    	if(line.startsWith("drug")) continue;
                String[] arrLine = line.split("\t");
		    	drugAndID.put(arrLine[0].toLowerCase(), arrLine[1]);
		    }
			
			FileInputStream fis = new FileInputStream(arg2);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
		    BufferedReader br = new BufferedReader(isr);
		         
		    FileOutputStream fos = new FileOutputStream(arg3);
		    OutputStreamWriter osr = new OutputStreamWriter(fos, "UTF-8");
		    BufferedWriter bw = new BufferedWriter(osr);
		    
		    output.add("drug\tdrug_lexicon_id");   
			while((line = br.readLine()) != null) {
                if(line.startsWith("ApplNo")) continue;
				String[] arrLine = line.split("\t");
				String drugID = drugAndID.get(arrLine[6].toLowerCase());
				output.add(arrLine[6] + "\t" + drugID);
				
				count++;
				//if(count==10) break;
				//if(count%1000==0) System.out.println(count);
			}
			
			//write to file
			for(String eachOutput : output) {
				bw.append(eachOutput);
				bw.append("\n");
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
