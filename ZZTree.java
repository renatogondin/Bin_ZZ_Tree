
public interface ZZTree<Key extends Comparable<Key>, Value> {
	
	/**
	 * @return true iff the tree is empty
	 */
	public boolean isEmpty();
	
	/**
	 * @param key The key to search
	 * @return true iff the given key exists
	 */
	public boolean contains(Key key);

	/**
	 * @param key The key to search
	 * @return the value associated with the given key, 
	 *         otherwise (if key does not exist) null
	 */
    public Value get(Key key);
    
    /**
     * Insert (or replace) a pair <key,value>
     * @param key The key to add/replace
     * @param value The new value associated with the key
     */
    public void put(Key key, Value value);
    
    /**
     * Remove given key from tree (if key does not exist, do nothing)
     * @param key The key to remove
     */
    public void remove(Key key);
    
    /**
     * @return The height of the tree
     */
    public int height();
    
    /**
     * @return The size of the tree
     */
    public int size();
    
    /**
     * @requires !isEmpty()
     * @return the key at tree's root
     */
    public Key rootKey();
 
    /**
     * A ZZTree must be a binary search tree
     * @return true iff invariant is uphold
     */
    public boolean keepsInvariant();
}
