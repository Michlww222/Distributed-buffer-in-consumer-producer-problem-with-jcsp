import org.jcsp.lang.*;

public final class Main {


    public static void main (String[] args) {
        new Main();
    }
    public Main (){
        final int oneBufferMax = 20;
        final int producersNumber = 10;
        final int consumerNumber = 10;
        final int bufferNumber = 3;
        final int seconds = 10;
        Simulation ourSimulation = new Simulation();
        try {
            ourSimulation.simulate(oneBufferMax,producersNumber,consumerNumber,bufferNumber,seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}