import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;


public class NCBIDownloadManager {
	private static void mySleep() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
	 
			e1.printStackTrace();
		}
	}

	public NCBIDownloadManager() {
		 
	}
	
	
	public String getProteinID(UniProtEntry uniProtEntry) {
	mySleep();
	
	System.out.println("Calling NCBI");
	
	String proteinName = uniProtEntry.getPrimaryUniProtAccession().getValue();
	
	String url = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=protein&term="+proteinName;
 
	
	System.out.println(url);
	 
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	try {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new URL(url).openStream());
		doc.getDocumentElement().normalize();
	 
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	 
		NodeList nList = doc.getElementsByTagName("Id");
		Node nNode = nList.item(0);
		String returnProteinID = nNode.getFirstChild().getNodeValue();
		System.out.println(">>>>>>>>>>>>"+returnProteinID);
		
		return returnProteinID;

 
	} catch(Exception err) {
		
	}
 

	 
	    return null;
	}
	
	
	public String getGeneID(String geneName, String organism) {
	mySleep();
	
	System.out.println("Calling NCBI");
	String url = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=gene&term="+geneName+"[Gene%20Name]+AND+"+organism+"[Organism]";
 
	
	System.out.println(url);
	 
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	try {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new URL(url).openStream());
		doc.getDocumentElement().normalize();
	 
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	 
		NodeList nList = doc.getElementsByTagName("Id");
		Node nNode = nList.item(0);
		String returnGeneID = nNode.getFirstChild().getNodeValue();
		System.out.println(">>>>>>>>>>>>"+returnGeneID);
		
		return returnGeneID;

 
	} catch(Exception err) {
		
	}
 

	 
	    return null;
	}
	

	public String getGeneFASTA(String geneName, String organism) {
		mySleep();
		
		String geneID = getGeneID(geneName,organism);
		
		System.out.println("Calling NCBI");
		String urlForGeneFasta = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id="+geneID+"&seq_start=1&rettype=fasta&retmode=txt";
	 
		
	    HttpClient client = new HttpClient();
	    GetMethod method = new GetMethod(urlForGeneFasta);

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

	
	
	public String getProteinImage(UniProtEntry uniProtEntry) {
	mySleep();
	
	String proteinID ="111234"; //getProteinID(uniProtEntry);
	
	System.out.println("Calling NCBI");
	String urlForProteinPDBCode = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=structure&id="+proteinID+"&retmode=text";
 
	
	System.out.println(urlForProteinPDBCode);
	 
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	try {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new URL(urlForProteinPDBCode).openStream());
		doc.getDocumentElement().normalize();
	 
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	 
		NodeList nList = doc.getElementsByTagName("Item");
		
		System.out.println("SIZE=========>"+nList.getLength());
		
		//TODO
		
  
	} catch(Exception err) {
		err.printStackTrace();
	}
	return null;
	
	}


}
