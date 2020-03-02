import java.util.Iterator;
import java.util.List;

/*
 * @author GRUPO 02
 * Renato Silva (50276), Luis Ferreira (46841), João Gil (52812)
 */


public class BinZZTree<Key extends Comparable<Key>, Value> implements ZZTree <Key, Value> {

	private Node root;


	//************************************************************************

	// node data type
	private class Node {
		private Key key;            // key
		private Value value;        // associated data
		private Node left;          // left and right subtrees
		private Node right;


		public Node(Key key, Value value) {
			this.key   = key;
			this.value = value;
		}

		// @ref https://stackoverflow.com/questions/4965335
		// @requires source code saved in UTF-8 encoding
		@Override
		public String toString() {
			return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
		}

		private StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
			if(right!=null) {
				right.toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
			}
			sb.append(prefix).append(isTail ? "└── " : "┌── ").append(value.toString()).append("\n");
			if(left!=null) {
				left.toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
			}
			return sb;
		}

	}

	//************************************************************************

	// default constructor builds an empty tree
	public BinZZTree() {}  

	// @requires keys.length == values.length
	public BinZZTree(List<Key> keys, List<Value> values) {

		Iterator<Key>   ks = keys  .iterator();
		Iterator<Value> vs = values.iterator();

		while (ks.hasNext() && vs.hasNext()) 
			put(ks.next(), vs.next());

	}

	//************************************************************************

	public boolean isEmpty() {
		return root == null;
	}

	//************************************************************************

	public boolean contains(Key key) {
		return get(key) != null;
	}

	//************************************************************************

	public Key rootKey() {
		return root.key;
	}

	//************************************************************************

	public int height() { 
		return height(root);
	}


	private int height(Node x){
		if(x == null){
			return 0;
		}
		return 1 + Math.max(height(x.left), height(x.right));
	}

	//************************************************************************

	public int size() {
		return size(root);
	}
	private int size(Node x) {
		if(x == null) {
			return 0;
		}
		return (size(x.left) + 1 + size(x.right));
	}

	//************************************************************************
	public Value get(Key key) {
		root = bubbleUp(root, key);
		int cmp = key.compareTo(root.key);
		if (cmp == 0) { 
			return root.value;
		}
		else {         
			return null;
		}
	}


	//************************************************************************
	public void put(Key key, Value value) {
		if (root == null) {
			root = new Node(key, value);
			return;
		}

		root = bubbleUp(root, key);

		int cmp = key.compareTo(root.key);

		if (cmp < 0) {
			Node a = new Node(key, value);
			a.left = root.left;
			a.right = root;
			root.left = null;
			root = a;
		}

		else if (cmp > 0) {
			Node a = new Node(key, value);
			a.right = root.right;
			a.left = root;
			root.right = null;
			root = a;
		}

		else {
			root.value = value;
		}

	}

	//************************************************************************

	public void remove(Key key) {
		if (root == null) { 
			return;
		} 

		root = bubbleUp(root, key);

		int cmp = key.compareTo(root.key);

		if (cmp == 0) {
			if (root.left == null) {
				root = root.right;
			} 
			else {
				Node a = root.right;
				root = root.left;
				bubbleUp(root, key);
				root.right = a;
			}
		}

	}




	//************************************************************************

	public String toString() {
		return root.toString();
	}

	//************************************************************************

	public boolean keepsInvariant() {
		return keepsInvariant(root, this.min(), this.max());
	}

	private boolean keepsInvariant(Node a, Key min, Key max) {
		if (a == null){
			return true;
		}
		if (min != null && a.key.compareTo(min) <= 0) {
			return false;
		}
		if (max != null && a.key.compareTo(max) >= 0) {
			return false;
		}
		return keepsInvariant(a.left, min, a.key) && keepsInvariant(a.right, a.key, max);
	} 
	//**********************************************************************


	/*
	 * Métodos auxiliares criados para o auxílio dos métodos anteriores
	 * 
	 */



	/*
	 * bubbleUp serve para fazer bubbleUP
	 * @param a - nó principal
	 * @param key - chave principal
	 * @return nó que depois será raiz
	 * @requires a != null && key != null
	 */
	private Node bubbleUp(Node a, Key key) {
		if (a == null) {
			return null;
		}

		int cmpX = key.compareTo(a.key);

		if (cmpX < 0) {

			if (a.left == null) {
				return a;
			}
			int cmpY = key.compareTo(a.left.key);
			if (cmpY < 0) {
				a.left.left = bubbleUp(a.left.left, key);
				a = goRight(a);
			}
			else if (cmpY > 0) {
				a.left.right = bubbleUp(a.left.right, key);
				if (a.left.right != null) {
					a.left = goLeft(a.left);
				}
			}

			if (a.left == null) {
				return a;
			}
			else return goRight(a);
		}

		else if (cmpX > 0) { 

			if (a.right == null) {
				return a;
			}

			int cmpY = key.compareTo(a.right.key);
			if (cmpY < 0) {
				a.right.left  = bubbleUp(a.right.left, key);
				if (a.right.left != null)
					a.right = goRight(a.right);
			}
			else if (cmpY > 0) {
				a.right.right = bubbleUp(a.right.right, key);
				a = goLeft(a);
			}

			if (a.right == null) { 
				return a;
			}
			else {
				return goLeft(a);
			}
		}

		else {
			return a;
		}
	}

	/*
	 * rodar para direita
	 * @param b - nó a ser usado
	 * 
	 */
	private Node goRight(Node b) {
		Node a = b.left;
		b.left = a.right;
		a.right = b;
		return a;
	}
	/*
	 * rodar para esquerda
	 * @param b - nó a ser usado
	 */
	private Node goLeft(Node b) {
		Node a = b.right;
		b.right = a.left;
		a.left = b;
		return a;
	}
	/*
	 * método para achar chave máxima
	 * da árvore
	 * @return chave máxima de uma árvore
	 */
	public Key max() {
		if (this.isEmpty()) {
			return null;
		}
		return max(root).key;
	} 
	/*
	 * método privado auxiliar 
	 * @param a - nó raiz de onde se procurará
	 * @return nó máximo
	 * @requires a != null
	 */
	private Node max(Node a) {
		if (a.right == null) {
			return a; 
		}
		else {                
			return max(a.right); 
		}
	}
	/*
	 * método para chave  mínima
	 * @return chave mínima
	 */
	public Key min() {
		if (isEmpty()) {
			return null;
		}
		return min(root).key;
	} 
	/*
	 * método que retorna nó m+inimo
	 * @param a - nó principal da sub-árvore
	 * @return nó mínimo
	 * @requires a != null
	 */
	private Node min(Node a) { 
		if (a.left == null) {
			return a; 
		}
		else {
			return min(a.left); 
		}
	} 

}


