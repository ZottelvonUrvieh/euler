import java.util.*;

public class evenFibo {
	public static void main(String[] args){

		System.out.println(solution());
	}
	
	private static int solution() {
		int answer = 0;
		int current,last,lastlast;
		current = last = lastlast = 1;
		while (last + current < 4000000){
			lastlast = last;
			last = current;
			current = lastlast + last;
			if (current%2 ==0){
				answer += current;
			}
		}
		return answer;
	}
}
