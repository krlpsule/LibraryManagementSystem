package libraryManagementSystem;

import java.util.Scanner;

public class Main {
	
	private static void pause(Scanner sc) {//METHOD TO NOT PRINT MENU IF USER DOESNT WANT AND LET USER SEE THE RESULT OF THE PREVIOUS ACTION
	    System.out.println("\nPress ENTER to return to menu...");
	    sc.nextLine();
	}
	
	private static int readValidInt(Scanner sc, String prompt) {//method to validate input to avoid exceptions and let user continue without restarting
	    while (true) {
	        System.out.print(prompt);
	        String input = sc.nextLine();//if true we go further

	        try {//otherwise we catch the exception and write the message
	            return Integer.parseInt(input);
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. You cannot use letters for the ID.");
	        }
	    }
	}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);//initiation
        Library lib = new Library();

        while (true) {//options
            System.out.println("""
                1 Add book
                2 Search by ID
                3 Search by title
                4 List all
                5 List alphabetically
                6 Borrow book
                7 Return book
                8 Undo
                9 Delete book
                10. Request to borrow a book
                11. Process borrow requests

                0 Exit
                """);

            int c = sc.nextInt();//c for the switch
            sc.nextLine();

            switch (c) {
            case 1 -> {
                int id = readValidInt(sc, "Enter the new book's ID:");//validating the input and also writing the message
                System.out.print("Enter the new book's title:");
                String t = sc.nextLine();
                System.out.print("Enter the new book's author: ");
                String a = sc.nextLine();
                lib.addBook(new Book(id, t, a, true));//the data is saved as strings, we insert everything s add book
                System.out.println("Succesfully added!");
                pause(sc);//method to wait and display press enter message
            }
            
                case 2 -> {
                	int id = readValidInt(sc, "Enter the ID of the book you want to find:");//validating and processing the request
                    sc.nextLine();
                    Book b = lib.searchById(id);
                    System.out.println(b != null ? b : "Not found");//otherwise printing not found
                    pause(sc);
                }

                case 3 -> {//searching by the name, calling the responsible method, otherwise printing not found
                    System.out.print("Enter the title of the book you are searching: ");
                    String title = sc.nextLine();
                    Book b = lib.searchByTitle(title);
                    System.out.println(b != null ? b : "Not found");
                    pause(sc);
                }
                case 4 -> {
                    lib.listAllBooks();//calling listing method
                    pause(sc);
                }
                case 5 -> {
                    lib.listAlphabetically();//calling listing method
                    pause(sc);
                }
                
                case 6 ->
                { int id = readValidInt(sc, "Enter the ID of the book you want to borrow:");//calling borrowing method
                lib.borrowBook(id);
                 System.out.println("Successfully borrowed!");
                 pause(sc);
                }
                
                case 7 ->{ //calling returning method
                	 int id = readValidInt(sc, "Enter the ID of the book you want to return:");
                	 lib.returnBook(id); 
                	 System.out.println("Successfully returned!");
                	 pause(sc);
                }
                
                case 9 -> {//calling deleting method
                	 int id = readValidInt(sc, "Enter the ID of the book you want to delete:");
                    lib.deleteBook(id);
                    pause(sc);
                }

                case 8 -> lib.undo();//calling undo method
                
                case 10 -> {//calling request of borrowing method
                    int id = readValidInt(sc, "Book ID: ");
                    lib.requestBorrow(id);
                    pause(sc);
                }
                
                case 11 -> {//calling process borrowing method
                    lib.processBorrowRequest();
                    pause(sc);
                }

 
                case 0 -> { return; }}
            }
        }
    }
