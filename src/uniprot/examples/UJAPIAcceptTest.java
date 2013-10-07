package uniprot.examples;


public class UJAPIAcceptTest{

	/**
	 * @param args
	 */
	public static void main(String[] args){
		try{
			AccessionIteratorExample.main(args);
			System.out.println("AccessionIteratorExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("AccessionIteratorExample failed");
		}
		try{
			ApplicationNoteDemo.main(args);
			System.out.println("ApplicationNoteDemo Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("ApplicationNoteDemo failed");
		}
		
		try{
			AttributeExample.main(args);
			System.out.println("AttributeExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("AttributeExample failed");
		}
		
		try{
			AttributeIteratorExample.main(args);
			System.out.println("AttributeIteratorExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("AttributeIteratorExample failed");
		}
		
		
		try{
			BatchExample.main(args);
			System.out.println("BatchExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("BatchExample failed");
		}
		
		try{
			EntryListQueryExample.main(args);
			System.out.println("EntryListQueryExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("EntryListQueryExample failed");
		}
		
		
		try{
			EntryRetrievalExample.main(args);
			System.out.println("EntryRetrievalExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("EntryRetrievalExample failed");
		}
		try{
			InterProExample.main(args);
			System.err.println("InterProExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("InterProExample failed");
		}
		try{
			ProteinDataQueryExample.main(args);
			System.out.println("ProteinDataQueryExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("ProteinDataQueryExample failed");
		}
		try{
			SetOperationExample.main(args);
			System.out.println("SetOperationExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("SetOperationExample failed");
		}
		try{
			UniParcQueryExample.main(args);
			System.out.println("UniParcQueryExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("UniParcQueryExample failed");
		}
		
		try{
			UniprotBlastExample.main(args);
			System.out.println("UniprotBlastExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("UniprotBlastExample failed");
		}
		
		try{
			UniprotBlastResultLimitExample.main(args);
			System.out.println("UniprotBlastResultLimitExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("UniprotBlastResultLimitExample failed");
		}
		try{
			UniProtFieldQueryExample.main(args);
			System.out.println("UniProtFieldQueryExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("UniProtFieldQueryExample failed");
		}
		try{
			UniProtQueryExample.main(args);
			System.out.println("UniProtQueryExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("UniProtQueryExample failed");
		}
		
		try{
			UniRefBlastExample.main(args);
			System.out.println("UniRefBlastExample Succeeded");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("UniRefBlastExample failed");
		}
	}
	
}
