package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.ProteinData;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.uuw.services.remoting.*;



public class InterProExample {

    public static void main(String[] args) throws Exception {

        //"IPR013530",
        // "IPR001323",

        String[] interProGroups = new String[]{ "IPR007664", "IPR008203", "IPR008625", "IPR009388"
                , "IPR012253", "IPR015350", "IPR011216"};

        ProteinDataQueryService service = UniProtJAPI.factory.getProteinDataQueryService();

        for (String interProGroup : interProGroups) {
            Query query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(DatabaseType.INTERPRO, interProGroup);
            EntryIterator<ProteinData> entryIterator = service.getEntryIterator(query);
            for (ProteinData proteinData : entryIterator) {
                System.out.println(proteinData.getUniProtEntry().getPrimaryUniProtAccession());
            }
        }
    }
}
