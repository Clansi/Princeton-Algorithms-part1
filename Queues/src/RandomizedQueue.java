import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {


    private Item[] array;
    private int size;
    private int capacity;
    private int Initialsize = 8;
    private double factor = 0.25;

    // construct an empty randomized queue
    public RandomizedQueue(){
        array = (Item[]) new Object[Initialsize];
        size = 0 ;
        capacity = Initialsize;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }


    private void resize(int targetcapacity){
        Item[] newarray = (Item[]) new Object[targetcapacity];
        for (int i = 0; i < size; i++) {
            newarray[i] = array[i];
        }
        array = newarray;
        capacity = targetcapacity;
    }

    // add the item
    public void enqueue(Item item){
        if (item == null){
            throw new IllegalArgumentException("can not add a null item");
        }

        if (size == capacity){
            resize(size * 2);
        }
        array[size++] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        if (isEmpty()){
            throw new NoSuchElementException("the queue is empty");
        }

        int index = StdRandom.uniformInt(size);
        Item result = array[index];
        array[index] = array[size-1];
        array[size-1] = null;
        size --;

        if ( (double)size/capacity <= factor && !isEmpty()){
            resize(capacity / 2);
        }

        return result;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (isEmpty()){
            throw new NoSuchElementException("the queue is empty");
        }

        int index = StdRandom.uniformInt(size);
        return array[index];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] Iterator_array;
        private int Iterator_size;
        public RandomizedQueueIterator(){
            Iterator_array = (Item[]) new Object[size];
            Iterator_size = size;
            for (int i = 0; i < Iterator_size; i++) {
                Iterator_array[i] = array[i];
            }
            StdRandom.shuffle(Iterator_array);
        }

        @Override
        public boolean hasNext() {
            return Iterator_size != 0;
        }

        @Override
        public Item next() {
            if (Iterator_size == 0){
                throw new java.util.NoSuchElementException();
            }
            return Iterator_array[--Iterator_size];
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 18; i++) {
            rq.enqueue("A" + i);
        }
        System.out.println("first iterator");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("second iterator ");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (int i = 0; i < 18; i++) {
            System.out.print("deque ");
            System.out.print(rq.dequeue());
            System.out.println(". remain " + rq.size() + " elements. now capacity ");
        }

    }
}

