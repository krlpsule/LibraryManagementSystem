package libraryManagementSystem;

// BST class is used to search books by title and list them alphabetically
// using Binary Search Tree (BST) data structure.
public class BST {
    
    // Node class to implement BST structure
    private class Node {
        Book data;              // book object stored in the node
        Node left, right;       // left and right child
        
        // constructor to initialize node with a Book
        Node(Book data) {
            this.data = data;
        }
    }
    // root node of the BST
    private Node root;
    // constructor initializes empty BST
    public BST() {
        root = null;
    }

    // worst-case time complexity: O(n), since it inserts a book into the BST using book title as the key
    public void insert(Book book) {
        root = insertRec(root, book);
    }
    
    // recursive helper method for insert operation
    private Node insertRec(Node root, Book book) {
        // if tree is empty, create a new node
        if (root == null) return new Node(book);
        
        // compare book titles (case-insensitive)
        if (book.getTitle().compareToIgnoreCase(root.data.getTitle()) < 0)
            root.left = insertRec(root.left, book);      // insert into left subtree
        else
            root.right = insertRec(root.right, book);    // insert into right subtree

        return root;
    }

    // worst-case time complexity: O(n), because it searches for a book by title in the BST
    public Book search(String title) {
        return searchRec(root, title);
    }
    
    // recursive helper method for search operation
    private Book searchRec(Node root, String title) {
        // if tree is empty or book not found
        if (root == null) return null;

        int cmp = title.compareToIgnoreCase(root.data.getTitle());

        if (cmp == 0) return root.data;                     // if titles match, return the book
        if (cmp < 0) return searchRec(root.left, title);    // if title is smaller, search left subtree
        return searchRec(root.right, title);                // otherwise, search right subtree
    }

    // worst-case time complexity: O(n), since it returns all books in alphabetical order using in-order traversal
     public DynamicArray<Book> inOrderTraversal() {
        DynamicArray<Book> list = new DynamicArray<>();
        inOrderRec(root, list);
        return list;
    }
    
    // recursive helper method for in-order traversal
    private void inOrderRec(Node node, DynamicArray<Book> list) {
        if (node != null) {
            inOrderRec(node.left, list);     // visit left subtree
            list.add(node.data);             // visit root
            inOrderRec(node.right, list);    // visit right subtree
        }
    }
    
    // worst-case time complexity: O(1)
    // clears the BST by removing reference to root
    public void clear() {
        root = null;
    }
}
