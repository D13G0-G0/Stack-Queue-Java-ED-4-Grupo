import java.util.ArrayList;

public class ImplementacionColas {
    public static void main(String[] args) {
        final int start = 10;
        final int end = 100000000;

        for (int size = start; size <= end; size *= 10) {
            final int currentSize = size;

            // ===== ENQUEUE — O(1) =====
            System.out.println("\n[ENQUEUE] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyQueue<Integer> queue = new MyQueue<>();
                for (int j = 0; j < currentSize; j++) queue.enqueue(j);
                execSingle(currentSize, "enqueue", () -> queue.enqueue(currentSize), true);
            }

            // ===== DEQUEUE — O(n) =====
            System.out.println("\n[DEQUEUE] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyQueue<Integer> queue = new MyQueue<>();
                for (int j = 0; j < currentSize; j++) queue.enqueue(j);
                execSingle(currentSize, "dequeue", () -> queue.dequeue(), false);
            }

            // ===== FRONT — O(1) =====
            System.out.println("\n[FRONT] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyQueue<Integer> queue = new MyQueue<>();
                for (int j = 0; j < currentSize; j++) queue.enqueue(j);
                execSingle(currentSize, "front", () -> queue.front(), true);
            }

            // ===== DELETE — O(n) =====
            System.out.println("\n[DELETE] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyQueue<Integer> queue = new MyQueue<>();
                for (int j = 0; j < currentSize; j++) queue.enqueue(j);
                execSingle(currentSize, "delete", () -> queue.delete(0), false);
            }

            // ===== SIZE — O(1) =====
            System.out.println("\n[SIZE] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyQueue<Integer> queue = new MyQueue<>();
                for (int j = 0; j < currentSize; j++) queue.enqueue(j);
                execSingle(currentSize, "size", () -> queue.size(), true);
            }

            // ===== ISEMPTY — O(1) =====
            System.out.println("\n[ISEMPTY] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyQueue<Integer> queue = new MyQueue<>();
                for (int j = 0; j < currentSize; j++) queue.enqueue(j);
                execSingle(currentSize, "isEmpty", () -> queue.isEmpty(), true);
            }
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
        System.out.printf("  [%s] N=%d → %.4f nanosegundos%n", method, size, nanos);
    }
}
}

class MyQueue<T> {
    public ArrayList<T> cola;

    public MyQueue() {
        this.cola = new ArrayList<>();
    }

    public void enqueue(T x) {
        cola.add(x);
    }

    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Cola vacía");
        T x = cola.get(0);
        cola.remove(0);
        return x;
    }

    public T front() {
        if (isEmpty()) throw new RuntimeException("Cola vacía");
        return cola.get(0);
    }

    public boolean isEmpty() {
        return cola.isEmpty();
    }

    public int size() {
        return cola.size();
    }

    public boolean delete(T n) {
        for (int i = 0; i < cola.size(); i++) {
            if (cola.get(i).equals(n)) {
                cola.remove(i);
                return true;
            }
        }
        return false;
    }
}