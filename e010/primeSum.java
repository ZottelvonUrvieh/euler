package e010;

public class primeSum {

	public static void main(String[] args) {
		
		System.out.println(sumPrimes(2000000));
	}
	
	public static long sumPrimes(int amount){
		boolean[] primes = primes(amount);
		long primeSum = 0;
		for (int i = 0; i < amount; i++){
			if (!primes[i]){
				primeSum += i;
			}
		}
		return primeSum;
	}
	
	public static boolean[] primes(final int max) {
	    final boolean[] compositeCandidates = new boolean[max];

	    final double maxRoot = Math.sqrt(max);
	    for (int candidate = 2; candidate < maxRoot; candidate++) {
	        if (!compositeCandidates[candidate]) {
	            for (int multiples = 2 * candidate; multiples < max; multiples += candidate) {
	                compositeCandidates[multiples] = true;
	            }
	        }
	    }
	    compositeCandidates[1] = true;	    
	    return compositeCandidates;
	}
}
