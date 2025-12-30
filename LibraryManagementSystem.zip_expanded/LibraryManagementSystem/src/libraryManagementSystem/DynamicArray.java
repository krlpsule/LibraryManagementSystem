package libraryManagementSystem;


public class DynamicArray<T> {
    private T[] array;
    private int size;      
    private int capacity; 

    @SuppressWarnings("unchecked")
    public DynamicArray() {
        this.capacity = 2; 
        this.array = (T[]) new Object[capacity];
        this.size = 0;
    }

    // Add method
    // Time Complexity: Average O(1), If it needs to grow  O(N)
    public void add(T element) {
        if (size == capacity) {
            resize();
        }
        array[size] = element;
        size++;
    }

    //Accessing element 
    // Time Complexity: O(1)
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return array[index];
    }

    // Removing element and shifting
    // Time Complexity: O(N) (Because we are shifting the elements)
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        T removedElement = array[index];

       //Shift the elements
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

       
        array[size - 1] = null;
        size--;
        
        return removedElement;
    }

    //size function
    public int size() {
        return size;
    }

    // (Resizing Logic)
    // It doubles the size
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        T[] newArray = (T[]) new Object[newCapacity];

        // Copy the elements from old array
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }

        this.array = newArray;
        this.capacity = newCapacity;
        
    }
}