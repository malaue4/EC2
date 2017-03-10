package com.company;

/**
 * Created by ev on 05-04-2016.
 */
public class LinkedListSimple<T> {

    private ListItem firstItem = null;
    private ListItem lastItem = null;
    private int size = 0;

    public void add(T item)
    {
        //create listItem
        ListItem newListItem = new ListItem();
        newListItem.item = item;
        size++;

        if (firstItem == null)
        {
            //inser firstItem
            firstItem = newListItem;
            lastItem = newListItem;
        }
        else
        {
            //insertLastItem
            lastItem.next = newListItem;
            lastItem = newListItem;
        }
    }

    public int getSize() {
        return size;
    }

    public Iterator getIterator() {
        return new MyIterator();
    }


    private class ListItem {
        T item;
        ListItem next;
    }

    class MyIterator implements Iterator<T>{
        ListItem currentItem;

        MyIterator(){
            currentItem = firstItem;
        }

        @Override
        public boolean hasNext() {
        	//wrong: currentItem.next != null
			return currentItem != null;
        }

        @Override
        public T next() {
        	T item = currentItem.item;
			currentItem = currentItem.next;
            return item;
        }
    }

}


