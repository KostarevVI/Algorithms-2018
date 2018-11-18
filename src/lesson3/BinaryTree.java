package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        if (root != null) {
            root = remover((T) o, root);
            size--;
            return true;
        } else {
            return false;
        }
    }
    //Трудоемкость и ресурсоемкость - O(h)
    //h - высота бинарного дерева

    private Node<T> minimum(Node<T> root) {                 //корень поддерева
        if (root.left == null) {
            return root;
        } else {
            return minimum(root.left);
        }
    }

    private Node<T> maximum(Node<T> root) {                //корень поддерева
        if (root.right == null) {
            return root;
        } else {
            return maximum(root.right);
        }
    }
    //Трудоёмкость обойх - O(h)
    //h - высота бинарного дерева
    //Ресурсоёмкость обоих - O(0)

    private Node<T> remover(T arg, Node<T> root) {            //удаляемый аргумент, корень поддерева
        if (root == null) {
            return root;
        }
        if (arg.compareTo(root.value) < 0) {
            root.left = remover(arg, root.left);
        } else {
            if (arg.compareTo(root.value) > 0) {
                root.right = remover(arg, root.right);
            } else {
                if (root.left != null && root.right != null) {
                    Node<T> newRoot = new Node<>(minimum(root.right).value);
                    newRoot.left = root.left;
                    newRoot.right = root.right;
                    root = newRoot;
                    root.right = remover(root.value, root.right);
                } else {
                    if (root.left != null) {
                        root = root.left;
                    } else {
                        root = root.right;
                    }
                }
            }
        }
        return root;
    }
    //Трудоёмкость - O(1)
    //Ресурсоёмкость - O(1)

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private Node<T> findNext() {
            Node<T> secRoot = null;
            Node<T> primRoot = root;

            if (current != null) {
                while (primRoot != null) {
                    if (primRoot.value.compareTo(current.value) > 0) {
                        secRoot = primRoot;
                        primRoot = primRoot.left;
                    } else {
                        primRoot = primRoot.right;
                    }
                }
                return secRoot;
            } else {
                secRoot = root;
                while (secRoot.left != null) {
                    secRoot = secRoot.left;
                }
                return secRoot;
            }
        }
        //Трудоемкость - O(h)
        //где h - высота бинарного дерева

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            root = remover(current.value, root);
            size--;
        }
    }
    //Трудоемкость и ресурсоемкость - O(h)
    //h - высота бинарного дерева

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        SortedSet<T> minSet = new TreeSet<>();
        minSet = minRootCount(minSet, root, toElement);
        return minSet;
    }
    //Трудоемкость - O(h)
    //h - высота бинарного дерева
    //Ресурсоёмкость - O(H)
    //количество элементов больше или равных заданного

    private SortedSet<T> minRootCount(SortedSet<T> minSet, Node<T> root, T toElement){
        if (root == null) {
            return (minSet);
        }
        else {
            if(root.value.compareTo(toElement)<0) {
                minSet.add(root.value);
            }
            minRootCount(minSet, root.left, toElement);
            minRootCount(minSet, root.right, toElement);
            return (minSet);
        }
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SortedSet<T> maxSet = new TreeSet<>();
        maxSet = maxRootCount(maxSet, root, fromElement);
        return maxSet;
    }
    //Трудоемкость - O(h)
    //h - высота бинарного дерева
    //Ресурсоёмкость - O(H)
    //количество элементов больше или равных заданного

    private SortedSet<T> maxRootCount(SortedSet<T> maxSet, Node<T> root, T fromElement){
        if (root == null) {
            return (maxSet);
        }
        else {
            if(root.value.compareTo(fromElement)>0 || root.value.compareTo(fromElement)==0) {
                maxSet.add(root.value);
            }
            maxRootCount(maxSet, root.left, fromElement);
            maxRootCount(maxSet, root.right, fromElement);
            return (maxSet);
        }
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
