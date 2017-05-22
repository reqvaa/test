import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ExamDay {

    private AtomicInteger examsCounter = new AtomicInteger(0);

    private Random random;

    private LinkedList<Student> students;

    private AtomicBoolean gradesPosted = new AtomicBoolean(false);

    public ExamDay() {
        random = new Random(System.currentTimeMillis());
        students = new LinkedList<>();
    }

    public int nextInt() {
        return random.nextInt(800);
    }

    public int nextPriority() {
        switch (random.nextInt(2)) {
        case 0:
            return Thread.MIN_PRIORITY;
        case 1:
            return Thread.NORM_PRIORITY;
        case 2:
            return Thread.MAX_PRIORITY;
        }
        return Thread.MAX_PRIORITY;
    }

    public int nextGrade() {
        int result = random.nextInt(100);
        return result < 10 ? result + 10 : result;
    }

    public int startExam() {
        return examsCounter.incrementAndGet();
    }

    public int getExamNumber() {
        return examsCounter.get();
    }

    public void postGrades() {
        gradesPosted.set(true);
        Student previousStudent = null;
        while (!students.isEmpty()) {
            Student student = students.pop();
            if (previousStudent != null) {
                student.doJoin(previousStudent);
            }
            previousStudent = student;
        }
    }

    public boolean isGradesPosted() {
        return gradesPosted.get();
    }

    public void arrive(Student student) {
        students.push(student);
    }

    public static void main(String[] args) {

        ExamDay examDay = new ExamDay();
        ExamRoom examRoom = new ExamRoom(Integer.parseInt(args[1]));

        int studentsCount = Integer.parseInt(args[0]);
        Student student = null;
        for (int i = 0; i < studentsCount; i++) {
            student = new Student("student-" + i, examDay, examRoom);
            student.start();
        }

        Instructor instructor = new Instructor(examDay, examRoom);
        instructor.start();

        try {
            student.join(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
