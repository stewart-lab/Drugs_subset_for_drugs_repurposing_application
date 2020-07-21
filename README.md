# **Drugs subset for drug repurposing application**

**Introduction:**    
Drugs subset project is meant to extract drugs that are associated with diseases, genes, phenotypes, variants and haplotypes. The disease-drug associations are from three resources namely Comparative Toxicogenomics Database (CTD), National Drug File-Reference Terminology (NDF-RT) and DrugBank. The gene-drug interactions are from three resources namely CTD, DrugBank and PharmGKB. The phenotype-drug associations are from CTD. The variant/haplotype-drug associations are from PharmGKB. The project also compiles a unique list of associations from all the resources.

**Prerequisites:**   
The resource files should be downloaded from CTD, NDF-RT, DrugBank and PharmGKB. While CTD provides free access to download the entire data, downloading the data from NDF-RT bioportal is challenging. Alternatively, we can downloaded NDF-RT data available within UMLS Metahesaurus, a collection of biomedical concepts from around 200 dictionaries. DrugBank file can be downloaded with a free registration. PharmGKB file can be downloaded with a free registration and approval. In addition to the resource files, this project requires our own drugs lexicon to map the customized ID to drugs. The drugs lexicon is compiled from three resources namely UMLS Metathesaurus, DrugBank and PharmGKB. Please refer to https://github.com/CutaneousBioinf/LiteratureMiningTool/DrugDict/. Please refer to https://github.com/CutaneousBioinf/LiteratureMiningTool/ConceptMap/ for processing of UMLS Metathesaurus. 

---- RUN IN AN IDE ----   

The entire project should be pulled into Java IDE, such as eclipse. For execution, please see Documentation/Drugs_subset_for_drugs_repurposing_application.docx.   

---- COMPILE AND RUN ON THE COMMAND LINE ----   

Processing includes Java programs and Linux commands. Please see Documentation/Drugs_subset_for_drugs_repurposing_application.docx.   

Java version used for development: JavaSE-1.8.  

Authors: Kalpana Raja  

Affiliation: Morgridge Institute for Research, Madison, WI, USA