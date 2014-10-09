import java.util.BitSet;


public class bint {
	BitSet bits;
	
	public bint() {
		this.bits = new BitSet(0);
	}
	
	public bint(BitSet bitset) {
		this.bits = bitset;
	}
	
	/**
	 * 
	 * @param number
	 * @param type (possible: 10 for decimal, 2 for binary)
	 */
	public bint(String number, int type) {
		if (type == 10) {
			this.bits = decimalToBitSet(number);
		}
		else if (type == 2) {
			this.bits = binaryToBitSet(number);
		}
	}

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
	
	public boolean mod2Test(String number) {
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
	
	public String divideBy2(String number) {
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
	
	public bint divideBy2() {
		bint answer = new bint();
		for (int i = this.bits.length()-1; i > 0; i--) {
			if (this.bits.get(i)) {
				answer.bits.set(i-1);
			}
		}
		return answer;		
	}
	
	
	// <<<<<<<<<<<<<<<Conversions>>>>>>>>>>>>>>
	public String toBinary() {
		String answer = "";			
		
		String binary = this.toBinary();		
		
		for (int i = 0; i < this.bits.length(); i++) {
			if (this.bits.get(i)) {
				answer += "2^" + i + " ";
			}
		}
		return answer;
	}
	
	public String toString() {
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
	
	public String toHex() {
		String[] answer = new String[((int) this.bits.length()+3)/4];
		String work = "";
		String bin = this.toBinary();
		int size = bin.length();
		int rest = size%4;
		for (int i = bin.length(); i >= rest; i = i-4) {
			try {
				work += bin.substring(i-4, i) + " ";
			}
			catch (Exception e){
				work += bin.substring(i-rest, i);
			}
		}
		return work;
	}
	
	// <<<<<<<<<<<<<<<Operations>>>>>>>>>>>>>>
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
	
	private static int countBooles(boolean one, boolean two, boolean three) {
		int amount = 0;
		if (one) amount++;
		if (two) amount++;
		if (three) amount++;
		return amount;
	}
	
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
				String newAddNumber = big.toBinary();
				for (int shift = 0; shift < i; shift++) {
					newAddNumber += "0";
				}				
				work = work.add(new bint(newAddNumber,2));
			}
		}		
		return work;		
	}
	
	public bint mod(bint divisor) {
		bint a, b, aFront, d, aBack;
		a = this;
		b = divisor;
		
		while (true) {
			if (divisor.isGreaterThan(a)){
				return a;
			}
			if (a.bits.length() == divisor.bits.length()) {
				this.bits = this.sub(divisor).bits;
			}	
			
			aFront = cutForDivisonAFront(a, divisor);
			d = aFront.sub(b);
			aBack = cutForDivisionABack(a, aFront);
			a = combineForDivision(d, aBack, a, b);
			if (a.bits.length() == aFront.bits.length() && divisor.isGreaterThan(d) && divisor.isGreaterThan(aFront)) {
				return d;
			}
		}
		
//		while (this.isGreaterThan(divisor)){
//			bint newDivident = new bint(this.bits);
//			for (int i = divisor.bits.length(); i >= 0; i--) {
//				if (this.isGreaterThan(newDivident)) {
//					newDivident = newDivident.divideBy2();
//				}
//			}
//			newDivident = newDivident.sub(divisor);
//			for (int i = this.bits.length() - divisor.bits.length() - 1; i >= 0; i--) {
//				if (this.bits.get(i)) { 
//					this.bits.set(i);
//				}
//			}
//			for (int i = newDivident.bits.length(); i >= 0; i--) {
//				if (newDivident.bits.get(i)) {
//					this.bits.set(i+this.bits.length(),true);
//				}
//			}		
//		}
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
	
	private bint cutForDivisionABack(bint a, bint aFront) {
		bint answer = fillWithBitsOfBint(a.bits);
		for (int i = a.bits.length() - aFront.bits.length(); i < a.bits.length(); i++) {
			answer.bits.set(i,false);
		}
		return answer;
	}

	bint combineForDivision(bint d, bint aBack, bint a, bint divisor) {
		bint answer = fillWithBitsOfBint(aBack.bits);
		int offset = answer.bits.length();
		if (a.bits.length() != divisor.bits.length() && aBack.bits.length() == 0) {
			offset++;
		}
		
		for (int i = 0; i < d.bits.length(); i++) {
			if (d.bits.get(i)) {
				answer.bits.set(i+offset);
			}
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
}
