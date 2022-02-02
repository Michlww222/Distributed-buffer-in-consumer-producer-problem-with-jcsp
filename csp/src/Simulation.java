import org.jcsp.lang.*;

public class Simulation {
    final Any2AnyChannel channelToBuffer = new StandardChannelFactory().createAny2Any();

    static CSProcess[] procList;
    Any2OneChannel[] toNextBuffer;
    Statistics[] privateStatistics;
    Statistics globalStats;

    public void simulate(int bufferMax,int producerNumber,int consumerNumber,int bufferNumber,int seconds) throws InterruptedException {

        this.procList = new CSProcess[producerNumber + consumerNumber + bufferNumber + 1];
        this.toNextBuffer = new Any2OneChannel[bufferNumber];
        this.privateStatistics = new Statistics[producerNumber+consumerNumber];
        this.globalStats = new Statistics(bufferMax,-1);
        for(int i=0;i<producerNumber+consumerNumber;i+=1){
            privateStatistics[i] = new Statistics(bufferMax,i);
        }
        for(int i=0;i<bufferNumber;i+=1){
            toNextBuffer[i] = new StandardChannelFactory().createAny2One();
        }
        for(int i=0;i<producerNumber;i++){
            procList[i] = new Producer(i,bufferMax,privateStatistics[i],globalStats);
        }
        for(int i=producerNumber;i<producerNumber + consumerNumber;i++){
            procList[i] = new Consumer(i,bufferMax,privateStatistics[i],globalStats);
        }
        for(int i=producerNumber + consumerNumber;i<producerNumber + consumerNumber + bufferNumber;i++){
            if(i != producerNumber + consumerNumber + bufferNumber -1){
                procList[i] = new Buffer(i,toNextBuffer[i-(producerNumber + consumerNumber)],toNextBuffer[i+1-(producerNumber + consumerNumber)],bufferMax);
            }
            else{
                procList[i] = new Buffer(i,toNextBuffer[i-(producerNumber + consumerNumber)],toNextBuffer[0],bufferMax);
            }
        }
        procList[producerNumber + consumerNumber + bufferNumber] = new endSimulation(seconds,procList,producerNumber,consumerNumber,globalStats);
        Parallel par = new Parallel(procList);
        par.run();
    }

    public static Buffer getRandomBuffer(){
        int value = (int)(Math.random()*(3)) + 10 + 10;
        return (Buffer) procList[value];
    }



}
