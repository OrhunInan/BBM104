public class CircularDoublyLinkedList<T> {

    private Node<T> firstNode;

    /**
     * Constructs an empty circular doubly-linked list.
     */
    public CircularDoublyLinkedList(){

        this.firstNode = null;
    }

    /**
     * Adds a new node with the given data to the start of the list.
     *
     * @param data the data to be stored in the new node
     */
    public void addToStart(T data){

        if (this.firstNode == null){

            this.firstNode = new Node<>(data);
        }
        else {

            Node<T> oldFirstNode = this.firstNode;
            Node<T> endNode = oldFirstNode.getPrevious();
            Node<T> newNode = new Node<>(data, oldFirstNode, endNode);

            endNode.setNext(newNode);
            oldFirstNode.setPrevious(newNode);
            this.firstNode = newNode;
        }
    }

    /**
     * Adds a new node with the given data to the end of the list.
     *
     * @param data the data to be stored in the new node
     */
    public void addToEnd(T data){

        if (this.firstNode == null){

            this.firstNode = new Node<>(data);
        }
        else {

            Node<T> oldEndNode = this.firstNode.getPrevious();
            Node<T> newNode = new Node<>(data, this.firstNode, oldEndNode);

            oldEndNode.setNext(newNode);
            this.firstNode.setPrevious(newNode);
        }
    }

    /**
     * Removes and returns the data from the first node in the list.
     *
     * @return the data from the first node, or null if the list is empty
     */
    public T popFromStart(){

        Node<T> oldFirstnode = this.firstNode;
        if (this.firstNode == null) return null;
        else {
            Node<T> newFirstNode = this.firstNode.getNext();
            Node<T> endNode = this.firstNode.getPrevious();

            newFirstNode.setPrevious(endNode);
            endNode.setNext(newFirstNode);
            this.firstNode = newFirstNode;
        }

        return oldFirstnode.getData();
    }

    /**
     * Removes and returns the data from the last node in the list.
     *
     * @return the data from the last node, or null if the list is empty
     */
    public T popFromEnd(){

        Node<T> oldEndNode = this.firstNode.getPrevious();

        if (this.length() == 1) this.firstNode = null;
        else {


            Node<T> newEndNode = oldEndNode.getPrevious();

            if(newEndNode == this.firstNode){

                this.firstNode.setNext(this.firstNode);
                this.firstNode.setPrevious(this.firstNode);
            }
            else{
                this.firstNode.setPrevious(newEndNode);
                newEndNode.setNext(firstNode);
            }
        }

        return oldEndNode.getData();
    }

    /**
     * Returns the number of nodes in the list.
     *
     * @return the number of nodes in the list
     */
    public int length(){

        if (this.firstNode == null) return 0;
        if (this.firstNode.getNext() == this.firstNode) return 1;

        Node<T> currrentNode = this.firstNode;
        int length = 1;

        while (currrentNode.getNext() != this.firstNode){

            length++;
            currrentNode = currrentNode.getNext();
        }

        return length;
    }
}
