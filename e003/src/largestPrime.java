import java.util.*;


public class largestPrime {
	
	public static void main(String[] args) {
		
		long input = 600851475143l;
		System.out.println("doing...");
		List<Integer> answer = new ArrayList<Integer>();
//		answer.add((long) nthPrime(1000000));
		answer = primes(input);
		System.out.println("done");
		System.out.println(input + " = ");
		for (long longi : answer) {
			System.out.println(longi);
		}
		//{
//			System.out.println(l);
//		}
	}
	
	private static List<Integer> primesList(int input){
//		List<Integer> inputList = new ArrayList<Integer>();
		List<Integer> curPrimes = new ArrayList<Integer>();		
//		for (long i = 1; i <= input/2; i++){
//			inputList.add(i * 2 + 1);		
//		}
//		
//		while (inputList.size() > 0){
//			curPrimes.add(inputList.get(0));
//			inputList.remove(0);
//			int inpLenght = inputList.size();
//			long curPrime = curPrimes.get(curPrimes.size()-1);
//			for (int i = 0; i < inpLenght; i++){
//				if (inputList.get(i) % curPrime == 0){
//					inputList.remove(i);
//					inpLenght--;
//				}
//			}
//		}
		for (int i = 1; i <= input; i++){
			curPrimes.add(nthPrime(i));
		}
		return curPrimes;
	}

	private static List<Integer> primes(long input) {
		List<Integer> answer = new ArrayList<Integer>();
		long inputSave = input;
		for (int i = 2; i < input + 1; i++){
			if (input % i == 0){
				answer.add(i);
				input = input / i;
				int curMult = 1;
				for (int a = 0; a < answer.size(); a++){
					curMult *= answer.get(a);
				}
				if (curMult == inputSave){
					break;
				}
				i = 1;
			}
		}
		return answer;
	}
	
	public static int nthPrime(int n) {
	    if (n < 2) return 2;
	    if (n == 2) return 3;
	    int limit, root, count = 1;
	    limit = (int)(n*(Math.log(n) + Math.log(Math.log(n)))) + 3;
	    root = (int)Math.sqrt(limit) + 1;
	    limit = (limit-1)/2;
	    root = root/2 - 1;
	    boolean[] sieve = new boolean[limit];
	    for(int i = 0; i < root; ++i) {
	        if (!sieve[i]) {
	            ++count;
	            for(int j = 2*i*(i+3)+3, p = 2*i+3; j < limit; j += p) {
	                sieve[j] = true;
	            }
	        }
	    }
	    int p;
	    for(p = root; count < n; ++p) {
	        if (!sieve[p]) {
	            ++count;
	        }
	    }
	    return 2*p+1;
	}
}
