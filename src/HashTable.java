/**
 * HashTable.java
 * @author
 * @author
 * CIS 22C, Lab 6
 */
import java.util.ArrayList;

public class HashTable<T> {

    private int numElements;
    private ArrayList<List<T>> Table;

    /**
     * Constructor for the hash table. Initializes the Table to be sized according
     * to value passed in as a parameter Inserts size empty Lists into the table.
     * Sets numElements to 0
     *
     * @param size the table size
     */
    public HashTable(int size) {
        this.numElements = 0;
        Table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.Table.add(new List<T>());
        }
    }

    /** Accessors */

    /**
     * returns the hash value in the Table for a given Object
     *
     * @param t the Object
     * @return the index in the Table
     */
    private int hash(String t) {
        int sum = 0;
        for(int i = 0; i < t.length(); i++) {
            sum += (int)t.charAt(i);
        }
        return sum % Table.size();
    }

    /**
     * counts the number of keys at this index
     *
     * @param index the index in the Table
     * @precondition 0 <= index < Table.length
     * @return the count of keys at this index
     * @throws IndexOutOfBoundsException
     */
    public int countBucket(int index) throws IndexOutOfBoundsException {
        if (index <= 0 || index > Table.size()) {
            throw new IndexOutOfBoundsException("countBucket: Cannot execute method, index is out of bounds");
        }
        return this.Table.get(index).getLength();
    }

    /**
     * returns total number of keys in the Table
     *
     * @return total number of keys
     */
    public int getNumElements() {
        return this.numElements;
    }

    /**
     * Accesses a specified key in the Table
     *
     * @param t the key to search for
     * @return the value to which the specified key is mapped, or null if this table
     *         contains no mapping for the key.
     * @precondition t != null
     * @throws NullPointerException if the specified key is null
     */
    public T get(String line, T t) throws NullPointerException {
        if (t == null) {
            throw new NullPointerException("get(): Cannot execute, value is null");
        }
        int num = this.hash(line);
        List<T> l = this.Table.get(num);
        int numit = l.linearSearch(t);
        if(numit != -1) {
            l.iteratorToIndex(numit);
            T value = this.Table.get(num).getIterator();
            return value;
        }
        return null;
    }

    /**
     * Determines whether a specified key is in the Table
     *
     * @param t the key to search for
     * @return whether the key is in the Table
     * @precondition t != null
     * @throws NullPointerException if the specified key is null
     */
    public boolean contains(String line, T t) throws NullPointerException {
        if (t == null) {
            throw new NullPointerException("Contains: t is null, cannot execute");
        }
        int num = hash(line);
        if(this.Table.get(num).linearSearch(t) > -1) {
            return true;
        }
        return false;
    }

    /** Mutators */

    /**
     * Inserts a new element in the Table at the end of the chain in the bucket to
     * which the key is mapped
     *
     * @param t the key to insert
     * @precondition t != null
     * @throws NullPointerException for a null key
     */
    public void put(String line, T t) throws NullPointerException {
        if (t == null) {
            throw new NullPointerException("put: t is null, cannot execute");
        }
        int num = this.hash(line);
        this.Table.get(num).addLast(t);
        numElements++;
    }

    /**
     * removes the key t from the Table calls the hash method on the key to
     * determine correct placement has no effect if t is not in the Table or for a
     * null argument
     *
     * @param t the key to remove
     * @throws NullPointerException if the key is null
     */
    public void remove(String line, T t) throws NullPointerException {
        if (t == null) {
            throw new NullPointerException("remove(): t is null, cannot execute");
        }
        int num = this.hash(line);
        List<T> l = this.Table.get(num);
        int numit = l.linearSearch(t);
        if(numit != -1) {
            l.iteratorToIndex(numit);
            l.removeIterator();
            numElements--;
        }
    }

    /**
     * Clears this hash table so that it contains no keys.
     */
    public void clear() {
        for (int i = 0; i < this.Table.size(); i++) {
            Table.set(i, new List<T>());
        }
        numElements = 0;
    }

    /** Additional Methods */

    public void printDuplicates(String line, T t, ArrayList<T> duplicates) {
        int bucket = hash(line);
        int count = 0;
        Table.get(bucket).placeIterator();
        while(!Table.get(bucket).offEnd()) {
            if(Table.get(bucket).getIterator().equals(t)) {
                System.out.println(Table.get(bucket).getIterator());
                duplicates.add(Table.get(bucket).getIterator());
                count++;
            }
            Table.get(bucket).advanceIterator();
        }
    }

    /**
     * Prints all the keys at a specified bucket in the Table. Tach key displayed on
     * its own line, with a blank line separating each key Above the keys, prints
     * the message "Printing bucket #<bucket>:" Note that there is no <> in the
     * output
     *
     * @param line the index in the Table
     */
    public void printBucket(String line) {
        int bucket = hash(line);
        System.out.println("\nUsers that likes " + line + ":" + "\n\n" + Table.get(bucket));
    }

    /**
     * Prints the first key at each bucket along with a count of the total keys with
     * the message "+ <count> -1 more at this bucket." Each bucket separated with
     * two blank lines. When the bucket is empty, prints the message "This bucket is
     * empty." followed by two blank lines
     */
    public void printTable() {
        String result = "\n";
        for (int i = 0; i < Table.size(); i++) {
            result += ("Bucket " + i);
            if(Table.get(i).isEmpty()) {
                result += " is empty";
            }
            else {
                result += (" " + Table.get(i).getFirst());
                result += (" " + (Table.get(i).getLength() - 1) + " more at this bucket");
            }
            result += "\n\n\n";
        }
        System.out.println(result);
    }

    /**
     * Starting at the first bucket, and continuing in order until the last bucket,
     * concatenates all elements at all buckets into one String
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < Table.size(); i++) {
            Table.get(i).placeIterator();
            for(int j = 0; j < this.Table.get(i).getLength(); j++) {
                result += Table.get(i).getIterator() +"\n\n";
                Table.get(i).advanceIterator();
            }
        }
        return result;
    }
}
