package ru.otus;

import java.util.*;


public class DIYArrayList<T> implements List<T> {
    private int modCount;
    private int size;
    private Object[] data;

    public DIYArrayList() {
        this.size = 0;
        this.data = new Object[10];
        this.modCount = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        if (this.size == 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.data, this.size);
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        // TODO: Добавить контроль за размером массива(диапазон int, 4byte)
        if (this.size == this.data.length){
            Object[] dataNew = new Object[this.data.length*2];
            System.arraycopy(this.data,0, dataNew, 0,this.data.length);
            this.data = dataNew;
        }
        //System.out.println("i="+this.size+":"+this.data.length);
        this.size++;
        this.data[this.size-1] = (T) t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int i) {
        return (T) this.data[i];
    }

    @Override
    public T set(int i, T t) {
        this.data[i] = (T) t;
        return (T) this.data[i];
    }

    @Override
    public void add(int i, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {

        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        this.modCount = 0;
        return new Ltr();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return null;
    }

    @Override
    public List<T> subList(int i, int i1) {
        return null;
    }

    class Ltr implements ListIterator{

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object next() {
//            System.out.println(modCount);
            return DIYArrayList.this.data[modCount];
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(Object o) {
//            System.out.println(modCount++);
            DIYArrayList.this.data[modCount++] = o;
        }

        @Override
        public void add(Object o) {
            throw new UnsupportedOperationException();
        }
    }
}
