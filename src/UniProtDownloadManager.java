import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.biojava3.data.sequence.FastaSequence;
import org.biojava3.ronn.Jronn;
import org.biojava3.ronn.Jronn.Range;

import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.Gene;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.citations.JournalArticle;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.go.Go;
import uk.ac.ebi.kraken.interfaces.uniprot.features.Feature;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryIterator;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;
import uk.ac.ebi.kraken.uuw.services.remoting.Query;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryBuilder;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryService;


@SuppressWarnings("deprecation")
public class UniProtDownloadManager {
	private String queryString;
	
	private static NCBIDownloadManager ncbiDownloadManager = new NCBIDownloadManager();
	
	public UniProtDownloadManager(String query) {
		this.queryString = query;
	}
	
	
	@SuppressWarnings("unchecked")
	private static String getPublicationList(UniProtEntry uniProtEntry) {
 
		StringBuffer publications = new StringBuffer();
        EntryRetrievalService entryRetrievalService = UniProtJAPI.factory.getEntryRetrievalService();
   
        @SuppressWarnings("unchecked")
		List<Go> goTerms = (List<Go>) entryRetrievalService.getUniProtAttribute(uniProtEntry.getPrimaryUniProtAccession().getValue(), "ognl:goTerms");

         for (Go go : goTerms) {
        	 publications.append("<br/>Id " + go.getGoId().getValue());
        }

         List<JournalArticle> citations = (List<JournalArticle>) entryRetrievalService.getUniProtAttribute("P99999", "ognl:getCitations(@uk.ac.ebi.kraken.interfaces.uniprot.citations.CitationTypeEnum@JOURNAL_ARTICLE)");

        //System.out.println("<br/>number journal article citations = " + citations.size());

        for (JournalArticle cit : citations) {
        	publications.append("<br/>Title = " + cit.getTitle().getValue());
        	publications.append("<br/>Authors " + cit.getAuthors().size());
        }
        
        return publications.toString();
	}
	
	private static void logToDetailsHTMLFile(UniProtEntry uniProtEntry) {
	
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append("<html>");
	    sb.append("<body>");
	    sb.append("<table border=\"1\">");
	    
    	sb.append("<tr><td>\r\n");
    	//String imageTag = ncbiDownloadManager.getProteinImage(uniProtEntry);
    	//System.out.println("image = >>>>>>>>>>>>>>"+imageTag);
    	//sb.append(imageTag);
    	sb.append("<br/>");
 	
    	sb.append("</td></tr>\r\n");
	    
    	sb.append("<tr><td>\r\n");
    	Collection<DatabaseCrossReference> references = uniProtEntry.getDatabaseCrossReferences();
    	for(DatabaseCrossReference reference : references) {
    	sb.append(reference.getDatabase().toDisplayName());
    	sb.append("<br/>");
	    	}	
    	sb.append("</td></tr>\r\n");
    	
    	sb.append("<tr><td>\r\n");
    	for(DatabaseCrossReference reference : references) {
    	sb.append(reference);
    	sb.append("<br/>");
    	}	
    	sb.append("</td></tr>\r\n");
    	
    	sb.append("<tr><td>\r\n");
    	ArrayList<Gene> geneList = (ArrayList<Gene>) uniProtEntry.getGenes();
    	for(Gene myGene : geneList) {
    	sb.append(ncbiDownloadManager.getGeneFASTA(myGene.getGeneName().getValue(),uniProtEntry.getOrganism().getCommonName().getValue()));
    	sb.append("<br/>");
    	}	
    	sb.append("</td></tr>\r\n");
    	
    	
    	sb.append("<tr><td>\r\n");
    	sb.append(getPublicationList(uniProtEntry));
    	sb.append("</td></tr>\r\n");
    	
    	sb.append("<tr><td>\r\n");
    	
    	if(uniProtEntry.getSequence().getValue().length() > 20) {
	    	FastaSequence fsequence = new FastaSequence("Prot1", uniProtEntry.getSequence().getValue());
	    	Range[] ranges = Jronn.getDisorder(fsequence);
	    	for(int i=0;i<ranges.length;i++) {
	    		sb.append("<br/>From:"+ranges[i].from);
	    		sb.append("<br/>To:"+ranges[i].to);
	    		sb.append("<br/>Disorder Probability:"+ranges[i].score);
	    	}
    	}
    	sb.append("</td></tr>\r\n");
    	
    	sb.append("<tr><td>\r\n");
    	ArrayList<Feature> features =  (ArrayList<Feature>) uniProtEntry.getFeatures();
    	for(Feature feature : features) {
    	sb.append(feature.getType());
    	sb.append("<br/>");
    	sb.append(feature.getFeatureLocation().getStart());
    	sb.append("<br/>");
    	sb.append(feature.getFeatureLocation().getEnd());
    	sb.append("<br/>");
    	sb.append(feature.getFeatureLocation().getStart());
    	sb.append("<br/>");
    	}	
    	sb.append("</td></tr>\r\n");
    	
    	sb.append("</table>");
    	sb.append("</body>");
	    sb.append("</html>");
	 
	    try {
	    
	    File file = new File("C:\\Users\\addou\\Desktop\\UniProt\\details\\"+uniProtEntry.getPrimaryUniProtAccession().getValue()+"_details.html");
	    String content = sb.toString();
	    if (!file.exists()) {
		file.createNewFile();
		}
	 
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	 
	 
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	
	private static void logToHtmlFile(StringBuffer sb, String outPutFileName) {
		try {
		 
		String content = sb.toString();
	 
		File file = new File("C:\\Users\\addou\\Desktop\\UniProt\\"+outPutFileName+".html");
	 
		if (!file.exists()) {
		file.createNewFile();
		}
	 
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	 
	 
		} catch (IOException e) {
		e.printStackTrace();
		}
		}
		
		
		

		public static String getFASTA(UniProtEntry uniProtEntry) {
		String url = "http://www.uniprot.org/uniprot/"+uniProtEntry.getPrimaryUniProtAccession().getValue()+".fasta";
	 
		    HttpClient client = new HttpClient();
		    GetMethod method = new GetMethod(url);

		    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
		    	new DefaultHttpMethodRetryHandler(3, false));

		    try {
		      int statusCode = client.executeMethod(method);

		      if (statusCode != HttpStatus.SC_OK) {
		        System.err.println("Method failed: " + method.getStatusLine());
		      }


		      byte[] responseBody = method.getResponseBody();
		      
		      return new String(responseBody).replace(">", "&gt;");

		    } catch (HttpException e) {
		      System.err.println("Fatal protocol violation: " + e.getMessage());
		      e.printStackTrace();
		    } catch (IOException e) {
		      System.err.println("Fatal transport error: " + e.getMessage());
		      e.printStackTrace();
		    } finally {
		      method.releaseConnection();
		    }  
		    
		    return null;
		}
		
		
		
		public void generateReviewedEntriesReport() {
			
		    UniProtQueryService entryRetrievalService2 = UniProtJAPI.factory.getUniProtQueryService();    
		    Query reviewedSpondinQuery   = UniProtQueryBuilder.setReviewedEntries(UniProtQueryBuilder.buildFullTextSearch(queryString));
				   
		    EntryIterator<UniProtEntry>  entryIterator = entryRetrievalService2.getEntryIterator(reviewedSpondinQuery);
		    System.out.println("Starting reviewed entries ============> "+entryIterator.getResultSize());
		    
		    StringBuffer sb = new StringBuffer();
		    
		    sb.append("<html>");
		    sb.append("<body>");
		    sb.append("<table border=\"1\">");
		    
	    for(UniProtEntry test : entryIterator) {
		    	
		    	sb.append("<tr>");
		    	
		    	sb.append("<td>");
		    	sb.append(test.getPrimaryUniProtAccession().getValue());
		    	System.out.println(test.getPrimaryUniProtAccession().getValue());
		    	sb.append("</td>");
		    
		    	sb.append("<td>");
		    	sb.append(test.getUniProtId());
		    	sb.append("</td>");
		    	
		    	sb.append("<td>");
		    	sb.append(test.getOrganism().getCommonName());
		    	sb.append("</td>");
		    	
		    	sb.append("<td>");
		    	sb.append(test.getOrganism().getScientificName());
		    	sb.append("</td>");
		    	
		    	sb.append("<td>");
		    	ArrayList<Gene> geneList = (ArrayList<Gene>) test.getGenes();
		    	for(Gene myGene : geneList) {
		    	sb.append(myGene.getGeneName());
		    	sb.append("<br/>");
		    	}	
		    	sb.append("</td>");
		    	
		    	
		    	sb.append("<td>");
		    	sb.append(getFASTA(test));
		    	sb.append("</td>");
		    	

		    	sb.append("<td>");
		    	String tmpFileName = test.getPrimaryUniProtAccession().getValue()+"_details.html";
		    	sb.append("<a href=\"details/"+tmpFileName+"\">CLICK FOR MORE DETAILS</a>");
		    	sb.append("</td>");

		    	
		    	sb.append("</tr>");
		    	
		    	logToDetailsHTMLFile(test);
		    	
		    	//break;
		    	
	 
		    }
	    
	    	sb.append("</table>");
	    	sb.append("</body>");
		    sb.append("</html>");
		    
		   
		    
	 

		    logToHtmlFile(sb,"reviewed_entries");
			
		}
		

	 
		
		public void generateNonReviewedEntriesReport() {
			
			
			
		    UniProtQueryService entryRetrievalService2 = UniProtJAPI.factory.getUniProtQueryService();
		    
		    Query unreviewedSpondinQuery = UniProtQueryBuilder.setUnreviewedEntries(UniProtQueryBuilder.buildFullTextSearch(queryString));
		   
		    EntryIterator<UniProtEntry>  entryIterator = entryRetrievalService2.getEntryIterator(unreviewedSpondinQuery);
		    System.out.println("Starting unreviewed entries ============> "+entryIterator.getResultSize());
		    
		    StringBuffer sb = new StringBuffer();
		    
		    sb.append("<html>");
		    sb.append("<body>");
		    sb.append("<table border=\"1\">");
		    
	    for(UniProtEntry test : entryIterator) {
		    	
		    	sb.append("<tr>");
		    	
		    	sb.append("<td>");
		    	sb.append(test.getPrimaryUniProtAccession().getValue());
		    	System.out.println(test.getPrimaryUniProtAccession().getValue());
		    	sb.append("</td>");
		    	
		    	sb.append("<td>");
		    	sb.append(test.getUniProtId());
		    	sb.append("</td>");
		    	
		    	sb.append("<td>");
		    	sb.append(test.getOrganism().getCommonName());
		    	sb.append("</td>");
		    	
		    	sb.append("<td>");
		    	sb.append(test.getOrganism().getScientificName());
		    	sb.append("</td>");
		    	
		    	
		    	
		    	sb.append("<td>");
		    	ArrayList<Gene> geneList = (ArrayList<Gene>) test.getGenes();
		    	for(Gene myGene : geneList) {
		    	sb.append(myGene.getGeneName());
		    	sb.append("<br/>");
		    	}	
		    	sb.append("</td>");
		    	
 
		    	
		    	sb.append("<td>");
		    	sb.append(getFASTA(test));
		    	sb.append("</td>");
		    	
		    	sb.append("<td>");
		    	String tmpFileName = test.getPrimaryUniProtAccession().getValue()+"_details.html";
		    	sb.append("<a href=\"details/"+tmpFileName+"\">CLICK FOR MORE DETAILS</a>");
		    	sb.append("</td>");

		    	
		    	sb.append("</tr>");
		    	
		    	logToDetailsHTMLFile(test);
		    	
		    	//break;
		    	
	 
		    }
	    
	    	sb.append("</table>");
	    	sb.append("</body>");
		    sb.append("</html>");
		    
		   
		    
	 

		    logToHtmlFile(sb,"non_reviewed_entries");
			
		}

}
