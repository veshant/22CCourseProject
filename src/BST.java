
/**
 * BST.java
 * @author Yi Jou (Ruby) Liao
 * @author Alvin Nguyen
 * CIS 22C, Course Project
 * ** DESCRIPTION HERE **
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;


public class BST<T extends Comparable<T>> {
	private class Node {
		private T data;
		private Node left;
		private Node right;

		public Node(T data) {
			this.data = data;
			left = null;
			right = null;
		}
	}
	private Node root;
	private Comparator<T> comp; // How data will be compared in the tree

	/*** CONSTRUCTORS ***/

	/**
	 * Default constructor for BST sets root and comp to null
	 */
	public BST() {
		root = null;
		comp = null;
	}
	
	/**
	 * BST constructor that sets custom comparator for comparing node data
	 * @param comp Comparator for how to compare node data in BST
	 */
	public BST(Comparator<T> comp) {
		root = null;
		this.comp = comp;
	}

	/**
	 * Copy constructor for BST
	 * 
	 * @param bst the BST to make a copy of
	 */
	public BST(BST<T> bst) {
		if(bst == null) {
			return;
		}
		if(bst.getSize() == 0) {
			root = null;
		} else {
			comp = bst.getComparator();
			copyHelper(bst.root);
		}
	}

	/**
	 * Helper method for copy constructor
	 * 
	 * @param node the node containing data to copy
	 */
	private void copyHelper(Node node) {
		if(node == null) {
			return;
		}
		// Recursively iterate through BST, copying each node
		insert(node.data);
		copyHelper(node.left);
		copyHelper(node.right);
	}

	/*** ACCESSORS ***/

	/**
	 * Returns the data stored in the root
	 * 
	 * @precondition !isEmpty()
	 * @return the data stored in the root
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getRoot() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("getRoot(): Cannot access root. Tree is empty.");
		}
		return root.data;
	}

	/**
	 * Determines whether the tree is empty
	 * 
	 * @return whether the tree is empty
	 */
	public boolean isEmpty() {
		return (root == null);
	}

	/**
	 * Returns the current size of the tree (number of nodes)
	 * 
	 * @return the size of the tree
	 */
	public int getSize() {
		return getSize(root);
	}

	/**
	 * Helper method for the getSize method
	 * 
	 * @param node the current node to count
	 * @return the size of the tree
	 */
	private int getSize(Node node) {
		if(node == null) {
			return 0;
		}
		return getSize(node.left) + getSize(node.right) + 1;
	}

	/**
	 * Returns the height of tree by counting edges.
	 * 
	 * @return the height of the tree
	 */
	public int getHeight() {
		return getHeight(root);
	}

	/**
	 * Helper method for getHeight method
	 * 
	 * @param node the current node whose height to count
	 * @return the height of the tree
	 */
	private int getHeight(Node node) {
		if(node == null) {
			return -1;
		}
		
		int leftHeight = getHeight(node.left);
		int rightHeight = getHeight(node.right);
		
		if(leftHeight < rightHeight) {
			return rightHeight + 1;
		} else {
			return leftHeight + 1;
		}
	}

	/**
	 * Returns comparator for the tree (null if there isn't one)
	 * 
	 * @return comparator for the tree
	 */
	public Comparator<T> getComparator(){
		return comp;
	}
	
	/**
	 * Returns the smallest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the smallest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMin() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMin(): Cannot find value. Tree is empty.");
		}
		return findMin(root);
	}

	/**
	 * Helper method to findMin method
	 * 
	 * @param node the current node to check if it is the smallest
	 * @return the smallest value in the tree
	 */
	private T findMin(Node node) {
		if (node.left == null) {
			return node.data;
		}
		return findMin(node.left);
	}

	/**
	 * Returns the largest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the largest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMax() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMax(): Cannot find value. Tree is empty.");
		}
		return findMax(root);
	}

	/**
	 * Helper method to findMax method
	 * 
	 * @param node the current node to check if it is the largest
	 * @return the largest value in the tree
	 */
	private T findMax(Node node) {
		if (node.right == null)
			return node.data;
		return findMax(node.right);
	}

	/**
	 * Searches for a specified value in the tree
	 * 
	 * @param data the value to search for
	 * @return ArrayList of objects matching the value being searched for
	 */
	public ArrayList<T> search(T data) {
		ArrayList<T> arr = new ArrayList<T>();
		if (root == null) {
			return arr;
		} else {
			return search(data, root, arr);
		}
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the value to search for
	 * @param node the current node to check
	 * @return ArrayList of objects matching the value being searched for
	 */
	private ArrayList<T> search(T data, Node node, ArrayList<T> arr) {
		if (node == null) { // Base case
			return arr;
		}
		if (compareData(data, node.data) == 0) { // Match found, add to ArrayList and check left for duplicates
			arr.add(node.data);
			return search(data, node.left, arr);
		} else if (compareData(data, node.data) < 0) {  // If target data < current node.data
			return search(data, node.left, arr); 
		} else {  // Target data > current node.data
			return search(data, node.right, arr);
		}
	}

	/**
	 * Determines whether two trees store identical data in the same structural
	 * position in the tree
	 * 
	 * @param o another Object
	 * @return whether the two trees are equal
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof BST)) {
			return false;
		} else {		
			@SuppressWarnings("unchecked")
			BST<T> bst = (BST<T>) o;
			if (this.getSize() != bst.getSize()) {
				return false;
			}
			if (this.getSize() == 0) {
				return true;
			} else {
				return equals(root, bst.root);
			}
		}
	}

	/**
	 * Helper method for the equals method
	 * 
	 * @param node1 the node of the first bst
	 * @param node2 the node of the second bst
	 * @return whether the two trees contain identical data stored in the same
	 *         structural position inside the trees
	 */
	private boolean equals(Node node1, Node node2) {
		if (node1 == null || node2 == null) { // If either node is null, return their comparison
			if(node1 == node2) {
				return true;
			} else {
				return false;
			}
		}

		if (!node1.data.equals(node2.data)) { // If corresponding nodes !=
			return false;
		}

		return equals(node1.left, node2.left) && equals(node1.right, node2.right);
	}

	/*** MUTATORS ***/

	/**
	 * Inserts a new node in the tree
	 * 
	 * @param data the data to insert
	 */
	public void insert(T data) {
		if(isEmpty()) {
			root = new Node(data);
		} else {
			insert(data, root);
		}
	}

	/**
	 * Helper method to insert Inserts a new value in the tree
	 * 
	 * @param data the data to insert
	 * @param node the current node in the search for the correct location in which
	 *             to insert
	 */
	private void insert(T data, Node node) {
		if (compareData(data, node.data) <= 0) {  // Insert <= current node.data
			if (node.left == null) {
				node.left = new Node(data);
			} else {
				insert(data, node.left);
			}
		} else { // Insert > current node
			if (node.right == null) {
				node.right = new Node(data);
			} else {
				insert(data, node.right);
			}
		}
	}

	/**
	 * Removes a value from the BST
	 * 
	 * @param data the value to remove
	 * @precondition !isEmpty()
	 * @precondition the data is located in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public void remove(T data) throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("remove(): Cannot remove. Tree is empty.");
		}
		
		if (this.search(data).isEmpty()) { // If target data not found
			throw new NoSuchElementException("remove(): Cannot remove. Element not found.");
		} else {
			root = remove(data, root); // Reassign BST's root in case root was removed
		}
	}

	/**
	 * Helper method to the remove method
	 * 
	 * @param data the data to remove
	 * @param node the current node
	 * @return an updated reference variable
	 */
	private Node remove(T data, Node node) {
		if (node == null) { 
			return node;
		} else if (compareData(data, node.data) < 0) {  // Target data < current node.data
			node.left = remove(data, node.left);  // Go to left child
		} else if (compareData(data, node.data) > 0) {  // Target data > current node.data
			node.right = remove(data, node.right); // Go to right child
		} else { // Current node.data == target data for deletion
			if (node.left == null && node.right == null) { // If leaf node
				node = null;
			} else if (node.left != null && node.right == null) {  // If only left child, replace this node with left child   
				node = node.left;
			} else if (node.left == null && node.right != null) {  // If only right child, replace this node with right child
				node = node.right;
			} else {  // Node has two children
				node.data = findMin(node.right);  // findMin node in right subtree to replace this node.data
				node.right = remove(node.data, node.right);  // Remove the min node in right subtree
			}
		}		
		return node;
	}

	/*** ADDITIONAL OPERATIONS ***/

	/**
	 * Prints the data in pre order to the console
	 */
	public void preOrderPrint() {
		if (!isEmpty()) {
			preOrderPrint(root);
		} else {
			System.out.println();
		}
	}

	/**
	 * Helper method to preOrderPrint method Prints the data in pre order to the
	 * console
	 */
	private void preOrderPrint(Node node) {
		if(node == null) {
			return;
		}
		System.out.print(node.data + "\n");
		preOrderPrint(node.left);
		preOrderPrint(node.right);
	}

	/**
	 * Prints the data in sorted order to the console
	 */
	public void inOrderPrint() {
		if (!isEmpty()) {
			inOrderPrint(root);
		} else{
			System.out.println();
		}
	}

	/**
	 * Helper method to inOrderPrint method Prints the data in sorted order to the
	 * console
	 */
	private void inOrderPrint(Node node) {
		if(node == null) {
			return;
		}
		inOrderPrint(node.left);
		System.out.print(node.data + "\n");
		inOrderPrint(node.right);
	}

	/**
	 * Prints the data in post order to the console
	 */
	public void postOrderPrint() {
		postOrderPrint(root);
		System.out.println();
	}

	/**
	 * Helper method to postOrderPrint method Prints the data in post order to the
	 * console
	 */
	private void postOrderPrint(Node node) {
		if(node == null) {
			return;
		}
		postOrderPrint(node.left);
		postOrderPrint(node.right);
		System.out.print(node.data + " ");
	}
	
	/**
	 * Creates a string representing BST tree data using in order traversal
	 * 
	 * @return String representing BST data in sorted order
	 */
	@Override
	public String toString() {
		String s = "";
		s = toString(s, root);
		return s;
	}
	
	/**
	 * Helper method to toString method Creates a string representing BST tree using in order traversal
	 * 
	 * @return String representing BST data in sorted order
	 */
	private String toString(String s, Node node) {
		if(node == null) {
			return s;
		}
		s = toString(s, node.left);
		s += node.data + "\n";
		s = toString(s, node.right);

		return s;
	}
	
	/**
	 * Compares two objects by the tree's comparator property if not null. 
	 * Otherwise compares objects using compareTo method.
	 * 
	 * @param data Object to be compared
	 * @param data2 Other object to be compared
	 * @return 0 if data == data2, -1 if data < data2, or 1 if data > data2 
	 */
	private int compareData(T data, T data2) {
		if (comp == null) {
			return data.compareTo(data2);
		} else {
			return comp.compare(data, data2);
		}
	}
}