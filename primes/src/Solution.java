import static java.lang.System.in;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class Sieve implements Iterable<Integer>, Iterator<Integer>{
	BitSet sv;
	int n;
	// ndx is the next prime we're working on
	int ndx = 2;
	
	// start at our current prime, and look until we find an unmarked space.  If there is one < n, 
	// hasNext is true
	@Override
	public boolean hasNext() {
		for (int k = ndx; k < n; k++) {
			if (!sv.get(k)) return true;
		}
		return false;
	}

	@Override
	public Integer next() {
		// find the next prime number
		while (ndx < n && sv.get(ndx)) ndx ++;
		
		// now mark mulitples of this number as not prime
		for (int k = ndx; k < n; k += ndx) sv.set(k);
		
		return Integer.valueOf(ndx);
	}

	@Override
	public void remove() {
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return this;
	}

	public Sieve(int n) {
		this.n = n;
		// sv is initialized to all zeros
		sv = new BitSet(n);
	}
}

class Prime {
	InputStream in = System.in;
	
	boolean isPrime(int n) {
		if (n <= 1) return false;
		int m = (int)Math.floor((Math.sqrt((double)n))) + 1;
		Sieve primes = new Sieve(m);
		
		for (int i : primes) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	public void checkPrime(int... trials) {
		for (int t : trials) {
			if (isPrime(t)) {
				System.out.print(t + " ");
			}
		}
		System.out.println();
	}
}

public class Solution {

	public static void main(String[] args) {
		try{
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		int n1=Integer.parseInt(br.readLine());
		int n2=Integer.parseInt(br.readLine());
		int n3=Integer.parseInt(br.readLine());
		int n4=Integer.parseInt(br.readLine());
		int n5=Integer.parseInt(br.readLine());
		Prime ob=new Prime();
		ob.checkPrime(n1);
		ob.checkPrime(n1,n2);
		ob.checkPrime(n1,n2,n3);
		ob.checkPrime(n1,n2,n3,n4,n5);	
		Method[] methods=Prime.class.getDeclaredMethods();
		Set<String> set=new HashSet<>();
		boolean overload=false;
		for(int i=0;i<methods.length;i++)
		{
			if(set.contains(methods[i].getName()))
			{
				overload=true;
				break;
			}
			set.add(methods[i].getName());
			
		}
		if(overload)
		{
			throw new Exception("Overloading not allowed");
		}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
}

