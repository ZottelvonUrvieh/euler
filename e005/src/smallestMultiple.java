
public class smallestMultiple {
	public static void main(String[] args) {
		int intervall = 20;
		System.out.println((multiple(intervall) - intervall));
	}
	
	public static long multiple(int intervall){
		long answer = intervall;
		boolean breaker = false;
		while (!breaker){
			breaker = true;
			for (long i = 1; i <= intervall; i++){
				if (answer%i != 0){
					breaker &= false;
					break;
				}				
			}
//			System.out.println(answer);
			answer += intervall;
		}
		return answer;
	}

}
