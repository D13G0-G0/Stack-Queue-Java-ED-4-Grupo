import java.util.Random;

public class Listas {

    public static void main(String[] args) {    
        long[] tamanos = {10, 100, 1000, 10000, 100000, 1000000}; 
        Random rand = new Random();

        System.out.println("====================================================================================================================================");
        System.out.println("                                       ANALISIS DE COMPLEJIDAD");
        System.out.println("====================================================================================================================================");
        System.out.printf("%-10s | %-12s | %-10s | %-10s | %-12s | %-12s | %-12s | %-10s\n", 
            "Tamaño(N)", "PushBack", "Find", "PopBack", "PushFront", "AddAfter", "AddBefore", "Erase");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

        for (long n : tamanos) {
            DoubleListWithTail<Integer> lista = new DoubleListWithTail<>();

            long inicioPush = System.nanoTime();
            for (int i = 0; i < n; i++) {
                lista.PushBack(rand.nextInt(1000));
            }
            long finPush = System.nanoTime() - inicioPush;

            lista.PushBack(-999); 
            long inicioFind = System.nanoTime();
            Listas.Nodo<Integer> nodoReferencia = lista.Find(-999);
            long finFind = System.nanoTime() - inicioFind;

            long inicioAfter = System.nanoTime();
            lista.addAfter(nodoReferencia, 555);
            long finAfter = System.nanoTime() - inicioAfter;

            long inicioBefore = System.nanoTime();
            lista.addBefore(nodoReferencia, 444);
            long finBefore = System.nanoTime() - inicioBefore;

            long inicioErase = System.nanoTime();
            lista.erase(-999);
            long finErase = System.nanoTime() - inicioErase;

            long inicioPop = System.nanoTime();
            lista.PopBack();
            long finPop = System.nanoTime() - inicioPop;

            long inicioFront = System.nanoTime();
            lista.PushFront(rand.nextInt(1000));
            long finFront = System.nanoTime() - inicioFront;

            System.out.printf("%-10d | %-12d | %-10d | %-10d | %-12d | %-12d | %-12d | %-10d\n", 
                n, finPush, finFind, finPop, finFront, finAfter, finBefore, finErase);

            lista = null;
            System.gc();
        }
        System.out.println("====================================================================================================================================");
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

    // Implementación detallada solicitada
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

        @Override
        public void addAfter(Nodo<T> node, T valor) {
            if (node == null) return;
            Nodo<T> nuevo = new Nodo<>(valor);
            nuevo.next = node.next;
            nuevo.prev = node;
            if (node.next != null) {
                node.next.prev = nuevo;
            } else {
                tail = nuevo;
            }
            node.next = nuevo;
        }

        @Override
        public void addBefore(Nodo<T> node, T valor) {
            if (node == null) return;
            Nodo<T> nuevo = new Nodo<>(valor);
            nuevo.prev = node.prev;
            nuevo.next = node;
            if (node.prev != null) {
                node.prev.next = nuevo;
            } else {
                head = nuevo;
            }
            node.prev = nuevo;
        }

        @Override
        public void erase(T valor) {
            Nodo<T> target = Find(valor);
            if (target == null) return;

            if (target.prev != null) {
                target.prev.next = target.next;
            } else {
                head = target.next;
            }

            if (target.next != null) {
                target.next.prev = target.prev;
            } else {
                tail = target.prev;
            }
        }
    }
    
}