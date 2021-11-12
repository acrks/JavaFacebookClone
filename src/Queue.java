
/**
 * Queue class
 * @author Daniel Winkler
 * @author Alex Crooks
 * CIS 22C, Group Project
 */

import java.util.NoSuchElementException;

public class Queue<T extends Comparable<T>> {

    // Node
    private class Node {
        // Node private variables
        private T data;
        private Node next;

        // Node constructor
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // private variables
    private int length;
    private Node front;
    private Node end;

    // Constructor
    public Queue() {
        this.length = 0;
        this.front = null;
        this.end = null;
    }

    // Copy Constructor
    public Queue(Queue<T> original) {
        if (original == null) {
            return;
        }
        if (original.length == 0) {
            length = 0;
            front = null;
            end = null;
        } else {
            Node temp = original.front;
            while (temp != null) {
                enqueue(temp.data);
                temp = temp.next;
            }
        }
    }

    /** ACCESSORS **/

    /**
     * Add a node into the queue
     *
     * @param data
     * @postcondition length++. New node is added at the end of this Queue.
     */
    public void enqueue(T data) {
        Node N = new Node(data);
        if (isEmpty()) {
            front = end = N;
        } else {
            end.next = N;
            end = end.next;
        }
        length++;
    }

    /**
     * Remove a node from the queue
     *
     * @precondition !isEmpty()
     * @throws NoSuchElementException if precondition is violated
     * @postcondition queue--, front node is now front.next
     */
    public void dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("dequeue(): Queue is empty. " + "No element to remove");
        } else if (length == 1) {
            front = end = null;
        } else {
            front = front.next;
        }
        length--;
    }

    /**
     * Return the first node's data
     *
     * @precondition !isEmpty()
     * @throws NoSuchElementException if precondition is violated
     * @return first node's data
     */
    public T getFront() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("getFront(): Queue is empty. " + "No element to remove");
        }
        return front.data;
    }

    /**
     * Return the last node's data
     *
     * @precondition !isEmpty()
     * @throws NoSuchElementException if precondition is violated
     * @return last node's data
     */
    public T getEnd() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("getEnd(): Queue is empty. " + "No element to remove");
        }
        return end.data;
    }

    /**
     * Returns stack's length
     *
     * @return length
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns true if length is empty, false if it's not
     *
     * @return (length == 0)
     */
    public boolean isEmpty() {
        if (length == 0) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether data is sorted in ascending order by calling its recursive
     * helper method isSorted() Note: when length == 0 data is (trivially) sorted
     *
     * @return whether the data is sorted
     */
    public boolean isSorted() {
        return isSorted(front);
    }

    /**
     * Helper method to isSorted Recursively determines whether data is sorted
     *
     * @param node the current node
     * @return whether the data is sorted
     */
    private boolean isSorted(Node node) {
        if (node.next == null) {
            return true;
        } else {
            if ((node.data).compareTo((node.next).data) > 0) {
                return false;
            }
            return isSorted(node.next);
        }
    }

    /** Additional Operations */

    /**
     * Returns out the stack's data
     *
     * @return all of this Stack's data
     */
    @Override
    public String toString() {
        String result = "";
        Node temp = front;
        for (int i = 0; i < this.length; i++) {
            result += (temp.data + " ");
            temp = temp.next;
        }
        return result + "\n";
    }

    /**
     * Prints in reverse order to the console, followed by a new line by calling the
     * recursive helper method printReverse
     */
    public void printReverse() {
        printReverse(front);
    }

    /**
     * Recursively prints to the console the data in reverse order (no loops)
     *
     * @param node the current node
     */
    private void printReverse(Node node) {
        if (node == null) {
            return;
        }

        printReverse(node.next);
        System.out.println(node.data);
    }

    /**
     * Returns the location from 1 to length where value is located by calling the
     * private helper method binarySearch
     *
     * @param value the value to search for
     * @return the location where value is stored from 1 to length, or -1 to
     *         indicate not found
     * @precondition isSorted()
     * @throws IllegalStateException when the precondition is violated.
     */
    public int binarySearch(T value) throws IllegalStateException {
        if (!isSorted()) {
            throw new IllegalStateException("binarySearch: Cannot implement, queue is not sorted!");
        }
        return binarySearch(1, length, value);
    }

    /**
     * Searches for the specified value in by implementing the recursive
     * binarySearch algorithm
     *
     * @param low   the lowest bounds of the search
     * @param high  the highest bounds of the search
     * @param value the value to search for
     * @return the location at which value is located from 1 to length or -1 to
     *         indicate not found
     */
    private int binarySearch(int low, int high, T value) {
        if (high < low) {
            return -1; // not found
        }
        int mid = low + (high - low) / 2; // midpoint formula
        Node temp = front;
        for (int i = 1; i < mid; i++) {
            temp = temp.next;
        }
        if (value.compareTo(temp.data) == 0) {
            return mid;
        } else if (value.compareTo(temp.data) < 0) {
            return binarySearch(low, mid - 1, value);
        } else {
            return binarySearch(mid + 1, high, value);
        }
    }

    /**
     * Checks if this queue is the same as another stack
     *
     * @param o a Queue object.
     * @return true if o is equal to this (queue). False if o is not equal to this.
     */
    @Override
    public boolean equals(Object o) {
        Queue c = (Queue) o;
        if (this == o) {
            return true;
        } else if (!(o instanceof Queue)) {
            return false;
        } else if (getLength() != c.getLength()) {
            return false;
        } else {
            Node temp = front;
            Node temp2 = c.front;
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

    /**
     * Uses the iterative linear search algorithm to locate a specific element and
     * return its position
     *
     * @param element the value to search for
     * @return the location of value from 1 to length
     * Note that in the case
     *         length==0 the element is considered not found
     */
    public int linearSearch(T element) {
        Node temp = front;
        for (int i = 1; i <= getLength(); i++) {
            if (element.compareTo(temp.data) == 0) {
                return i;
            }
            temp = temp.next;
        }
        return -1;
    }
}
