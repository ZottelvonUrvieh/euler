import java.util.*;


public class largestPrime {
	
	public static void main(String[] args) {
		
		long input = 600851475143l;
		List<Long> answer = primmes(20);
		System.out.println("done");
		for (long longi : answer) {
			System.out.println(longi);
		}
		//{
//			System.out.println(l);
//		}
	}

	private static List<Long> prims(long input) {
		List<Long> answer = new ArrayList<Long>();
		
		answer.add(input);
		return answer;
	}
	
	public static List<Long> primmes(int n)
	{
		List<Long> list = new ArrayList<Long>();
		List<Long> answer = new ArrayList<Long>();
		list.add(2l);
		for (int i = 1; i < n/2; i++)
		{
			list.add((long)(2*i+1));
		}
		

		answer.add(2l);
		for (long i = 3; i < Math.sqrt(n); i++)
		{
			answer.add(i);
			while (list.size() > 0) {
				c
				if (cur%i == 0){
					list.remove(cur);
				}
			}
		}
		return answer;
	}
	
	public static List<Integer> primes(int n)
	{
		if (n < 2)
		{
			return new ArrayList<Integer>();
		}
		char[] is_composite = new char[(n - 2 >> 5) + 1];
		final int limit_i = n - 2 >> 1, limit_j = 2 * limit_i + 3;
		// boolean[] is_composite = new boolean[n - 2 >> 1];
		List<Integer> results = new ArrayList<>((int) Math.ceil(1.25506 * n / Math.log(n)));
		results.add(2);
		for (int i = 0; i < limit_i; ++i)
		{
			if ((is_composite[i >> 4] & 1 << (i & 0xF)) == 0)
			{
				results.add(2 * i + 3);
				for (long j = 4L * i * i + 12L * i + 9; j < limit_j; j += 4 * i + 6)
				{
					is_composite[(int) (j - 3L >> 5)] |= 1 << (j - 3L >> 1 & 0xF);
				}
			}
		}
		return results;
	}

}
