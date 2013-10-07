package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class CommandLineExample  {
    private static String ac;
    private static String queryStr;


    public static void main2(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage:  <expression> <ac>");
            System.exit(1);
        }

        String expression = args[0];
        String ac = args[1].toUpperCase();

        Object o = UniProtJAPI.factory.getEntryRetrievalService().getUniProtAttribute(ac, expression);
        System.out.println(o);

    }


    public static void main(String[] args) {

        System.out.println("This command line example querries UniProt data using expression langauages (ognl, jexl)");
        boolean cont = true;
        String last = "Entry";
        while (cont) {
            System.out.print("Search or Entry (" + last + "):");

            //  open up standard input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            //  read the type from the command-line; need to use try/catch with the
            //  readLine() method
            try {
                String acStr = br.readLine();
                if (acStr == null || acStr.length() == 0)
                    acStr = last;

                if (acStr.equalsIgnoreCase("search")) {
                    cont = CommandLineExample.Search();
                    last = "Search";
                } else if (acStr.equalsIgnoreCase("entry")) {
                    cont = CommandLineExample.Entry();
                    last = "Entry";
                }
            } catch (IOException ioe) {
                System.out.println("IO error trying to read accession!");
                System.exit(1);
            }
        }
    }


    public static boolean Entry() {

        // Create UniProt query service
        String expression = null;

        String display = (CommandLineExample.ac == null) ? "Entry uniprot accession:" : "Entry uniprot accession(" + CommandLineExample.ac + "):";
        //  prompt the user to enter their name
        System.out.print(display);

        //  open up standard input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //  read the accession from the command-line; need to use try/catch with the
        //  readLine() method
        try {
            String acStr = br.readLine();
            if (acStr != null && acStr.length() > 0)
                CommandLineExample.ac = acStr;
        } catch (IOException ioe) {
            System.out.println("IO error trying to read accession!");
            System.exit(1);
        }
        //  prompt the user to enter their name
        System.out.print("Enter uniprot expression: ");

        //  open up standard input
        br = new BufferedReader(new InputStreamReader(System.in));

        //  read the expression from the command-line; need to use try/catch with the
        //  readLine() method
        try {
            expression = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read expression!");
            System.exit(1);
        }
        try {

            System.out.println(UniProtJAPI.factory.getEntryRetrievalService().getUniProtAttribute(CommandLineExample.ac, expression));


        } catch (Exception e) {
            System.out.println("ERROR OCURRED:" + e);
        }
        System.out.print("again (Y):");
        //  open up standard input
        br = new BufferedReader(new InputStreamReader(System.in));

        //  read the yes/no from the command-line; need to use try/catch with the
        //  readLine() method
        try {
            String againStr = br.readLine();
            if ((againStr != null && againStr.length() > 0) && (!(againStr.equalsIgnoreCase("Y"))))
                return false;

        } catch (IOException ioe) {
            System.exit(1);
        }
        return true;
    }

    public static boolean Search() {

        UniProtQueryService uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();
        // Create UniProt query service
        String expression = null;

        String display = (CommandLineExample.queryStr == null) ? "Entry a query:" : "Entry a query(" + CommandLineExample.queryStr + "):";
        //  prompt the user to enter their name
        System.out.print(display);

        //  open up standard input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //  read the query from the command-line; need to use try/catch with the
        //  readLine() method
        try {
            String acStr = br.readLine();
            if (acStr != null && acStr.length() > 0)
                CommandLineExample.queryStr = acStr;
        } catch (IOException ioe) {
            System.out.println("IO error trying to read accession!");
            System.exit(1);
        }
        //  prompt the user to enter their name
        System.out.print("Enter uniprot expression: ");

        //  open up standard input
        br = new BufferedReader(new InputStreamReader(System.in));

        //  read the expression from the command-line; need to use try/catch with the
        //  readLine() method
        try {
            expression = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read expression!");
            System.exit(1);
        }
        try {

            Query query = UniProtQueryBuilder.buildFullTextSearch(CommandLineExample.queryStr);
            AttributeIterator<UniProtEntry> it = uniProtQueryService.getAttributes(query, expression);
            int total = it.getResultSize();
            int counter=0;
            boolean all = false;
            for (Attribute a : it) {
                System.out.print(a.getAccession() + ":");
                System.out.println(a.getValue());
                counter++;

                if (counter%100==0 && all == false){
                    System.out.print(counter + " out of " +total +" processed.Either show all(A), abort(B), or default to continue(C):");
                    //  open up standard input
                    BufferedReader bre = new BufferedReader(new InputStreamReader(System.in));
                    String cont = null;
                    try {
                        cont = bre.readLine();
                        if ((cont != null && cont.length() > 0) && ((cont.equalsIgnoreCase("A"))))
                            all = true;
                        else if ((cont != null && cont.length() > 0) && ((cont.equalsIgnoreCase("B"))))
                            break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }




        } catch (Exception e) {
            System.out.println("ERROR OCURRED:" + e);
        }
        System.out.print("again (Y):");
        //  open up standard input
        br = new BufferedReader(new InputStreamReader(System.in));

        //  read the yes/no from the command-line; need to use try/catch with the
        //  readLine() method
        try {
            String againStr = br.readLine();
            if ((againStr != null && againStr.length() > 0) && (!(againStr.equalsIgnoreCase("Y"))))
                return false;

        } catch (IOException ioe) {
            System.exit(1);
        }
        return true;
    }
}
