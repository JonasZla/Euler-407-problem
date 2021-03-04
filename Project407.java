package project407;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Project407 {
    static int[] spf = listTotients(10000000);
    static int[] smallestPrimeFactor = listSmallestPrimeFactors(10000000);
    
    public static void main(String[] args) {
        long sum = 0;
        int startN = 2;
        int EndN = 10000000;
        System.out.println("Start");
        for (int xx = startN; xx<EndN+1;xx++ ) {
            List<Integer> factorization = new ArrayList<>();
            int number = xx;
            
            for (int j = number; j != 1; ) {
                int p = smallestPrimeFactor[j];
                int q = 1;
                do {
                    j /= p;
                    q *= p;
                } while (j % p == 0);
                factorization.add(q);
            }

            UW uw = new UW();
            if (factorization.size()>1){
                for (int i=1; i<factorization.size()+1;i++ ) {
                    printCombination(factorization, factorization.size(), i,uw, number);        
                }
            }
            int max = 1;
            if (uw.number!=0) 
                max = uw.number;
            sum += (long)max;
        }
        System.out.println(sum);
        System.out.println("End");
    }
    
    //Randa mažiausią daliklį
    public static int[] listSmallestPrimeFactors(int n) {
	int[] result = new int[n + 1];
	int limit = (int)Math.sqrt(n);
	for (int i = 2; i < result.length; i++) {
            if (result[i] == 0) {
		result[i] = i;
		if (i <= limit) {
                    for (int j = i * i; j <= n; j += i) {
                        if (result[j] == 0)
                            result[j] = i;
                    }
                }
            }
	}
	return result;
    }
    
    public static int[] listTotients(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Negative array size");
		int[] result = new int[n + 1];
		for (int i = 0; i <= n; i++)
			result[i] = i;
		
		for (int i = 2; i <= n; i++) {
			if (result[i] == i) {  // i is prime
				for (int j = i; j <= n; j += i)
					result[j] -= result[j] / i;
			}
		}
		return result;
	}
    
    static void combinationUtil(List<Integer> arr, int data[], int start, 
                                int end, int index, int r,UW uw,int number) 
    { 
        int u=1;

        if (index == r) 
        { 
            for (int j=0; j<r; j++) {
                u=u*data[j];
            }
            if (number ==u) return;
            int v = number / u;
            int k = spf[v]-1;    
            
            BigInteger ub = BigInteger.valueOf(u);
            
           // BigInteger k1 = pow(ub,BigInteger.valueOf(k));
            
            int w = ub.modPow(BigInteger.valueOf(k), BigInteger.valueOf(v)).intValue();
            
            if (!(u*w<number)) return;
            if (u*w>uw.number) uw.number=u*w;
            
            return; 
        } 
  
        // replace index with all possible elements. The condition 
        // "end-i+1 >= r-index" makes sure that including one element 
        // at index will make a combination with remaining elements 
        // at remaining positions 
        for (int i=start; i<=end && end-i+1 >= r-index; i++) 
        { 
            data[index] = arr.get(i); 
            combinationUtil(arr, data, i+1, end, index+1, r, uw, number); 
        } 
    } 
    
 static void printCombination(List<Integer> arr, int n, int r,UW uw, int number) 
    { 
        // A temporary array to store all combination one by one 
        int data[]=new int[r]; 
  
        // Print all combination using temprary array 'data[]' 
        combinationUtil(arr, data, 0, n-1, 0, r, uw, number); 
    } 
 
}

 
 
 class UW {
  public int number;
  public UW()
  {
    number = 0;
  }
}
