
public class Student extends ExamThread {

    private int examsTaken;

    public Student(String name, ExamDay day, ExamRoom room) {
        super(name, day, room);
    }

    @Override
    public void run() {

        doSleep(day.nextInt());

        msg("Got to school.");

        while (!day.isGradesPosted()) {

            while (examsTaken < 2) {

                int defaultPriority = getPriority();

                setPriority(day.nextPriority());

                waitRoom();

                doSleep(10);
                setPriority(defaultPriority);

                if (!room.enter(this)) {
                    yield();
                    yield();
                    continue;
                }

                msg("Got into the room.");

                examsTaken++;

                try {
                    sleep(20000);
                } catch (InterruptedException e) {
                    doSleep(day.nextInt());
                    continue;
                }

            }
        }

        doSleep(day.nextInt());
    }

    public void doJoin(Student student) {
        if (!student.isAlive()) {
            return;
        }
        try {
            student.join();
        } catch (InterruptedException e) {

        }
    }

    private void waitRoom() {
        msg("Waiting room.");
        while (!room.isOpened()) {
            doSleep(10);
        }
    }

    public Integer getWork() {
        return day.nextGrade();
    }

}
