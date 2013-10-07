package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.uniparc.UniParcEntry;
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

public class UniParcQueryExample {

    public static void main(String[] args) throws Exception {

        UniParcQueryService queryService = UniProtJAPI.factory.getUniParcQueryService();

        // Only active cross reference query
        Query proteinQuery = UniParcQueryBuilder.buildActiveCrossReferenceQuery("P99999");
        EntryIterator<UniParcEntry> entries = queryService.getEntryIterator(proteinQuery);
        System.out.println("Entries with active xref 'p99999' = " + entries.getResultSize());

        // Any Cross reference query
        Query smartQuery = UniParcQueryBuilder.buildCrossReferenceQuery("P99999");
        EntryIterator<UniParcEntry> iterator = queryService.getEntryIterator(smartQuery);
        System.out.println("Entries with any(active/deleted) xref 'p99999' = " + iterator.getResultSize());

        // Any Cross reference query
        Query crc64Query = UniParcQueryBuilder.buildCrc64Query("009F5858F4BFB178");
        iterator = queryService.getEntryIterator(crc64Query);
        System.out.println("Entries with matching CRC64 = " + iterator.getResultSize());

        // Full Text query
        Query fullText = UniParcQueryBuilder.buildSequenceLengthQuery(155l, 200l);
        iterator = queryService.getEntryIterator(fullText);
        System.out.println("Entries where length of sequence is between 155 and 200 = " + iterator.getResultSize());
    }
}
