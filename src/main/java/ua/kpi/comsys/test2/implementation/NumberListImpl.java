/*
 * Copyright (c) 2014, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */

package ua.kpi.comsys.test2.implementation;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import ua.kpi.comsys.test2.NumberList;

/**
 * Custom implementation of INumberList interface.

 * @author Зозуля Марія, ІС-34, варіант №4
 *
 */

public class NumberListImpl implements NumberList {

    private static class Node {
        Byte data;
        Node next;

        Node(Byte data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private int currentBase = 16;

    public NumberListImpl() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public NumberListImpl(File file) {
        this();
        try {
            if (file != null && file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                initFromDecimalString(content.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NumberListImpl(String value) {
        this();
        initFromDecimalString(value);
    }

    private void initFromDecimalString(String value) {
        if (value == null || value.isEmpty()) return;
        try {
            value = value.trim();
            for (char c : value.toCharArray()) {
                if (!Character.isDigit(c) && c != '-') return;
            }

            BigInteger bigInt = new BigInteger(value);
            if (bigInt.compareTo(BigInteger.ZERO) < 0) return;

            // Конвертуємо в Hex (базова система)
            String hexString = bigInt.toString(16).toUpperCase();

            for (int i = 0; i < hexString.length(); i++) {
                char hexChar = hexString.charAt(i);
                int digit = Character.digit(hexChar, 16);
                add((byte) digit);
            }
        } catch (NumberFormatException e) {
        }
    }

    public void saveList(File file) {
        try {
            String decimalStr = toDecimalString();
            Files.write(file.toPath(), decimalStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getRecordBookNumber() {
        return 4;
    }

    /**
     * Change Scale to BINARY (Base 2).
     */
    public NumberListImpl changeScale() {
        BigInteger val = getBigIntegerValues();

        NumberListImpl newList = new NumberListImpl();
        newList.clear();

        newList.currentBase = 2;

        String binaryString = val.toString(2);
        for (int i = 0; i < binaryString.length(); i++) {
            int digit = Character.digit(binaryString.charAt(i), 2);
            newList.add((byte) digit);
        }

        return newList;
    }

    public NumberListImpl additionalOperation(NumberList arg) {
        BigInteger val1 = this.getBigIntegerValues();

        BigInteger val2;
        if (arg instanceof NumberListImpl) {
            val2 = ((NumberListImpl) arg).getBigIntegerValues();
        } else {
            val2 = BigInteger.ZERO;
        }

        if (val2.equals(BigInteger.ZERO)) {
            return new NumberListImpl("0");
        }

        BigInteger remainder = val1.remainder(val2);

        return new NumberListImpl(remainder.toString());
    }

    public String toDecimalString() {
        if (isEmpty()) return "";
        return getBigIntegerValues().toString();
    }

    // Тепер враховує currentBase
    private BigInteger getBigIntegerValues() {
        if (size == 0) return BigInteger.ZERO;

        BigInteger res = BigInteger.ZERO;
        BigInteger base = BigInteger.valueOf(currentBase); // Використовуємо поле currentBase

        Node curr = head;
        for (int i = 0; i < size; i++) {
            res = res.multiply(base).add(BigInteger.valueOf(curr.data));
            curr = curr.next;
        }
        return res;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        Node curr = head;

        for (int i = 0; i < size; i++) {
            int val = curr.data;
            if (val >= 0 && val <= 9) {
                sb.append(val);
            } else if (val >= 10 && val <= 15) {
                sb.append((char)('A' + (val - 10)));
            }
            curr = curr.next;
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof List)) return false;
        List<?> other = (List<?>) o;
        if (size != other.size()) return false;

        Iterator<Byte> it1 = this.iterator();
        Iterator<?> it2 = other.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            Object o1 = it1.next();
            Object o2 = it2.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2)))
                return false;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) return false;
        Node curr = head;
        for (int i = 0; i < size; i++) {
            if (curr.data.equals(o)) return true;
            curr = curr.next;
        }
        return false;
    }

    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {
            private Node curr = head;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public Byte next() {
                if (!hasNext()) throw new NoSuchElementException();
                Byte val = curr.data;
                curr = curr.next;
                count++;
                return val;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node curr = head;
        for (int i = 0; i < size; i++) {
            arr[i] = curr.data;
            curr = curr.next;
        }
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Byte e) {
        if (e == null) return false;
        Node newNode = new Node(e);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            newNode.next = head; // Замикання в коло
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head; // Замикання в коло
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (isEmpty()) return false;

        Node curr = head;
        Node prev = tail;

        for (int i = 0; i < size; i++) {
            if (curr.data.equals(o)) {
                unlink(prev, curr);
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    private void unlink(Node prev, Node target) {
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            prev.next = target.next;
            if (target == head) {
                head = target.next;
                tail.next = head;
            }
            if (target == tail) {
                tail = prev;
            }
        }
        size--;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Byte> c) {
        boolean modified = false;
        for (Byte b : c) {
            if (add(b)) modified = true;
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Byte> c) {
        boolean modified = false;
        int currentIdx = index;
        for (Byte b : c) {
            add(currentIdx++, b);
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object item : c) {
            while (contains(item)) {
                remove(item);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        if (isEmpty()) return false;

        Node curr = head;
        Node prev = tail;
        int originalSize = size;
        int checked = 0;

        while (checked < originalSize && !isEmpty()) {
            Node nextNode = curr.next;
            if (!c.contains(curr.data)) {
                unlink(prev, curr);
                modified = true;
                curr = nextNode;
            } else {
                prev = curr;
                curr = nextNode;
            }
            checked++;
        }
        return modified;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public Byte get(int index) {
        checkIndex(index);
        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.data;
    }

    @Override
    public Byte set(int index, Byte element) {
        checkIndex(index);
        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        Byte old = curr.data;
        curr.data = element;
        return old;
    }

    @Override
    public void add(int index, Byte element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();

        if (index == size) {
            add(element);
            return;
        }

        Node newNode = new Node(element);

        if (index == 0) {
            if (isEmpty()) {
                add(element);
            } else {
                newNode.next = head;
                head = newNode;
                tail.next = head;
                size++;
            }
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            newNode.next = prev.next;
            prev.next = newNode;
            size++;
        }
    }

    @Override
    public Byte remove(int index) {
        checkIndex(index);

        Node curr = head;
        Node prev = tail;

        for (int i = 0; i < index; i++) {
            prev = curr;
            curr = curr.next;
        }

        Byte val = curr.data;
        unlink(prev, curr);
        return val;
    }

    @Override
    public int indexOf(Object o) {
        if (isEmpty()) return -1;
        Node curr = head;
        for (int i = 0; i < size; i++) {
            if (curr.data.equals(o)) return i;
            curr = curr.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (isEmpty()) return -1;
        int lastIdx = -1;
        Node curr = head;
        for (int i = 0; i < size; i++) {
            if (curr.data.equals(o)) lastIdx = i;
            curr = curr.next;
        }
        return lastIdx;
    }

    @Override
    public ListIterator<Byte> listIterator() {
        return new MyListIterator(0);
    }

    @Override
    public ListIterator<Byte> listIterator(int index) {
        return new MyListIterator(index);
    }

    @Override
    public List<Byte> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class MyListIterator implements ListIterator<Byte> {
        int cursor;

        MyListIterator(int index) {
            this.cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public Byte next() {
            if (!hasNext()) throw new NoSuchElementException();
            Byte val = get(cursor);
            cursor++;
            return val;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public Byte previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            cursor--;
            return get(cursor);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            NumberListImpl.this.remove(cursor - 1);
            cursor--;
        }

        @Override
        public void set(Byte e) {
            NumberListImpl.this.set(cursor - 1, e);
        }

        @Override
        public void add(Byte e) {
            NumberListImpl.this.add(cursor, e);
            cursor++;
        }
    }

    @Override
    public boolean swap(int index1, int index2) {
        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) return false;
        if (index1 == index2) return true;

        Node n1 = head;
        for(int i=0; i<index1; i++) n1 = n1.next;

        Node n2 = head;
        for(int i=0; i<index2; i++) n2 = n2.next;

        Byte tmp = n1.data;
        n1.data = n2.data;
        n2.data = tmp;

        return true;
    }

    @Override
    public void sortAscending() {
        if (size <= 1) return;
        for (int i = 0; i < size; i++) {
            Node curr = head;
            for (int j = 0; j < size - 1 - i; j++) {
                if (curr.data > curr.next.data) {
                    Byte tmp = curr.data;
                    curr.data = curr.next.data;
                    curr.next.data = tmp;
                }
                curr = curr.next;
            }
        }
    }

    @Override
    public void sortDescending() {
        if (size <= 1) return;
        for (int i = 0; i < size; i++) {
            Node curr = head;
            for (int j = 0; j < size - 1 - i; j++) {
                if (curr.data < curr.next.data) {
                    Byte tmp = curr.data;
                    curr.data = curr.next.data;
                    curr.next.data = tmp;
                }
                curr = curr.next;
            }
        }
    }

    @Override
    public void shiftLeft() {
        if (size > 1) {
            head = head.next;
            tail = tail.next;
        }
    }

    @Override
    public void shiftRight() {
        if (size > 1) {
            Node newTail = head;
            for(int i=0; i < size - 2; i++) {
                newTail = newTail.next;
            }
            head = tail;
            tail = newTail;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
    }
}
