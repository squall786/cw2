import java.io.*;
import java.util.Arrays;

public class Main {

	private static final String Filetrain = "cw2Dataset1.csv";
	private static final String Filetest = "cw2Dataset2.csv";
	static int[][] trainMat=new int[2810][65];
	static int[][] testMat=new int[2810][65];
	static double correctResult = 0;
	static int eachFold = 0;
	static int functionCount = 0;
	static double oneFold = 0;
	static double twoFold = 0;
	
	public static void main(String[] args) throws IOException {
		readFile(Filetest,trainMat);
		readFile(Filetrain, testMat);
		findclosest(eachFold);
		//System.out.println("Success rate eachfold is "+ ((eachFold/2810)*100 ));
		readFile(Filetest,testMat);
		readFile(Filetrain, trainMat);
		findclosest(eachFold);
		//System.out.println("Success rate eachfold is "+ twoFold);
		System.out.println("Average of two-fold test is \t "+ ((correctResult/(2810*2))*100 ));			
	}
	
	public static void findclosest(double counter) {
		functionCount++;
		//float num = ((float)2/((float)trainMat.length);				
		for (int i=0; i<testMat.length; i++){
			int [] testDigit = testMat[i];
			double closeDistance =999999;
			int result = 0;			
			for (int j=0; j<trainMat.length; j++){
				int [] trainDigit = trainMat[j];
				double distance = computeDistance(testMat[i], trainDigit);
				if (distance <= closeDistance ){
					closeDistance = distance;
					result = trainDigit[64];
				}	
			}
			
			if (result == testDigit[64]){
				correctResult++;
				eachFold++;
			}
			//System.out.println("Result--" + result + "--Expected--" + testDigit[64] +"correct result is " + correctResult/2);
		}
		
		if(functionCount == 1) {
			oneFold = eachFold;
			eachFold = 0;
		}
		else if (functionCount == 2) {
			twoFold = eachFold;
		}
		System.out.println("First fold is "+(oneFold/2810)*100 +" and Second fold is " + (twoFold/2810)*100);
		
	}
	//function for reading-in the files
	private static void readFile(String path, int[][]matrix) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		int lineCounter = 0;
		while ((line = reader.readLine()) != null) {
			String[] data = line.split(",");
			for (int i = 0; i < 65; i++) {
				matrix[lineCounter][i] = Integer.parseInt(data[i]);
			}
			lineCounter++;
		}
		
	}

	// calculating distances for rows
	private static double computeDistance(int[] w, int[] x){
		double sum = 0; 
		for (int i=0; i<w.length-1; i++ ){
			sum += ((w[i]-x[i])*(w[i]-x[i]));			
		}
		return Math.sqrt(sum);		
	}
}
