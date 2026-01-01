package libraryManagementSystem;

/*
 
 * This Stack class is used to support the Undo feature in the
 * Library Management System.
 *
 * Data Structure: Stack (LIFO – Last In First Out)
 *
 * The stack stores actions as String values 
 */

public class Stack {

    /*
     * Node class represents a single element in the stack.
     * Each node stores one action (String) and a reference
     * to the next node in the stack.
     */
    private static class Node {
        String data;   // Stores the action information
        Node next;     // Reference to the next node

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    // Top of the stack (last inserted element)
    private Node top;

    /*
     * Constructor initializes an empty stack
     */
    public Stack() {
        top = null;
    }

    /*
     * Pushes a new action onto the stack
     *
     * Worst-case Time Complexity: O(1)
     * Because insertion is done at the top of the stack.
     */
    public void push(String data) {
        Node newNode = new Node(data); // Create new node
        newNode.next = top;            // Point to previous top
        top = newNode;                 // Update top
    }

    /*
     * Removes and returns the last action from the stack
     *
     * Worst-case Time Complexity: O(1)
     * Because removal is done from the top of the stack.
     */
    public String pop() {
        if (top == null)
            return null;               // Stack is empty

        String data = top.data;        // Get top element
        top = top.next;                // Remove top
        return data;                   // Return removed data
    }

    /*
     * Checks whether the stack is empty
     *
     * Worst-case Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return top == null;
    }
}
