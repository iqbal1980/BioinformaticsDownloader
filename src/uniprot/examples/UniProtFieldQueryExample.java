package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
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

public class UniProtFieldQueryExample {
	public static void main(String[] args) throws Exception {

		UniProtQueryService queryService = UniProtJAPI.factory.getUniProtQueryService();

		//Build query for Protein Name
		Query query1 = UniProtQueryBuilder.buildProteinNameQuery("Clpc");

		EntryIterator<UniProtEntry> entries = queryService.getEntryIterator(query1);

		System.out.println("Number of entries with protein name Clpl = " + entries.getResultSize());

		for (UniProtEntry entry : entries) {
			System.out.println("entry.getPrimaryUniProtAccession() = " + entry.getPrimaryUniProtAccession());
		}

		//Build query for EC number
		Query query2 = UniProtQueryBuilder.buildECNumberQuery("3.1.6.-");
		Query query = UniProtQueryBuilder.setReviewedEntries(query2);

		//You can retrieve just accession numbers
		AccessionIterator<UniProtEntry> iterator = queryService.getAccessions(query);

		System.out.println("Number of entries with EC 3.1.6.- = " + iterator.getResultSize());

		//Build query to select entries which were updated during the certain period
		Date startDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH).parse("01/09/2007");
		Date endDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH).parse("01/23/2007");

		Query dateQuery = UniProtQueryBuilder.buildCreatedQuery(startDate, endDate);

		//Query for SwissProt data set
		Query reviewedQuery = UniProtQueryBuilder.setReviewedEntries(dateQuery);

		AccessionIterator<UniProtEntry> accessions = queryService.getAccessions(reviewedQuery);
		System.out.println("Created entries = " + accessions.getResultSize());

	}
}
