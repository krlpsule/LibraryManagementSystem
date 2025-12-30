package libraryManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class BST {

    private class Node {
        Book data;
        Node left, right;

        Node(Book data) {
            this.data = data;
        }
    }

    private Node root;

    public BST() {
        root = null;
    }

    // Worst-case: O(n)
    public void insert(Book book) {
        root = insertRec(root, book);
    }

    private Node insertRec(Node root, Book book) {
        if (root == null) return new Node(book);

        if (book.getTitle().compareToIgnoreCase(root.data.getTitle()) < 0)
            root.left = insertRec(root.left, book);
        else
            root.right = insertRec(root.right, book);

        return root;
    }

    // Worst-case: O(n)
    public Book search(String title) {
        return searchRec(root, title);
    }

    private Book searchRec(Node root, String title) {
        if (root == null) return null;

        int cmp = title.compareToIgnoreCase(root.data.getTitle());

        if (cmp == 0) return root.data;
        if (cmp < 0) return searchRec(root.left, title);
        return searchRec(root.right, title);
    }

    // Worst-case: O(n)
    public List<Book> inOrderTraversal() {
        List<Book> list = new ArrayList<>();
        inOrderRec(root, list);
        return list;
    }

    private void inOrderRec(Node root, List<Book> list) {
        if (root != null) {
            inOrderRec(root.left, list);
            list.add(root.data);
            inOrderRec(root.right, list);
        }
    }

    public void clear() {
        root = null;
    }
}
