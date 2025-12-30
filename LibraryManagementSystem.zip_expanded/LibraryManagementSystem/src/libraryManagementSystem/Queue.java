package libraryManagementSystem;

public class Queue<T> {

    public class BorrowRequest {
        private String userName;
        private int bookId;

        public BorrowRequest(String userName, int bookId) {
            this.userName = userName;
            this.bookId = bookId;
        }

        public String getUserName() { return userName; }
        public int getBookId() { return bookId; }
    }

    private class Node {
        T data;
        Node next;
        Node(T data) { this.data = data; }
    }

    private Node front, rear;

    public void enqueue(T item) {
        Node n = new Node(item);
        if (rear == null) front = rear = n;
        else {
            rear.next = n;
            rear = n;
        }
    }

    public T dequeue() {
        if (front == null) return null;
        T data = front.data;
        front = front.next;
        if (front == null) rear = null;
        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
