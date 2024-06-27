public class Node<T>{

    private T data;
    private Node<T> next;
    private Node<T> previous;

    /**
     * Constructs a new node with the given data and sets the next and previous references to itself.
     *
     * @param data the data to be stored in the node
     */
    public Node(T data){

        this.data = data;
        this.next = this.previous = this;
    }

    /**
     * Constructs a new node with the given data and next and previous references.7
     *
     * @param data the data to be stored in the node
     * @param next the reference to the next node in the list
     * @param previous the reference to the previous node in the list
     */
    public Node(T data, Node<T> next, Node<T> previous) {

        this.data = data;
        this.next = next;
        this.previous = previous;
    }

    /**
     * Sets the data of the node to the given value.
     *
     * @param data the new data to be stored in the node
     */
    public void setData(T data){

        this.data = data;
    }

    /**
     * Sets the next reference of the node to the given value.
     *
     * @param next the new reference to the next node in the list
     */
    public void setNext(Node<T> next) {

        this.next = next;
    }

    /**
     * Sets the previous reference of the node to the given value.
     *
     * @param previous the new reference to the previous node in the list
     */
    public void setPrevious(Node<T> previous) {

        this.previous = previous;
    }

    /**
     * Returns the data stored in the node.
     *
     * @return the data stored in the node
     */
    public T getData() {

        return data;
    }

    /**
     * Returns the reference to the next node in the list.
     *
     * @return the reference to the next node in the list
     */
    public Node<T> getNext() {

        return next;
    }

    /**
     * Returns the reference to the previous node in the list.
     *
     * @return the reference to the previous node in the list
     */
    public Node<T> getPrevious() {

        return previous;
    }
}
