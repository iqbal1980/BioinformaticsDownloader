package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

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

public class AccessionIteratorExample {
    public static void main(String[] args) throws Exception {
        UniProtQueryService queryService = UniProtJAPI.factory.getUniProtQueryService();

        //Query for Protein Name
        Query proteinQuery = UniProtQueryBuilder.buildProteinNameQuery("Clpc");
        AccessionIterator<UniProtEntry> iterator0 = queryService.getAccessions(proteinQuery);
        System.out.println("Entries with protein name Clpl = " + iterator0.getResultSize());
        for (String ac : iterator0) {
            System.out.println("ac = " + ac);
        }

        // "SmartSearch" query
        Query smartQuery = UniProtQueryBuilder.buildQuery("ClpC");
        AccessionIterator<UniProtEntry> iterator1 = queryService.getAccessions(smartQuery);
        System.out.println("Entries with query ClpC  = " + iterator1.getResultSize());
        for (String ac : iterator1) {
            System.out.println("ac = " + ac);
        }
        // Query for Gene Name
        Query geneNameQuery = UniProtQueryBuilder.buildGeneNameQuery("ClpC");
        AccessionIterator<UniProtEntry> iterator2 = queryService.getAccessions(geneNameQuery);
        System.out.println("Entries with Gene name ClpC = " + iterator2.getResultSize());
        for (String ac : iterator2) {
            System.out.println("ac = " + ac);
        }

        // Full Text query
        Query fullText = UniProtQueryBuilder.buildFullTextSearch("ClpC");
        AccessionIterator<UniProtEntry> iterator3 = queryService.getAccessions(fullText);
        System.out.println("Entries where full text returns ClpC = " + iterator3.getResultSize());
        for (String ac : iterator3) {
            System.out.println("ac = " + ac);
        }
    }

}
