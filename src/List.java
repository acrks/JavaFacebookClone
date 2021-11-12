
/**
 * Defines a singly-linked list class
 * @author Daniel Winkler
 * @author Alex Crooks
 * CIS 22C, Lab 6
 */

import java.util.NoSuchElementException;

public class List<T> {

    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;

        }
    }

    // Private Variables
    private int length;
    private Node first;
    private Node last;
    private Node iterator;

    /**** CONSTRUCTORS ****/

    /**
     * Instantiates a new List with default values
     *
     * @postcondition Initializes list
     */
    /**
     * Instantiates a new List by copying another List
     * @param original the List to make a copy of
     * @postcondition a new List object, which is an identical
     * but separate copy of the List original
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

    /**
     * Instantiates a new List with copy of list values
     *
     * @postcondition Initializes a deep copy of the list
     */
    public List() {
        this.length = 0;
        this.first = this.last = this.iterator = null;
    }


    /**** MUTATORS ****/

    /**
     * Creates a new first element
     *
     * @param data the data to insert at the front of the list
     * @postcondition a new first element
     */
    public void addFirst(T data) {
        Node N = new Node(data);
        if (first == null) {
            first = last = N;
        } else {
            N.next = first;
            first.prev = N;
            first = N;
        }
        length++;
    }

    /**
     * Creates a new last element
     *
     * @param data the data to insert at the end of the list
     * @postcondition a new last element
     */
    public void addLast(T data) {
        Node N = new Node(data);
        if (last == null) {
            first = last = N;
        } else {
            last.next = N;
            N.prev = last;
            last = N;
        }
        length++;
    }


    /**
     * removes the element at the front of the list
     *
     * @precondition first != null
     * @postcondition length-- / first Node removed
     * @throws NoSuchElementException when precondition is violated
     */
    public void removeFirst() throws NoSuchElementException {
        if (length == 0) { // precondition
            throw new NoSuchElementException("removeFirst: list is empty. Cannot remove.");
        } else if (length == 1) { // edge case
            first = last = iterator = null;
        } else { // general case
            if (iterator == first) { // edge case
                iterator = null;
            }
            first = first.next;
            first.prev = null;
        }
        length--;
    }

    /**
     * removes the element at the end of the list
     *
     * @precondition last != null
     * @postcondition length-- / last Node removed
     * @throws NoSuchElementException when precondition is violated
     */
    public void removeLast() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException("removeLast: list is empty. Nothing to remove.");
        } else if (length == 1) {
            first = last = iterator = null;
        } else {
            if (iterator == last) {
                iterator = null;
            }
            last = last.prev;
            last.next = null;
        }
        length--;
    }

    /**
     * places the iterator at the first Node
     *
     * @postcondition iterator = first
     * @throws NoSuchElementException when precondition is violated
     */
    public void placeIterator()
    {
        iterator = first;
    }

    /**
     * add Node after the iterator
     *
     * @precondition iterator != null
     * @postcondition length++ / Node added after iterator
     * @throws NullPointerException when precondition is violated
     */
    public void addIterator(T data) throws NullPointerException {
        if (iterator == null) { // precondition
            throw new NullPointerException("addIterator: iterator is Null.");
        } else if (iterator == last) { // edge case
            addLast(data);
        } else { // general case
            Node node = new Node(data);

            node.next = iterator.next;
            node.prev = iterator;

            iterator.next.prev = node;
            iterator.next = node;

            length++;
        }
    }


    /**
     * removes the Node that is pointed to by the iterator
     *
     * @precondition !offEnd()
     * @postcondition iterator removed
     * @throws NoSuchElementException when precondition is violated
     */
    public void removeIterator() throws NullPointerException {
        if (offEnd()) { // precondition
            throw new NullPointerException("removeIterator: iterator is off end.");
        } else if (iterator == first) { // edge case
            removeFirst(); // should set iterator to null in this case
        } else if (iterator == last) { // edge case
            removeLast(); // should set iterator to null in this case
        } else { // general case
            iterator.next.prev = iterator.prev;
            iterator.prev.next = iterator.next;
            iterator = null;
            length--;
        }
    }

    /**
     * moves the iterator one node forward
     *
     * @precondition !offEnd()
     * @postcondition Iterator now points to the next node
     * @throws NoSuchElementException when precondition is violated
     */
    public void advanceIterator() throws NoSuchElementException {
        if (offEnd()) {
            throw new NullPointerException("advanceIterator: iterator is null. Nothing to advance");
        }
        iterator = iterator.next;
    }

    /**
     * moves the iterator one node backwards
     *
     * @precondition !offEnd()
     * @postcondition Iterator now points to the previous node
     * @throws NoSuchElementException when precondition is violated
     */
    public void reverseIterator() throws NoSuchElementException{
        if (offEnd()) {
            throw new NullPointerException("reverseIterator: iterator is null. Nothing to advance");
        }
        iterator = iterator.prev;
    }

    /**** ACCESSORS ****/

    /**
     * Returns the value stored in the first node
     *
     * @precondition length > 0 / At least Node one must be in the listCore.List
     * @return the value stored at node first
     * @throws NoSuchElementException when precondition is violated
     */
    public T getFirst() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException("getFirst: List is Empty. No data to access!");
        }
        return first.data; // why do we return first.data and not first?
    }

    /**
     * Returns the value stored in the last node
     *
     * @precondition length > 0 / At least Node one must be in the listCore.List
     * @return the value stored in the node last
     * @throws NoSuchElementException when precondition is violated
     */
    public T getLast() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException("getLast: List is Empty. No data to access!");
        }
        return last.data; // why do we return last.data and not last?
    }

    /**
     * Returns the current length of the list
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
        if (length == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the current length of the list
     * @return the length of the list from 0 to n
     * @precondition iterator != null or (!offEnd())
     * @throws NoSuchElementException
     * @throws NullPointerException
     */
    public T getIterator() throws NoSuchElementException, NullPointerException {
        if (iterator == null) {
            throw new NullPointerException("getIterator: Iterator is null. No data to access!");
        }
        if (length == 0) {
            throw new NoSuchElementException("getIterator: list is empty. No data to access!");
        }
        return iterator.data;
    }

    /**
     * Returns true if is off the end
     *
     * @return the length of the list from 0 to n
     */
    public boolean offEnd(){
        return (iterator == null);
    }

    /**
     * Checks if a list is the same as another list
     * @precondition list is initialized
     * @param o is a list of the same type of this object
     * @return true if the two lists are equal, false if the lists are not equal.
     */
    @Override public boolean equals(Object o) {
        List c = (List) o;

        if (this == o) {
            return true;
        } else if(!(o instanceof List)) {
            return false;
        } else if(getLength() != c.getLength()){
            return false;
        } else{
            Node temp = first;
            Node temp2 = c.first;
            while (temp != null) {
                if (!(temp.data.equals(temp2.data))) {
                    return false;
                }
                temp = temp.next;
                temp2 = temp2.next;
            }
            return true;
        }
    }

    /**** ADDITIONAL OPERATIONS ****/

    /**
     * returns a string that contains the list's data
     *
     * @return the List as a String for display
     */
    @Override
    public String toString() {
        String result = "";
        Node temp = first;
        for(int i = 0; i < this.length; i++) {
            result += temp.data + "\n";
            temp = temp.next;
        }
        return result + "\n";
    }

    /**
     * prints the list
     * @throws NoSuchElementException if precondition is violated
     */
    public void printNumberedList() throws NoSuchElementException{
        if (length == 0) {
            throw new NoSuchElementException("printNumberedList: list is empty. Nothing to advance");
        }
        Node temp = first;
        for (int i = 0; i < this.length; i++){
            System.out.println((i + 1) + ": " + temp.data + "\n");
            temp = temp.next;
        }
    }

    /**
     * Points the iterator at first
     * and then advances it to the
     * specified index
     * @param index the index where
     * the iterator should be placed
     * @precondition 0 < index <= length
     * @throws IndexOutOfBoundsException
     * when precondition is violated
     */
    public void iteratorToIndex(int index) throws IndexOutOfBoundsException{
        if (0 > index || index > length){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        placeIterator();
        for(int i  = 0; i < index; i++){
            advanceIterator();
        }
    }

    /**
     * Searches the List for the specified
     * value using the linear  search algorithm
     * @param value the value to search for
     * @return the location of value in the
     * List or -1 to indicate not found
     * Note that if the List is empty we will
     * consider the element to be not found
     * post: position of the iterator remains
     * unchanged
     */
    public int linearSearch(T value) {
        Node temp = first;
        for(int i = 0; i < length; i++) {
            if(temp.data.equals(value)) {
                return i;
            }
            temp = temp.next;
        }
        return -1;
    }
}
