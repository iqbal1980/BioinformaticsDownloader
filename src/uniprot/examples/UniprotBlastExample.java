package uniprot.examples;

import java.util.Collection;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.ProteinData;
import uk.ac.ebi.kraken.interfaces.interpro.InterProMatch;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.interpro.InterPro;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureType;
import uk.ac.ebi.kraken.interfaces.uniprot.features.ModResFeature;
import uk.ac.ebi.kraken.model.blast.JobStatus;
import uk.ac.ebi.kraken.model.blast.parameters.DatabaseOptions;
import uk.ac.ebi.kraken.model.blast.parameters.ExpectedThreshold;
import uk.ac.ebi.kraken.model.blast.parameters.GapAlign;
import uk.ac.ebi.kraken.model.blast.parameters.SimilarityMatrixOptions;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryIterator;
import uk.ac.ebi.kraken.uuw.services.remoting.Query;
import uk.ac.ebi.kraken.uuw.services.remoting.SetOperation;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryBuilder;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryService;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastData;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastHit;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastInput;


public class UniprotBlastExample {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		UniProtQueryService service = UniProtJAPI.factory.getUniProtQueryService();
		  String querySequence = ">MES00005665499\n" +
	                "MSNHGFAYFFTSYQSLSLDSSSPPPSPHPRAHASSRFPPRARAVASFHTSCKMARTKQTA\n" +
	                "RKSTGGKAPRKQLATKAARKSAPATGGVKKPHRYRPGTVALREIRKYQKSTELLIRKLPF\n" +
	                "QRLVREIAQDFKTDLRFQSSAVLALQEASEAYLVGLFEDTNLCAIHAKRVTIMPKDVQLA\n" +
	                "RRIRGERA";
		BlastInput input = new BlastInput(DatabaseOptions.SWISSPROT,
		querySequence, 
		ExpectedThreshold.ONEHUNDRED,
		GapAlign.FALSE,
		SimilarityMatrixOptions.PAM_30);
		String jobid = service.submitBlast(input);
		  while (!(service.checkStatus(jobid) == JobStatus.FINISHED)) {
	            Thread.sleep(5000);
	        }
		   BlastData<UniProtEntry> blastResult = service.getResults(jobid);
	        List<BlastHit<UniProtEntry>> blastHits = blastResult.getBlastHits();
	        UniProtEntry bestHit = blastHits.get(0).getEntry();
	        System.out.print("Best hit: " +bestHit.getPrimaryUniProtAccession().getValue() + " ");
	        System.out.print(bestHit.getProteinDescription().getRecommendedName().getFields() + " ");
	        System.out.println(bestHit.getOrganism());
	        // Show all InterPro cross-references
	        List<InterPro> databaseCrossReferences = bestHit.
	                getDatabaseCrossReferences(DatabaseType.INTERPRO);
	        Collection<DatabaseCrossReference> xref = bestHit.getDatabaseCrossReferences();
	        for (DatabaseCrossReference databaseCrossReference : xref) {
	            System.out.println("databaseCrossReference = " + databaseCrossReference);
	        }
	
	        // Show all postranslational modifications on the sequence
	        Collection<ModResFeature> features = bestHit.getFeatures(FeatureType.MOD_RES);
	        for (ModResFeature feature : features) {
	            System.out.println("From " + feature.getFeatureLocation().getStart() + " to "
	                    + feature.getFeatureLocation().getEnd() + " " + feature.getFeatureDescription().getValue()
	                    + " " + feature.getFeatureStatus().getValue());
	        }
	        EntryIterator<UniProtEntry> blastResultIterator = service.getEntryIterator(blastResult.getResultAsQuery());
	        System.out.println("Number of Blast hits " + blastResultIterator.getResultSize());
	        for (InterPro ipro : databaseCrossReferences) {
	            Query query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(ipro.toString());
	            EntryIterator<UniProtEntry> resultSet = service.getEntryIterator(query);
	            System.out.println("InterPro " + ipro + " contains " + resultSet.getResultSize() + " members");
	            // Create intersection between Blast result
	            blastResultIterator = service.getEntryIterator(blastResultIterator, SetOperation.And, resultSet);
	        }
	        System.out.println("Intersection contains " + blastResultIterator.getResultSize());


	}

}
