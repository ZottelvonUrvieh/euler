
public class sumSquareDiff {
	public static void main(String[] args) {
		System.out.println(sumSquareDiff(100));
	}
	
	public static long sumSquareDiff(int max){
		long first = 0;
		long second = 0;
		for (int i = 1; i <= max; i++){
			first += i*i;
		}
		
		for (int i = 0; i <= max; i++) {
			second += i;
		}
		second *= second;
		return second - first;
	}
}

