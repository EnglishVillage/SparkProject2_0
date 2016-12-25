package Algorithm.TopologicalSort;

import java.util.Iterator;

/**
 * Created by Administrator on 2016/10/17.
 */
public class LinkedBag<Item> implements Iterable<Item>
{
    private Node<Item> first;
    private int N;

    public void add(Item item)
    {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        N++;
    }

    public int size()
    {
        return N;
    }

    public boolean isEmpty()
    {
        return (0 == N);
    }

    @Override
    public Iterator<Item> iterator()
    {
        return new LinkedListIterator<Item>(first);
    }
}
