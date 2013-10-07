package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.go.Go;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.util.List;

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

public class AttributeIteratorExample {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {

        //Create query service
        UniProtQueryService service = UniProtJAPI.factory.getUniProtQueryService();

        // Build a query
        Query query = UniProtQueryBuilder.buildProteinNameQuery("Mitogen activated");

        //Retrieve Go Terms for an entry by its accession number
        AttributeIterator<UniProtEntry> iter = service.getAttributes(query, "ognl:goTerms");

        System.out.println("number matches = " + iter.getResultSize());

        // iterator over them
        for (Attribute att : iter) {
            System.out.println("UniProt Accession " + att.getAccession());
            List<Go> goTerms = (List<Go>) att.getValue();
            for (Go go : goTerms) {
                System.out.println("go.getGoId().getValue() = " + go.getGoId().getValue());
            }
        }

    }
}
