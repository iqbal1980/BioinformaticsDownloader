package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.uniprot.citations.JournalArticle;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.go.Go;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;

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

public class AttributeExample {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception{



        //Create entry retrieval service
        EntryRetrievalService entryRetrievalService = UniProtJAPI.factory.getEntryRetrievalService();

        //Retrieve Go Terms for an entry by its accession number
        //noinspection unchecked
        List<Go> goTerms = (List<Go>) entryRetrievalService.getUniProtAttribute("Q12345", "ognl:goTerms");

        System.out.println("number terms = " + goTerms.size());

        // iterator over them
        for (Go go : goTerms) {
            System.out.println("go.getGoId().getValue() = " + go.getGoId().getValue());
        }

        // List of all Journal article for a given entry. Note how we use the @ to access static methods and enumarations.
        List<JournalArticle> citations = (List<JournalArticle>) entryRetrievalService.getUniProtAttribute("P99999", "ognl:getCitations(@uk.ac.ebi.kraken.interfaces.uniprot.citations.CitationTypeEnum@JOURNAL_ARTICLE)");

        System.out.println("number journal artical citations = " + citations.size());

        // iterator over them
        for (JournalArticle cit : citations) {
            System.out.println("cit.getTitle().getValue() = " + cit.getTitle().getValue());
            System.out.println("number of authors= " + cit.getAuthors().size());
        }
    }
}
