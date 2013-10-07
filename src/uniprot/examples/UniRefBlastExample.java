package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.blast.LocalAlignment;
import uk.ac.ebi.kraken.interfaces.uniref.UniRefEntry;
import uk.ac.ebi.kraken.model.blast.JobStatus;
import uk.ac.ebi.kraken.model.blast.parameters.DatabaseOptions;
import uk.ac.ebi.kraken.model.blast.parameters.SortOptions;
import uk.ac.ebi.kraken.model.blast.parameters.StatsOptions;
import uk.ac.ebi.kraken.uuw.services.remoting.UniRefQueryService;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastData;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastHit;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastInput;

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

public class UniRefBlastExample {

    public static void main(String[] args) throws Exception {

        String sequence = ">Q99683|M3K5_HUMAN Mitogen-activated protein kinase kinase kinase 5 - Homo sapiens\n" +
                "MSTEADEGITFSVPPFAPSGFCTIPEGGICRRGGAAAVGEGEEHQLPPPPPGSFWNVESA\n" +
                "AAPGIGCPAATSSSSATRGRGSSVGGGSRRTTVAYVINEASQGQLVVAESEALQSLREAC\n" +
                "ETVGATLETLHFGKLDFGETTVLDRFYNADIAVVEMSDAFRQPSLFYHLGVRESFSMANN\n" +
                "IILYCDTNSDSLQSLKEIICQKNTMCTGNYTFVPYMITPHNKVYCCDSSFMKGLTELMQP\n" +
                "NFELLLGPICLPLVDRFIQLLKVAQASSSQYFRESILNDIRKARNLYTGKELAAELARIR\n" +
                "QRVDNIEVLTADIVINLLLSYRDIQDYDSIVKLVETLEKLPTFDLASHHHVKFHYAFALN\n" +
                "RRNLPGDRAKALDIMIPMVQSEGQVASDMYCLVGRIYKDMFLDSNFTDTESRDHGASWFK\n" +
                "KAFESEPTLQSGINYAVLLLAAGHQFESSFELRKVGVKLSSLLGKKGNLEKLQSYWEVGF\n" +
                "FLGASVLANDHMRVIQASEKLFKLKTPAWYLKSIVETILIYKHFVKLTTEQPVAKQELVD\n" +
                "FWMDFLVEATKTDVTVVRFPVLILEPTKIYQPSYLSINNEVEEKTISIWHVLPDDKKGIH\n" +
                "EWNFSASSVRGVSISKFEERCCFLYVLHNSDDFQIYFCTELHCKKFFEMVNTITEEKGRS\n" +
                "TEEGDCESDLLEYDYEYDENGDRVVLGKGTYGIVYAGRDLSNQVRIAIKEIPERDSRYSQ\n" +
                "PLHEEIALHKHLKHKNIVQYLGSFSENGFIKIFMEQVPGGSLSALLRSKWGPLKDNEQTI\n" +
                "GFYTKQILEGLKYLHDNQIVHRDIKGDNVLINTYSGVLKISDFGTSKRLAGINPCTETFT\n" +
                "GTLQYMAPEIIDKGPRGYGKAADIWSLGCTIIEMATGKPPFYELGEPQAAMFKVGMFKVH\n" +
                "PEIPESMSAEAKAFILKCFEPDPDKRACANDLLVDEFLKVSSKKKKTQPKLSALSAGSNE\n" +
                "YLRSISLPVPVLVEDTSSSSEYGSVSPDTELKVDPFSFKTRAKSCGERDVKGIRTLFLGI\n" +
                "PDENFEDHSAPPSPEEKDSGFFMLRKDSERRATLHRILTEDQDKIVRNLMESLAQGAEEP\n" +
                "KLKWEHITTLIASLREFVRSTDRKIIATTLSKLKLELDFDSHGISQVQVVLFGFQDAVNK\n" +
                "VLRNHNIKPHWMFALDSIIRKAVQTAITILVPELRPHFSLASESDTADQEDLDVEDDHEE\n" +
                "QPSNQTVRRPQAVIEDAVATSGVSTLSSTVSHDSQSAHRSLNVQLGRMKIETNRLLEELV\n" +
                "RKEKELQALLHRAIEEKDQEIKHLKLKSQPIEIPELPVFHLNSSGTNTEDSELTDWLRVN\n" +
                "GADEDTISRFLAEDYTLLDVLYYVTRDDLKCLRLRGGMLCTLWKAIIDFRNKQT";


        BlastInput input = new BlastInput(DatabaseOptions.UNIREF_100, sequence, StatsOptions.POISSON, SortOptions.HIGHSCORE);
        // use factory to get access to a uniref query service
        UniRefQueryService service = UniProtJAPI.factory.getUniRefQueryService();
        // the job is returned from the service.
        String jobid = service.submitBlast(input);

        System.out.println("jobid = " + jobid);

        while (!(service.checkStatus(jobid) == JobStatus.FINISHED)) {
            try {
                System.out.println("jobId = " + jobid);
                System.out.println("is running,");
                System.out.println("sleeping for 5 seconds");
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        BlastData<UniRefEntry> blastResult = service.getResults(jobid);
        System.out.println("blastResult.getHits().size() = " + blastResult.getBlastHits().size());

        for (BlastHit<UniRefEntry> hit : blastResult.getBlastHits()) {
            if (hit.getEntry()!=null)
                System.out.println("Entry name = " + hit.getEntry().getName().getValue());
            else
                System.out.println("hit.getHit().getAc() = " + hit.getHit().getAc());

            for (LocalAlignment align : hit.getHit().getAlignments()) {
                System.out.println("align.getScore() = " + align.getScore());
            }
        }

    }
}
