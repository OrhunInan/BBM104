public class Queue<T> {

    private final CircularDoublyLinkedList<T> queueBody;

    /**
     * Constructs an empty queue.
     */
    public Queue(){

        this.queueBody = new CircularDoublyLinkedList<>();
    }

    /**
     * Adds a new element to the end of the queue.
     *
     * @param data the data to be stored in the new element
     */
    public void add(T data){

        this.queueBody.addToStart(data);
    }

    /**
     * Removes and returns the element from the front of the queue.
     *
     * @return the element from the front of the queue, or null if the queue is empty
     */
    public T pop(){

       return this.queueBody.popFromEnd();
    }

    /**
     * Returns the element from the front of the queue without removing it.
     *
     * @return the element from the front of the queue, or null if the queue is empty
     */
    public T read(){

        T data = this.queueBody.popFromEnd();
        this.queueBody.addToEnd(data);

        return data;
    }
}
