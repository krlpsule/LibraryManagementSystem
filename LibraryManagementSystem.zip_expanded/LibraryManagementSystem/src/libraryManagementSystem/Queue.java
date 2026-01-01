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
        //constructor
        Node(T data) { this.data = data;}
    }
    //Queue have to keep track of front and rear
    private Node front, rear;
    //Worst case time complexity is O(1), because we don't traverse the queue to find rear. 
    public void enqueue(T request) {
        Node n = new Node(request);
        //checks if the queue is empty
        if (rear == null) front = rear = n; //Queue is empty, then just insert new request.
        else { //Queue is not empty, then insert new request and update rear.
            rear.next = n;
            rear = n;
        }
    }
    //Worst case time complexity is O(1), because we don't traverse the queue to find front. 
    public T dequeue() {
        //checks if the queue is empty
        if (front == null) return null; //Empty queue, nothing to delete, returns null
        //If not empty, temporarly store value of front and delete the front node
        T data = front.data;
        front = front.next;
        //If there is no other element left, update rear.
        if (front == null) rear = null;
        //return the temporarly stored value of old front
        return data;
    }
    //Worst case time complexity is O(1), because isEmpty method just checks if the queue has data or not. 
    public boolean isEmpty() {
        return front == null;
    }
}
