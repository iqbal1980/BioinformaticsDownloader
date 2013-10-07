package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.ProteinData;
import uk.ac.ebi.kraken.interfaces.interpro.InterProMatch;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.util.List;

public class ProteinDataQueryExample {

    public static void main(String[] args) throws Exception{


        // ProteinDataQueryService provides more data than the UniProtQueryService
        ProteinDataQueryService queryService = UniProtJAPI.factory.getProteinDataQueryService();

        //Query for entries with a specific Protein Name
        Query proteinQuery = UniProtQueryBuilder.buildProteinNameQuery("Clpc");
        EntryIterator<ProteinData> entries = queryService.getEntryIterator(proteinQuery);
        System.out.println("Entries with protein name Clpl = " + entries.getResultSize());
        // ProteinData contains additional information that is not provided by UniProt
        for (ProteinData entry : entries) {
            // for example the
            List<InterProMatch> list = entry.getInterProMatches();
            for (InterProMatch interProMatch : list) {
                System.out.print(interProMatch.getMethodName().getValue());
                System.out.print(" - Position in Sequence: "+interProMatch.getFromPos()+"-");
                System.out.print(interProMatch.getToPos()+",");
                System.out.print(" (Score: "+interProMatch.getScore()+")\n");
            }
        }
    }
}
