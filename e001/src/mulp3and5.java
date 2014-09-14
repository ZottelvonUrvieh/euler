import java.util.*;

public class mulp3and5 {
	public static void main(String[] args){
		List<Integer> array = new ArrayList<Integer>();
		for (int i = 1; i < 1000; i++) {
			if (i%3== 0 | i%5 == 0 )
					array.add(i);
			
		}
		int sum = 0;
		for (Integer integer : array) {
			sum += integer;
		}
        System.out.println(sum);
   }
}
