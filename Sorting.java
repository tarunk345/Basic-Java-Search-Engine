package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may (or may not) need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) < 0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<>(results.keySet());
        if (sortedUrls.isEmpty()) {
            return null;
        }
        quickSort(sortedUrls, results, 0, sortedUrls.size() - 1);
        return sortedUrls;
    }

    private static <K, V extends Comparable<V>> void quickSort(ArrayList<K> keys, HashMap<K, V> results, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            // Use median-of-three pivot selection
            int pivotIndex = median(keys, results, leftIndex, rightIndex);
            // Swap pivot to the rightmost position temporarily
            swap(keys, pivotIndex, rightIndex);

            V pivotValue = results.get(keys.get(rightIndex));
            int i = leftIndex;
            for (int j = leftIndex; j < rightIndex; j++) {
                if (results.get(keys.get(j)).compareTo(pivotValue) >= 0) {
                    swap(keys, i, j);
                    i++;
                }
            }
            // Swap pivot back to its correct position
            swap(keys, i, rightIndex);

            quickSort(keys, results, leftIndex, i - 1);
            quickSort(keys, results, i + 1, rightIndex);
        }
    }

    private static <K> void swap(ArrayList<K> keys, int i, int j) {
        K temp = keys.get(i);
        keys.set(i, keys.get(j));
        keys.set(j, temp);
    }

    private static <K, V extends Comparable<V>> int median(ArrayList<K> keys, HashMap<K, V> results, int leftIndex, int rightIndex) {
        int midIndex = leftIndex + (rightIndex - leftIndex) / 2;

        V leftValue = results.get(keys.get(leftIndex));
        V midValue = results.get(keys.get(midIndex));
        V rightValue = results.get(keys.get(rightIndex));

        if (leftValue.compareTo(midValue) > 0) {
            if (midValue.compareTo(rightValue) > 0) {
                return midIndex;
            } else if (leftValue.compareTo(rightValue) > 0) {
                return rightIndex;
            } else {
                return leftIndex;
            }
        } else {
            if (leftValue.compareTo(rightValue) > 0) {
                return leftIndex;
            } else if (midValue.compareTo(rightValue) > 0) {
                return rightIndex;
            } else {
                return midIndex;
            }
        }
    }
}