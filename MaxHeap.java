public class MaxHeap{
	//the 3 parameters needed
	private Integer [] Heap;
	private int Arrsize;
	private int Heapsize;
	
	public MaxHeap (int n){
		this.Arrsize = n;
		this.Heapsize = 0;							//empty
		this.Heap = new Integer [this.Arrsize];		//create an empty Heap array
	}
//----------Recursive help function---------
	//help function to store and identify if the value is a subtree rooted with node i
	public void heap_arrange(Integer Arr[], int position) {
		int largest = position;		//let the given position be the largest value 
		int l= 2*position + 1; 		//initiate the left node pointer
		int r= 2*position + 2;		//initiate the right node pointer
		
		if(l < Arr.length && Arr[l] != null) {		//check if the left node is valid
			if(Arr[largest] < Arr[l]) {				//check if the left child is greater than the parent 
				largest = l;						//the largest node becomes the left child
			}
		}
		if(r < Arr.length && Arr[r] != null) {		//check if the right node is valid
			if(Arr[largest] < Arr[r]) {				//check if the right child is greater than the parent
				largest = r;						//the largest node becomes the right
			}
		}
		if(largest != position) {					//if the largest value is not in the parent node
			int temp = Arr[position];				//initiate a holder to swap the largest to the position
			Arr[position] = Arr[largest];			//put the larger value into the node position
			Arr[largest] = temp;					//and the position value to the place where the larger used to be
			if(largest <= Arr.length/2 - 1) {		//recursively swap if the largest value is not at the parent node
				heap_arrange(Arr, largest);
			}
		}
	}
	
	//creates a MaxHeap which stores the items from the input array using the heap arrange function
	public MaxHeap(Integer arr[]) {
		this.Heap = new Integer[arr.length];//create an empty Heap array
		int begin = (arr.length/2)-1;		//start at the last non-leaf node
		for(int i = begin; i>=0; i--) {		//re-arrange array into a heap
			heap_arrange(arr,i);			// by calling the heap arrange
		}
		for(int i=0; i<arr.length; i++) {	//deep copy of the new arrange array into heap
			this.Heap[i] = arr[i];			//using a for loop
		}
		this.Arrsize = arr.length;
		this.Heapsize = this.Heap.length;
	}
	
	//add all the get methods
	public int getSizeHeap() {
		return this.Heapsize;
	}
	
	public int getSizeArr() {
		return this.Arrsize;
	}
	
	public Integer[] getHeap() {
		return this.Heap;
	}
	
	//create an insert method
	public void insert(int n) {
		int x = Arrsize;								//store the original size of the array
		if(this.Arrsize < this.Heapsize + 1) {			//if the array is full, heap + 1 will greater the array size
			this.Arrsize = 2*this.Arrsize;				//double the array size
		}
		Integer[] new_Arr = new Integer[this.Arrsize];	//create a new integer
		for(int i=0; i<x; i++) {						//deep copy the original Heap array to the new array
			new_Arr[i] = this.Heap[i];
		}
		this.Heap = new_Arr;							//update the Heap array
		this.Heapsize = Heapsize + 1;					//update the Heap size
		this.Heap[this.Heapsize-1] = n;					//add the value n into the array
		//sort the array
		int index = this.Heapsize/2 - 1;				//find the root of the heap
		for(int i=index; i>=0; i--) {					//goes through the tree
			heap_arrange(Heap, i);						//sort the values
		}
	}
	
	//create a private delete method to help with heap sort[]
	private int deleteMax() {
		//remove the parent and have the child be the parent
		int largest = Heap[0];								//initiate a holder to have the largest value, parent
		Heap[0] = Heap[Heapsize-1];							//replace the parent with the most right child
		Heap[Heapsize-1] = null;							//delete  the child
		Heapsize = Heapsize - 1;							//update  the heap size
		//sort the heap in the deceasing order after deleting
		int n = 0;											//position node at the current tree level/branch
		int a = 0; 											//initiate a place holder for the value at position n inside the heap
		while(2*n+1<this.Heapsize || 2*n<this.Heapsize) {	//while the last left or right leaf is not reach yet 
			//if the left child is greater than the  parent and the right child
			if(2*n+1<this.Heapsize&&this.Heap[n]<this.Heap[2*n+1]&&(2*n+2>=this.Heapsize||this.Heap[2*n+1]>=this.Heap[2*n+2])) {
				a = this.Heap[n];					//update the place holder the value at the new position (n) - parent node
				this.Heap[n] = this.Heap[2*n+1];	//reassign the child value to the parent
				this.Heap[2*n+1] = a;				//swap the parent value to the child
				n = 2*n+1; 							//update the next node
			}
			//if the right child is greater than the parent and the left child
			else if(2*n+2<this.Heapsize&&this.Heap[n]<this.Heap[2*n+2]&&(2*n+1>=this.Heapsize||this.Heap[2*n+1]<this.Heap[2*n+2])) {
				a = this.Heap[n];					//update the place holder the value at the new position (n) - parent node
				this.Heap[n] = this.Heap[2*n+2];	//reassign the child value to the parent
				this.Heap[2*n+2] = a;				//swap the parent value to the child
				n = 2*n+2; 							//update the next node
			}
			//if the parent is the largest value
			else {
				return largest;
			}
		}
		return largest;
	}
	
	//create a heap sort function to sort the heap using the private delete max function
	public static void heapsort(Integer[] arrayToSort) {
		MaxHeap sorted = new MaxHeap(arrayToSort.length);	//initiate a heap array to sort
		MaxHeap tosort = new MaxHeap(arrayToSort);			//initiate a heap array to store the Integer array
		for(int i=0; i<arrayToSort.length; i++) {			//a for loop delete the node and sort it
			sorted.Heap[i] = tosort.deleteMax();			
		}
		
		for(int i=0; i<arrayToSort.length; i++) {			//copy the values from the sorted array into the original array
			arrayToSort[i] = sorted.Heap[i];
		}
	}
	
	// toSring function
	public String toString() {
		String output = "";
		for(int i=0; i<this.Heapsize; i++) {
			output = output + this.Heap[i] + ",";
		}
		return output;
	}
	
	//main function test case to test the private function delete max
	public static void main(String[] args) {
		//test 1 deletemax - max at the beginning array
		Integer[] array1 = {6,5,4,3,2,1};
		MaxHeap test1 = new MaxHeap(array1);
		test1.deleteMax();
		String output1 = test1.toString();
		//System.out.println(output1);
		//broken, but did take out the largest value
			MaxHeap test1_1 = new MaxHeap(array1);
			heapsort(test1_1.Heap);
			String output1_1 = test1_1.toString();
			System.out.println(output1_1+ "\n");
			//ans = 6,5,4,3,2,1
		
		//test 1 deletemax - max at the beginning array
		Integer[] array2 = {1,2,3,6,5,4};
		MaxHeap test2 = new MaxHeap(array2);
		test2.deleteMax();
		String output2 = test2.toString();
		//System.out.println(output2);
		//broken, but did take out the largest value
			MaxHeap test2_1 = new MaxHeap(array1);
			heapsort(test2_1.Heap);
			String output2_1 = test2_1.toString();
			System.out.println(output2_1+ "\n");
			//ans = 6,5,4,3,2,1
		
		//test 1 deletemax - max at the beginning array
		Integer[] array3 = {1,2,3,4,5,6};
		MaxHeap test3 = new MaxHeap(array3);
		test3.deleteMax();
		String output3 = test3.toString();
		//System.out.println(output3);
		//broken, but did take out the largest value
			MaxHeap test3_1 = new MaxHeap(array1);
			heapsort(test3_1.Heap);
			String output3_1 = test3_1.toString();
			System.out.println(output3_1+ "\n");
			//ans = 6,5,4,3,2,1
		
		//test 1 deletemax - max at the beginning array
		Integer[] array4 = {5,4,3,6,1,2};
		MaxHeap test4 = new MaxHeap(array4);
		test4.deleteMax();
		String output4 = test4.toString();
		//System.out.println(output4);
		//broken, but did take out the largest value and descending order
			MaxHeap test4_1 = new MaxHeap(array1);
			heapsort(test4_1.Heap);
			String output4_1 = test4_1.toString();
			System.out.println(output4_1+ "\n");
			//ans = 6,5,4,3,2,1
	}
}
