package libraryManagementSystem;

import java.io.*;
import java.util.Stack;

public class Library {

    // O(1) adding stack, queue, array and bst using created classes
    public DynamicArray<Book> catalog = new DynamicArray<>();

    private BST bst = new BST();

    private Queue<Queue.BorrowRequest> requestQueue = new Queue<>();

    private Stack<String> actionStack = new Stack<>();

    private Queue<Integer> borrowQueue = new Queue<>();


    // O(1) file to save 
    private static final String BOOK_FILE = "books.txt";

    // O(n) library has load books
    public Library() {
        loadBooks();
    }

    // O(n) for loading it we use the book file and return if he exists
    private void loadBooks() {
        File file = new File(BOOK_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {// O(n) reading and displaying the contents
                String[] p = line.split(",");
                Book b = new Book(
                        Integer.parseInt(p[0]),
                        p[1],
                        p[2],
                        Boolean.parseBoolean(p[3])
                );
                catalog.add(b);
                bst.insert(b);
            }
        } catch (IOException ignored) {}
    }

    // O(n) commands to process the queue orequests for borrowing
    public void processBorrowRequest() {
        if (borrowQueue.isEmpty()) {
            System.out.println("No borrow requests in queue.");
            return;//returning if no requests
        }

        int bookId = borrowQueue.dequeue();
        Book b = searchById(bookId);//search the book 

        if (b == null) {//if book doesnt exist
            System.out.println("Requested book no longer exists.");
            return;
        }

        if (!b.isAvailable()) {//if book exists but unavailable
            System.out.println("Book is already borrowed.");
            return;
        }

        b.setAvailable(false);//changing its availability
        saveBooks();//saving

        System.out.println("Book borrowed: " + b.getTitle());//printing the book name
    }

    // O(n) first we search the book
    public void requestBorrow(int bookId) {
        Book b = searchById(bookId);//first we search the book

        if (b == null) {//in case it doesnt exists
            System.out.println("Book not found.");
            return;
        }

        borrowQueue.enqueue(bookId);//otherwise we add to the queue to borrow
        System.out.println("Borrow request added to queue.");
    }

    // O(n) traversing the book list to find the book by id
    public void deleteBook(int id) {
        for (int i = 0; i < catalog.size(); i++) //traversing the book list to find the book by id
        {
            Book b = catalog.get(i);

            if (b.getId() == id) {//comparing ids
                catalog.remove(i);//remove if the match was found

                // O(n) clearing the bst
                bst.clear();
                for (int j = 0; j < catalog.size(); j++) {
                    bst.insert(catalog.get(j));//inserting all books back
                }
                actionStack.push("DELETE," + b.toFileString());//deletion command

                saveBooks();//save everything
                System.out.println("Succesfully deleted: " + b.getTitle());//message to the user
                return;
            }
        }

        System.out.println("Book not found.");//otherwise print this message
    }

    // O(n) saving book to the file 
    private void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOK_FILE))) {//saving book to the file 
            for (int i = 0; i < catalog.size(); i++) {//traversing
                bw.write(catalog.get(i).toFileString());//adding the file
                bw.newLine();//going to the new line
            }
        } catch (IOException ignored) {}//exception catching
    }

    // O(1) to add we add to the catalog, inserting it into the bst aand then saving everything
    public void addBook(Book book) {
        catalog.add(book);//to add we add to the catalog
        bst.insert(book);//inserting it into the bst
        saveBooks();//saving everything
    }

    // O(n) traverse
    public Book searchById(int id) {
        for (int i = 0; i < catalog.size(); i++)//traverse
            if (catalog.get(i).getId() == id)//compare the id
                return catalog.get(i);//return the file with id
        return null;
    }

    // O(log n) calling search method to find 
    public Book searchByTitle(String title) {
        return bst.search(title);//calling search method to find 
    }

    // O(n) traverse the whole list 
    public void listAllBooks() {
        for (int i = 0; i < catalog.size(); i++)//traverse the whole list 
            System.out.println(catalog.get(i));//while printing the values
    }

    // O(n) using the inorder traversal, we print the ordered tree
    public void listAlphabetically() {
        for (Book b : bst.inOrderTraversal())//using the inorder traversal, we print the ordered tree
            System.out.println(b);
    }

    // O(n) check the book list and if this id doesnt exist return
    public void borrowBook(int id) {
        Book b = searchById(id);//check the book list and if this id doesnt exist return
        if (b == null) return;

        if (!b.isAvailable()) {//if using isAvailable we found book and it is not available
            requestQueue.enqueue(
                new Queue<Queue.BorrowRequest>().new BorrowRequest("User", id)//add the request with info
            );
            return;
        }

        b.setAvailable(false);//use borrow otherwise
        actionStack.push("BORROW," + id);
        saveBooks();//save everything
    }

    // O(n) search the list for the id
    public void returnBook(int id) {
        Book b = searchById(id);//search the list for the id
        if (b == null) return;//if doesnt exist return

        b.setAvailable(true);//if inavailable, can be returned
        actionStack.push("RETURN," + id);
        saveBooks();//save data

        if (!requestQueue.isEmpty()) {//if this book has a queue, we dequeue the listener
            Queue.BorrowRequest r = requestQueue.dequeue();
            borrowBook(r.getBookId());//call borrow
        }
    }

    // O(n) waiting for stack
    public void undo() {
        if (actionStack.isEmpty()) return;//if there are no action, return

        String[] a = actionStack.pop().split(",");//otherwise pop the stack
        Book b = searchById(Integer.parseInt(a[1]));
        if (b == null) return;

        b.setAvailable(a[0].equals("BORROW"));//change the availability
        saveBooks();//save info
    }
}
