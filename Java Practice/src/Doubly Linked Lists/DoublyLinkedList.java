//import java.util.Iterator;
//import java.util.ListIterator;
//import java.util.NoSuchElementException;
//import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 *  This class contains the methods of Doubly Linked List.
 *
 *  @author  
 *  @version 09/10/18 11:13:22
 */


/**
 * Class DoublyLinkedList: implements a *generic* Doubly Linked List.
 * @param <T> This is a type parameter. T is used as a class name in the
 * definition of this class.
 *
 * When creating a new DoublyLinkedList, T should be instantiated with an
 * actual class name that extends the class Comparable.
 * Such classes include String and Integer.
 *
 * For example to create a new DoublyLinkedList class containing String data: 
 *    DoublyLinkedList<String> myStringList = new DoublyLinkedList<String>();
 *
 * The class offers a toString() method which returns a comma-separated sting of
 * all elements in the data structure.
 * 
 * This is a bare minimum class you would need to completely implement.
 * You can add additional methods to support your code. Each method will need
 * to be tested by your jUnit tests -- for simplicity in jUnit testing
 * introduce only public methods.
 */
class DoublyLinkedList<T extends Comparable<T>>
{

    /**
     * private class DLLNode: implements a *generic* Doubly Linked List node.
     */
    private class DLLNode
    {
        public final T data; // this field should never be updated. It gets its
                             // value once from the constructor DLLNode.
        public DLLNode next;
        public DLLNode prev;
    
        /**
         * Constructor
         * @param theData : data of type T, to be stored in the node
         * @param prevNode : the previous Node in the Doubly Linked List
         * @param nextNode : the next Node in the Doubly Linked List
         * @return DLLNode
         */
        public DLLNode(T theData, DLLNode prevNode, DLLNode nextNode) 
        {
          data = theData;
          prev = prevNode;
          next = nextNode;
        }
    }

    // Fields head and tail point to the first and last nodes of the list.
    private DLLNode head, tail;

    /**
     * Constructor of an empty DLL
     * @return DoublyLinkedList
     */
    public DoublyLinkedList() 
    {
      head = null;
      tail = null;
    }

    /**
     * Tests if the doubly linked list is empty
     * @return true if list is empty, and false otherwise
     *
     * Worst-case asymptotic running time cost: Theta(1)
     *
     * Justification:
     *  Asymptotic runtime is constant in this small function which return simply a boolean based on whether this.head is null or not
     */
    public boolean isEmpty()
    {
        if(this.head==null) {
            return true;
        }
        return false;
    }

    /**
     * Inserts an element in the doubly linked list
     * @param pos : The integer location at which the new data should be
     *      inserted in the list. We assume that the first position in the list
     *      is 0 (zero). If pos is less than 0 then add to the head of the list.
     *      If pos is greater or equal to the size of the list then add the
     *      element at the end of the list.
     * @param data : The new data of class T that needs to be added to the list
     * @return none
     *
     * Worst-case asymptotic running time cost: Theta(N)
     *
     * Justification:
     *  Runtime is constant until it reaches the else containing the for loop which is linear meaning the highest order term in N. 
     *  Theta(1) + Theta(N) = Theta(N)
     */
    public void insertBefore( int pos, T data )
    {
        DLLNode tmp;
        DLLNode node = null;
        DLLNode newNode = new DLLNode(data, null,null);
        if(isEmpty()){
//            System.out.print("Empty");
            
            tail = newNode;
            head = newNode;
            return;
        }
        if(pos<=0) {
//            System.out.print("Head");
            node = head;
            node.prev = newNode;
            newNode.next = node;
            head = newNode;
        }
        else{
            if(pos == 1) {
                if(head.next!=null) {
                    node = head.next;
                    head.next = newNode;
                    newNode.prev = head;
                    node.prev = newNode;
                    newNode.next = node;
//                    System.out.print("1st pos next not null");
                }
                else{
                    head.next = newNode;
                    newNode.prev = head;
                    tail = newNode;
//                    System.out.print("1stpos");
                }
            }
            else{
                if(head.next!=null) {
                    node = head.next;
                    for(int x = 1; x<=pos; x++){
                        if(x==pos-1){
                            if(node.next!=null){
//                                System.out.print("pos-1");
                                newNode.prev = node;
                            }
                            else{
                                newNode.prev = node;
                                node.next = newNode;
                                tail = newNode;
//                                System.out.println("pos-1 return");
                                return;
                            }
                        }
                        if(x==pos){
                            if(node.next!=null){
//                                System.out.print("pos return");
                                newNode.next = node;
                                node.prev = newNode;
                                newNode.prev.next = newNode;
                                return;
                            }
                            else{
//                                System.out.print("pos2aasdasd return");
                                newNode.prev.next = newNode;
                                newNode.next = tail;
                                tail.prev = newNode;
                                return;                    
                            }
                        }
                        if(node.next!=null) {
                            node = node.next;
                        }
                        else {
                            tmp = node;
                            newNode.prev = tmp;
                            tmp.next = newNode;
                            tail = newNode;
//                            System.out.println("over return");
                            return;
                        }
                    }
                }
                else {
                    tail = newNode;
                    tail.prev = head;
                    head.next = tail;
                }
            }
        }
//        System.out.println("endloop");
        return;
    }

    /**
     * Returns the data stored at a particular position
     * @param pos : the position
     * @return the data at pos, if pos is within the bounds of the list, and null otherwise.
     *
     * Worst-case asymptotic running time cost: Theta(N)
     *
     * Justification:
     *  Asymptotic runtime is constant until it reaches the for loop which is linear meaning it is run N times. 
     *    Theta(1) + Theta(N) = Theta(N)
     */
    public T get(int pos) 
    {
        if(pos<0||isEmpty()) {
            return null;
        }
        if(pos==0){
            return head.data;
        }
        if(head.next!=null){
            DLLNode tmp = head.next;
            int x;
            for(x = 1; tmp.next!=null; x++){
                    if(x==pos){
                        return tmp.data;
                    }
                    tmp = tmp.next;
            }
            if(x==pos) {
                return tail.data;
            }
            return null;
        }
        return null;
    }

    /**
     * Deletes the element of the list at position pos.
     * First element in the list has position 0. If pos points outside the
     * elements of the list then no modification happens to the list.
     * @param pos : the position to delete in the list.
     * @return true : on successful deletion, false : list has not been modified. 
     *
     * Worst-case asymptotic running time cost: Theta(N)
     *
     * Justification:
     *  Asymptotic runtime here is also constant until it reaches the for loop inside of the else statement. Asymptotic runtime then becomes Theta(N) as it is a linear for loop.
     *  Theta(1) + Theta(N) = Theta(N)
     */
    public boolean deleteAt(int pos) 
    {
        if(isEmpty()) {
            return false;
        }
        if(pos==0){
            if(head.next!=null){
                head = head.next;
                head.prev = null;
                if(head.next!=null) {
                    return true;
                }
                tail = head;
                return true;
            }
            else{
                head = null;
                tail = null;
                return true;
            }
        }
        else if(pos<0) {
            return false;
        }
        else
        {
            if(head.next!=null) {
                DLLNode tmp = head.next;
                int x;
                for(x = 1; tmp.next!=null; x++){
                        if(x==pos){
                            tmp.prev.next = tmp.next;
                            tmp.next.prev = tmp.prev;
                            return true;
                        }
                        tmp = tmp.next;
                }
                if(x==pos){
                    tail = tail.prev;
                    tail.next = null;
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * Reverses the list.
     * If the list contains "A", "B", "C", "D" before the method is called
     * Then it should contain "D", "C", "B", "A" after it returns.
     *
     * Worst-case asymptotic running time cost: Theta(N)
     *
     * Justification:
     *  Asymptotic runtime is constant up until the while loop which is run N times depending on the size of the list.
     *  Theta(1) + Theta(N) = Theta(N)
     */
    public void reverse()
    {
        if(isEmpty()!=true) {
            if(head!=tail) {
                DLLNode tmp = head.next;
                DLLNode node;
                head.prev = head.next;
                head.next = null;
                while(tmp!=tail) {
                    node = tmp;
                    tmp = tmp.next;
                    node.next = node.prev;
                    node.prev = tmp;
                }
                tail.next = tail.prev;
                tail.prev = null;
                tmp = head;
                head = tail;
                tail = tmp;
            }
        }
    }

    /**
     * Removes all duplicate elements from the list.
     * The method should remove the _least_number_ of elements to make all elements uniqueue.
     * If the list contains "A", "B", "C", "B", "D", "A" before the method is called
     * Then it should contain "A", "B", "C", "D" after it returns.
     * The relative order of elements in the resulting list should be the same as the starting list.
     *
     * Worst-case asymptotic running time cost: Theta(N)
     *
     * Justification:
     * Asymptotic runtime is constant until it reaches the first while loop, then it become N. Then it enters another while loop where, in the event of a duplicate, 
     * the deleteAt() function is called which has asymptotic runtime Theta(N).
     * Theta(1) + Theta(N) + Theta(N) = Theta(N)
     */
     public void makeUnique()
    {
        if(isEmpty()) {
            return;
        }
         int tmp1pos = 0;
         int tmp2pos = 1;
         if(head.next!=null) {
             DLLNode tmp1 = head;
             DLLNode tmp2 = head.next;
        	 System.out.println("tmp1: "+tmp1.data);
             while(tmp1.next!=null){
                 while(tmp2.next!=null){
                     if(tmp1.data != tmp2.data) {
                    	 tmp2pos++;
                         tmp2 = tmp2.next;     
                     }
                     else
                     {
                    	 tmp2 = tmp2.next;
                         deleteAt(tmp2pos);
                     }
                 }
                 if(tmp1.data == tmp2.data) {
                	 tmp2 = tmp2.next;
                     deleteAt(tmp2pos);
                 }
                 if(tmp1.next!=null) {
                     tmp1 = tmp1.next;
                     tmp1pos++;
                     tmp2 = tmp1.next;
                     tmp2pos = tmp1pos+1;
                 }

             }
             return;
         }
         else {
             return;
         }
    }


    /*----------------------- STACK API 
     * If only the push and pop methods are called the data structure should behave like a stack.
     */

    /**
     * This method adds an element to the data structure.
     * How exactly this will be represented in the Doubly Linked List is up to the programmer.
     * @param item : the item to push on the stack
     *
     * Worst-case asymptotic running time cost: Theta(1)
     *
     * Justification: 
     * Asymptotic runtime is constant throughout the function. The isEmpty() function is called at the beginning which has an asymptotic runtime Theta(N). 
     *  Theta(1) + Theta(1) = Theta(1)
     */
    public void push(T item) 
    {
        DLLNode newNode = new DLLNode(item, null, null);

        if(isEmpty()) {
            head = newNode;
            tail = newNode;
            return;
        }
        if(head == tail) {
            tail.prev = newNode;
            newNode.next = tail;
            head = newNode;
        }
        else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
    }

    /**
     * This method returns and removes the element that was most recently added by the push method.
     * @return the last item inserted with a push; or null when the list is empty.
     *
     * Worst-case asymptotic running time cost: Theta(1)
     *
     * Justification: 
     * Asymptotic runtime is constant throughout the function. The isEmpty() function is called at the beginning which has an asymptotic runtime Theta(N). 
     *  Theta(1) + Theta(1) = Theta(1)
     */
    public T pop() 
    {
        DLLNode tmp;
        if(isEmpty()) {
            return null;
        }
        else if(head == tail) {
            tmp = head;
            head = null;
            tail = null;
            return tmp.data;
        }
        else {
            DLLNode oldHead = head;
            head.next = head;
            head.prev = null;
            return oldHead.data;
        }
    }

    /*----------------------- QUEUE API
     * If only the enqueue and dequeue methods are called the data structure should behave like a FIFO queue.
     */
 
    /**
     * This method adds an element to the data structure.
     * How exactly this will be represented in the Doubly Linked List is up to the programmer.
     * @param item : the item to be enqueued to the stack
     *
     * Worst-case asymptotic running time cost: Theta(1)
     *
     * Justification: 
     * Asymptotic runtime is constant throughout the function. The isEmpty() function is called at the beginning which has an asymptotic runtime Theta(N). 
     *  Theta(1) + Theta(1) = Theta(1)
     */
    public void enqueue(T item) 
    {
        DLLNode newNode = new DLLNode(item, null, null);

        if(isEmpty()) {
            head = newNode;
            tail = newNode;
            return;
        }
        if(head == tail) {
            tail.prev = newNode;
            newNode.next = tail;
            head = newNode;
        }
        else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
    }

     /**
     * This method returns and removes the element that was least recently added by the enqueue method.
     * @return the earliest item inserted with an equeue; or null when the list is empty.
     *
     * Worst-case asymptotic running time cost: Theta(1)
     *
     * Justification: 
     * Asymptotic runtime is constant throughout the function. The isEmpty() function is called at the beginning which has an asymptotic runtime Theta(N). 
     *  Theta(1) + Theta(1) = Theta(1)
     */
    public T dequeue() 
    {
        DLLNode tmp;
        if(isEmpty()) {
            return null;
        }
        else if(head == tail) {
            tmp = head;
            head = null;
            tail = null;
            return tmp.data;
        }
        else{
            tmp = tail;
            tail = tail.prev;
            tail.next = null;
            return tmp.data;
        }
    }

    /**
     * @return a string with the elements of the list as a comma-separated
     * list, from beginning to end
     *
     * Worst-case asymptotic running time cost:   Theta(n)
     *
     * Justification:
     *  We know from the Java documentation that StringBuilder's append() method runs in Theta(1) asymptotic time.
     *  We assume all other method calls here (e.g., the iterator methods above, and the toString method) will execute in Theta(1) time.
     *  Thus, every one iteration of the for-loop will have cost Theta(1).
     *  Suppose the doubly-linked list has 'n' elements.
     *  The for-loop will always iterate over all n elements of the list, and therefore the total cost of this method will be n*Theta(1) = Theta(n).
     */
    public String toString() 
    {
      StringBuilder s = new StringBuilder();
      boolean isFirst = true; 

      // iterate over the list, starting from the head
      for (DLLNode iter = head; iter != null; iter = iter.next)
      {
        if (!isFirst)
        {
          s.append(",");
        } else {
          isFirst = false;
        }
        s.append(iter.data.toString());
      }

      return s.toString();
    }
}




