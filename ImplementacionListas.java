import java.util.Random;

public class ImplementacionListas {

    public static void main(String[] args) {    
        long[] tamanos = {10, 100, 1000, 10000, 100000, 1000000, 10000000}; 
        Random rand = new Random();

        System.out.println("==================================================================================");
        System.out.println("      ANALISIS DE COMPLEJIDAD: DoubleListWithTail");
        System.out.println("==================================================================================");
        System.out.printf("%-12s | %-15s | %-15s | %-15s | %-15s\n", "Tamaño (N)", "PushBack (ns)", "Find (ns)", "PopBack (ns)", "PushFront (ns)");
        System.out.println("----------------------------------------------------------------------------------");

        for (long n : tamanos) {
            DoubleListWithTail<Integer> lista = new DoubleListWithTail<>();

            long inicioPush = System.nanoTime();
            for (int i = 0; i < n; i++) {
                lista.PushBack(rand.nextInt(1000));
            }
            long finPush = System.nanoTime() - inicioPush;

            lista.PushBack(-999); 
            long inicioFind = System.nanoTime();
            lista.Find(-999);
            long finFind = System.nanoTime() - inicioFind;

            long inicioPop = System.nanoTime();
            lista.PopBack();
            long finPop = System.nanoTime() - inicioPop;

            long inicioFront=System.nanoTime();
            lista.PushFront(rand.nextInt(1000));
            long finFront=System.nanoTime()-inicioFront;

            System.out.printf("%-12d | %-15d | %-15d | %-15d | %-15d\n", n, finPush, finFind, finPop, finFront);

            lista = null;
            System.gc();
        }
        System.out.println("==================================================================================");
    }

    static class Nodo<T> {
        T valor;
        Nodo<T> next, prev;
        public Nodo(T valor) { this.valor = valor; }
    }

    interface List<T> {
        void PushFront(T valor);
        void PushBack(T valor);
        void PopFront();
        void PopBack();
        Nodo<T> Find(T valor);
        boolean isEmpty();
    }

    static class SimpleLinkedList<T> implements List<T> {
        Nodo<T> head;
        public boolean isEmpty() { return head == null; }
        public void PushFront(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            nuevo.next = head;
            head = nuevo;
        }
        public void PushBack(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (isEmpty()) head = nuevo;
            else {
                Nodo<T> temp = head;
                while (temp.next != null) temp = temp.next;
                temp.next = nuevo;
            }
        }
        public void PopFront() { if (!isEmpty()) head = head.next; }
        public void PopBack() {
            if (isEmpty()) return;
            if (head.next == null) head = null;
            else {
                Nodo<T> temp = head;
                while (temp.next.next != null) temp = temp.next;
                temp.next = null;
            }
        }
        public Nodo<T> Find(T valor) {
            Nodo<T> temp = head;
            while (temp != null) {
                if (temp.valor.equals(valor)) return temp;
                temp = temp.next;
            }
            return null;
        }
    }

    static class SimpleListWithTail<T> implements List<T> {
        Nodo<T> head, tail;
        public boolean isEmpty() { return head == null; }
        public void PushFront(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (isEmpty()) head = tail = nuevo;
            else { nuevo.next = head; head = nuevo; }
        }
        public void PushBack(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (isEmpty()) head = tail = nuevo;
            else { tail.next = nuevo; tail = nuevo; }
        }
        public void PopFront() {
            if (isEmpty()) return;
            head = head.next;
            if (head == null) tail = null;
        }
        public void PopBack() {
            if (isEmpty()) return;
            if (head == tail) head = tail = null;
            else {
                Nodo<T> temp = head;
                while (temp.next != tail) temp = temp.next;
                temp.next = null;
                tail = temp;
            }
        }
        public Nodo<T> Find(T valor) {
            Nodo<T> temp = head;
            while (temp != null) {
                if (temp.valor.equals(valor)) return temp;
                temp = temp.next;
            }
            return null;
        }
    }

    static class DoubleLinkedList<T> implements List<T> {
        Nodo<T> head;
        public boolean isEmpty() { return head == null; }
        public void PushFront(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (!isEmpty()) { nuevo.next = head; head.prev = nuevo; }
            head = nuevo;
        }
        public void PushBack(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (isEmpty()) head = nuevo;
            else {
                Nodo<T> temp = head;
                while (temp.next != null) temp = temp.next;
                temp.next = nuevo; nuevo.prev = temp;
            }
        }
        public void PopFront() { if (!isEmpty()) { head = head.next; if (head != null) head.prev = null; } }
        public void PopBack() {
            if (isEmpty()) return;
            if (head.next == null) head = null;
            else {
                Nodo<T> temp = head;
                while (temp.next != null) temp = temp.next;
                temp.prev.next = null;
            }
        }
        public Nodo<T> Find(T valor) {
            Nodo<T> temp = head;
            while (temp != null) {
                if (temp.valor.equals(valor)) return temp;
                temp = temp.next;
            }
            return null;
        }
    }

    static class DoubleListWithTail<T> implements List<T> {
        Nodo<T> head, tail;
        public boolean isEmpty() { return head == null; }
        public void PushFront(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (isEmpty()) head = tail = nuevo;
            else { nuevo.next = head; head.prev = nuevo; head = nuevo; }
        }
        public void PushBack(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (isEmpty()) head = tail = nuevo;
            else { tail.next = nuevo; nuevo.prev = tail; tail = nuevo; }
        }
        public void PopFront() {
            if (isEmpty()) return;
            head = head.next;
            if (head != null) head.prev = null; else tail = null;
        }
        public void PopBack() {
            if (isEmpty()) return;
            if (head == tail) head = tail = null;
            else { tail = tail.prev; tail.next = null; }
        }
        public Nodo<T> Find(T valor) {
            Nodo<T> temp = head;
            while (temp != null) {
                if (temp.valor.equals(valor)) return temp;
                temp = temp.next;
            }
            return null;
        }
    }
}