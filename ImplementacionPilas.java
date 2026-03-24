import java.util.ArrayList;

public class ImplementacionPilas {
    public static void main(String[] args) {
        final int start = 10;
        final int end = 100000000;

        for (int size = start; size <= end; size *= 10) {
            final int currentSize = size;

            // ===== PUSH — O(1) =====
            System.out.println("\n[PUSH] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyStack<Integer> stack = new MyStack<>();
                for (int j = 0; j < currentSize; j++) stack.push(j);
                execSingle(currentSize, "push", () -> stack.push(currentSize), true);
            }

            // ===== POP — O(1) =====
            System.out.println("\n[POP] size=" + currentSize);
        for (int i = 0; i < 5; i++) {
            MyStack<Integer> stack = new MyStack<>();
            for (int j = 0; j < currentSize; j++) stack.push(j);

            int reps = 1000;
            long inicio = System.nanoTime();
            for (int r = 0; r < reps; r++) {
                stack.push(currentSize);
                stack.pop();
            }
            long finish = System.nanoTime();
            double nanos = (double)(finish - inicio) / reps;
            System.out.printf("  [%s] N=%d → %.4f nanosegundos (promedio de %d reps)%n",
                    "pop", currentSize, nanos, reps);
        }

            // ===== PEEK — O(1) =====
            System.out.println("\n[PEEK] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyStack<Integer> stack = new MyStack<>();
                for (int j = 0; j < currentSize; j++) stack.push(j);
                execSingle(currentSize, "peek", () -> stack.peek(), true);
            }

            // ===== DELETE — O(n) =====
            System.out.println("\n[DELETE] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyStack<Integer> stack = new MyStack<>();
                for (int j = 0; j < currentSize; j++) stack.push(j);
                execSingle(currentSize, "delete", () -> stack.delete(0), false);
            }

            // ===== SIZE — O(1) =====
            System.out.println("\n[SIZE] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyStack<Integer> stack = new MyStack<>();
                for (int j = 0; j < currentSize; j++) stack.push(j);
                execSingle(currentSize, "size", () -> stack.size(), true);
            }

            // ===== ISEMPTY — O(1) =====
            System.out.println("\n[ISEMPTY] size=" + currentSize);
            for (int i = 0; i < 5; i++) {
                MyStack<Integer> stack = new MyStack<>();
                for (int j = 0; j < currentSize; j++) stack.push(j);
                execSingle(currentSize, "isEmpty", () -> stack.isEmpty(), true);
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

class MyStack<T> {
    public ArrayList<T> pila;

    public MyStack() {
        this.pila = new ArrayList<>();
    }

    public void push(T x) {
        pila.add(x);
    }

    public T pop() {
        if (isEmpty()) throw new RuntimeException("Pila vacía");
        T x = pila.get(pila.size() - 1);
        pila.remove(pila.size() - 1);
        return x;
    }

    public T peek() {
        if (isEmpty()) throw new RuntimeException("Pila vacía");
        return pila.get(pila.size() - 1);
    }

    public boolean isEmpty() {
        return pila.isEmpty();
    }

    public int size() {
        return pila.size();
    }

    public boolean delete(T n) {
        for (int i = pila.size() - 1; i >= 0; i--) {
            if (pila.get(i).equals(n)) {
                pila.remove(i);
                return true;
            }
        }
        return false;
    }
}