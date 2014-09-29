import java.util.*;


public class largestPrime {
	
	public static void main(String[] args) {
		
		long input = 600851475143l;
		System.out.println("doing...");
		List<Long> answer = primesList(200000);
		System.out.println("done");
		for (long longi : answer) {
			System.out.println(longi);
		}
		//{
//			System.out.println(l);
//		}
	}
	
	private static List<Long> primesList(long input){
		List<Long> inputList = new ArrayList<Long>();
		List<Long> curPrimes = new ArrayList<Long>();
		List<Long> answer = new ArrayList<Long>();
		
		for (long i = 1; i <= input/2; i++){
			inputList.add(i * 2 + 1);		
		}
		
		//testing ---
		
		while (inputList.size() > 0){
			curPrimes.add(inputList.get(0));
			inputList.remove(0);
			int inpLenght = inputList.size();
			long curPrime = curPrimes.get(curPrimes.size()-1);
			for (int i = 0; i < inpLenght; i++){
				if (inputList.get(i) % curPrime == 0){
					inputList.remove(i);
					inpLenght--;
				}
			}
		}
		
		//end-testing ---
		
		return curPrimes;
	}

	private static List<Long> primes(long input) {
		List<Long> answer = new ArrayList<Long>();
		
		answer.add(input);
		return answer;
	}
}
