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

public class UniProtQueryExample {

    public static void main(String[] args) throws Exception {

        UniProtQueryService queryService = UniProtJAPI.factory.getUniProtQueryService();

        //Query for Protein Name
        Query proteinQuery = UniProtQueryBuilder.buildProteinNameQuery("Clpc");
        EntryIterator<UniProtEntry> entries = queryService.getEntryIterator(proteinQuery);
        System.out.println("Entries with protein name Clpl = " + entries.getResultSize());

        // "SmartSearch" query
        Query smartQuery = UniProtQueryBuilder.buildQuery("ClpC");
        EntryIterator<UniProtEntry> iterator = queryService.getEntryIterator(smartQuery);
        System.out.println("iterator.getResultSize() = " + iterator.getResultSize());

        // Query for Gene Name
        Query geneNameQuery = UniProtQueryBuilder.buildGeneNameQuery("ClpC");
        EntryIterator<UniProtEntry> iterator1 = queryService.getEntryIterator(geneNameQuery);
        System.out.println("iterator.getResultSize() = " + iterator1.getResultSize());

        // Full Text query
        Query fullText = UniProtQueryBuilder.buildFullTextSearch("ClpC");
        iterator = queryService.getEntryIterator(fullText);
        System.out.println("iterator.getResultSize() = " + iterator.getResultSize());
    }
}
