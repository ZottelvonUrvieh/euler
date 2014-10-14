import java.util.BitSet;


public class bint {
	BitSet bits;
	BitSet decimals;
	BitSet negative;
	
	public bint() {
		this.bits = new BitSet(0);
		this.decimals = new BitSet(0);
		this.negative = new BitSet(1);
		negative.set(0,false);
	}
	
	public bint(BitSet bitset) {
		this.bits = bitset;
		this.negative = new BitSet(1);
		negative.set(0,false);
	}
	
	public bint(BitSet bitset, BitSet decimals) {
		this.bits = bitset;
		this.decimals = decimals;
	}
	
	public bint(BitSet bitset, BitSet decimals, boolean negative) {
		this.bits = bitset;
		this.decimals = decimals;
		this.negative = new BitSet(1);
		if (negative) this.negative.set(0,true);
	}
	
	public bint(BitSet bitset, boolean negative) {
		this.bits = bitset;
		this.negative = new BitSet(1);
		if (negative) this.negative.set(0,true);
	}
	
	/**
	 * Creates new big numbers e.g. "-1993,452" (biggest: max_int 9's)
	 * Example call: bint a = new bint("-1993,452", 10);
	 * @param number
	 * @param type (possibilities: 10 for decimal, 2 for binary)
	 */
	public bint(String number, int type) {
		String[] numberArray = number.split(",");
		if (type == 10) {
			this.bits = decimalToBitSet(numberArray[0]);
			if (numberArray.length == 2) this.decimals = decimalToBitSet(numberArray[1]);
		}
		else if (type == 2) {
			this.bits = binaryToBitSet(number);
			if (numberArray.length == 2) this.decimals = binaryToBitSet(number);
		}
	}
	
	
	/*
		>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<
	  	>>>>>>>>>>>>>>>>Operations<<<<<<<<<<<<<<
	  	>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<
	*/
	/**
	 * Simply adds a bint number to the other and returns the sum
	 * @param bint number
	 * @return bint sum of the two bint numbers
	 */
	public bint add(bint number2) {
		int maxlenght = 0;
		BitSet bitset = new BitSet();
		boolean carry = false;
		if (this.bits.length() > number2.bits.length()) {
			maxlenght = this.bits.length();
		}
		else {
			maxlenght = number2.bits.length();
		}
		for (int i = 0; i <= maxlenght; i++) {
			int countBool = countBooles(number2.bits.get(i), this.bits.get(i), carry);
			if (countBool == 0) {
				bitset.set(i, false);
				carry = false;
			}
			else if(countBool == 1) {
				bitset.set(i, true);
				carry = false;
			}
			else if(countBool == 2) {
				bitset.set(i, false);
				carry = true;
			}
			else if(countBool == 3) {
				bitset.set(i, true);
				carry = true;
			}
		}
		if (carry) {
			bitset.set(bitset.length(),true);
		}			
		return new bint(bitset);
	}
	

	public bint sub(bint substract) {
//		int maxlenght = 0;
		BitSet bitset = new BitSet();
		boolean carry = false;
		if (substract.isGreaterThan(this)) {
			bint zero = new bint("0",10);
			return zero;
		}
		for (int i = 0; i <= this.bits.length(); i++) {
			if ((!substract.bits.get(i) && !this.bits.get(i) && !carry)| (substract.bits.get(i) && this.bits.get(i) && !carry) | (!substract.bits.get(i) && this.bits.get(i) && carry)){
				bitset.set(i, false);
				carry = false;
			}
			else if(!substract.bits.get(i) && this.bits.get(i) && !carry) {
				bitset.set(i, true);
				carry = false;
			}
			else if(substract.bits.get(i) && !this.bits.get(i) && carry) {
				bitset.set(i, false);
				carry = true;
			}
			else if ((!substract.bits.get(i) && !this.bits.get(i) && carry) | (substract.bits.get(i) && !this.bits.get(i) && !carry)| (substract.bits.get(i) && this.bits.get(i) && carry)) {
				bitset.set(i, true);
				carry = true;
			}
		}
//		if (carry) {
//			bitset.set(bitset.length(),true);
//		}			
		return new bint(bitset);
	}
	

	
	/**
	 * Multiplies a bint number with the other and returns the product
	 * @param bint multipliedWithThisValue
	 * @return bint product of the two bint numbers
	 */
	public bint mult(bint multip) {
		bint work = new bint();
		bint big = new bint();
		bint small = new bint();
		if (multip.bits.length() > this.bits.length()) {
			big.bits = multip.bits;
			small.bits = this.bits;
		}
		else {
			big.bits = this.bits;
			small.bits = multip.bits;
		}
		
		for (int i = 0; i < small.bits.length(); i++) {
			if (small.bits.get(i)) {
				String newAddNumber = big.toBinaryString();
				for (int shift = 0; shift < i; shift++) {
					newAddNumber += "0";
				}				
				work = work.add(new bint(newAddNumber,2));
			}
		}		
		return work;		
	}
	
	
	
	/**
	 * In process... DONT USE
	 * @param divder DONT USE
	 * @return nothing good jet... DONT USE
	 */
	public bint divide(bint divisor) {
		bint a, aFront, aBack, substract, answer = new bint("0",10), newA = new bint();
		
		a = this;
		int fillZeros, i;
		int pullDown = 0;
		while (a.isGreaterOrEqualThan(divisor)) {
			i = 0;
			fillZeros = 0;
			aFront = a.cutToNthDigit(divisor.bits.length());
			while (divisor.isGreaterThan(aFront)) {
				i++;
				aFront = a.cutToNthDigit(a.bits.length() - i);
				fillZeros++;
			}
			substract = aFront.sub(divisor);	
//			a = a.buildNewA(substract, aFront);
			
			bint tempA = substract;
			for (int k = a.bits.length() - 1 - aFront.bits.length(); k >= 0; k--) {
				pullDown++;
				tempA = tempA.mult(new bint("2",10));
				if (this.bits.get(k)) {
					tempA.bits.set(0,true);
				}
			}
			a = tempA;
			
			fillZeros = a.bits.length() - pullDown - divisor.bits.length();
			
			answer = answer.addZerosAndOne(fillZeros);				
		}		
		return answer;
		
		
//		a = this;
//		int backLenghtBefore = -1;
//		int leadingZeros = 0;
//		while (a.isGreaterOrEqualThan(divisor)) {
//			aFront =  cutForDivisonAFront(a, divisor);
//			aBack = cutForDivisionABack(a, aFront);
//			substract = aFront.sub(divisor);
//			leadingZeros = a.bits.length() - aFront.bits.length() - aBack.bits.length();
//			newA = combineForDivision(substract, aBack, leadingZeros);
//			answer = calcAndConcatinateOnesAndZeros(a, aFront, answer, newA, backLenghtBefore);
//			backLenghtBefore = a.bits.length() - aFront.bits.length();
//			a = newA;
//		}	
//		
//		for (int i = 0; i < leadingZeros; i++) {
//			answer = answer.mult(new bint ("2",10));
//		}
//		return answer;	
	}


	/**
	 * Simple and fast implementation of dividing a bint value by 2
	 * @return bint 
	 */
	public bint divideBy2() {
		bint answer = new bint();
		for (int i = this.bits.length()-1; i > 0; i--) {
			if (this.bits.get(i)) {
				answer.bits.set(i-1);
			}
		}
		return answer;		
	}
	
	/**
	 * Calculates the remainder of division
	 * @param bint moduloThisValue
	 * @return bint remainder of division
	 */
	public bint mod(bint divisor) {
		bint a, aFront, aBack, substract = new bint();
		a = this;
		while (a.isGreaterOrEqualThan(divisor)) {
			aFront =  cutForDivisonAFront(a, divisor);
			aBack = cutForDivisionABack(a, aFront);
			substract = aFront.sub(divisor);
			a = combineForDivision(substract, aBack, a.bits.length() - aFront.bits.length() - aBack.bits.length());
		}				
		return a;
	}
	
	
	public boolean isGreaterOrEqualThan(bint smaller) {
		if (smaller.bits.length() > this.bits.length()){
			return false;
		}
		else if (smaller.bits.length() == this.bits.length()) {
			for (int i = smaller.bits.length() - 1; i >= 0; i--) {
				if (smaller.bits.get(i) && !this.bits.get(i)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isGreaterThan(bint smaller) {
		if (smaller.bits.length() > this.bits.length()){
			return false;
		}
		else if (smaller.bits.length() == this.bits.length()) {
			for (int i = smaller.bits.length() - 1; i >= 0; i--) {
				if (smaller.bits.get(i) && !this.bits.get(i)) {
					return false;
				}
				if (i == 0 && ((smaller.bits.get(i) && this.bits.get(i)) | (!smaller.bits.get(i) && !this.bits.get(i)))) {
					return false;
				}
			}
		}
		return true;
	}

	
	/*
		>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<
		>>>>>>>>>>>>>>>>Conversions<<<<<<<<<<<<<<<
		>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<
	*/
	public String toString() {
		String answer = "0";
		for (int i = this.bits.length()-1; i >= 0; i--) {
			answer = multBy2(answer);
			if(this.bits.get(i)) {
				answer = addOne(answer);
			}
		}
		return answer;
	}
	
	public String toBinaryString() {
		String answer = "";
		for (int i = 0; i < this.bits.length(); i++) {
			if (this.bits.get(i)) {
				answer = "1" + answer;				
			}
			else {
				answer = "0" + answer;
			}
		}
		return answer;
	}
	
	public long toLong() {
		long answer = 0;
		for (int i = 0; i < this.bits.length(); i++) {
			if (this.bits.get(i)) {
				answer += Math.pow(2, i);
			}
		}
		return answer;
	}
	
	
	/*
		>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<
  		>>>>>>>>>>>>>>>>Help-Funcions<<<<<<<<<<<<<<<
  		>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<
	*/

	
	//Constructor Help-Functions
	
	
	private BitSet binaryToBitSet(String number) {
		BitSet answer = new BitSet(number.length());
		for (int i = 0; i < number.length(); i++) {
			String numb = number.substring(number.length() -1 - i, number.length() - i);
			if (numb.equals("1")) {
				answer.set(i,true);
			}
			else {
				answer.set(i,false);
			}
		}
		return answer;
	}	
	
	/**
	 * @param number
	 * @return 
	 */
	private BitSet decimalToBitSet(String number) {
		BitSet answer = new BitSet();
		int index = 0;
		while (number.length() > 1 | !number.endsWith("0")) {
			if (!mod2Test(number)) {
				answer.set(index);
			}
			number = divideBy2(number);
			index++;
		}
		return answer;
	}	

	private bint fillWithBitsOfBint(BitSet bits) {
		bint answer = new bint();
		for(int i = 0; i < bits.length(); i++) {
			if (bits.get(i)) {
				answer.bits.set(i,true);
			}
		}
		return answer;
	}	
	
	
	
	//String "calculating" functions for e.g. toString()
	
	
	private String addOne(String s){
		String[] work = s.split("|");
		String answer = "";
		int carry = 1;
		int index = s.length() - 1;
		while (carry == 1) {
			if (index < 0) break;
			if (Integer.parseInt(work[index]) < 9) {
				work[index] = (Integer.parseInt(work[index]) + 1) + "";
				carry = 0;
			}
			else {
				work[index] = 0 + "";
				carry = 1;
			}
			index--;
		}	
		for (int i = 0; i < work.length; i++) {
			answer += work[i];
		}
		if (carry == 1) {
			answer = 1 + answer;
		}
		return answer;		
	}
	
	private String divideBy2(String number) {
		String answer = "";
		String[] work = number.split("|");
		int uneven = 0;
		for (int i = 0; i < work.length; i++) {
			if (Integer.parseInt(work[i]) %2 == 0) {
				answer += ((Integer.parseInt(work[i]) / 2) + uneven);
				uneven = 0;
			}
			else {
				answer += (Integer.parseInt(work[i]) / 2) + uneven ;
				uneven = 5;
			}
		}
		if (answer.startsWith("0")) {
			answer = answer.replaceFirst("0", "");
		}
		if (answer.equals("")) {
			return 0 + "";
		}
		return answer;
	}
	
	private boolean mod2Test(String number) {
		String[] serialized = number.split("|");
		int length = serialized.length;
		String numb;
		if (length > 1) {
			numb = serialized[length - 2] + serialized[length - 1];
		}
		else {
			numb = serialized[length - 1];
		}
		
		if (Integer.parseInt(numb)%2 == 0) {
			return true;
		}
		return false;
	}
	
	private String multBy2 (String number) {
		String answer = "";
		String[] work = number.split("|");
		int carry = 0;
		for (int i = work.length - 1; i >= 0 ; i--) {
			if ((Integer.parseInt(work[i])*2 + carry) < 10) {
				answer = (Integer.parseInt(work[i])*2 + carry) + answer;
				carry = 0;
			}
			else {
				answer = ((Integer.parseInt(work[i]) - 5)*2 + carry) + answer;
				carry = 1;
			}
		}
		if (carry == 1) {
			answer = 1 + answer;
		}
		return answer;
	}

	
	
	// Dividing and Modulo Help-Functions
	
	
	private bint combineForDivision(bint substract, bint aBack, int backLeadingZeros) {		
		// extending the substract with "0's" on the end to make a addition with aBack possible
		bint two = new bint();
		two.bits.set(1);
		for (int i = 0; i < backLeadingZeros + aBack.bits.length(); i++) {
			substract = substract.mult(two);
		}	
		return substract.add(aBack);
	}
	
	private int countBooles(boolean one, boolean two, boolean three) {
		int amount = 0;
		if (one) amount++;
		if (two) amount++;
		if (three) amount++;
		return amount;
	}
	
	private bint cutForDivisionABack(bint a, bint aFront) {
		bint answer = fillWithBitsOfBint(a.bits);
		for (int i = a.bits.length() - aFront.bits.length(); i < a.bits.length(); i++) {
			answer.bits.set(i,false);
		}
		return answer;
	}
	
	/**
	 * 
	 * @param n how many bits should remain (from MSB to LSB)
	 */
	private bint cutForDivisonAFront(bint a, bint divisor) {
		bint answer = new bint(a.bits);
		for (int i = 0; i < a.bits.length() - divisor.bits.length(); i++) {
			answer = answer.divideBy2();
		}
		if (answer.isGreaterOrEqualThan(divisor)) {
			return answer;
		}
		else {
			answer = new bint(a.bits);
			for (int i = 0; i < a.bits.length() - divisor.bits.length() - 1; i++) {
				answer = answer.divideBy2();
			}
		}
		return answer;
	}
	
	private bint calcAndConcatinateOnesAndZeros(bint a, bint aFront, bint answer, bint newA, int backLenghtBefore) {	
		
		if (backLenghtBefore == -1) {
			answer.bits.set(0,true);
		}
		else {
			for (int i = 0; i < (backLenghtBefore - 1) - cutForDivisionABack(a, aFront).bits.length(); i++) {
				answer = answer.mult(new bint("2", 10));
			}
			answer = answer.mult(new bint("2", 10));
			answer = answer.add(new bint("1",10));
		}		
		return answer;
	}

	private bint cutToNthDigit(int i) {
		bint answer = new bint();
		int length = this.bits.length();
		int max = Math.min(i, length);
		for (int a = max; a > 0 ; a--) {
			if (this.bits.get(length - a)) answer.bits.set(max - a, true);
		}
		return answer;
	}
	
	private bint addZerosAndOne(int fillZeros) {
		bint answer = this;
		for (int i = 0; i <= fillZeros; i++) {
			answer = answer.mult(new bint ("2",10));
		}
		answer = answer.add(new bint("1",10));
		return answer;
	}

	private bint buildNewA(bint substract, bint aFront) {
		bint answer = substract;
		for (int i = this.bits.length() - aFront.bits.length(); i >= 0; i--) {
			answer = answer.mult(new bint("2",10));
			if (this.bits.get(i)) {
				answer.bits.set(0,true);
			}
		}
		return answer;
	}
}
