import java.util.HashMap;
import java.util.Map;

public class Instructor extends ExamThread {

    private Map<Integer, Map<Student, Integer>> results;

    public Instructor(ExamDay examDay, ExamRoom room) {
        super("Instructor", examDay, room);
        results = new HashMap<>();
    }

    @Override
    public void run() {

        doSleep(day.nextInt());

        msg("Got to the school.");

        while (day.startExam() < 4) {
            room.open();

            msg("Opened room");

            doSleep(15000);

            msg("Wrapping examen");

            Student student;
            Map<Student, Integer> examResult = new HashMap<>();
            while ((student = room.expel()) != null) {
                if (!student.isInterrupted()) {
                    student.interrupt();
                }
                examResult.put(student, student.getWork());
            }
            results.put(day.getExamNumber(), examResult);

            doSleep(day.nextInt());

        }
        
        day.postGrades();

    }

}
