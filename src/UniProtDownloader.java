
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
import org.biojava3.data.sequence.FastaSequence;
import org.biojava3.ronn.Jronn;
import org.biojava3.ronn.Jronn.Range;

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
	
	private static void logToCSVFile(StringBuffer sb, String fileName) {
	try {
	 
	String content = sb.toString();
 
	File file = new File("C:\\Java\\UniProt\\"+fileName);
 
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
	      
	      //return new String(responseBody).replace(">", "&gt;");
	      return new String(responseBody);
	   
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
	    Query unreviewedSpondinQuery = UniProtQueryBuilder.setUnreviewedEntries(UniProtQueryBuilder.buildFullTextSearch("spondin"));
	   
	    ////////////////////////////////////////////////////
	    EntryIterator<UniProtEntry>  entryIteratorReviewed = entryRetrievalService2.getEntryIterator(reviewedSpondinQuery);
	    System.out.println("Starting reviewed entries ============> "+entryIteratorReviewed.getResultSize());

	    StringBuffer sbReviewed = new StringBuffer();
 
 
	    for(UniProtEntry test : entryIteratorReviewed) {
	    	sbReviewed.append(getFASTA(test));
	    	sbReviewed.append("\n");
	    }
	    logToCSVFile(sbReviewed, "reviewed.txt");
	    ////////////////////////////////////////////////////
	    
	    
	    ////////////////////////////////////////////////////
	    EntryIterator<UniProtEntry>  entryIteratorUnreviewed = entryRetrievalService2.getEntryIterator(unreviewedSpondinQuery);
	    System.out.println("Starting unreviewed entries ============> "+entryIteratorUnreviewed.getResultSize());

 
	    StringBuffer sbUnReviewed = new StringBuffer();
 
	    for(UniProtEntry test : entryIteratorUnreviewed) {
	    	sbUnReviewed.append(getFASTA(test));
	    	sbReviewed.append("\n");
	    }
	    logToCSVFile(sbUnReviewed, "unreviewed.txt");
	    ////////////////////////////////////////////////////
	    
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
