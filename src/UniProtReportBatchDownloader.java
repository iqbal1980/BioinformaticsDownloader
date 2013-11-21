
//C:\Users\addou\Desktop\


public class UniProtReportBatchDownloader {
	
	public static void main(String[] args) {
		UniProtDownloadManager uniProtDownloadManager = new UniProtDownloadManager("spondin");
		
		uniProtDownloadManager.generateReviewedEntriesReport();
		uniProtDownloadManager.generateNonReviewedEntriesReport();
	}

 

}
