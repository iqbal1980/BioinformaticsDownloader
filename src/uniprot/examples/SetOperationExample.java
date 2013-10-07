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

public class SetOperationExample {

    public static void main(String[] args) throws Exception {

		UniProtQueryService uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();

         // build a human iterator
        EntryIterator<UniProtEntry> humanIterator = uniProtQueryService.getEntryIterator(UniProtQueryBuilder.buildOrganismQuery("human"));
        //.... do something
        System.out.println(humanIterator.getResultSize());

        // Build a kinase iterator
        EntryIterator<UniProtEntry> kinaseIterator = uniProtQueryService.getEntryIterator(UniProtQueryBuilder.buildProteinNameQuery("kinase"));
        //.... do something
        System.out.println(kinaseIterator.getResultSize());

        // now I want to intersect these two sets
        EntryIterator<UniProtEntry> intersection = uniProtQueryService.getEntryIterator(humanIterator, SetOperation.And,kinaseIterator);
        //.... do something
        System.out.println(intersection.getResultSize());
    }
}
