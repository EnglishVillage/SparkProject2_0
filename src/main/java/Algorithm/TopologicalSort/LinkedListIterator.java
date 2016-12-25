package Algorithm.TopologicalSort;

import java.util.Iterator;

/**
 * Created by Administrator on 2016/10/17.
 */
public class LinkedListIterator<Item> implements Iterator<Item>
{
    private Node<Item> head;
    private Node<Item> current;

    public LinkedListIterator(Node<Item> node)
    {
        this.current = node;
        this.head = node;
    }

    @Override
    public boolean hasNext()
    {
        return (null != current);
    }

    @Override
    public Item next()
    {
        Item item = current.item;
        current = current.next;
        return item;
    }

    @Override
    public void remove()
    {

    }

    public void reset()
    {
        this.current = this.head;
    }

}
