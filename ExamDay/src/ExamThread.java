
public class ExamThread extends Thread {

    protected ExamDay day;
    protected ExamRoom room;
    private long time;

    public ExamThread(String name, ExamDay day, ExamRoom room) {
        super(name);
        this.day = day;
        this.room = room;
        this.time = System.currentTimeMillis();
    }

    protected void doSleep(long time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {

        }
    }

    protected void msg(String message) {
        System.out.println(String.format("[%d]%s: %s", System.currentTimeMillis() - time, getName(), message));
    }

}
