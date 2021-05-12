
/**
 * List.java
 * @author Yi Jou (Ruby) Liao
 * @author Alvin Nguyen
 * CIS 22C, Course Project
 */

import java.util.NoSuchElementException;

public class List<T extends Comparable<T>> {
	private class Node { // inner Node class
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) { // Node constructor
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new List with default values
	 * 
	 * @postcondition a new List with default values is created
	 */
	public List() { // Default value for objects is "null" and for int is "0"
		first = null;
		last = null;
		iterator = null;
		length = 0;
	}

	/**
	 * Instantiates a new List by copying another List
	 * 
	 * @param original the List to make a copy of
	 * @postcondition a new List object, which is an identical but separate copy of
	 *                the List original
	 */
	public List(List<T> original) {
		if (original == null) {
			return;
		}
		if (original.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
		}
	}

	/**** ACCESSORS ****/

	/**
	 * Returns the index from 1 to length where value is located in the List by
	 * calling the private helper method binarySearch
	 * 
	 * @param value the value to search for
	 * @return the index where value is stored from 1 to length, or -1 to indicate
	 *         not found
	 * @precondition inSortedOrder()
	 * @postcondition the position of the iterator must remain unchanged!
	 * @throws IllegalStateException when the precondition is violated.
	 */
	public int binarySearch(T value) throws IllegalStateException {
		if (!inSortedOrder()) {
			throw new IllegalStateException("binarySearch: Cannot do binary search. List is unsorted.");
		} else if (isEmpty()) {
			return -1;
		}

		return binarySearch(1, length, value);
	}

	/**
	 * Searches for the specified value in the List by implementing the recursive
	 * binarySearch algorithm
	 * 
	 * @param low   the lowest bounds of the search
	 * @param high  the highest bounds of the search
	 * @param value the value to search for
	 * @return the index at which value is located or -1 to indicate not found
	 * @postcondition the location of the iterator must remain unchanged
	 */
	private int binarySearch(int low, int high, T value) {
		if (high < low) {
			return -1;
		}

		int mid = low + (high - low) / 2;
		Node temp = first;
		for (int i = 1; i < mid; i++) { // Move temp iterator to mid node
			temp = temp.next;
		}

		if (temp.data.equals(value)) {
			return mid;
		} else if (temp.data.compareTo(value) < 0) { // value is in upper half
			return binarySearch(mid + 1, high, value);
		} else { // value is in lower half
			return binarySearch(low, mid - 1, value);
		}
	}

	/**
	 * Uses the iterative linear search algorithm to locate a specific element in
	 * the list
	 * 
	 * @param element the value to search for
	 * @return the location of value in the List or -1 to indicate not found Note
	 *         that if the List is empty we will consider the element to be not
	 *         found
	 * @postcondition: position of the iterator remains unchanged!
	 */
	public int linearSearch(T element) {
		if (isEmpty()) {
			return -1;
		}
		int index = 1;
		Node temp = first;
		while (!element.equals(temp.data)) {
			temp = temp.next;
			if (temp == null) {
				return -1;
			}
			index++;
		}
		return index;
	}

	/**
	 * Returns the index of the iterator from 1 to n. Note that there is no index 0.
	 * Does not use recursion.
	 * 
	 * @precondition iterator != null
	 * @return the index of the iterator
	 * @throws NullPointerException when the precondition is violated
	 */
	public int getIndex() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("getIndex: Cannot access. Iterator is null.");
		}
		int index = 1;
		Node temp = first;
		while (temp != iterator && temp.next != null) {
			temp = temp.next;
			index++;
		}
		return index;
	}

	/**
	 * Determines whether a List is sorted by calling its recursive helper method
	 * isSorted Note: An empty List can be considered to be (trivially) sorted
	 * 
	 * @return whether this List is sorted
	 */
	public boolean inSortedOrder() {
		if (isEmpty()) {
			return true;
		}
		return inSortedOrder(first);
	}

	/**
	 * Helper method to inSortedOrder Determines whether a List is sorted in
	 * ascending order recursively
	 * 
	 * @return whether this List is sorted
	 */
	private boolean inSortedOrder(Node node) {
		if (node == last) { // Only one element in list
			return true;
		} else if (node.data.compareTo(node.next.data) > 0) {
			return false;
		} else {
			return inSortedOrder(node.next);
		}
	}

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition length != 0
	 * @return the value stored at node first
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getFirst: List is Empty. No data to access!");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition length != 0
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getLast: List is Empty. No data to access!");
		}
		return last.data;
	}

	/**
	 * Returns the current length of the list
	 * 
	 * @return the length of the list from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the list is currently empty
	 * 
	 * @return whether the list is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * Returns the element that the iterator is pointing to.
	 * 
	 * @precondition iterator != null
	 * @return the value pointed to by the iterator
	 * @throws NullPointerException when precondition is violated
	 */
	public T getIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIterator: Iterator is pointing to null.");
		}
		return iterator.data;
	}

	/**
	 * Returns whether the iterator is off the end.
	 * 
	 * @return whether the iterator is off the end.
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/**
	 * Determines whether two Lists have the same data in the same order
	 * 
	 * @param L the List to compare to this List
	 * @return whether the two Lists are equal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List<T> L = (List<T>) o;
			if (this.length != L.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) { // Lists are same length
					if (temp1.data != temp2.data) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/**** MUTATORS ****/

	/**
	 * Places the iterator at first and then iteratively advances it to the
	 * specified index no recursion
	 * 
	 * @param index the index where the iterator should be placed
	 * @precondition 1 <= index <= length
	 * @throws IndexOutOfBoundsException when precondition is violated
	 */
	public void advanceToIndex(int index) throws IndexOutOfBoundsException {
		if (index < 1 || index > length) {
			throw new IndexOutOfBoundsException("advanceToIndex(): Cannot advance iterator. Index out of bounds.");
		}
		placeIterator();
		for (int i = 1; i < index; i++) {
			advanceIterator();
		}
	}

	/**
	 * Creates a new first element
	 * 
	 * @param data the data to insert at the front of the list
	 * @postcondition a new first Node is created and added to beginning of List
	 */
	public void addFirst(T data) {

		if (length == 0) { // Edge case: list is empty
			first = last = new Node(data);
		} else { // General case: list is not empty
			Node N = new Node(data); // Create new Node
			N.next = first; // Update reference of new Node to previous first Node
			first.prev = N; // Update previous first Node to point back to new first Node
			first = N; // Update first pointer to new Node
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the list
	 * @postcondition a new last Node is created and added to end of List
	 */
	public void addLast(T data) {

		if (length == 0) { // Edge case: list is empty
			first = last = new Node(data);
		} else { // General case: list is not empty
			Node N = new Node(data); // Create new Node
			last.next = N; // Update reference of previous last Node to new Node
			N.prev = last; // Update new node to point back to previous last Node
			last = N; // Update last pointer to new Node
		}
		length++;
	}

	/**
	 * Removes the element at the front of the list
	 * 
	 * @precondition length != 0
	 * @postcondition the first Node in the list is removed
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeFirst() throws NoSuchElementException {
		if (length == 0) { // List is empty; precondition
			throw new NoSuchElementException("removeFirst: Cannot remove from an empty List");
		} else if (length == 1) { // List has one node; edge case
			first = last = iterator = null; // Return List to empty state, where first, last, and iterator are null and
											// length is 0
		} else { // List has 1+ node; general case
			if (iterator == first) {
				iterator = null;
			}
			first.next.prev = null; // Update next node's previous pointer to null
			first = first.next; // Advance first reference variable to link to the second Node
		}
		length--;
	}

	/**
	 * Removes the element at the end of the list
	 * 
	 * @precondition length != 0
	 * @postcondition the last Node in the list is removed
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeLast() throws NoSuchElementException {
		if (length == 0) { // List is empty; precondition
			throw new NoSuchElementException("removeLast: Cannot remove from empty List");
		} else if (length == 1) { // List has one node; edge case
			first = last = iterator = null;
		} else { // List has 1+ node; general case
			if (iterator == last) {
				iterator = null;
			}
			last = last.prev;
			last.next = null;
		}
		length--;
	}

	/**
	 * Has iterator point to the first node in the List.
	 * 
	 * @postcondition Iterator now points to first
	 */
	public void placeIterator() {
		iterator = first;
	}

	/**
	 * Removes the element that the iterator is pointing to.
	 * 
	 * @precondition Iterator != null
	 * @postcondition The node iterator was pointing to is removed from the list;
	 *                iterator points to NULL
	 * @throws NullPointerException when precondition is violated
	 */
	public void removeIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("removeIterator: Can't remove. Iterator is off the end");
		} else if (iterator == first) {
			removeFirst();
		} else if (iterator == last) {
			removeLast();
		} else { // general case
			iterator.prev.next = iterator.next;
			iterator.next.prev = iterator.prev;
			length--;
		}
		iterator = null;
	}

	/**
	 * Adds element after the node iterator is pointing to.
	 * 
	 * @precondition iterator != null
	 * @postcondition new node is added after the node iterator is pointing to
	 * @throws NullPointerException when precondition is violated
	 */
	public void addIterator(T data) throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("addIterator: Can't add. Iterator is off the end.");
		} else if (iterator == last) {
			addLast(data);
		} else { // general case
			Node n = new Node(data);
			n.next = iterator.next;
			iterator.next = n;
			n.prev = iterator;
			n.next.prev = n;
			length++;
		}
	}

	/**
	 * Updates iterator to point to next node in the list.
	 * 
	 * @precondition iterator != null
	 * @postcondition iterator points to next node in list
	 * @throws NullPointerException when precondition is violated
	 */
	public void advanceIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("advanceIterator: Can't advance. Iterator is off the end.");
		} else {
			iterator = iterator.next;
		}
	}

	/**
	 * Updates iterator to point to previous node in the list.
	 * 
	 * @precondition iterator != null
	 * @postcondition iterator points to previous node in list
	 * @throws NullPointerException when precondition is violated
	 */
	public void reverseIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("reverseIterator: Can't reverse. Iterator is off the end.");
		} else {
			iterator = iterator.prev;
		}
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Prints a linked list to the console in reverse by calling the private
	 * recursive helper method printInReverse
	 */
	public void printInReverse() {
		if (!isEmpty()) {
			printInReverse(first);
		}
		System.out.println();
	}

	/**
	 * Recursively prints a linked list to the console in reverse order from last to
	 * first (no loops) Each element separated by a space Should print a new line
	 * after all elements have been displayed
	 */

	private void printInReverse(Node node) {
		if (node == last) {
			System.out.print(node.data);
		} else {
			printInReverse(node.next);
			System.out.print(" " + node.data);
		}

	}

	/**
	 * List with each value on its own line At the end of the List a new line
	 * 
	 * @return the List as a String for display
	 */
	@Override
	public String toString() {
		String result = "";
		Node temp = first;
		while (temp != null) {
			result += temp.data + "\n";
			temp = temp.next;
		}
		return result;
	}
}