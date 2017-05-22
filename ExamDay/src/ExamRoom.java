import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ExamRoom {

    private AtomicBoolean opened = new AtomicBoolean(false);

    private LinkedList<Student> students;

    private int capacity;

    private AtomicInteger counter = new AtomicInteger(0);

    public ExamRoom(int capacity) {
        students = new LinkedList<>();
        this.capacity = capacity;
    }

    public void open() {
        counter.set(0);
        opened.set(true);
    }

    public void close() {
        opened.set(false);
    }

    public boolean isOpened() {
        return opened.get();
    }

    public synchronized boolean enter(Student student) {
        if (counter.get() >= capacity) {
            close();
            return false;
        }
        counter.incrementAndGet();
        students.push(student);
        return true;
    }

    public Student expel() {
        if (students.isEmpty()) {
            return null;
        }
        return students.pop();
    }

}
