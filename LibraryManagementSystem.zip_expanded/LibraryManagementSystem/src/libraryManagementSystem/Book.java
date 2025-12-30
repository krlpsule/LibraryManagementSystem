package libraryManagementSystem;

public class Book {//book class has id, name, author and availability bool
    private int id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int id, String title, String author, boolean isAvailable) {//initialization
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }//getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

   
    public String toFileString() {//saving to file
        return id + "," + title + "," + author + "," + isAvailable;
    }

    @Override
    public String toString() {//method to write out the insides to the users
        return "ID: " + id + " | Title: " + title + " | Author: " + author + " | Available: " + (isAvailable ? "Yes" : "No");
    }
}
