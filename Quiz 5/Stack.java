public class Stack<T> {
    private final CircularDoublyLinkedList<T> stackBody;

    /**
     * Constructs an empty stack.
     */
    public Stack(){

        this.stackBody = new CircularDoublyLinkedList<>();
    }

    /**
     * Adds a new element to the top of the stack.
     *
     * @param data the data to be stored in the new element
     */
    public void add(T data){

        this.stackBody.addToEnd(data);
    }

    /**
     * Removes and returns the element from the top of the stack.
     *
     * @return the element from the top of the stack, or null if the stack is empty
     */
    public T pop(){

        if (this.stackBody.length() == 0) return null;

        return this.stackBody.popFromEnd();
    }

    /**
     * Returns the element from the top of the stack without removing it.
     *
     * @return the element from the top of the stack, or null if the stack is empty
     */
    public T read(){

        T data = this.pop();
        this.add(data);
        return data;
    }

    public int length(){
        return this.stackBody.length();
    }
}
