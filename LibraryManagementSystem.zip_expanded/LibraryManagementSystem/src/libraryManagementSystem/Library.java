package libraryManagementSystem;

import java.io.*;
import java.util.Stack;

public class Library {

    public DynamicArray<Book> catalog = new DynamicArray<>();
    private BST bst = new BST();
    private Queue<Queue.BorrowRequest> requestQueue = new Queue<>();
    private Stack<String> actionStack = new Stack<>();
    private Queue<Integer> borrowQueue = new Queue<>();


    private static final String BOOK_FILE = "books.txt";

    public Library() {
        loadBooks();
    }

    private void loadBooks() {
        File file = new File(BOOK_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
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
    
    public void processBorrowRequest() {
        if (borrowQueue.isEmpty()) {
            System.out.println("No borrow requests in queue.");
            return;
        }

        int bookId = borrowQueue.dequeue();
        Book b = searchById(bookId);

        if (b == null) {
            System.out.println("Requested book no longer exists.");
            return;
        }

        if (!b.isAvailable()) {
            System.out.println("Book is already borrowed.");
            return;
        }

        b.setAvailable(false);
        saveBooks();

        System.out.println("Book borrowed: " + b.getTitle());
    }

    
    public void requestBorrow(int bookId) {
        Book b = searchById(bookId);

        if (b == null) {
            System.out.println("Book not found.");
            return;
        }

        borrowQueue.enqueue(bookId);
        System.out.println("Borrow request added to queue.");
    }

    
    public void deleteBook(int id) {
        for (int i = 0; i < catalog.size(); i++) {
            Book b = catalog.get(i);

            if (b.getId() == id) {
                catalog.remove(i);

                // Rebuild BST to keep it consistent
                bst.clear();
                for (int j = 0; j < catalog.size(); j++) {
                    bst.insert(catalog.get(j));
                }
                actionStack.push("DELETE," + b.toFileString());

                saveBooks();
                System.out.println("Succesfully deleted: " + b.getTitle());
                return;
            }
        }

        System.out.println("Book not found.");
    }


    private void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (int i = 0; i < catalog.size(); i++) {
                bw.write(catalog.get(i).toFileString());
                bw.newLine();
            }
        } catch (IOException ignored) {}
    }

    public void addBook(Book book) {
        catalog.add(book);
        bst.insert(book);
        saveBooks();
    }

    public Book searchById(int id) {
        for (int i = 0; i < catalog.size(); i++)
            if (catalog.get(i).getId() == id)
                return catalog.get(i);
        return null;
    }

    public Book searchByTitle(String title) {
        return bst.search(title);
    }

    public void listAllBooks() {
        for (int i = 0; i < catalog.size(); i++)
            System.out.println(catalog.get(i));
    }

    public void listAlphabetically() {
        for (Book b : bst.inOrderTraversal())
            System.out.println(b);
    }

    public void borrowBook(int id) {
    	
        Book b = searchById(id);
        if (b == null) return;

        if (!b.isAvailable()) {
            requestQueue.enqueue(
                new Queue<Queue.BorrowRequest>().new BorrowRequest("User", id)
            );
            return;
        }

        b.setAvailable(false);
        actionStack.push("BORROW," + id);
        saveBooks();
    }

    public void returnBook(int id) {
        Book b = searchById(id);
        if (b == null) return;

        b.setAvailable(true);
        actionStack.push("RETURN," + id);
        saveBooks();

        if (!requestQueue.isEmpty()) {
            Queue.BorrowRequest r = requestQueue.dequeue();
            borrowBook(r.getBookId());
        }
    }

    public void undo() {
        if (actionStack.isEmpty()) return;

        String[] a = actionStack.pop().split(",");
        Book b = searchById(Integer.parseInt(a[1]));
        if (b == null) return;

        b.setAvailable(a[0].equals("BORROW"));
        saveBooks();
    }
}
