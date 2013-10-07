package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.util.ArrayList;
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

public class EntryListQueryExample {
	
	public static void main(String[] args) throws Exception{
		get4Entries();
		get50Entries();
	}

	private static void get4Entries()  throws Exception{
		long start = System.currentTimeMillis();

		// Create UniProt query service
		UniProtQueryService uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();

		//Create a list of accession numbers
		List<String> accList = new ArrayList<String>();
		accList.add("O60243");
		accList.add("Q8IZP7");
		accList.add("P02070");
		//Will be working from the release 9.7
		accList.add("Q4R572-1");

		Query query = UniProtQueryBuilder.buildIDListQuery(accList);

		EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);
		for (UniProtEntry entry : entries) {
			System.out.println("entry.getUniProtId() = " + entry.getUniProtId());
		}
		long end = System.currentTimeMillis();
		long l = end - start;
		System.out.println(l/1000);
	}



	private static void get50Entries() throws Exception{
		long start = System.currentTimeMillis();

		// Create UniProt query service
		UniProtQueryService uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();

		//Create a list of accession numbers
		List<String> accList = new ArrayList<String>();
		accList.add("O60243"); accList.add("P29305"); accList.add("Q9SAR0"); accList.add("Q8X607"); accList.add("Q5QWZ3");
		accList.add("Q8IZP7"); accList.add("P68250"); accList.add("Q06402"); accList.add("Q487A4"); accList.add("Q124G2");
		accList.add("P02070"); accList.add("P31946"); accList.add("P63102"); accList.add("P16909"); accList.add("Q8KLU8");
		accList.add("Q4R572"); accList.add("Q9CQV8"); accList.add("Q63Y16"); accList.add("A2SL82"); accList.add("Q9I3W8");
		accList.add("P42644"); accList.add("P68251"); accList.add("Q39CF3"); accList.add("P29361"); accList.add("Q3KGE4");
		accList.add("P48349"); accList.add("P35213"); accList.add("Q2T1P5"); accList.add("A1AII6"); accList.add("A1VJF1");
		accList.add("Q06967"); accList.add("P62261"); accList.add("P63104"); accList.add("Q0TA53"); accList.add("Q6D012");
		accList.add("P29307"); accList.add("P62258"); accList.add("Q47BD0"); accList.add("Q1R3T1"); accList.add("Q02JM4");
		accList.add("P29308"); accList.add("P62259"); accList.add("Q146J2"); accList.add("P11071"); accList.add("Q6LUQ1");
		accList.add("Q41246"); accList.add("P63103"); accList.add("P29310"); accList.add("P63101"); accList.add("Q7MZA0");
		Query query = UniProtQueryBuilder.buildIDListQuery(accList);

		EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);
		for (UniProtEntry entry : entries) {
			System.out.println("entry.getUniProtId() = " + entry.getUniProtId());
		}
		long end = System.currentTimeMillis();
		long l = end - start;
		System.out.println(l/1000);
	}

}
