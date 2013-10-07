package uniprot.examples;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.model.blast.JobStatus;
import uk.ac.ebi.kraken.model.blast.parameters.DatabaseOptions;
import uk.ac.ebi.kraken.model.blast.parameters.MaxNumberResultsOptions;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryService;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastData;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastInput;


public class UniprotBlastResultLimitExample {
	public static void main(String[] args) throws Exception {
		UniProtQueryService service =
				UniProtJAPI.factory.getUniProtQueryService();

		// set limit to 150 results

		BlastInput input = new BlastInput(
				DatabaseOptions.UNIPROTKB,

				"MEEPQSDPSVEPPLSQETFSDLWKLLPENNVLSPLPSQAMDDLMLSPDDIEQWFTEDPGPDEAPRMPEAAPPVAPAPAAPTPAAPAPAPSWPLSSSVPSQKTYQGSYGFRLGFLHSGTAKSVTCTYSPALNKMFCQLAKTCPVQLWVDSTPPPGTRVRAMAIYKQSQHMTEVVRRCPHHERCSDSDGLAPPQHLIRVEGNLRVEYLDDRNTFRHSVVVPYEPPEVGSDCTTIHYNYMCNSSCMGGMNRRPILTIITLEDSSGNLLGRNSFEVRVCACPGRDRRTEEENLRKKGEPHHELPPGSTKRALPNNTSSSPQPKKKPLDGEYFTLQIRGRERFEMFRELNEALELKDAQAGKEPGGSRAHSSHLKSKKGQSTSRHKKLMFKTEGPDSD",
				MaxNumberResultsOptions.ONE_HUNDRED_FIFTY
				);

		String jobid = service.submitBlast(input);
		while (!(service.checkStatus(jobid) == JobStatus.FINISHED)) {
			try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
		}

		BlastData<UniProtEntry> blastResult = service.getResults(jobid);
		System.out.println(String.format("results limit = 150, results size = %d", blastResult.getBlastHits().size()));

		// set limit to 500 results

		input = new BlastInput(
				DatabaseOptions.UNIPROTKB,

				"MEEPQSDPSVEPPLSQETFSDLWKLLPENNVLSPLPSQAMDDLMLSPDDIEQWFTEDPGPDEAPRMPEAAPPVAPAPAAPTPAAPAPAPSWPLSSSVPSQKTYQGSYGFRLGFLHSGTAKSVTCTYSPALNKMFCQLAKTCPVQLWVDSTPPPGTRVRAMAIYKQSQHMTEVVRRCPHHERCSDSDGLAPPQHLIRVEGNLRVEYLDDRNTFRHSVVVPYEPPEVGSDCTTIHYNYMCNSSCMGGMNRRPILTIITLEDSSGNLLGRNSFEVRVCACPGRDRRTEEENLRKKGEPHHELPPGSTKRALPNNTSSSPQPKKKPLDGEYFTLQIRGRERFEMFRELNEALELKDAQAGKEPGGSRAHSSHLKSKKGQSTSRHKKLMFKTEGPDSD",
				MaxNumberResultsOptions.FIVE_HUNDRED
				);

		jobid = service.submitBlast(input);
		while (!(service.checkStatus(jobid) == JobStatus.FINISHED)) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			}
		}

		blastResult = service.getResults(jobid);
		System.out.println(String.format("results limit = 500, resultssize = %d", blastResult.getBlastHits().size()));

	}

}
