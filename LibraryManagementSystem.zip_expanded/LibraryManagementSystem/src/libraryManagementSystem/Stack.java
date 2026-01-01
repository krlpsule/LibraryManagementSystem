package libraryManagementSystem;

/*
 * MEMBER 4 – STACK & UNDO FEATURE
 * Name   : Ayşegül Özdemir
 * ID     : 2311051036
 *
 * Responsibilities:
 * - Custom Stack implementation (LIFO)
 * - Store borrow / return actions
 * - Support Undo last action feature
 *
 * NOTE:
 * This file contains ALL responsibilities of Member 4.
 * It is integrated by calling methods from Library.java.
 */

public class Stack {

    /*
     * Action class represents a Borrow or Return operation
     */
    public static class Action {
        private int bookId;
        private boolean previousAvailability;

        public Action(int bookId, boolean previousAvailability) {
            this.bookId = bookId;
            this.previousAvailability = previousAvailability;
        }

        public int getBookId() {
            return bookId;
        }

        public boolean getPreviousAvailability() {
            return previousAvailability;
        }
    }

    /*
     * Node class for Stack (Linked List based)
     */
    private static class Node {
        Action data;
        Node next;

        Node(Action data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;

    public Stack() {
        top = null;
    }

    /*
     * Push an action onto the stack
     * Worst-case Time Complexity: O(1)
     */
    public void push(Action action) {
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
    }

    /*
     * Pop the last action from the stack
     * Worst-case Time Complexity: O(1)
     */
    public Action pop() {
        if (top == null) {
            return null;
        }
        Action action = top.data;
        top = top.next;
        return action;
    }

    /*
     * Check whether stack is empty
     * Worst-case Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return top == null;
    }
}
