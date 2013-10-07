
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
//C:\Users\addou\Desktop\

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.Gene;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.features.Feature;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryIterator;
 
import uk.ac.ebi.kraken.uuw.services.remoting.Query;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryBuilder;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryService;


public class UniProtDownloader implements Handler {
	
	private static void logToCSVFile(StringBuffer sb) {
	try {
	 
	String content = sb.toString();
 
	File file = new File("C:\\Java\\UniProt\\filename.html");
 
	if (!file.exists()) {
	file.createNewFile();
	}
 
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(content);
	bw.close();
 
	System.out.println("Done");
 
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	
	
	
	private static String getGeneFASTA(UniProtEntry uniProtEntry) {
	String url = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=protein&id=15718680,157427902,119703751";
 
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

	private static String getFASTA(UniProtEntry uniProtEntry) {
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
 
	public static void main(String[] args) {
 
	    UniProtQueryService entryRetrievalService2 = UniProtJAPI.factory.getUniProtQueryService();
	    
	    
	    Query reviewedSpondinQuery   = UniProtQueryBuilder.setReviewedEntries(UniProtQueryBuilder.buildFullTextSearch("spondin"));
	    Query unreviewedSpondinQuery = UniProtQueryBuilder.setReviewedEntries(UniProtQueryBuilder.buildFullTextSearch("spondin"));
	   
	    EntryIterator<UniProtEntry>  entryIterator = entryRetrievalService2.getEntryIterator(reviewedSpondinQuery);
	    System.out.println("Starting reviewed entries ============> "+entryIterator.getResultSize());
	    
	   
	    
	    StringBuffer sb = new StringBuffer();
	    
	    StringBuffer sbProteinFastas = new StringBuffer();
	    
	    
	    sb.append("<html>\r\n");
	    sb.append("<head>\r\n");
	    sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"jqueryui/css/smoothness/jquery-ui-1.10.3.custom.min.css\"/>\r\n");
	    sb.append("</head>\r\n");
	    
 
	    sb.append("<body>\r\n");
	    
	    sb.append("<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script>\r\n");
	    sb.append("<script src=\"http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js\"></script>\r\n");
 
	    sb.append("<script type=\"text/javascript\">$(document).ready(function() { $('td').click(function() { $( '.comment' ).css('visibility', 'visible');$( '.comment' ).dialog({ modal: true, width:'450', heigh: 'auto' }); }) });   </script>\r\n");
 
	    sb.append("<table border=\"1\">\r\n");
	    
	    
	    
    for(UniProtEntry test : entryIterator) {
	    	
	    	sb.append("<tr>\r\n");
	    	
	    	sb.append("<td>\r\n");
	    	sb.append(test.getPrimaryUniProtAccession().getValue());
	    	sb.append("</td>\r\n");
	    	
	    	sb.append("<td>\r\n");
	    	sb.append(test.getUniProtId());
	    	sb.append("</td>\r\n");
	    	
	    	sb.append("<td>\r\n");
	    	sb.append(test.getOrganism().getCommonName());
	    	sb.append("</td>\r\n");
	    	
	    	sb.append("<td>\r\n");
	    	sb.append(test.getOrganism().getScientificName());
	    	sb.append("</td>\r\n");
	    	
	    	
	    	/*
	    	sb.append("<td>");
	    	ArrayList<Gene> geneList = (ArrayList<Gene>) test.getGenes();
	    	for(Gene myGene : geneList) {
	    	sb.append(myGene.getGeneName());
	    	sb.append("<br/>");
	    	}	
	    	sb.append("</td>");
	    	
	    	
	    	sb.append("<td>");
	    	Collection<DatabaseCrossReference> references = test.getDatabaseCrossReferences();
	    	for(DatabaseCrossReference reference : references) {
	    	sb.append(reference.getDatabase().toDisplayName());
	    	sb.append("<br/>");
 	    	}	
	    	sb.append("</td>");
	    	
	    	sb.append("<td>");
	    	for(DatabaseCrossReference reference : references) {
	    	sb.append(reference);
	    	sb.append("<br/>");
	    	}	
	    	sb.append("</td>");
	    	
	    	
	    	
	    	sb.append("<td>");
	    	ArrayList<Feature> features =  (ArrayList<Feature>) test.getFeatures();
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
	    	sb.append("</td>");
	    	*/
	    	
	    	sb.append("<td>\r\n");
	    	sb.append("CLICK HERE");
	    	sb.append("</div>\r\n");
	    	
	    	sbProteinFastas.append("<div style=\"visibility: hidden; word-wrap: break-word;\" class=\"comment\">"+getFASTA(test)+"</div>\r\n");
	    	
	    	sb.append("</tr>\r\n");
	    	
	    	break;
	    	//System.out.println(test.getPrimaryUniProtAccession().getValue());
	    	//System.out.println(test.getUniProtId());
	    	
	    	//System.out.println("test = "+test.getUniProtId());
	    	//break;
	    	//System.out.println(test.getOrganism().getCommonName());
	    	//System.out.println(test.getOrganism().getScientificName());

 
	    	//System.out.println("---------------------------------------------------------------");
	    	//System.out.println( getFASTA(test));
	    	//System.out.println("---------------------------------------------------------------");
 
	    	//System.out.println("ssssssssssssssssss = "+test.getProteinDescription());
	    }
    
    	sb.append("</table>\r\n");
    	sb.append(sbProteinFastas);
    	sb.append("</body>\r\n");
	    sb.append("</html>\r\n");
	    
	   
	    
	   /* 
	    for(UniProtEntry test : entryIterator) {
	    	
	    	
	    	sb.append(test.getPrimaryUniProtAccession().getValue());
	    	sb.append(",");
	    	sb.append(test.getUniProtId());
	    	sb.append(",");
	    	sb.append(test.getOrganism().getCommonName());
	    	sb.append(",");
	    	sb.append(test.getOrganism().getScientificName());
	    	sb.append(",");
	    	sb.append(getFASTA(test));
	    	sb.append(",");
	    	sb.append("\n");
	    	
	    	//System.out.println(test.getPrimaryUniProtAccession().getValue());
	    	//System.out.println(test.getUniProtId());
	    	
	    	//System.out.println("test = "+test.getUniProtId());
	    	//break;
	    	//System.out.println(test.getOrganism().getCommonName());
	    	//System.out.println(test.getOrganism().getScientificName());

 
	    	//System.out.println("---------------------------------------------------------------");
	    	//System.out.println( getFASTA(test));
	    	//System.out.println("---------------------------------------------------------------");
 
	    	//System.out.println("ssssssssssssssssss = "+test.getProteinDescription());
	    }
	    */

	    logToCSVFile(sb);
	    
	}

	@Override
	public boolean handleMessage(MessageContext context) {
	// TODO Auto-generated method stub
	return false;
	}

	@Override
	public boolean handleFault(MessageContext context) {
	// TODO Auto-generated method stub
	return false;
	}

	@Override
	public void close(MessageContext context) {
	// TODO Auto-generated method stub
	
	}

}
