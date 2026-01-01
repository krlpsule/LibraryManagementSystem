package libraryManagementSystem;
//Member 3: Ahsen Durmaz
//Queue.java class borrows requests while following FIFO rule.
public class Queue<T> {
    //BorrowRequest class to store user info
    public class BorrowRequest {
        private String userName;
        private int bookId;
        //constructor to get user info and store them in private
        public BorrowRequest(String userName, int bookId) {
            this.userName = userName;
            this.bookId = bookId;
        }
        //getter methods to access private info
        public String getUserName() { return userName; }
        public int getBookId() { return bookId; }
    }
    //Node class to implement Queue in Singly Linked List based structure
    private class Node {
        T data;
        Node next;
        Node(T data) { this.data = data; }
    }
    //Queue have to keep track of front and rear
    private Node front, rear;
    //Worst case time complexity is O(1), because we don't traverse the queue to find rear. 
    public void enqueue(T request) {
        Node n = new Node(request);
        if (rear == null) front = rear = n;
        else {
            rear.next = n;
            rear = n;
        }
    }
    //Worst case time complexity is O(1), because we don't traverse the queue to find front. 
    public T dequeue() {
        if (front == null) return null;
        T data = front.data;
        front = front.next;
        if (front == null) rear = null;
        return data;
    }
    //Worst case time complexity is O(1), because isEmpty method just checks if the queue has data or not. 
    public boolean isEmpty() {
        return front == null;
    }
}
