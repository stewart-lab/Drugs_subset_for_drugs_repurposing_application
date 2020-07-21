import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


/** 
 * The DiseaseDrugRelationshipExtractor program retrieves relationship
 * from DrugBank downloaded file,full database.xml. 
 * 
 * @author Kalpana Raja
 *
 */

public class DiseaseDrugRelationshipExtractor {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String line="", id="", drugbankID="", name="", geneSymbol="", indicationTaggedText="";
		int count=0, count1=0;
		boolean flag=false, flag1=false, flag2=false, flag3=false;
		
		ArrayList<String> drugName = new ArrayList<String>();
		ArrayList<String> drugNameList = new ArrayList<String>();
		ArrayList<String> synonyms = new ArrayList<String>();
		ArrayList<String> indication = new ArrayList<String>();
		
		ArrayList<String> saltTags = new ArrayList<String>();
		
		try {
			FileInputStream fis = new FileInputStream("full database.xml");
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
		    BufferedReader br = new BufferedReader(isr);
		         
		    FileOutputStream fos = new FileOutputStream("disease_drug_relationship.txt");
		    OutputStreamWriter osr = new OutputStreamWriter(fos, "UTF-8");
		    BufferedWriter bw = new BufferedWriter(osr);

			bw.append("Drug\tDrug_synonyms\tDrugBank_ID\tIndication");
			bw.append("\n");		       
			while((line = br.readLine()) != null) {
				if(line.trim().startsWith("<drugbank-id ")) {
					id = "";
					synonyms.clear();
					saltTags.clear();
					
					id = line.substring(line.indexOf("\">")+2, line.indexOf("</"));
					if(!id.contains("DBSALT")) {
						drugbankID = id;
					}
				}
				else if(line.trim().startsWith("<name>")) {
					if(!flag1) {
						name = line.substring(line.indexOf(">")+1, line.indexOf("</"));
						if(drugName.isEmpty()) {
							drugName.add(name);
							if(!drugNameList.contains(name)) {
								drugNameList.add(name);
							}
						}
						flag1=true;
					}
				}
				else if(line.trim().startsWith("<synonym ")) {
					flag=false;
					String synonym = line.substring(line.indexOf(">")+1, line.indexOf("</"));
					synonyms.add(synonym.trim());
				}
				else if(line.trim().startsWith("<indication>") && line.trim().endsWith("</indication>")) {
					indicationTaggedText = line.trim();
					flag2=true;
				}
				else if(line.trim().startsWith("<indication>") && !line.trim().endsWith("</indication>")) {
					flag3 = true;
				}
				else if(!line.trim().startsWith("<indication>") && line.trim().endsWith("</indication>")) {
					flag3 = true;
				}
				else if(line.trim().equals("</indication>")) {
					flag2=false;
				}
				else if(line.trim().equals("</drug>")) {
					ArrayList<String> records = writeToFile(drugbankID, drugName, synonyms, indication);
					
					for(String eachRecord : records) {
						bw.append(eachRecord);
						bw.append("\n");
					}
											
					name="";
					flag1 = false;
					flag2 = false;
					flag3 = false;
					indicationTaggedText="";
					drugName.clear();
					indication.clear();
					count++;
					//if(count==5000) break;
				}

				if(flag3) {
					if(indicationTaggedText.isEmpty()) indicationTaggedText = line.trim();
					else indicationTaggedText = indicationTaggedText + " " + line.trim();

					if(indicationTaggedText.startsWith("<indication>") && indicationTaggedText.endsWith("</indication>")) {
						line = indicationTaggedText;
						flag2 = true;
					}
				}
				
				if(flag2 && line.trim().startsWith("<indication>")) {
					String indicationText = line.substring(line.indexOf(">")+1, line.indexOf("</"));
					indication.add(indicationText);
					flag2=false;
				}
			}
			br.close();
			bw.close();
		} catch(IOException e) {
			System.err.println(e);
		}

		long stopTime = System.currentTimeMillis();
      		long elapsedTime = stopTime - startTime;
      		System.out.println("Excution time in milliseconds: " + elapsedTime);
	}
	
	public static ArrayList<String> writeToFile(String drugbankID, ArrayList<String> drugName, 
			ArrayList<String> synonyms, ArrayList<String> indication) {
		String record="";
		
		ArrayList<String> records = new ArrayList<String>();
		
		if(!drugbankID.isEmpty()) {
			String drugsList="", synonymsList="";
			
			if(!drugName.isEmpty()) {
				for(String eachDrugName : drugName) {
					if(drugsList.isEmpty()) { drugsList = eachDrugName; }
					else { drugsList = drugsList+"###"+eachDrugName; }
				}
			}
			if(!synonyms.isEmpty()) {
				for(String eachSynonym : synonyms) {
					if(synonymsList.isEmpty()) { synonymsList = eachSynonym; }
					else { synonymsList = synonymsList+"###"+eachSynonym; }
				}
			}
			
			for(String eachIndication : indication) {
				record = drugsList+"\t"+synonymsList+"\t"+drugbankID+"\t"+eachIndication;
				records.add(record);
			}
		}
		
		return records;
	}
	
}
