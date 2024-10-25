package week9;

public class Sorting {

	
	private int partition(int[] list, int begin, int end) {
		int pivot = list[end];
		
		int i = begin - 1; 
		
		// if the elem is less than pivot, swap with i
		// ends with all smaller elems on the left 
		for(int j = begin; j < end; j++) {
			if (list[j] <= pivot) {
				i++; 
				int swapTemp = list[i];
				list[i] = list [j];
				list[j] = swapTemp; 
			}
		}
		
		//swaps the pivot in between the smaller elems on left and larger elems on right
		int swapTemp = list[i+1];
		list[i+1] = list[end];
		list[end] = swapTemp; 
		
		return i+1; 
		
	}
	
	public void quickSort(int[] list, int begin, int end) { 
		if(begin < end) {
			int partitionIndex  = partition(list, begin, end);
			
			//perform sort on both piles leftover
			quickSort(list, begin, partitionIndex-1);
			quickSort(list, partitionIndex +1, end);
			
		}
	}
	
	// merges all the lists back together
	private void merge(int[] a, int[] l, int[] r, int left, int right) {
		int i = 0, j = 0, k = 0;
		
		while(i < left && j < right) {
			if(l[i] <= r[j]) {
				a[k++] = l[i++];
			} else {
				a[k++] = r[j++];
			}
		}
		
		while(i < left) {
			a[k++] = l[i++];
		}
		
		while(j < right) {
			a[k++] = r[j++];
		}
		
		
	
	}
	
	
	public void mergeSort(int[] a, int n) {
		if(n < 2) {
			return; 
		}
		
		// divide list into left and right half arrays 
		int middle = n/2; 
		int[] left = new int[middle];
		int[] right = new int[n - middle];
		
		
		//copy all values from a into the left and right arrays
		for(int i = 0; i < middle; i++) {
			left[i] = a[i];
		}
		
		for(int i = middle; i<n; i++) {
			right[i - middle] = a[i];
		}
		
		//breaks the entire array down until each elem is its own list of size 1
		mergeSort(left, middle);
		mergeSort(right, n - middle);
		
		merge(a, left, right, middle, n - middle);
	}
	
	
	
}
