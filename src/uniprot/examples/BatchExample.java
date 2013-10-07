package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
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

public class BatchExample {

    public static void main(String[] args) {

        UniProtQueryService service = UniProtJAPI.factory.getUniProtQueryService();
        System.out.println("Initialized Services");

        // Retrieve all UniProtEntries containing the DatabaseCrossReference PF00069
        Query query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(DatabaseType.PFAM, "PF00069");
        EntryIterator<UniProtEntry> entryIterator = service.getEntryIterator(query);

        System.out.println("Number of UniProtEntries containing PF00069: " + entryIterator.getResultSize());

        long time = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            entryIterator.next();
            if (i % 50 == 0) {
                long temp = System.currentTimeMillis() - start;
                System.out.println("Time to retrieve 50 entries is " + temp + " ms");
                time = time + temp;
                start = System.currentTimeMillis();
            }
        }
        System.out.println("Time to Retrieve 200 UniProtEntries " + (time / 1000) + " s");
    }
}
