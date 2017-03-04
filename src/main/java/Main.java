import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by 77060 on 2016/12/13.
 */
public class Main {
    public static void throwE() {
        int x = 3 / 0;
        List<Integer> list = new ArrayList<>();

        try {
            Queue<Integer> q = new PriorityQueue<>();
        }
        finally{
            System.out.println("haha");
        }
    }
    public static void main(String[] args) {
        A a = new A();
        A.AA b = a.new AA();
        A.AA c = a.new AA();

        b.print();
        c.print();
    }
}


class A {
    int x = 0;

    public class AA {
        public void print() {
            System.out.println(++x);
        }
    }
}
