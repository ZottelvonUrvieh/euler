
public class palindromeProduct {

	public static void main(String[] args) {
		System.out.println(drome(3));
	}

	public static String drome (int digits){
		int lowest = 10 ^ digits;
		int highest = 0;
		int curHigh = 0;
		int numA = 0;
		int numB = 0;
		for (int i = 0; i < digits; i++){
			highest += 9 * (Math.pow(10, i));
		}
		
		for (int i = lowest; i <= highest; i++){
			for (int k = lowest; k <= highest; k++){
				if (curHigh < i*k && i*k == revertInt(i*k)){
					curHigh = i*k;
					numA = i;
					numB =k;
				}
			}
		}
		
		return "" + numA*numB + " = " + numA + " * " + numB;
	}
	
	public static int revertInt(int number){
		int answer = 0;
		while( number != 0 )
	      {
	          answer = answer * 10;
	          answer = answer + number%10;
	          number = number/10;
	      }
		return answer;
	}
}
