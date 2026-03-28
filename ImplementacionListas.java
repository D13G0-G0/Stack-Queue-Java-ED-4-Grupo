import java.util.Random;

public class ImplementacionListas {

    static Random rand = new Random();

    public static void main(String[] args) {
        final int start = 10;
        final int end   = 100000000;

        for (int size = start; size <= end; size *= 10) {
            final int currentSize = size;

            System.out.println("\n[PUSHBACK] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                DoubleListWithTail<Integer> lista = new DoubleListWithTail<>();
                for (int j = 0; j < currentSize; j++) lista.PushBack(rand.nextInt(1000));
                execSingle(currentSize, "PushBack", () -> lista.PushBack(rand.nextInt(1000)), true);
            }

            System.out.println("\n[PUSHFRONT] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                DoubleListWithTail<Integer> lista = new DoubleListWithTail<>();
                for (int j = 0; j < currentSize; j++) lista.PushBack(rand.nextInt(1000));
                execSingle(currentSize, "PushFront", () -> lista.PushFront(rand.nextInt(1000)), true);
            }

            System.out.println("\n[POPBACK] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                DoubleListWithTail<Integer> lista = new DoubleListWithTail<>();
                for (int j = 0; j < currentSize; j++) lista.PushBack(rand.nextInt(1000));
                execSingle(currentSize, "PopBack", () -> lista.PopBack(), true);
            }

            System.out.println("\n[ADDAFTER] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                DoubleListWithTail<Integer> lista = new DoubleListWithTail<>();
                for (int j = 0; j < currentSize; j++) lista.PushBack(rand.nextInt(1000));
                lista.PushBack(-999);
                Nodo<Integer> ref = lista.Find(-999);
                execSingle(currentSize, "addAfter", () -> lista.addAfter(ref, 555), true);
            }

            System.out.println("\n[ADDBEFORE] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                DoubleListWithTail<Integer> lista = new DoubleListWithTail<>();
                for (int j = 0; j < currentSize; j++) lista.PushBack(rand.nextInt(1000));
                lista.PushBack(-999);
                Nodo<Integer> ref = lista.Find(-999);
                execSingle(currentSize, "addBefore", () -> lista.addBefore(ref, 444), true);
            }

            System.out.println("\n[FIND] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                DoubleListWithTail<Integer> lista = new DoubleListWithTail<>();
                for (int j = 0; j < currentSize; j++) lista.PushBack(rand.nextInt(1000));
                lista.PushBack(-999);
                execSingle(currentSize, "Find", () -> lista.Find(-999), false);
            }

            System.out.println("\n[ERASE] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                DoubleListWithTail<Integer> lista = new DoubleListWithTail<>();
                for (int j = 0; j < currentSize; j++) lista.PushBack(rand.nextInt(1000));
                lista.PushBack(-999);
                execSingle(currentSize, "erase", () -> lista.erase(-999), false);
            }

            System.gc();
        }
    }

    public static void execSingle(int size, String method, Runnable operation, boolean isConstant) {
        if (isConstant) {
            int reps = 1000;
            long start = System.nanoTime();
            for (int i = 0; i < reps; i++) operation.run();
            long finish = System.nanoTime();
            double nanos = (double)(finish - start) / reps;
            System.out.printf("  [%s] N=%d → %.4f nanosegundos (promedio de %d reps)%n",
                    method, size, nanos, reps);
        } else {
            long start = System.nanoTime();
            operation.run();
            long finish = System.nanoTime();
            double nanos = (double)(finish - start);
            System.out.printf("  [%s] N=%d → %.4f nanosegundos%n",
                    method, size, nanos);
        }
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
        void addAfter(Nodo<T> node, T valor);
        void addBefore(Nodo<T> node, T valor);
        void erase(T valor);
        boolean isEmpty();
    }

    static class DoubleListWithTail<T> implements List<T> {
        Nodo<T> head, tail;

        public boolean isEmpty() { return head == null; }

        public void PushFront(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (isEmpty()) { head = tail = nuevo; }
            else { nuevo.next = head; head.prev = nuevo; head = nuevo; }
        }

        public void PushBack(T valor) {
            Nodo<T> nuevo = new Nodo<>(valor);
            if (isEmpty()) { head = tail = nuevo; }
            else { tail.next = nuevo; nuevo.prev = tail; tail = nuevo; }
        }

        public void PopFront() {
            if (isEmpty()) return;
            head = head.next;
            if (head != null) head.prev = null; else tail = null;
        }

        public void PopBack() {
            if (isEmpty()) return;
            if (head == tail) { head = tail = null; }
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

        @Override
        public void addAfter(Nodo<T> node, T valor) {
            if (node == null) return;
            Nodo<T> nuevo = new Nodo<>(valor);
            nuevo.next = node.next;
            nuevo.prev = node;
            if (node.next != null) node.next.prev = nuevo; else tail = nuevo;
            node.next = nuevo;
        }

        @Override
        public void addBefore(Nodo<T> node, T valor) {
            if (node == null) return;
            Nodo<T> nuevo = new Nodo<>(valor);
            nuevo.prev = node.prev;
            nuevo.next = node;
            if (node.prev != null) node.prev.next = nuevo; else head = nuevo;
            node.prev = nuevo;
        }

        @Override
        public void erase(T valor) {
            Nodo<T> target = Find(valor);
            if (target == null) return;
            if (target.prev != null) target.prev.next = target.next; else head = target.next;
            if (target.next != null) target.next.prev = target.prev; else tail = target.prev;
        }
    }
}