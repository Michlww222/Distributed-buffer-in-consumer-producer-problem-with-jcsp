import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Parallel;

public class endSimulation implements CSProcess {
    private CSProcess[] procList;
    private int seconds;
    private int rangeP;
    private int rangeC;
    private Statistics global;
    public endSimulation(int number,CSProcess[] procList,int rangeP,int rangeC,Statistics global){
        this.seconds = number;
        this.procList = procList;
        this.rangeP = rangeP;
        this.rangeC = rangeC;
        this.global = global;
    }
    public void run(){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----PRODUCERS-----");
        for(int i=0;i<rangeP;i++) {
            Producer current = (Producer) procList[i];
            System.out.println(current.getStatistic());
        }
        System.out.println("-------------------");
        System.out.println("-----CONSUMERS-----");
        for(int i=rangeP;i<rangeP+rangeC;i++) {
            Consumer current = (Consumer) procList[i];
            System.out.println(current.getStatistic());
        }
        System.out.println("-------------------");
        System.out.println("-----GLOBAL-----");
        System.out.println(global.returnStats());
        System.out.println("-------------------");
    }
}


