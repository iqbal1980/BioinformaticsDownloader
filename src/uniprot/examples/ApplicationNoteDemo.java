package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.ProteinData;
import uk.ac.ebi.kraken.interfaces.interpro.InterProMatch;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.interfaces.uniprot.features.ModResFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureType;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.interpro.InterPro;
import uk.ac.ebi.kraken.model.blast.JobStatus;
import uk.ac.ebi.kraken.model.blast.parameters.DatabaseOptions;
import uk.ac.ebi.kraken.uuw.services.remoting.*;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastData;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastHit;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastInput;

import java.util.List;
import java.util.Collection;

/*
 * Copyright 1999,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class ApplicationNoteDemo {
    public static void main(String args[]) throws Exception {
        ProteinDataQueryService service = UniProtJAPI.factory.getProteinDataQueryService();
        String sequence = ">MES00005665499\n" +
                "MSNHGFAYFFTSYQSLSLDSSSPPPSPHPRAHASSRFPPRARAVASFHTSCKMARTKQTA\n" +
                "RKSTGGKAPRKQLATKAARKSAPATGGVKKPHRYRPGTVALREIRKYQKSTELLIRKLPF\n" +
                "QRLVREIAQDFKTDLRFQSSAVLALQEASEAYLVGLFEDTNLCAIHAKRVTIMPKDVQLA\n" +
                "RRIRGERA";
        String jobid = service.submitBlast(new BlastInput(DatabaseOptions.SWISSPROT, sequence));
        // Jobid used to check status
        while (!(service.checkStatus(jobid) == JobStatus.FINISHED)) {
            Thread.sleep(5000);
        }
        System.out.println("Blast job complete");
        // The blast data contains the job information and the best hits
        BlastData<ProteinData> blastResult = service.getResults(jobid);
        List<BlastHit<ProteinData>> blastHits = blastResult.getBlastHits();
        ProteinData bestHit = blastHits.get(0).getEntry();
        System.out.print("Best hit: " + bestHit.getUniProtEntry().getPrimaryUniProtAccession().getValue() + " ");
        System.out.print(bestHit.getUniProtEntry().getProteinDescription().getRecommendedName().getFields() + " ");
        System.out.println(bestHit.getUniProtEntry().getOrganism());
        // Show all InterPro cross-references
        List<InterPro> databaseCrossReferences = bestHit.getUniProtEntry().
                getDatabaseCrossReferences(DatabaseType.INTERPRO);
        Collection<DatabaseCrossReference> xref = bestHit.getUniProtEntry().getDatabaseCrossReferences();
        for (DatabaseCrossReference databaseCrossReference : xref) {
            System.out.println("databaseCrossReference = " + databaseCrossReference);
        }
        // Show all InterPro matches with positions and score
        List<InterProMatch> matches = bestHit.getInterProMatches();
        for (InterProMatch match : matches) {
            System.out.println("Match: " + match.getMethodName().getValue() + " " + match.getMethodAccession()
                    + " from " + match.getFromPos() + " to " + match.getToPos()
                    + " " + match.getScore());
        }
        // Show all postranslational modifications on the sequence
        Collection<ModResFeature> features = bestHit.getUniProtEntry().getFeatures(FeatureType.MOD_RES);
        for (ModResFeature feature : features) {
            System.out.println("From " + feature.getFeatureLocation().getStart() + " to "
                    + feature.getFeatureLocation().getEnd() + " " + feature.getFeatureDescription().getValue()
                    + " " + feature.getFeatureStatus().getValue());
        }
        EntryIterator<ProteinData> blastResultIterator = service.getEntryIterator(blastResult.getResultAsQuery());
        System.out.println("Number of Blast hits " + blastResultIterator.getResultSize());
        for (InterPro ipro : databaseCrossReferences) {
            Query query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(ipro.toString());
            EntryIterator<ProteinData> resultSet = service.getEntryIterator(query);
            System.out.println("InterPro " + ipro + " contains " + resultSet.getResultSize() + " members");
            // Create intersection between Blast result
            blastResultIterator = service.getEntryIterator(blastResultIterator, SetOperation.And, resultSet);
        }
        System.out.println("Intersection contains " + blastResultIterator.getResultSize());
    }
}